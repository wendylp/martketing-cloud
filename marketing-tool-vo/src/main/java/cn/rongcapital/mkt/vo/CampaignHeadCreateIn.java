/*************************************************
 * @功能简述: VO:SegmentHeadIn
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.vo;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class CampaignHeadCreateIn extends BaseInput {
	
    private Integer publishStatus = null;
  
    private String campaignName = null;
  
    @NotEmpty
    private String userToken = null;
  
    @JsonProperty("publish_status")
    public Integer getPublishStatus() {
	    return publishStatus;
  	}
  
  	public void setPublishStatus(Integer publishStatus) {
  		this.publishStatus = publishStatus;
  	}
  
    @JsonProperty("user_token")
    public String getUserToken() {
	    return userToken;
    }
  
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @JsonProperty("campaign_name")
	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
  
}

