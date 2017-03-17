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

import javax.jms.Queue;

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

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cancelInnerTask(TaskSchedule taskSchedule) {

        boolean needCancel = true;
        List<TaskSchedule> schedules = this.taskScheduleDao.selectPreByCampaignIdAndItemId(taskSchedule.getCampaignHeadId(), taskSchedule.getCampaignItemId());
        for (TaskSchedule schedule: schedules) {
            if(schedule.getTaskStatus() == ApiConstant.TASK_STATUS_VALID
                    && schedule.getStatus() == ApiConstant.TASK_STATUS_VALID) {
                needCancel = false;
            }
        }

        //查看心相应的Queue中是否包含了未处理的数据
        Queue queue = getDynamicQueue(taskSchedule.getCampaignHeadId()+"-"+taskSchedule.getCampaignItemId());//获取MQ中的当前节点对应的queue
        if(queue != null) {
            int queueSize= getQueueSize(queue);//获取MQ数据
            if(queueSize>0){
                needCancel = false;
            }
        }

        //当前任务节点的所以前置节点都没有相应的队列数据时，方可停掉当前任务
        if(needCancel) {
            super.cancelCampaignInnerTask(taskSchedule);

            taskSchedule.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
            taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);
            this.taskScheduleDao.updateById(taskSchedule);
        }

//
//        Integer campaignHeadId = taskSchedule.getCampaignHeadId();
//        //判断当前活动是否已经处于已结束的状态
//        CampaignHead campaignHead = new CampaignHead();
//        campaignHead.setId(taskSchedule.getCampaignHeadId());
//        List<CampaignHead> campaignHeads = this.campaignHeadDao.selectList(campaignHead);
//        if(CollectionUtils.isNotEmpty(campaignHeads)){
//            if(campaignHeads.get(0).getPublishStatus() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH){
//
//                //查看当前任务节点的所有上游节点相应的队列是否包含有有数据，如果没有数据则停掉
//                CampaignSwitch campaignSwitchP = new CampaignSwitch();
//                campaignSwitchP.setCampaignHeadId(taskSchedule.getCampaignHeadId());
//                campaignSwitchP.setNextItemId(taskSchedule.getCampaignItemId());
//                List<CampaignSwitch> switches = this.campaignSwitchDao.selectList(campaignSwitchP);
//                for (CampaignSwitch item : switches) {
//
//                    //校验当前节点的上一级节点，所对应的任务是否已经停止，如果停止了,并且所对应的MQ也没有数据了，则停掉当前任务
//                    CampaignBody campaignBodyP = new CampaignBody();
//                    campaignBodyP.setHeadId(taskSchedule.getCampaignHeadId());
//                    campaignBodyP.setItemId(item.getItemId());
//                    List<CampaignBody> campaignBodies = this.campaignBodyDao.selectList(campaignBodyP);
//                    if(CollectionUtils.isNotEmpty(campaignBodies)){
//                        TaskSchedule taskScheduleP = new TaskSchedule();
//                        taskScheduleP.setId(campaignBodies.get(0).getTaskId());
//                        List<TaskSchedule> taskSchedules = this.taskScheduleDao.selectList(taskScheduleP);
//                        if(CollectionUtils.isNotEmpty(taskSchedules)){
//                            TaskSchedule taskScheduleItem = taskSchedules.get(0);
//                            if(taskScheduleItem.getTaskStatus() == ApiConstant.TASK_STATUS_VALID
//                                    && taskScheduleItem.getStatus() == ApiConstant.TASK_STATUS_VALID){
//                                needCancel = false;
//                            }
//                        }
//                    }
//                }
//
//
//            }
//        }
    }
}
