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

import java.util.*;
import java.util.concurrent.*;

import javax.jms.JMSException;
import javax.jms.Queue;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.job.util.ScheduledFutureExecutor;
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
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
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
    @Test
    public  void testValidateAndUpdateTaskStatus() throws Exception {
        TaskSchedule taskSchedule = new TaskSchedule();
        ScheduledFutureExecutor scheduledScheduledFutureExecutor = null;
        PowerMockito.doReturn( true ).when( service, "validCampaignHead", Mockito.any());
        PowerMockito.doReturn( true ).when( service, "validPreNode", Mockito.any());
        PowerMockito.doReturn( true ).when( service, "validQueueSize", Mockito.any());
        PowerMockito.doReturn( true ).when( service, "cancelCampaignInnerTask", Mockito.any());
        PowerMockito.doReturn( true ).when( service, "validScheduleFutureRunning", Mockito.any());
        PowerMockito.doNothing().when( service, "updateDataStatus", Mockito.any());
        boolean result = this.service.validateAndUpdateTaskStatus(taskSchedule, scheduledScheduledFutureExecutor);
        Assert.assertEquals(Boolean.TRUE,result);
    }
    @Test
    public  void testValidateAndUpdateTaskStatusFalse() throws Exception {
        TaskSchedule taskSchedule = new TaskSchedule();
        ScheduledFutureExecutor scheduledScheduledFutureExecutor = null;
        PowerMockito.doReturn( false ).when( service, "validCampaignHead", Mockito.any());
        PowerMockito.doReturn( false ).when( service, "validPreNode", Mockito.any());
        PowerMockito.doReturn( false ).when( service, "validQueueSize", Mockito.any());
        PowerMockito.doReturn( false ).when( service, "cancelCampaignInnerTask", Mockito.any());
        PowerMockito.doReturn( false ).when( service, "validScheduleFutureRunning", Mockito.any());
        PowerMockito.doNothing().when( service, "updateDataStatus", Mockito.any());
        boolean result = this.service.validateAndUpdateTaskStatus(taskSchedule, scheduledScheduledFutureExecutor);
        Assert.assertEquals(Boolean.FALSE,result);
    }

    @Test
    public  void testValidAndUpdateTaskSchedule() throws Exception {
        TaskSchedule taskSchedule = new TaskSchedule();
        ScheduledFutureExecutor scheduledScheduledFutureExecutor = null;
        PowerMockito.doReturn( true ).when( service, "validCampaignHead", Mockito.any());
        PowerMockito.doReturn( true ).when( service, "validPreNode", Mockito.any());
        PowerMockito.doReturn( true ).when( service, "validQueueSize", Mockito.any());
        PowerMockito.doReturn( true ).when( service, "cancelCampaignInnerTask", Mockito.any());
        PowerMockito.doReturn( true ).when( service, "validScheduleFutureRunning", Mockito.any());
        PowerMockito.doNothing().when( service, "updateDataStatus", Mockito.any());
        boolean result =   Whitebox.invokeMethod(this.service,"validAndUpdateTaskSchedule",taskSchedule);
        Assert.assertEquals(Boolean.TRUE,result);
    }

    @Test
    public  void testValidAndUpdateTaskScheduleFalse() throws Exception {
        TaskSchedule taskSchedule = new TaskSchedule();
        PowerMockito.doReturn( false ).when( service, "validCampaignHead", Mockito.any());
        PowerMockito.doReturn( false ).when( service, "validPreNode", Mockito.any());
        PowerMockito.doReturn( false ).when( service, "validQueueSize", Mockito.any());
        PowerMockito.doReturn( false ).when( service, "cancelCampaignInnerTask", Mockito.any());
        PowerMockito.doReturn( false ).when( service, "validScheduleFutureRunning", Mockito.any());
        PowerMockito.doNothing().when( service, "updateDataStatus", Mockito.any());
        boolean result =   Whitebox.invokeMethod(this.service,"validAndUpdateTaskSchedule",taskSchedule);
        Assert.assertEquals(Boolean.FALSE,result);
    }




    @Test
    public void testValidScheduleFutureRunning() throws Exception {
        ScheduledFuture<?> scheduledFuture = null;
    ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        Date startTime = new Date();
        ConcurrentTaskScheduler concurrentTaskScheduler = new ConcurrentTaskScheduler(scheduledExecutor);

        ScheduledFuture<?> schedule = concurrentTaskScheduler.schedule(new Runnable() {
            public void run() {

            }
        }, startTime);
        Thread.sleep(1*1000);
        ScheduledFutureExecutor executor = new ScheduledFutureExecutor(scheduledFuture, scheduledExecutor) ;

        boolean result =   Whitebox.invokeMethod(this.service,"validScheduleFutureRunning",executor);
        Assert.assertEquals(Boolean.FALSE,result);
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
        boolean result =   Whitebox.invokeMethod(this.service,"validQueueSize",campaignTriggerTimeTask);
        Assert.assertEquals(Boolean.TRUE,result);
    }

    @Test
    public void testUpdateDataStatus() throws Exception {

        TaskSchedule campaignActionWaitTask = new TaskSchedule();
        campaignActionWaitTask.setServiceName("campaignActionWaitTask");
        campaignActionWaitTask.setTaskStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setStatus(Byte.valueOf("1"));
        campaignActionWaitTask.setCampaignHeadId(468);
        campaignActionWaitTask.setCampaignItemId("1489028042642");

        Whitebox.invokeMethod(this.service,"updateDataStatus",campaignActionWaitTask);
        campaignActionWaitTask.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);
        campaignActionWaitTask.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
        Mockito.verify(this.taskScheduleDao,Mockito.times(1)).updateById(campaignActionWaitTask);
    }
    @Test
    public  void testValidPreNode() throws Exception {
        TaskSchedule campaignTriggerTimeTask = new TaskSchedule();
        campaignTriggerTimeTask.setServiceName("campaignTriggerTimeTask");
        campaignTriggerTimeTask.setTaskStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setStatus(Byte.valueOf("0"));
        campaignTriggerTimeTask.setCampaignHeadId(468);
        campaignTriggerTimeTask.setCampaignItemId("1489028037209");

        List<TaskSchedule> schedules = new ArrayList<>();
        schedules.add(campaignTriggerTimeTask);
        PowerMockito.when(this.taskScheduleDao.selectPreByCampaignIdAndItemId(Mockito.anyInt(),Mockito.anyString())).thenReturn(schedules);

        boolean result =   Whitebox.invokeMethod(this.service,"validPreNode",campaignTriggerTimeTask);
        Assert.assertEquals(Boolean.TRUE,result);
    }

    @Test
    public  void testValidCampaignHead() throws Exception {
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
        boolean result =   Whitebox.invokeMethod(this.service,"validCampaignHead",campaignTriggerTimeTask);
        Assert.assertEquals(Boolean.TRUE,result);
    }
} 
