package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class SegmentSummaryListDataOut {
	
	private String segmentName;
	
	private Byte publishStatus;

	private Integer segmentHeadId;
	
	private Integer coverCount;
	
	
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

    @JsonProperty("cover_count")
	public Integer getCoverCount() {
		return coverCount;
	}

	public void setCoverCount(Integer coverCount) {
		this.coverCount = coverCount;
	}
}
