package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class SystemTagSegmentOut {
    private String tagId; // 标签ID

    private String tagName; // 标签名称

    private String tagValue;// 标签值

    private String tagPath; // 标签路径

    private Boolean isTag = false; // 是否是标签，0-标签，1-标签值

    private Integer searchMod; // 搜索标识

    private String tagValueSeq; // 标签值序号

    public SystemTagSegmentOut() {}

    public SystemTagSegmentOut(String tagId, String tagName, String tagValue, String tagPath, String isTag,
            Integer searchMod, String tagValueSeq) {
        super();
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.tagPath = tagPath;
        this.isTag = "1".equals(isTag);
        this.searchMod = searchMod;
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

    @JsonProperty("is_tag")
    public Boolean getIsTag() {
        return isTag;
    }

    public void setIsTag(String isTag) {
        this.isTag = "1".equals(isTag);
    }

    @JsonProperty("search_mod")
    public Integer getSearchMod() {
        return searchMod;
    }

    public void setSearchMod(Integer searchMod) {
        this.searchMod = searchMod;
    }

    @JsonProperty("tag_value_seq")
    public String getTagValueSeq() {
        return tagValueSeq;
    }

    public void setTagValueSeq(String tagValueSeq) {
        this.tagValueSeq = tagValueSeq;
    }

}
