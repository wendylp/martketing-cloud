package cn.rongcapital.mkt.job.service.base;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.job.util.ScheduledFutureExecutor;
import cn.rongcapital.mkt.po.CampaignHead;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
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
import cn.rongcapital.mkt.common.enums.TaskNameEnum;
import cn.rongcapital.mkt.common.enums.TaskTypeEnum;
import cn.rongcapital.mkt.dao.TaskRunLogDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.po.TaskRunLog;
import cn.rongcapital.mkt.po.TaskSchedule;

@Component
public class TaskManager {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext cotext;
    @Autowired
    private TaskScheduleDao taskScheduleDao;
    @Autowired
    private TaskRunLogDao taskRunLogDao;

    @Autowired
    private CampaignHeadDao campaignHeadDao;

    private static final int pageSize = 100;

    private final String TASK_SCHEDULE_SERVICE_NAME="campaignActionPubWechatSendH5Task";

    private static final ConcurrentHashMap<String, ScheduledFutureExecutor> taskMap =
            new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, TaskSchedule> taskPropMap =
                    new ConcurrentHashMap<String, TaskSchedule>();

    private static volatile boolean taskInited = false;

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

    /**
     * 手动初始化任务
     * @param taskId
     * @param taskName
     */
    public synchronized void manualInitTask(int taskId, String taskName) {
        scanTask();
        prepareTasks();
    }

    public synchronized void initTask() {
        logger.info("initTask");
        if (taskInited) {
            return;
        }
        taskInited = true;
        ConcurrentTaskScheduler concurrentTaskScheduler = new ConcurrentTaskScheduler();
        concurrentTaskScheduler.scheduleAtFixedRate(scanTask, ApiConstant.TASK_SCAN_INTERVAL_MILLS);
        concurrentTaskScheduler.scheduleAtFixedRate(prepareTasks, ApiConstant.TASK_DO_INTERVAL_MILLS);
    }

    /**
     * 扫描数据库中的任务列表，扫描整个task_schedule表，按照每页100条数据进行处理
     * 将数据库中最新的数据更新到内存TaskMananger.taskPropMap变量中
     */
    private synchronized void scanTask() {
        logger.debug("scanTask");
        ConcurrentHashMap<String, TaskSchedule> taskPropMapTmp = new ConcurrentHashMap<String, TaskSchedule>();
        TaskSchedule t = new TaskSchedule();
        t.setTaskStatus(ApiConstant.TASK_STATUS_VALID);
        int totalRecord = taskScheduleDao.selectListCount(t);
        int totalPage = (totalRecord + pageSize -1) / pageSize;
        for(int index = 1; index <= totalPage; index++) {
        	t = new TaskSchedule(index,pageSize);
            t.setTaskStatus(ApiConstant.TASK_STATUS_VALID);
        	List<TaskSchedule> taskScheduleList = taskScheduleDao.selectList(t);
        	if(CollectionUtils.isEmpty(taskScheduleList)) {
        		break;
        	}
        	for (TaskSchedule ts : taskScheduleList) {
        		if (StringUtils.isNotBlank(ts.getServiceName())) {
        			taskPropMapTmp.put(ts.getId().toString(), ts);
        			TaskManager.taskPropMap.put(ts.getId().toString(), ts);
        		}
        	}
        }
    	TaskManager.taskPropMap.forEach((k, v) -> {
    		if (!taskPropMapTmp.containsKey(k)) {// 任务已被物理删除
    			v.setTaskStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
    			v.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
//    			ScheduledFuture<?> scheduledFuture = TaskManager.taskMap.get(k);
                ScheduledFutureExecutor scheduledFutureExecutor = TaskManager.taskMap.get(k);
                if (null != scheduledFutureExecutor) {
                    //将任务线程所在的线程池停掉
                    scheduledFutureExecutor.getScheduledExecutor().shutdown();
                    if(scheduledFutureExecutor.getScheduledExecutor().isTerminated()) {
                        TaskManager.taskMap.remove(k);// 任务从内存中删除
                        TaskManager.taskPropMap.remove(k);//从内存中删掉已经停止的线程
                    }
    			}
    		}
    	});
    }

