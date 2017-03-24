package cn.rongcapital.mkt.dataauth.vo;

import cn.rongcapital.mkt.dataauth.po.Organization;

public class OrganizationOut {
	private Long org_id;
	private Long parentId;
	private String name;
	private Byte status;

	public OrganizationOut(Long org_id, Long parentId, String name,  Byte status) {
		this.org_id = org_id;
		this.parentId = parentId;
		this.name = name;
		this.status = status;
	}

	public OrganizationOut(Long org_id, Long parentId, String name, Integer status) {
		this.org_id = org_id;
		this.parentId = parentId;
		this.name = name;
		this.status = Byte.valueOf(status.toString());
	}
	
	public OrganizationOut(Organization org) {
		this.org_id = org.getOrgId() != null ? org.getOrgId() : null;
		this.parentId = org.getParentId() != null ? org.getParentId() : null;
		this.name = org.getName() != null ? org.getName() : "";
		this.status = org.getStatus() != null ? Byte.valueOf(org.getStatus()) : null;
	}

	public OrganizationOut() {
	}



	public Long getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
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

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Organization[ org_id:" + org_id + ", parentId:" + parentId + ", name:" + name + ", status:" + status + " ]";
	}

}
