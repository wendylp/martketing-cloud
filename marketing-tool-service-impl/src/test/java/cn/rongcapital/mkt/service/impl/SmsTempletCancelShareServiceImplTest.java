/*************************************************
 * @功能简述: 取消短信模板分享测试类
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
import cn.rongcapital.mkt.service.SmsTempletCancelShareService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletCancelShareIn;

@RunWith(MockitoJUnitRunner.class)
public class SmsTempletCancelShareServiceImplTest {

    @Mock
    private DataAuthService dataAuthService;

    private SmsTempletCancelShareService service;

    @Before
    public void setUp() throws Exception {
        service = new SmsTempletCancelShareServiceImpl();
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testCancelShareSmsTemplet() {
        SmsTempletCancelShareIn smsTempleCancelShareIn = new SmsTempletCancelShareIn();
        smsTempleCancelShareIn.setOrgId(9L);
        smsTempleCancelShareIn.setShareId("DDDDD");
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(dataAuthService).unshare(smsTempleCancelShareIn.getShareId());
        ReflectionTestUtils.setField(service, "dataAuthService", dataAuthService);

        BaseOutput output = service.cancelShareSmsTemplet(smsTempleCancelShareIn);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(0, output.getTotal());
        Assert.assertEquals(0, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isEmpty(output.getData()));
    }

}
