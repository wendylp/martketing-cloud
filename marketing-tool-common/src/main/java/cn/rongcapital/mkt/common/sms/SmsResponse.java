package cn.rongcapital.mkt.common.sms;

public class SmsResponse {
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 结果代码
	 */
	private String code;
	/**
	 * 结果提示信息
	 */
	private String msg;

	public SmsResponse() {
		// default
	}

	public SmsResponse(String phone, String code, String msg) {
		this.phone = phone;
		this.code = code;
		this.msg = msg;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "SmsResponse [phone=" + phone + ", code=" + code + ", msg=" + msg + "]";
	}

}
