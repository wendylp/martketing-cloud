package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class NodeTrigger extends BaseQuery {
    private Long id;

    private Byte type;

    private Date startTime;

    private Date endTime;

    private Long eventId;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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
