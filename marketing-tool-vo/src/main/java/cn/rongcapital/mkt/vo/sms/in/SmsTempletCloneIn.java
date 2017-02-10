package cn.rongcapital.mkt.vo.sms.in;


import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsTempletCloneIn {

	@NotNull
	private Integer id;
	
	@NotNull
	private List<Long> orgIds;
	
	@NotNull
	private String creator;
	
	@NotNull
	private String updateUser;
    
    @JsonProperty("id")
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonProperty("org_ids")
	public List<Long> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<Long> orgIds) {
		this.orgIds = orgIds;
	}

	@JsonProperty("creator")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@JsonProperty("update_user")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}
