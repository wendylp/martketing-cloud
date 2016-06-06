/*************************************************
 * @功能简述: VO:SegmentBodyFilterGroupIn
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.vo.in;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class SegmentBodyFilterGroupIn {

	private Integer groupIndex;

	private List<SegmentBodyTagsIn> tagList;

	@JsonProperty("group_index")
	public Integer getGroupIndex() {
		return groupIndex;
	}

	@JsonProperty("group_index")
	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}

	@JsonProperty("tag_list")
	public List<SegmentBodyTagsIn> getTagList() {
		return tagList;
	}

	@JsonProperty("tag_list")
	public void setTagList(List<SegmentBodyTagsIn> tagList) {
		this.tagList = tagList;
	}

}
