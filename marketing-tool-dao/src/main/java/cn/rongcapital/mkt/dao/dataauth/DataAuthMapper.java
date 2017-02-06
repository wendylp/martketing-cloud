package cn.rongcapital.mkt.dao.dataauth;

import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.dataauth.po.DataAuth;
import org.apache.ibatis.annotations.Param;

public interface DataAuthMapper {

    
    int deleteByPrimaryKey(Long daId);

    
    int insert(DataAuth record);

    
    int insertSelective(DataAuth record);

    
    DataAuth selectByPrimaryKey(Long daId);

    
    int updateByPrimaryKeySelective(DataAuth record);

    
    int updateByPrimaryKey(DataAuth record);
    
    int deleteDataAuth(DataAuth record);
    
    /**
     * @功能描述: 
     * @author xie.xiaoliang
     * @param params TODO
     * @since 2017-01-22 
     */
    DataAuth selectByPrimaryKeyAndLevelOwner(Map<String, Object> params);
    
    
    DataAuth selectByOwnerAndShare(Map<String, Object> params);
    
    DataAuth selectByTableNameResourceIDOrgId(@Param("tableName")String tableName,@Param("orgId") long orgId,@Param("resourceId") long resourceId);


    /**
     * 通过sharedId 查询被分享的数据权限
     * @功能描述: message
     * @param shareId void
     * @author xie.xiaoliang
     * @since 2017-01-31 
     */
    DataAuth selectByShareId(String shareId);


    /**
     * @功能描述: message
     * @author xie.xiaoliang
     * @param list 
     * @param tableName 
     * @param resourceId 
     * @since 2017-01-31 
     */
    List<DataAuth> selectByOrgIds(@Param("list")List<Long> list,@Param("tableName") String tableName,@Param("resourceId") long resourceId);
    
   
    
    
    /**
     * @功能描述: 查询是否有分享的资源
     * @param params void
     * @author xie.xiaoliang
     * @since 2017-01-31 
     */
    List<DataAuth> selectByFromOrgIds(@Param("tableName")String tableName,@Param("fromResourceId")long fromResourceId,@Param("fromOrgId")long fromOrgId);


    /**
     * @功能描述: message
     * @param resourceType
     * @param resourceId void
     * @author xie.xiaoliang
     * @since 2017-02-06 
     */
    DataAuth selectOwnerByResouceId(@Param("tableName")String resourceType,@Param("resourceId") long resourceId);
    
    
}