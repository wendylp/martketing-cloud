/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的） 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-02-07
 * @date(最后修改日期)：2017-02-07
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.dataauth.interceptor;

import org.junit.After;
import org.junit.Assert;
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

        // Mockito.doNothing().when(dataAuthService).put(Mockito.any(long.class),
        // Mockito.any(String.class),
        // Mockito.any(long.class));
        // ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);
    }

    public class PutBeanService {
        public ReturnBean save(PutBean putBean) {
            return null;
        }
    }
    public class PutBean {
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

    public class ReturnBean {

        private Long outputId;

        public Long getOutputId() {
            return outputId;
        }

        public void setOutputId(Long outputId) {
            this.outputId = outputId;
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

    /**
     * @功能描述: 传入参数resource_id不为空的情况（表达式为SPEL）
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @Test
    public void test() throws Throwable {

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class), Mockito.any(long.class));

        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", resourceId = "#putBean.resourceId", orgId = "#putBean.orgId", outputResourceId = "outputId", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                ReturnBean ret = new ReturnBean();
                ret.setOutputId(99L);
                return ret;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);


        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802L);
        putBean.setOrgId(1L);
        proxy.save(putBean);

        Mockito.verify(dataAuthService, Mockito.times(0)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }


    /**
     * @功能描述: 传入参数resource_id为空的情况（表达式为SPEL）
     * @throws java.lang.Exception void
     * @author zhuxuelong
     * @since 2017-02-07
     */
    @Test
    public void testResource_IDISNULL() throws Throwable {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Long arg0 = (Long) args[0];
                Assert.assertEquals(1, arg0.intValue());
                String arg1 = (String) args[1];
                Assert.assertEquals("business", arg1);
                Long arg2 = (Long) args[2];
                Assert.assertEquals(99, arg2.intValue());
                return null;
            }
        }).when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class), Mockito.any(long.class));

        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", resourceId = "#putBean.resourceId", orgId = "#putBean.orgId", outputResourceId = "outputId", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                ReturnBean ret = new ReturnBean();
                ret.setOutputId(99L);
                return ret;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setOrgId(1L);
        proxy.save(putBean);

        Mockito.verify(dataAuthService, Mockito.times(1)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }

    /**
     * @功能描述: 传入参数resource_id为0的情况（表达式为SPEL）
     * @throws java.lang.Exception void
     * @author zhuxuelong
     * @since 2017-02-07
     */
    @Test
    public void testResource_IDIS0() throws Throwable {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Long arg0 = (Long) args[0];
                Assert.assertEquals(1, arg0.intValue());
                String arg1 = (String) args[1];
                Assert.assertEquals("business", arg1);
                Long arg2 = (Long) args[2];
                Assert.assertEquals(99, arg2.intValue());
                return null;
            }
        }).when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class), Mockito.any(long.class));

        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", resourceId = "#putBean.resourceId", orgId = "#putBean.orgId", outputResourceId = "outputId", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                ReturnBean ret = new ReturnBean();
                ret.setOutputId(99L);
                return ret;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceId(0L);
        putBean.setResourceType("business");
        putBean.setOrgId(1L);
        proxy.save(putBean);

        Mockito.verify(dataAuthService, Mockito.times(1)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }
    
    /**
     * @功能描述: 声明没有元素resource_id的情况（表达式为SPEL）
     * @throws java.lang.Exception void
     * @author zhuxuelong
     * @since 2017-02-07
     */
    @Test
    public void testNoResource_ID() throws Throwable {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Long arg0 = (Long) args[0];
                Assert.assertEquals(1, arg0.intValue());
                String arg1 = (String) args[1];
                Assert.assertEquals("business", arg1);
                Long arg2 = (Long) args[2];
                Assert.assertEquals(99, arg2.intValue());
                return null;
            }
        }).when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class), Mockito.any(long.class));

        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", orgId = "#putBean.orgId", outputResourceId = "outputId", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                ReturnBean ret = new ReturnBean();
                ret.setOutputId(99L);
                return ret;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setOrgId(1L);
        proxy.save(putBean);

        Mockito.verify(dataAuthService, Mockito.times(1)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }
    

    /**
     * @功能描述: 传入参数resource_id不为空的情况（表达式为Default）
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @Test
    public void testParamDefault() throws Throwable {

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class), Mockito.any(long.class));

        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", resourceId = "98", orgId = "1", outputResourceId = "98", type = ParamType.Default)
            public ReturnBean save(PutBean putBean) {
                return null;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(98L);
        putBean.setOrgId(1L);
        proxy.save(putBean);

        Mockito.verify(dataAuthService, Mockito.times(0)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }

    /**
     * @功能描述: 传入参数resource_id为空的情况（表达式为Default）
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @Test
    public void testParamDefault01() throws Throwable {

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Long arg0 = (Long) args[0];
                Assert.assertEquals(1, arg0.intValue());
                String arg1 = (String) args[1];
                Assert.assertEquals("business", arg1);
                Long arg2 = (Long) args[2];
                Assert.assertEquals(98, arg2.intValue());
                return null;
            }
        }).when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class), Mockito.any(long.class));

        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", resourceId = "0", orgId = "1", outputResourceId = "98", type = ParamType.Default)
            public ReturnBean save(PutBean putBean) {
                ReturnBean ret = new ReturnBean();
                ret.setOutputId(99L);
                return ret;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(0L);
        putBean.setOrgId(1L);
        proxy.save(putBean);

        Mockito.verify(dataAuthService, Mockito.times(1)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }

    @Test
    public void testNoSuchElementException01() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "", resourceId = "#putBean.resourceId", orgId = "#putBean.orgId", outputResourceId = "#putBean.orgId", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                return null;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802L);
        putBean.setOrgId(1L);
        try {
            proxy.save(putBean);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("resourceType expression is illegal.", e.getMessage());
        }
    }
    
    @Test
    public void testNoSuchElementException02() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "abc", resourceId = "#putBean.resourceId", orgId = "", outputResourceId = "#putBean.orgId", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                return null;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802L);
        putBean.setOrgId(1L);
        try {
            proxy.save(putBean);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("orgId expression is illegal.", e.getMessage());
        }
    }
    
    @Test
    public void testNoSuchElementException03() throws Throwable {

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "abc", resourceId = "#putBean.resourceId", orgId = "#putBean.orgId", outputResourceId = "", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                return null;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802L);
        putBean.setOrgId(1L);
        try {
            proxy.save(putBean);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("outputResourceId expression is illegal.", e.getMessage());
        }
    }

    /**
     * @功能描述: 没有返回值的情况
     * @throws java.lang.Exception void
     * @author zhuxuelong
     * @since 2017-02-07
     */
    @Test
    public void testNoReturn() throws Throwable {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class), Mockito.any(long.class));

        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", orgId = "#putBean.orgId", outputResourceId = "outputId", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                return null;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setOrgId(1L);
        try {
            proxy.save(putBean);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("business method has no output.", e.getMessage());
        }

        Mockito.verify(dataAuthService, Mockito.times(0)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }


    /**
     * @功能描述: 输出资源ID不合法的情况
     * @throws java.lang.Exception void
     * @author zhuxuelong
     * @since 2017-02-07
     */
    @Test
    public void testReturnISNULL() throws Throwable {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class), Mockito.any(long.class));

        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", orgId = "#putBean.orgId", outputResourceId = "outputId", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                ReturnBean ret = new ReturnBean();
                return ret;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setOrgId(1L);
        try {
            proxy.save(putBean);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("calculate generated resource_id from expression is illegal.", e.getMessage());
        }

        Mockito.verify(dataAuthService, Mockito.times(0)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }

    /**
     * @功能描述: 输出资源ID不合法的情况
     * @throws java.lang.Exception void
     * @author zhuxuelong
     * @since 2017-02-07
     */
    @Test
    public void testReturnIS0() throws Throwable {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class), Mockito.any(long.class));

        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", orgId = "#putBean.orgId", outputResourceId = "outputId", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                ReturnBean ret = new ReturnBean();
                ret.setOutputId(0L);
                return ret;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);

        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setOrgId(1L);
        try {
            proxy.save(putBean);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("calculate generated resource_id from expression is illegal.", e.getMessage());
        }

        Mockito.verify(dataAuthService, Mockito.times(0)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }
    
    /**
     * @功能描述: 解析的orgid为空的情况（表达式为SPEL）
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @Test
    public void testGetOrgidIsNULL() throws Throwable {

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class), Mockito.any(long.class));

        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", orgId = "#putBean.orgId", outputResourceId = "outputId", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                ReturnBean ret = new ReturnBean();
                ret.setOutputId(99L);
                return ret;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);


        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802L);
        try {
            proxy.save(putBean);
        } catch (Exception e) {
            Assert.assertEquals("calculate orgId from expression is illegal.", e.getMessage());
        }

        Mockito.verify(dataAuthService, Mockito.times(0)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }
    
    /**
     * @功能描述: 解析的orgid0的情况（表达式为SPEL）
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2017-02-07
     */
    @Test
    public void testGetOrgidIs0() throws Throwable {

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(dataAuthService).put(Mockito.any(long.class), Mockito.any(String.class), Mockito.any(long.class));

        ReflectionTestUtils.setField(aspect, "dataAuthService", dataAuthService);

        AspectJProxyFactory factory = new AspectJProxyFactory(new PutBeanService() {
            @Override
            @DataAuthPut(resourceType = "business", orgId = "#putBean.orgId", outputResourceId = "outputId", type = ParamType.SpEl)
            public ReturnBean save(PutBean putBean) {
                ReturnBean ret = new ReturnBean();
                ret.setOutputId(99L);
                return ret;
            }
        });
        factory.setProxyTargetClass(true);
        factory.addAspect(aspect);


        PutBeanService proxy = factory.getProxy();
        PutBean putBean = new PutBean();
        putBean.setResourceType("business");
        putBean.setResourceId(802L);
        putBean.setOrgId(0L);
        try {
            proxy.save(putBean);
        } catch (Exception e) {
            Assert.assertEquals("calculate orgId from expression is illegal.", e.getMessage());
        }

        Mockito.verify(dataAuthService, Mockito.times(0)).put(Mockito.any(long.class), Mockito.any(String.class),
                Mockito.any(long.class));
    }

}
