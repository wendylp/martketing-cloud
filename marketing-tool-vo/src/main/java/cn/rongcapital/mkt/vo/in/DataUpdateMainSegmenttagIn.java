package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataUpdateMainSegmenttagIn extends BaseInput{
    
    private String method;
    
    private String userToken;
    
    private String tagName;
    
    private Integer contactId;
    
    @JsonProperty("method")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @JsonProperty("tag_name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @JsonProperty("contact_id")
    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }
    
    

}
