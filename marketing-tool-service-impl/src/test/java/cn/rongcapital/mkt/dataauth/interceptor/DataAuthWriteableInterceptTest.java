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

import cn.rongcapital.mkt.common.exception.NoWriteablePermissionException;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RunWith(MockitoJUnitRunner.class)
public class DataAuthWriteableInterceptTest {

    @Mock
    private DataAuthService dataAuthService;

    private DataAuthWriteableInterceptor aspect = null;



    /**
     * @功能描述: message
     * @throws Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @Before
    public void setUp() throws Exception {
        aspect = new DataAuthWriteableInterceptor();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Boolean.TRUE;
            }
        }).when(dataAuthService).validateWriteable(Mockito.anyString(),Mockito.anyLong(),Mockito.anyLong());
        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);
    }

    public class WriteableBeanService {
        public void writeable(WriteableBean writeableBean) throws NoWriteablePermissionException {

        }
    }
    public class WriteableBean {
        private String resourceType;
        private Long resourceId;
        private Long orgId;

        public String getResourceType() {
            return resourceType;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }

        public Long getResourceId() {
            return resourceId;
        }

        public void setResourceId(Long resourceId) {
            this.resourceId = resourceId;
        }

        public Long getOrgId() {
            return orgId;
        }

        public void setOrgId(Long orgId) {
            this.orgId = orgId;
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

        AspectJProxyFactory factory = new AspectJProxyFactory(new WriteableBeanService() {
            @Override
            @DataAuthWriteable(resourceType = "business", resourceId = "#writeableBean.resourceId", orgId = "#writeableBean.orgId", type = ParamType.SpEl)
            public void writeable(WriteableBean writeableBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        WriteableBeanService proxy = factory.getProxy();
        WriteableBean writeableBean = new WriteableBean();
        writeableBean.setResourceType("business");
        writeableBean.setResourceId(802l);
        writeableBean.setOrgId(1l);
        proxy.writeable(writeableBean);
        
        Mockito.verify(dataAuthService,Mockito.times(1)).validateWriteable(Mockito.anyString(),Mockito.anyLong(),Mockito.anyLong());
    }

    @Test
    public void testParamDefault() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new WriteableBeanService() {
            @Override
            @DataAuthWriteable(resourceType = "business", resourceId = "1", orgId = "1", type = ParamType.Default)
            public void writeable(WriteableBean writeableBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        WriteableBeanService proxy = factory.getProxy();
        WriteableBean writeableBean = new WriteableBean();
        writeableBean.setResourceType("business");
        writeableBean.setResourceId(802l);
        writeableBean.setOrgId(1l);
        proxy.writeable(writeableBean);
        
        Mockito.verify(dataAuthService,Mockito.times(1)).validateWriteable(Mockito.anyString(),Mockito.anyLong(),Mockito.anyLong());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new WriteableBeanService() {
            @Override
            @DataAuthWriteable(resourceType = "", resourceId = "#writeableBean.resourceId", orgId = "#writeableBean.orgId", type = ParamType.SpEl)
            public void writeable(WriteableBean writeableBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        WriteableBeanService proxy = factory.getProxy();
        WriteableBean writeableBean = new WriteableBean();
        writeableBean.setResourceType("");
        writeableBean.setResourceId(802l);
        writeableBean.setOrgId(1l);
        proxy.writeable(writeableBean);
    }
    @Test(expected = NoWriteablePermissionException.class)
    public void testNoWriteablePermissionException() throws Throwable {
        Mockito.doThrow(new NoWriteablePermissionException()).when(dataAuthService).validateWriteable(Mockito.anyString(),Mockito.anyLong(),Mockito.anyLong());
        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);
        AspectJProxyFactory factory = new AspectJProxyFactory(new WriteableBeanService() {
            @Override
            @Transactional
            @DataAuthWriteable(resourceType = "business", resourceId = "#writeableBean.resourceId", orgId = "#writeableBean.orgId", type = ParamType.SpEl)
            public void writeable(WriteableBean writeableBean) throws NoWriteablePermissionException {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        WriteableBeanService proxy = factory.getProxy();
        WriteableBean writeableBean = new WriteableBean();
        writeableBean.setResourceType("");
        writeableBean.setResourceId(802l);
        writeableBean.setOrgId(1l);
        proxy.writeable(writeableBean);
    }
    @Test
    public void testNoWriteableResourceIdIsnull() throws Throwable {
        Mockito.doThrow(new NoWriteablePermissionException()).when(dataAuthService).validateWriteable(Mockito.anyString(),Mockito.anyLong(),Mockito.anyLong());
        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);
        AspectJProxyFactory factory = new AspectJProxyFactory(new WriteableBeanService() {
            @Override
            @Transactional
            @DataAuthWriteable(resourceType = "business", resourceId = "#writeableBean.resourceId", orgId = "#writeableBean.orgId", type = ParamType.SpEl)
            public void writeable(WriteableBean writeableBean) throws NoWriteablePermissionException {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        WriteableBeanService proxy = factory.getProxy();
        WriteableBean writeableBean = new WriteableBean();
        writeableBean.setResourceType("");
        writeableBean.setResourceId(null);
        writeableBean.setOrgId(1l);
        proxy.writeable(writeableBean);
        Mockito.verify(dataAuthService,Mockito.times(0)).validateWriteable(Mockito.anyString(),Mockito.anyLong(),Mockito.anyLong());
    }
    @Test
    public void testNoWriteableResourceIdIsBlank() throws Throwable {
        Mockito.doThrow(new NoWriteablePermissionException()).when(dataAuthService).validateWriteable(Mockito.anyString(),Mockito.anyLong(),Mockito.anyLong());
        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);
        AspectJProxyFactory factory = new AspectJProxyFactory(new WriteableBeanService() {
            @Override
            @Transactional
            @DataAuthWriteable(resourceType = "business", resourceId = "", orgId = "#writeableBean.orgId", type = ParamType.SpEl)
            public void writeable(WriteableBean writeableBean) throws NoWriteablePermissionException {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        WriteableBeanService proxy = factory.getProxy();
        WriteableBean writeableBean = new WriteableBean();
        writeableBean.setResourceType("");
        writeableBean.setResourceId(null);
        writeableBean.setOrgId(1l);
        proxy.writeable(writeableBean);
        Mockito.verify(dataAuthService,Mockito.times(0)).validateWriteable(Mockito.anyString(),Mockito.anyLong(),Mockito.anyLong());
    }
    
   
}

