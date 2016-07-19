/*************************************************
 * @功能简述: VO:SegmentHeadIn
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignHeadUpdateIn extends BaseInput {
	
	@NotNull
	private Integer campaignId;

	@NotNull
    private Byte publishStatus = null;
    
    @NotEmpty
    private String campaignName = null;
  
    @NotEmpty
    private String userToken = null;

	private Byte triggerType = null;

    @JsonProperty("campaign_head_id")
	public Integer getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}

	@JsonProperty("publish_status")
	public Byte getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Byte publishStatus) {
		this.publishStatus = publishStatus;
	}

	@JsonProperty("campaign_name")
	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	@JsonProperty("user_token")
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

    @JsonProperty("trigger_type")
    public Byte getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Byte triggerType) {
        this.triggerType = triggerType;
    }
}

