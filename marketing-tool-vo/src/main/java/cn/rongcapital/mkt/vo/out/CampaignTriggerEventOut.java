package cn.rongcapital.mkt.vo.out;


public class CampaignTriggerEventOut {

    /**
     * 节点名称
     */
    private String name;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        return "CampaignTriggerEventOut [name=" + name + ", eventId=" + eventId + ", eventCode=" + eventCode
                + ", eventName=" + eventName + "]";
    }

}
