package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class TriggerNode extends BaseQuery{
    private Long id;

    private Byte type;

    private Date start_time;

    private Date end_time;

    private Long event_id;

    private Long campaign_tmpl_id;

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

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public Long getCampaign_tmpl_id() {
        return campaign_tmpl_id;
    }

    public void setCampaign_tmpl_id(Long campaign_tmpl_id) {
        this.campaign_tmpl_id = campaign_tmpl_id;
    }
}
