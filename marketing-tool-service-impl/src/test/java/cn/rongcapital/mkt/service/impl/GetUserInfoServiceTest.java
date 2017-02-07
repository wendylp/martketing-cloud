/*************************************************
 * @功能简述: Service实现测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;


import static org.mockito.Matchers.any;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.UserInfoDao;
import cn.rongcapital.mkt.dao.dataauth.OrganizationDao;
import cn.rongcapital.mkt.dataauth.po.Organization;
import cn.rongcapital.mkt.po.UserInfo;
import cn.rongcapital.mkt.service.GetUserInfoService;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class GetUserInfoServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private GetUserInfoService getUserInfoService;
    
    
    @Mock
    private UserInfoDao userInfoDao;
    
    private String userId = "user";
    UserInfo user = new UserInfo();
    Organization org = new Organization();
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：GetUserInfoService 准备---------------------");
        
        getUserInfoService = new GetUserInfoServiceImpl();
       
        user.setUserId("user");
        user.setUserCode("111");
        user.setOrgId(11);
        user.setStatus((byte) 1);
        // 把mock的dao set进入service
        
       
        org.setName("辽宁总代理");
        org.setOrgId(11L);
        ReflectionTestUtils.setField(getUserInfoService, "userInfoDao", userInfoDao);
    }
    
    @Test
    public void testGetUserInfo01(){
        logger.info("测试方法: getUserInfo ");
        Mockito.when(userInfoDao.getUserInfo(any())).thenReturn(user);
        BaseOutput out = getUserInfoService.getUserInfo(userId);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), out.getCode());
    }
    
    @Test
    public void testGetUserInfo02(){
        logger.info("测试方法: getUserInfo ");
        Mockito.when(userInfoDao.getUserInfo(any())).thenReturn(null);
        BaseOutput out = getUserInfoService.getUserInfo(userId);
        Assert.assertEquals(ApiErrorCode.THE_PRESON_NOT_FOUND.getCode(), out.getCode());
    }
   
    
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：GetUserInfoService 结束---------------------");
    }
    
//    /**
//     * 反射机制处理orgId属性
//     * @param bean
//     * @param fieldName
//     * @param value
//     * @throws Exception
//     */
//    private static void setFieldValue(Object bean, String fieldName, Object value)
//            throws Exception {
//        StringBuffer result = new StringBuffer();
//        String methodName = result.append("set")
//                .append(fieldName.substring(0, 1).toUpperCase())
//                .append(fieldName.substring(1)).toString();
//
//        @SuppressWarnings("rawtypes")
//		Class[] classArr = new Class[1];
//        classArr[0]= Integer.class;
//        Method method=bean.getClass().getMethod(methodName,classArr);
//        method.invoke(bean,value);
//    }
    
}


