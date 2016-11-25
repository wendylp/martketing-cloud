package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.CampaignActionSaveAudienceDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.AudienceListPartyMap;
import cn.rongcapital.mkt.po.CampaignActionSaveAudience;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignActionSaveAudienceTask extends BaseMQService implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CampaignActionSaveAudienceDao campaignActionSaveAudienceDao;
	@Autowired
	private AudienceListPartyMapDao audienceListPartyMapDao;
	
	public void task (TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
		CampaignActionSaveAudience campaignActionSaveAudienceT = new CampaignActionSaveAudience();
		campaignActionSaveAudienceT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignActionSaveAudienceT.setCampaignHeadId(campaignHeadId);
		campaignActionSaveAudienceT.setItemId(itemId);
		List<CampaignActionSaveAudience> campaignActionSaveAudienceList = campaignActionSaveAudienceDao.selectList(campaignActionSaveAudienceT);
		if(CollectionUtils.isEmpty(campaignActionSaveAudienceList) ||
		   null == campaignActionSaveAudienceList.get(0).getAudienceId()) {
			logger.error("没有配置节点属性,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignActionSaveAudience campaignActionSaveAudience = campaignActionSaveAudienceList.get(0);
		logger.info("key is ============================{}", campaignHeadId+"-"+itemId);
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
					    logger.info("监听到消息 ================== {}", message.toString());
						List<Segment> segmentList = (List<Segment>)((ObjectMessage)message).getObject();
						if(CollectionUtils.isNotEmpty(segmentList)) {
							processMqMessage(segmentList,campaignHeadId,
											 itemId,campaignEndsList,campaignActionSaveAudience);
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
	private void processMqMessage(List<Segment> segmentList,
			  Integer campaignHeadId,String itemId,
			  List<CampaignSwitch> campaignEndsList,
			  CampaignActionSaveAudience campaignActionSaveAudience) {
		List<Segment> segmentListToNext = new ArrayList<Segment>();
		String queueKey = campaignHeadId+"-"+itemId;
		for(Segment segment:segmentList) {
			if(!checkNodeAudienceExist(campaignHeadId, itemId, segment.getDataId())) {
				insertNodeAudience(campaignHeadId, itemId, segment);
				Integer dataId = segment.getDataId();
				AudienceListPartyMap audienceListPartyMapT = new AudienceListPartyMap();
				audienceListPartyMapT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				audienceListPartyMapT.setPartyId(dataId);
				audienceListPartyMapT.setAudienceListId(campaignActionSaveAudience.getAudienceId());
				int count = audienceListPartyMapDao.selectListCount(audienceListPartyMapT);
				if(count == 0) {
					audienceListPartyMapDao.insert(audienceListPartyMapT);
				}
				segmentListToNext.add(segment);
			}
		}
		if(CollectionUtils.isNotEmpty(campaignEndsList)) {
			for(CampaignSwitch cs:campaignEndsList) {
				//发送segment数据到后面的节点
				sendDynamicQueue(segmentListToNext, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
				logicDeleteNodeAudience(campaignHeadId,itemId,segmentListToNext);
				logger.info(queueKey+"-out:"+JSON.toJSONString(segmentListToNext));
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
