package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CampaignTemplate extends BaseQuery {
    private Long id;

    private Long campaignId;

    private Long nodeId;

    private Long preNodeId;

    private Long nextNodeId;

    private String nodeTableName;

    private Byte preNodeLineType;

    private Byte nextNodeLineType;

    private Byte nodeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getPreNodeId() {
        return preNodeId;
    }

    public void setPreNodeId(Long preNodeId) {
        this.preNodeId = preNodeId;
    }

    public Long getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(Long nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public String getNodeTableName() {
        return nodeTableName;
    }

    public void setNodeTableName(String nodeTableName) {
        this.nodeTableName = nodeTableName == null ? null : nodeTableName.trim();
    }

    public Byte getPreNodeLineType() {
        return preNodeLineType;
    }

    public void setPreNodeLineType(Byte preNodeLineType) {
        this.preNodeLineType = preNodeLineType;
    }

    public Byte getNextNodeLineType() {
        return nextNodeLineType;
    }

    public void setNextNodeLineType(Byte nextNodeLineType) {
        this.nextNodeLineType = nextNodeLineType;
    }

    public Byte getNodeType() {
        return nodeType;
    }

    public void setNodeType(Byte nodeType) {
        this.nodeType = nodeType;
    }
}
