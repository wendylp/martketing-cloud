package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
public class CampaignItemInfo {
	private String type;
	private List<CampaignItemAudiencesInfo> audiences = new ArrayList<CampaignItemAudiencesInfo>();

	public CampaignItemInfo() {
	}

	public CampaignItemInfo(String type) {
		this.type = type;
	}

	public CampaignItemInfo(String type, List<CampaignItemAudiencesInfo> audiences) {
		this.type = type;
		this.audiences = audiences;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<CampaignItemAudiencesInfo> getAudiences() {
		return audiences;
	}

	public void setAudiences(List<CampaignItemAudiencesInfo> audiences) {
		this.audiences = audiences;
	}

	@Override
	public String toString() {
		return "CampaignItemInfo [type=" + type + ", audiences=" + audiences + "]";
	}

}
