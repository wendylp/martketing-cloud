package cn.rongcapital.mkt.job.service.impl.mq;

import javax.jms.MessageConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.TaskSchedule;

@Service
public class CampaignTimeTriggerTaskImpl extends BaseMQService implements TaskService {

//	private static Logger logger = LoggerFactory.getLogger(CampaignDecisionTagTaskImpl.class);
	
    @Autowired
    private TaskScheduleDao taskScheduleDao;
    
    MessageConsumer consumer = null;
    
	@Override
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		//激活该活动对应的全部任务
		taskScheduleDao.activateTaskByCampaignHeadId(campaignHeadId);
	}
	
	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub
		
	}
	
}
