package cn.rongcapital.mkt.po.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by byf on 1/15/17.
 */
@Document(collection = "custom_tag_category")
public class CustomTagCategory implements Serializable{

    private static final long serialVersionUID = 4433196889041138176L;

    @Id
    private String id;

    @Field(value = "custom_tag_category_id")
    private String customTagCategoryId;

    @Field(value = "custom_tag_category_name")
    private String customTagCategoryName;

    @Field(value = "custom_tag_category_type")
    private Integer customTagCategoryType;

    @Field(value = "level")
    private Integer level;

    @Field(value = "is_deleted")
    private Integer isDeleted;

    @Field(value = "parent_id")
    private String parentId;

    @Field(value = "create_time")
    private Date createTime;

    @Field(value = "update_time")
    private Date updateTime;

    @Field(value = "custom_tag_category_source")
    private String customTagCategorySource;

    @Field(value = "children_custom_tag_category_list")
    private ArrayList<String> childrenCustomTagCategoryList;

    @Field(value = "children_custom_tag_list")
    private ArrayList<String> childrenCustomTagList;
    
    public CustomTagCategory(){}
    
    public CustomTagCategory(String customTagCategoryId, String customTagCategoryName, Integer isDeleted,
            List<String> childrenCustomTagList) {
        super();
        this.customTagCategoryId = customTagCategoryId;
        this.customTagCategoryName = customTagCategoryName;
        this.isDeleted = isDeleted;
        this.childrenCustomTagList = (ArrayList)childrenCustomTagList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomTagCategoryId() {
        return customTagCategoryId;
    }

    public void setCustomTagCategoryId(String customTagCategoryId) {
        this.customTagCategoryId = customTagCategoryId;
    }

    public String getCustomTagCategoryName() {
        return customTagCategoryName;
    }

    public void setCustomTagCategoryName(String customTagCategoryName) {
        this.customTagCategoryName = customTagCategoryName;
    }

    public Integer getCustomTagCategoryType() {
        return customTagCategoryType;
    }

    public void setCustomTagCategoryType(Integer customTagCategoryType) {
        this.customTagCategoryType = customTagCategoryType;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getCustomTagCategorySource() {
        return customTagCategorySource;
    }

    public void setCustomTagCategorySource(String customTagCategorySource) {
        this.customTagCategorySource = customTagCategorySource;
    }

    public ArrayList<String> getChildrenCustomTagCategoryList() {
        return childrenCustomTagCategoryList;
    }

    public void setChildrenCustomTagCategoryList(ArrayList<String> childrenCustomTagCategoryList) {
        this.childrenCustomTagCategoryList = childrenCustomTagCategoryList;
    }

    public ArrayList<String> getChildrenCustomTagList() {
        return childrenCustomTagList;
    }

    public void setChildrenCustomTagList(ArrayList<String> childrenCustomTagList) {
        this.childrenCustomTagList = childrenCustomTagList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((childrenCustomTagCategoryList == null) ? 0 : childrenCustomTagCategoryList.hashCode());
        result = prime * result + ((childrenCustomTagList == null) ? 0 : childrenCustomTagList.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((customTagCategoryId == null) ? 0 : customTagCategoryId.hashCode());
        result = prime * result + ((customTagCategoryName == null) ? 0 : customTagCategoryName.hashCode());
        result = prime * result + ((customTagCategorySource == null) ? 0 : customTagCategorySource.hashCode());
        result = prime * result + ((customTagCategoryType == null) ? 0 : customTagCategoryType.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
        result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CustomTagCategory other = (CustomTagCategory) obj;
        if (childrenCustomTagCategoryList == null) {
            if (other.childrenCustomTagCategoryList != null) return false;
        } else if (!childrenCustomTagCategoryList.equals(other.childrenCustomTagCategoryList)) return false;
        if (childrenCustomTagList == null) {
            if (other.childrenCustomTagList != null) return false;
        } else if (!childrenCustomTagList.equals(other.childrenCustomTagList)) return false;
        if (createTime == null) {
            if (other.createTime != null) return false;
        } else if (!createTime.equals(other.createTime)) return false;
        if (customTagCategoryId == null) {
            if (other.customTagCategoryId != null) return false;
        } else if (!customTagCategoryId.equals(other.customTagCategoryId)) return false;
        if (customTagCategoryName == null) {
            if (other.customTagCategoryName != null) return false;
        } else if (!customTagCategoryName.equals(other.customTagCategoryName)) return false;
        if (customTagCategorySource == null) {
            if (other.customTagCategorySource != null) return false;
        } else if (!customTagCategorySource.equals(other.customTagCategorySource)) return false;
        if (customTagCategoryType == null) {
            if (other.customTagCategoryType != null) return false;
        } else if (!customTagCategoryType.equals(other.customTagCategoryType)) return false;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (isDeleted == null) {
            if (other.isDeleted != null) return false;
        } else if (!isDeleted.equals(other.isDeleted)) return false;
        if (level == null) {
            if (other.level != null) return false;
        } else if (!level.equals(other.level)) return false;
        if (parentId == null) {
            if (other.parentId != null) return false;
        } else if (!parentId.equals(other.parentId)) return false;
        if (updateTime == null) {
            if (other.updateTime != null) return false;
        } else if (!updateTime.equals(other.updateTime)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "CustomTagCategory [id=" + id + ", customTagCategoryId=" + customTagCategoryId
                + ", customTagCategoryName=" + customTagCategoryName + ", customTagCategoryType="
                + customTagCategoryType + ", level=" + level + ", isDeleted=" + isDeleted + ", parentId=" + parentId
                + ", createTime=" + createTime + ", updateTime=" + updateTime + ", customTagCategorySource="
                + customTagCategorySource + ", childrenCustomTagCategoryList=" + childrenCustomTagCategoryList
                + ", childrenCustomTagList=" + childrenCustomTagList + "]";
    }
    
    
}

