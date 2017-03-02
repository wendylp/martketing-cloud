package cn.rongcapital.mkt.common.util;

import java.util.Map;

/**
 * 短信接口， 支持单挑发送或者群发。
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
public interface SmsService {

	/**
	 * 短信发送结果
	 */
	public final static boolean SUCCESS = true;
	public final static boolean FAILURE = false;

	/**
	 * 单条短信发送
	 * 
	 * @return 发送成功返回true, 发送失败返回false.
	 */
	default public boolean sendSms(String httpUrl, String phoneNum, String msg, Map<String, Object> param) {
		System.out.println("手  机  号：" + phoneNum);
		System.out.println("短信内容：" + msg);
		System.out.println("平台地址：" + httpUrl);
		System.out.print("其它参数：");
		param.forEach((key, value) -> System.out.print(key + "=" + value + ", "));
		return SUCCESS;
	}

	/**
	 * 群发相同内容,例如：批量发送订单状态通知，活动信息群发
	 * 
	 * @param httpUrl
	 * @param phoneNum
	 * @param msg
	 * @param param
	 * @return 发送成功返回true, 发送失败返回false.
	 */
	default public boolean sendMultSms(String httpUrl, String[] phoneNum, String msg, Map<String, Object> param) {
		for (String phone : phoneNum) {
			System.out.println("手  机  号：" + phone);
			System.out.println("短信内容：" + msg + "\r\n");
		}
		System.out.println("平台地址：" + httpUrl);
		System.out.print("其它参数：");
		param.forEach((key, value) -> System.out.print(key + "=" + value + ", "));
		return SUCCESS;
	}

	/**
	 * 群发不相同内容,例如：批量发送短信内容带变量的订单状态通知，活动信息群发
	 * 
	 * @param httpUrl
	 * @param phoneNum
	 * @param msg
	 * @param param
	 * @return 发送成功返回true, 发送失败返回false.
	 */
	default public boolean sendMultSms(String httpUrl, String[] phoneNum, String[] msg, Map<String, Object> param) {
		if (phoneNum.length != msg.length) {
			return FAILURE;
		}
		for (int i = 0; i < phoneNum.length; i++) {
			System.out.println("手  机  号：" + phoneNum[i]);
			System.out.println("短信内容：" + msg[i] + "\r\n");
		}
		System.out.println("平台地址：" + httpUrl);
		System.out.print("其它参数：");
		param.forEach((key, value) -> System.out.print(key + "=" + value + ", "));
		return SUCCESS;
	}
}
