package cn.rongcapital.mkt.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("smsServiceImpl")
public class SmsServiceImpl implements SmsService {

	@Value("${sms.incake.url}")
	private String message_send_url;
	@Value("${sms.incake.name}")
	private String default_partner_name;
	@Value("${sms.incake.no}")
	private String default_partner_no;
	@Value("${sms.incake.key}")
	private String incake_num;

	@Override
	public boolean sendSms(String phoneNum, String msg) {
		System.out.println("======================>>>" + message_send_url);
		return SUCCESS;
	}



}
