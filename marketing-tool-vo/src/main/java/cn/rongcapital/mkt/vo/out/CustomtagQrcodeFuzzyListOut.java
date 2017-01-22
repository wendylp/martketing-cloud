package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CustomtagQrcodeFuzzyListOut {
    @JsonProperty("custom_tag_id")
    private String customTagId;// 自定义标签id
    @JsonProperty("custom_tag_name")
    private String customTagName;// 自定义标签name
    @JsonProperty("custom_tag_category_id")
    private String customTagCategoryId;// 自定义标签分类id
    @JsonProperty("custom_tag_category_name")
    private String customTagCategoryName;// 自定义标签分类name

    public CustomtagQrcodeFuzzyListOut() {}

    public CustomtagQrcodeFuzzyListOut(String customTagId, String customTagName, String customTagCategoryId,
            String customTagCategoryName) {
        super();
        this.customTagId = customTagId;
        this.customTagName = customTagName;
        this.customTagCategoryId = customTagCategoryId;
        this.customTagCategoryName = customTagCategoryName;
    }

    public String getCustomTagId() {
        return customTagId;
    }

    public void setCustomTagId(String customTagId) {
        this.customTagId = customTagId;
    }

    public String getCustomTagName() {
        return customTagName;
    }

    public void setCustomTagName(String customTagName) {
        this.customTagName = customTagName;
    }

    public String getCustomTagCategoryId() {
        return customTagCategoryId;
    }

    public void setCustomTagCategoryId(String customTagCategoryId) {
        this.customTagCategoryId = customTagCategoryId;
    }

    public String getCustomTagCategoryName() {
        return customTagCategoryName;
    }

    public void setCustomTagCategoryName(String customTagCategoryName) {
        this.customTagCategoryName = customTagCategoryName;
    }

    @Override
    public String toString() {
        return "CustomtagQrcodeFuzzyListOut [customTagId=" + customTagId + ", customTagName=" + customTagName
                + ", customTagCategoryId=" + customTagCategoryId + ", customTagCategoryName=" + customTagCategoryName
                + "]";
    }



}
