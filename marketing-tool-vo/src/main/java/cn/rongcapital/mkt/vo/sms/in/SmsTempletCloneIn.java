package cn.rongcapital.mkt.vo.sms.in;


import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsTempletCloneIn {

	@NotNull
	private Integer id;
	
	@NotNull
	private Integer fromOrgId;
	
	@NotNull
	private Integer toOrgId;
	
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

	@JsonProperty("from_org_id")
	public Integer getFromOrgId() {
		return fromOrgId;
	}

	public void setFromOrgId(Integer fromOrgId) {
		this.fromOrgId = fromOrgId;
	}

	@JsonProperty("to_org_id")
	public Integer getToOrgId() {
		return toOrgId;
	}

	public void setToOrgId(Integer toOrgId) {
		this.toOrgId = toOrgId;
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
