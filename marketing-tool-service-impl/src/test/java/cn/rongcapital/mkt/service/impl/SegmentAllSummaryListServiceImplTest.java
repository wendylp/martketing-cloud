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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.SegmentManageCalService;
import cn.rongcapital.mkt.vo.out.SegmentSummaryListOut;
import junit.framework.Assert;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class SegmentAllSummaryListServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SegmentAllSummaryListServiceImpl segmentAllSummaryListServiceImpl;
    
    @Mock
    private SegmentationHeadDao segmentationHeadDao;
    
    @Mock
	private SegmentManageCalService segmentManageCalService;


	@Before
	public void setUp() {
        logger.info("测试：SegmentAllSummaryListServiceImpl 开始---------------------");
		segmentAllSummaryListServiceImpl = new SegmentAllSummaryListServiceImpl();		
        ReflectionTestUtils.setField(segmentAllSummaryListServiceImpl, "segmentationHeadDao", segmentationHeadDao);
        ReflectionTestUtils.setField(segmentAllSummaryListServiceImpl, "segmentManageCalService", segmentManageCalService);
	}
	
	@Test
	public void testInvalidPublishStatus() {
		Mockito.when(segmentationHeadDao.selectListCount(any())).thenReturn(0);
		SegmentSummaryListOut list = segmentAllSummaryListServiceImpl.segmentAllSummaryList("userToken", 99, "ver");
		Assert.assertEquals(0, list.getDataCustom().size());
	}
	
	
	@Test
	public void testPublishStatusAll() {		
		List<SegmentationHead> expectedList = new ArrayList<SegmentationHead>();
		SegmentationHead head = new SegmentationHead();
		head.setName("testhead");
		expectedList.add(head );
		Mockito.when(segmentationHeadDao.selectListCount(any())).thenReturn(1);
		Mockito.when(segmentationHeadDao.selectListByKeyword(any())).thenReturn(expectedList);
		Mockito.when(segmentManageCalService.scard(any(), any())).thenReturn((long) 10);		
		
		SegmentSummaryListOut list = segmentAllSummaryListServiceImpl.segmentAllSummaryList("userToken", 3, "ver");
		Assert.assertEquals(1, list.getDataCustom().size());
		Assert.assertEquals("testhead", list.getDataCustom().get(0).getSegmentName());
		Assert.assertEquals(10, list.getDataCustom().get(0).getCoverCount().intValue());
	}
	
	
	@After
	public void tearDown() {
        logger.info("测试：SegmentAllSummaryListServiceImpl 结束---------------------");
	}

}
