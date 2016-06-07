package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.service.CampaignSummaryGetService;
import cn.rongcapital.mkt.vo.BaseOutput;


@Service
public class CampaignSummaryGetServiceImpl implements CampaignSummaryGetService {
	@Autowired
	private CampaignHeadDao campaignHeadDao;
	@Autowired
	private CampaignBodyDao campaignBodyDao;

	@Override
	public BaseOutput campaignSummaryGet() {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
		List<Object> data = new ArrayList<Object>();
		Map<String,Object> result = new HashMap<String, Object>();
		
		int totalCampaignCount = campaignHeadDao.selectListCount(null);
		int totalCampaignAudienceCount = campaignBodyDao.selectCampaignAudienceCount();
		
		result.put("total_campaign_count", totalCampaignCount);
		result.put("total_campaign_audience_count", totalCampaignAudienceCount);
		data.add(result);
		
		baseOutput.setData(data);
		baseOutput.setTotal(data.size());
		
		//return Response.ok().entity(result).build();
		return null;
	}

}
