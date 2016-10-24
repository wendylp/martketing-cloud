package cn.rongcapital.mkt.vo.sms.in;


import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsTempletIn {

	@NotNull
    private Byte channelType;

	@NotNull
    private Byte type;

    private String auditor;

    private String creator;

    private String updateUser;

    @NotEmpty
    private String content;

    @JsonProperty("channel_type")
	public Byte getChannelType() {
		return channelType;
	}

	public void setChannelType(Byte channelType) {
		this.channelType = channelType;
	}

	@JsonProperty("type")
	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	@JsonProperty("auditor")
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
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

	@JsonProperty("content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    
}
