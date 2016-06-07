/*************************************************
 * @功能简述: VO:SegmentBodyTagsOut
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class SegmentBodyTagsOut {

	private Integer tagId;

	private String tagName;

	private Integer tagGroupId;

	private String tagGroupName;

	private Integer exclude;

	@JsonProperty("tag_id")
	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	@JsonProperty("tag_name")
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@JsonProperty("tag_group_id")
	public Integer getTagGroupId() {
		return tagGroupId;
	}

	public void setTagGroupId(Integer tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

	@JsonProperty("tag_group_name")
	public String getTagGroupName() {
		return tagGroupName;
	}

	public void setTagGroupName(String tagGroupName) {
		this.tagGroupName = tagGroupName;
	}

	@JsonProperty("exclude")
	public Integer getExclude() {
		return exclude;
	}

	public void setExclude(Integer exclude) {
		this.exclude = exclude;
	}

}
