/*************************************************
 * @功能简述: MaterialCouponAudienceCreateService实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date:
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponAudienceCreateService;
import cn.rongcapital.mkt.service.AudienceListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponAudienceCreateServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private MaterialCouponAudienceCreateService mcacService;

    @Mock
    MaterialCouponDao materialCouponDao;

    @Mock
    MaterialCouponCodeDao materialCouponCodeDao;

    @Mock
    AudienceListService audienceListService;

    List<String> mobileList = new ArrayList<>();

    List<MaterialCoupon> mc = new ArrayList<MaterialCoupon>();

    @Before
    public void setUp() throws Exception {
        logger.info("测试：MaterialCouponVerifyGeneralService 准备---------------------");

        mcacService = new MaterialCouponAudienceCreateServiceImpl();

        String tempMobile = "";
        for (int a = 0; a < 6; a++) {
            tempMobile = "1860000000" + a;
            mobileList.add(tempMobile);
        }

        MaterialCoupon tempMc = new MaterialCoupon();
        tempMc.setTaskId(10l);
        mc.add(tempMc);

        ReflectionTestUtils.setField(mcacService, "materialCouponDao", materialCouponDao);
        ReflectionTestUtils.setField(mcacService, "materialCouponCodeDao", materialCouponCodeDao);
        ReflectionTestUtils.setField(mcacService, "audienceListService", audienceListService);
    }

    @Test
    public void testCreateTargetAudienceGroup01() {
        logger.info("测试方法: testCreateTargetAudienceGroup01 start");

        Mockito.when(materialCouponCodeDao.getCouponCodeVerifyUserInfoList(any())).thenReturn(mobileList);
        Mockito.when(materialCouponDao.selectListByIdList(any())).thenReturn(mc);
        Mockito.when(audienceListService.saveAudienceByMobile(any(), any(), any())).thenReturn(true);

        BaseOutput result0 = mcacService.createTargetAudienceGroup(0l, "audienceName", "blurSearch", "releaseStatus",
                "verifyStatus", "expireStatus");

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result0.getCode());
        logger.info("测试方法: testCreateTargetAudienceGroup01 end");
    }

    @Test
    public void testCreateTargetAudienceGroup02() {
        logger.info("测试方法: testCreateTargetAudienceGroup02 start");

        Mockito.when(materialCouponCodeDao.getCouponCodeVerifyUserInfoList(any())).thenReturn(null);
        Mockito.when(materialCouponDao.selectListByIdList(any())).thenReturn(mc);
        Mockito.when(audienceListService.saveAudienceByMobile(any(), any(), any())).thenReturn(false);

        BaseOutput result0 = mcacService.createTargetAudienceGroup(0l, "audienceName", "blurSearch", "releaseStatus",
                "verifyStatus", "expireStatus");
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CREATE_AUDIENCE_FAILED.getCode(), result0.getCode());
        logger.info("测试方法: testCreateTargetAudienceGroup02 end");
    }

    @Test
    public void testCreateTargetAudienceGroup03() {
        logger.info("测试方法: testCreateTargetAudienceGroup03 start");

        Mockito.when(materialCouponCodeDao.getCouponCodeVerifyUserInfoList(any())).thenReturn(new ArrayList<>());
        Mockito.when(materialCouponDao.selectListByIdList(any())).thenReturn(null);
        Mockito.when(audienceListService.saveAudienceByMobile(any(), any(), any())).thenReturn(true);

        BaseOutput result0 = mcacService.createTargetAudienceGroup(0l, "audienceName", "blurSearch", "releaseStatus",
                "verifyStatus", "expireStatus");
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CREATE_AUDIENCE.getCode(),
                result0.getCode());
        logger.info("测试方法: testCreateTargetAudienceGroup03 end");
    }
    
    @Test
    public void testCreateTargetAudienceGroup04() {
        logger.info("测试方法: testCreateTargetAudienceGroup04 start");

        Mockito.when(materialCouponCodeDao.getCouponCodeVerifyUserInfoList(any())).thenReturn(new ArrayList<>());
        Mockito.when(materialCouponDao.selectListByIdList(any())).thenReturn(new ArrayList<MaterialCoupon>());
        Mockito.when(audienceListService.saveAudienceByMobile(any(), any(), any())).thenReturn(true);

        BaseOutput result0 = mcacService.createTargetAudienceGroup(0l, "audienceName", "blurSearch", "releaseStatus",
                "verifyStatus", "expireStatus");
        Assert.assertEquals(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CREATE_AUDIENCE.getCode(),
                result0.getCode());
        logger.info("测试方法: testCreateTargetAudienceGroup04 end");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("测试：MaterialCouponVerifyGeneralService 结束---------------------");
    }

}
