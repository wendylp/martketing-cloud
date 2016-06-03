package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class CampaignActionSendPub extends BaseQuery {
    private Integer id;

    private String itemId;

    private String name;

    private Integer wechatH5Id;

    private String wechatH5Name;

    private String pubId;

    private String pubName;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getWechatH5Id() {
        return wechatH5Id;
    }

    public void setWechatH5Id(Integer wechatH5Id) {
        this.wechatH5Id = wechatH5Id;
    }

    public String getWechatH5Name() {
        return wechatH5Name;
    }

    public void setWechatH5Name(String wechatH5Name) {
        this.wechatH5Name = wechatH5Name == null ? null : wechatH5Name.trim();
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId == null ? null : pubId.trim();
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName == null ? null : pubName.trim();
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
