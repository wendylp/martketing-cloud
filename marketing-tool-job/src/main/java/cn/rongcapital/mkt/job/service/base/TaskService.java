package cn.rongcapital.mkt.job.service.base;

import cn.rongcapital.mkt.po.TaskSchedule;

public interface TaskService {
	
	public void task (Integer taskId);
	
	default void task (TaskSchedule taskSchedule){
		
	}
	
	default void cancelInnerTask(){
		
	}
}
