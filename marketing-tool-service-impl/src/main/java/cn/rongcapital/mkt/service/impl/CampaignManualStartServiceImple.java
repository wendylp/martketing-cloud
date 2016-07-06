package cn.rongcapital.mkt.service.impl;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.impl.mq.BaseMQService;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.service.CampaignManualStartService;
import cn.rongcapital.mkt.vo.in.CampaignManualStartIn;
import cn.rongcapital.mkt.vo.out.CampaignManualStartOut;

@Service
public class CampaignManualStartServiceImple extends BaseMQService implements CampaignManualStartService {
	
	private static Logger logger = LoggerFactory.getLogger(CampaignManualStartServiceImple.class);
	@Autowired
	private CampaignHeadDao campaignHeadDao;
	@Autowired
	private TaskScheduleDao taskScheduleDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public CampaignManualStartOut campaignManualStart(CampaignManualStartIn body, SecurityContext securityContext) {
		CampaignManualStartOut ur = checkPublishStatus(body.getCampaignHeadId());
		if(null != ur) {
			logger.error("活动不是发布状态，无法手动开启,campaignHeadId:"+body.getCampaignHeadId());
			return ur;
		}		
		//检查细分状态,如果细分不存在或是未发布状态，则不能开启活动,如果检查通过，则设置用到的细分状态为:活动中
		CampaignManualStartOut urSegment = checkAndSetSegmentStatus(body.getCampaignHeadId());
		if(null != urSegment) {
			logger.error("活动使用的细分未发布或者不存在，无法手动开启,campaignHeadId:"+body.getCampaignHeadId());
			return urSegment;
		}
		//设置活动状态为:活动中
		CampaignHead t = new CampaignHead();
		t.setId(body.getCampaignHeadId());
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS);
		campaignHeadDao.updateById(t);
		//激活该活动对应的全部任务
	    taskScheduleDao.activateTaskByCampaignHeadId(body.getCampaignHeadId());
		CampaignManualStartOut out = new CampaignManualStartOut(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		return out;
	}
	
}
