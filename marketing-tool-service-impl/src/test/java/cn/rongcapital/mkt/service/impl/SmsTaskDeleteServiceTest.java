/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

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
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.service.SmsTaskDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsTaskDeleteIn;

/**
 * @author shuiyangyang
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SmsTaskDeleteServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    SmsTaskDeleteService smsTaskDeleteService;
    
    @Mock
    private SmsTaskHeadDao smsTaskHeadDao;
    
    @Mock
    private SmsTaskBodyDao smsTaskBodyDao;
    
    private List<SmsTaskBody> smsTaskBodyLists;
    
    
    
    @Before
    public void setUp() throws Exception {

        smsTaskBodyLists = new ArrayList<SmsTaskBody>();
        smsTaskBodyLists.add(new SmsTaskBody());
        smsTaskBodyLists.add(new SmsTaskBody());

        Mockito.when(smsTaskHeadDao.updateById(any())).thenReturn(1);
        Mockito.when(smsTaskBodyDao.selectList(any())).thenReturn(smsTaskBodyLists);
        
        smsTaskDeleteService = new SmsTaskDeleteServiceImpl();

        // 把mock的dao set进入service
        ReflectionTestUtils.setField(smsTaskDeleteService, "smsTaskHeadDao", smsTaskHeadDao);
        ReflectionTestUtils.setField(smsTaskDeleteService, "smsTaskBodyDao", smsTaskBodyDao);
    }

    @Test
    public void testmessageSendRecordGet() {

        BaseOutput result = smsTaskDeleteService.smsTaskDelete(new SmsTaskDeleteIn(), null);

        // 断言
        Assert.assertEquals(result.getCode(), ApiErrorCode.SUCCESS.getCode());
        Assert.assertEquals(result.getTotal(), 1);
    }


    @After
    public void tearDown() throws Exception {}

}
