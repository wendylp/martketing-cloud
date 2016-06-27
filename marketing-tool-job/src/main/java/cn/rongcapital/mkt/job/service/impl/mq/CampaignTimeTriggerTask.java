package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.List;

import javax.jms.MessageConsumer;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.vo.out.CampaignManualStartOut;

@Service
public class CampaignTimeTriggerTask extends BaseMQService implements TaskService {

//	private static Logger logger = LoggerFactory.getLogger(CampaignDecisionTagTaskImpl.class);
	
    @Autowired
    private TaskScheduleDao taskScheduleDao;
    MessageConsumer consumer = null;
	@Autowired
	CampaignHeadDao campaignHeadDao;
	
	@Override
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		CampaignManualStartOut ur = checkPublishStatus(campaignHeadId);
		if(null != ur) {
			return;//只有发布状态的活动才能被时间触发节点自动开启
		}	
		//设置活动状态为:活动中
		CampaignHead t = new CampaignHead();
		t.setId(campaignHeadId);
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS);
		campaignHeadDao.updateById(t);
		//激活该活动对应的全部任务
		taskScheduleDao.activateTaskByCampaignHeadId(campaignHeadId);
	}
	
	private CampaignManualStartOut checkPublishStatus(int id) {
		 CampaignManualStartOut ur = null;
		 CampaignHead t = new CampaignHead(); 
		 t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		 t.setId(id);
		 List<CampaignHead> segList = campaignHeadDao.selectList(t);
		 if(CollectionUtils.isNotEmpty(segList)) {
			 CampaignHead ch = segList.get(0);
			 //只有发布状态的活动才能被开启
			 if(ch.getPublishStatus() != ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH) {
				 ur = new CampaignManualStartOut(ApiErrorCode.BIZ_ERROR_CANPAIGN_CAN_NOT_MANUAL_START.getCode(),
					    ApiErrorCode.BIZ_ERROR_CANPAIGN_CAN_NOT_MANUAL_START.getMsg(),
						ApiConstant.INT_ZERO,null);
			 }
		 } else {
			ur = new CampaignManualStartOut(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
						 ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(),
						 ApiConstant.INT_ZERO,null);
		 }
		 return ur;
	 }
	
	public void cancelInnerTask(TaskSchedule taskSchedule){
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		//停止该活动对应的全部任务
		taskScheduleDao.deActivateTaskByCampaignHeadId(campaignHeadId);
		//设置活动状态为:已结束
		CampaignHead t = new CampaignHead();
		t.setId(campaignHeadId);
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH);
		campaignHeadDao.updateById(t);
	}

	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub
		
	}
	
}
