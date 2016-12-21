package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TagCustomTaxonomyRootSaveIn extends BaseInput {


    private String tagTreeId;
    @NotEmpty
    private String tagTreeName;

    @JsonProperty("tag_tree_id")
    public String getTagTreeId() {
        return tagTreeId;
    }

    public void setTagTreeId(String tagTreeId) {
        this.tagTreeId = tagTreeId;
    }

    @JsonProperty("tag_tree_name")
    public String getTagTreeName() {
        return tagTreeName;
    }

    public void setTagTreeName(String tagTreeName) {
        this.tagTreeName = tagTreeName;
    }


}
