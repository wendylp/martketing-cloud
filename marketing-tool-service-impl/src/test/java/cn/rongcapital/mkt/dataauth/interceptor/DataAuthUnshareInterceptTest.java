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
public class DataAuthUnshareInterceptTest {

    @Mock
    private DataAuthService dataAuthService;

    private DataAuthUnshareInterceptor aspect = null;



    /**
     * @功能描述: message
     * @throws Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @Before
    public void setUp() throws Exception {
        aspect = new DataAuthUnshareInterceptor();

        Mockito.doNothing().when(dataAuthService).unshare(Mockito.any(String.class));
        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);
    }

    public class PutBeanService {
        public void unshare(PutBean putBean) {

        }
    }
    public class PutBean {
        private String shareId;
        private long orgId;

        public String getShareId() {
            return shareId;
        }

        public void setShareId(String shareId) {
            this.shareId = shareId;
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
            @DataAuthUnshare(shareId = "#putBean.shareId",orgId = "#putBean.orgId",  type = ParamType.SpEl)
            public void unshare(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setShareId("123123123123");
        putBean.setOrgId(1);
        
        proxy.unshare(putBean);
        Mockito.verify(dataAuthService,Mockito.times(1)).unshare(Mockito.any(String.class));
    }

    @Test
    public void testParamDefault() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthUnshare(shareId = "123123",orgId = "1", type = ParamType.Default)
            public void unshare(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setShareId("12312312312321");
        putBean.setOrgId(1);
        
        proxy.unshare(putBean);
        Mockito.verify(dataAuthService,Mockito.times(1)).unshare(Mockito.any(String.class));
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthUnshare(shareId = "#putBean.shareId",orgId = "#putBean.orgId",  type = ParamType.SpEl)
            public void unshare(PutBean putBean) {

            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setShareId("");
        putBean.setOrgId(1);
        
        proxy.unshare(putBean);
    }

}

