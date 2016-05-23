package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class TagDistribution extends BaseQuery{
    private Long id;

    private Long tag_id;

    private String coverage_percent;

    private String inuse_tag_percent;

    private String audience_count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTag_id() {
        return tag_id;
    }

    public void setTag_id(Long tag_id) {
        this.tag_id = tag_id;
    }

    public String getCoverage_percent() {
        return coverage_percent;
    }

    public void setCoverage_percent(String coverage_percent) {
        this.coverage_percent = coverage_percent == null ? null : coverage_percent.trim();
    }

    public String getInuse_tag_percent() {
        return inuse_tag_percent;
    }

    public void setInuse_tag_percent(String inuse_tag_percent) {
        this.inuse_tag_percent = inuse_tag_percent == null ? null : inuse_tag_percent.trim();
    }

    public String getAudience_count() {
        return audience_count;
    }

    public void setAudience_count(String audience_count) {
        this.audience_count = audience_count == null ? null : audience_count.trim();
    }
}