    /**
     * 准备任务和开始任务，
     * 如果TaskManager.taskMap根据k能够查询到ScheduledFuture，说明任务已经在运行了，不需要再次开始
     * 如果任务的开始时间和截数据时间为null，或者当前事件在开始于结束事件之间，则任务是要立即执行
     * 如果此任务状态已经被设置为不可用，或者已经到了任务的结束事件，则执行取消任务操作
     */
    private synchronized void prepareTasks() {
        logger.debug("prepareTasks");
        TaskManager.taskPropMap.forEach((k, v) -> {
            ScheduledFutureExecutor scheduledFutureExecutor = TaskManager.taskMap.get(k);
            if (v.getTaskStatus().byteValue() == ApiConstant.TASK_STATUS_VALID) {

                if (null == scheduledFutureExecutor) {
                    if (v.getStartTime() == null || v.getStartTime().before(Calendar.getInstance().getTime())) {
                        if (v.getEndTime() == null || v.getEndTime().after(Calendar.getInstance().getTime())) {
                            //校验是否是活动的定时触发和受众任务，并且当前活动是否处于活动中的状态
                            if (ApiConstant.TASK_NAME_CAMPAIGN_TRUGGER_TIME.equals(v.getServiceName()) || ApiConstant.TASK_NAME_CAMPAIGN_AUDIENCE_FIX.equals(v.getServiceName()) || ApiConstant.TASK_NAME_CAMPAIGN_AUDIENCE_TARGET.equals(v.getServiceName())) {
                                if (v.getCampaignHeadId() != null) {
                                    //验证当前任务节点所处活动是否已经停止了
                                    CampaignHead t = new CampaignHead();
                                    t.setId(v.getCampaignHeadId());
                                    List<CampaignHead> campaignHeads = this.campaignHeadDao.selectList(t);
                                    if (CollectionUtils.isNotEmpty( campaignHeads) && (campaignHeads.get(0).getPublishStatus() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH)) {
                                        //修改此节点对应的数据库状态
                                        v.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);
                                        v.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
                                        this.taskScheduleDao.updateById(v);
                                        return;
                                    }
                                }
                            }
                            startTask(v);
                        }
                    }
                }
            }
            // 校验是否需要停止任务，如果需要停止，则只是将数据库中的task_status 和status字段修改，
            // 不对线程终止，当下一次scanTask时，直接删除任务线程
            String serviceName = getServiceName(v.getServiceName());
            Object serviceBean = cotext.getBean(serviceName);
            if (serviceBean instanceof TaskService) {
                TaskService taskService = (TaskService) serviceBean;
                if(null != scheduledFutureExecutor) {
                    taskService.validateAndUpdateTaskStatus(v,scheduledFutureExecutor);
                }
                //事件触发活动，到了结束的时间，需要将相应的活动状态发生改变
                if(v.getEndTime() != null && v.getEndTime().before(Calendar.getInstance().getTime())){
                    taskService.stopTimerTriggerTask(v);
                }
            }
        });
    }

    private TaskRunLog addTaskLog(TaskSchedule taskSchedulePo) {
        TaskRunLog taskRunLogT = new TaskRunLog();
        if(TASK_SCHEDULE_SERVICE_NAME.equals(taskSchedulePo.getServiceName())){
            taskRunLogT.setTaskName("Task_" + taskSchedulePo.getServiceName() + "_"
                    + RandomStringUtils.randomAlphabetic(5).toUpperCase());
            taskRunLogT.setTaskType((byte)TaskTypeEnum.DISPLAY.getCode());
        }else{
            taskRunLogT.setTaskName("Task_" + taskSchedulePo.getTaskName() + "_"
                    + RandomStringUtils.randomAlphabetic(5).toUpperCase());
            taskRunLogT.setTaskType((byte)TaskTypeEnum.HIDE.getCode());
        }
        taskRunLogT.setTaskId(taskSchedulePo.getId());
        taskRunLogT.setStartTime(new Date());
        taskRunLogDao.insert(taskRunLogT);
        return taskRunLogT;
    }

    private synchronized void startTask(TaskSchedule taskSchedulePo) {
        Runnable task = new Runnable() {
            public void run() {
            	logger.debug("startTask:" + JSON.toJSONString(taskSchedulePo));
                try {
                    String serviceName = getServiceName(taskSchedulePo.getServiceName());
                    Object serviceBean = cotext.getBean(serviceName);
                    if (serviceBean instanceof TaskService) {
                        TaskService taskService = (TaskService) serviceBean;
                        TaskRunLog taskRunLog = null;
//                        if (taskSchedulePo.getIntervalMinutes() == null && taskSchedulePo.getCampaignHeadId() == null) {
                            taskRunLog = addTaskLog(taskSchedulePo);
//                        }
                        taskService.task(taskSchedulePo.getId());
                        taskService.task(taskSchedulePo);
                        if (null != taskRunLog) {
                            taskRunLog.setEndTime(new Date());
                            taskRunLogDao.updateById(taskRunLog);
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    // TO DO: fixme:need to do sth
                }
            }
        };
        ScheduledFuture<?> scheduledFuture = null;
        String cronStr = taskSchedulePo.getSchedule();
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        ConcurrentTaskScheduler concurrentTaskScheduler = new ConcurrentTaskScheduler(scheduledExecutor);
        if (StringUtils.isNotBlank(cronStr)) {
            Trigger triger = new CronTrigger(cronStr);
            scheduledFuture = concurrentTaskScheduler.schedule(task, triger);
        } else {
            Date startTime = taskSchedulePo.getStartTime() == null ? Calendar.getInstance().getTime()
                            : taskSchedulePo.getStartTime();
            Float interMinutes = taskSchedulePo.getIntervalMinutes();
            if (null != interMinutes && interMinutes > 0) {
                long period = (long) (interMinutes * 60 * 1000);
                scheduledFuture = concurrentTaskScheduler.scheduleAtFixedRate(task, startTime, period);
            } else {
                scheduledFuture = concurrentTaskScheduler.schedule(task, startTime);
            }
        }
        if (null != scheduledFuture) {

            TaskManager.taskMap.put(taskSchedulePo.getId().toString(), new ScheduledFutureExecutor(scheduledFuture,scheduledExecutor));
        }
    }

    private String getServiceName(String serviceName) {
        char serviceNameChar[] = serviceName.toCharArray();
        serviceNameChar[0] = Character.toLowerCase(serviceNameChar[0]);
        String sname = String.valueOf(serviceNameChar);
        return sname;
    }

    /**
     * 启动需要在前端页面显示task名称进度的task
     * @param serviceName:任务名称
     * @param serviceName:定时任务的service类名称
     * @param taskTypeEnum:0:显示,1:不显示
     */
    public void initFrontTask(TaskNameEnum taskNameEnum,String serviceName,TaskTypeEnum taskTypeEnum) {
    	serviceName = getServiceName(serviceName);
        Object serviceBean = cotext.getBean(serviceName);
        if (serviceBean instanceof TaskService) {
        	Runnable task = new Runnable() {
        		public void run() {
        			TaskService taskService = (TaskService) serviceBean;
        			TaskRunLog taskRunLogT = new TaskRunLog();
        			taskRunLogT.setTaskName(taskNameEnum.getDescription()+"_"+System.currentTimeMillis());
        			taskRunLogT.setTaskId(ApiConstant.MANUAL_RUN_ONCE_TASK_ID);
        			taskRunLogT.setStartTime(new Date());
        			taskRunLogT.setTaskType((byte)taskTypeEnum.getCode());
        			taskRunLogDao.insert(taskRunLogT);
        			taskService.task(ApiConstant.MANUAL_RUN_ONCE_TASK_ID);
        			taskRunLogT.setEndTime(new Date());
        			taskRunLogDao.updateById(taskRunLogT);
        		}
        	};
        	ConcurrentTaskScheduler concurrentTaskScheduler = new ConcurrentTaskScheduler();
        	concurrentTaskScheduler.schedule(task, new Date());
        }
    }
}
