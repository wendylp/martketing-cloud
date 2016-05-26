package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class TriggerNode extends BaseQuery {
    private Long id;

    private Byte type;

    private Date startTime;

    private Date endTime;

    private Long eventId;

    private Long campaignTmplId;

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

    public Long getCampaignTmplId() {
        return campaignTmplId;
    }

    public void setCampaignTmplId(Long campaignTmplId) {
        this.campaignTmplId = campaignTmplId;
    }
}
