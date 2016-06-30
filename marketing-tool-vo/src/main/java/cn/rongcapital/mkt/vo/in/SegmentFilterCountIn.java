package cn.rongcapital.mkt.vo.in;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class SegmentFilterCountIn extends BaseInput{

    
    private String userToken;

    
    private List<SegmentFilterCondition> conditions;
          
    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
    
    
    
    @JsonProperty("conditions")
    public List<SegmentFilterCondition> getConditions() {
        return conditions;
    }

    
    public void setConditions(List<SegmentFilterCondition> conditions) {
        this.conditions = conditions;
    }
		
	
}
