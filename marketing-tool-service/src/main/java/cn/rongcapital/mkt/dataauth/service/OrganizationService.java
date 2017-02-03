package cn.rongcapital.mkt.dataauth.service;

import java.util.List;

import cn.rongcapital.mkt.dataauth.po.Organization;
import cn.rongcapital.mkt.dataauth.vo.OrgChildTreeOutPut;
import cn.rongcapital.mkt.dataauth.vo.OrgParentLineOutPut;

public interface OrganizationService {
	
	public int insert(Organization org);
	
    public int update(Organization org);

    public OrgChildTreeOutPut getOrgTreeById(Long id);
    
    public Organization	getOrgById(Long id);

    public OrgParentLineOutPut getOrgLineById(Long id);
    
    public List<Organization> getOrgLineListById(Long id);

    public List<Organization> getChildOrgListById(Long id);
}
