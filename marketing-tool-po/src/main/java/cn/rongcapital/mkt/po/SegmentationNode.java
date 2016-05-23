package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class SegmentationNode extends BaseQuery{
    private Long id;

    private Long segmentation_id;

    private Long campaign_tmpl_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSegmentation_id() {
        return segmentation_id;
    }

    public void setSegmentation_id(Long segmentation_id) {
        this.segmentation_id = segmentation_id;
    }

    public Long getCampaign_tmpl_id() {
        return campaign_tmpl_id;
    }

    public void setCampaign_tmpl_id(Long campaign_tmpl_id) {
        this.campaign_tmpl_id = campaign_tmpl_id;
    }
}
