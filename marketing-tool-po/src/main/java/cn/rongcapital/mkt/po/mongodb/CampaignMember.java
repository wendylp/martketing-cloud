package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
@Document(collection = "campaign_member")
public class CampaignMember implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Field(value = "campaign_id")
	private Integer campaignId;
	@Field(value = "item_id")
	private String itemId;
	private Integer mid;
	@Field(value = "member_id")
	private Integer memberId;
	private String phone;
	@Field("wx_id")
	private String wxId;
	@Field("open_id")
	private String openId;
	@Field("is_touch")
	private Integer isTouch = 0;
	@Field("is_respond")
	private Integer isRespond = 0;
	@Field("is_buy")
	private Integer isBuy = 0;
	@Field("deal_sum")
	private Double dealSum;
	@Field("coupon_id")
	private Integer couponId;
	@Field("order_id")
	private Long orderId;
	@Field("update_time")
	private Date updateTime = new Date();

	public CampaignMember() {
	}

	public CampaignMember(Integer campaignId, String itemId, Integer mid) {
		this.campaignId = campaignId;
		this.itemId = itemId;
		this.mid = mid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getIsTouch() {
		return isTouch;
	}

	public void setIsTouch(Integer isTouch) {
		this.isTouch = isTouch;
	}

	public Integer getIsRespond() {
		return isRespond;
	}

	public void setIsRespond(Integer isRespond) {
		this.isRespond = isRespond;
	}

	public Integer getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(Integer isBuy) {
		this.isBuy = isBuy;
	}

	public Double getDealSum() {
		return dealSum;
	}

	public void setDealSum(Double dealSum) {
		this.dealSum = dealSum;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "CampaignMember [campaignId=" + campaignId + ", itemId=" + itemId + ", mid=" + mid + ", memberId=" + memberId
				+ ", phone=" + phone + ", wxId=" + wxId + ", openId=" + openId + ", isTouch=" + isTouch + ", isRespond="
				+ isRespond + ", isBuy=" + isBuy + ", dealSum=" + dealSum + ", couponId=" + couponId + ", orderId=" + orderId
				+ ", updateTime=" + updateTime + "]";
	}

}
