package cn.rongcapital.mkt.po;


public class CampaignTriggerEvent {

    /**
     * 事件主键
     */
    private Long eventId;

    /**
     * 事件编码
     */
    private String eventCode;

    /**
     * 事件名称
     */
    private String eventName;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return "CampaignTriggerEvent [eventId=" + eventId + ", eventCode=" + eventCode + ", eventName=" + eventName
                + "]";
    }


}
