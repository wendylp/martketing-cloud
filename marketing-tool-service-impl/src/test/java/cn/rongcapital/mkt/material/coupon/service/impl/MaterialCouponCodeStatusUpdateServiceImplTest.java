/*************************************************
 * @功能简述: MaterialCouponCodeStatusUpdateService实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016-12-07
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeStatusUpdateService;
import cn.rongcapital.mkt.material.coupon.service.impl.MaterialCouponCodeStatusUpdateServiceImpl;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCodeStatusUpdateVO;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponCodeStatusUpdateServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private MaterialCouponCodeStatusUpdateService service;

    @Mock
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Before
    public void setUp() throws Exception {
        service = new MaterialCouponCodeStatusUpdateServiceImpl();
        logger.info("测试：MaterialCouponCodeStatusUpdateService 开始---------------------");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("测试：MaterialCouponCodeStatusUpdateService 结束---------------------");
    }

    /**
     * 正常Case
     */
    @Test
    public void test01() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                List<MaterialCouponCode> dataList = (List<MaterialCouponCode>) args[0];
                Assert.assertEquals(1, dataList.size());
                MaterialCouponCode data = dataList.get(0);
                Assert.assertEquals(6, data.getId().intValue());
                Assert.assertEquals("12", data.getReleaseStatus());
                Assert.assertEquals("12345", data.getUser());
                return null;
            }
        }).when(materialCouponCodeDao).batchUpdateByIdAndStatus(Mockito.any());
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        MaterialCouponCodeStatusUpdateVO vo = new MaterialCouponCodeStatusUpdateVO();
        vo.setId(6L);
        vo.setStatus("12");
        vo.setUser("12345");
        voList.add(vo);
        service.updateMaterialCouponCodeStatus(voList);
    }

    /**
     * 不传ID
     */
    @Test
    public void test02() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(materialCouponCodeDao).batchUpdateByIdAndStatus(Mockito.any());
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        MaterialCouponCodeStatusUpdateVO vo = new MaterialCouponCodeStatusUpdateVO();
        vo.setStatus("1");
        vo.setUser("12345");
        voList.add(vo);
        service.updateMaterialCouponCodeStatus(voList);
    }

    /**
     * 不传Status
     */
    @Test
    public void test03() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(materialCouponCodeDao).batchUpdateByIdAndStatus(Mockito.any());
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        MaterialCouponCodeStatusUpdateVO vo = new MaterialCouponCodeStatusUpdateVO();
        vo.setId(6L);
        vo.setUser("12345");
        voList.add(vo);
        service.updateMaterialCouponCodeStatus(voList);
    }

    /**
     * 不传User
     */
    @Test
    public void test04() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(materialCouponCodeDao).batchUpdateByIdAndStatus(Mockito.any());
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        MaterialCouponCodeStatusUpdateVO vo = new MaterialCouponCodeStatusUpdateVO();
        vo.setId(6L);
        vo.setStatus("1");
        voList.add(vo);
        service.updateMaterialCouponCodeStatus(voList);
    }

    /**
     * 整页分割
     */
    @Test
    public void testSplitList01() {
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        List<List<MaterialCouponCodeStatusUpdateVO>> expecList =
                        new ArrayList<List<MaterialCouponCodeStatusUpdateVO>>();
        List<MaterialCouponCodeStatusUpdateVO> expecListItem = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        for (int i = 0; i < MaterialCouponCodeStatusUpdateServiceImpl.BATCH_PROCESS_CNT * 2; i++) {
            MaterialCouponCodeStatusUpdateVO vo = new MaterialCouponCodeStatusUpdateVO();
            vo.setId(Long.valueOf(i + 1));
            vo.setStatus("1");
            voList.add(vo);

            if (i % MaterialCouponCodeStatusUpdateServiceImpl.BATCH_PROCESS_CNT == 0) {
                expecListItem = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
                expecListItem.add(vo);
                expecList.add(expecListItem);
            } else {
                expecListItem.add(vo);
            }
        }
        List<List<MaterialCouponCodeStatusUpdateVO>> actual =
                        ReflectionTestUtils.invokeMethod(service, "splitList", voList);
        Assert.assertEquals(2, actual.size());
        Assert.assertEquals(expecList, actual);
    }

    /**
     * 非整页分割
     */
    @Test
    public void testSplitList02() {
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        List<List<MaterialCouponCodeStatusUpdateVO>> expecList =
                        new ArrayList<List<MaterialCouponCodeStatusUpdateVO>>();
        List<MaterialCouponCodeStatusUpdateVO> expecListItem = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        for (int i = 0; i < MaterialCouponCodeStatusUpdateServiceImpl.BATCH_PROCESS_CNT * 2 + 1; i++) {
            MaterialCouponCodeStatusUpdateVO vo = new MaterialCouponCodeStatusUpdateVO();
            vo.setId(Long.valueOf(i + 1));
            vo.setStatus("1");
            voList.add(vo);

            if (i % MaterialCouponCodeStatusUpdateServiceImpl.BATCH_PROCESS_CNT == 0) {
                expecListItem = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
                expecListItem.add(vo);
                expecList.add(expecListItem);
            } else {
                expecListItem.add(vo);
            }
        }
        List<List<MaterialCouponCodeStatusUpdateVO>> actual =
                        ReflectionTestUtils.invokeMethod(service, "splitList", voList);
        Assert.assertEquals(3, actual.size());
        Assert.assertEquals(expecList, actual);
    }


    /**
     * 数据都不合法
     */
    @Test
    public void testProcessMaterialCouponCode01() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Assert.fail();
                return null;
            }
        }).when(materialCouponCodeDao).batchUpdateByIdAndStatus(Mockito.any());
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        MaterialCouponCodeStatusUpdateVO vo = new MaterialCouponCodeStatusUpdateVO();
        vo.setId(6L);
        vo.setStatus("1");
        voList.add(vo);
        ReflectionTestUtils.invokeMethod(service, "processMaterialCouponCode", voList);
    }

    /**
     * 数据部分不合法
     */
    @Test
    public void testProcessMaterialCouponCode02() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                List<MaterialCouponCode> dataList = (List<MaterialCouponCode>) args[0];
                Assert.assertEquals(1, dataList.size());
                MaterialCouponCode data = dataList.get(0);
                Assert.assertEquals(7, data.getId().intValue());
                Assert.assertEquals("2", data.getReleaseStatus());
                Assert.assertEquals("12345", data.getUser());
                return null;
            }
        }).when(materialCouponCodeDao).batchUpdateByIdAndStatus(Mockito.any());
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        MaterialCouponCodeStatusUpdateVO vo = new MaterialCouponCodeStatusUpdateVO();
        vo.setId(6L);
        vo.setStatus("1");
        voList.add(vo);
        vo = new MaterialCouponCodeStatusUpdateVO();
        vo.setId(7L);
        vo.setUser("12345");
        vo.setStatus("2");
        voList.add(vo);
        ReflectionTestUtils.invokeMethod(service, "processMaterialCouponCode", voList);
    }
    
    /**
     * 处理异常
     */
    @Test
    public void testProcessMaterialCouponCode03() {
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                throw new Exception("XXX");
            }
        }).when(materialCouponCodeDao).batchUpdateByIdAndStatus(Mockito.any());
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);
        List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        MaterialCouponCodeStatusUpdateVO vo = new MaterialCouponCodeStatusUpdateVO();
        vo.setId(6L);
        vo.setStatus("1");
        voList.add(vo);
        vo = new MaterialCouponCodeStatusUpdateVO();
        vo.setId(7L);
        vo.setUser("12345");
        vo.setStatus("2");
        voList.add(vo);
        ReflectionTestUtils.invokeMethod(service, "processMaterialCouponCode", voList);
    }

}
