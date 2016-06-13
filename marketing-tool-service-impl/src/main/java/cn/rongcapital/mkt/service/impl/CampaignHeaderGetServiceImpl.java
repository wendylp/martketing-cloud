package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.UserSessionUtil;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.service.CampaignHeaderGetService;
import cn.rongcapital.mkt.vo.out.CampaignHeaderGetDataOut;
import cn.rongcapital.mkt.vo.out.CampaignHeaderGetOut;

@Service
public class CampaignHeaderGetServiceImpl implements CampaignHeaderGetService {
	
	@Autowired
	CampaignHeadDao campaignHeadDao;

	@Override
	public CampaignHeaderGetOut campaignHeaderGet(String userToken, String ver, int campaignHeadId) {
		CampaignHead t = new CampaignHead();
		t.setId(campaignHeadId);
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		List<CampaignHead> list = campaignHeadDao.selectList(t);
		
		CampaignHeaderGetOut out = new CampaignHeaderGetOut(ApiConstant.INT_ZERO,
															ApiErrorCode.SUCCESS.getMsg(),
															ApiConstant.INT_ZERO);
		
		if(null != list && list.size() > 0){
			CampaignHead obj = list.get(0);
			CampaignHeaderGetDataOut chgto = new CampaignHeaderGetDataOut();
			chgto.setCampaignName(obj.getName());
			chgto.setPublishStatus(obj.getPublishStatus());
			chgto.setId(obj.getId());
			chgto.setOper(UserSessionUtil.getUserNameByUserToken());
			chgto.setUpdatetime(DateUtil.getStringFromDate(obj.getUpdateTime(), 
								ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
			out.getDataCutom().add(chgto);
		}
		out.setTotal(out.getDataCutom().size());
		return out;
	}

}
