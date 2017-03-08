package cn.rongcapital.mkt.common.sms.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import cn.rongcapital.mkt.common.sms.SmsService;

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
	public int sendMultSms(String[] phoneNum, String msg) {
		StringBuilder sb = new StringBuilder();
		for(String num : phoneNum){
			sb.append(num + ",");
		}
		sb.substring(0, sb.length()-1);
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair nameValuePair = null;
		nameValuePair = new BasicNameValuePair("userId", "userId");
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("password", "password");
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("pszMobis", sb.toString());
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("pszMsg", msg);
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("pszSubPort", "pszSubPort");
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("MsgId", "MsgId");
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("iReqType", "iReqType");
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("Sa", "MsgId");
		params.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("multixmt", "multixmt");
		params.add(nameValuePair);
		this.send(httpUrl, params);
		return phoneNum.length;
	}

	private List<? extends NameValuePair> generate() {

	}

	@Override
	public int sendMultSms(String[] phoneNum, String[] msg) {

		try{
			StringBuffer multixmt = new StringBuffer();// 批量请求包字符串

			for (int i = 0; i < phoneNum.hashCode(); i++) {
				multixmt.append("0").append("|");// 用户自定义流水号，不带请输入0（流水号范围-（ 2^63） ……2^63-1）
				multixmt.append("*").append("|");// 通道号，不需要请填*
				multixmt.append(phoneNum[i]).append("|");// 设置手机号码
				String strBase64Msg = new String(Base64.encodeBase64(msg[i].getBytes("GBK")));// 设置短信内容
				multixmt.append(strBase64Msg).append(",");
			}
			String Multixmt = multixmt.substring(0, multixmt.length() - 1);// 截取最后一个逗号

			Params params = new Params();
			params.setUserId(userId);// 设置账号
			params.setPassword(password);// 设置密码
			params.setMultixmt(Multixmt);// 设置批量请求包



			CHttpPost sms=new CHttpPost();//短信请求业务类
			StringBuffer strPtMsgId=new StringBuffer("");//如果成功，存流水号。失败，存错误码。
			int result = sms.SendMultixSms(strPtMsgId, ip, port, strUserId, strPwd, multixMts);
			// 短信息发送接口（不同内容群发，可自定义不同流水号，自定义不同扩展号） POST请求。
			if(result==0){//返回0，则提交成功
			System.out.println("发送成功： "+strPtMsgId.toString());//打印流水号
			}else{//返回非0，则提交失败
			System.out.println("发送失败： "+strPtMsgId.toString());//打印错误码
			}
			}catch (Exception e) {
			e.printStackTrace();//异常处理
			}
		
		
		// TODO Auto-generated method stub
		return SmsService.super.sendMultSms(phoneNum, msg);
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

	public class MULTIX_MT {
		private String strUserMsgId;/* 用户自定义的消息编号 */
		private String strSpNumber;/* 通道,可填完整,可不填,可填*,可只填扩展 */
		private String strMobile;/* 手机号 */
		private String strBase64Msg;/* 短信内容,需为 base64 编码,编码前为 GBK */

		public String getStrUserMsgId() {
			return strUserMsgId;
		}

		public void setStrUserMsgId(String strUserMsgId) {
			this.strUserMsgId = strUserMsgId;
		}

		public String getStrSpNumber() {
			return strSpNumber;
		}

		public void setStrSpNumber(String strSpNumber) {
			this.strSpNumber = strSpNumber;
		}

		public String getStrMobile() {
			return strMobile;
		}

		public void setStrMobile(String strMobile) {
			this.strMobile = strMobile;
		}

		public String getStrBase64Msg() {
			return strBase64Msg;
		}

		public void setStrBase64Msg(String strBase64Msg) {
			this.strBase64Msg = strBase64Msg;
		}
	}

	public class Params {

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

	/**
	 * 使用post请求
	 * 
	 * @param obj
	 *            请求参数对象
	 * @param httpUrl
	 *            请求URL地址
	 * @return 请求网关的返回值
	 * @throws Exception
	 */
	private String executePost(Object obj, String httpUrl) throws Exception {
		String result = "";
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		// 设置请求参数
		String fieldName = null;
		String fieldNameUpper = null;
		Method getMethod = null;
		String value = null;
		for (int i = 0; i < fields.length; i++) {// 循环设置请求参数
			fieldName = fields[i].getName();
			fieldNameUpper = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
			getMethod = cls.getMethod("get" + fieldNameUpper);// 通过反射获取get方法
			value = (String) getMethod.invoke(obj);// 通过反射调用get方法
			if (value != null) {// 请求参数值不为空，才设置
				params.add(new BasicNameValuePair(fieldName, value));
			}
		}
		HttpPost httppost = new HttpPost(httpUrl);// 设置HttpPost
		httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); // 设置参数的编码UTF-8
		HttpClient httpclient = new DefaultHttpClient();// 创建HttpClient
		HttpEntity entity = httpclient.execute(httppost).getEntity();// Http请求网关
		if (entity != null && entity.getContentLength() > 0) {// 返回值不为空，且长度大于0
			result = EntityUtils.toString(entity);// 将返回值转换成字符串
		}// 处理返回结果
		httpclient.getConnectionManager().shutdown();// 关闭连接
		return result;// 返回返回值
	}
}
