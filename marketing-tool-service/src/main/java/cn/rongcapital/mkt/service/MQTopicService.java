package cn.rongcapital.mkt.service;

import javax.jms.JMSException;

import cn.rongcapital.mkt.vo.ActiveMqMessageVO;

public interface MQTopicService {

	 public void senderMessage(String topicName, ActiveMqMessageVO message)throws JMSException;
	 
	 public void initReceiver();
}