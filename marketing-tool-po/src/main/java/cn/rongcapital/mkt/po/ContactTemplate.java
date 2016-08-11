package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class ContactTemplate extends BaseQuery {
    private Integer id;

    private String contactName;

    private String contactTitle;

    private String contactDescript;

    private String fieldName;

    private String fieldCode;

    private Byte selected;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private String qrcodeUrl;

    private Integer pageViews;

    private String keyList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle == null ? null : contactTitle.trim();
    }

    public String getContactDescript() {
        return contactDescript;
    }

    public void setContactDescript(String contactDescript) {
        this.contactDescript = contactDescript == null ? null : contactDescript.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode == null ? null : fieldCode.trim();
    }

    public Byte getSelected() {
        return selected;
    }

    public void setSelected(Byte selected) {
        this.selected = selected;
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

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl == null ? null : qrcodeUrl.trim();
    }

    public Integer getPageViews() {
        return pageViews;
    }

    public void setPageViews(Integer pageViews) {
        this.pageViews = pageViews;
    }

    public String getKeyList() {
        return keyList;
    }

    public void setKeyList(String keyList) {
        this.keyList = keyList == null ? null : keyList.trim();
    }
}
