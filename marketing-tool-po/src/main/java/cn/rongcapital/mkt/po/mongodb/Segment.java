package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "segment")
public class Segment implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@Field(value = "segmentation_head_id")
	private String segmentationHeadId;
	
	@Field(value = "data_id")
	private String dataId;

	//调用大连接口后，返回的任务id
	private Integer taskId;
	
	//调用大连发送公众号图文接口中，发送方的公众号pubId
	private String pubId;
	
	//发送的微信h5图文地址
	private String h5Url;
	
	//粉丝/好友的微信open_id
	private String fansFriendsOpenId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSegmentationHeadId() {
		return segmentationHeadId;
	}

	public void setSegmentationHeadId(String segmentationHeadId) {
		this.segmentationHeadId = segmentationHeadId;
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

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getH5Url() {
		return h5Url;
	}

	public void setH5Url(String h5Url) {
		this.h5Url = h5Url;
	}

	public String getFansFriendsOpenId() {
		return fansFriendsOpenId;
	}

	public void setFansFriendsOpenId(String fansFriendsOpenId) {
		this.fansFriendsOpenId = fansFriendsOpenId;
	}
	
}
