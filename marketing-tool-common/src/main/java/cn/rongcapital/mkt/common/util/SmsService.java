package cn.rongcapital.mkt.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信接口， 支持单挑发送或者群发。
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
public interface SmsService {

	public Logger logger = LoggerFactory.getLogger(SmsService.class);

	/**
	 * 短信发送结果
	 */
	public final static boolean SUCCESS = true;
	public final static boolean FAILURE = false;
	public final static String APPLICATION_JSON = "application/x-www-form-urlencoded";
	public final static String CONTENT_TYPE = "text/json";

	/**
	 * 单条短信发送
	 * 
	 * @param phoneNum
	 * @param msg
	 * @return 发送成功返回true, 发送失败返回false.
	 */
	default public boolean sendSms(String phoneNum, String msg) {
		logger.info("\r\n手  机  号：" + phoneNum + "\r\n短信内容：" + msg + "\r\n");
		return SUCCESS;
	}

	/**
	 * 群发相同内容,例如：批量发送订单状态通知，活动信息群发
	 * 
	 * @param phoneNum
	 * @param msg
	 * @return 发送成功返回true, 发送失败返回false.
	 */
	default public boolean sendMultSms(String[] phoneNum, String msg) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String phone : phoneNum) {
			stringBuilder.append("手  机  号：" + phone + "\r\n短信内容：" + msg + "\r\n");
		}
		logger.info("\r\n" + stringBuilder.toString());
		return SUCCESS;
	}

	/**
	 * 群发不相同内容,例如：批量发送短信内容带变量的订单状态通知，活动信息群发
	 * 
	 * @param phoneNum
	 * @param msg
	 * @return 发送成功返回true, 发送失败返回false.
	 */
	default public boolean sendMultSms(String[] phoneNum, String[] msg) {
		if (phoneNum.length != msg.length) {
			return FAILURE;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < phoneNum.length; i++) {
			stringBuilder.append("手  机  号：" + phoneNum[i] + "\r\n短信内容：" + msg[i] + "\r\n");
		}
		logger.info("\r\n" + stringBuilder.toString());
		return SUCCESS;
	}

	/**
	 * 大陆号码或香港号码均可
	 */
	default public boolean isPhoneLegal(String str) throws PatternSyntaxException {
		return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	}

	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数 17+除9的任意数 147
	 */
	default public boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数
	 */
	default public boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
