package cn.rongcapital.mkt.service.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.base.TaskManager;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.service.CampaignManualStartService;
import cn.rongcapital.mkt.vo.in.CampaignManualStartIn;
import cn.rongcapital.mkt.vo.out.CampaignManualStartOut;

@Service
public class CampaignManualStartServiceImple implements CampaignManualStartService {

	@Autowired
	CampaignHeadDao campaignHeadDao;
	@Autowired
	TaskManager taskManager;
	@Autowired
	private TaskScheduleDao taskScheduleDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public CampaignManualStartOut campaignManualStart(CampaignManualStartIn body, SecurityContext securityContext) {
		CampaignManualStartOut ur = checkPublishStatus(body.getCampaignHeadId());
		if(null != ur) {
			return ur;
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

	private CampaignManualStartOut checkPublishStatus(int id) {
		 CampaignManualStartOut ur = null;
		 CampaignHead t = new CampaignHead(); 
		 t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		 t.setId(id);
		 List<CampaignHead> segList = campaignHeadDao.selectList(t);
		 if(CollectionUtils.isNotEmpty(segList)) {
			 CampaignHead ch = segList.get(0);
			 //只有发布状态的活动才能被手动开启
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
	
}
