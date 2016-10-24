package cn.rongcapital.mkt.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.SmsTaskHead;

@RunWith(SpringJUnit4ClassRunner.class)
public class SmsTaskHeadDaoTest extends AbstractUnitTest{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SmsTaskHeadDao smsTaskHeadDao;
	
    @Before
    public void setUp() throws Exception {
/*    	String[] smsTaskNames = {"测试短信发送2", "测试短信发送3", "测试短信发送4"} ;
    	Long[] smsTaskSignatureIds = {1l,2l,3l};
    	Long[] smsTaskTemplateIds = {1l,2l,3l};
    	String[] smsTaskTemplateContents = {"模板内容测试2", "模板内容测试3", "模板内容测试4"} ;
        Integer[] smsTaskSendTypes = {1, 2,1};
        Integer[] smsTaskAppTypes = {0, 1, 2};
        Integer[] smsTaskStatus = {0, 2, 4};
        Byte[] status = {0, 1, 0};
                
        for(int i =0 ; i < 3; i++) {
        	SmsTaskHead smsTaskHeadTemp = new SmsTaskHead();
        	smsTaskHeadTemp.setSmsTaskName(smsTaskNames[i]);
        	smsTaskHeadTemp.setSmsTaskSignatureId(smsTaskSignatureIds[i]);
        	smsTaskHeadTemp.setSmsTaskTemplateId(smsTaskTemplateIds[i]);
        	smsTaskHeadTemp.setSmsTaskTemplateContent(smsTaskTemplateContents[i]);
        	smsTaskHeadTemp.setSmsTaskSendType(smsTaskSendTypes[i]);
        	smsTaskHeadTemp.setSmsTaskAppType(smsTaskAppTypes[i]);
        	smsTaskHeadTemp.setSmsTaskStatus(smsTaskStatus[i]);
        	smsTaskHeadTemp.setStatus(status[i]);
        	smsTaskHeadTemp.setCreateTime(new Date());
        	smsTaskHeadDao.insert(smsTaskHeadTemp);
        }*/
    }
    
    @Test
    public void testSmsTaskHeadList(){
    	/**
    	 * 设置查询条件   查询所有、名称模糊查询、应用通道、任务执行状态条件筛选查询  
    	 */
		SmsTaskHead smsTaskHeadTemp = new SmsTaskHead();
		
		/**
		 * 全条件
		 */
/*		smsTaskHeadTemp.setSmsTaskAppType(0);
		smsTaskHeadTemp.setSmsTaskStatus(0);
		smsTaskHeadTemp.setSmsTaskName("测试");*/
		/**
		 * 只有名称模糊查询
		 */
/*		smsTaskHeadTemp.setSmsTaskName("测试");*/		
		/**
		 * 只有应用通道
		 * 0:营销短信模板,1:服务通知模板,2：短信验证码模板
		 */
//		smsTaskHeadTemp.setSmsTaskAppType(0);
//		smsTaskHeadTemp.setSmsTaskAppType(1);
//		smsTaskHeadTemp.setSmsTaskAppType(2);
		/**
		 * 只有任务执行状态
		 * 0:未启动\n1:已预约\n2:执行中\n3:暂停中\n4:已结束
		 */		
//		smsTaskHeadTemp.setSmsTaskStatus(0);
//		smsTaskHeadTemp.setSmsTaskStatus(2);
//		smsTaskHeadTemp.setSmsTaskStatus(4);
		/**
		 * 应用通道、任务执行状态查询
		 */
/*		smsTaskHeadTemp.setSmsTaskAppType(0);
		smsTaskHeadTemp.setSmsTaskStatus(0);*/
/*		smsTaskHeadTemp.setSmsTaskAppType(1);
		smsTaskHeadTemp.setSmsTaskStatus(2);*/		
/*		smsTaskHeadTemp.setSmsTaskAppType(1);
		smsTaskHeadTemp.setSmsTaskStatus(4);*/
		/**
		 * 应用通道、名称模糊查询
		 */
/*		smsTaskHeadTemp.setSmsTaskAppType(0);
		smsTaskHeadTemp.setSmsTaskName("测试");*/
/*		smsTaskHeadTemp.setSmsTaskAppType(1);
		smsTaskHeadTemp.setSmsTaskName("测试");*/
		/**
		 * 任务执行状态、名称模糊查询
		 */
/*		smsTaskHeadTemp.setSmsTaskStatus(0);
		smsTaskHeadTemp.setSmsTaskName("测试");*/
/*		smsTaskHeadTemp.setSmsTaskStatus(1);
		smsTaskHeadTemp.setSmsTaskName("测试");*/
/*		smsTaskHeadTemp.setSmsTaskStatus(1);
		smsTaskHeadTemp.setSmsTaskName("");	*/
		
		/**
		 * 查询所有
		 */
		smsTaskHeadTemp.setOrderField("create_time");
		smsTaskHeadTemp.setOrderFieldType("DESC");
		smsTaskHeadTemp.setStartIndex(0);
		smsTaskHeadTemp.setPageSize(12);
		
		List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectList(smsTaskHeadTemp);
		Assert.assertEquals(5, smsTaskHeads.size());
		 
    }
    
    @Test
    public void testSelectListCount(){
    	/**
    	 * 设置查询条件
    	 */
		SmsTaskHead smsTaskHeadTemp = new SmsTaskHead();
//		smsTaskHeadTemp.setSmsTaskAppType(0);
		smsTaskHeadTemp.setSmsTaskStatus(0);
		smsTaskHeadTemp.setSmsTaskName("测试");
		smsTaskHeadTemp.setOrderField("create_time");
		smsTaskHeadTemp.setOrderFieldType("DESC");
		smsTaskHeadTemp.setStartIndex(0);
		smsTaskHeadTemp.setPageSize(12);
		
    	int totalCount = smsTaskHeadDao.selectListCount(smsTaskHeadTemp);
		Assert.assertEquals(1, totalCount);
    }
    
	@Test
    public void testCountStatusById(){
    	List<Map<String, Object>> smsTaskStatusCountMapList = smsTaskHeadDao.countStatusById(1l);
    	String jsonStr = JSONObject.toJSONString(smsTaskStatusCountMapList);
    	logger.info(jsonStr);
    }
       
    @After
    public void tearDown() throws Exception {
    	
    }


    public void setSmsTaskHeadDao(SmsTaskHeadDao smsTaskHeadDao) {
		this.smsTaskHeadDao = smsTaskHeadDao;
	}
    
    
}
