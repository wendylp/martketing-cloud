package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class SegmentDistribution extends BaseQuery{
    private Long id;

    private Long sagmentation_id;

    private Long campaign_id;

    private Integer audience_count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSagmentation_id() {
        return sagmentation_id;
    }

    public void setSagmentation_id(Long sagmentation_id) {
        this.sagmentation_id = sagmentation_id;
    }

    public Long getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(Long campaign_id) {
        this.campaign_id = campaign_id;
    }

    public Integer getAudience_count() {
        return audience_count;
    }

    public void setAudience_count(Integer audience_count) {
        this.audience_count = audience_count;
    }
}
