/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-01-20 
 * @date(最后修改日期)：2017-01-20 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.dataauth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.dataauth.DataAuthTypeEnum;
import cn.rongcapital.mkt.common.enums.dataauth.ShareOrgTypeEnum;
import cn.rongcapital.mkt.common.exception.CannotShareToOwnerException;
import cn.rongcapital.mkt.common.exception.NoWriteablePermissionException;
import cn.rongcapital.mkt.dao.dataauth.DataAuthMapper;
import cn.rongcapital.mkt.dataauth.po.DataAuth;
import cn.rongcapital.mkt.dataauth.po.Organization;
import cn.rongcapital.mkt.dataauth.po.OutPutOrganization;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.dataauth.service.OrganizationService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataAuthServiceImpl implements DataAuthService {
    @Autowired
    private DataAuthMapper dataAuthMapper;
    
    @Autowired
    private OrganizationService organizationService;

    /* (non-Javadoc)
     * @see DataAuthService#put(long, java.lang.String, long)
     */
    @Override
    @Transactional
    public void put(long orgId, String resourceType, long resourceId) {
        
        DataAuth auth = new DataAuth();
        auth.setTableName(resourceType);
        auth.setResourceId(resourceId);
        auth.setOrgId(orgId);
        auth.setFirsthand(Boolean.TRUE);//设置直接权限
        auth.setWriteable(Boolean.TRUE);//设置具有写权限
        auth.setShared(Boolean.FALSE);//设置是否被分享过
        Date date = new Date();
        auth.setCreateTime(date);
        this.dataAuthMapper.insert(auth);
        
        // 其次插入当前资源的间接权限
        insertSecondhandAccess(orgId, auth);
    }

    /**
     * @功能描述: 插入间接权限
     * @param orgId
     * @param auth void
     * @author xie.xiaoliang
     * @since 2017-02-02 
     */
    private void insertSecondhandAccess(long orgId, DataAuth auth) {
        List<Organization> organizations = this.organizationService.getOrgLineListById(orgId);
        DataAuth dataAuthPar = null;
        if (organizations.size() > 0) {
            for (Organization organization : organizations) {
                dataAuthPar = new DataAuth();
                BeanUtils.copyProperties(auth, dataAuthPar);
                dataAuthPar.setOrgId(organization.getOrgId());
                dataAuthPar.setFirsthand(Boolean.FALSE);//设置直接权限
                dataAuthPar.setWriteable(Boolean.TRUE);//设置具有写权限
                this.dataAuthMapper.insert(dataAuthPar);
            }
        }
    }

    /* (non-Javadoc)
     * @see DataAuthService#evict(java.lang.String, long)
     */
    @Override
    @Transactional
    public void evict(String resourceType, long resourceId) {
        DataAuth dataAuth = new DataAuth();
        dataAuth.setTableName(resourceType);
        dataAuth.setResourceId(resourceId);
        this.dataAuthMapper.deleteDataAuth(dataAuth);
    }

    /* (non-Javadoc)
     * @see DataAuthService#share(java.lang.String, java.lang.String, long, long, long, boolean)
     */
    @Override
    @Transactional
    public void share(String resourceType, long resourceId, long toOrgId, boolean writeable) throws CannotShareToOwnerException {
        List<DataAuth> fromDataAuth = this.dataAuthMapper.selectOwnerByResouceId(resourceType,resourceId);
        long fromOrgId = fromDataAuth.get(0).getOrgId();
        
        List<Organization> orgParentList = this.organizationService.getOrgLineListById(fromOrgId);
        List<Long> orgIdList = new ArrayList<>();
        for (Organization organization : orgParentList) {
            orgIdList.add(organization.getOrgId());
        }
        orgIdList.add(fromOrgId);
        if(orgIdList.contains(toOrgId)){
            String message = "To org is the owner of current resource,can not be shared.";
            throw new CannotShareToOwnerException(message );
        }
        
        
        String shareId = UUID.randomUUID().toString();
        
       
        shareLoop(DataAuthTypeEnum.SHARE.getCode(), resourceType, resourceId, fromOrgId, toOrgId, writeable, shareId, true);
        // 回写数据的分享状态
        writeBackShareStatus(resourceType, resourceId, fromOrgId,Boolean.TRUE);
    }

    /**
     * @功能描述: 回写原始数据的分享状态
     * @param resourceType
     * @param resourceId
     * @param fromOrgId void
     * @author xie.xiaoliang
     * @since 2017-02-02 
     */
    private void writeBackShareStatus(String resourceType, long resourceId, long fromOrgId,boolean shared) {
        DataAuth fromDataAuth = this.dataAuthMapper.selectByTableNameResourceIDOrgId(resourceType, fromOrgId, resourceId);
        fromDataAuth.setShared(shared);
        this.dataAuthMapper.updateByPrimaryKey(fromDataAuth);
    }

    /**
     * @功能描述: message
     * @param shareType
     * @param resourceType
     * @param resourceId
     * @param fromOrgId
     * @param toOrgId
     * @param writeable void
     * @param shareId TODO
     * @param firsthand TODO
     * @author xie.xiaoliang
     * @since 2017-01-30 
     */
    private void shareLoop(String shareType, String resourceType, long resourceId, long fromOrgId, long toOrgId,
            boolean writeable, String shareId, boolean firsthand) {
        boolean over = false;
        
        Map<String,String> resultMap = new HashMap<>();
        
        //查看当前组织是否对当前数据已经拥有数据权限
        DataAuth dataAuth =this.dataAuthMapper.selectByTableNameResourceIDOrgId(resourceType, toOrgId, resourceId);
        
        if(dataAuth != null){
            //当前组织已经是当前数据的拥有者时，返回警告不能再分享
            if(StringUtils.isEmpty( dataAuth.getShareType())){
                resultMap.put("result", "当前已经为当前数据的拥有者，不能再进行分享");
                over = true;
            }else{
                //当前组织不是当前数据权限的拥有者，且具有最高权限，则不需要再吃分享
                if(dataAuth.getWriteable()){
                    resultMap.put("result", "当前数据已经被分享过，不需要再分享");
                    over = true;
                }else{
                    //需要再次分享，设置当前状态为分享的状态,并更新数据
                    dataAuth.setWriteable(writeable);
                    dataAuth.setShareId(shareId);
                    dataAuth.setFirsthand(firsthand);
                    this.dataAuthMapper.updateByPrimaryKey(dataAuth);
                    over = false;
                }
            }
        }else{//并没有在原数据权限表中查询到数据，需要新增一条数据
            dataAuth = new DataAuth();
            dataAuth.setCreateTime(new Date());
            dataAuth.setFirsthand(firsthand);
            dataAuth.setFromOrgId(fromOrgId);
            dataAuth.setFromResourceId(resourceId);
            dataAuth.setOrgId(toOrgId);
            dataAuth.setResourceId(resourceId);
            dataAuth.setShared(Boolean.FALSE);
            dataAuth.setShareId(shareId);
            dataAuth.setShareType(DataAuthTypeEnum.SHARE.getCode());
            dataAuth.setTableName(resourceType);
            dataAuth.setWriteable(writeable);
            this.dataAuthMapper.insert(dataAuth);
            over = false;
        }
        
        Organization org = this.organizationService.getOrgById(toOrgId);
        if(!over && (org.getParentId() != null && org.getParentId() >0)){//如果当前组织有上级组织，则数据权限向上递归,并且为只读权限
            writeable = Boolean.FALSE;
            firsthand = Boolean.FALSE;
            this.shareLoop(shareType, resourceType, resourceId, fromOrgId, org.getParentId(), writeable, shareId, firsthand);
        }
    }

    /* (non-Javadoc)
     * @see DataAuthService#unshare(java.lang.String, java.lang.String, long, long, long)
     */
    @Override
    @Transactional
    public void unshare(String shareType, String resourceType, long resourceId, long fromOrgId, long toOrgId) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see DataAuthService#unshare(java.lang.String)
     */
    @Override
    @Transactional
    public void unshare(String shareId) {
        
        DataAuth dataAuth = this.dataAuthMapper.selectByShareId(shareId);
        
        String resourceType = dataAuth.getTableName();
        long resourceId = dataAuth.getResourceId();
        long toOrgId = dataAuth.getOrgId();
        unshareLoop(dataAuth.getDaId(), shareId, toOrgId, resourceType, resourceId);
        
        
        
        List<DataAuth> sharedList = this.dataAuthMapper.selectByFromOrgIds(resourceType,dataAuth.getFromResourceId(),dataAuth.getFromOrgId() );
        if(sharedList == null || sharedList.size() == 0){
            //回写状态，设置资源是否被分享过
            this.writeBackShareStatus(resourceType, dataAuth.getFromResourceId(), 
                dataAuth.getFromOrgId(),Boolean.FALSE);
        }
        
    }

    /**
     * @功能描述: message
     * @param daId TODO
     * @param shareId
     * @param orgId
     * @param resourceType
     * @param resourceId void
     * @author xie.xiaoliang
     * @since 2017-01-31 
     */
    private void unshareLoop(long daId, String shareId, long orgId, String resourceType, long resourceId) {
        if (daId != 0) {
            this.dataAuthMapper.deleteByPrimaryKey(daId);
        }
        List<Organization> orgList = this.organizationService.getChildOrgListById(orgId);

        if (orgList != null && orgList.size() > 0) {

            List<Long> childOrgIds = new ArrayList<>();
            for (Organization item : orgList) {
                childOrgIds.add(item.getOrgId());
            }
            List<DataAuth> dataAuthList = this.dataAuthMapper.selectByOrgIds(childOrgIds, resourceType, resourceId);

            if (dataAuthList != null && dataAuthList.size() > 0) {
                boolean original = false;
                DataAuth currentDataAuth = new DataAuth();
                for (DataAuth item : dataAuthList) {
                    if (StringUtils.isEmpty(item.getShareType())) {
                        original = true;
                        BeanUtils.copyProperties(item, currentDataAuth);
                        break;
                    }
                }
                // 如果包含原始数据，则直接设置当前节点的权限为拥有者权限，且不再向上递归
                if (original) {
                    currentDataAuth.setOrgId(orgId);
                    currentDataAuth.setFirsthand(Boolean.FALSE);// 设置直接权限
                    currentDataAuth.setWriteable(Boolean.TRUE);// 设置具有写权限
                    this.dataAuthMapper.insert(currentDataAuth);
                } else {// 不包含原始数据，则包含的是分享数据，需要将权限设置为只读的权限，补充在该节点
                    currentDataAuth = new DataAuth();
                    BeanUtils.copyProperties(dataAuthList.get(0), currentDataAuth);
                    currentDataAuth.setOrgId(orgId);
                    currentDataAuth.setFirsthand(Boolean.FALSE);// 设置直接权限
                    currentDataAuth.setWriteable(Boolean.FALSE);// 设置具有只读权限
                    this.dataAuthMapper.insert(currentDataAuth);
                }
            }
        } else {
            Organization currentOrg = this.organizationService.getOrgById(orgId);

            DataAuth parentDataAuth = this.dataAuthMapper.selectByTableNameResourceIDOrgId(resourceType, currentOrg.getParentId(), resourceId);
            if (parentDataAuth != null && !parentDataAuth.getWriteable()
                    && shareId.equals(parentDataAuth.getShareId())) {// 当前节点的父节点为只读权限且为同一次分享，则继续向上递归
                this.unshareLoop(parentDataAuth.getDaId(), shareId, currentOrg.getParentId(), resourceType, resourceId);
            }
        }
    }

    /* (non-Javadoc)
     * @see DataAuthService#clone(java.lang.String, java.lang.String, long, long, long, boolean)
     */
    @Override
    @Transactional
    public void clone(String resourceType, long resourceId, long fromResourceId, long toOrgId, boolean writeable) {
    
        List<DataAuth> fromDataAuth = this.dataAuthMapper.selectOwnerByResouceId(resourceType,fromResourceId);
        long fromOrgId = fromDataAuth.get(0).getOrgId();
        
        DataAuth auth = new DataAuth();
        auth.setTableName(resourceType);
        auth.setResourceId(resourceId);
        auth.setOrgId(toOrgId);
        auth.setFirsthand(Boolean.TRUE);//设置直接权限
        auth.setShareType(DataAuthTypeEnum.CLONE.getCode());
        auth.setWriteable(Boolean.TRUE);//设置具有写权限
        auth.setFromOrgId(fromOrgId);
        auth.setFromResourceId(fromResourceId);
        auth.setShared(Boolean.FALSE);
        auth.setCreateTime(new Date());
        this.dataAuthMapper.insert(auth);
        //再次插入当前资源的间接权限
        insertSecondhandAccess(toOrgId, auth);
        
        //回写数据的分享状态
        writeBackShareStatus(resourceType, fromResourceId, fromOrgId,Boolean.TRUE);
    }

    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.dataauth.service.DataAuthService#validateWriteable(java.lang.String, long, long)
     */
    @Override
    @Transactional
    public boolean validateWriteable(String resouceType, long resourceId, long orgId)
            throws NoWriteablePermissionException {
        DataAuth dataAuth = this.dataAuthMapper.selectByTableNameResourceIDOrgId(resouceType, orgId, resourceId);
        if(dataAuth !=null && dataAuth.getWriteable()){
            return true;
        }
        String excMsg = "The organization do not have writeable permission.";
        throw new NoWriteablePermissionException(excMsg);
    }

	@Override
	public BaseOutput getOrgFromResShare(long resourceId, long orgId, String tableName, String type, String oprType, Integer index, Integer size) {
		
		int startIndex = (index == null || index.intValue() == 0) ? 1 : index;
		int pageSize = (size == null || size.intValue() == 0) ? 10 : size;
		
		if(StringUtils.isEmpty(type)){
			throw new IllegalArgumentException("type is empty");
		}
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		 
		//分享给哪些组织
		if(ShareOrgTypeEnum.TOORGS.getCode().equals(type)){
			List<OutPutOrganization> orgList = dataAuthMapper.getOrgs(resourceId, orgId, tableName, oprType, (startIndex - 1) * pageSize, pageSize);
			int totle = dataAuthMapper.getOrgsTotleCount(resourceId, orgId, tableName, oprType);
			orgList.forEach(out -> result.getData().add(out));
	        result.setTotal(result.getData().size());
	        result.setTotalCount(totle);
		}
		//某个组织分享来得
		if(ShareOrgTypeEnum.ORGTO.getCode().equals(type)){
			List<OutPutOrganization> out =dataAuthMapper.getOrg(resourceId, orgId, tableName, oprType);
			result.getData().add(out.get(0));
		}
		return result;
	}

}
