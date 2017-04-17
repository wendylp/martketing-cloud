package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
public class CampaignDetailOut extends BaseOutput {

	private List<CampaignInfo> campaignInfos = new ArrayList<CampaignInfo>();

	public CampaignDetailOut() {
	}

	public CampaignDetailOut(List<CampaignInfo> campaignInfos) {
		this.campaignInfos = campaignInfos;
	}

	public List<CampaignInfo> getCampaignInfos() {
		return campaignInfos;
	}

	public void setCampaignInfos(List<CampaignInfo> campaignInfos) {
		this.campaignInfos = campaignInfos;
	}

	@Override
	public String toString() {
		return "CampaignDetailOut [campaignInfos=" + campaignInfos + "]";
	}

}
