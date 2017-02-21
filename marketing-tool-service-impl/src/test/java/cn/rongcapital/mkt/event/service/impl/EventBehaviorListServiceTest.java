package cn.rongcapital.mkt.event.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.dao.event.EventSourceDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.po.EventSource;
import cn.rongcapital.mkt.event.service.EventBehaviorListService;
import cn.rongcapital.mkt.event.vo.in.EventBehavierListIn;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorOut;
import cn.rongcapital.mkt.po.mongodb.event.EventBehaviors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RunWith(MockitoJUnitRunner.class)
public class EventBehaviorListServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(EventBehaviorListServiceTest.class);
	
	private static final String ATTRIBUTES = "object.attributes.";
	
	private EventBehaviorListService service;
	@Mock
	private MongoOperations mongoOperations;
	@Mock
	private EventDao eventDao;
	@Mock
	private EventSourceDao eventSourceDao;
	@Mock
	private EventObjectDao eventObjectDao;
	
	Event eventF = new Event();
	Event eventT = new Event();
	Event eventS = new Event();
	Event eventO = new Event();
	EventSource es = new EventSource();
	EventObject eo = new EventObject();
	EventSource esRtn = new EventSource();
	EventObject eoRtn = new EventObject();
	
	List<EventBehaviors> list = new ArrayList<EventBehaviors>();
	@Before
	public void setUp(){
		logger.info("测试：EventBehaviorListServiceTest 准备---------------------");	 
		service = new EventBehaviorListServiceImpl();
		//事件为订阅
		eventF.setId(1L);
		eventF.setName("扫描二维码");
		eventF.setSourceId(1L);
		eventF.setObjectId(1L);
		eventF.setSubscribed(false);
		
		eventS.setId(1L);
		eventS.setName("扫描二维码");
		eventS.setObjectId(1L);
		eventS.setSubscribed(false);
		
		eventO.setId(1L);
		eventO.setName("扫描二维码");
		eventO.setSourceId(1L);
		eventO.setSubscribed(false);
		
		eventT.setId(1L);
		eventT.setName("扫描二维码");
		eventT.setSourceId(1L);
		eventT.setObjectId(1L);
		eventT.setSubscribed(true);
		
		es.setId(eventF.getSourceId());
		esRtn.setId(eventF.getSourceId());
		esRtn.setName("MC微信二维码");
		
		eo.setId(eventF.getObjectId());
		eoRtn.setId(eventF.getObjectId());
		eoRtn.setName("扫一扫事件推送");
		
		EventBehaviors eb = new EventBehaviors("1", 1L, null, null, null, false);
		list.add(eb);
		
	}
	
    @After
    public void tearDown() throws Exception {
        logger.info("测试：EventBehaviorListServiceTest 结束---------------------");
    }
    
    
    //事件未订阅或不存在
    @Test
    public void Test00(){
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(null);
    	Mockito.when(eventSourceDao.getEventSource(es)).thenReturn(null);
    	Mockito.when(eventObjectDao.getEventObject(eo)).thenReturn(null);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);
    	ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.EVENT_ERROR_NOT_FOUND_ERROR.getCode(), out.getCode());
    }
    
    
    //事件未订阅或不存在
    @Test
    public void Test01(){
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(eventF);
    	Mockito.when(eventSourceDao.getEventSource(es)).thenReturn(null);
    	Mockito.when(eventObjectDao.getEventObject(eo)).thenReturn(null);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);
    	ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.EVENT_SOURCE_ERROR_NOT_FOUND_ERROR.getCode(), out.getCode());
    }
    
  
    @Test
    public void Test02(){
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(eventT);
    	Mockito.when(eventSourceDao.getEventSource(es)).thenReturn(null);
    	Mockito.when(eventObjectDao.getEventObject(eo)).thenReturn(null);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);
    	ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.EVENT_SOURCE_ERROR_NOT_FOUND_ERROR.getCode(), out.getCode());
    }
    
    @Test
    public void Test03(){
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(eventT);
    	Mockito.when(eventSourceDao.getEventSource(Mockito.any(EventSource.class))).thenReturn(esRtn);
    	Mockito.when(eventObjectDao.getEventObject(eo)).thenReturn(null);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);
    	ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.EVENT_OBJECT_ERROR_NOT_FOUND_ERROR.getCode(), out.getCode());
    }
    
    @Test
    public void Test04(){
    	
    	JSONArray arrayValue = new JSONArray();
    	arrayValue.add("红");
    	arrayValue.add("蓝");
    	JSONArray array = new JSONArray();
    	JSONObject obj = new JSONObject();
    	obj.put("name", "color");
    	obj.put("values", arrayValue);
    	array.add(obj);
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	JSONArray js = new JSONArray();
    	js.add("aaa");
    	ebli.setAttributes(js);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(eventT);
    	Mockito.when(eventSourceDao.getEventSource(Mockito.any(EventSource.class))).thenReturn(esRtn);
    	Mockito.when(eventObjectDao.getEventObject(Mockito.any(EventObject.class))).thenReturn(eoRtn);
    	Criteria cri = getCriteria(array.toJSONString());
		Query query = new Query();
		query.addCriteria(cri);
    	
    	Mockito.when(mongoOperations.find(query, EventBehaviors.class)).thenReturn(list);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);
    	ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
    	ReflectionTestUtils.setField(service, "mongoOperations", mongoOperations);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.VALIDATE_JSON_ERROR.getCode(), out.getCode());
    }
    
    @Test
    public void Test05(){
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(eventS);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.EVENT_SOURCE_ID_NOT_FOUND.getCode(), out.getCode());
    }
    
    
    @Test
    public void Test06(){
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(eventT);
    	Mockito.when(eventSourceDao.getEventSource(Mockito.any(EventSource.class))).thenReturn(esRtn);
    	Mockito.when(eventObjectDao.getEventObject(eo)).thenReturn(null);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);
    	ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.EVENT_OBJECT_ERROR_NOT_FOUND_ERROR.getCode(), out.getCode());
    
    }
    
    
    @Test
    public void Test07(){
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(eventO);
    	Mockito.when(eventSourceDao.getEventSource(Mockito.any(EventSource.class))).thenReturn(esRtn);
    	Mockito.when(eventObjectDao.getEventObject(eo)).thenReturn(null);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);
    	ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.EVENT_OBJECT_ID_NOT_FOUND.getCode(), out.getCode());
    }
    
    @Test
    public void Test08(){
    	
    	JSONArray arrayValue = new JSONArray();
    	arrayValue.add("红");
    	arrayValue.add("蓝");
    	JSONArray array = new JSONArray();
    	JSONObject obj = new JSONObject();
    	obj.put("name", "color");
    	obj.put("values", arrayValue);
    	array.add(obj);
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(eventT);
    	Mockito.when(eventSourceDao.getEventSource(Mockito.any(EventSource.class))).thenReturn(esRtn);
    	Mockito.when(eventObjectDao.getEventObject(Mockito.any(EventObject.class))).thenReturn(eoRtn);
    	Criteria cri = getCriteria(array.toJSONString());
		Query query = new Query();
		query.addCriteria(cri);
		query.skip(0);
		query.limit(10);
    	Mockito.when(mongoOperations.find(query, EventBehaviors.class)).thenReturn(list);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);
    	ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
    	ReflectionTestUtils.setField(service, "mongoOperations", mongoOperations);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), out.getCode());
    }
    
    
    @Test
    public void Test09(){
    	
    	JSONArray arrayValue = new JSONArray();
    	arrayValue.add("红");
    	arrayValue.add("蓝");
    	JSONArray array = new JSONArray();
    	JSONObject obj = new JSONObject();
    	obj.put("name", "color");
    	obj.put("values", arrayValue);
    	array.add(obj);
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	ebli.setAttributes(array);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(eventT);
    	Mockito.when(eventSourceDao.getEventSource(Mockito.any(EventSource.class))).thenReturn(esRtn);
    	Mockito.when(eventObjectDao.getEventObject(Mockito.any(EventObject.class))).thenReturn(eoRtn);
    	Criteria cri = getCriteria(array.toJSONString());
		Query query = new Query();
		query.addCriteria(cri);
		query.skip(0);
		query.limit(10);
    	Mockito.when(mongoOperations.find(query, EventBehaviors.class)).thenReturn(list);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);
    	ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
    	ReflectionTestUtils.setField(service, "mongoOperations", mongoOperations);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), out.getCode());
    }
    
    
    @Test
    public void Test10(){
    	
    	JSONArray arrayValue = new JSONArray();
    	arrayValue.add("红");
    	arrayValue.add("蓝");
    	JSONArray array = new JSONArray();
    	JSONObject obj = new JSONObject();
    	obj.put("name", "color");
    	obj.put("values", arrayValue);
    	array.add(obj);
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	ebli.setAttributes(array);
    	ebli.setIndex(1);
    	ebli.setSize(10);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(eventT);
    	Mockito.when(eventSourceDao.getEventSource(Mockito.any(EventSource.class))).thenReturn(esRtn);
    	Mockito.when(eventObjectDao.getEventObject(Mockito.any(EventObject.class))).thenReturn(eoRtn);
    	Criteria cri = getCriteria(array.toJSONString());
		Query query = new Query();
		query.addCriteria(cri);
		query.skip(0);
		query.limit(10);
    	Mockito.when(mongoOperations.find(query, EventBehaviors.class)).thenReturn(list);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);
    	ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
    	ReflectionTestUtils.setField(service, "mongoOperations", mongoOperations);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), out.getCode());
    }
    
    @Test
    public void Test11(){
    	
    	JSONArray arrayValue = new JSONArray();
    	arrayValue.add("红");
    	arrayValue.add("蓝");
    	JSONArray array = new JSONArray();
    	JSONObject obj = new JSONObject();
    	obj.put("name", "color");
    	obj.put("values", arrayValue);
//    	array.add(obj);
    	EventBehavierListIn ebli = new EventBehavierListIn();
    	ebli.setEventId(1L);
    	ebli.setAttributes(array);
    	ebli.setIndex(1);
    	ebli.setSize(10);
    	Mockito.when(eventDao.getEvent(Mockito.any(Event.class))).thenReturn(eventT);
    	Mockito.when(eventSourceDao.getEventSource(Mockito.any(EventSource.class))).thenReturn(esRtn);
    	Mockito.when(eventObjectDao.getEventObject(Mockito.any(EventObject.class))).thenReturn(eoRtn);
    	Criteria cri = getCriteria(array.toJSONString());
		Query query = new Query();
		query.addCriteria(cri);
		query.skip(0);
		query.limit(10);
    	Mockito.when(mongoOperations.find(query, EventBehaviors.class)).thenReturn(list);
    	ReflectionTestUtils.setField(service, "eventDao", eventDao);
    	ReflectionTestUtils.setField(service, "eventSourceDao", eventSourceDao);
    	ReflectionTestUtils.setField(service, "eventObjectDao", eventObjectDao);
    	ReflectionTestUtils.setField(service, "mongoOperations", mongoOperations);
    	EventBehaviorOut out = service.getEventBehavierList(ebli);
    	Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), out.getCode());
    }
    
    
    
	private Criteria getCriteria(String attribute){
		Criteria cri = null;
		cri = Criteria.where("subscribed").is(true);
		JSONArray condition = JSONArray.parseArray(attribute);
		int conditionSize = condition.size();
		if(conditionSize > 0){
			for(int i = 0; i < conditionSize; i++){
 				JSONObject jsObject = condition.getJSONObject(i);
				String nameKey = jsObject.getString("name");
				String field = ATTRIBUTES + nameKey;
				JSONArray jsonArry = jsObject.getJSONArray("values");
				List<String> list = JSONArray.parseArray(jsonArry.toString(), String.class);
				cri.and(field).in(list);
			}
		}
		return cri;
	} 
    
}
