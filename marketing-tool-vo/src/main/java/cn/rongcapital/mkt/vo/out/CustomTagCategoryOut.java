package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CustomTagCategoryOut {

    private String customTagCategoryId;
    private String customTagCategoryName;
    private Integer level;
    private long customTagCount;

    public CustomTagCategoryOut() {}

    public CustomTagCategoryOut(String customTagCategoryId, String customTagCategoryName, Integer level,
            long customTagCount) {
        super();
        this.customTagCategoryId = customTagCategoryId;
        this.customTagCategoryName = customTagCategoryName;
        this.level = level;
        this.customTagCount = customTagCount;
    }

    @JsonProperty("custom_tag_category_id")
    public String getCustomTagCategoryId() {
        return customTagCategoryId == null ? "" : customTagCategoryId;
    }

    public void setCustomTagCategoryId(String customTagCategoryId) {
        this.customTagCategoryId = customTagCategoryId;
    }

    @JsonProperty("custom_tag_category_name")
    public String getCustomTagCategoryName() {
        return customTagCategoryName == null ? "" : customTagCategoryName;
    }

    public void setCustomTagCategoryName(String customTagCategoryName) {
        this.customTagCategoryName = customTagCategoryName;
    }

    @JsonProperty("level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @JsonProperty("custom_tag_count")
    public long getCustomTagCount() {
        return customTagCount;
    }

    public void setCustomTagCount(long customTagCount) {
        this.customTagCount = customTagCount;
    }



}
