package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CustomTagValueCount extends BaseQuery {
    private String customTagId;

    private String customTagName;

    private Long coverNumber;

    private Long coverFrequency;

    private String tagPath;

    public String getCustomTagId() {
        return customTagId;
    }

    public void setCustomTagId(String customTagId) {
        this.customTagId = customTagId == null ? null : customTagId.trim();
    }

    public String getCustomTagName() {
        return customTagName;
    }

    public void setCustomTagName(String customTagName) {
        this.customTagName = customTagName == null ? null : customTagName.trim();
    }

    public Long getCoverNumber() {
        return coverNumber;
    }

    public void setCoverNumber(Long coverNumber) {
        this.coverNumber = coverNumber;
    }

    public Long getCoverFrequency() {
        return coverFrequency;
    }

    public void setCoverFrequency(Long coverFrequency) {
        this.coverFrequency = coverFrequency;
    }

    public String getTagPath() {
        return tagPath;
    }

    public void setTagPath(String tagPath) {
        this.tagPath = tagPath == null ? null : tagPath.trim();
    }
}