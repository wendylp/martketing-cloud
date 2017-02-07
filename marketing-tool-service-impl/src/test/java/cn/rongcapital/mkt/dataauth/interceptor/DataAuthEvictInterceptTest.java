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
public class DataAuthEvictInterceptTest {

    @Mock
    private DataAuthService dataAuthService;

    private DataAuthEvictInterceptor aspect = null;



    /**
     * @功能描述: message
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @Before
    public void setUp() throws Exception {
        aspect = new DataAuthEvictInterceptor();

        Mockito.doNothing().when(dataAuthService).evict(Mockito.any(String.class),
                Mockito.any(long.class));
        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);
    }

    public class PutBeanService {
        public void evict(PutBean putBean) {

        }
    }
    public class PutBean {
        private String resourceType;
        private long resourceId;

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
            @DataAuthEvict(resourceType = "business", resourceId = "#putBean.resourceId",  type = ParamType.SpEl)
            public void evict(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802);
        
        proxy.evict(putBean);
    }

    @Test
    public void testParamDefault() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthEvict(resourceType = "business", resourceId = "1", type = ParamType.Default)
            public void evict(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802);
        
        proxy.evict(putBean);
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthEvict(resourceType = "", resourceId = "#putBean.resourceId",  type = ParamType.SpEl)
            public void evict(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802);
        
        proxy.evict(putBean);
    }

}

