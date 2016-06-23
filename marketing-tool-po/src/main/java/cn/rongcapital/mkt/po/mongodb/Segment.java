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
	
}
