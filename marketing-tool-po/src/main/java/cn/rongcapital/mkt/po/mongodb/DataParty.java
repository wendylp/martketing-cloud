package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "data_party")
public class DataParty implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private Integer mid;
	
	@Field(value = "md_type")
	private Integer mdType;
	
	private String name;
	
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

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getMdType() {
		return mdType;
	}

	public void setMdType(Integer mdType) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
