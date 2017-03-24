/**
 * 
 */
package cn.rongcapital.mkt.dao;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.SmsTaskBody;

/**
 * @author shuiyangyang
 * @Date 2016.10.18
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SmsTaskBodyDaoTest extends AbstractUnitTest {

	private Logger logger = LoggerFactory.getLogger(SmsTaskBodyDaoTest.class);

    @Autowired
	private SmsTaskBodyDao smsTaskBodyDao;
	private SmsTaskBody body;

    @Before
    public void setUp() throws Exception {
		logger.info("lalal");
    }

	@Test
	public void test() {
		// insert
		body = new SmsTaskBody();
		body.setSmsTaskHeadId(99L);
		body.setTargetType(0);
		body.setUpdateTime(new Date());
		this.smsTaskBodyDao.insert(body);
		// select
		body = new SmsTaskBody();
		body.setSmsTaskHeadId(99L);
		List<SmsTaskBody> list = this.smsTaskBodyDao.selectList(body);
		body = list.get(0);
		// update
		body.setSendStatus(1);
		this.smsTaskBodyDao.updateById(body);
		list = this.smsTaskBodyDao.selectList(body);
		Assert.assertEquals("短信详情测试不通过", 1, list.size());
	}



    @After
    public void tearDown() throws Exception {

    }

}
