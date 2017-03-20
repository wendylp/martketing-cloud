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
import java.util.Objects;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;

import javax.jms.JMSException;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQTempQueue;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest(CampaignAutoCancelTaskService.class)
public class CampaignAutoCancelTaskServiceTest {

    @Mock
    private TaskScheduleDao taskScheduleDao;
    @Mock
    private CampaignHeadDao campaignHeadDao;

    private CampaignAutoCancelTaskService service ;
    @Before
    public void before() throws Exception {

        PowerMockito.spy(CampaignAutoCancelTaskService.class);
        service =PowerMockito.spy( new CampaignAutoCancelTaskService() {
            @Override
            public void task(Integer taskId) {

            }
        });

        ReflectionTestUtils.setField(service, "taskScheduleDao", taskScheduleDao);
        ReflectionTestUtils.setField(service, "campaignHeadDao", campaignHeadDao);

    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: cancelInnerTask(TaskSchedule taskSchedule)
     */
    @Test
    public void testCancelInnerTask() throws Exception {

        TaskSchedule campaignActionWaitTask = new TaskSchedule();
        campaignActionWaitTask.setServiceName("campaignActionWaitTask");
        campaignActionWaitTask.setTaskStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setCampaignHeadId(468);
        campaignActionWaitTask.setCampaignItemId("1489028042642");

        ScheduledFuture<?> scheduledFuture = null;
        PowerMockito.doReturn( true ).when( service, "isNeedCancel", Mockito.any());
        service.cancelInnerTask(campaignActionWaitTask,scheduledFuture);
        Mockito.verify(taskScheduleDao,Mockito.times(1)).updateById(Mockito.any(TaskSchedule.class));
    }

    @Test
    public void testCancelInnerTaskIsFalse() throws Exception {

        TaskSchedule campaignActionWaitTask = new TaskSchedule();
        campaignActionWaitTask.setServiceName("campaignActionWaitTask");
        campaignActionWaitTask.setTaskStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setCampaignHeadId(468);
        campaignActionWaitTask.setCampaignItemId("1489028042642");

        ScheduledFuture<?> scheduledFuture = null;
        PowerMockito.doReturn( false ).when( service, "isNeedCancel", Mockito.any());
        service.cancelInnerTask(campaignActionWaitTask,scheduledFuture);
        Mockito.verify(taskScheduleDao,Mockito.times(0)).updateById(Mockito.any(TaskSchedule.class));
    }

    @Test
    public void testIsNeedCancel() throws Exception {

        CampaignHead campaignHead = new CampaignHead();
        campaignHead.setId(468);
        campaignHead.setName("xiexiaoliang_test_24");
        campaignHead.setPublishStatus(Byte.valueOf("3"));
        campaignHead.setStatus(Byte.valueOf("0"));
        TaskSchedule campaignTriggerTimeTask = new TaskSchedule();
        campaignTriggerTimeTask.setServiceName("campaignTriggerTimeTask");
        campaignTriggerTimeTask.setTaskStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setCampaignHeadId(468);
        campaignTriggerTimeTask.setCampaignItemId("1489028037209");
        List<CampaignHead> campaignHeads = new ArrayList<>();
        campaignHeads.add(campaignHead);
        PowerMockito.when(this.campaignHeadDao.selectList(Mockito.any(CampaignHead.class))).thenReturn(campaignHeads);

        List<TaskSchedule> schedules = new ArrayList<>();
        schedules.add(campaignTriggerTimeTask);
        PowerMockito.when(this.taskScheduleDao.selectPreByCampaignIdAndItemId(Mockito.anyInt(),Mockito.anyString())).thenReturn(null);

        PowerMockito.doReturn(null).when(this.service,"getDynamicQueue",Mockito.anyString());
        boolean isNeedCancel =   Whitebox.invokeMethod(this.service,"isNeedCancel",campaignTriggerTimeTask);
        Assert.assertEquals(Boolean.TRUE,isNeedCancel);
    }

    @Test
    public void testIsNeedCancelCampasignInProcess() throws Exception {

        CampaignHead campaignHead = new CampaignHead();
        campaignHead.setId(468);
        campaignHead.setName("xiexiaoliang_test_24");
        campaignHead.setPublishStatus(Byte.valueOf("2"));
        campaignHead.setStatus(Byte.valueOf("0"));
        TaskSchedule campaignTriggerTimeTask = new TaskSchedule();
        campaignTriggerTimeTask.setServiceName("campaignTriggerTimeTask");
        campaignTriggerTimeTask.setTaskStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setCampaignHeadId(468);
        campaignTriggerTimeTask.setCampaignItemId("1489028037209");
        List<CampaignHead> campaignHeads = new ArrayList<>();
        campaignHeads.add(campaignHead);
        PowerMockito.when(this.campaignHeadDao.selectList(Mockito.any(CampaignHead.class))).thenReturn(campaignHeads);

        List<TaskSchedule> schedules = new ArrayList<>();
        schedules.add(campaignTriggerTimeTask);
        PowerMockito.when(this.taskScheduleDao.selectPreByCampaignIdAndItemId(Mockito.anyInt(),Mockito.anyString())).thenReturn(null);

        PowerMockito.doReturn(null).when(this.service,"getDynamicQueue",Mockito.anyString());
        boolean isNeedCancel =   Whitebox.invokeMethod(this.service,"isNeedCancel",campaignTriggerTimeTask);
        Assert.assertEquals(Boolean.FALSE,isNeedCancel);
    }

    @Test
    public void testIsNeedCancelPreTaskIsValid() throws Exception {

        CampaignHead campaignHead = new CampaignHead();
        campaignHead.setId(468);
        campaignHead.setName("xiexiaoliang_test_24");
        campaignHead.setPublishStatus(Byte.valueOf("3"));
        campaignHead.setStatus(Byte.valueOf("0"));
        TaskSchedule campaignTriggerTimeTask = new TaskSchedule();
        campaignTriggerTimeTask.setServiceName("campaignTriggerTimeTask");
        campaignTriggerTimeTask.setTaskStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setCampaignHeadId(468);
        campaignTriggerTimeTask.setCampaignItemId("1489028037209");
        List<CampaignHead> campaignHeads = new ArrayList<>();
        campaignHeads.add(campaignHead);
        PowerMockito.when(this.campaignHeadDao.selectList(Mockito.any(CampaignHead.class))).thenReturn(campaignHeads);

        List<TaskSchedule> schedules = new ArrayList<>();
        schedules.add(campaignTriggerTimeTask);
        PowerMockito.when(this.taskScheduleDao.selectPreByCampaignIdAndItemId(Mockito.anyInt(),Mockito.anyString())).thenReturn(schedules);

        PowerMockito.doReturn(null).when(this.service,"getDynamicQueue",Mockito.anyString());
        boolean isNeedCancel =   Whitebox.invokeMethod(this.service,"isNeedCancel",campaignTriggerTimeTask);
        Assert.assertEquals(Boolean.FALSE,isNeedCancel);
    }

    @Test
    public void testIsNeedCancelPreTaskIsInValid() throws Exception {

        CampaignHead campaignHead = new CampaignHead();
        campaignHead.setId(468);
        campaignHead.setName("xiexiaoliang_test_24");
        campaignHead.setPublishStatus(Byte.valueOf("3"));
        campaignHead.setStatus(Byte.valueOf("0"));
        TaskSchedule campaignTriggerTimeTask = new TaskSchedule();
        campaignTriggerTimeTask.setServiceName("campaignTriggerTimeTask");
        campaignTriggerTimeTask.setTaskStatus(Byte.valueOf("1"));
        campaignTriggerTimeTask.setStatus(Byte.valueOf("1"));
        campaignTriggerTimeTask.setCampaignHeadId(468);
        campaignTriggerTimeTask.setCampaignItemId("1489028037209");
        List<CampaignHead> campaignHeads = new ArrayList<>();
        campaignHeads.add(campaignHead);
        PowerMockito.when(this.campaignHeadDao.selectList(Mockito.any(CampaignHead.class))).thenReturn(campaignHeads);

        List<TaskSchedule> schedules = new ArrayList<>();
        schedules.add(campaignTriggerTimeTask);
        PowerMockito.when(this.taskScheduleDao.selectPreByCampaignIdAndItemId(Mockito.anyInt(),Mockito.anyString())).thenReturn(schedules);

        PowerMockito.doReturn(null).when(this.service,"getDynamicQueue",Mockito.anyString());
        boolean isNeedCancel =   Whitebox.invokeMethod(this.service,"isNeedCancel",campaignTriggerTimeTask);
        Assert.assertEquals(Boolean.TRUE,isNeedCancel);
    }

    @Test
    public void testIsNeedCancelPreTaskQueueHasSize() throws Exception {

        CampaignHead campaignHead = new CampaignHead();
        campaignHead.setId(468);
        campaignHead.setName("xiexiaoliang_test_24");
        campaignHead.setPublishStatus(Byte.valueOf("3"));
        campaignHead.setStatus(Byte.valueOf("0"));
        TaskSchedule campaignTriggerTimeTask = new TaskSchedule();
        campaignTriggerTimeTask.setServiceName("campaignTriggerTimeTask");
        campaignTriggerTimeTask.setTaskStatus(Byte.valueOf("1"));
        campaignTriggerTimeTask.setStatus(Byte.valueOf("1"));
        campaignTriggerTimeTask.setCampaignHeadId(468);
        campaignTriggerTimeTask.setCampaignItemId("1489028037209");
        List<CampaignHead> campaignHeads = new ArrayList<>();
        campaignHeads.add(campaignHead);
        PowerMockito.when(this.campaignHeadDao.selectList(Mockito.any(CampaignHead.class))).thenReturn(campaignHeads);

        List<TaskSchedule> schedules = new ArrayList<>();
        schedules.add(campaignTriggerTimeTask);
        PowerMockito.when(this.taskScheduleDao.selectPreByCampaignIdAndItemId(Mockito.anyInt(),Mockito.anyString())).thenReturn(schedules);

        PowerMockito.doReturn(new ActiveMQTempQueue()).when(this.service,"getDynamicQueue",Mockito.anyString());
        PowerMockito.doReturn(1).when(this.service,"getQueueSize",Mockito.any(Queue.class));
        boolean isNeedCancel =   Whitebox.invokeMethod(this.service,"isNeedCancel",campaignTriggerTimeTask);
        Assert.assertEquals(Boolean.FALSE,isNeedCancel);
    }

    @Test
    public void teetUpdateTaskStatus() throws Exception {
        TaskSchedule campaignActionWaitTask = new TaskSchedule();
        campaignActionWaitTask.setServiceName("campaignActionWaitTask");
        campaignActionWaitTask.setTaskStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setCampaignHeadId(468);
        campaignActionWaitTask.setCampaignItemId("1489028042642");

        ScheduledFuture<?> scheduledFuture = null;
        PowerMockito.doReturn( true ).when( service, "isNeedCancel", Mockito.any());
        service.updateTaskStatus(campaignActionWaitTask);
        Mockito.verify(taskScheduleDao,Mockito.times(1)).updateById(Mockito.any(TaskSchedule.class));
    }
} 
