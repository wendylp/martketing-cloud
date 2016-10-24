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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SmsTaskHeadService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author yinheng
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SmsTaskHeadServiceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	SmsTaskHeadService smsTaskHeadService;
	
	@Mock
	private SmsTaskHeadDao smsTaskHeadDao;
	@Mock
	private MQTopicService mqTopicService;
	
	private List<SmsTaskHead> smsTaskHeads;
	
	
    @Before
    public void setUp() throws Exception {
    	smsTaskHeads = new ArrayList<SmsTaskHead>();
    	for(int i=0;i<=10;i++){
    		SmsTaskHead smsTaskHead = this.setSmsTaskHead(i);
    		if(smsTaskHead!=null){
    			smsTaskHeads.add(smsTaskHead);
    		}    		
    	}
        Mockito.when(smsTaskHeadDao.selectList(any())).thenReturn(smsTaskHeads);
        Mockito.when(smsTaskHeadDao.selectListCount(any())).thenReturn(smsTaskHeads.size());
    	
    	smsTaskHeadService = new SmsTaskHeadServiceImpl();
    	// 把mock的dao set进入service
    	ReflectionTestUtils.setField(smsTaskHeadService, "smsTaskHeadDao", smsTaskHeadDao);
    	ReflectionTestUtils.setField(smsTaskHeadService, "mqTopicService", mqTopicService);
    }
    
    private SmsTaskHead setSmsTaskHead(int sms_task_status){
    	SmsTaskHead smsTaskHead = null;
    	switch(sms_task_status%5){
			case 0:{ 
		    	smsTaskHead = new SmsTaskHead();
		    	smsTaskHead.setId(Long.parseLong(String.valueOf(sms_task_status+1)));
		    	smsTaskHead.setSmsTaskName("测试任务"+sms_task_status);
		    	smsTaskHead.setCreateTime(new Date());
		    	smsTaskHead.setSmsTaskAppType(sms_task_status%2);
		    	smsTaskHead.setSmsTaskTemplateId(Long.parseLong(String.valueOf(sms_task_status+1)));
		    	smsTaskHead.setSmsTaskStatus(sms_task_status);
				smsTaskHead.setTotalCoverNum(sms_task_status+1000);
		    	smsTaskHead.setSendingFailNum(0);
		    	smsTaskHead.setSendingSuccessNum(0);
		    	smsTaskHead.setWaitingNum(1000+sms_task_status);
		    	smsTaskHead.setAudienceGenerateStatus(0);
				break; 
				}
			case 2:{ 
		    	smsTaskHead = new SmsTaskHead();
		    	smsTaskHead.setId(Long.parseLong(String.valueOf(sms_task_status+1)));
		    	smsTaskHead.setSmsTaskName("测试任务"+sms_task_status);
		    	smsTaskHead.setCreateTime(new Date());
		    	smsTaskHead.setSmsTaskAppType(sms_task_status%2);
		    	smsTaskHead.setSmsTaskTemplateId(Long.parseLong(String.valueOf(sms_task_status+1)));
		    	smsTaskHead.setSmsTaskStatus(sms_task_status);
		    	smsTaskHead.setTotalCoverNum(sms_task_status+1000);
		    	smsTaskHead.setSendingFailNum(500);
		    	smsTaskHead.setSendingSuccessNum(300);
		    	smsTaskHead.setWaitingNum(200+sms_task_status);
				break; 
				}
			case 4:{ 
		    	smsTaskHead = new SmsTaskHead();
		    	smsTaskHead.setId(Long.parseLong(String.valueOf(sms_task_status+1)));
		    	smsTaskHead.setSmsTaskName("测试任务"+sms_task_status);
		    	smsTaskHead.setCreateTime(new Date());
		    	smsTaskHead.setSmsTaskAppType(sms_task_status%2);
		    	smsTaskHead.setSmsTaskTemplateId(Long.parseLong(String.valueOf(sms_task_status+1)));
		    	smsTaskHead.setSmsTaskStatus(sms_task_status);
				smsTaskHead.setTotalCoverNum(sms_task_status+1000);
		    	smsTaskHead.setSendingFailNum(0);
		    	smsTaskHead.setSendingSuccessNum(1000+sms_task_status);
		    	smsTaskHead.setWaitingNum(0);
				break; 
				}
			default :{
				  break;
			  }			
    	} 
		return smsTaskHead;   	
    }
    
    @Test
    public void testSmsTaskHeadList(){    	
    	// 测试查询任务列表
        BaseOutput result = smsTaskHeadService.smsTaskHeadList("", 0, 12, "0", "0", "");        
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), result.getMsg());
    }
    
    @Test
    public void testSmsTaskHeadPublish(){    	
    	// 发布任务
        BaseOutput result = smsTaskHeadService.smsTaskHeadPublish("", 1);       
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), result.getMsg());
    }
    
    
    @After
    public void tearDown() throws Exception {
    	
    }

	public void setSmsTaskHeadService(SmsTaskHeadService smsTaskHeadService) {
		this.smsTaskHeadService = smsTaskHeadService;
	}

	public void setSmsTaskHeadDao(SmsTaskHeadDao smsTaskHeadDao) {
		this.smsTaskHeadDao = smsTaskHeadDao;
	}

	public void setMqTopicService(MQTopicService mqTopicService) {
		this.mqTopicService = mqTopicService;
	}
	
    
    
}
