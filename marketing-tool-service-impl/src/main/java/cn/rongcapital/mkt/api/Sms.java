package cn.rongcapital.mkt.api;

import org.hibernate.validator.constraints.NotBlank;

public class Sms {

	@NotBlank
	private String receive;
	@NotBlank
	private String msg;

	public Sms() {
		//
	}

	public Sms(String receive, String msg) {
		this.receive = receive;
		this.msg = msg;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
