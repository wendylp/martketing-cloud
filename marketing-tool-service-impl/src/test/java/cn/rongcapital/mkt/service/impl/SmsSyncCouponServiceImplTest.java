package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailStateDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.MQTopicService;

@RunWith(MockitoJUnitRunner.class)
public class SmsSyncCouponServiceImplTest {
    
    @Mock
	private MQTopicService mqTopicService;
	@Mock
	private SmsTaskHeadDao smsTaskHeadDao;
	@Mock
	private SmsTaskDetailDao smsTaskDetailDao;
	@Mock
	private SmsTaskDetailStateDao smsTaskDetailStateDao;
	@InjectMocks
	private SmsSyncCouponServiceImpl impl;
	private Integer campaignHeadId = 555;
	private Long smsTaskHeadId = 666L;
	private List<Long> smsTaskDetailIds = Arrays.asList(1l, 2l, 3l, 4l, 5l);

    @Before
    public void setUp() {
    	SmsTaskHead smsTaskHead = new SmsTaskHead();
    	smsTaskHead.setSendingFailNum(0);
		Mockito.when(smsTaskHeadDao.selectList(Mockito.anyObject())).thenReturn(Arrays.asList(smsTaskHead));
    }

    @Test 
	public void testProcessSmsStatus() {
		boolean flag = impl.processSmsStatus(campaignHeadId, smsTaskHeadId, smsTaskDetailIds);
		Assert.assertTrue("processSmsStatus测试失败", flag);
		flag = impl.processSmsStatus(null, smsTaskHeadId, smsTaskDetailIds);
		Assert.assertTrue("processSmsStatus测试失败", flag);
		flag = impl.processSmsStatus(campaignHeadId, null, smsTaskDetailIds);
		Assert.assertFalse("processSmsStatus测试失败", flag);
		flag = impl.processSmsStatus(campaignHeadId, smsTaskHeadId, null);
		Assert.assertFalse("processSmsStatus测试失败", flag);
		flag = impl.processSmsStatus(campaignHeadId, smsTaskHeadId, new ArrayList<Long>());
		Assert.assertTrue("processSmsStatus测试失败", flag);
    }
    
    @Test 
	public void testUpdateSmsTaskHead() {
		boolean flag = impl.updateSmsTaskHead(smsTaskHeadId, smsTaskDetailIds.size());
		Assert.assertTrue("processSmsStatus测试失败", flag);
    }
    
	@Test
	public void testConvert() {
		List<Long> longs = Arrays.asList(1l, 2l, 3l, 4l, 4l, null);
		List<Integer> integers = impl.convert(longs);
		Assert.assertNotNull("List转换失败", integers);
		Assert.assertEquals("List转换失败", 5, integers.size());
		Assert.assertArrayEquals("List转换失败", new Integer[] { 1, 2, 3, 4, 4 }, integers.toArray());
	}

	@Test
	public void testSplit() {
		String str = "121493800460434";
		int index = str.indexOf("-");
		System.out.println(index);
		System.out.println(str.substring(index + 1));
	}

    @After
    public void tearDown() {

    }
}
