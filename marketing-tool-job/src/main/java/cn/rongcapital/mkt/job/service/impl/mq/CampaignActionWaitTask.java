package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

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
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignActionWaitDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignActionWait;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignActionWaitTask extends BaseMQService implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignActionWaitDao campaignActionWaitDao;
	private MessageConsumer consumer = null;
	@Autowired
    private ConcurrentTaskScheduler taskSchedule;
	private static ConcurrentHashMap<String, ScheduledFuture<?>> waitTaskMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();
	
	public void task (TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
		
		CampaignActionWait campaignActionWaitT = new CampaignActionWait();
		campaignActionWaitT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignActionWaitT.setCampaignHeadId(campaignHeadId);
		campaignActionWaitT.setItemId(itemId);
		List<CampaignActionWait> campaignActionWaitList =  campaignActionWaitDao.selectList(campaignActionWaitT);
		if(CollectionUtils.isEmpty(campaignActionWaitList) ||
		   ((null == campaignActionWaitList.get(0).getRelativeType() || 
		   null == campaignActionWaitList.get(0).getRelativeValue()) &&
		   (null == campaignActionWaitList.get(0).getSpecificTime()))
		   ) {
			  logger.error("没有配置等待动作的属性,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			  return;
		}
		Byte realativeType = campaignActionWaitList.get(0).getRelativeType();
		Integer relativeValue = campaignActionWaitList.get(0).getRelativeValue();
		Date specificTime = campaignActionWaitList.get(0).getSpecificTime();
		
		Queue queue = getDynamicQueue(campaignHeadId+"-"+itemId);//获取MQ中的当前节点对应的queue
		consumer = getQueueConsumer(queue);//获取queue的消费者对象
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
											 itemId,campaignEndsList,realativeType,relativeValue,specificTime);
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
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}     
		}
	}
		
	private void processMqMessage(List<Segment> segmentList,
								  Integer campaignHeadId,String itemId,List<CampaignSwitch> campaignEndsList,
								  Byte realativeType,Integer relativeValue,Date specificTime) {
		for(Segment segment:segmentList) {
			NodeAudience nodeAudience = new NodeAudience();
			nodeAudience.setCampaignHeadId(campaignHeadId);
			nodeAudience.setItemId(itemId);
			nodeAudience.setDataId(segment.getDataId());
			nodeAudience.setName(segment.getName());
			if(!checkNodeAudienceExist(campaignHeadId, itemId, segment.getDataId())) {
				mongoTemplate.insert(nodeAudience);//插入mongo的node_audience表
			}
		}
		if(CollectionUtils.isNotEmpty(campaignEndsList)) {
			for(CampaignSwitch cs:campaignEndsList) {
				Runnable task = new Runnable() {
					public void run() {
						sendDynamicQueue(segmentList, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
					}
				};
				ScheduledFuture<?> scheduledFuture = null;
				if(null != realativeType && null != relativeValue) {
					switch (realativeType) {
					case 0://小时
						scheduledFuture = taskSchedule.scheduleWithFixedDelay(task, relativeValue*3600*1000);
						break;
					case 1://天
						scheduledFuture = taskSchedule.scheduleWithFixedDelay(task, relativeValue*24*3600*1000);
						break;
					case 2://周
						scheduledFuture = taskSchedule.scheduleWithFixedDelay(task, relativeValue*7*24*3600*1000);
						break;
					case 3://月
						scheduledFuture = taskSchedule.scheduleWithFixedDelay(task, relativeValue*30*7*24*3600*1000);
						break;
					}
				} else if(null != specificTime) {
					scheduledFuture = taskSchedule.schedule(task, specificTime);
				}
				if(null != scheduledFuture) {
					waitTaskMap.put(System.currentTimeMillis()+"-"+segmentList.hashCode()+"-"+cs.getCampaignHeadId()+"-"+cs.getNextItemId(), scheduledFuture);
				}
			}
		}
	}	
	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		if(null != consumer) {
			try {
				consumer.close();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		waitTaskMap.forEach((k,scheduledFuture)->{
			scheduledFuture.cancel(true);
		});
	}
	
	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}

}
