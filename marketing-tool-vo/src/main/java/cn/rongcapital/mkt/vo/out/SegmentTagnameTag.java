/*************************************************
 * @功能简述: 查询系统推荐标签的输出实体 
 * @see SegmentTagnameTagList：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-06
 *************************************************/
package cn.rongcapital.mkt.vo.out;

public class SegmentTagnameTag {
	private Integer tagGroupId;
	
	private String tagGroupName;

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
}
