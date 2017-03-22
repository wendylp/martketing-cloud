package cn.rongcapital.mkt.api;

import java.util.HashMap;
import java.util.Map;

public class SmsStatusResponse {

	private String errorCode;
	private String errorMsg;
	private Map<String, SmsStatus> smsMap = new HashMap<String, SmsStatus>();

	public SmsStatusResponse() {
	}

	public SmsStatusResponse(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Map<String, SmsStatus> getSmsMap() {
		return smsMap;
	}

	public void setSmsMap(Map<String, SmsStatus> smsMap) {
		this.smsMap = smsMap;
	}

	@Override
	public String toString() {
		return "SmsStatusResponse [errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", smsMap=" + smsMap + "]";
	}

}
