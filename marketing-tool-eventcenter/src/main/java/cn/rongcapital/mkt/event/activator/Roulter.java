/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年2月24日 
 * @date(最后修改日期)：2017年2月24日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.activator;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.service.RoutStrategy;

public class Roulter {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(Roulter.class);
    
    @Autowired
    private RoutStrategy  routStrategy;
    
    //做事件开关只有订阅的
    public String route(Message<?> message) throws ExecutionException {
        String headerName = message.getHeaders().get(EventConstant.EVENT_HEADER).toString();
        // todo 增加逻辑判断是否可继续传递
        if (EventConstant.EVENT_SYSTEM.equals(headerName)) {
            return headerName;
        } else {
            String json = message.getPayload().toString();
            if (routStrategy.iFRouter(json)) { 
                return headerName;
            }


        }

        return null;


    }
}
