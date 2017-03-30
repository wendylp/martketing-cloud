package cn.rongcapital.mkt.service.impl.vo;

import java.util.ArrayList;
import java.util.List;

public class SmsStatusResponse {

	private String errorCode;
	private String errorMsg;
	private List<SmsStatus> smsList = new ArrayList<SmsStatus>();

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

	public List<SmsStatus> getSmsList() {
		return smsList;
	}

	public void setSmsList(List<SmsStatus> smsList) {
		this.smsList = smsList;
	}

	@Override
	public String toString() {
		return "SmsStatusResponse [errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", smsList=" + smsList + "]";
	}
}
