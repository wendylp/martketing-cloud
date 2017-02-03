package cn.rongcapital.mkt.dataauth.vo;

import java.util.ArrayList;
import java.util.List;

import cn.rongcapital.mkt.dataauth.po.Organization;


public class OrgChildTreeOutPut extends Organization {
	private List<OrgChildTreeOutPut> orgList = new ArrayList<OrgChildTreeOutPut>();
	public List<OrgChildTreeOutPut> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<OrgChildTreeOutPut> orgList) {
		this.orgList = orgList;
	}

	public OrgChildTreeOutPut(Organization org) {
		super(org);
	}
	
}
