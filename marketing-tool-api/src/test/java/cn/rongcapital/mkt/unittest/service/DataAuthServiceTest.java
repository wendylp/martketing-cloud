/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-01-30 
 * @date(最后修改日期)：2017-01-30 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.unittest.service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.enums.dataauth.DataAuthTypeEnum;
import cn.rongcapital.mkt.common.exception.CannotShareToOwnerException;
import cn.rongcapital.mkt.common.exception.NotFoundResourceException;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.unittest.AbstractUnitTest;


public class DataAuthServiceTest extends AbstractUnitTest{
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DataAuthService dataAuthService;
    @Before
    public void setUp() throws Exception {
        logger.info("-------------------------测试开始---------------------------");
    }
    
    @After
    public void setAfter() throws Exception {
        logger.info("-------------------------测试结束---------------------------");
    }
    @Test
    public void TestPut(){
        long orgId = 16l;
        String resourceType = "business";
        long resourceId = 2;
        
        this.dataAuthService.put(orgId, resourceType,
            resourceId);
        
    }
    @Test
    public void TestEvict(){
        String resourceType = "business";
        long resourceId = 2;
        this.dataAuthService.evict(resourceType, resourceId);
    }
    @Test(expected=CannotShareToOwnerException.class)
    public void TestShare() throws CannotShareToOwnerException, NotFoundResourceException{
        String shareType = DataAuthTypeEnum.SHARE.getCode();
        String resourceType = "sms_templet";
        long resourceId = 800l;
        long fromOrgId = 16l;
        long toOrgIds = 14l;
        boolean writeable = Boolean.TRUE;
        
        this.dataAuthService.share(resourceType, resourceId, toOrgIds, writeable);
    }
    
    @Test
    public void TestUnShare(){
        String shareId= "01adfd47-d50f-4ab5-ab85-32792b5e3d3c";
        
        this.dataAuthService.unshare(shareId);
    }
    
    @Test
    public void TestClone(){
        
        String resourceType="sms_templet";
        long resourceId = 800l;
        long fromOrgId = 16L;
        long fromResourceId = 1l;
        long toOrgId = 18l;
        boolean writeable = Boolean.TRUE;
        this.dataAuthService.clone(resourceType, resourceId, fromResourceId, toOrgId, writeable);
    }

}
