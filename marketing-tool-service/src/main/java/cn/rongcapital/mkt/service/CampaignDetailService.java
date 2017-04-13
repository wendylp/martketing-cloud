package cn.rongcapital.mkt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.vo.out.CampaignDetailOut;

public interface CampaignDetailService {

	public Logger logger = LoggerFactory.getLogger(CampaignDetailService.class);

	/**
	 * 活动名称
	 * 
	 * @param name
	 * @return
	 */
	public CampaignDetailOut campaignDetail(String name);
}
