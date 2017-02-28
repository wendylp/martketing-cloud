package cn.rongcapital.mkt.po.mongodb.event;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.alibaba.fastjson.JSONObject;

/*************************************************
 * @功能及特点的描述简述: 事件行为
 * 
 * @see （与该类关联的类): eventTehavior
 * @对应项目名称: MC系统
 * @author: guozhenchao
 * @version: v1.3 @date(创建、开发日期): 2017-01-05 最后修改日期: 2017-01-05
 * @复审人: guozhenchao
 *************************************************/
@Document(collection = "event_behavior")
public class EventBehaviors implements Serializable {

	private static final long serialVersionUID = 456107105313329296L;
	

	@Id
	private String id;
	@Field(value = "time")
	private Long time;
	@Field(value = "object")
	private JSONObject object;
	@Field(value = "subject")
	private JSONObject subject;
	@Field(value = "event")
	private JSONObject event;
	@Field(value = "subscribed")
	private boolean subscribed;
	private String sourceName;
	private String objectName;
	private String eventName;

	public EventBehaviors(String id,Long time,JSONObject object,JSONObject subject,JSONObject event, boolean subscribed){
		super();
		this.id = id;
		this.time = time;
		this.object = object;
		this.subject = subject;
		this.event = event;
		this.subscribed = subscribed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public JSONObject getObject() {
		return object;
	}

	public void setObject(JSONObject object) {
		this.object = object;
	}

	public JSONObject getSubject() {
		return subject;
	}

	public void setSubject(JSONObject subject) {
		this.subject = subject;
	}

	public JSONObject getEvent() {
		return event;
	}

	public void setEvent(JSONObject event) {
		this.event = event;
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
}