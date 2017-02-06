package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CampaignActionSendSmsCoupon extends BaseQuery {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer campaignActionSendSmsId;

    private String couponId;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCampaignActionSendSmsId() {
        return campaignActionSendSmsId;
    }

    public void setCampaignActionSendSmsId(Integer campaignActionSendSmsId) {
        this.campaignActionSendSmsId = campaignActionSendSmsId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId == null ? null : couponId.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}