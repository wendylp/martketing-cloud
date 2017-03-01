package cn.rongcapital.mkt.usersource.po;

import java.util.Date;

public class Usersource {
    private Byte id;

    private String name;

    private String identityId;

    private Boolean available;

    private String description;

    private Byte classificationId;

    private Byte status;

    private Boolean initialData;

    private Date createTime;

    private Date updateTime;

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId == null ? null : identityId.trim();
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Byte getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Byte classificationId) {
        this.classificationId = classificationId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getInitialData() {
        return initialData;
    }

    public void setInitialData(Boolean initialData) {
        this.initialData = initialData;
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