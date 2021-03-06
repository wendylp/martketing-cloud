package cn.rongcapital.mkt.event.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventBehaviorsOut{


	private String id;
	private Long time;
	private JSONObject object;
	private JSONObject subject;
	private JSONObject event;
	private boolean subscribed;
	private String sourceName;
	private String eventName;
	private String objectName;
	
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

	
//	public String getObject() {
//		return object;
//	}
//
//	public void setObject(String object) {
//		this.object = object;
//	}
//
//	public String getSubject() {
//		return subject;
//	}
//
//	public void setSubject(String subject) {
//		this.subject = subject;
//	}
//
//	public String getEvent() {
//		return event;
//	}
//
//	public void setEvent(String event) {
//		this.event = event;
//	}

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

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
}