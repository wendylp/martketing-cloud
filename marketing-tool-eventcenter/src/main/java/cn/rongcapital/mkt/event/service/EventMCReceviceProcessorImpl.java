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

package cn.rongcapital.mkt.event.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.event.activator.EventDispatcherActivator;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;

public class EventMCReceviceProcessorImpl implements EventProcessor {

    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.event.service.EventProcessor#process(java.lang.String)
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EventMCReceviceProcessorImpl.class);
    
    @Autowired
    private EventReceviceService eventReceviceService; 
    
    @Override
    public void process(String event) {
        // TODO Auto-generated method stub
        LOGGER.info("MARKETING event info {}:",event); 
        EventBehavior eventbehavior =JSON.parseObject(event,EventBehavior.class);
        eventReceviceService.receviceEvent(eventbehavior);
        LOGGER.info("处理结束....");
        
    }

    
    
    
    
    

}
