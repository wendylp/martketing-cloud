package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class CustomTagDeleteIn {

	@NotEmpty
	private String userToken = null;
	
	@NotNull
	private Integer tagId = null;

	@JsonProperty("user_token")
	public String getUserToken() {
		return userToken;
	}

	@JsonProperty("tag_id")
	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
}
