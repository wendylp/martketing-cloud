package cn.rongcapital.mkt.dao.dataauth;

import java.util.List;

import cn.rongcapital.mkt.dataauth.po.Organization;

public interface OrganizationDao  {
	
	int insert(Organization org);
	
	int updateById(Organization org);

	String determinOrgTypeById(Long id);
	
	Organization getNodeById(Long id);
	
	List<Organization> getChildNodeById(Long id);

	List<Organization> selectList(Organization org);
}
