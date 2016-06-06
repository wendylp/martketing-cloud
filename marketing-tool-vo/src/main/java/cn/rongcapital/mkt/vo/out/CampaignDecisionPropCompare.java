package cn.rongcapital.mkt.vo.out;


import java.util.Date;

public class CampaignDecisionPropCompare {
    private Integer id;

    private Integer campaignHeadId;

    private String itemId;

    private String name;

    private Byte propType;

    private Byte rule;

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

    public Byte getPropType() {
        return propType;
    }

    public void setPropType(Byte propType) {
        this.propType = propType;
    }

    public Byte getRule() {
        return rule;
    }

    public void setRule(Byte rule) {
        this.rule = rule;
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
