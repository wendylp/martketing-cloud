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

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;

@RunWith(SpringJUnit4ClassRunner.class)
public class NodeAudienceMongoDBTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
	private MongoTemplate mongoTemplate;
    
    @Before  
    public void setUp() throws Exception {
		logger.info("测试: NodeAudienceMongoDB 开始---------------------");
    }
    
	@Test
	public void testFind() {
		Criteria criteria = Criteria.where("campaignHeadId").is(49).and("itemId").is("1491097150045");
		List<NodeAudience> nodeAudienceList = mongoTemplate.find(new Query(criteria), NodeAudience.class);
		Assert.assertNotNull(nodeAudienceList);
		Assert.assertFalse(nodeAudienceList.isEmpty());
	    logger.info("测试方法: insertAudience ");    
	}
	
	@Test
	public void testCount() {
		Criteria criteria = Criteria.where("campaignHeadId").is(49).and("itemId").is("1491097150045");
		Query query = new Query(criteria);
		Long totalCount = mongoTemplate.count(query, NodeAudience.class);
		Assert.assertTrue(totalCount.intValue() > 0);
		logger.info("测试方法: count ");
	}
	
	
	
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: AudienceListDao 结束---------------------");
    }
}