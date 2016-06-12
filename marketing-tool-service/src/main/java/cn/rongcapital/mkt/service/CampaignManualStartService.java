package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.in.CampaignManualStartIn;
import cn.rongcapital.mkt.vo.out.CampaignManualStartOut;

public interface CampaignManualStartService {

	public CampaignManualStartOut campaignManualStart(CampaignManualStartIn body, SecurityContext securityContext);
}
