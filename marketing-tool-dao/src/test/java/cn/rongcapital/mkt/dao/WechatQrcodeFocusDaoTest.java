/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

@RunWith(SpringJUnit4ClassRunner.class)
public class WechatQrcodeFocusDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private WechatQrcodeFocusDao wechatQrcodeFocusDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: WechatQrcodeFocusDao 开始---------------------");
    }
    
	@Test
	public void testGetLostFocusMax() {
	    logger.info("测试方法: getLostFocusMax ");    
	}
	
	@Test
	public void testGetNewFocusMax() {
	    logger.info("测试方法: getNewFocusMax ");    
	}
	
	@Test
	public void testGetQrcodeIdList() {
	    logger.info("测试方法: getQrcodeIdList ");    
	}
	
	@Test
	public void testGetAddFocusMax() {
	    logger.info("测试方法: getAddFocusMax ");    
	}
	
	@Test
	public void testGetAllFocusData() {
	    logger.info("测试方法: getAllFocusData ");    
	}
	
	@Test
	public void testSelectTheEarliestFocus() {
	    logger.info("测试方法: selectTheEarliestFocus ");    
	}
	
	@Test
	public void testGetAmountFocusMax() {
	    logger.info("测试方法: getAmountFocusMax ");    
	}
	
	@Test
	public void testSelectTheEarliestFocusByQrcodeId() {
	    logger.info("测试方法: selectTheEarliestFocusByQrcodeId ");    
	}
	
	@Test
	public void testGetFocusOrUnFocusCount() {
	    logger.info("测试方法: getFocusOrUnFocusCount ");    
	}
	
	@Test
	public void testInsert() {
	    logger.info("测试方法: insert ");    
	}
	
	@Test
	public void testSelectListCount() {
	    logger.info("测试方法: selectListCount ");    
	}
	
	@Test
	public void testUpdateById() {
	    logger.info("测试方法: updateById ");    
	}
	
	@Test
	public void testSelectList() {
	    logger.info("测试方法: selectList ");    
	}
	
	@Test
	public void testSelectListByIdList() {
	    logger.info("测试方法: selectListByIdList ");    
	}
	
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: WechatQrcodeFocusDao 结束---------------------");
    }
}