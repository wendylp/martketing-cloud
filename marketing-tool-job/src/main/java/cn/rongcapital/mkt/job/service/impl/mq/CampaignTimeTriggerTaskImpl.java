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

import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignTimeTriggerTaskImpl extends BaseMQService implements TaskService {

	private static Logger logger = LoggerFactory.getLogger(CampaignDecisionTagTaskImpl.class);
	
    @Autowired
    private TaskScheduleDao taskScheduleDao;
    
    @Autowired  
    private MQUtil mqUtil;
    
    MessageConsumer consumer = null;
    
	@Override
	public void task(TaskSchedule taskSchedule) {
//		Queue q = getDynamicQueue("ff");
//		List<Segment> ls = getQueueData(q);
//		System.out.println(ls);
//		test1();
		
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		//激活该活动对应的全部任务
		taskScheduleDao.activateTaskByCampaignHeadId(campaignHeadId);
	}
	
	private void test1() {
		List<Segment> segmentListToMqYes = new ArrayList<Segment>();//是分支的数据对象list
		Segment segment = new Segment();
		segment.setDataId("111");
		segmentListToMqYes.add(segment);
		mqUtil.sendDynamicQueue(segmentListToMqYes,"fff");
		Queue queue = getDynamicQueue("fff");//获取MQ
		consumer = getQueueConsumer(queue);
		MessageListener listner = new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try {
//					Message msg = consumer.receive();
					if(null!=message){
						@SuppressWarnings("unchecked")
						//获取数据对象list
						List<Segment> segmentList = (List<Segment>)((ObjectMessage)message).getObject();
						if(CollectionUtils.isNotEmpty(segmentList)) {
							for(Segment stt:segmentList) {
								System.out.println(stt.getDataId());
							}
							List<Segment> segmentListToMqYes = new ArrayList<Segment>();//是分支的数据对象list
							Segment segment = new Segment();
							segment.setDataId("222");
							segmentListToMqYes.add(segment);
							mqUtil.sendDynamicQueue(segmentListToMqYes,"fff");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		if(null!=consumer){
			try {
				consumer.setMessageListener(listner);
			} catch (Exception e) {
				e.printStackTrace();
			}     
		}
	}

	public void cancelInnerTask() {
		if(null != consumer) {
			try {
				consumer.close();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub
		
	}
	
}
