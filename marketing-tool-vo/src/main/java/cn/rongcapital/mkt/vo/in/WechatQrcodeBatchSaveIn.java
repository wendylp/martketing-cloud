package cn.rongcapital.mkt.vo.in;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class WechatQrcodeBatchSaveIn extends BaseInput{
	
	@NotEmpty
	@JsonProperty("batch_id")
	private String batchId;
	
	@NotEmpty
    private List<AssociationTag> association_tags;
	
	@NotEmpty
    @JsonProperty("user_token")
    private String userToken;
	
	@JsonProperty("expiration_time")
	private String expirationTime;
	
	private String comment;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public List<AssociationTag> getAssociation_tags() {
		return association_tags;
	}

	public void setAssociation_tags(List<AssociationTag> association_tags) {
		this.association_tags = association_tags;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	

}
