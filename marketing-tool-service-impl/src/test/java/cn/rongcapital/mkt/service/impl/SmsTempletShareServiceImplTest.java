/*************************************************
 * @功能简述: 短信模板分享测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/2/4
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.service.SmsTempletShareService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletShareIn;

@RunWith(MockitoJUnitRunner.class)
public class SmsTempletShareServiceImplTest {

    @Mock
    private DataAuthService dataAuthService;

    private SmsTempletShareService service;

    @Before
    public void setUp() throws Exception {
        service = new SmsTempletShareServiceImpl();
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testShareSmsTemplet() {
        SmsTempletShareIn smsTempletShareIn = new SmsTempletShareIn();
        smsTempletShareIn.setFromOrgId(11L);
        smsTempletShareIn.setToOrgId(10L);
        smsTempletShareIn.setResourceId(12L);
        smsTempletShareIn.setWriteable(true);
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        })
                .when(dataAuthService)
                .share("sms_templet", smsTempletShareIn.getResourceId(), smsTempletShareIn.getFromOrgId(),
                        smsTempletShareIn.getToOrgId(), smsTempletShareIn.getWriteable());
        ReflectionTestUtils.setField(service, "dataAuthService", dataAuthService);

        BaseOutput output = service.shareSmsTemplet(smsTempletShareIn);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(1, output.getTotal());
        Assert.assertEquals(1, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isEmpty(output.getData()));
    }

}
