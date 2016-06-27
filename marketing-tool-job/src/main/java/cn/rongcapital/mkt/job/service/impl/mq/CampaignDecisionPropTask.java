package cn.rongcapital.mkt.job.service.impl.mq;

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

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignDecisionPropCompareDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignDecisionPropCompare;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;

public class CampaignDecisionPropTask extends BaseMQService implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CampaignDecisionPropCompareDao campaignDecisionPropCompareDao;
//	@Autowired
//	private MongoTemplate mongoTemplate;
	private MessageConsumer consumer = null;	
	
	public void task (TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		List<CampaignSwitch> campaignSwitchYesList = queryCampaignSwitchYesList(campaignHeadId, itemId);
		List<CampaignSwitch> campaignSwitchNoList = queryCampaignSwitchNoList(campaignHeadId, itemId);
		if(CollectionUtils.isEmpty(campaignSwitchYesList) && 
		   CollectionUtils.isEmpty(campaignSwitchNoList)) {
			return;//标签判断节点下面如果没有分支，则该条活动线路截止
		}
		CampaignDecisionPropCompare campaignDecisionPropCompareT = new CampaignDecisionPropCompare();
		campaignDecisionPropCompareT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignDecisionPropCompareT.setCampaignHeadId(campaignHeadId);
		campaignDecisionPropCompareT.setItemId(itemId);
		List<CampaignDecisionPropCompare> campaignDecisionPropCompareList = campaignDecisionPropCompareDao.selectList(campaignDecisionPropCompareT);
		if(CollectionUtils.isEmpty(campaignDecisionPropCompareList) || 
		   null == campaignDecisionPropCompareList.get(0).getRule()
		  ){
			logger.info("节点属性为空,return,campagin_head_id:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignDecisionPropCompare campaignDecisionPropCompare = campaignDecisionPropCompareList.get(0);
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
							processMqMessage(message,segmentList,campaignSwitchYesList,campaignSwitchNoList,campaignDecisionPropCompare);
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
	//处理listener接收到的数据
	//TO DO:目前属性判断分支，接收到的数据都会传递到下面节点,等需求明确了再完善
	private void processMqMessage(Message message,List<Segment> segmentList,
								  List<CampaignSwitch> campaignSwitchYesList,
								  List<CampaignSwitch> campaignSwitchNoList,
								  CampaignDecisionPropCompare campaignDecisionPropCompare) throws Exception{
//		List<Segment> segmentListToMqYes = new ArrayList<Segment>();//初始化"是"分支的数据对象list
//		List<Segment> segmentListToMqNo = new ArrayList<Segment>();//初始化"非"分支的数据对象list
//		Byte rule = campaignDecisionPropCompare.getRule();
//		for(Segment segment:segmentList) {
//			switch (rule) {
//			case 0://0：等于
//				break;
//			case 1://1:包含
//				break;
//			case 2://2:starts_with
//				break;
//			case 3://3:ends_with
//				break;
//			case 4://4:empty
//				break;
//			case 5://5:大于
//				break;
//			case 6://6:小于
//				break;
//			case 7://7:大于等于
//				break;
//			case 8://8:小于等于
//				break;
//			}
//		}
		if(CollectionUtils.isNotEmpty(campaignSwitchYesList)) {
			CampaignSwitch csYes = campaignSwitchYesList.get(0);
			sendDynamicQueue(segmentList, csYes.getCampaignHeadId() +"-"+csYes.getNextItemId());
		}
		if(CollectionUtils.isNotEmpty(campaignSwitchNoList)) {
			CampaignSwitch csNo = campaignSwitchNoList.get(0);
			sendDynamicQueue(segmentList, csNo.getCampaignHeadId() +"-"+csNo.getNextItemId());
		}
	}
	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		
	}
	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}

}
