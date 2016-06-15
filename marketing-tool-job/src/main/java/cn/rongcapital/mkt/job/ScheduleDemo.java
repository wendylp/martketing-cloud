package cn.rongcapital.mkt.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class ScheduleDemo {
	
	@Autowired
    private ConcurrentTaskScheduler taskSchedule;
	
	public void doTask(String s){
	    Runnable task = new Runnable() {
	       public void run() {
	          System.out.println("doing work==>"+s);
	       }
	    };
//	    taskSchedule.scheduleAtFixedRate(task, 3000);
	    Trigger trigger = new CronTrigger("0 0 12 * * ?");
	    taskSchedule.schedule(task, trigger);
	}


}
