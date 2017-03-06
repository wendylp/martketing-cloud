/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年3月6日 
 * @date(最后修改日期)：2017年3月6日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.kafka.support.KafkaHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class StreamEventSendImpl implements StreamEventSend {

    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.event.service.StreamEventSend#sendDate(java.lang.String)
     */
    
    @Autowired
    private MessageChannel inputToKafka; 
    
    
    @Override
    public void sendDate(String event) {
        // TODO Auto-generated method stub
        String topic="streamtomc";
        inputToKafka.send(MessageBuilder.withPayload(event).setHeader(KafkaHeaders.TOPIC,topic).build());    
    }

}
