/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年2月25日 
 * @date(最后修改日期)：2017年2月25日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.service;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import cn.rongcapital.mkt.event.activator.EventConstant;



public class KafKaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafKaConsumer.class);    
 
    private MessageChannel channel;
    
    public void process(final Map  message)
    {
       List<String> mes= EventConstant.formatMessage((Map)message);
       for(String info :mes)
       {    
           LOGGER.info("from_kafka_mes: {}",info);
           if(info.contains("apply_submit_ruixuesoft"))
           {    
               LOGGER.info("to_MC_mes: {}",info);
               channel.send(MessageBuilder.withPayload(convertMessage(info)).setHeader(EventConstant.EVENT_HEADER,EventConstant.EVENT_STREAM).build());
           }   
       }
    }
    
    public MessageChannel getChannel() {
        return channel;
    }

    public void setChannel(MessageChannel channel) {
        this.channel = channel;
    }

    /**
     * @author liuhaizhan
     * @功能简述: 转换成MC事件
     * @param 合成格式：
     **
     */
    
    private String convertMessage(final String mes)
    {
        StringBuilder   mcInfo=new StringBuilder("{");
        mcInfo.append("\"subject\":{");
        mcInfo.append(EventConstant.getParam(mes,"\\\"subject\\\":{","},"));
        mcInfo.append("},\"time\":").append(getDateTime(EventConstant.getParam(mes,"\\\"time\\\":\\\"", "\\\",")));
        mcInfo.append(",\"object\":{\"code\": \"form_signup_ruixuesoft\",\"attributes\":{");
        mcInfo.append(EventConstant.getParam(mes, "\\\"object\\\":{","},"));
        mcInfo.append("}},\"event\":{\"code\": \"apply_submit_ruixuesoft\",\"attributes\":{");
        mcInfo.append(EventConstant.getParam(mes, "\\\"properties\\\":{",",\\\"b_dollar_os\\\":"));
        mcInfo.append("}}}"); 
        return mcInfo.toString().replaceAll("\\\\", "");
        
    }
    
    private long getDateTime(String time)
    {
        
        return time==null?new DateTime().getMillis():new DateTime(time).getMillis();
    }
   
}
