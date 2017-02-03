package cn.rongcapital.mkt.dataauth.po;

public class Organization {
	private Long orgId;
	private Long parentId;
	private String name;
	private String type;
	private Byte status;

	public Organization(Long orgId, Long parentId, String name, String type, Byte status) {
		this.orgId = orgId;
		this.parentId = parentId;
		this.name = name;
		this.type = type;
		this.status = status;
	}

	public Organization(Long orgId, Long parentId, String name, String type, Integer status) {
		this.orgId = orgId;
		this.parentId = parentId;
		this.name = name;
		this.type = type;
		this.status = Byte.valueOf(status.toString());
	}
	
	public Organization(Organization org) {
		this.orgId = org.getOrgId() != null ? org.getOrgId() : null;
		this.parentId = org.getParentId() != null ? org.getParentId() : null;
		this.name = org.getName() != null ? org.getName() : "";
		this.type = org.getType() != null ? org.getType() : "";
		this.status = org.getStatus() != null ? Byte.valueOf(org.getStatus()) : null;
	}

	public Organization() {
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Organization[ orgId:" + orgId + ", parentId:" + parentId + ", name:" + name + ", type:" + type
				+ ", status:" + status + " ]";
	}

}
