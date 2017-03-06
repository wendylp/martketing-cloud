package cn.rongcapital.mkt.usersource.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UsersourceClassificationIn extends BaseInput{

	@JsonProperty("id")
	private Long id;
	
	@NotNull
	@JsonProperty("name")
	private String name;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
