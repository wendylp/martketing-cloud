package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by lijinkai on 2017-01-16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SegmentAnalysisCustomVO {
	
	private String tagId;
	private String tagName;
	private Integer coverCount;
	
    @JsonProperty("tag_id")
    public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

    @JsonProperty("tag_name")
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
    @JsonProperty("cover_count")
	public Integer getCoverCount() {
		return coverCount;
	}

	public void setCoverCount(Integer coverCount) {
		this.coverCount = coverCount;
	}
}
