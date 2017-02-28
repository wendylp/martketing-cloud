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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customTagCategoryId == null) ? 0 : customTagCategoryId.hashCode());
        result = prime * result + ((customTagCategoryName == null) ? 0 : customTagCategoryName.hashCode());
        result = prime * result + ((customTagId == null) ? 0 : customTagId.hashCode());
        result = prime * result + ((customTagName == null) ? 0 : customTagName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CustomtagQrcodeFuzzyListOut other = (CustomtagQrcodeFuzzyListOut) obj;
        if (customTagCategoryId == null) {
            if (other.customTagCategoryId != null) return false;
        } else if (!customTagCategoryId.equals(other.customTagCategoryId)) return false;
        if (customTagCategoryName == null) {
            if (other.customTagCategoryName != null) return false;
        } else if (!customTagCategoryName.equals(other.customTagCategoryName)) return false;
        if (customTagId == null) {
            if (other.customTagId != null) return false;
        } else if (!customTagId.equals(other.customTagId)) return false;
        if (customTagName == null) {
            if (other.customTagName != null) return false;
        } else if (!customTagName.equals(other.customTagName)) return false;
        return true;
    }

}
