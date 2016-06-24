package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;


public class SegmentFilterCondition{

    @NotEmpty
    private String tag_id;
    
    @NotEmpty
    private String tag_name;
	
    @NotEmpty
	private String exclude;
   
    
	
    @JsonProperty("tag_id")
	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}
	
	@JsonProperty("tag_name")
	public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
	
    @JsonProperty("exclude")
	public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }
	
	
	
}
