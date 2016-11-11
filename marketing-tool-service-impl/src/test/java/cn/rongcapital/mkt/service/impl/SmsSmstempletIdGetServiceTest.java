package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsSmstempletIdGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SmsSmstempletIdGetOut;

@RunWith(MockitoJUnitRunner.class)
public class SmsSmstempletIdGetServiceTest {

    private SmsSmstempletIdGetService smsSmstempletIdGetService;

    @Mock
    private SmsTempletDao smsTempletDao;

    private SmsTemplet smsTemplet;

    @Before
    public void setUp() throws Exception {

        smsTemplet = new SmsTemplet(Integer.valueOf(1), "", (byte) 0, (byte) 0, (byte) 1, "sms",
                        (byte) 0, "sms", null, "user1", null, "无", "审核", new Date(),
                        "【融数金服】大伟牌模板，专注短信模板20年，质量信得过！");

        List<SmsTemplet> SmsTempletList = new ArrayList<SmsTemplet>();
        SmsTempletList.add(smsTemplet);

        smsSmstempletIdGetService = new SmsSmstempletIdGetServiceImpl();

        Mockito.when(smsTempletDao.selectList(any())).thenReturn(SmsTempletList);

        // 把mock的dao set进入service
        ReflectionTestUtils.setField(smsSmstempletIdGetService, "smsTempletDao", smsTempletDao);
    }

    /**
     * 接口：mkt.sms.smstemplet.id.get 实习方法测试
     * 
     * @param id
     * @return
     * @Date 2016-11-11
     * @author shuiyangyang
     */
    @Test
    public void testGetSmsSmstempletById() {
        BaseOutput result = smsSmstempletIdGetService.getSmsSmstempletById(smsTemplet.getId());

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(1, result.getTotal());

        SmsSmstempletIdGetOut smsSmstempletIdGetOut = new SmsSmstempletIdGetOut(smsTemplet.getId(),
                        smsTemplet.getChannelType(), smsTemplet.getType(),
                        smsTemplet.getAuditStatus(), smsTemplet.getName(),
                        smsTemplet.getAuditReason(), null, smsTemplet.getContent());
        smsSmstempletIdGetOut.setAuditTime(DateUtil.getStringFromDate(smsTemplet.getAuditTime(),
                        "yyyy-MM-dd HH:mm:ss"));
        result.getData().add(smsSmstempletIdGetOut);

        Assert.assertEquals(smsSmstempletIdGetOut, result.getData().get(0));
    }

    @After
    public void tearDown() throws Exception {}

}
