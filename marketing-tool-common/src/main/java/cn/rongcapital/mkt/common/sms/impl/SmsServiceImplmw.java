package cn.rongcapital.mkt.common.sms.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.sms.SmsResponse;
import cn.rongcapital.mkt.common.sms.SmsService;

/**
 * 梦网科技短信接口实现
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
@Service("smsServiceImplmw")
public class SmsServiceImplmw implements SmsService {

	@Value("${sms.mw.httpUrl}")
	private String httpUrl; // 用户密码
	@Value("${sms.mw.userId}")
	private String userId; // 用户账号
	@Value("${sms.mw.password}")
	private String password; // 用户密码

	private CloseableHttpClient httpclient = HttpClients.createDefault();// 创建HttpClient

	@Override
	public Map<String, SmsResponse> sendSms(String phoneNum, String msg) {
		List<BasicNameValuePair> params = this.generate();
		BasicNameValuePair nameValuePair = null;
		nameValuePair = new BasicNameValuePair("pszMobis", phoneNum);
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("pszMsg", msg);
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("iMobiCount", "1");
		params.add(nameValuePair);
		this.send(httpUrl, params);
		return null;
	}

	@Override
	public Map<String, SmsResponse> sendMultSms(String[] phoneNum, String msg) {
		StringBuilder sb = new StringBuilder();
		for(String num : phoneNum){
			sb.append(num + ",");
		}
		sb.substring(0, sb.length()-1);
		List<BasicNameValuePair> params = this.generate();
		BasicNameValuePair nameValuePair = null;
		nameValuePair = new BasicNameValuePair("pszMobis", sb.toString());
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("pszMsg", msg);
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("iMobiCount", phoneNum.length + "");
		params.add(nameValuePair);
		this.send(httpUrl, params);
		return null;
	}




	@Override
	public Map<String, SmsResponse> sendMultSms(String[] phoneNum, String[] msg) {
		int len = 0; // 发送成功计数器
		StringBuffer multixmt = new StringBuffer();// 批量请求包字符串
		for (int i = 0; i < phoneNum.hashCode(); i++) {
			try {
				multixmt.append("0").append("|");// 用户自定义流水号，不带请输入0（流水号范围-（ 2^63） ……2^63-1）
				multixmt.append("*").append("|");// 通道号，不需要请填*
				multixmt.append(phoneNum[i]).append("|");// 设置手机号码
				String strBase64Msg = new String(Base64.encodeBase64(msg[i].getBytes("GBK")));// 设置短信内容
				multixmt.append(strBase64Msg).append(",");
				len++;
			} catch (UnsupportedEncodingException e) {
				logger.debug("错误的短信编码", e);
			}
		}
		String multixmt_tostring = multixmt.substring(0, multixmt.length() - 1);// 截取最后一个逗号
		List<BasicNameValuePair> params = this.generate();
		BasicNameValuePair nameValuePair = new BasicNameValuePair("multixmt", multixmt_tostring);
		params.add(nameValuePair);
		this.send(httpUrl, params);
		return null;
	}

	/**
	 * 生成默认参数
	 * 
	 * @return
	 */
	private List<BasicNameValuePair> generate() {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair nameValuePair = null;
		nameValuePair = new BasicNameValuePair("userId", userId);
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("password", password);
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("pszSubPort", "*"); // 子端口号码，不带请填星号{*} 长度由账号类型定4-6位，通道号总长度不能超过20位。
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("MsgId", "0"); // 用户自定义流水号，不带请输入0（流水号范围-（ 2^63） ……2^63-1）
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("iReqType", "1"); // 请求类型(0: 上行&状态报告 1:上行 2: 状态报告)
		params.add(nameValuePair);
		return params;
	}

	public boolean send(String url, List<? extends NameValuePair> params) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建连接
		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			response = httpclient.execute(httpPost); // 在MC中到达这一步就算短信发送成功
			return SUCCESS;
		} catch (UnsupportedEncodingException e) {
			logger.error("短信编码异常", e);
		} catch (ClientProtocolException e) {
			logger.error("短信协议异常", e);
		} catch (IOException e) {
			logger.debug("发送短信失败", e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return FAILURE;
	}
}
