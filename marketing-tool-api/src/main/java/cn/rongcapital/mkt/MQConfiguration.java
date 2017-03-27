/*************************************************
 * @功能简述: MQ配置
 * @项目名称: marketing cloud
 * @see:
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2017/3/2
 * @复审人:
 *************************************************/

package cn.rongcapital.mkt;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;


@Configuration
public class MQConfiguration {

    @Value("${spring.activemq.broker-url}")
    private String brokerURL;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(brokerURL);
    }

    @Bean
    public JmsOperations jmsOperations() {
        return new JmsTemplate(connectionFactory());
    }
}
