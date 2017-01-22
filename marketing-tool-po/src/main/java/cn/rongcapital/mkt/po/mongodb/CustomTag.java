package cn.rongcapital.mkt.po.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by byf on 1/15/17.
 */
@Document(collection = "custom_tag")
public class CustomTag implements Serializable {

    private static final long serialVersionUID = -7889851955972516269L;

    @Id
    private String id;

    @Field(value = "custom_tag_id")
    private String customTagId;

    @Field(value = "custom_tag_name")
    private String customTagName;

    @Field(value = "custom_tag_type")
    private Integer customTagType;

    @Field(value = "parent_id")
    private String parentId;

    @Field(value = "is_deleted")
    private Integer isDeleted;

    @Field(value = "recommend_flag")
    private Integer recommendFlag;

    @Field(value = "create_time")
    private Date createTime;

    @Field(value = "update_time")
    private Date updateTime;

    @Field(value = "custom_tag_source")
    private String customTagSource;
    
    @Field(value = "cover_number")
    private Integer coverNumber;// 覆盖人数
    
    @Field(value = "cover_frequency")
    private Integer coverFrequency;// 覆盖人次

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomTagId() {
        return customTagId;
    }

    public void setCustomTagId(String customTagId) {
        this.customTagId = customTagId;
    }

    public String getCustomTagName() {
        return customTagName;
    }

    public void setCustomTagName(String customTagName) {
        this.customTagName = customTagName;
    }

    public Integer getCustomTagType() {
        return customTagType;
    }

    public void setCustomTagType(Integer customTagType) {
        this.customTagType = customTagType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getRecommendFlag() {
        return recommendFlag;
    }

    public void setRecommendFlag(Integer recommnedFlag) {
        this.recommendFlag = recommnedFlag;
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

    public String getCustomTagSource() {
        return customTagSource;
    }

    public void setCustomTagSource(String customTagSource) {
        this.customTagSource = customTagSource;
    }

    public Integer getCoverNumber() {
        return coverNumber;
    }

    public void setCoverNumber(Integer coverNumber) {
        this.coverNumber = coverNumber;
    }

    public Integer getCoverFrequency() {
        return coverFrequency;
    }

    public void setCoverFrequency(Integer coverFrequency) {
        this.coverFrequency = coverFrequency;
    }

    @Override
    public String toString() {
        return "CustomTag [id=" + id + ", customTagId=" + customTagId + ", customTagName=" + customTagName
                + ", customTagType=" + customTagType + ", parentId=" + parentId + ", isDeleted=" + isDeleted
                + ", recommendFlag=" + recommendFlag + ", createTime=" + createTime + ", updateTime=" + updateTime
                + ", customTagSource=" + customTagSource + ", coverNumber=" + coverNumber + ", coverFrequency="
                + coverFrequency + "]";
    }
    
}