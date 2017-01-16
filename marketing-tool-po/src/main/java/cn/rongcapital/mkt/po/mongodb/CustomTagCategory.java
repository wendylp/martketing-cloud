package cn.rongcapital.mkt.po.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

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
}

