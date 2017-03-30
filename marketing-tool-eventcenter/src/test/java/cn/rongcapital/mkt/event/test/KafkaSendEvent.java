/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年2月28日 
 * @date(最后修改日期)：2017年2月28日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.integration.kafka.support.KafkaHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//import kafka.javaapi.producer.Producer;
//import kafka.producer.KeyedMessage;
//import kafka.producer.ProducerConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:event-center-test-sendtokafak.xml" })
public class KafkaSendEvent {

    @Autowired
    private MessageChannel inputToKafka;
    
    //@Test //1.3.0            
    public void generalTest()
    {
        /*Properties props = new Properties();
        String topic="streamtomc";
        props.put( "metadata.broker.list" , "10.200.11.36:9092" );
        props.put( "serializer.class" , "kafka.serializer.StringEncoder" );
        props.put( "request.required.acks" , "1" );
        ProducerConfig config = new ProducerConfig(props);    
        Producer<String, String> producer = new Producer<String, String>(config);    
        KeyedMessage<String, String> message = new KeyedMessage<String, String>(topic, "hell9888_word,");
        producer.send(message);
        producer.close();*/
        
    }
    
    @Test
    public void integrationTest()
    { 
       /*Map<String, Object> headers = new HashMap<String, Object>();
       headers.put(KafkaHeaders.TOPIC,"streamtomc");
       headers.put(KafkaHeaders.MESSAGE_KEY, "1");*/
//       String topic="streamtomc";
//       inputToKafka.send(MessageBuilder.withPayload("0000400008888----11111").setHeader(KafkaHeaders.TOPIC,topic).build());
         
    }
}
