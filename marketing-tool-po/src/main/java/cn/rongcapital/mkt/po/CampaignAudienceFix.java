package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CampaignAudienceFix extends BaseQuery {
    private Integer id;

    private Integer campaignHeadId;

    private String itemId;

    private String name;

    private Integer audienceFixId;

    private String audienceFixName;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Boolean invalid = Boolean.FALSE;

    private Date invalidateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCampaignHeadId() {
        return campaignHeadId;
    }

    public void setCampaignHeadId(Integer campaignHeadId) {
        this.campaignHeadId = campaignHeadId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAudienceFixId() {
        return audienceFixId;
    }

    public void setAudienceFixId(Integer audienceFixId) {
        this.audienceFixId = audienceFixId;
    }

    public String getAudienceFixName() {
        return audienceFixName;
    }

    public void setAudienceFixName(String audienceFixName) {
        this.audienceFixName = audienceFixName == null ? null : audienceFixName.trim();
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

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }

    public Date getInvalidateTime() {
        return invalidateTime;
    }

    public void setInvalidateTime(Date invalidateTime) {
        this.invalidateTime = invalidateTime;
    }
}