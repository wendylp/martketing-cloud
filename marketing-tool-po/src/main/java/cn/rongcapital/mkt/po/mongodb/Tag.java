package cn.rongcapital.mkt.po.mongodb;

import org.springframework.data.mongodb.core.mapping.Field;

public class Tag {

	@Field(value = "tag_id")
	private String tagId;
	
	@Field(value = "tag_name")
	private String tagName;

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
	
	
}
