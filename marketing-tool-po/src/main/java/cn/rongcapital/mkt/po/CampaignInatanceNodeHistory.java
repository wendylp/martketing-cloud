package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CampaignInatanceNodeHistory extends BaseQuery {
    private Long id;

    private Long campaignInstanceId;

    private Long nodeId;

    private Integer audienceCount;

    private Long segmentId;

    private Long snapshaotId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignInstanceId() {
        return campaignInstanceId;
    }

    public void setCampaignInstanceId(Long campaignInstanceId) {
        this.campaignInstanceId = campaignInstanceId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(Integer audienceCount) {
        this.audienceCount = audienceCount;
    }

    public Long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }

    public Long getSnapshaotId() {
        return snapshaotId;
    }

    public void setSnapshaotId(Long snapshaotId) {
        this.snapshaotId = snapshaotId;
    }
}
