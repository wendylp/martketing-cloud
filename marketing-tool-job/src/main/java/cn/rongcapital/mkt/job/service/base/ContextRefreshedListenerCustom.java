package cn.rongcapital.mkt.job.service.base;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.util.DJobUtil;
import cn.rongcapital.mkt.job.service.impl.mq.BaseMQService;

import cn.rongcapital.mkt.service.MQTopicService;

 
@Component
public class ContextRefreshedListenerCustom implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private BaseMQService baseMQService;
	
	@Autowired
	private MQTopicService mQTopicService;
	
	@Autowired
    private Environment env;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		baseMQService.initJndiEvironment();
		taskManager.initTask();
		mQTopicService.initReceiver();
		try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		DJobUtil.registerSchedule(env.getProperty("djob-service"),Integer.valueOf(env.getProperty("djob-port")), "mc", "mc", "cn.rongcapital.mkt.job.service.base.BrithDayEventService", env.getProperty("djob-brithday-expression"));
		
	}

}