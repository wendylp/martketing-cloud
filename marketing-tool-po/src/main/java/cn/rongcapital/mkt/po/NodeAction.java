package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class NodeAction extends BaseQuery {
    private Long id;

    private Long campaignTplId;

    private Byte nodeStatus;

    private String desciption;

    private Byte type;

    private Long assetId;

    private String value;

    private String posX;

    private String posY;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption == null ? null : desciption.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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
