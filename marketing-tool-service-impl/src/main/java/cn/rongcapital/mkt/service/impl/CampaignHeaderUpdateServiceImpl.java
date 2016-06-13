package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
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
    	BaseOutput ur = new BaseOutput(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
    	ur.getData().add(map);
    	ur.setTotal(ur.getData().size());
    	return ur;
	}

}
