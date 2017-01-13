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

package cn.rongcapital.mkt.event.test;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class MessageSender {
  
    @Autowired
    private RabbitTemplate amqpTemplate;
    
    
    @Autowired
    private AmqpAdmin amqpAdmin;

    
    @PostConstruct  //必须要先创建一个
    public void setUpQueue() {
        this.amqpAdmin.declareQueue(new Queue("test.queue.events"));
    }  
    
  public void  sendMsg ()
  {        for (int i=0;i<10;i++)
             {
      amqpTemplate.convertAndSend("test.queue.events","hello word+++"+i);
             }
  }
}
