package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by hiro on 17/2/6.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomTagNameIn {

    private String CustomTagName;

    @JsonProperty("custom_tag_name")
    public String getCustomTagName() {
        return CustomTagName;
    }

    public void setCustomTagName(String customTagName) {
        CustomTagName = customTagName;
    }
}
