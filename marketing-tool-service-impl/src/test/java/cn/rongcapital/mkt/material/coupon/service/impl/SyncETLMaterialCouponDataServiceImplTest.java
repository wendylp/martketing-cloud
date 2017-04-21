package cn.rongcapital.mkt.material.coupon.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.service.SyncETLMaterialCouponDataService;
import cn.rongcapital.mkt.po.mongodb.Coupon;

@RunWith(MockitoJUnitRunner.class)
public class SyncETLMaterialCouponDataServiceImplTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Mock
	private MongoTemplate mongoTemplate;
	
	@Mock
    private MaterialCouponDao materialCouponDao;
    
    private SyncETLMaterialCouponDataService service; 
    
    private List<Coupon> nodeAudienceList = new ArrayList<Coupon>();
    
    private Coupon c1;
    private Coupon c2;
    private Coupon c3;
    private Coupon c4;
    
    @Before
	public void setUp() throws Exception {
    	logger.info("测试：SyncETLMaterialCouponDataService 准备---------------------");
    	service = new SyncETLMaterialCouponDataServiceImpl();
    	
    	c1= new Coupon();
		c1.setCouponId(1L);
		c1.setCouponName("Test1");
		c1.setCouponValue(12.03);
		c1.setBeginTime("2015-01-01");
		c1.setEndTime("2016-01-02");
		
    	ReflectionTestUtils.setField(service, "mongoTemplate", mongoTemplate);
		ReflectionTestUtils.setField(service, "materialCouponDao", materialCouponDao);
    }
    
    
	@Test //happy flow
	public void testSync01() {
		
		nodeAudienceList.add(c1);
		Mockito.when(mongoTemplate.find(Query.query(Criteria.where("SyncFlag").exists(false)), Coupon.class)).thenReturn(nodeAudienceList);
		Mockito.when(materialCouponDao.insert(any())).thenReturn(0);
        
		int result = service.sync();
		Assert.assertEquals(result,0);
	}
	
	@Test //mongo db 没查出值
	public void testSync02() {
		
		Mockito.when(mongoTemplate.find(Query.query(Criteria.where("SyncFlag").exists(false)), Coupon.class)).thenReturn(null);
		Mockito.when(materialCouponDao.insert(any())).thenReturn(0);
        
		int result = service.sync();
		Assert.assertEquals(result,0);
	}
	
	@Test 
	public void testSync03() {
		//ETL数据格式不对  BeginTime 不是 "yyyy-mm-dd" 
		c1= new Coupon();
		c1.setCouponId(1L);
		c1.setCouponName("Test1");
		c1.setCouponValue(12.03);
		c1.setBeginTime("15-Jun-01");
		c1.setEndTime("2015-01-01");
		nodeAudienceList.add(c1);
		
		//ETL数据格式不对  EndTime 不是 "yyyy-mm-dd"
		c2= new Coupon();
		c2.setCouponId(1L);
		c2.setCouponName("Test1");
		c2.setCouponValue(12.03);
		c2.setBeginTime("2015-01-01");
		c2.setEndTime("15-Jun-01");
		nodeAudienceList.add(c2);
		
		//EndTime为空
		c3= new Coupon();
		c3.setCouponId(1L);
		c3.setCouponName("Test1");
		c3.setCouponValue(12.03);
		c3.setBeginTime("2015-01-01");
		nodeAudienceList.add(c3);
		
		//BeginTime为空
		c2= new Coupon();
		c2.setCouponId(1L);
		c2.setCouponName("Test1");
		c2.setCouponValue(12.03);
		c2.setEndTime("2015-01-01");
		nodeAudienceList.add(c4);
		
		Mockito.when(mongoTemplate.find(Query.query(Criteria.where("SyncFlag").exists(false)), Coupon.class)).thenReturn(nodeAudienceList);
		Mockito.when(materialCouponDao.insert(any())).thenReturn(0);
        
		int result = service.sync();
		Assert.assertEquals(result,4);
	}
	
	@Test 
	public void testSync04() {
		//ETL数据格式不对  CouponName 为空
		c1= new Coupon();
		c1.setCouponId(1L);
		c1.setCouponName(null);
		c1.setCouponValue(12.03);
		c1.setBeginTime("2015-01-01");
		c1.setEndTime("2015-01-01");
		nodeAudienceList.add(c1);
		
		//ETL数据格式不对  CouponId 为空
		c2= new Coupon();
		c2.setCouponId(null);
		c2.setCouponName("Test1");
		c2.setCouponValue(12.03);
		c2.setBeginTime("2015-01-01");
		c2.setEndTime("2015-01-01");
		nodeAudienceList.add(c2);
		
		//CouponValue为空
		c3= new Coupon();
		c3.setCouponId(1L);
		c3.setCouponName("Test1");
		c3.setCouponValue(null);
		c3.setBeginTime("2015-01-01");
		c3.setEndTime("2015-01-01");
		nodeAudienceList.add(c3);
		
		Mockito.when(mongoTemplate.find(Query.query(Criteria.where("SyncFlag").exists(false)), Coupon.class)).thenReturn(nodeAudienceList);
		Mockito.when(materialCouponDao.insert(any())).thenReturn(0);
        
		int result = service.sync();
		Assert.assertEquals(result,2);
	}
}
