package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class NodeDecision extends BaseQuery {
    private Long id;

    private Byte type;

    private String ruleKey;

    private String campaignTplId;

    private Byte nodeStatus;

    private String posX;

    private String posY;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getRuleKey() {
        return ruleKey;
    }

    public void setRuleKey(String ruleKey) {
        this.ruleKey = ruleKey == null ? null : ruleKey.trim();
    }

    public String getCampaignTplId() {
        return campaignTplId;
    }

    public void setCampaignTplId(String campaignTplId) {
        this.campaignTplId = campaignTplId == null ? null : campaignTplId.trim();
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
