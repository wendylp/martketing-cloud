package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class AudienceListPartyMap extends BaseQuery {
    private Integer audienceListId;

    private Integer partyId;

    private Byte status;

    private String oper;

    private Date updateTime;

    public Integer getAudienceListId() {
        return audienceListId;
    }

    public void setAudienceListId(Integer audienceListId) {
        this.audienceListId = audienceListId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper == null ? null : oper.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
