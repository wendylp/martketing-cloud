package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.in.CampaignBodyCreateIn;
import cn.rongcapital.mkt.vo.out.CampaignBodyOut;

public interface CampaignBodyCreateService {

	public CampaignBodyOut campaignBodyCreate(CampaignBodyCreateIn body, SecurityContext securityContext);
}
