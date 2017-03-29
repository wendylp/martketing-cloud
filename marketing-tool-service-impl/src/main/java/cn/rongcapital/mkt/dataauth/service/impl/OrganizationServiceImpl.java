/*************************************************
 * @功能及特点的描述简述: 组织结构Service
 * @对应项目名称：营销云系统
 * @author: 单璟琦
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.dataauth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.dataauth.OrganizationDao;
import cn.rongcapital.mkt.dataauth.po.Organization;
import cn.rongcapital.mkt.dataauth.service.OrganizationService;
import cn.rongcapital.mkt.dataauth.vo.OrgChildTreeOutPut;
import cn.rongcapital.mkt.dataauth.vo.OrgParentLineOutPut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrganizationDao organizationDao;

	/**
	 * @author
	 * @功能简述: 根据节点ID获取当前节点及其所有叶子节点，并以树形结构返回
	 * @param Long id
	 * @return OrgChildTreeOutPut
	 */
	@Override
	public OrgChildTreeOutPut getOrgTreeById(Long id) {
		return generateOrgTree(id);
	}

	private OrgChildTreeOutPut generateOrgTree(Long id) {

		Organization org = organizationDao.getNodeById(id);
		OrgChildTreeOutPut orgCT = null;
		if (org != null) {
			orgCT = new OrgChildTreeOutPut(org);
			List<Organization> clist = organizationDao.getChildNodeById(id);
			for (Organization child : clist) {
				OrgChildTreeOutPut node = generateOrgTree(child.getOrgId());
				orgCT.getOrgList().add(node);
			}
		}
		return orgCT;
	}
	
	@Override
	public BaseOutput getOrgTreeByIdForUI(Long id) {
		BaseOutput output = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
		OrgChildTreeOutPut tree = getOrgTreeById(id);
		List<OrgChildTreeOutPut> list = new ArrayList<>();
		list.add(tree);
		output.getData().add(list);
		output.setTotal(list.size());
		
		return output;
	}
	
	@Override
	public BaseOutput getChildTreeAndBrotherListById(Long id) {
		BaseOutput output = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
		OrgChildTreeOutPut tree = getOrgTreeById(id);
		output.getData().add(tree);
		
		if(tree.getParentId() == null){ //只有根节点没有parent id，目前系统只支持一个root
			Organization org = new Organization();
			org.setParentId(tree.getParentId());
			org.setStatus((byte)0);
			List<Organization> brotherList =  organizationDao.selectList(org);
			
			OrgChildTreeOutPut brother = null;
			if(!CollectionUtils.isEmpty(brotherList)){
				for (Organization temp : brotherList) {
					if(temp.getOrgId().longValue() != id.longValue()){
						brother = new OrgChildTreeOutPut(temp);
						output.getData().add(brother);
					}
				}
			}
		}

		return output;
	}

	/**
	 * @author
	 * @功能简述: 根据节点ID获取取当前节点及其所有父节点，并以树形结构返回
	 * @param Long id
	 * @return OrgParentLineOutPut
	 */
	@Override
	public OrgParentLineOutPut getOrgLineById(Long id) {
		return generateOrgLine(id);
	}

	private OrgParentLineOutPut generateOrgLine(Long id) {

		Organization org = organizationDao.getNodeById(id);
		OrgParentLineOutPut orgFL = null;
		if (org != null) {
			orgFL = new OrgParentLineOutPut(org);
			OrgParentLineOutPut node = generateOrgLine(org.getParentId());
			orgFL.setOrganization(node);
		}
		return orgFL;
	}

	/**
	 * @author
	 * @功能简述: 根据节点ID获取当前节点所有父节点(不包含当前节点)，并以list形式返回
	 * @param Long id
	 * @return List<Organization>
	 */
	@Override
	public List<Organization> getOrgLineListById(Long id) {
		List<Organization> orgList = new ArrayList<Organization>();
		
		Organization org = organizationDao.getNodeById(id);
		if(org!=null && org.getParentId()!=null){
			getParentLineList(org.getParentId(), orgList);
		}
		
		return orgList;
	}
	
	private void getParentLineList(Long id, List<Organization> list) {
		Organization org = organizationDao.getNodeById(id);
		if(org!=null){
			list.add(org);
			if(org.getParentId()!=null){
				getParentLineList(org.getParentId(), list);
			}
		}
	}

	/**
	 * @author
	 * @功能简述: 根据节点ID获取其所有子节点，不包含当前节点，并以list形式返回
	 * @param Long id
	 * @return List<Organization>
	 */
	@Override
	public List<Organization> getChildOrgListById(Long id) {
		List<Organization> list = new ArrayList<Organization>();
		generateOrgTreeList(id, list);
		return list;
	}

	private void generateOrgTreeList(Long id, List<Organization> list) {

		Organization org = organizationDao.getNodeById(id);
		if (org != null) {
			List<Organization> clist = organizationDao.getChildNodeById(id);
			if (clist != null && clist.size() > 0) {
				list.addAll(clist);
				for (Organization child : clist) {
					generateOrgTreeList(child.getOrgId(), list);
				}
			}
		}
	}

	/**
	 * @author
	 * @功能简述: 根据节点ID获取当前节点以及其所有子节点，并以list形式返回
	 * @param Long id
	 * @return BaseOutput
	 */
	@Override
	public BaseOutput getOrgListById(Long id) {
		BaseOutput output = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
		
		Organization org = organizationDao.getNodeById(id);
		if (org != null) {
			List<Organization> list = new ArrayList<Organization>();
			list.add(org);
			list.addAll(getChildOrgListById(id));
			if(!CollectionUtils.isEmpty(list)){
				output.setTotal(list.size());
				output.getData().addAll(list);
			}
		}
		
		return output;
	}

	@Override
	public Organization getOrgById(Long id) {
		return organizationDao.getNodeById(id);
	}

	@Override
	public int insert(Organization org) {
		return organizationDao.insert(org);
	}

	@Override
	public int update(Organization org) {
		return organizationDao.updateById(org);
	}

}
