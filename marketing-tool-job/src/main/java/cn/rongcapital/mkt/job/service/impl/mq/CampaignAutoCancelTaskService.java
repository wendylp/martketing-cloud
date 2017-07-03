/*************************************************
 * @功能及特点的描述简述: 活动相关任务进行停止处理基类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-3-15
 * @date(最后修改日期)：2017-3-15
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.List;
import java.util.concurrent.*;

import javax.jms.Queue;

import cn.rongcapital.mkt.job.util.ScheduledFutureExecutor;
import cn.rongcapital.mkt.po.CampaignHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.TaskSchedule;

public abstract class CampaignAutoCancelTaskService extends BaseMQService implements TaskService {

    @Autowired
    private TaskScheduleDao taskScheduleDao;

    @Autowired
    private  CampaignHeadDao campaignHeadDao;

    private static Logger logger = LoggerFactory.getLogger(CampaignAutoCancelTaskService.class);

    //执行的线程数
    private static final int THREAD_POOL_FIX_SIZE = 10;
    //执行线程的Service公共类
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(THREAD_POOL_FIX_SIZE);


    @Override
    @Transactional
    public boolean validateAndUpdateTaskStatus(TaskSchedule taskSchedule, ScheduledFutureExecutor scheduledScheduledFutureExecutor) {

        //验证当前任务节点所处活动是否停止
        //验证上一个节点是否已经停止
        //验证当前节点对应的MQ 队列的数据已经全部消费掉
        //验证任务线程是否已经执行完毕
        //当前节点对应的MQ consumer 关闭
        if (validCampaignHead(taskSchedule)
                ||validPreNode(taskSchedule)
                ||validQueueSize(taskSchedule)
                ||super.cancelCampaignInnerTask(taskSchedule)
                ||validScheduleFutureRunning(scheduledScheduledFutureExecutor)
                ) {
            return true;
        }
        logger.info("Task schedule id is {}, service name is {}，need to set stop.",taskSchedule.getId(),taskSchedule.getServiceName());
        //修改此节点对应的数据库状态
        updateDataStatus(taskSchedule);

        return false;
    }

    /**
     *
     * @param taskSchedule
     * @return
     */
    @Transactional
    protected boolean validAndUpdateTaskSchedule(TaskSchedule taskSchedule){
        //验证当前任务节点所处活动是否停止
        //验证上一个节点是否已经停止
        //验证当前节点对应的MQ 队列的数据已经全部消费掉
        //当前节点对应的MQ consumer 关闭
        if (validCampaignHead(taskSchedule)
                ||validPreNode(taskSchedule)
                ||validQueueSize(taskSchedule)
                ||super.cancelCampaignInnerTask(taskSchedule)){
            return true;
        }
        updateDataStatus(taskSchedule);
        return false;
    }

    /**
     * 校验当前任务线程是否已经执行完毕
     * @param scheduledFutureExecutor
     * @return
     */
    private boolean validScheduleFutureRunning(ScheduledFutureExecutor scheduledFutureExecutor){
        scheduledFutureExecutor.getScheduledExecutor().shutdown();
        if(!scheduledFutureExecutor.getScheduledExecutor().isShutdown()){
            return true;
        }
        return  false;
    }

    /**
     * 校验队列是否还有未处理的信息
     * @param taskSchedule
     * @return
     */
    private boolean validQueueSize(TaskSchedule taskSchedule) {
        //查看心相应的Queue中是否包含了未处理的数据
        Queue queue = getDynamicQueue(taskSchedule.getCampaignHeadId()+"-"+taskSchedule.getCampaignItemId());//获取MQ中的当前节点对应的queue
        if(queue != null) {
            int queueSize= getQueueSize(queue);//获取MQ数据大小
            if(queueSize>0){
                return true;
            }
        }
        return false;
    }

    /**
     * 更新节点对应的数据库状态
     * @param taskSchedule
     */
    private void updateDataStatus(TaskSchedule taskSchedule) {
        //修改此节点对应的数据库状态
        taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);
        taskSchedule.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
        this.taskScheduleDao.updateById(taskSchedule);
    }

    /**
     * 校验上一节点是否已经停止
     * @param taskSchedule
     * @return
     */
    private boolean validPreNode(TaskSchedule taskSchedule) {
        //验证此节点的上一级节点是否已经停止
        List<TaskSchedule> schedules = this.taskScheduleDao.selectPreByCampaignIdAndItemId(taskSchedule.getCampaignHeadId(), taskSchedule.getCampaignItemId());
        if (schedules != null) {
            for (TaskSchedule schedule : schedules) {
                if (schedule.getTaskStatus() == ApiConstant.TASK_STATUS_VALID || schedule.getStatus() == ApiConstant.TABLE_DATA_STATUS_VALID) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 校验任务所处活动是否不再活动中
     * @param taskSchedule
     * @return
     */
    private boolean validCampaignHead(TaskSchedule taskSchedule) {
        //验证当前任务节点所处活动是否已经停止了
        CampaignHead t = new CampaignHead();
        t.setId(taskSchedule.getCampaignHeadId());
        List<CampaignHead> campaignHeads = this.campaignHeadDao.selectList(t);
        if(campaignHeads.get(0).getPublishStatus() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS){
            return true;
        }
        return false;
    }
}
