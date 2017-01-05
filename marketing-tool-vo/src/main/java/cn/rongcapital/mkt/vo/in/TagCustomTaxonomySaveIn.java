package cn.rongcapital.mkt.vo.in;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TagCustomTaxonomySaveIn extends BaseInput {

    @NotEmpty
    private String tagTreeId;
    private List<TagCustomTaxonomySaveChildrenIn> children;
    private List<TagCustomTaxonomySaveChildrenTagIn> childrenTag;

    @JsonProperty("tag_tree_id")
    public String getTagTreeId() {
        return tagTreeId;
    }

    public void setTagTreeId(String tagTreeId) {
        this.tagTreeId = tagTreeId;
    }

    @JsonProperty("children")
    public List<TagCustomTaxonomySaveChildrenIn> getChildren() {
        return children;
    }

    public void setChildren(List<TagCustomTaxonomySaveChildrenIn> children) {
        this.children = children;
    }

    @JsonProperty("children_tag")
    public List<TagCustomTaxonomySaveChildrenTagIn> getChildrenTag() {
        return childrenTag;
    }

    public void setChildrenTag(List<TagCustomTaxonomySaveChildrenTagIn> childrenTag) {
        this.childrenTag = childrenTag;
    }



}
