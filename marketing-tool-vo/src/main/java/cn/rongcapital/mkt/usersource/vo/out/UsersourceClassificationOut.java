package cn.rongcapital.mkt.usersource.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class UsersourceClassificationOut{
	
	private Long id;
	
	@JsonProperty("parent_id")
	private Long parentId;
	
	private String name;
	
	private Long count;
	
	private Byte status; 
	
	@JsonProperty("children_node")
	private List<UsersourceClassificationOut> list = new ArrayList<>();
	
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
	
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public List<UsersourceClassificationOut> getList() {
		return list;
	}
	
	public void setList(List<UsersourceClassificationOut> list) {
		this.list = list;
	}
	
	
}
