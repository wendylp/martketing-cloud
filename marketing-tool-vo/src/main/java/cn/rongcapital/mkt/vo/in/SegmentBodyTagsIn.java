/*************************************************
 * @功能简述: VO:SegmentBodyTagsIn
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;

public class SegmentBodyTagsIn {

	private Integer tagId;

	private Integer tagGroupId;

	private Integer exclude;

	@JsonProperty("tag_id")
	public Integer getTagId() {
		return tagId;
	}

	@JsonProperty("tag_id")
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	@JsonProperty("tag_group_id")
	public Integer getTagGroupId() {
		return tagGroupId;
	}

	@JsonProperty("tag_group_id")
	public void setTagGroupId(Integer tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

	@JsonProperty("exclude")
	public Integer getExclude() {
		return exclude;
	}

	@JsonProperty("exclude")
	public void setExclude(Integer exclude) {
		this.exclude = exclude;
	}

}