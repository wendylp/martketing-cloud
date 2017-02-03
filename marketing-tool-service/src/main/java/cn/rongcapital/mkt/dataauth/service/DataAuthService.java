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
package cn.rongcapital.mkt.dataauth.service;

public interface DataAuthService {
    
    /**
     * 新增数据权限
     * @功能描述: message
     * @param orgId
     * @param resourceType
     * @param resourceId void
     * @author xie.xiaoliang
     * @since 2017-01-30
     */
    void put(long orgId,String resourceType,long resourceId);
    /**
     * 删除数据权限
     * @功能描述: message
     * @param resourceType
     * @param resourceId void
     * @author xie.xiaoliang
     * @since 2017-01-30
     */
    void evict(String resourceType,long resourceId);
    
    /**
     * 分享数据权限
     * @功能描述: message void
     * @author xie.xiaoliang
     * @since 2017-01-30
     */
    void share(String resourceType,long resourceId,long fromOrgId,long toOrgId,boolean writeable);
    
    /**
     * 取消分享
     * @功能描述: message
     * @param shareType
     * @param resourceType
     * @param resourceId
     * @param fromOrgId
     * @param toOrgId void
     * @author xie.xiaoliang
     * @since 2017-01-30
     */
    void unshare(String shareType,String resourceType,long resourceId,long fromOrgId,long toOrgId);
    /**
     * 
     * @功能描述: 通过shareId删除数据权限
     * @param shareId void
     * @author xie.xiaoliang
     * @since 2017-01-30
     */
    void unshare(String shareId);
    
    /**
     * clone数据权限
     * @功能描述: message void
     * @author xie.xiaoliang
     * @param fromResourceId TODO
     * @since 2017-01-30
     */
    void clone(String resourceType,long resourceId,long fromOrgId,long fromResourceId,long toOrgId,boolean writeable);
    
 
}
