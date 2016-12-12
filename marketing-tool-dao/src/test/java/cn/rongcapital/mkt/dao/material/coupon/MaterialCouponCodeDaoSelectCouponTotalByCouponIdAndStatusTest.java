package cn.rongcapital.mkt.dao.material.coupon;

import java.util.ArrayList;
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

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MeterialCouponCodeCountByStatus;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialCouponCodeDaoSelectCouponTotalByCouponIdAndStatusTest extends AbstractUnitTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaterialCouponCodeDao materialCouponCodeDao;

	@Before
	public void setUp() throws Exception {
		logger.info("测试: MaterialCouponDao 开始---------------------");
	}

	@Test
	public void testSelectCouponTotalByCouponIdAndReleStatus() {
		logger.info("测试方法: selectCouponTotalByCouponIdAndReleStatus ");
		Map<String, Object> paramMap = new HashMap();
		List<String> list = new ArrayList();
		list.add("received");
		list.add("unreceived");
		paramMap.put("releaseStatusList", list);
		paramMap.put("id", 12);

		List<MeterialCouponCodeCountByStatus> result = materialCouponCodeDao
				.selectCouponTotalByCouponIdAndReleStatus(paramMap);
		Long receivedValue = -1l;
		Long unreceivedValue = -1l;
		for (MeterialCouponCodeCountByStatus mcs : result) {
			if ("received".equals(mcs.getStatus())) {
				receivedValue = mcs.getCount();
			} else if ("unreceived".equals(mcs.getStatus())) {
				unreceivedValue = mcs.getCount();
			}
		}

		Assert.assertEquals(6, receivedValue.longValue());
		Assert.assertEquals(2, unreceivedValue.longValue());
		logger.info("测试方法: selectCouponTotalByCouponIdAndReleStatus ");
	}

	@Test
	public void testselectCouponTotalByCouponIdAndVeriStatus() {
		logger.info("测试方法: selectCouponTotalByCouponIdAndVeriStatus ");
		Map<String, Object> paramMap = new HashMap();
		List<String> list = new ArrayList();
		list.add("verified");
		paramMap.put("verifyStatusList", list);
		paramMap.put("id", 12);

		List<MeterialCouponCodeCountByStatus> result = materialCouponCodeDao
				.selectCouponTotalByCouponIdAndVeriStatus(paramMap);
		Long verifiedValue = -1l;
		for (MeterialCouponCodeCountByStatus mcs : result) {
			if ("verified".equals(mcs.getStatus())) {
				verifiedValue = mcs.getCount();
			}
		}

		Assert.assertEquals(3, verifiedValue.longValue());

		logger.info("测试方法: selectCouponTotalByCouponIdAndVeriStatus ");
	}

	@After
	public void tearDown() throws Exception {
		logger.info("测试: MaterialCouponDao 结束---------------------");
	}

}