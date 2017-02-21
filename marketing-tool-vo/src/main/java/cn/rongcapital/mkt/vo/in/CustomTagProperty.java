package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by byf on 1/17/17.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomTagProperty {

    @NotEmpty
    private String customTagName;

    @JsonProperty("custom_tag_name")
    public String getCustomTagName() {
        return customTagName;
    }

    public void setCustomTagName(String customTagName) {
        this.customTagName = customTagName;
    }
}
