package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class SystemTagCampaignOut {
    private String tagId; // 标签ID

    private String tagName; // 标签名称

    private String tagValue;// 标签值

    private String tagPath; // 标签路径

    private String tagValueSeq; // 标签值序号

    public SystemTagCampaignOut() {}

    public SystemTagCampaignOut(String tagId, String tagName, String tagValue, String tagPath, String tagValueSeq) {
        super();
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.tagPath = tagPath;
        this.tagValueSeq = tagValueSeq;
    }

    @JsonProperty("tag_id")
    public String getTagId() {
        return tagId == null ? "" : tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @JsonProperty("tag_name")
    public String getTagName() {
        return tagName == null ? "" : tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @JsonProperty("tag_value")
    public String getTagValue() {
        return tagValue == null ? "" : tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    @JsonProperty("tag_path")
    public String getTagPath() {
        return tagPath == null ? "" : tagPath;
    }

    public void setTagPath(String tagPath) {
        this.tagPath = tagPath;
    }

    @JsonProperty("tag_value_seq")
    public String getTagValueSeq() {
        return tagValueSeq;
    }

    public void setTagValueSeq(String tagValueSeq) {
        this.tagValueSeq = tagValueSeq;
    }

    @Override
    public String toString() {
        return "SystemTagCampaignOut [tagId=" + tagId + ", tagName=" + tagName + ", tagValue=" + tagValue + ", tagPath="
                + tagPath + ", tagValueSeq=" + tagValueSeq + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
        result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
        result = prime * result + ((tagPath == null) ? 0 : tagPath.hashCode());
        result = prime * result + ((tagValue == null) ? 0 : tagValue.hashCode());
        result = prime * result + ((tagValueSeq == null) ? 0 : tagValueSeq.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SystemTagCampaignOut other = (SystemTagCampaignOut) obj;
        if (tagId == null) {
            if (other.tagId != null) return false;
        } else if (!tagId.equals(other.tagId)) return false;
        if (tagName == null) {
            if (other.tagName != null) return false;
        } else if (!tagName.equals(other.tagName)) return false;
        if (tagPath == null) {
            if (other.tagPath != null) return false;
        } else if (!tagPath.equals(other.tagPath)) return false;
        if (tagValue == null) {
            if (other.tagValue != null) return false;
        } else if (!tagValue.equals(other.tagValue)) return false;
        if (tagValueSeq == null) {
            if (other.tagValueSeq != null) return false;
        } else if (!tagValueSeq.equals(other.tagValueSeq)) return false;
        return true;
    }

}
