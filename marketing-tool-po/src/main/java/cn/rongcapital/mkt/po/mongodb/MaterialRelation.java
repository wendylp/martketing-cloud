package cn.rongcapital.mkt.po.mongodb;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "material_relation")
public class MaterialRelation {

	@Id
	private String id;

	@Field(value = "material_code")
	private String materialCode; // 物料CODE

	@Field(value = "material_type")
	private String materialType; // 物料类型

	@Field(value = "last_time")
	private Long lastTime; // 最后一次发生时间

	@Field(value = "status")
	private Integer status; // 状态

	@Field(value = "event_list")
	private List<MaterialRelatedEvent> eventList;
	
	public MaterialRelation() {
		super();
	}

	
	public MaterialRelation(String id, String materialCode, String materialType, Long lastTime, Integer status,
			List<MaterialRelatedEvent> eventList) {
		this.id = id;
		this.materialCode = materialCode;
		this.materialType = materialType;
		this.lastTime = lastTime;
		this.status = status;
		this.eventList = eventList;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<MaterialRelatedEvent> getEventList() {
		return eventList;
	}

	public void setEventList(List<MaterialRelatedEvent> eventList) {
		this.eventList = eventList;
	}

}
