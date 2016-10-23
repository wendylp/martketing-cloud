package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletIn;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletListIn;

public interface SmsTempletService {

	public BaseOutput smsTempletList(SmsTempletListIn smsTempletListIn);
	
	public BaseOutput smsTempletList(String userId,Integer index,Integer size,Integer channelType,Integer type,String content);
	
	public BaseOutput insertSmsTemplet(SmsTempletIn smsTempletIn);
	
}
