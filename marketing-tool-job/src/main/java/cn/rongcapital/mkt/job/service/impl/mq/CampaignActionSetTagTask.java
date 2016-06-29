package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.Arrays;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignActionSetTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignActionSetTag;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignActionSetTagTask extends BaseMQService implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignActionSetTagDao campaignActionSetTagDao;
	@Autowired
	private CustomTagMapDao customTagMapDao;
	
	public void task (TaskSchedule taskSchedule){
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
		CampaignActionSetTag campaignActionSetTagT = new CampaignActionSetTag();
		campaignActionSetTagT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignActionSetTagT.setCampaignHeadId(campaignHeadId);
		campaignActionSetTagT.setItemId(itemId);
		List<CampaignActionSetTag> campaignActionSetTagDaoList = campaignActionSetTagDao.selectList(campaignActionSetTagT);
		if(CollectionUtils.isEmpty(campaignActionSetTagDaoList) ||
		   StringUtils.isBlank(campaignActionSetTagDaoList.get(0).getTagIds())) {
			logger.error("没有配置节点属性,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignActionSetTag campaignActionSetTag = campaignActionSetTagDaoList.get(0);
		String tagIds = campaignActionSetTag.getTagIds();
		Queue queue = getDynamicQueue(campaignHeadId+"-"+itemId);//获取MQ中的当前节点对应的queue
		MessageConsumer consumer = getQueueConsumer(queue);//获取queue的消费者对象
		//监听MQ的listener
		MessageListener listener = new MessageListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onMessage(Message message) {
				if(message!=null) {
					try {
						//获取segment list数据对象
						List<Segment> segmentList = (List<Segment>)((ObjectMessage)message).getObject();
						if(CollectionUtils.isNotEmpty(segmentList)) {
							processMqMessage(segmentList,campaignHeadId, itemId,campaignEndsList,tagIds);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
				}
			}
		};
		if(null != consumer){
			try {
				//设置监听器
				consumer.setMessageListener(listener);
				consumerMap.put(campaignHeadId+"-"+itemId, consumer);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}     
		}
	}

	private void processMqMessage(List<Segment> segmentList, Integer campaignHeadId,String itemId,
								  List<CampaignSwitch> campaignEndsList, String tagIds) {
		for(Segment segment:segmentList) {
			NodeAudience nodeAudience = new NodeAudience();
			nodeAudience.setCampaignHeadId(campaignHeadId);
			nodeAudience.setItemId(itemId);
			nodeAudience.setDataId(segment.getDataId());
			nodeAudience.setName(segment.getName());
			if(!checkNodeAudienceExist(campaignHeadId, itemId, segment.getDataId())) {
				mongoTemplate.insert(nodeAudience);//插入mongo的node_audience表
			}
			Integer dataId = segment.getDataId();
			List<String> tagIdList = Arrays.asList(tagIds);
			for(String idStr:tagIdList) {
				int tagId = Integer.parseInt(idStr);
				CustomTagMap customTagMapT = new CustomTagMap();
				customTagMapT.setTagId(tagId);
				customTagMapT.setMapId(dataId);
				customTagMapDao.insert(customTagMapT);
			}
		}
		if(CollectionUtils.isNotEmpty(campaignEndsList)) {
			for(CampaignSwitch cs:campaignEndsList) {
				//发送segment数据到后面的节点
				sendDynamicQueue(segmentList, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
				deleteNodeAudience(campaignHeadId,itemId,segmentList);
			}
		}
	}
	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		super.cancelCampaignInnerTask(taskSchedule);
	}

	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}
	

}
