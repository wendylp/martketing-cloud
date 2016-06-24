package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "node_audience")
public class NodeAudience implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@Field(value = "campaign_head_id")
	private String campaignHeadId;
	
	@Field(value = "item_id")
	private String itemId;
	
	@Field(value = "data_id")
	private String dataId;
	
	private Integer taskId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCampaignHeadId() {
		return campaignHeadId;
	}

	public void setCampaignHeadId(String campaignHeadId) {
		this.campaignHeadId = campaignHeadId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
	
}
