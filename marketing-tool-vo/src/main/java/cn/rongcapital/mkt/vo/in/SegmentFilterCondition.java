package cn.rongcapital.mkt.vo.in;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class SegmentFilterCondition {

    @NotEmpty
    private String tag_id;
	
    @NotEmpty
	private String exclude;
   
    
	
   
	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}
	
	
	public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }
	
	
	
}
