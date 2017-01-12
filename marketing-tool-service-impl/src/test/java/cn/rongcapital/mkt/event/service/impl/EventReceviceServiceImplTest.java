/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月12日 
 * @date(最后修改日期)：2017年1月12日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.service.EventObjectPropValueService;
import cn.rongcapital.mkt.event.service.EventReceviceService;
import cn.rongcapital.mkt.mongodb.event.EventBehaviorRepository;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;
import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class EventReceviceServiceImplTest {

    @Mock
    private EventBehaviorRepository  eventBehaviorRepository;
    @Mock
    private EventDao eventDao;
    @Mock
    private EventObjectPropValueService  eventObjectPropValueService;
    
    private EventReceviceService eventReceviceService;
    
    private EventBehavior  eventBehavior =new EventBehavior();
    @Before
    public void set()
    {
        eventReceviceService=new EventReceviceServiceImpl();
        eventBehavior.setObjectId(10000l);
        Map<String,Object> map =new HashMap<String,Object>();
        Map<String,Object> attribute =new HashMap<String,Object>();
        attribute.put("CODE", "Copon");
        attribute.put("VALUE", 100);
        map.put("attributes", attribute); 
        eventBehavior.setObject(map);
        Map<String,Object> event=new HashMap<String,Object>();
        event.put("code", "use_coupon");
        eventBehavior.setEvent(event);
        ReflectionTestUtils.setField(eventReceviceService, "eventBehaviorRepository", eventBehaviorRepository);
        ReflectionTestUtils.setField(eventReceviceService, "eventObjectPropValueService", eventObjectPropValueService);
        ReflectionTestUtils.setField(eventReceviceService, "eventDao", eventDao);
    }
    
    @Test
    public void test()
    {
        Event eventOb=new Event ();
        eventOb.setSubscribed(true);
        Mockito.when(eventDao.selectByCode(Mockito.anyString())).thenReturn(eventOb);
        EventBehavior ev =new EventBehavior();
        ev.setObjectId(10000l);
        Mockito.when(eventBehaviorRepository.insert(Mockito.any(EventBehavior.class))).thenReturn(ev);
        eventReceviceService.receviceEvent(eventBehavior);
        Mockito.when(eventBehaviorRepository.insert(Mockito.any(EventBehavior.class))).thenReturn(null);
        eventReceviceService.receviceEvent(eventBehavior);
    }
}
