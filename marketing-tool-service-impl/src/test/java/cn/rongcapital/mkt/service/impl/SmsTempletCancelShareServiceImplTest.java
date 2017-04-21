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

import java.util.ArrayList;
import java.util.List;

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
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsTempletCancelShareService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletCancelShareIn;

@RunWith(MockitoJUnitRunner.class)
public class SmsTempletCancelShareServiceImplTest {

    @Mock
    private DataAuthService dataAuthService;

    @Mock
    private SmsTempletDao smsTempletDao;

    private SmsTempletCancelShareService service;

    @Before
    public void setUp() throws Exception {
        service = new SmsTempletCancelShareServiceImpl();
    }

    @After
    public void tearDown() throws Exception {}

    /**
     * 短信模板存在
     */
    @Test
    public void testCancelShareSmsTemplet01() {
        SmsTempletCancelShareIn smsTempleCancelShareIn = new SmsTempletCancelShareIn();
        smsTempleCancelShareIn.setResourceId(10L);
        List<String> shareids = new ArrayList<String>();
        shareids.add("AAA");
        shareids.add("BBB");
        smsTempleCancelShareIn.setShareIds(shareids);

        shareids.forEach(item -> {
            Mockito.doAnswer(new Answer<Void>() {
                @Override
                public Void answer(InvocationOnMock invocation) throws Throwable {
                    return null;
                }
            }).when(dataAuthService).unshare(item);
        });
        ReflectionTestUtils.setField(service, "dataAuthService", dataAuthService);

        List<SmsTemplet> smsTempletList = new ArrayList<SmsTemplet>();
        SmsTemplet item = new SmsTemplet();
        smsTempletList.add(item);
        Mockito.when(smsTempletDao.selectList(Mockito.any())).thenReturn(smsTempletList);
        ReflectionTestUtils.setField(service, "smsTempletDao", smsTempletDao);

        BaseOutput output = service.cancelShareSmsTemplet(smsTempleCancelShareIn);
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(0, output.getTotal());
        Assert.assertEquals(0, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isEmpty(output.getData()));
    }

    /**
     * 短信模板不存在
     */
    @Test
    public void testCancelShareSmsTemplet02() {
        SmsTempletCancelShareIn smsTempleCancelShareIn = new SmsTempletCancelShareIn();
        smsTempleCancelShareIn.setResourceId(10L);
        List<String> shareids = new ArrayList<String>();
        shareids.add("AAA");
        shareids.add("BBB");
        smsTempleCancelShareIn.setShareIds(shareids);

        Mockito.when(smsTempletDao.selectList(Mockito.any())).thenReturn(null);
        ReflectionTestUtils.setField(service, "smsTempletDao", smsTempletDao);

        BaseOutput output = service.cancelShareSmsTemplet(smsTempleCancelShareIn);
        Assert.assertEquals(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), output.getMsg());
        Assert.assertEquals(0, output.getTotal());
        Assert.assertEquals(0, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isEmpty(output.getData()));
    }

}
