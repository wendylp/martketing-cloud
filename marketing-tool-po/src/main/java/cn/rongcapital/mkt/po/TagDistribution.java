package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class TagDistribution extends BaseQuery {
    private Long id;

    private Long tagId;

    private String coveragePercent;

    private String inuseTagPercent;

    private String audienceCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getCoveragePercent() {
        return coveragePercent;
    }

    public void setCoveragePercent(String coveragePercent) {
        this.coveragePercent = coveragePercent == null ? null : coveragePercent.trim();
    }

    public String getInuseTagPercent() {
        return inuseTagPercent;
    }

    public void setInuseTagPercent(String inuseTagPercent) {
        this.inuseTagPercent = inuseTagPercent == null ? null : inuseTagPercent.trim();
    }

    public String getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(String audienceCount) {
        this.audienceCount = audienceCount == null ? null : audienceCount.trim();
    }
}
