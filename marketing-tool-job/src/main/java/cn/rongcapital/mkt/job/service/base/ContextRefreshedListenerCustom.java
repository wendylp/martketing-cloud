package cn.rongcapital.mkt.job.service.base;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
 
@Component
public class ContextRefreshedListenerCustom implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private TaskManager taskManager;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		taskManager.initTask();
	}

}