/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date: 2017.01.11
 * @复审人:
 *************************************************/

package cn.rongcapital.mkt.dao.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.enums.event.EventChannelEnum;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.po.EventPlatform;
import cn.rongcapital.mkt.event.po.EventSource;
import cn.rongcapital.mkt.event.vo.out.EventModelListOut;

public class EventDaoGetEventModelListTest extends AbstractUnitTest{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private EventDao eventDao;
    
    @Autowired
    private EventSourceDao eventSourceDao;
    
    @Autowired
    private EventPlatformDao eventPlatformDao;
    
    @Autowired
    private EventObjectDao eventObjectDao;
    
    private List<Event> eList = new ArrayList<>();
    private List<EventPlatform> epList = new ArrayList<>();
    private List<EventSource> esList = new ArrayList<>();
    private List<EventObject> eoList = new ArrayList<>();
    
    private List<EventPlatform> originaleEPList = new ArrayList<>();
    
    @Before
    public void setUp() throws Exception {
    	logger.info("测试: EventDao getEventModelCountList 开始---------------------");
    	logger.info("测试: EventDao 消除旧数据影响");
    	EventPlatform temp = new EventPlatform();
    	temp.setStatus((byte)0);
    	originaleEPList = eventPlatformDao.selectList(temp);
    	for (EventPlatform e : originaleEPList) {
			e.setStatus((byte)1);
			eventPlatformDao.updateById(e);
		}
    	
        logger.info("测试: EventDao 插入测试数据");
        EventSource eventSource = new EventSource();
        EventPlatform eventPlatform1 = new EventPlatform();
        EventObject eventObject = new EventObject();
        Event event = new Event();
    	
        eventPlatform1.setChannel(EventChannelEnum.CHANNEL1.getCode());
        eventPlatform1.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 20));
        eventPlatform1.setName("getEventModelList_测试数据");
        eventPlatform1.setStatus((byte) 0);
        eventPlatform1.setCreateTime(new Date());
        eventPlatformDao.insert(eventPlatform1);
        epList.add(eventPlatform1);
        
        eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 20));
        eventSource.setName("getEventModelList_测试数据");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
        eventObject.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 20));
        eventObject.setName("getEventModelList_测试数据");
        eventObject.setStatus((byte)0);
        eventObject.setCreateTime(new Date());
        eventObject.setSystemObject(false);
        eventObject.setType("type");
        eventObjectDao.insert(eventObject);
        eoList.add(eventObject);
        
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据1");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据2");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据3");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        eventPlatform1 = new EventPlatform();
        eventPlatform1.setChannel(EventChannelEnum.CHANNEL2.getCode());
        eventPlatform1.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 21));
        eventPlatform1.setName("getEventModelList_测试数据");
        eventPlatform1.setStatus((byte) 0);
        eventPlatform1.setCreateTime(new Date());
        eventPlatformDao.insert(eventPlatform1);
        epList.add(eventPlatform1);
        
        
        eventSource = new EventSource();
        eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 21));
        eventSource.setName("getEventModelList_测试数据");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据4");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据5");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据6");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        eventSource = new EventSource();
        eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 22));
        eventSource.setName("getEventModelList_测试数据");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据7");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据8");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据9");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        eventPlatform1 = new EventPlatform();
        eventPlatform1.setChannel(EventChannelEnum.CHANNEL3.getCode());
        eventPlatform1.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 22));
        eventPlatform1.setName("getEventModelList_测试数据");
        eventPlatform1.setStatus((byte) 0);
        eventPlatform1.setCreateTime(new Date());
        eventPlatformDao.insert(eventPlatform1);
        epList.add(eventPlatform1);
        
        eventSource = new EventSource();
        eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 23));
        eventSource.setName("getEventModelList_测试数据");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据10");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据11");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据12");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
        
        eventSource = new EventSource();
        eventSource.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 23));
        eventSource.setName("getEventModelList_测试数据");
        eventSource.setPlatformId(eventPlatform1.getId());
        eventSource.setStatus((byte) 0);
        eventSource.setCreateTime(new Date());
        eventSource.setSystemSource(false);
        eventSourceDao.insert(eventSource);
        esList.add(eventSource);
        
        event = new Event();
        event.setCode("TEST_"+UUID.randomUUID().toString().substring(0, 35));
        event.setName("getEventModelList_测试数据13");
        event.setSourceId(eventSource.getId());
        event.setObjectId(eventObject.getId());
        event.setStatus((byte)0);
        event.setCreateTime(new Date());
        event.setSystemEvent(false);
        event.setSubscribed(true);
        event.setRegisterOpportunity("1");
        event.setTriggerOpportunity("2");
        event.setUnsubscribable(false);
        eventDao.insert(event);
        eList.add(event);
    }
    
    @Test
    public void getEventModelCountList01() {
    	Map<String, Object> paramMap = new HashMap<>();
        List<EventModelListOut> list = eventDao.getEventModelList(paramMap);
        int i = eventDao.getEventModelListCnt(paramMap);
        Assert.assertEquals(0, list.size());
        Assert.assertEquals(13, i);
    }
    
    @Test
    public void getEventModelCountList02() {
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("channel", EventChannelEnum.CHANNEL1.getCode());
    	paramMap.put("index", 0);
    	paramMap.put("size", 10);
    	List<EventModelListOut> list = eventDao.getEventModelList(paramMap);
        int i = eventDao.getEventModelListCnt(paramMap);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(3, i);
    }

    @Test
    public void getEventModelCountList03() {
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("source_id", esList.get(4).getId());
    	paramMap.put("index", 0);
    	paramMap.put("size", 10);
    	List<EventModelListOut> list = eventDao.getEventModelList(paramMap);
        int i = eventDao.getEventModelListCnt(paramMap);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, i);
        Assert.assertEquals("getEventModelList_测试数据13", list.get(0).getName());
    }
    
    @Test
    public void getEventModelCountList04() {
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("event_name", "getEventModelList_测试数据");
    	paramMap.put("index", 0);
    	paramMap.put("size", 20);
    	List<EventModelListOut> list = eventDao.getEventModelList(paramMap);
        int i = eventDao.getEventModelListCnt(paramMap);
        Assert.assertEquals(13, list.size());
        Assert.assertEquals(13, i);
    }
    
    @Test
    public void getEventModelCountList05() {
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("index", 3);
    	paramMap.put("size", 3);
        List<EventModelListOut> list = eventDao.getEventModelList(paramMap);
        int i = eventDao.getEventModelListCnt(paramMap);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(13, i);
    }
    
    @After
    public void tearDown() throws Exception {
    	logger.info("测试: EventDao 还原旧数据 ");
    	for (EventPlatform e : originaleEPList) {
			e.setStatus((byte)0);
			eventPlatformDao.updateById(e);
		}
    	
        logger.info("测试: EventDao 删除测试数据 ");
        for (Event e: eList){
        	e.setStatus((byte) 1);
        	eventDao.updateById(e);
        }
        
        logger.info("测试: EventPlatformDao 删除测试数据 ");
        for (EventPlatform e: epList){
        	e.setStatus((byte) 1);
        	eventPlatformDao.updateById(e);
        }
        
        logger.info("测试: EventSourceDao 删除测试数据 ");
        for (EventSource e: esList){
        	e.setStatus((byte) 1);
        	eventSourceDao.updateById(e);
        }
        
        logger.info("测试: EventObjectDao 删除测试数据 ");
        for (EventObject e: eoList){
        	e.setStatus((byte) 1);
        	eventObjectDao.updateById(e);
        }
          
        logger.info("测试: EventDao getEventModelCountList 结束---------------------");
    }
    
}
