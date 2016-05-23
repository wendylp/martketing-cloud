package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CampaignInatanceNodeHistory extends BaseQuery{
    private Long id;

    private Long campaign_instance_id;

    private Long node_id;

    private Integer audience_count;

    private Long segment_id;

    private Long snapshaot_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaign_instance_id() {
        return campaign_instance_id;
    }

    public void setCampaign_instance_id(Long campaign_instance_id) {
        this.campaign_instance_id = campaign_instance_id;
    }

    public Long getNode_id() {
        return node_id;
    }

    public void setNode_id(Long node_id) {
        this.node_id = node_id;
    }

    public Integer getAudience_count() {
        return audience_count;
    }

    public void setAudience_count(Integer audience_count) {
        this.audience_count = audience_count;
    }

    public Long getSegment_id() {
        return segment_id;
    }

    public void setSegment_id(Long segment_id) {
        this.segment_id = segment_id;
    }

    public Long getSnapshaot_id() {
        return snapshaot_id;
    }

    public void setSnapshaot_id(Long snapshaot_id) {
        this.snapshaot_id = snapshaot_id;
    }
}
