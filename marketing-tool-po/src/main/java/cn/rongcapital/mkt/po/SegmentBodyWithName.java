/*************************************************
 * @功能简述: PO:SegmentBodyWithName
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
 *************************************************/

package cn.rongcapital.mkt.po;


public class SegmentBodyWithName {

	private Integer groupIndex;

	private Integer tagId;

	private String tagName;

	private Integer tagGroupId;

	private String tagGroupName;

	private Integer exclude;

	public Integer getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}

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

	public Integer getTagGroupId() {
		return tagGroupId;
	}

	public void setTagGroupId(Integer tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

	public String getTagGroupName() {
		return tagGroupName;
	}

	public void setTagGroupName(String tagGroupName) {
		this.tagGroupName = tagGroupName;
	}

	public Integer getExclude() {
		return exclude;
	}

	public void setExclude(Integer exclude) {
		this.exclude = exclude;
	}

}
