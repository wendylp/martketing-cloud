package cn.rongcapital.mkt.bbx.service.impl;

import cn.rongcapital.mkt.bbx.po.BbxCouponCodeAdd;
import cn.rongcapital.mkt.bbx.po.TBBXMember;
import cn.rongcapital.mkt.bbx.service.BbxCouponCodeAddService;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.*;
import cn.rongcapital.mkt.dao.bbx.BbxCouponCodeAddDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeCheckService;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCodeStatusUpdateVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-4-14
 * @date(最后修改日期)：2017-4-14
 * @复审人：
 *************************************************/

@RunWith(MockitoJUnitRunner.class)
public class BbxCouponCodeAddServiceImplTest {

    private BbxCouponCodeAddService service;

    @Mock
    private MaterialCouponCodeDao materialCouponCodeDao;
    @Mock
    private MaterialCouponDao materialCouponDao;
    @Mock
    private BbxCouponCodeAddDao bbxCouponCodeAddDao;

    @Mock
    private MaterialCouponCodeCheckService codeVerifyService;

    @Mock
    private MongoTemplate mongoTemplate;


    @Before
    public void setUp() throws Exception {
        service = new BbxCouponCodeAddServiceImpl();

        MaterialCouponCode code = new MaterialCouponCode();
        code.setCode("123456");
        code.setCouponId(1l);
        code.setCreateTime(new Date());
        code.setId(1l);
        code.setReadyStatus(MaterialCouponReadyStatusType.READY.getCode());
        code.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.RELEASED.getCode());
        code.setStatus(Byte.valueOf("0"));
        code.setUpdateTime(new Date());
        code.setUser("13888888888");
        code.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
        code.setVerifyTime(null);

        List<MaterialCouponCode> codes  = new ArrayList<>();
        codes.add(code);

        Mockito.when(materialCouponCodeDao.selectList(Mockito.any(MaterialCouponCode.class))).thenReturn(codes);
        ReflectionTestUtils.setField(service, "materialCouponCodeDao", materialCouponCodeDao);

        MaterialCoupon coupon = new MaterialCoupon();
        coupon.setId(1l);
        coupon.setAmount(new BigDecimal("20.00"));
        coupon.setChannelCode(MaterialCouponChannelCodeEnum.SMS.getCode());
        coupon.setCouponStatus(MaterialCouponStatusEnum.RELEASED.getCode());
        coupon.setCreateTime(new Date());
        coupon.setEndTime(new Date());
        coupon.setReadyStatus(MaterialCouponReadyStatusType.READY.getCode());
        coupon.setRule("");
        coupon.setSourceCode(MaterialCouponSourceCodeEnum.COMMON.getCode());
        coupon.setStartTime(new Date());
        coupon.setStockRest(1);
        coupon.setStockTotal(1);
        coupon.setTaskId(1l);
        coupon.setTaskName("测试短信任务");
        coupon.setUpdateTime(new Date());
        List<MaterialCoupon> coupons = new ArrayList<>();
        coupons.add(coupon);

        Mockito.when(materialCouponDao.selectList(Mockito.any(MaterialCoupon.class))).thenReturn(coupons);
        ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
        ReflectionTestUtils.setField(service, "bbxCouponCodeAddDao", bbxCouponCodeAddDao);
        BaseOutput successResult = new BaseOutput(
                ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);
        Mockito.when(this.codeVerifyService.materialCouponCodeVerify(Mockito.anyLong(),Mockito.anyString(),Mockito.anyString())).thenReturn(successResult);
        ReflectionTestUtils.setField(service, "codeVerifyService", codeVerifyService);
        TBBXMember member = new TBBXMember();
        member.setMemberid(34350);
        Mockito.when(this.mongoTemplate.findOne(Mockito.any(),Mockito.any())).thenReturn(member);
        ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addCouponCodeToBBX() throws Exception {

        List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<>();
        MaterialCouponCodeStatusUpdateVO vo = new MaterialCouponCodeStatusUpdateVO();
        vo.setId(1l);
        vo.setStatus("0");
        vo.setUser("13888888888");
        voList.add(vo);
        service.addCouponCodeToBBX(voList);
        Mockito.verify(bbxCouponCodeAddDao,Mockito.times(1)).batchInsert(Mockito.anyList());
    }
    @Test
    public void verifyCouponCode(){
        this.service.verifyCouponCode();
//        Mockito.verify(this.codeVerifyService,Mockito.atLeast(1)).materialCouponCodeVerify(Mockito.anyLong(),Mockito.anyString(),Mockito.anyString());
    }

}