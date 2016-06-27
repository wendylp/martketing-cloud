package cn.rongcapital.mkt.service.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
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
	
	@Override
	public CampaignManualStartOut campaignManualStart(CampaignManualStartIn body, SecurityContext securityContext) {
		CampaignHead t = new CampaignHead();
		t.setId(body.getCampaignHeadId());
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setPublishStatus(ApiConstant.SEGMENT_PUBLISH_STATUS_IN_CAMPAIGN);
		campaignHeadDao.updateById(t);
		taskManager.manualInitTask();//执行扫描任务表
		CampaignManualStartOut out = new CampaignManualStartOut(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		return out;
	}

}
