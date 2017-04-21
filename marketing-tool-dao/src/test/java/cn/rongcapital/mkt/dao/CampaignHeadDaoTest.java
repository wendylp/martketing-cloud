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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.CampaignHead;

//@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class CampaignHeadDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private CampaignHeadDao campaignHeadDao;
	private static final String REDIS_KEY_PREFIX = "segmentcover:";
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: CampaignHeadDao 开始---------------------");

    }
    
	@Test
	public void testSelectCampaignProgressStatusListByPublishStatusCount() {
	    logger.info("测试方法: selectCampaignProgressStatusListByPublishStatusCount ");    
		Long populationCount = null;
		try {
			List<String> hmget = JedisClient.hmget(REDIS_KEY_PREFIX + 101,2,"segmentcount");
			populationCount = Long.parseLong(hmget.get(0));

			logger.info("测试方法: campaignProfileList {}", populationCount);
		} catch (JedisException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Test
	public void testSelectInProgressandPrepareStatusCampaignHeadLimitation() {
	    logger.info("测试方法: selectInProgressandPrepareStatusCampaignHeadLimitation ");    
	}
	
	@Test
	public void testSelectCampaignHeadCountGroupByPublishStatus() {
	    logger.info("测试方法: selectCampaignHeadCountGroupByPublishStatus ");    
	}
	
	@Test
	public void testSelectCampaignProgressStatusListByPublishStatus() {
		Map<String, Object> customMap = new HashMap<String, Object>();
		customMap.put("keyword", "xv");
		CampaignHead query = new CampaignHead();
		// query.setStatus((byte) 0);
		query.setCustomMap(customMap);
		List<CampaignHead> campaignHeads = campaignHeadDao.selectCampaignProgressStatusListByPublishStatus(query);
		Assert.assertNotNull(campaignHeads);
		Assert.assertFalse(campaignHeads.isEmpty());
	    logger.info("测试方法: selectCampaignProgressStatusListByPublishStatus ");    
	}
	
	@Test
	public void testSelectCampaignHeadListBySearchDate() {
	    logger.info("测试方法: selectCampaignHeadListBySearchDate ");    
	}
	
	@Test
	public void testSelectInProgressandPrepareStatusCampaignHead() {
	    logger.info("测试方法: selectInProgressandPrepareStatusCampaignHead ");    
	}
	
	@Test
	public void testSearchDataMain() {
	    logger.info("测试方法: searchDataMain ");    
	}
	
	@Test
	public void testSelectCampaignCount() {
	    logger.info("测试方法: selectCampaignCount ");    
	}
	
	@Test
	public void testInsert() {
		CampaignHead head = new CampaignHead();
		head.setName("xv_insert_test");
		Byte noPublish = 0;
		head.setPublishStatus(noPublish);
		head.setCreateTime(new Date());
		int count = this.campaignHeadDao.insert(head);
		Assert.assertEquals(1, count);
		Assert.assertNotNull(head.getId());
	    logger.info("测试方法: insert ");    
	}
	
	@Test
	public void testSelectListCount() {
	    logger.info("测试方法: selectListCount ");    
	}
	
	@Test
	public void testUpdateById() {
		CampaignHead head = new CampaignHead();
		head.setName("xv_update_test");
		Byte noPublish = 0;
		head.setPublishStatus(noPublish);
		head.setCreateTime(new Date());
		this.campaignHeadDao.insert(head);

		head.setPublishStatus((byte) 2);
		head.setStartTime(new Date());
		head.setEndTime(new Date());
		int count = this.campaignHeadDao.updateById(head);
		Assert.assertEquals(1, count);
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
        logger.info("测试: CampaignHeadDao 结束---------------------");
    }
}