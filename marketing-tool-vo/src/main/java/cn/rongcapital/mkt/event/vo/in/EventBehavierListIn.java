package cn.rongcapital.mkt.event.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

import com.alibaba.fastjson.JSONArray;

public class EventBehavierListIn extends BaseInput {

	 @NotNull
	 @JsonProperty("event_id")
	 private Long eventId;
	 
	 @JsonProperty("attributes")
	 private JSONArray attributes;
	 
	 @NotEmpty
	 @JsonProperty("user_id")
	 private String userId;
	 
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public JSONArray getAttributes() {
		return attributes;
	}

	public void setAttributes(JSONArray attributes) {
		this.attributes = attributes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
