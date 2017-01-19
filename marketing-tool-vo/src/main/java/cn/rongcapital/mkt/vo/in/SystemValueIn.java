package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by byf on 11/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemValueIn {

    @NotEmpty
    private String tagValueId;

    @NotEmpty
    private String tagValue;

    @NotNull
    private Integer tagStatus;
    
    @JsonProperty("tag_value_id")
    public String getTagValueId() {
        return tagValueId;
    }

    public void setTagValueId(String tagValueId) {
        this.tagValueId = tagValueId;
    }

    @JsonProperty("tag_value")
    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    @JsonProperty("tag_status")
	public Integer getTagStatus() {
		return tagStatus;
	}

	public void setTagStatus(Integer tagStatus) {
		this.tagStatus = tagStatus;
	}
    
    
}
