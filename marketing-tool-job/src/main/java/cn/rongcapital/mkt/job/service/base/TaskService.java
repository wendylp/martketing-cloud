package cn.rongcapital.mkt.job.service.base;

import cn.rongcapital.mkt.po.TaskSchedule;

public interface TaskService {

	public static final String APPLICATION_JSON = "application/json";
	public static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	public void task(Integer taskId);

	default void task(TaskSchedule taskSchedule) {
	}

	default void cancelInnerTask(TaskSchedule taskSchedule) {
	}

	default void task() {
	}

	default void task(String jsonMessage) {
	}
}
