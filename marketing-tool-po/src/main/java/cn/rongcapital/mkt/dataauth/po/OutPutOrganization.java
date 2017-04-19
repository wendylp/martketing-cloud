package cn.rongcapital.mkt.dataauth.po;

import java.util.Date;

public class OutPutOrganization{
	private Long orgId;
	private String name;
	private Long resourceId;
	private Date createTime;
	
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getOrgId() {
		return orgId;
	}
	
}
