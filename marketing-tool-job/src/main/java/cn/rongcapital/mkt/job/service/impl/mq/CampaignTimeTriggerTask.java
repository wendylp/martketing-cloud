package cn.rongcapital.mkt.job.service.impl.mq;

import javax.jms.MessageConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.TaskSchedule;

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
		//设置活动状态为:活动中
		CampaignHead t = new CampaignHead();
		t.setId(campaignHeadId);
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS);
		campaignHeadDao.updateById(t);
		//激活该活动对应的全部任务
		taskScheduleDao.activateTaskByCampaignHeadId(campaignHeadId);
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
