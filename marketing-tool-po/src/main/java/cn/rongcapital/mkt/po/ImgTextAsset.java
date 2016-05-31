package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class ImgTextAsset extends BaseQuery {
    private Integer id;

    private String name;

    private Byte type;

    private String ownerName;

    private String pcPreviewUrl;

    private String mobilePreviewUrl;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private String imgfileUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getPcPreviewUrl() {
        return pcPreviewUrl;
    }

    public void setPcPreviewUrl(String pcPreviewUrl) {
        this.pcPreviewUrl = pcPreviewUrl == null ? null : pcPreviewUrl.trim();
    }

    public String getMobilePreviewUrl() {
        return mobilePreviewUrl;
    }

    public void setMobilePreviewUrl(String mobilePreviewUrl) {
        this.mobilePreviewUrl = mobilePreviewUrl == null ? null : mobilePreviewUrl.trim();
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

    public String getImgfileUrl() {
        return imgfileUrl;
    }

    public void setImgfileUrl(String imgfileUrl) {
        this.imgfileUrl = imgfileUrl == null ? null : imgfileUrl.trim();
    }
}
