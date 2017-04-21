package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.Date;
import java.util.List;

import javax.jms.MessageConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.service.CampaignDetailService;
import cn.rongcapital.mkt.service.CampaignHeaderUpdateService;
import cn.rongcapital.mkt.vo.out.CampaignManualStartOut;

@Service
public class CampaignTriggerTimeTask extends BaseMQService implements TaskService {

	private static Logger logger = LoggerFactory.getLogger(CampaignTriggerTimeTask.class);
	
    @Autowired
    private TaskScheduleDao taskScheduleDao;
    MessageConsumer consumer = null;
	@Autowired
	CampaignHeadDao campaignHeadDao;
	@Autowired
	private CampaignDetailService campaignDetailService;
    @Autowired
    CampaignHeaderUpdateService campaignHeaderUpdateService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		CampaignManualStartOut ur = checkPublishStatus(campaignHeadId);
		if(null != ur) {
			logger.error("活动不是发布状态，活动触发启动失败,campaignHeadId:"+campaignHeadId);
			return;//只有发布状态的活动才能被时间触发节点自动开启
		}	
		//检查细分状态,如果细分不存在或是未发布状态，则不能开启活动,如果检查通过，则设置用到的细分状态为:活动中
		CampaignManualStartOut urSegment = checkAndSetSegmentStatus(campaignHeadId);
		if(null != urSegment) {
			logger.error("活动使用的细分未发布或者不存在，活动触发启动失败,campaignHeadId:"+campaignHeadId);
			return;
		}
		//设置活动状态为:活动中
		CampaignHead t = new CampaignHead();
		t.setId(campaignHeadId);
		t.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS);
		t.setStartTime(new Date()); // @since 1.9 记录活动启动时间
		this.campaignDetailService.saveCampaignDetail(campaignHeadId); // @since 1.9 记录活动统计数据
		campaignHeadDao.updateById(t);
		//激活该活动对应的全部任务
		taskScheduleDao.activateTaskByCampaignHeadId(campaignHeadId);
	}

	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void stopTimerTriggerTask(TaskSchedule taskSchedule) {
		CampaignHead t = new CampaignHead();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setId(taskSchedule.getCampaignHeadId());
		List<CampaignHead> campaignHeads = campaignHeadDao.selectList(t);
		if(campaignHeads != null && campaignHeads.size() >0){
			CampaignHead campaignHead = campaignHeads.get(0);
			if( ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS == campaignHead.getPublishStatus()){
				campaignHead.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH);
				campaignHead.setEndTime(new Date()); // @since 1.9 记录活动手动停止时间
				campaignHeadDao.updateById(campaignHead);
				this.campaignDetailService.updateCampaignDetailMemberTotal(campaignHead.getId()); // @since 1.9 记录活动统计数据
			}
		}
	}
}
