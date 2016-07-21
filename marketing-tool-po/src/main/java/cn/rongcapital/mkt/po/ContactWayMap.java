package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class ContactWayMap extends BaseQuery {
    private Integer id;

    private Integer contactWayId;

    private String contactWayName;

    private Boolean status;

    private Date timeCondition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContactWayId() {
        return contactWayId;
    }

    public void setContactWayId(Integer contactWayId) {
        this.contactWayId = contactWayId;
    }

    public String getContactWayName() {
        return contactWayName;
    }

    public void setContactWayName(String contactWayName) {
        this.contactWayName = contactWayName == null ? null : contactWayName.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getTimeCondition() {
        return timeCondition;
    }

    public void setTimeCondition(Date timeCondition) {
        this.timeCondition = timeCondition;
    }
}
