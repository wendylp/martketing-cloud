package cn.rongcapital.mkt.common.sms.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import cn.rongcapital.mkt.common.util.SmsService;

/**
 * 梦网科技短信接口实现
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
public class SmsServiceImplmw implements SmsService {

	@Value("${sms.mw.httpUrl}")
	private String httpUrl; // 用户密码
	@Value("${sms.mw.userId}")
	private String userId; // 用户账号
	@Value("${sms.mw.password}")
	private String password; // 用户密码

	private CloseableHttpClient httpclient = HttpClients.createDefault();// 创建HttpClient

	@Override
	public boolean sendSms(String phoneNum, String msg) {
		// TODO Auto-generated method stub
		return SmsService.super.sendSms(phoneNum, msg);
	}

	@Override
	public boolean sendMultSms(String[] phoneNum, String msg) {
		// TODO Auto-generated method stub
		return SmsService.super.sendMultSms(phoneNum, msg);
	}

	@Override
	public boolean sendMultSms(String[] phoneNum, String[] msg) {
		// TODO Auto-generated method stub
		return SmsService.super.sendMultSms(phoneNum, msg);
	}


	public boolean sendSms(List<? extends NameValuePair> params, Integer sendFailureCount) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建连接
		CloseableHttpResponse response = null; // 返回结果

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));// 设置参数的编码UTF-8
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();// Http请求网关
			if (entity != null && entity.getContentLength() > 0) {// 返回值不为空，且长度大于0
				String result = EntityUtils.toString(entity);// 将返回值转换成字符串
			}// 处理返回结果

			return SUCCESS;// 返回返回值
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return FAILURE;
	}


	public class Param {

		private String userId; // 用户账号
		private String password; // 用户密码
		private String pszMobis; // 目标号码，用英文逗号(,)分隔，最大1000个号码。
		private String pszMsg; // 短信内容， 内容长度不大于350个汉字
		private String iMobiCount;// 号码个数（最大1000个手机）
		private String pszSubPort; // 子端口号码，不带请填星号{*} 长度由账号类型定4-6位，通道号总长度不能超过20位。
		private String MsgId; // 一个8字节64位的大整型（ INT64），格式化成的字符串。
		private String iReqType; // 请求类型(0: 上行&状态报告 1:上行 2: 状态报告)
		private String Sa; // 扩展号
		private String multixmt;// 批量短信请求包。该字段中包含N个短信包结构体。每个结构体间用固定的分隔符隔开。

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPszMobis() {
			return pszMobis;
		}

		public void setPszMobis(String pszMobis) {
			this.pszMobis = pszMobis;
		}

		public String getPszMsg() {
			return pszMsg;
		}

		public void setPszMsg(String pszMsg) {
			this.pszMsg = pszMsg;
		}

		public String getiMobiCount() {
			return iMobiCount;
		}

		public void setiMobiCount(String iMobiCount) {
			this.iMobiCount = iMobiCount;
		}

		public String getPszSubPort() {
			return pszSubPort;
		}

		public void setPszSubPort(String pszSubPort) {
			this.pszSubPort = pszSubPort;
		}

		public String getMsgId() {
			return MsgId;
		}

		public void setMsgId(String msgId) {
			MsgId = msgId;
		}

		public String getiReqType() {
			return iReqType;
		}

		public void setiReqType(String iReqType) {
			this.iReqType = iReqType;
		}

		public String getSa() {
			return Sa;
		}

		public void setSa(String sa) {
			Sa = sa;
		}

		public String getMultixmt() {
			return multixmt;
		}

		public void setMultixmt(String multixmt) {
			this.multixmt = multixmt;
		}

	}
}
