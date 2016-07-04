package cn.rongcapital.mkt.po.mongodb;

import org.springframework.data.mongodb.core.mapping.Field;

public class Tag {

	@Field(value = "tag_id")
	private Integer tagId;
	
	@Field(value = "tag_name")
	private String tagName;
	
	@Field(value = "tag_group_id")
	private String tagGroupId;

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagGroupId() {
		return tagGroupId;
	}

	public void setTagGroupId(String tagGroupId) {
		this.tagGroupId = tagGroupId;
	}
	
}
