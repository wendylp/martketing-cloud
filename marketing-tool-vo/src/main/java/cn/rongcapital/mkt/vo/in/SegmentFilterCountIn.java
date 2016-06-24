package cn.rongcapital.mkt.vo.in;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class SegmentFilterCountIn extends BaseInput{

    @NotEmpty
    private String userToken;

    
    @NotEmpty
    private String segment_head_id;
	
    @NotEmpty
	private String group_index;
    
    private List<SegmentFilterCondition> conditions;
          
    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
    
	
    @JsonProperty("segment_head_id")
	public String getSegment_head_id() {
		return segment_head_id;
	}

	public void setSegment_head_id(String segment_head_id) {
		this.segment_head_id = segment_head_id;
	}
	
	@JsonProperty("group_index")
	public String getGroup_index() {
        return group_index;
    }

    public void setGroup_index(String group_index) {
        this.group_index = group_index;
    }
    
    @JsonProperty("conditions")
    public List<SegmentFilterCondition> getConditions() {
        return conditions;
    }

    
    public void setConditions(List<SegmentFilterCondition> conditions) {
        this.conditions = conditions;
    }
		
	
}
