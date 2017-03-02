package cn.rongcapital.mkt.usersource.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UsersourceClassificationOut {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("parent_id")
	private Long parentId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("count")
	private Long count;
	
	
	public UsersourceClassificationOut(){};
	public UsersourceClassificationOut(Long id, Long parentId, String name, Long count) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.count = count;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
	
}
