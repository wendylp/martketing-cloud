package cn.rongcapital.mkt.vo.out;

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

    public String getCustomTagCategoryId() {
        return customTagCategoryId == null ? "" : customTagCategoryId;
    }

    public void setCustomTagCategoryId(String customTagCategoryId) {
        this.customTagCategoryId = customTagCategoryId;
    }

    public String getCustomTagCategoryName() {
        return customTagCategoryName == null ? "" : customTagCategoryName;
    }

    public void setCustomTagCategoryName(String customTagCategoryName) {
        this.customTagCategoryName = customTagCategoryName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public long getCustomTagCount() {
        return customTagCount;
    }

    public void setCustomTagCount(long customTagCount) {
        this.customTagCount = customTagCount;
    }



}
