package cn.rongcapital.mkt.common.util;

import java.util.Map;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsServiceImplIncake implements SmsService {

	private static final String APPLICATION_JSON = "application/x-www-form-urlencoded";
	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
	private static final int HTTP_STATUS_OK = 200;
	private static final String HTTP_ERROR = "-99";
	private static final double HTTP_ERROR_INTEGER = -99;
	private static final String MESSAGE_SEND_URL = "http://gk.incake.net/YunXiangSMS/SendCRMSms";
	private static final String DEFAULT_PARTNER_NAME = "云像";
	private static final String DEFAULT_PARTNER_NO = "001";
	private static final String INCAKE_NUM = "ERROR_INCAKE_NUM";
	private static final String[] BATCH_RETURN_ERROR_CODE_LIST = { "-1001", "-1004", "-0000" };

	private CloseableHttpClient httpclient = null;
	private Logger logger = LoggerFactory.getLogger(SmsServiceImplIncake.class);

	@Override
	public boolean sendSms(String httpUrl, String phoneNum, String msg, Map<String, Object> param) {
		if (httpclient == null) {
			this.httpclient = HttpClients.createDefault();
		}

		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON); // 设置请求头

		try {
			// 设置post的body
			StringEntity stringEntity = new StringEntity(msg, "utf-8");
			stringEntity.setContentType(CONTENT_TYPE_TEXT_JSON);
			stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
			httpPost.setEntity(stringEntity);

			CloseableHttpResponse response = httpclient.execute(httpPost);// 发送请求
			StatusLine statusLine = response.getStatusLine();// 获取状态码
			int statusCode = statusLine.getStatusCode();
			logger.debug("短信状态码：{}", statusCode);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean sendMultSms(String httpUrl, String[] phoneNum, String msg, Map<String, Object> param) {
		// TODO Auto-generated method stub
		SmsService.super.sendMultSms(httpUrl, phoneNum, msg, param);
		return SmsService.SUCCESS;
	}

	@Override
	public boolean sendMultSms(String httpUrl, String[] phoneNum, String[] msg, Map<String, Object> param) {
		// TODO Auto-generated method stub
		SmsService.super.sendMultSms(httpUrl, phoneNum, msg, param);
		return SmsService.SUCCESS;
	}

}
