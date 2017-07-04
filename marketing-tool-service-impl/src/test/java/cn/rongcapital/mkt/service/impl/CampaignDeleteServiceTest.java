/*************************************************
 * @功能简述: Service实现测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;


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
import cn.rongcapital.mkt.dao.CampaignActionSendSmsDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.po.CampaignActionSendSms;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.service.CampaignDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class CampaignDeleteServiceTest  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private CampaignDeleteService campaignDeleteService;
    
    @Mock
    private CampaignHeadDao campaignHeadDao;
    
    @Mock
	private CampaignActionSendSmsDao campaignActionSendSmsDao;
	
    @Mock
	private SmsMaterialDao smsMaterialDao;
    
    List<CampaignHead> list;
    
    List<CampaignActionSendSms> listCAS;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试：CampaignDeleteService 准备---------------------");
        campaignDeleteService = new CampaignDeleteServiceImpl();
        
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(campaignDeleteService, "campaignHeadDao", campaignHeadDao);
        ReflectionTestUtils.setField(campaignDeleteService, "campaignActionSendSmsDao", campaignActionSendSmsDao);
        ReflectionTestUtils.setField(campaignDeleteService, "smsMaterialDao", smsMaterialDao);

    }
    
    
    /**
     * happy flow
     */
    @Test
    public void testCampaignDelete() {
        logger.info("测试方法: campaignDelete ");
        CampaignHead ch = new CampaignHead();
        ch.setPublishStatus((byte)0);
        list = new ArrayList<CampaignHead>();
        list.add(ch);
        
        CampaignActionSendSms cass = new CampaignActionSendSms();
        cass.setSmsMaterialId(1);
        listCAS = new ArrayList<CampaignActionSendSms>();
        listCAS.add(cass);
        
        Mockito.when(campaignHeadDao.updateById(Mockito.any())).thenReturn(1);
        Mockito.when(campaignHeadDao.selectListByIdListFromBasic(Mockito.any())).thenReturn(list);
        Mockito.when(campaignActionSendSmsDao.selectList(Mockito.any())).thenReturn(listCAS);
        
        BaseOutput result = campaignDeleteService.campaignDelete(Mockito.any());
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
    }
    
    /**
     * campaign 不存在
     */
    @Test
    public void testCampaignDelete2() {
        
        Mockito.when(campaignHeadDao.updateById(Mockito.any())).thenReturn(0);
        
        BaseOutput result = campaignDeleteService.campaignDelete(Mockito.any());
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
    }
    
    /**
     * campaign 结束
     */
    @Test
    public void testCampaignDelete3() {
        
    	CampaignHead ch = new CampaignHead();
        ch.setPublishStatus((byte)2);
        list = new ArrayList<CampaignHead>();
        list.add(ch);
        
        Mockito.when(campaignHeadDao.updateById(Mockito.any())).thenReturn(1);
        Mockito.when(campaignHeadDao.selectListByIdListFromBasic(Mockito.any())).thenReturn(list);
        
        BaseOutput result = campaignDeleteService.campaignDelete(Mockito.any());
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
    }
    
    /**
     * campaign 没有短信节点
     */
    @Test
    public void testCampaignDelete4() {
        
    	CampaignHead ch = new CampaignHead();
        ch.setPublishStatus((byte)0);
        list = new ArrayList<CampaignHead>();
        list.add(ch);
        
        CampaignActionSendSms cass = new CampaignActionSendSms();
        cass.setSmsMaterialId(1);
        
        Mockito.when(campaignHeadDao.updateById(Mockito.any())).thenReturn(1);
        Mockito.when(campaignHeadDao.selectListByIdListFromBasic(Mockito.any())).thenReturn(list);
        Mockito.when(campaignActionSendSmsDao.selectList(Mockito.any())).thenReturn(listCAS);
        
        BaseOutput result = campaignDeleteService.campaignDelete(Mockito.any());
        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
    }
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试：CampaignDeleteService 结束---------------------");
    }

}


