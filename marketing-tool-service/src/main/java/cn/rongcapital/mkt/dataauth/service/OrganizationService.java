package cn.rongcapital.mkt.dataauth.service;

import java.util.List;

import cn.rongcapital.mkt.dataauth.po.Organization;
import cn.rongcapital.mkt.dataauth.vo.OrgChildTreeOutPut;
import cn.rongcapital.mkt.dataauth.vo.OrgParentLineOutPut;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface OrganizationService {
	
	public int insert(Organization org);
	
    public int update(Organization org);
    
    public Organization	getOrgById(Long id);

	/**
	 * @author
	 * @功能简述: 根据节点ID获取当前节点及其所有叶子节点，并以树形结构返回
	 * @param Long id
	 * @return OrgChildTreeOutPut
	 */
    public OrgChildTreeOutPut getOrgTreeById(Long id);

	/**
	 * @author
	 * @功能简述: 根据节点ID获取取当前节点及其所有父节点，并以树形结构返回
	 * @param Long id
	 * @return OrgParentLineOutPut
	 */
    public OrgParentLineOutPut getOrgLineById(Long id);
    
	/**
	 * @author
	 * @功能简述: 根据节点ID获取当前节点以及其所有父节点，并以list形式返回
	 * @param Long id
	 * @return List<Organization>
	 */
    public List<Organization> getOrgLineListById(Long id);

	/**
	 * @author
	 * @功能简述: 根据节点ID获取其所有子节点，不包含当前节点，并以list形式返回
	 * @param Long id
	 * @return List<Organization>
	 */
    public List<Organization> getChildOrgListById(Long id);
    
	/**
	 * @author
	 * @功能简述: 根据节点ID获取当前节点以及其所有子节点，并以list形式返回
	 * @param Long id
	 * @return BaseOutput
	 */
    public BaseOutput getOrgListById(Long id);
}
