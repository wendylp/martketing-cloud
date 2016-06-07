/*************************************************
 * @功能简述: 获取不同状态下的campaign数量
 * @项目名称: marketing cloud
 * @see: 
 * @author: yuhaixin
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.service.CampaignProgressStatusCountService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CampaignProgressStatusCountServiceImpl implements
		CampaignProgressStatusCountService {
	@Autowired
	private CampaignHeadDao campaignHeadDao;

	/**
	 * mkt.campaign.progressstatus.count.get
	 * @param
	 * @return BaseOutput
	 */
	@Override
	public BaseOutput campaignProgressStatusCountGet() {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

		// 查询campaign_head表，对status=0(代表本条数据有效)的数据，根据publish_status(各个值得含义下表中已经给出)做统计
		List<Object> campaignHeadCount = campaignHeadDao
				.selectCampaignHeadCountGroupByPublishStatus();
		
		baseOutput.setData(campaignHeadCount);
		baseOutput.setTotal(baseOutput.getData().size());
		
		return baseOutput;
	}

}
