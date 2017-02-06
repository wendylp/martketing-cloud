package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignActionSendSmsDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignActionSendSms;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.service.CampaignActionSendSmsService;
import cn.rongcapital.mkt.service.SmsActivationCreateOrUpdateService;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;

@Service
public class CampaignActionSendSmsTask extends BaseMQService implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private DataPartyDao dataPartyDao;
	@Autowired
	private CampaignActionSendSmsDao campaignActionSendSmsDao;
	
	@Autowired
	private CampaignActionSendSmsService campaignActionSendSmsService;
	
	@Autowired
	private SmsActivationCreateOrUpdateService smsActivationCreateOrUpdateService;
	
	
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		
		List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
		
		CampaignActionSendSms campaignActionSendSmsTemp = new CampaignActionSendSms();
		campaignActionSendSmsTemp.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignActionSendSmsTemp.setCampaignHeadId(campaignHeadId);
		campaignActionSendSmsTemp.setItemId(itemId);
		List<CampaignActionSendSms> campaignActionSendSmsList = campaignActionSendSmsDao.selectList(campaignActionSendSmsTemp);

		CampaignActionSendSms campaignActionSendSms = campaignActionSendSmsList.get(0);
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
							processMqMessage(segmentList,campaignHeadId,
											 itemId,campaignEndsList,campaignActionSendSms);
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
								  CampaignActionSendSms campaignActionSendSms) {
		String queueKey = campaignHeadId+"-"+itemId;
		
		List<Segment> segmentListToNext = new ArrayList<Segment>();//要传递给下面节点的数据(执行了发送微信操作的数据)
		
		Integer materialId = campaignActionSendSms.getSmsMaterialId();
		Set<Integer> dataPartyIds = new HashSet<>();
		for(Segment segment:segmentList) {
			if(!checkNodeAudienceExist(campaignHeadId, itemId, segment.getDataId())) {
				insertNodeAudience(campaignHeadId, itemId, segment);
				Integer dataId = segment.getDataId();
				//从mongo的主数据表中查询该条id对应的主数据详细信息
				DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(dataId)), DataParty.class);
				if(null!=dp && null !=dp.getMdType() &&
				    dp.getMdType() == ApiConstant.DATA_PARTY_MD_TYPE_POPULATION) {
					Integer mid = dp.getMid();
					cn.rongcapital.mkt.po.DataParty dataParty = new cn.rongcapital.mkt.po.DataParty();
					dataParty.setId(mid);
					List<cn.rongcapital.mkt.po.DataParty> dataPartyList = dataPartyDao.selectList(dataParty);
					
					segmentListToNext.add(segment);//数据放入向后面节点传递的list里
					dataPartyIds.add(dp.getMid());
				}
			}
		}
		
		//创建发送短信
		try {
			campaignActionSendSmsService.storeDataPartyIds(dataPartyIds,campaignActionSendSms.getId());
			SmsActivationCreateIn smsActivationCreateIn = campaignActionSendSmsService.getSmsActivationCreateIn(campaignHeadId, itemId, campaignActionSendSms);
			smsActivationCreateOrUpdateService.createOrUpdateSmsActivation(smsActivationCreateIn);
		} catch (JMSException e) {
			logger.info(queueKey+"-out:"+JSON.toJSONString(e.getMessage()));
		}

		if(CollectionUtils.isNotEmpty(campaignEndsList) && CollectionUtils.isNotEmpty(segmentListToNext)) {
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
		

	}

}
