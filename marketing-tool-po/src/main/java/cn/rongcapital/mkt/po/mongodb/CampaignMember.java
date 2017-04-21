package cn.rongcapital.mkt.po.mongodb;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */

public class CampaignMember {

	private Integer mid;
	@Field(value = "member_id")
	private Integer MemberId;
	private String Phone;
	@Field("wx_id")
	private String WxId;
	@Field("open_id")
	private String OpenId;
	@Field("is_touch")
	private Integer IsTouch;
	@Field("is_respond")
	private Integer IsRespond;
	@Field("is_buy")
	private Integer IsBuy;
	@Field("deal_sum")
	private Double DealSum;
	@Field("coupon_id")
	private Integer CouponId;
	@Field("order_id")
	private Long OrderId;
	@Field("update_time")
	private Date updateTime;

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getMemberId() {
		return MemberId;
	}

	public void setMemberId(Integer memberId) {
		MemberId = memberId;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getWxId() {
		return WxId;
	}

	public void setWxId(String wxId) {
		WxId = wxId;
	}

	public String getOpenId() {
		return OpenId;
	}

	public void setOpenId(String openId) {
		OpenId = openId;
	}

	public Integer getIsTouch() {
		return IsTouch;
	}

	public void setIsTouch(Integer isTouch) {
		IsTouch = isTouch;
	}

	public Integer getIsRespond() {
		return IsRespond;
	}

	public void setIsRespond(Integer isRespond) {
		IsRespond = isRespond;
	}

	public Integer getIsBuy() {
		return IsBuy;
	}

	public void setIsBuy(Integer isBuy) {
		IsBuy = isBuy;
	}

	public Double getDealSum() {
		return DealSum;
	}

	public void setDealSum(Double dealSum) {
		DealSum = dealSum;
	}

	public Integer getCouponId() {
		return CouponId;
	}

	public void setCouponId(Integer couponId) {
		CouponId = couponId;
	}

	public Long getOrderId() {
		return OrderId;
	}

	public void setOrderId(Long orderId) {
		OrderId = orderId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "CampaignMember [mid=" + mid + ", MemberId=" + MemberId + ", Phone=" + Phone + ", WxId=" + WxId + ", OpenId="
				+ OpenId + ", IsTouch=" + IsTouch + ", IsRespond=" + IsRespond + ", IsBuy=" + IsBuy + ", DealSum=" + DealSum
				+ ", CouponId=" + CouponId + ", OrderId=" + OrderId + ", updateTime=" + updateTime + "]";
	}

}
