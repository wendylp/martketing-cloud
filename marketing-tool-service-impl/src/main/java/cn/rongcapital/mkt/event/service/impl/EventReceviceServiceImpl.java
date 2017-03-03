/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月9日 
 * @date(最后修改日期)：2017年1月9日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.service.impl;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import cn.rongcapital.mkt.common.util.CacheManage;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.service.EventObjectPropValueService;
import cn.rongcapital.mkt.event.service.EventReceviceService;
import cn.rongcapital.mkt.mongodb.event.EventBehaviorRepository;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;
@Service
public class EventReceviceServiceImpl implements EventReceviceService {
  
    @Autowired
    private EventBehaviorRepository  eventBehaviorRepository;
    
    @Autowired
    private EventObjectPropValueService  eventObjectPropValueService;

    
   
    @Override
    public void receviceEvent(EventBehavior eventbehavior) {
        // TODO Auto-generated method stub
        Map<String, Object> event =eventbehavior.getEvent();
        String eventcode=event.get("code").toString();
        EventBehavior eb= eventBehaviorRepository.insert(eventbehavior);
        if(eb!=null) //插入成功
        {
            try {
                eb.setObjectId(CacheManage.cache.get(eventcode));
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } //设置事件客体ID值
            eventObjectPropValueService.insertPropValue(eb);//属性值的入库
        }
        
    }
    
   
 

}
