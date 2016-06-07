package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class CampaignBodyCreateIn extends BaseInput {

	@NotEmpty
	private String userToken = null;
	
	private CampaignNodeChain campaignNodeChain = new CampaignNodeChain();

	@JsonProperty("campaign_node_chain")
	public CampaignNodeChain getCampaignNodeChain() {
		return campaignNodeChain;
	}

	public void setCampaignNodeChain(CampaignNodeChain campaignNodeChain) {
		this.campaignNodeChain = campaignNodeChain;
	}

	@JsonProperty("user_token")
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	
}
