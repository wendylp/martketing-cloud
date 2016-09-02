package cn.rongcapital.mkt.vo.weixin;

import org.codehaus.jackson.annotate.JsonProperty;

public class WXTag {

	private String tagName;
	
	private Integer tagId;
	
	private Integer count;
	
	private String alias;
	
	private String headImg;

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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	@JsonProperty("head_img")
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
}
