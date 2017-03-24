package cn.rongcapital.mkt.service.impl.vo;

public class SmsStatus {

	private String receive;
	private String errorCode;
	private String errorMsg;

	public SmsStatus() {

	}

	public SmsStatus(String receive, String errorCode, String errorMsg) {
		this.receive = receive;
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

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	@Override
	public String toString() {
		return "SmsStatus [receive=" + receive + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
	}

}
