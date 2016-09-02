package cn.rongcapital.mkt.vo.weixin;

import org.codehaus.jackson.annotate.JsonProperty;

public class WXTag {

	private String tagName;
	
	private Integer tagId;
	
	private Integer count;

	@JsonProperty("name")
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	@JsonProperty("id")
	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	@JsonProperty("count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	
	
}
