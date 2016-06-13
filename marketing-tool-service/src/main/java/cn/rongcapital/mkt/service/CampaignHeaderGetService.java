package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.CampaignHeaderGetOut;

public interface CampaignHeaderGetService {
	public CampaignHeaderGetOut campaignHeaderGet(String userToken, String ver, int campaignHeadId);
}
