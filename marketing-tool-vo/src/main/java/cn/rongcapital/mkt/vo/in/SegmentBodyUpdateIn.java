/*************************************************
 * @功能简述: VO:SegmentBodyUpdateIn
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
import org.hibernate.validator.constraints.NotEmpty;

public class SegmentBodyUpdateIn {

	@NotEmpty
	private String segmentHeadId;

	@NotEmpty
	private List<SegmentBodyFilterGroupIn> filterGroups;

	@NotEmpty
	private String userToken = null;

	@JsonProperty("segment_head_id")
	public String getSegmentHeadId() {
		return segmentHeadId;
	}

	@JsonProperty("segment_head_id")
	public void setSegmentHeadId(String segmentHeadId) {
		this.segmentHeadId = segmentHeadId;
	}

	@JsonProperty("filter_groups")
	public List<SegmentBodyFilterGroupIn> getFilterGroups() {
		return filterGroups;
	}

	@JsonProperty("filter_groups")
	public void setFilterGroups(List<SegmentBodyFilterGroupIn> filterGroups) {
		this.filterGroups = filterGroups;
	}
	
	@JsonProperty("user_token")
	public String getUserToken() {
		return userToken;
	}

	@JsonProperty("user_token")
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

}