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
    

    public CustomTag() {}

    public CustomTag(String customTagId, String customTagName) {
        super();
        this.customTagId = customTagId;
        this.customTagName = customTagName;
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coverFrequency == null) ? 0 : coverFrequency.hashCode());
        result = prime * result + ((coverNumber == null) ? 0 : coverNumber.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((customTagId == null) ? 0 : customTagId.hashCode());
        result = prime * result + ((customTagName == null) ? 0 : customTagName.hashCode());
        result = prime * result + ((customTagSource == null) ? 0 : customTagSource.hashCode());
        result = prime * result + ((customTagType == null) ? 0 : customTagType.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
        result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
        result = prime * result + ((recommendFlag == null) ? 0 : recommendFlag.hashCode());
        result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CustomTag other = (CustomTag) obj;
        if (coverFrequency == null) {
            if (other.coverFrequency != null) return false;
        } else if (!coverFrequency.equals(other.coverFrequency)) return false;
        if (coverNumber == null) {
            if (other.coverNumber != null) return false;
        } else if (!coverNumber.equals(other.coverNumber)) return false;
        if (createTime == null) {
            if (other.createTime != null) return false;
        } else if (!createTime.equals(other.createTime)) return false;
        if (customTagId == null) {
            if (other.customTagId != null) return false;
        } else if (!customTagId.equals(other.customTagId)) return false;
        if (customTagName == null) {
            if (other.customTagName != null) return false;
        } else if (!customTagName.equals(other.customTagName)) return false;
        if (customTagSource == null) {
            if (other.customTagSource != null) return false;
        } else if (!customTagSource.equals(other.customTagSource)) return false;
        if (customTagType == null) {
            if (other.customTagType != null) return false;
        } else if (!customTagType.equals(other.customTagType)) return false;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (isDeleted == null) {
            if (other.isDeleted != null) return false;
        } else if (!isDeleted.equals(other.isDeleted)) return false;
        if (parentId == null) {
            if (other.parentId != null) return false;
        } else if (!parentId.equals(other.parentId)) return false;
        if (recommendFlag == null) {
            if (other.recommendFlag != null) return false;
        } else if (!recommendFlag.equals(other.recommendFlag)) return false;
        if (updateTime == null) {
            if (other.updateTime != null) return false;
        } else if (!updateTime.equals(other.updateTime)) return false;
        return true;
    }
    
    
    
}