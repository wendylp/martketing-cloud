/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月10日 
 * @date(最后修改日期)：2017年1月10日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dao.event.EventObjectPropValueDao;
import cn.rongcapital.mkt.event.po.EventPropValue;
import cn.rongcapital.mkt.event.service.EventObjectPropValueService;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;

@RunWith(MockitoJUnitRunner.class)
public class EventObjectPropValueServiceImplTest {
    
   private Logger logger = LoggerFactory.getLogger(getClass());
   
   
   private EventObjectPropValueService  eventObjectPropValueService;
   
   @Mock
   private EventObjectPropValueDao eventObjectPropValueDao;
   
   private EventBehavior  eventBehavior =new EventBehavior();
   
   List<EventPropValue> list =new ArrayList<EventPropValue>();
   @Before
   public void set() {
       eventObjectPropValueService = new EventObjectPropValueServiceImpl();
       eventBehavior.setId("10000");
       Map<String,Object> map =new HashMap<String,Object>();
       Map<String,Object> attribute =new HashMap<String,Object>();
       attribute.put("CODE", "Copon");
       attribute.put("VALUE", 100);
       map.put("attributes", attribute); 
       eventBehavior.setObject(map);
       
       EventPropValue event =new EventPropValue();
       event.setObjectId(Long.valueOf(eventBehavior.getId()));
       event.setPropName("CODE");
       event.setPropValue("100");
       list.add(event);
       
     ReflectionTestUtils.setField(eventObjectPropValueService, "eventObjectPropValueDao", eventObjectPropValueDao);
   }
   
   @Test
   public void test()
   {
      // mock 为null 
       
       Mockito.when(eventObjectPropValueDao.selectByObjectId(Mockito.anyChar())).thenReturn(null);  
       eventObjectPropValueService.insertPropValue(eventBehavior);
       
       
       Mockito.when(eventObjectPropValueDao.selectByObjectId(Mockito.anyChar())).thenReturn(list);  
       
       eventObjectPropValueService.insertPropValue(eventBehavior);
       
       
   }
   
   
   

}
