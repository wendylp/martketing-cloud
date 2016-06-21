package cn.rongcapital.mkt.po.mongodb;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "data_party")
public class DataParty {

	@Id
	private String id;
	
	private String mid;
	
	@Field(value = "md_type")
	private String mdType;
	
	@Field(value = "mapping_keyid")
	private String mappingKeyid;
	
	@Field(value = "audience_list")
	private List<Audience> audienceList;
	
	@Field(value = "tag_list")
	private List<Tag> tagList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMdType() {
		return mdType;
	}

	public void setMdType(String mdType) {
		this.mdType = mdType;
	}

	public String getMappingKeyid() {
		return mappingKeyid;
	}

	public void setMappingKeyid(String mappingKeyid) {
		this.mappingKeyid = mappingKeyid;
	}

	public List<Audience> getAudienceList() {
		return audienceList;
	}

	public void setAudienceList(List<Audience> audienceList) {
		this.audienceList = audienceList;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	
}
