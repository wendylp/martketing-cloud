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

import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.NoSuchElementException;

@RunWith(MockitoJUnitRunner.class)
public class DataAuthCloneInterceptTest {

    @Mock
    private DataAuthService dataAuthService;

    private DataAuthCloneInterceptor aspect = null;



    /**
     * @功能描述: message
     * @throws Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @Before
    public void setUp() throws Exception {
        aspect = new DataAuthCloneInterceptor();

        Mockito.doNothing().when(dataAuthService).clone(Mockito.anyString(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyBoolean());
        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);
    }

    public class PutBeanService {
        public void clone(PutBean putBean) {

        }
    }
    public class PutBean {

        private String resourceType;
        private long resourceId;
        private long fromResourceId;
        private long toOrgId;
        private boolean writeable;

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

        public long getFromResourceId() {
            return fromResourceId;
        }

        public void setFromResourceId(long fromResourceId) {
            this.fromResourceId = fromResourceId;
        }

        public long getToOrgId() {
            return toOrgId;
        }

        public void setToOrgId(long toOrgId) {
            this.toOrgId = toOrgId;
        }

        public boolean isWriteable() {
            return writeable;
        }

        public void setWriteable(boolean writeable) {
            this.writeable = writeable;
        }
    }



    /**
     * @功能描述: message
     * @throws Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @After
    public void tearDown() throws Exception {}

    @Test
    public void test() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthClone(resourceType = "business", resourceId = "#putBean.resourceId",fromResourceId = "#putBean.fromResourceId",toOrgId = "#putBean.toOrgId",writeable = "#putBean.writeable", type = ParamType.SpEl)
            public void clone(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802);
        putBean.setFromResourceId(1);
        putBean.setToOrgId(2);
        putBean.setWriteable(Boolean.TRUE);
        proxy.clone(putBean);
        Mockito.verify(dataAuthService,Mockito.times(1)).clone(Mockito.anyString(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyBoolean());
    }

    @Test
    public void testParamDefault() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthClone(resourceType = "business",resourceId = "802",fromResourceId = "1",toOrgId = "1",writeable = "true", type = ParamType.Default)
            public void clone(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802);
        putBean.setFromResourceId(1);
        putBean.setToOrgId(2);
        putBean.setWriteable(Boolean.TRUE);
        proxy.clone(putBean);
        Mockito.verify(dataAuthService,Mockito.times(1)).clone(Mockito.anyString(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyBoolean());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthClone(resourceType = "", resourceId = "#putBean.resourceId",fromResourceId = "#putBean.fromResourceId",toOrgId = "#putBean.toOrgId",writeable = "#putBean.writeable", type = ParamType.SpEl)
            public void clone(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802);
        putBean.setFromResourceId(1);
        putBean.setToOrgId(2);
        putBean.setWriteable(Boolean.TRUE);
        proxy.clone(putBean);
        
        
    }

}

