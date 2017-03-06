package cn.rongcapital.mkt.common.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@PropertySources({ @PropertySource(value = "classpath:sms.properties") })
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SmsServiceTest {

	@Autowired
	@Qualifier("smsServiceImplIncake")
	private SmsService incake;

	@Test
	public void sendSmsDefaultTest() {
		SmsService sms = new SmsServiceImpl();

		// 单挑发送
		sms.sendSms("18704282857", "你好");
		System.out.println("\r\n");

		// 多条相同内容发送
		sms.sendMultSms(new String[] { "18704282857", "18704282857", "18704282857" }, "你好");
		System.out.println("\r\n");

		// 多条不同内容发送
		sms.sendMultSms(new String[] { "18704282857", "18704282857", "18704282857" }, new String[] { "大连", "北京", "上海" });
	}


	@Test
	public void sendSmsServiceImplIncake() {

		String str = "林清轩山茶花润肤油30ml山茶花油精华油滋养肌肤补水保湿修护商品热卖至3月8日，请尽快购买！\r\n";

		// 单挑发送
		System.out.println(incake.sendSms("18704282857", str + "【单挑发送】"));

		// 多条相同内容发送
		incake.sendMultSms(new String[] { "18704282857", "18704282857" }, str + "【多条相同内容发送】");

		// 多条不同内容发送
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			map.putIfAbsent(i + "", "val_" + i);
		}
		incake.sendMultSms(new String[] { "18704282857", "18704282857" }, new String[] { str + "【多条不同内容发送】", str + "【多条不同内容发送】" });
	}

	/**
	 * 梦网科技短信测试
	 */
	@Test
	public void sendSmsServiceImplmw() {

	}
}

