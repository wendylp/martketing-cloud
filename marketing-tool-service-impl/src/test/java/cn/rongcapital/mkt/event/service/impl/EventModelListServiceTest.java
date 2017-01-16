/*************************************************
 * @功能简述: EventModelListService 实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date:	2017.01.10
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
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
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.service.EventModelListService;
import cn.rongcapital.mkt.event.vo.out.EventModelListOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class EventModelListServiceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	EventModelListService emlService;

	@Mock
	private EventDao eventDao;

	@Before
	public void setUp() throws Exception {
		logger.info("测试：EventModelListService 开始---------------------");
		emlService = new EventModelListServiceImpl();

		ReflectionTestUtils.setField(emlService, "eventDao", eventDao);

	}

	/**
     * 验参数
     */
	@Test
	public void testGetEventModelList01() {
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Map<String, Object> param = (Map<String, Object>) args[0];
				Assert.assertEquals(Long.valueOf("2"), param.get("source_id"));
				Assert.assertEquals("channel2", param.get("channel"));
				Assert.assertEquals("eventName", param.get("event_name"));
				Assert.assertEquals(Integer.valueOf("8"), param.get("index"));
				Assert.assertEquals(Integer.valueOf("4"), param.get("size"));
				return null;
			}
		}).when(eventDao).getEventModelListCnt(Mockito.any());
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Assert.fail();
				return null;
			}
		}).when(eventDao).getEventModelList(Mockito.any());

		BaseOutput result = emlService.getEventModelList("channel2", 2l, "eventName", 3, 4);
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
		Assert.assertEquals(0, result.getTotalCount());
		Assert.assertEquals(0, result.getData().size());
	}
	
	/**
     * 验参数
     */
	@Test
	public void testGetEventModelList02() {
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Map<String, Object> param = (Map<String, Object>) args[0];
				Assert.assertEquals(Long.valueOf("2"), param.get("source_id"));
				Assert.assertEquals("channel2", param.get("channel"));
				Assert.assertEquals("eventName", param.get("event_name"));
				Assert.assertEquals(Integer.valueOf("0"), param.get("index"));
				Assert.assertEquals(Integer.valueOf("10"), param.get("size"));
				return null;
			}
		}).when(eventDao).getEventModelListCnt(Mockito.any());
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Assert.fail();
				return null;
			}
		}).when(eventDao).getEventModelList(Mockito.any());

		BaseOutput result = emlService.getEventModelList("channel2", 2l, "eventName", null, null);
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
		Assert.assertEquals(0, result.getTotalCount());
		Assert.assertEquals(0, result.getData().size());
	}
	
	@Test
	public void testGetEventModelList03() {
		List<EventModelListOut> mockList = new ArrayList<EventModelListOut>();
		EventModelListOut  eml = new EventModelListOut();
		eml.setBindCount(0l);
		eml.setChannel("channel1");
		eml.setCode("TEST");
		eml.setId(1l);
		eml.setName("name");
		eml.setObjectId(2l);
		eml.setObjectName("objName");
		eml.setPlatform("platform");
		eml.setSource("source");
		eml.setSubscribed(true);
		eml.setSystemEvent(false);
		eml.setUnsubscribable(true);
		mockList.add(eml);
		
		Mockito.when(eventDao.getEventModelListCnt (Mockito.anyMap())).thenReturn(20);
		Mockito.when(eventDao.getEventModelList(Mockito.anyMap())).thenReturn(mockList);
		
		BaseOutput result = emlService.getEventModelList(null, null, null, null, null);
		EventModelListOut temp =  (EventModelListOut) result.getData().get(0);
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(1, result.getTotal());
		Assert.assertEquals(20, result.getTotalCount());
		Assert.assertEquals(1, result.getData().size());
		Assert.assertEquals(0, temp.getBindCount().longValue());
		Assert.assertEquals(1, temp.getId().longValue());
		Assert.assertEquals(2, temp.getObjectId().longValue());
		Assert.assertEquals("channel1", temp.getChannel());
		Assert.assertEquals("TEST", temp.getCode());
		Assert.assertEquals("name", temp.getName());
		Assert.assertEquals("objName", temp.getObjectName());
		Assert.assertEquals("platform", temp.getPlatform());
		Assert.assertEquals("source", temp.getSource());
		Assert.assertEquals(true, temp.getSubscribed());
		Assert.assertEquals(false, temp.getSystemEvent());
		Assert.assertEquals(true, temp.getUnsubscribable());
		
	}
	
	@Test
	public void testGetEventModelList04() {
		
		Mockito.when(eventDao.getEventModelListCnt (Mockito.anyMap())).thenReturn(20);
		Mockito.when(eventDao.getEventModelList(Mockito.anyMap())).thenReturn(null);
		
		BaseOutput result = emlService.getEventModelList(null, null, null, 0, 0);
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
		Assert.assertEquals(20, result.getTotalCount());
		Assert.assertEquals(0, result.getData().size());
		
	}

	@After
	public void tearDown() throws Exception {
		logger.info("测试：EventModelCountService 结束---------------------");
	}

}
