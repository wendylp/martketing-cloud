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

import cn.rongcapital.mkt.common.exception.CannotCloneBySharerException;
import cn.rongcapital.mkt.common.exception.CannotShareToOwnerException;
import cn.rongcapital.mkt.common.exception.NoWriteablePermissionException;
import cn.rongcapital.mkt.common.exception.NotFoundResourceException;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface DataAuthService {
    
    /**
     * 新增数据权限
     * @功能描述: 新增数据权限
     * @param orgId 组织ID
     * @param resourceType 相应的数据表名
     * @param resourceId 资源ID
     * @author xie.xiaoliang
     * @since 2017-01-30
     */
    void put(long orgId,String resourceType,long resourceId);
    /**
     * 删除数据权限
     * @功能描述: 删除数据权限
     * @param resourceType 相应的数据表名
     * @param resourceId 资源ID
     * @author xie.xiaoliang
     * @since 2017-01-30
     */
    void evict(String resourceType,long resourceId);
    
    /**
     * 分享数据权限
     * @功能描述: 分享数据权限
     * @param resourceType 相应数据表名
     * @param resourceId 主键ID
     * @param toOrgId 分享给哪个组织
     * @param writeable 是否可写权限
     * @author xie.xiaoliang
     * @throws CannotShareToOwnerException 
     * @throws NotFoundResourceException 
     * @since 2017-02-03
     */
    void share(String resourceType,long resourceId,long toOrgId,boolean writeable) throws CannotShareToOwnerException, NotFoundResourceException;
    
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
    @Deprecated
    void unshare(String shareType,String resourceType,long resourceId,long fromOrgId,long toOrgId);
    /**
     * 
     * @功能描述: 通过shareId删除数据权限
     * @param shareId 分享ID
     * @author xie.xiaoliang
     * @since 2017-01-30
     */
    void unshare(String shareId);
    
    /**
     * 克隆数据权限
     * @功能描述: 克隆数据权限
     * @param resourceType 相应的数据表名
     * @param resourceId 主键ID
     * @param fromOrgId
     * @param fromResourceId 从哪个数据ID克隆
     * @param toOrgId 克隆给哪个组织
     * @param writeable 可写权限    @author xie.xiaoliang
     * @since 2017-02-03
     */
    void clone(String resourceType, long resourceId, long fromOrgId, long fromResourceId, long toOrgId, boolean writeable) throws NotFoundResourceException, CannotShareToOwnerException, CannotCloneBySharerException;
    
    /**
     * 判断当前组织对该资源是否具有writeable 可写权限
     * @功能描述: 判断当前组织对该资源是否具有writeable 可写权限
     * @param resouceType 资源表名称
     * @param resouceId 资源ID
     * @param orgId 组织ID
     * @return boolean 验证是否通过
     * @exception NoWriteablePermissionException 无可写权限异常
     * @author xie.xiaoliang
     * @since 2017-02-06
     */
    boolean validateWriteable(String resourceType,long resourceId,long orgId) throws NoWriteablePermissionException;
 
    /**
     * 
     * @param resourceId 资源id
     * @param orgId 组织id
     * @param tableName 表名
     * @param type toOrgs(分享给了哪些组织),orgsTo(从哪个组织分享来的)
     * @param oprType 操作类型 分享/克隆
     * @return 返回组织列表
     * @author xie.xiaoliang
     * @since 2017-02-08
     */
    BaseOutput getOrgFromResShare(long resourceId, long orgId, String tableName, String type, String oprType, Integer index, Integer size);
}
