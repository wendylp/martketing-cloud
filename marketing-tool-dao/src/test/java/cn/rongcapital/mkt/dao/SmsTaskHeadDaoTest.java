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
    	 * 设置查询条件   查询所有
    	 */
		SmsTaskHead smsTaskHeadTemp = new SmsTaskHead();
		smsTaskHeadTemp.setOrderField("create_time");
		smsTaskHeadTemp.setOrderFieldType("DESC");
		smsTaskHeadTemp.setStartIndex(0);
		smsTaskHeadTemp.setPageSize(12);
		
		List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectList(smsTaskHeadTemp);
		Assert.assertEquals(1, smsTaskHeads.size());
		 
    	/**
    	 * 设置查询条件 ——查询条件没有 名称模糊查询
    	 */
		SmsTaskHead smsTaskHeadTemp0 = new SmsTaskHead();
		smsTaskHeadTemp0.setSmsTaskAppType(0);
		smsTaskHeadTemp0.setSmsTaskStatus(0);
		smsTaskHeadTemp0.setOrderField("create_time");
		smsTaskHeadTemp0.setOrderFieldType("DESC");
		smsTaskHeadTemp0.setStartIndex(0);
		smsTaskHeadTemp0.setPageSize(12);
		
		List<SmsTaskHead> smsTaskHeads0 = smsTaskHeadDao.selectList(smsTaskHeadTemp0);
		Assert.assertEquals(1, smsTaskHeads0.size());
		
    	/**
    	 * 设置查询条件———名称模糊查询  
    	 */
		SmsTaskHead smsTaskHeadTemp1 = new SmsTaskHead();
/*		smsTaskHeadTemp1.setSmsTaskAppType(0);
		smsTaskHeadTemp1.setSmsTaskStatus(0);*/
		smsTaskHeadTemp1.setSmsTaskName("测试");
		smsTaskHeadTemp1.setOrderField("create_time");
		smsTaskHeadTemp1.setOrderFieldType("DESC");
		smsTaskHeadTemp1.setStartIndex(0);
		smsTaskHeadTemp1.setPageSize(12);
		
		List<SmsTaskHead> smsTaskHeads1 = smsTaskHeadDao.selectList(smsTaskHeadTemp);
		Assert.assertEquals(1, smsTaskHeads1.size());
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
