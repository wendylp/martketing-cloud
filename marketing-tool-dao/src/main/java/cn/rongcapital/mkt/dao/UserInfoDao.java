package cn.rongcapital.mkt.dao;

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.UserInfo;

public interface UserInfoDao extends BaseDao<UserInfo>{
    
    /**
     * @Title: getUserInfo   
     * @Description: 通过userId查询用户信息  
     * @param: @param userId
     * @param: @return      
     * @return: UserInfo      
     * @throws
     */
    public UserInfo getUserInfo(@Param("userId") String userId, @Param("userCode") String userCode);
    
    
     //add by lhz
    public UserInfo getMappingUserInfo(@Param("userId") String userId,@Param("userCode") String userCode);
    
    //add by lhz
    public int insert(UserInfo t);
    
}