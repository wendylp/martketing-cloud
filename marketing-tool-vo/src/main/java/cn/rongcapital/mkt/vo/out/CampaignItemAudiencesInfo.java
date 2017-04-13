package cn.rongcapital.mkt.vo.out;

/**
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
public class CampaignItemAudiencesInfo {

	private Integer mid;
	private String mobile;
	private String openid;

	public CampaignItemAudiencesInfo() {
	}

	public CampaignItemAudiencesInfo(Integer mid) {
		this.mid = mid;
	}

	public CampaignItemAudiencesInfo(Integer mid, String mobile) {
		this.mid = mid;
		this.mobile = mobile;
	}

	public CampaignItemAudiencesInfo(Integer mid, String mobile, String openid) {
		this.mid = mid;
		this.mobile = mobile;
		this.openid = openid;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Override
	public String toString() {
		return "CampaignItemAudiencesInfo [mid=" + mid + ", mobile=" + mobile + ", openid=" + openid + "]";
	}

}
