package cn.rongcapital.mkt.job.service.base;

import java.util.concurrent.ScheduledFuture;

import cn.rongcapital.mkt.po.TaskSchedule;

public interface TaskService {
	
	public void task (Integer taskId);
	
	default void task (TaskSchedule taskSchedule) {}
	
	default void cancelInnerTask(TaskSchedule taskSchedule) {}
	
	default void cancelInnerTask(TaskSchedule taskSchedule,ScheduledFuture<?> scheduleFuture) {}
	
	public default void updateTaskStatus(TaskSchedule taskSchedule) {}
	
	default void task (){}
	
	default void task (String jsonMessage){}
}
