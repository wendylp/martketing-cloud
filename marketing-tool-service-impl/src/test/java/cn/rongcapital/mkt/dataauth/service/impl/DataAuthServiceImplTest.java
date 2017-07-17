/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-02-06 
 * @date(最后修改日期)：2017-02-06 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.dataauth.service.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.exception.NoWriteablePermissionException;
import cn.rongcapital.mkt.dao.dataauth.DataAuthMapper;
import cn.rongcapital.mkt.dataauth.po.DataAuth;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;

@RunWith(MockitoJUnitRunner.class)
public class DataAuthServiceImplTest {
    
    @Mock
    private DataAuthMapper dataAuthMapper;
    
    private DataAuthService dataAuthService;
    
    private String resouceType = "business";
    private long resourceId=  1;
    private long orgId = 1;
    
    @Before
    public void setUp() throws Exception {
        dataAuthService = new DataAuthServiceImpl();
    }

    @After
    public void tearDown() throws Exception {}
    @Test(expected=NoWriteablePermissionException.class)
    public void testValidateWriteable() throws NoWriteablePermissionException{
        Mockito.when(dataAuthMapper.selectByTableNameResourceIDOrgId
            (Mockito.any(String.class), Mockito.any(long.class), Mockito.any(long.class)))
        .thenReturn(null);
        ReflectionTestUtils.setField(dataAuthService, "dataAuthMapper", dataAuthMapper);
        
        this.dataAuthService.validateWriteable(resouceType, resourceId, orgId);
    }
    
    @Test
    public void testValidateWriteableSuccess() throws NoWriteablePermissionException{
        DataAuth dataAuth = new DataAuth();
        dataAuth.setWriteable(Boolean.TRUE);
        
        Mockito.when(dataAuthMapper.selectByTableNameResourceIDOrgId
            (Mockito.any(String.class), Mockito.any(long.class), Mockito.any(long.class)))
        .thenReturn(dataAuth);
        ReflectionTestUtils.setField(dataAuthService, "dataAuthMapper", dataAuthMapper);
        
        boolean result =  this.dataAuthService.validateWriteable(resouceType, resourceId, orgId);
        Assert.assertEquals(Boolean.TRUE, result);
    }

}
