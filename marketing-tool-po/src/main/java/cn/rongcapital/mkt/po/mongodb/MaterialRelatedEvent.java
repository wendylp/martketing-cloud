package cn.rongcapital.mkt.po.mongodb;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 物料相关联的事件
 * @author 王伟强
 *
 */
public class MaterialRelatedEvent {
	
	private String id;
	
	@Field(value = "event_code")
	private String eventCode;			//事件code
	
	@Field(value = "event_name")
	private String eventName;			//事件名称
	
	@Field(value = "source_name")
	private String sourceName;			//发生源名称
	
	@Field(value = "mid")
	private Integer mid;	//事件关联主体Id集合
	
	@Field(value = "time")
	private Long time;					//事件发生时间
	
	

	public MaterialRelatedEvent() {
		super();
	}
	
	public MaterialRelatedEvent(String id, String eventCode, String eventName, String sourceName, Integer mid,
			Long time) {
		this.id = id;
		this.eventCode = eventCode;
		this.eventName = eventName;
		this.sourceName = sourceName;
		this.mid = mid;
		this.time = time;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	
}
