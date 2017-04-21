package cn.rongcapital.mkt.material.coupon.service.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.material.coupon.service.CouponSaveService;
import cn.rongcapital.mkt.material.coupon.vo.in.MaterialCouponStockTotalIn;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class CouponSaveServiceBBXTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private CouponSaveService couponSaveService;
    
    @Mock
    private MQTopicService mqTopicService;
    @Before
    public void setUp() throws Exception {
        logger.info("测试: SegmentCalcServiceTest 开始---------------------");
        couponSaveService = new CouponSaveServiceImpl();
        ReflectionTestUtils.setField(couponSaveService, "mqTopicService", mqTopicService);
    }
    
    @Test
    public void test01() {
    	MaterialCouponStockTotalIn in = new MaterialCouponStockTotalIn();
    	in.setId(1L);
    	in.setStockTotal(20);
    	BaseOutput result = couponSaveService.saveForBBX(in);
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：SegmentCalcServiceTest 结束---------------------");
    }

}