package cn.rongcapital.mkt.material.coupon.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;

import com.alibaba.fastjson.JSONObject;

@RunWith(MockitoJUnitRunner.class)
public class CouponCodeSaveTaskBBXImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private CouponCodeSaveTaskBBXImpl couponCodeSaveTaskBBXImpl;
    
    @Mock
    MaterialCouponDao materialCouponDao;

    @Mock
    MaterialCouponCodeDao materialCouponCodeDao;
    
    MaterialCoupon coupon = new MaterialCoupon();
    
    JSONObject json = new JSONObject();
    
    @Before
    public void setUp(){
        logger.info("测试：CouponCodeSaveTaskImplTest 准备---------------------");
        couponCodeSaveTaskBBXImpl = new CouponCodeSaveTaskBBXImpl();
        ReflectionTestUtils.setField(couponCodeSaveTaskBBXImpl, "materialCouponDao", materialCouponDao);
        ReflectionTestUtils.setField(couponCodeSaveTaskBBXImpl, "materialCouponCodeDao", materialCouponCodeDao);
        coupon.setId(426L);
        coupon.setAmount(new BigDecimal(5));
        coupon.setRule("{\"type_code\":\"mixed\",\"length\":5}");
        coupon.setTitle("新MC优惠券");
        coupon.setSourceCode("2");
        coupon.setStockTotal(1);
        Date now = new Date();
        coupon.setStartTime(now);
        coupon.setEndTime(now);
        coupon.setChannelCode("sms");
        
        json.put("stock_total", 5);
        json.put("id", coupon.getId());
    }
    
    @Test
    public void testCreateMixedCode(){
        Mockito.when(materialCouponDao.selectOneCoupon(426L)).thenReturn(coupon);
        couponCodeSaveTaskBBXImpl.task(json.toString());
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：CouponCodeSaveTaskImplTest 结束---------------------");
    }
}
