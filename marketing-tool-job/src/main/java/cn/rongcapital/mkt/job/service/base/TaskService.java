package cn.rongcapital.mkt.job.service.base;

import cn.rongcapital.mkt.job.util.ScheduledFutureExecutor;
import cn.rongcapital.mkt.po.TaskSchedule;

public interface TaskService {
	
	public void task (Integer taskId);
	
	default void task (TaskSchedule taskSchedule) {}
	
	default void cancelInnerTask(TaskSchedule taskSchedule) {}

	/**
	 * 验证当前任务节点是否可以停止，如果可以，则将数据库状态进行设置为不可用
	 * @param taskSchedule
	 * @param scheduledScheduledFutureExecutor
	 */
	default boolean validateAndUpdateTaskStatus(TaskSchedule taskSchedule, ScheduledFutureExecutor scheduledScheduledFutureExecutor) {
		return false;
	}

	/**
	 * 停止时间触发活动
	 * @param taskSchedule
	 */
	default void stopTimerTriggerTask(TaskSchedule taskSchedule) {}
	
	default void task (){}
	
	default void task (String jsonMessage){}
}
