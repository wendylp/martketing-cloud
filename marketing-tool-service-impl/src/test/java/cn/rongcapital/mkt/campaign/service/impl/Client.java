package cn.rongcapital.mkt.campaign.service.impl;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Test;

public class Client {

	@Test
	public void tester() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost/api/v1");
		SmsApi smsApi = target.proxy(SmsApi.class);
		String str = smsApi.sendSms("1870428285", "hel");
		System.out.println(str);
	}
}
