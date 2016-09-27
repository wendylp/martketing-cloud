package cn.rongcapital.mkt.po.mongodb;

import org.springframework.data.mongodb.core.mapping.Field;

public class Tag {

	@Field(value = "tag_id")
	private String tagId;
	
    @Field(value = "tag_name")
    private String tagName;
    
    @Field(value = "tag_name_eng")
    private String tagNameEng;
	
    @Field(value = "tag_value")
    private String tagValue;
	
	@Field(value = "tag_group_id")
	private Integer tagGroupId;

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

	public Integer getTagGroupId() {
		return tagGroupId;
	}

	public void setTagGroupId(Integer tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public String getTagNameEng() {
        return tagNameEng;
    }

    public void setTagNameEng(String tagNameEng) {
        this.tagNameEng = tagNameEng;
    }
    
}
