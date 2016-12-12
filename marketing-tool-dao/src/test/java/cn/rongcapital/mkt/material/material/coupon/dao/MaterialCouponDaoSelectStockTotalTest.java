package cn.rongcapital.mkt.material.material.coupon.dao;

import java.util.HashMap;
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

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.dao.MaterialCouponDao;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialCouponDaoSelectStockTotalTest extends AbstractUnitTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaterialCouponDao materialCouponDao;

	@Before
	public void setUp() throws Exception {
		logger.info("测试: MaterialCouponDao 开始---------------------");
	}

	@Test
	public void testSelectStockTotalByCouponId() {
		logger.info("测试方法: selectStockTotalByCouponId ");
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("id", 12);
		Long result = materialCouponDao.selectStockTotalByCouponId(paramMap);
		Assert.assertEquals(20000, result.longValue());
		logger.info("测试方法: selectStockTotalByCouponId ");
	}

	@After
	public void tearDown() throws Exception {
		logger.info("测试: MaterialCouponDao 结束---------------------");
	}
}
