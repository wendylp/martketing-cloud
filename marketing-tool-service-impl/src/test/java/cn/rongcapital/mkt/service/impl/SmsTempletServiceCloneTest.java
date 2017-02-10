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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTempleteAuditStatusEnum;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.po.SmsTempletMaterialMap;
import cn.rongcapital.mkt.service.SmsTempletService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletCloneIn;

@RunWith(MockitoJUnitRunner.class)
public class SmsTempletServiceCloneTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    SmsTempletService smsTempletService;
    
    @Mock
    private SmsTempletDao smsTempletDao;
    
    @Mock
    private DataAuthService dataAuthService;
    
    private List<SmsTemplet> dataList;
    
    private SmsTempletCloneIn clone;
    
    @Before
    public void setUp() throws Exception {
    	logger.info("测试：SmsTempletService  smsTempletClone 准备---------------------");
    	
        smsTempletService = new SmsTempletServiceImpl();
        
        ReflectionTestUtils.setField(smsTempletService, "smsTempletDao", smsTempletDao);
        ReflectionTestUtils.setField(smsTempletService, "dataAuthService", dataAuthService);
    }

    @Test
    public void testSmsTempletClone() {
    	
    	dataList = new ArrayList<SmsTemplet>();
        SmsTemplet smsTemplet = new SmsTemplet();
        smsTemplet.setAuditStatus(NumUtil.int2OneByte(SmsTempleteAuditStatusEnum.AUDIT_STATUS_PASS.getStatusCode()));
        smsTemplet.setId(222);
        smsTemplet.setCode("code");
        smsTemplet.setContent("content");
        dataList.add(smsTemplet);
        
        SmsTempletMaterialMap stmm = new SmsTempletMaterialMap();
        stmm.setId(111l);
        stmm.setMaterialType("materialType");
        stmm.setMd_type("md_type");
        
        Mockito.when(smsTempletDao.selectList(any())).thenReturn(dataList);
        
    	Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {

				return null;
			}
		}).when(smsTempletDao).insert(any());
    	
        
    	Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {

				return null;
			}
		}).when(dataAuthService).clone("", 1l, 1l, 1l, Boolean.TRUE);
    	
    	List<Long> b= new ArrayList<>();
    	b.add(1l);
    	b.add(2l);
    	clone = new SmsTempletCloneIn();
    	clone.setId(1);
    	clone.setCreator("creator");
    	clone.setUpdateUser("updateUser");
    	clone.setOrgIds(b);
        BaseOutput result = smsTempletService.smsTempletClone(clone);
    	
        // 断言
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：SmsTempletService  smsTempletClone 结束---------------------");
    }
    
}
