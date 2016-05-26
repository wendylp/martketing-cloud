package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class SegmentDistribution extends BaseQuery {
    private Long id;

    private Long sagmentationId;

    private Long campaignId;

    private Integer audienceCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSagmentationId() {
        return sagmentationId;
    }

    public void setSagmentationId(Long sagmentationId) {
        this.sagmentationId = sagmentationId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Integer getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(Integer audienceCount) {
        this.audienceCount = audienceCount;
    }
}
