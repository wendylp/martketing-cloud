package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CustomTagCampaignOut {

    private String customTagId;
    private String customTagName;
    private String tagPath;
    private String customTagCategoryId;
    private String customTagCategoryName;

    public CustomTagCampaignOut() {}

    public CustomTagCampaignOut(String customTagId, String customTagName, String tagPath, String customTagCategoryId,
            String customTagCategoryName) {
        super();
        this.customTagId = customTagId;
        this.customTagName = customTagName;
        this.tagPath = tagPath;
        this.customTagCategoryId = customTagCategoryId;
        this.customTagCategoryName = customTagCategoryName;
    }

    @JsonProperty("custom_tag_id")
    public String getCustomTagId() {
        return customTagId;
    }

    public void setCustomTagId(String customTagId) {
        this.customTagId = customTagId;
    }

    @JsonProperty("custom_tag_name")
    public String getCustomTagName() {
        return customTagName;
    }

    public void setCustomTagName(String customTagName) {
        this.customTagName = customTagName;
    }

    @JsonProperty("tag_path")
    public String getTagPath() {
        return tagPath;
    }

    public void setTagPath(String tagPath) {
        this.tagPath = tagPath;
    }

    @JsonProperty("custom_tag_category_id")
    public String getCustomTagCategoryId() {
        return customTagCategoryId;
    }

    public void setCustomTagCategoryId(String customTagCategoryId) {
        this.customTagCategoryId = customTagCategoryId;
    }

    @JsonProperty("custom_tag_category_name")
    public String getCustomTagCategoryName() {
        return customTagCategoryName;
    }

    public void setCustomTagCategoryName(String customTagCategoryName) {
        this.customTagCategoryName = customTagCategoryName;
    }



}
