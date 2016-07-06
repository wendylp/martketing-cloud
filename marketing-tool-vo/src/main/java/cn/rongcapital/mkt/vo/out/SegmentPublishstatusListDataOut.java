package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class SegmentPublishstatusListDataOut {
	
	private String segmentName;
	
	private Integer segmentHeadId;
	
	private Byte publishStatus;

	@JsonProperty("segment_name")
	public String getSegmentName() {
		return segmentName;
	}

	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}

	@JsonProperty("segment_head_id")
	public Integer getSegmentHeadId() {
		return segmentHeadId;
	}

	public void setSegmentHeadId(Integer segmentHeadId) {
		this.segmentHeadId = segmentHeadId;
	}

	@JsonProperty("publish_status")
	public Byte getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Byte publishStatus) {
		this.publishStatus = publishStatus;
	}
	
	

}
