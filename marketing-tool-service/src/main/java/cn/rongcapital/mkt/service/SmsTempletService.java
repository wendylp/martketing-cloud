package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletIn;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletListIn;

public interface SmsTempletService {
	
	/**
	 * @param userId
	 * @param index
	 * @param size
	 * @param channelType
	 * @param type
	 * @param content
	 * @return
	 */
	public BaseOutput smsTempletList(String userId,Integer index,Integer size,Integer channelType,Integer type,String content);
	
	/**
	 * @param smsTempletIn
	 * @return
	 */
	public BaseOutput insertSmsTemplet(SmsTempletIn smsTempletIn);
	
}
