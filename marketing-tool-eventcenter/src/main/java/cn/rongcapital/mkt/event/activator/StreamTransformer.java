/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年2月26日 
 * @date(最后修改日期)：2017年2月26日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.activator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public class StreamTransformer {
 
    private static final String HEADER_KEY_EVENT_TYPE = "eventCenter.eventType";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamTransformer.class);
    
    public Message<?> transformStream(final Message<?> streammessage)
    {
        return MessageBuilder.withPayload(formatMessage((Map)streammessage.getPayload())).setHeader(HEADER_KEY_EVENT_TYPE,"STREAM").build();
    }
    
    private String formatMessage(final Map message)
    {
        Set<Map.Entry> set = message.entrySet();
        for (Map.Entry entry : set) {
            String topic = (String) entry.getKey();
            LOGGER.info("topic ："+topic);
            ConcurrentHashMap<Integer, List<byte[]>> messages =
                    (ConcurrentHashMap<Integer, List<byte[]>>) entry.getValue();
            Collection<List<byte[]>> values = messages.values();
            for (Iterator<List<byte[]>> iterator = values.iterator(); iterator.hasNext();) {
                List<byte[]> list = iterator.next();
                for (byte[] object : list) {
                    return new String(object);

                }
            }
        }

        return null;

    }
    
    
    
}
