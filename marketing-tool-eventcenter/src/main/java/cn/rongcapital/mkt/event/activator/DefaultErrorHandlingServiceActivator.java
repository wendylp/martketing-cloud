/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月11日 
 * @date(最后修改日期)：2017年1月11日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.activator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

import com.alibaba.fastjson.JSON;

public class DefaultErrorHandlingServiceActivator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultErrorHandlingServiceActivator.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    private final String errinfo="mq_error_info";
    
     @ServiceActivator
     public void handleError(Message<Throwable> errorMessage)
     {
         Throwable throwable= errorMessage.getPayload();
       //LOGGER.info(String.format("message: %s,statck trace: %s",throwable.getMessage(),ExceptionUtils.getFullStackTrace(throwable)));
        if(throwable instanceof MessagingException)
         {
             Message<?> failedMessage=((MessagingException)throwable).getFailedMessage();
             if(failedMessage !=null)
             {   
                 String json="{\"payload\":"+failedMessage.getPayload()+",\"header\":\""+
               failedMessage.getHeaders().get("eventCenter.eventType")+"\",\"message\":\""+throwable.getMessage()+"\"}";
                Object object =null; // 为了不让error队列阻塞
                try {
                    object = JSON.parse(json);
                } catch (Exception e) {
                    object = JSON.parse("{\"payload\":\"消息体出错不符合JSON 格式,\",,\"header\":\""
                            + failedMessage.getHeaders().get("eventCenter.eventType") + "\",\"message\":\"error\"}");
                }
                 mongoTemplate.insert(object,errinfo);
             }
             
         }
     }
}
