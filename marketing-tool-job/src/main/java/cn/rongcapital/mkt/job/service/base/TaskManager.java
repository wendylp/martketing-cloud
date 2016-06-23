package cn.rongcapital.mkt.job.service.base;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.po.TaskSchedule;

@Component
public class TaskManager {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	 
	@Autowired
	private ApplicationContext cotext;
	@Autowired
    private ConcurrentTaskScheduler taskSchedule;
	@Autowired
	private TaskScheduleDao taskScheduleDao;
	
	private static final ConcurrentHashMap<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();
	
	private static final ConcurrentHashMap<String, TaskSchedule> taskPropMap = new ConcurrentHashMap<String, TaskSchedule>();	
	
	private volatile boolean isInited = false;
	
	private Runnable scanTask = new Runnable() {
		public void run() {
				scanTask();
	       }
	}; 
	private Runnable prepareTasks = new Runnable() {
		public void run() {
				prepareTasks();
	       }
	};
	
	public synchronized void manualInitTask () {
		taskSchedule.submit(scanTask);
		taskSchedule.submit(prepareTasks);
	}
	
	public void initTask() {
		logger.debug("initTask");
		if(isInited){
			return;
		}
		isInited = true;
		taskSchedule.scheduleAtFixedRate(scanTask, ApiConstant.TASK_SCAN_INTERVAL_MILLS);
		taskSchedule.scheduleAtFixedRate(prepareTasks, ApiConstant.TASK_DO_INTERVAL_MILLS);
	}
	
	private void scanTask() {
		logger.debug("scanTask");
		TaskSchedule t= new TaskSchedule();
		List<TaskSchedule> taskScheduleList = taskScheduleDao.selectList(t);
		ConcurrentHashMap<String, TaskSchedule> taskPropMapTmp = new ConcurrentHashMap<String, TaskSchedule>();
		for(TaskSchedule ts:taskScheduleList) {
			if(StringUtils.isNotBlank(ts.getServiceName())) {
				taskPropMapTmp.put(ts.getId().toString(),ts);
				TaskManager.taskPropMap.put(ts.getId().toString(),ts);
			}
		}
		TaskManager.taskPropMap.forEach((k,v)->{
			if(!taskPropMapTmp.containsKey(k)){//任务已被物理删除
				v.setTaskStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
				v.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
				ScheduledFuture<?> scheduledFuture = TaskManager.taskMap.get(k);
				if(null != scheduledFuture && scheduledFuture.isDone()) {
					TaskManager.taskMap.remove(k);//任务从内存中删除
				}
			}
		});
	}
	
	private void prepareTasks() {
		logger.debug("prepareTasks");
		TaskManager.taskPropMap.forEach((k,v)->{
			ScheduledFuture<?> taskSchedule = TaskManager.taskMap.get(k);
			if(v.getStatus().byteValue() == ApiConstant.TABLE_DATA_STATUS_VALID && 
			   v.getTaskStatus().byteValue() == ApiConstant.TASK_STATUS_VALID) {
				if(null == taskSchedule || taskSchedule.isCancelled()) {
					if(v.getStartTime() == null || v.getStartTime().before(Calendar.getInstance().getTime())) {
						if(v.getEndTime() == null || v.getEndTime().after(Calendar.getInstance().getTime())) {
							startTask(v);
						}
					}
				}
			}
			
			if(v.getStatus().byteValue() == ApiConstant.TABLE_DATA_STATUS_INVALID || 
			   v.getTaskStatus().byteValue() == ApiConstant.TASK_STATUS_INVALID ||
			   (v.getStartTime() != null && v.getStartTime().after(Calendar.getInstance().getTime())) || 
			   (v.getEndTime() != null && v.getEndTime().before(Calendar.getInstance().getTime()))) {
					if(null != taskSchedule && !taskSchedule.isDone() && !taskSchedule.isCancelled()) {
						taskSchedule.cancel(true);
					}
					//停止内嵌的任务/线程
					String serviceName = getServiceName(v.getServiceName());
					TaskService taskService = (TaskService)cotext.getBean(serviceName);
					taskService.cancelInnerTask();
			}
		});
	}
	
	private void startTask(TaskSchedule taskSchedulePo) {
		logger.debug("startTask:"+JSON.toJSONString(taskSchedulePo));
		Runnable task = new Runnable() {
		       public void run() {
				try {
					String serviceName = getServiceName(taskSchedulePo.getServiceName());
					TaskService taskService = (TaskService)cotext.getBean(serviceName);
					taskService.task(taskSchedulePo.getId());
					taskService.task(taskSchedulePo);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
		       }
		};
		ScheduledFuture<?> scheduledFuture = null;
		String cronStr = taskSchedulePo.getSchedule();
		if(StringUtils.isNotBlank(cronStr)) {
			Trigger triger = new CronTrigger(cronStr);
			scheduledFuture = taskSchedule.schedule(task,triger);
		} else {
			Date startTime = taskSchedulePo.getStartTime() == null ? 
							 Calendar.getInstance().getTime():taskSchedulePo.getStartTime();
			Integer interMinutes = taskSchedulePo.getIntervalMinutes();
			if(null != interMinutes) {
				scheduledFuture =  taskSchedule.scheduleAtFixedRate(task, startTime, interMinutes*60*1000);
			}else {
				scheduledFuture = taskSchedule.schedule(task,startTime);
			}
		}
		if(null != scheduledFuture) {
			TaskManager.taskMap.put(taskSchedulePo.getId().toString(),scheduledFuture);
		}
	}
	
	private String getServiceName(String serviceName) {
		char serviceNameChar[] = serviceName.toCharArray();
		serviceNameChar[0] = Character.toLowerCase(serviceNameChar[0]);
		String sname = String.valueOf(serviceNameChar);
		return sname;
	}
}
