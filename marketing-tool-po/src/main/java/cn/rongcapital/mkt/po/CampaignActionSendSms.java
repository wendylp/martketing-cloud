package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CampaignActionSendSms extends BaseQuery {
    private Integer id;

    private Integer campaignHeadId;

    private String itemId;

    private String name;

    private Integer smsMaterialId;

    private Integer smsCategoryType;

    private Byte status;

    private Date createTime;

    private Date updateTime;

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

    public Integer getSmsMaterialId() {
        return smsMaterialId;
    }

    public void setSmsMaterialId(Integer smsMaterialId) {
        this.smsMaterialId = smsMaterialId;
    }

    public Integer getSmsCategoryType() {
        return smsCategoryType;
    }

    public void setSmsCategoryType(Integer smsCategoryType) {
        this.smsCategoryType = smsCategoryType;
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