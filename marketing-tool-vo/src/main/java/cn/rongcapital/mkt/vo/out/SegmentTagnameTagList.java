/*************************************************
 * @功能简述: 查询系统推荐标签列表的输出实体 
 * @see SegmentTagnameTagListService：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-06
 *************************************************/
package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

public class SegmentTagnameTagList extends BaseOutput{
	
	private List<SegmentTagnameTag> tagList = new ArrayList<SegmentTagnameTag>();

	@JsonProperty("data")
	public List<SegmentTagnameTag> getTagList() {
		return tagList;
	}

	public void setTagList(List<SegmentTagnameTag> tagList) {
		this.tagList = tagList;
	}
}
