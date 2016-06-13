package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.UserSessionUtil;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.service.CampaignHeaderUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CampaignHeadUpdateIn;

@Service
public class CampaignHeaderUpdateServiceImpl implements CampaignHeaderUpdateService {

	@Autowired
	private CampaignHeadDao campaignHeadDao;
	
	@Override
	public BaseOutput campaignHeaderUpdate(CampaignHeadUpdateIn body, SecurityContext securityContext) {
		BaseOutput ur = checkPublishStatus(body.getCampaignId());
		if(null != ur) {
			return ur;
		}
		CampaignHead t = new CampaignHead();  
        t.setId(body.getCampaignId());
    	t.setName(body.getCampaignName());
    	t.setPublishStatus(body.getPublishStatus());
    	Date now = new Date();
    	t.setCreateTime(now);
    	campaignHeadDao.updateById(t);
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("oper", UserSessionUtil.getUserNameByUserToken());//TO DO:获取当前用户名
    	map.put("updatetime", DateUtil.getStringFromDate(now, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
    	map.put("id", t.getId());
    	ur = new BaseOutput(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
    	ur.getData().add(map);
    	ur.setTotal(ur.getData().size());
    	return ur;
	}

	private BaseOutput checkPublishStatus(int id) {
		 BaseOutput ur = null;
		 CampaignHead t = new CampaignHead(); 
		 t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		 t.setId(id);
		 List<CampaignHead> segList = campaignHeadDao.selectList(t);
		 if(null != segList && segList.size() > 0) {
			 CampaignHead ch = segList.get(0);
			if(ch.getPublishStatus() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS) {
				ur = new BaseOutput(ApiErrorCode.BIZ_ERROR_CANPAIGN_IN_PROGRESS.getCode(),
									ApiErrorCode.BIZ_ERROR_CANPAIGN_IN_PROGRESS.getMsg(),
									ApiConstant.INT_ZERO,null);
			}
			if(ch.getPublishStatus() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH) {
				ur = new BaseOutput(ApiErrorCode.BIZ_ERROR_CANPAIGN_FINISH.getCode(),
									ApiErrorCode.BIZ_ERROR_CANPAIGN_FINISH.getMsg(),
									ApiConstant.INT_ZERO,null);
			}
		 } else {
			ur = new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
								ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(),
								ApiConstant.INT_ZERO,null);
		 }
		 return ur;
	 }
}
