package cn.rongcapital.mkt.dataauth.vo;

import cn.rongcapital.mkt.dataauth.po.Organization;

public class OrgParentLineOutPut extends Organization {
	private OrgParentLineOutPut organization;
	public OrgParentLineOutPut getOrganization() {
		return organization;
	}

	public void setOrganization(OrgParentLineOutPut organization) {
		this.organization = organization;
	}
	
	public OrgParentLineOutPut() {
	}
	
	public OrgParentLineOutPut(Organization org) {
		super(org);
	}
}
