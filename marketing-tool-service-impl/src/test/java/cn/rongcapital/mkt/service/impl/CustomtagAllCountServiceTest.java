package cn.rongcapital.mkt.service.impl;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.service.CustomtagAllCountService;
import cn.rongcapital.mkt.vo.BaseOutput;
@RunWith(MockitoJUnitRunner.class)
public class CustomtagAllCountServiceTest {

    @Autowired
    CustomtagAllCountService customtagAllCountService;
    
    @Mock
    private MongoCustomTagDao mongoCustomTagDao;
    
    private long count = 10;
    
    @Before
    public void setUp() throws Exception {
        Mockito.when(mongoCustomTagDao.countAll()).thenReturn(count);
        
        customtagAllCountService = new CustomtagAllCountServiceImpl();
        
        ReflectionTestUtils.setField(customtagAllCountService, "mongoCustomTagDao", mongoCustomTagDao);
    }
    
    @Test
    public void testCustomtagAllCount() {
        BaseOutput baseOutput = customtagAllCountService.customtagAllCount();
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), baseOutput.getCode());
        
        Assert.assertEquals(count, ((Map<String,Object>)baseOutput.getData().get(0)).get("count"));
        
    }

    @After
    public void tearDown() throws Exception {}



}
