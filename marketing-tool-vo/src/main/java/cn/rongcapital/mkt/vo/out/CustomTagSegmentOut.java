package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CustomTagSegmentOut {

    private String customTagId;
    private String customTagName;
    private String tagPath;
    private String customTagCategoryId;
    private String customTagCategoryName;

    public CustomTagSegmentOut() {}

    public CustomTagSegmentOut(String customTagId, String customTagName, String tagPath, String customTagCategoryId,
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

    @Override
    public String toString() {
        return "CustomTagSegmentOut [customTagId=" + customTagId + ", customTagName=" + customTagName + ", tagPath="
                + tagPath + ", customTagCategoryId=" + customTagCategoryId + ", customTagCategoryName="
                + customTagCategoryName + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customTagCategoryId == null) ? 0 : customTagCategoryId.hashCode());
        result = prime * result + ((customTagCategoryName == null) ? 0 : customTagCategoryName.hashCode());
        result = prime * result + ((customTagId == null) ? 0 : customTagId.hashCode());
        result = prime * result + ((customTagName == null) ? 0 : customTagName.hashCode());
        result = prime * result + ((tagPath == null) ? 0 : tagPath.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CustomTagSegmentOut other = (CustomTagSegmentOut) obj;
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
        if (tagPath == null) {
            if (other.tagPath != null) return false;
        } else if (!tagPath.equals(other.tagPath)) return false;
        return true;
    }

}
