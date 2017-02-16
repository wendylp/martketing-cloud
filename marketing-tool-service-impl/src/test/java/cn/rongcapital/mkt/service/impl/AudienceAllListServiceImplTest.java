package cn.rongcapital.mkt.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.vo.BaseOutput;
import junit.framework.Assert;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class AudienceAllListServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private AudienceAllListServiceImpl audienceAllListServiceImpl;
    
    @Mock
    private AudienceListDao audienceListDao;

    @Before
    public void setUp() {
        logger.info("测试：AudienceAllListServiceImpl 准备---------------------");
        audienceAllListServiceImpl = new AudienceAllListServiceImpl();
        ReflectionTestUtils.setField(audienceAllListServiceImpl, "audienceListDao", audienceListDao);
    }

    @Test 
    public void testGetZeroData() {
    	List<AudienceList> list = new ArrayList<AudienceList>();
    	Mockito.when(audienceListDao.selectListCount(any())).thenReturn(0);    	
		Mockito.when(audienceListDao.selectList(any())).thenReturn(list);    	    	
    	
    	BaseOutput out = audienceAllListServiceImpl.audienceAllList("token");
    	Assert.assertEquals(0, out.getTotal());
    	Assert.assertEquals(0, out.getTotalCount());
    	Assert.assertEquals(0, out.getTotal());    	
    }
    
    @Test 
    public void testGetJustOneData() {
    	List<AudienceList> list = new ArrayList<AudienceList>();
    	AudienceList e = new AudienceList();
    	e.setAudienceName("testname");
    	e.setAudienceRows(10);
    	e.setId(1);
		list.add(e );
    	Mockito.when(audienceListDao.selectListCount(any())).thenReturn(1);    	
		Mockito.when(audienceListDao.selectList(any())).thenReturn(list);    	    	
    	
    	BaseOutput out = audienceAllListServiceImpl.audienceAllList("token");
    	Assert.assertEquals(1, out.getTotal());
    	Assert.assertEquals(1, out.getTotalCount());
    	Map<String, Object>  realAudienceList = (Map<String, Object>)out.getData().get(0);       	
    	Assert.assertEquals("testname", realAudienceList.get("audience_list_name"));
    	Assert.assertEquals(1, realAudienceList.get("audience_list_id"));
    	Assert.assertEquals(10, realAudienceList.get("audience_count"));    	
    }
    
    @After
    public void tearDown() {
        logger.info("测试：AudienceAllListServiceImpl 结束---------------------");	
    }
}
