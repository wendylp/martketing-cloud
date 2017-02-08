/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-02-07 
 * @date(最后修改日期)：2017-02-07 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.dataauth.interceptor;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dataauth.service.DataAuthService;

@RunWith(MockitoJUnitRunner.class)
public class DataAuthPutInterceptTest {

    @Mock
    private DataAuthService dataAuthService;

    private DataAuthPutInterceptor aspect = null;



    /**
     * @功能描述: message
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @Before
    public void setUp() throws Exception {
        aspect = new DataAuthPutInterceptor();

        Mockito.doNothing().when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);
    }

    public class PutBeanService {
        public void save(PutBean putBean) {

        }
    }
    public class PutBean {
        private String resourceType;
        private long resourceId;
        private long orgId;

        public String getResourceType() {
            return resourceType;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }

        public long getResourceId() {
            return resourceId;
        }

        public void setResourceId(long resourceId) {
            this.resourceId = resourceId;
        }

        public long getOrgId() {
            return orgId;
        }

        public void setOrgId(long orgId) {
            this.orgId = orgId;
        }


    }



    /**
     * @功能描述: message
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @After
    public void tearDown() throws Exception {}

    @Test
    public void test() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", resourceId = "#putBean.resourceId", orgId = "#putBean.orgId", type = ParamType.SpEl)
            public void save(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802);
        putBean.setOrgId(1);
        proxy.save(putBean);
        Mockito.verify(dataAuthService,Mockito.times(1)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }

    @Test
    public void testParamDefault() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", resourceId = "1", orgId = "1", type = ParamType.Default)
            public void save(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802);
        putBean.setOrgId(1);
        proxy.save(putBean);
        
        Mockito.verify(dataAuthService,Mockito.times(1)).put(Mockito.any(long.class), Mockito.any(String.class),
            Mockito.any(long.class));
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "", resourceId = "#putBean.resourceId", orgId = "#putBean.orgId", type = ParamType.SpEl)
            public void save(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802);
        putBean.setOrgId(1);
        proxy.save(putBean);
        
    }

}

