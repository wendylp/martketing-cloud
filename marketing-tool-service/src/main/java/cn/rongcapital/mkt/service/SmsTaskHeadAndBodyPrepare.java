package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;

public interface SmsTaskHeadAndBodyPrepare {

	SmsTaskHead smsTaskHeadAndBodyFullFill(SmsActivationCreateIn smsActivationCreateIn,
			SmsTaskHead insertSmsTaskHead, Integer smsTaskTrigger, boolean isEventTask);
}
