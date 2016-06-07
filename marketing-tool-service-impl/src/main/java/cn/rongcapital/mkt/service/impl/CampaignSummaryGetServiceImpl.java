/*************************************************
 * @功能�?�?: 查询营销活动个数和触达人�?
 * @项目名称: marketing cloud
 * @see: 
 * @author: yuhaixin
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审�?: 
 *************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

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

	/**
	 * mkt.campaign.summary.get
	 * 
	 * @param
	 * @return BaseOutput
	 */
	@Override
	public BaseOutput campaignSummaryGet() {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		Map<String, Object> result = new HashMap<String, Object>();

		int totalCampaignCount = campaignHeadDao.selectCampaignCount();
		int totalCampaignAudienceCount = campaignBodyDao
				.selectCampaignAudienceCount();

		result.put("total_campaign_count", totalCampaignCount);
		result.put("total_campaign_audience_count", totalCampaignAudienceCount);
		baseOutput.getData().add(result);
		baseOutput.setTotal(baseOutput.getData().size());

		return baseOutput;
	}

}
