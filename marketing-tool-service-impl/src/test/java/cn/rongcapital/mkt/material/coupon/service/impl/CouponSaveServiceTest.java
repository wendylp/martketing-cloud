package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.CouponSaveService;
import cn.rongcapital.mkt.vo.in.CouponInfoIn;

public class CouponSaveServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private CouponSaveService couponSaveService;
    
    @Mock
    private MaterialCouponDao materialCouponDao;
    
    @Mock
    private MaterialCouponCodeDao materialCouponCodeDao;
    
    @Before
    public void setUp() throws Exception {
        logger.info("测试: SegmentCalcServiceTest 开始---------------------");
        couponSaveService = new CouponSaveServiceImpl();
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：SegmentCalcServiceTest 结束---------------------");
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
        ReflectionTestUtils.setField(couponSaveService, "materialCouponDao", materialCouponDao);
        ReflectionTestUtils.setField(couponSaveService, "materialCouponCodeDao", materialCouponCodeDao);
        CouponInfoIn in =new CouponInfoIn();
//        in.setAmount(5);
//        vo.setId(6L);
//        vo.setStatus("12");
//        vo.setUser("12345");
//        voList.add(vo);
//        couponSaveService.save(couponInfo, user_token);
    }
    
}
