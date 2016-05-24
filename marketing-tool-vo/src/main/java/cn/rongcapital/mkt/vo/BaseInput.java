package cn.rongcapital.mkt.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseInput {

	@NotEmpty
	private String method;
	  
	@JsonProperty("method")
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
	  this.method = method;
	}

}
