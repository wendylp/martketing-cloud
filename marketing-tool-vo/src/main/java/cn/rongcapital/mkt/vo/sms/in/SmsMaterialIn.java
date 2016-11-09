package cn.rongcapital.mkt.vo.sms.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsMaterialIn {
	
	private Integer id;
	@NotEmpty
	private String name;	
	@NotNull
	private Integer smsType;
	@NotNull
	private Integer smsSignId;
	@NotEmpty
	private String smsSignName;
	@NotNull
	private Integer smsTempletId;	
	@NotEmpty
	private String smsTempletContent;
	
    private String creator;

    private String updateUser;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSmsType() {
		return smsType;
	}

	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}

	public Integer getSmsSignId() {
		return smsSignId;
	}

	public void setSmsSignId(Integer smsSignId) {
		this.smsSignId = smsSignId;
	}

	public String getSmsSignName() {
		return smsSignName;
	}

	public void setSmsSignName(String smsSignName) {
		this.smsSignName = smsSignName;
	}

	public Integer getSmsTempletId() {
		return smsTempletId;
	}

	public void setSmsTempletId(Integer smsTempletId) {
		this.smsTempletId = smsTempletId;
	}

	public String getSmsTempletContent() {
		return smsTempletContent;
	}

	public void setSmsTempletContent(String smsTempletContent) {
		this.smsTempletContent = smsTempletContent;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
    
    
}
