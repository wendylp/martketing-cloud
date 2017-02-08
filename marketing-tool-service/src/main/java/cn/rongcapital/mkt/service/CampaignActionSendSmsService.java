package cn.rongcapital.mkt.service;

import java.util.Set;

import cn.rongcapital.mkt.po.CampaignActionSendSms;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;

public interface CampaignActionSendSmsService {

	
	public SmsActivationCreateIn getSmsActivationCreateIn(Integer campaignHeadId,String itemId,CampaignActionSendSms campaignActionSendSms,Set<Integer> dataPartyIds);
	
	public void storeDataPartyIds(Set<Integer> dataPartyIds,Long targetId);
	
}
