package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class WechatQrcode extends BaseQuery {
    private Integer id;

    private String wxName;

    private Integer chCode;

    private String isAudience;

    private String audienceName;

    private String relatedTags;

    private String comments;

    private Date createTime;

    private Date expirationTime;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName == null ? null : wxName.trim();
    }

    public Integer getChCode() {
        return chCode;
    }

    public void setChCode(Integer chCode) {
        this.chCode = chCode;
    }

    public String getIsAudience() {
        return isAudience;
    }

    public void setIsAudience(String isAudience) {
        this.isAudience = isAudience == null ? null : isAudience.trim();
    }

    public String getAudienceName() {
        return audienceName;
    }

    public void setAudienceName(String audienceName) {
        this.audienceName = audienceName == null ? null : audienceName.trim();
    }

    public String getRelatedTags() {
        return relatedTags;
    }

    public void setRelatedTags(String relatedTags) {
        this.relatedTags = relatedTags == null ? null : relatedTags.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}
