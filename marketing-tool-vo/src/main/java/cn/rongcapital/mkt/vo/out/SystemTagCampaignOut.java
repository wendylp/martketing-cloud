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

}
