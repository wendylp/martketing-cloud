package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

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
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsTempletService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletIn;

@RunWith(MockitoJUnitRunner.class)
public class SmsTempletServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    SmsTempletService smsTempletService;
    
    @Mock
    private SmsTempletDao smsTempletDao;
    
    private int selectListCountResult = 10;
    
    private int insertResult = 1;
    
    private List<SmsTemplet> dataList;
    
    @Before
    public void setUp() throws Exception {
        
        dataList = new ArrayList<SmsTemplet>();
        SmsTemplet smsTemplet = new SmsTemplet();
        dataList.add(smsTemplet);
        dataList.add(smsTemplet);
        
        Mockito.when(smsTempletDao.selectListCount(any())).thenReturn(selectListCountResult);
        Mockito.when(smsTempletDao.selectList(any())).thenReturn(dataList);
        
        Mockito.when(smsTempletDao.insert(any())).thenReturn(insertResult);
        smsTempletService = new SmsTempletServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(smsTempletService, "smsTempletDao", smsTempletDao);
    }

    @Test
    public void testSmsTempletList() {
        BaseOutput result = smsTempletService.smsTempletList("111", 0, 10, 1, 1, "Hello Wrold!");
        
        // 断言
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(selectListCountResult, result.getTotalCount());
        Assert.assertEquals(dataList, result.getData());
    }
    
    @Test
    public void testInsertSmsTemplet() {
        SmsTempletIn smsTempletIn = new SmsTempletIn();
        BaseOutput result = smsTempletService.insertSmsTemplet(smsTempletIn);
        
        // 断言
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
    }

}
