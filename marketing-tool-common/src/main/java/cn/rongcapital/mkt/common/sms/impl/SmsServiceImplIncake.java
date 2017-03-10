package cn.rongcapital.mkt.common.sms.impl;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.sms.SmsResponse;
import cn.rongcapital.mkt.common.sms.SmsService;
import cn.rongcapital.mkt.vo.sms.out.SmsResponseVo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service("smsServiceImplIncake")
public class SmsServiceImplIncake implements SmsService {

	@Value("${sms.incake.url}")
	private String message_send_url;
	@Value("${sms.incake.name}")
	private String default_partner_name;
	@Value("${sms.incake.no}")
	private String default_partner_no;
	@Value("${sms.incake.key}")
	private String incake_num;

	private CloseableHttpClient httpclient = HttpClients.createDefault();
	private HttpPost httpPost = null;

	@Override
	public Map<String, SmsResponse> sendSms(String phoneNum, String msg) {
		List<SmsServiceImplIncake.SmsRequestVo> smses = new ArrayList<SmsServiceImplIncake.SmsRequestVo>(1);
		SmsServiceImplIncake.SmsRequestVo sms = new SmsServiceImplIncake.SmsRequestVo(default_partner_name, default_partner_no, phoneNum, msg);
		smses.add(sms);
		logger.debug("sms phone nuber is {}, content is {}.", phoneNum, msg);
		return this.send(smses);
	}

	@Override
	public Map<String, SmsResponse> sendMultSms(String[] phoneNum, String msg) {
		List<SmsServiceImplIncake.SmsRequestVo> smses = new ArrayList<SmsServiceImplIncake.SmsRequestVo>();
		SmsServiceImplIncake.SmsRequestVo sms = null;
		for (String num : phoneNum) {
			sms = new SmsServiceImplIncake.SmsRequestVo(default_partner_name, default_partner_no, num, msg);
			smses.add(sms);
		}
		logger.debug("sms phone nuber is {}, content is {}.", phoneNum, msg);
		return this.send(smses);
	}

	@Override
	public Map<String, SmsResponse> sendMultSms(String[] phoneNum, String[] msg) {
		List<SmsServiceImplIncake.SmsRequestVo> smses = new ArrayList<SmsServiceImplIncake.SmsRequestVo>();
		SmsServiceImplIncake.SmsRequestVo sms = null;
		for (int i = 0; i < phoneNum.length; i++) {
			sms = new SmsServiceImplIncake.SmsRequestVo(default_partner_name, default_partner_no, phoneNum[i], msg[i]);
			smses.add(sms);
		}
		logger.debug("sms phone nuber is {}, content is {}.", phoneNum, msg);
		return this.send(smses);
	}

	private Map<String, SmsResponse> send(List<SmsServiceImplIncake.SmsRequestVo> smses) {
		if (httpPost == null) {
			httpPost = new HttpPost(message_send_url);
		}

		httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON); // 设置请求头
		String json = JSONObject.toJSONString(smses);
		String data = "order=" + json + "&sagin=" + stringMD5(json + incake_num); // 签名方式：md5(json + key)
		StringEntity stringEntity = new StringEntity(data, "UTF-8");// 设置post的body
		stringEntity.setContentType(CONTENT_TYPE);
		stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
		httpPost.setEntity(stringEntity);
		CloseableHttpResponse response = null;
		Map<String, SmsResponse> res = new HashMap<String, SmsResponse>();
		try {
			response = httpclient.execute(httpPost);// 发送请求
			StatusLine statusLine = response.getStatusLine();// 获取状态码
			res.put("0000", new SmsResponse("", String.valueOf(statusLine.getStatusCode()), statusLine.getReasonPhrase()));

			if (statusLine.getStatusCode() == 200) { // 成功返回
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity, "utf-8");
				List<SmsResponseVo> outVoList = JSONArray.parseArray(result, SmsResponseVo.class);
				for (SmsResponseVo vo : outVoList) {
					res.put(vo.get_Phone(), new SmsResponse(vo.get_Phone(), vo.get_Code(), vo.get_Msg()));
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			res.put("0000", new SmsResponse("", "", e.getMessage()));
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.debug(e.getMessage());
				}
			}
		}

		return res;
	}

	public static String stringMD5(String input) {
		try {
			// 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			// 输入的字符串转换成字节数组
			byte[] inputByteArray = input.getBytes();

			// inputByteArray是输入字符串转换得到的字节数组
			messageDigest.update(inputByteArray);

			// 转换并返回结果，也是字节数组，包含16个元素
			byte[] resultByteArray = messageDigest.digest();

			// 字符数组转换成字符串返回
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String byteArrayToHex(byte[] byteArray) {
		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}

	public class SmsRequestVo implements Serializable {

		private static final long serialVersionUID = 1L;
		private String partner_Name;
		private String partner_No;
		private String partner_Phone;
		private String partner_Msg;
		private String partner_Time;

		public SmsRequestVo() {
			super();
		}

		public SmsRequestVo(String partner_Name, String partner_No, String partner_Phone, String partner_Msg) {
			super();
			this.partner_Name = partner_Name;
			this.partner_No = partner_No;
			this.partner_Phone = partner_Phone;
			this.partner_Msg = partner_Msg;
		}

		public String getPartner_Name() {
			return partner_Name;
		}

		public void setPartner_Name(String partner_Name) {
			this.partner_Name = partner_Name;
		}

		public String getPartner_No() {
			return partner_No;
		}

		public void setPartner_No(String partner_No) {
			this.partner_No = partner_No;
		}

		public String getPartner_Phone() {
			return partner_Phone;
		}

		public void setPartner_Phone(String partner_Phone) {
			this.partner_Phone = partner_Phone;
		}

		public String getPartner_Msg() {
			return partner_Msg;
		}

		public void setPartner_Msg(String partner_Msg) {
			this.partner_Msg = partner_Msg;
		}

		public String getPartner_Time() {
			return partner_Time;
		}

		public void setPartner_Time(String partner_Time) {
			this.partner_Time = partner_Time;
		}

	}
}
