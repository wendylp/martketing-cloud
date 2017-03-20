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
import java.util.concurrent.ScheduledFuture;

import javax.jms.Queue;

import cn.rongcapital.mkt.po.CampaignHead;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.TaskSchedule;

public abstract class CampaignAutoCancelTaskService extends BaseMQService implements TaskService {

    @Autowired
    private TaskScheduleDao taskScheduleDao;

    @Autowired
    private  CampaignHeadDao campaignHeadDao;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cancelInnerTask(TaskSchedule taskSchedule,ScheduledFuture<?> scheduleFuture) {

        boolean needCancel = isNeedCancel(taskSchedule);

        //当前任务节点的所以前置节点都没有相应的队列数据时，方可停掉当前任务
        if(needCancel) {
//            super.cancelCampaignInnerTask(taskSchedule);
            taskSchedule.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
            this.taskScheduleDao.updateById(taskSchedule);
        }
    }

    private boolean isNeedCancel(TaskSchedule taskSchedule) {
        boolean needCancel  = true;
        CampaignHead t = new CampaignHead();
        t.setId(taskSchedule.getCampaignHeadId());
        List<CampaignHead> campaignHeads = this.campaignHeadDao.selectList(t);
        if(campaignHeads.get(0).getPublishStatus() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS){
            needCancel = false;
        }
        List<TaskSchedule> schedules = this.taskScheduleDao.selectPreByCampaignIdAndItemId(taskSchedule.getCampaignHeadId(), taskSchedule.getCampaignItemId());

        if (schedules != null) {
            for (TaskSchedule schedule : schedules) {
                if (schedule.getTaskStatus() == ApiConstant.TASK_STATUS_VALID) {
                    needCancel = false;
                }
            }
        }

        //查看心相应的Queue中是否包含了未处理的数据
        Queue queue = getDynamicQueue(taskSchedule.getCampaignHeadId()+"-"+taskSchedule.getCampaignItemId());//获取MQ中的当前节点对应的queue
        if(queue != null) {
            int queueSize= getQueueSize(queue);//获取MQ数据大小
            if(queueSize>0){
                needCancel = false;
            }
        }
        return needCancel;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateTaskStatus(TaskSchedule taskSchedule) {
        boolean needCancel = isNeedCancel(taskSchedule);;

        if(needCancel) {
            super.cancelCampaignInnerTask(taskSchedule);
            taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);
            taskSchedule.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
            this.taskScheduleDao.updateById(taskSchedule);
        }

    }
   
}
