package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/*************************************************
 * @功能及特点的描述简述: 推荐标签列表
 * 
 * @see （与该类关联的类): TagRecommend
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.3 @date(创建、开发日期): 2016-09-21 最后修改日期: 2016-09-21
 * @复审人: 丛树林
 *************************************************/
@Document(collection = "tag_recommend")
public class TagRecommend implements Serializable {

	private static final long serialVersionUID = 5009763496605546365L;

	@Id
	private String id;
	@Field(value = "tag_id")
	private String tagId;
	@Field(value = "tag_name")
	private String tagName;
	@Field(value = "tag_list")
	private List<String> tagList;
	private int status;
	private Boolean flag;
	@Field(value = "tag_desc")
	private String tagDesc;
	@Field(value = "create_time")
	private Date createTime;
	@Field(value = "update_time")
	private Date updateTime;
	@Field(value = "tag_name_eng")
	private String tagNameEng;

    @Field(value = "source")
    private String source;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<String> getTagList() {
		return tagList;
	}

	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
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

    public String getTagNameEng() {
        return tagNameEng;
    }

    public void setTagNameEng(String tagNameEng) {
        this.tagNameEng = tagNameEng;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}