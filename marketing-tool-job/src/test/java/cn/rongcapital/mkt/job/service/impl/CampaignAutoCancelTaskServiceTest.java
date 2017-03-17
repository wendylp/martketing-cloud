/*************************************************
 * @功能及特点的描述简述: 活动相关任务停止业务测试类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-3-15
 * @date(最后修改日期)：2017-3-15
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.job.service.impl.mq.CampaignAutoCancelTaskService;
import cn.rongcapital.mkt.po.CampaignBody;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;
@RunWith(MockitoJUnitRunner.class)
public class CampaignAutoCancelTaskServiceTest {

    @Mock
    private TaskScheduleDao taskScheduleDao;
    @Mock
    private CampaignHeadDao campaignHeadDao;
    @Mock
    private CampaignBodyDao campaignBodyDao;
    @Mock
    private CampaignSwitchDao campaignSwitchDao;

    private CampaignAutoCancelTaskService service ;
    @Before
    public void before() throws Exception {

        service = PowerMockito.spy(new CampaignAutoCancelTaskService() {
            @Override
            public void task(Integer taskId) {

            }
        });
                ;
        PowerMockito.doReturn( null ).when( service, "getDynamicQueue", Mockito.anyString());
        ReflectionTestUtils.setField(service, "taskScheduleDao", taskScheduleDao);
        ReflectionTestUtils.setField(service, "campaignHeadDao", campaignHeadDao);
        ReflectionTestUtils.setField(service, "campaignBodyDao", campaignBodyDao);
        ReflectionTestUtils.setField(service, "campaignSwitchDao", campaignSwitchDao);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: cancelInnerTask(TaskSchedule taskSchedule)
     */
    @Test
    public void testCancelInnerTask() throws Exception {
        TaskSchedule campaignTriggerTimeTask = new TaskSchedule();
        campaignTriggerTimeTask.setServiceName("campaignTriggerTimeTask");
        campaignTriggerTimeTask.setTaskStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setStatus(Byte.valueOf("1"));
        campaignTriggerTimeTask.setCampaignHeadId(468);
        campaignTriggerTimeTask.setCampaignItemId("1489028037209");

        TaskSchedule campaignAudienceTargetTask = new TaskSchedule();
        campaignAudienceTargetTask.setServiceName("campaignAudienceTargetTask");
        campaignAudienceTargetTask.setTaskStatus(Byte.valueOf("0"));
        campaignAudienceTargetTask.setStatus(Byte.valueOf("1"));
        campaignAudienceTargetTask.setCampaignHeadId(468);
        campaignAudienceTargetTask.setCampaignItemId("1489028040010");

        TaskSchedule campaignActionWaitTask = new TaskSchedule();
        campaignActionWaitTask.setServiceName("campaignActionWaitTask");
        campaignActionWaitTask.setTaskStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setCampaignHeadId(468);
        campaignActionWaitTask.setCampaignItemId("1489028042642");

        CampaignHead campaignHead = new CampaignHead();
        campaignHead.setId(468);
        campaignHead.setName("xiexiaoliang_test_24");
        campaignHead.setPublishStatus(Byte.valueOf("3"));
        campaignHead.setStatus(Byte.valueOf("0"));

        CampaignSwitch campaignSwitch1 = new CampaignSwitch();
        campaignSwitch1.setId(2925);
        campaignSwitch1.setCampaignHeadId(468);
        campaignSwitch1.setItemId("1489028037209");
        campaignSwitch1.setType(Byte.valueOf("2"));
        campaignSwitch1.setDrawType(Byte.valueOf("0"));
        campaignSwitch1.setColor("#787878");
        campaignSwitch1.setNextItemId("1489028040010");
        campaignSwitch1.setStatus(Byte.valueOf("0"));

        CampaignSwitch campaignSwitch2 = new CampaignSwitch();
        campaignSwitch2.setId(2926);
        campaignSwitch2.setCampaignHeadId(468);
        campaignSwitch2.setItemId("1489028040010");
        campaignSwitch2.setType(Byte.valueOf("2"));
        campaignSwitch2.setDrawType(Byte.valueOf("0"));
        campaignSwitch2.setColor("#787878");
        campaignSwitch2.setNextItemId("1489028042642");
        campaignSwitch2.setStatus(Byte.valueOf("0"));

        CampaignBody body = new CampaignBody();
        body.setId(4604);
        body.setHeadId(468);
        body.setNodeType(Byte.valueOf("0"));
        body.setItemType(Byte.valueOf("0"));
        body.setItemId("1489028037209");
        body.setTaskId(7692);
        body.setStatus(Byte.valueOf("0"));

        CampaignBody body1 = new CampaignBody();
        body1.setId(4605);
        body1.setHeadId(468);
        body1.setNodeType(Byte.valueOf("1"));
        body1.setItemType(Byte.valueOf("0"));
        body1.setItemId("1489028040010");
        body1.setTaskId(7693);
        body1.setStatus(Byte.valueOf("0"));

        CampaignBody body2 = new CampaignBody();
        body2.setId(4606);
        body2.setHeadId(468);
        body2.setNodeType(Byte.valueOf("3"));
        body2.setItemType(Byte.valueOf("0"));
        body2.setItemId("1489028042642");
        body2.setTaskId(7694);
        body2.setStatus(Byte.valueOf("0"));

        List<CampaignHead> heads = new ArrayList<>();
        heads.add(campaignHead);
        Mockito.when(this.campaignHeadDao.selectList(Mockito.any(CampaignHead.class))).thenReturn(heads);
        List<CampaignSwitch> switches = new ArrayList<>();
        switches.add(campaignSwitch1);
        Mockito.when(this.campaignSwitchDao.selectList(Mockito.any(CampaignSwitch.class))).thenReturn(switches);
        List<CampaignBody> bodies = new ArrayList<>();
        bodies.add(body1);
        Mockito.when(this.campaignBodyDao.selectList(Mockito.any(CampaignBody.class))).thenReturn(bodies);
        List<TaskSchedule> schedules=  new ArrayList<>();
        schedules.add(campaignAudienceTargetTask);
        Mockito.when(this.taskScheduleDao.selectList(Mockito.any(TaskSchedule.class))).thenReturn(schedules);


        service.cancelInnerTask(campaignActionWaitTask);
        Mockito.verify(taskScheduleDao,Mockito.times(1)).updateById(Mockito.any(TaskSchedule.class));
    }


    @Test
    public void testCancelInnerTaskScheduleStatusValid() throws Exception {
        TaskSchedule campaignTriggerTimeTask = new TaskSchedule();
        campaignTriggerTimeTask.setServiceName("campaignTriggerTimeTask");
        campaignTriggerTimeTask.setTaskStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setCampaignHeadId(468);
        campaignTriggerTimeTask.setCampaignItemId("1489028037209");

        TaskSchedule campaignAudienceTargetTask = new TaskSchedule();
        campaignAudienceTargetTask.setServiceName("campaignAudienceTargetTask");
        campaignAudienceTargetTask.setTaskStatus(Byte.valueOf("0"));
        campaignAudienceTargetTask.setStatus(Byte.valueOf("0"));
        campaignAudienceTargetTask.setCampaignHeadId(468);
        campaignAudienceTargetTask.setCampaignItemId("1489028040010");

        TaskSchedule campaignActionWaitTask = new TaskSchedule();
        campaignActionWaitTask.setServiceName("campaignActionWaitTask");
        campaignActionWaitTask.setTaskStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setCampaignHeadId(468);
        campaignActionWaitTask.setCampaignItemId("1489028042642");

        CampaignHead campaignHead = new CampaignHead();
        campaignHead.setId(468);
        campaignHead.setName("xiexiaoliang_test_24");
        campaignHead.setPublishStatus(Byte.valueOf("3"));
        campaignHead.setStatus(Byte.valueOf("0"));

        CampaignSwitch campaignSwitch1 = new CampaignSwitch();
        campaignSwitch1.setId(2925);
        campaignSwitch1.setCampaignHeadId(468);
        campaignSwitch1.setItemId("1489028037209");
        campaignSwitch1.setType(Byte.valueOf("2"));
        campaignSwitch1.setDrawType(Byte.valueOf("0"));
        campaignSwitch1.setColor("#787878");
        campaignSwitch1.setNextItemId("1489028040010");
        campaignSwitch1.setStatus(Byte.valueOf("0"));

        CampaignSwitch campaignSwitch2 = new CampaignSwitch();
        campaignSwitch2.setId(2926);
        campaignSwitch2.setCampaignHeadId(468);
        campaignSwitch2.setItemId("1489028040010");
        campaignSwitch2.setType(Byte.valueOf("2"));
        campaignSwitch2.setDrawType(Byte.valueOf("0"));
        campaignSwitch2.setColor("#787878");
        campaignSwitch2.setNextItemId("1489028042642");
        campaignSwitch2.setStatus(Byte.valueOf("0"));

        CampaignBody body = new CampaignBody();
        body.setId(4604);
        body.setHeadId(468);
        body.setNodeType(Byte.valueOf("0"));
        body.setItemType(Byte.valueOf("0"));
        body.setItemId("1489028037209");
        body.setTaskId(7692);
        body.setStatus(Byte.valueOf("0"));

        CampaignBody body1 = new CampaignBody();
        body1.setId(4605);
        body1.setHeadId(468);
        body1.setNodeType(Byte.valueOf("1"));
        body1.setItemType(Byte.valueOf("0"));
        body1.setItemId("1489028040010");
        body1.setTaskId(7693);
        body1.setStatus(Byte.valueOf("0"));

        CampaignBody body2 = new CampaignBody();
        body2.setId(4606);
        body2.setHeadId(468);
        body2.setNodeType(Byte.valueOf("3"));
        body2.setItemType(Byte.valueOf("0"));
        body2.setItemId("1489028042642");
        body2.setTaskId(7694);
        body2.setStatus(Byte.valueOf("0"));

        List<CampaignHead> heads = new ArrayList<>();
        heads.add(campaignHead);
        Mockito.when(this.campaignHeadDao.selectList(Mockito.any(CampaignHead.class))).thenReturn(heads);
        List<CampaignSwitch> switches = new ArrayList<>();
        switches.add(campaignSwitch1);
        Mockito.when(this.campaignSwitchDao.selectList(Mockito.any(CampaignSwitch.class))).thenReturn(switches);
        List<CampaignBody> bodies = new ArrayList<>();
        bodies.add(body1);
        Mockito.when(this.campaignBodyDao.selectList(Mockito.any(CampaignBody.class))).thenReturn(bodies);
        List<TaskSchedule> schedules=  new ArrayList<>();
        schedules.add(campaignAudienceTargetTask);
        Mockito.when(this.taskScheduleDao.selectList(Mockito.any(TaskSchedule.class))).thenReturn(schedules);


        service.cancelInnerTask(campaignActionWaitTask);
        Mockito.verify(taskScheduleDao,Mockito.times(0)).updateById(Mockito.any(TaskSchedule.class));
    }
    @Test
    public void testCancelInnerTaskScheduleQueue() throws Exception {
        TaskSchedule campaignTriggerTimeTask = new TaskSchedule();
        campaignTriggerTimeTask.setServiceName("campaignTriggerTimeTask");
        campaignTriggerTimeTask.setTaskStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setCampaignHeadId(468);
        campaignTriggerTimeTask.setCampaignItemId("1489028037209");

        TaskSchedule campaignAudienceTargetTask = new TaskSchedule();
        campaignAudienceTargetTask.setServiceName("campaignAudienceTargetTask");
        campaignAudienceTargetTask.setTaskStatus(Byte.valueOf("0"));
        campaignAudienceTargetTask.setStatus(Byte.valueOf("0"));
        campaignAudienceTargetTask.setCampaignHeadId(468);
        campaignAudienceTargetTask.setCampaignItemId("1489028040010");

        TaskSchedule campaignActionWaitTask = new TaskSchedule();
        campaignActionWaitTask.setServiceName("campaignActionWaitTask");
        campaignActionWaitTask.setTaskStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setCampaignHeadId(468);
        campaignActionWaitTask.setCampaignItemId("1489028042642");

        CampaignHead campaignHead = new CampaignHead();
        campaignHead.setId(468);
        campaignHead.setName("xiexiaoliang_test_24");
        campaignHead.setPublishStatus(Byte.valueOf("3"));
        campaignHead.setStatus(Byte.valueOf("0"));

        CampaignSwitch campaignSwitch1 = new CampaignSwitch();
        campaignSwitch1.setId(2925);
        campaignSwitch1.setCampaignHeadId(468);
        campaignSwitch1.setItemId("1489028037209");
        campaignSwitch1.setType(Byte.valueOf("2"));
        campaignSwitch1.setDrawType(Byte.valueOf("0"));
        campaignSwitch1.setColor("#787878");
        campaignSwitch1.setNextItemId("1489028040010");
        campaignSwitch1.setStatus(Byte.valueOf("0"));

        CampaignSwitch campaignSwitch2 = new CampaignSwitch();
        campaignSwitch2.setId(2926);
        campaignSwitch2.setCampaignHeadId(468);
        campaignSwitch2.setItemId("1489028040010");
        campaignSwitch2.setType(Byte.valueOf("2"));
        campaignSwitch2.setDrawType(Byte.valueOf("0"));
        campaignSwitch2.setColor("#787878");
        campaignSwitch2.setNextItemId("1489028042642");
        campaignSwitch2.setStatus(Byte.valueOf("0"));

        CampaignBody body = new CampaignBody();
        body.setId(4604);
        body.setHeadId(468);
        body.setNodeType(Byte.valueOf("0"));
        body.setItemType(Byte.valueOf("0"));
        body.setItemId("1489028037209");
        body.setTaskId(7692);
        body.setStatus(Byte.valueOf("0"));

        CampaignBody body1 = new CampaignBody();
        body1.setId(4605);
        body1.setHeadId(468);
        body1.setNodeType(Byte.valueOf("1"));
        body1.setItemType(Byte.valueOf("0"));
        body1.setItemId("1489028040010");
        body1.setTaskId(7693);
        body1.setStatus(Byte.valueOf("0"));

        CampaignBody body2 = new CampaignBody();
        body2.setId(4606);
        body2.setHeadId(468);
        body2.setNodeType(Byte.valueOf("3"));
        body2.setItemType(Byte.valueOf("0"));
        body2.setItemId("1489028042642");
        body2.setTaskId(7694);
        body2.setStatus(Byte.valueOf("0"));

        List<CampaignHead> heads = new ArrayList<>();
        heads.add(campaignHead);
        Mockito.when(this.campaignHeadDao.selectList(Mockito.any(CampaignHead.class))).thenReturn(heads);
        List<CampaignSwitch> switches = new ArrayList<>();
        switches.add(campaignSwitch1);
        Mockito.when(this.campaignSwitchDao.selectList(Mockito.any(CampaignSwitch.class))).thenReturn(switches);
        List<CampaignBody> bodies = new ArrayList<>();
        bodies.add(body1);
        Mockito.when(this.campaignBodyDao.selectList(Mockito.any(CampaignBody.class))).thenReturn(bodies);
        List<TaskSchedule> schedules=  new ArrayList<>();
        schedules.add(campaignAudienceTargetTask);
        Mockito.when(this.taskScheduleDao.selectList(Mockito.any(TaskSchedule.class))).thenReturn(schedules);

        Queue queue = new Queue() {
            @Override
            public String getQueueName() throws JMSException {
                return null;
            }
        };
        PowerMockito.doReturn( queue ).when( service, "getDynamicQueue", Mockito.anyString());
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment());
        PowerMockito.doReturn( segments ).when( service, "getQueueData", Mockito.any());

        service.cancelInnerTask(campaignActionWaitTask);
        Mockito.verify(taskScheduleDao,Mockito.times(0)).updateById(Mockito.any(TaskSchedule.class));
    }

} 
