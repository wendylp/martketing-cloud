package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class SegmentationNode extends BaseQuery {
    private Long id;

    private Long segmentationId;

    private Long campaignTmplId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSegmentationId() {
        return segmentationId;
    }

    public void setSegmentationId(Long segmentationId) {
        this.segmentationId = segmentationId;
    }

    public Long getCampaignTmplId() {
        return campaignTmplId;
    }

    public void setCampaignTmplId(Long campaignTmplId) {
        this.campaignTmplId = campaignTmplId;
    }
}
