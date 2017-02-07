package cn.rongcapital.mkt.event.vo.in;

import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonProperty;
import cn.rongcapital.mkt.vo.BaseInput;

public class EventBehavierIn extends BaseInput{

	 @NotNull
	 @JsonProperty("object_code")
	 private String objectCode;
	 
	 @NotNull
	 @JsonProperty("qrcode_id")
	 private String qrcodeId;
	 
	 @NotNull
	 @JsonProperty("begin_time")
	 private Long beginTime;
	 
	 @NotNull
	 @JsonProperty("end_time")
	 private Long endTime;

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public String getQrcodeId() {
		return qrcodeId;
	}

	public void setQrcodeId(String qrcodeId) {
		this.qrcodeId = qrcodeId;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
}
