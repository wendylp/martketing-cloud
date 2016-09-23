package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/*************************************************
 * @功能及特点的描述简述: 标签组列表
 * 
 * @see （与该类关联的类): TagTree
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.3 @date(创建、开发日期): 2016-09-21 最后修改日期: 2016-09-21
 * @复审人: 丛树林
 *************************************************/
@Document(collection = "tag_tree")
public class TagTree implements Serializable {

	private static final long serialVersionUID = 5009763496605546365L;

	@Id
	private String id;
	@Field(value = "tag_id")
	private int tagId;
	@Field(value = "tag_name")
	private String tagName;
	private int level;
	private String path;
	private int status;
	private String parent;
	@Field(value = "create_time")
	private Date createTime;
	@Field(value = "update_time")
	private Date updateTime;

	private List<Integer> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParent() {
		return parent;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setChildren(List<Integer> children) {
		this.children = children;
	}

	public List<Integer> getChildren() {
		return children;
	}

}