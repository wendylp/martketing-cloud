package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignTriggerEventOut {

    /**
     * 节点名称
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * 事件主键
     */
    @JsonProperty("event_id")
    private Long eventId;

    /**
     * 事件编码
     */
    @JsonProperty("event_code")
    private String eventCode;

    /**
     * 事件名称
     */
    @JsonProperty("event_name")
    private String eventName;
    
    /**
     * 关怀时间
     */
    @JsonProperty("caring_time")
    private Integer caringTime;

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
    
    public Integer getCaringTime() {
		return caringTime;
	}

	public void setCaringTime(Integer caringTime) {
		this.caringTime = caringTime;
	}

	@Override
    public String toString() {
        return "CampaignTriggerEventOut [name=" + name + ", eventId=" + eventId + ", eventCode=" + eventCode
                + ", eventName=" + eventName + ", caringTime=" + caringTime + "]";
    }

}
