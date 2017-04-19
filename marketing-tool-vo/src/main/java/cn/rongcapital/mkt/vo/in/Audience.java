package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class Audience extends BaseInput{

    @NotEmpty
    @JsonProperty("audience_name")
    private String audience_name;
	
	@NotEmpty
    @JsonProperty("user_token")
    private String userToken;
	
	@NotNull
    @JsonProperty("org_id")
    private Long orgid;
	
	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

    public String getAudience_name() {
        return audience_name;
    }

    public void setAudience_name(String audience_name) {
        this.audience_name = audience_name;
    }
	
	
}
