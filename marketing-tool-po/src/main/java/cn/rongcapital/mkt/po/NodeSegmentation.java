package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class NodeSegmentation extends BaseQuery {
    private Long id;

    private Long segmentationId;

    private Long campaignTplId;

    private Byte nodeStatus;

    private String posX;

    private String posY;

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

    public Long getCampaignTplId() {
        return campaignTplId;
    }

    public void setCampaignTplId(Long campaignTplId) {
        this.campaignTplId = campaignTplId;
    }

    public Byte getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(Byte nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public String getPosX() {
        return posX;
    }

    public void setPosX(String posX) {
        this.posX = posX == null ? null : posX.trim();
    }

    public String getPosY() {
        return posY;
    }

    public void setPosY(String posY) {
        this.posY = posY == null ? null : posY.trim();
    }
}
