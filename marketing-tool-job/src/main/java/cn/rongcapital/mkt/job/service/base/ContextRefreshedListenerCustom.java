package cn.rongcapital.mkt.job.service.base;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.job.service.impl.mq.BaseMQService;
 
@Component
public class ContextRefreshedListenerCustom implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private BaseMQService baseMQService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		baseMQService.initJndiEvironment();
//		taskManager.initTask();
	}

}