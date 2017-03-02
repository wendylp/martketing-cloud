package cn.rongcapital.mkt.common.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SmsServiceTest {

	@Test
	public void sendSmsTest() {
		SmsServiceImpl sms = new SmsServiceImpl();
		sms.sendSms("www.baidu.com", "18704282857", "你好", null);
	}

	@Test
	public void sendSmsMultATest() {
		SmsServiceImpl sms = new SmsServiceImpl();
		sms.sendMultSms("www.baidu.com", new String[] { "18704282857", "18704282857", "18704282857" }, "你好", null);
	}

	@Test
	public void sendSmsMultBTest() {
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			map.putIfAbsent(i + "", "val_" + i);
		}
		SmsServiceImpl sms = new SmsServiceImpl();
		sms.sendMultSms("www.baidu.com", new String[] { "18704282857", "18704282857", "18704282857" }, new String[] { "大连", "北京", "上海" }, map);
	}
}
