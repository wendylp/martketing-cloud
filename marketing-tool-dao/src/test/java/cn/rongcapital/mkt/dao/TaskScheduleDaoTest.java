/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.dao.TaskScheduleDao;

import cn.rongcapital.mkt.po.CampaignBody;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TaskScheduleDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private TaskScheduleDao taskScheduleDao;

    @Autowired
	private CampaignBodyDao campaignBodyDao;
    @Autowired
    private CampaignSwitchDao campaignSwitchDao;
    @Autowired
    private CampaignHeadDao campaignHeadDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: TaskScheduleDao 开始---------------------");
    }
    
	@Test
	public void testDeActivateTaskByCampaignHeadId() {
	    logger.info("测试方法: deActivateTaskByCampaignHeadId ");    
	}
	
	@Test
	public void testPhysicalDeleteById() {
	    logger.info("测试方法: physicalDeleteById ");    
	}
	
	@Test
	public void testActivateTaskByCampaignHeadId() {
	    logger.info("测试方法: activateTaskByCampaignHeadId ");    
	}
	
	@Test
	public void testSelectIdByServiceName() {
	    logger.info("测试方法: selectIdByServiceName ");    
	}
	
	@Test
	public void testInsert() {
	    logger.info("测试方法: insert ");    
	}
	
	@Test
	public void testSelectListCount() {
	    logger.info("测试方法: selectListCount ");    
	}
	
	@Test
	public void testUpdateById() {
	    logger.info("测试方法: updateById ");    
	}
	
	@Test
	public void testSelectList() {
	    logger.info("测试方法: selectList ");    
	}
	
	@Test
	public void testSelectListByIdList() {
	    logger.info("测试方法: selectListByIdList ");    
	}
	
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: TaskScheduleDao 结束---------------------");
    }
	@Test
	public void testSelectPreByCampaignIdAndItemId(){
		TaskSchedule campaignTriggerTimeTask = new TaskSchedule();
		campaignTriggerTimeTask.setServiceName("campaignTriggerTimeTask");
		campaignTriggerTimeTask.setTaskStatus(Byte.valueOf("0"));
		campaignTriggerTimeTask.setStatus(Byte.valueOf("1"));
		campaignTriggerTimeTask.setCampaignHeadId(-1);
		campaignTriggerTimeTask.setCampaignItemId("1489028037209");
		this.taskScheduleDao.insert(campaignTriggerTimeTask);

		TaskSchedule campaignAudienceTargetTask = new TaskSchedule();
		campaignAudienceTargetTask.setServiceName("campaignAudienceTargetTask");
		campaignAudienceTargetTask.setTaskStatus(Byte.valueOf("0"));
		campaignAudienceTargetTask.setStatus(Byte.valueOf("1"));
		campaignAudienceTargetTask.setCampaignHeadId(-1);
		campaignAudienceTargetTask.setCampaignItemId("1489028040010");
		this.taskScheduleDao.insert(campaignAudienceTargetTask);

		TaskSchedule campaignActionWaitTask = new TaskSchedule();
		campaignActionWaitTask.setServiceName("campaignActionWaitTask");
		campaignActionWaitTask.setTaskStatus(Byte.valueOf("1"));
		campaignActionWaitTask.setStatus(Byte.valueOf("1"));
		campaignActionWaitTask.setCampaignHeadId(-1);
		campaignActionWaitTask.setCampaignItemId("1489028042642");
		this.taskScheduleDao.insert(campaignActionWaitTask);

		CampaignHead campaignHead = new CampaignHead();
		campaignHead.setId(-1);
		campaignHead.setName("xiexiaoliang_test_24");
		campaignHead.setPublishStatus(Byte.valueOf("3"));
		campaignHead.setStatus(Byte.valueOf("0"));
		this.campaignHeadDao.insert(campaignHead);

		CampaignSwitch campaignSwitch1 = new CampaignSwitch();
		campaignSwitch1.setId(2925);
		campaignSwitch1.setCampaignHeadId(-1);
		campaignSwitch1.setItemId("1489028037209");
		campaignSwitch1.setType(Byte.valueOf("2"));
		campaignSwitch1.setDrawType(Byte.valueOf("0"));
		campaignSwitch1.setColor("#787878");
		campaignSwitch1.setNextItemId("1489028040010");
		campaignSwitch1.setStatus(Byte.valueOf("0"));
		this.campaignSwitchDao.insert(campaignSwitch1);

		CampaignSwitch campaignSwitch2 = new CampaignSwitch();
		campaignSwitch2.setId(2926);
		campaignSwitch2.setCampaignHeadId(-1);
		campaignSwitch2.setItemId("1489028040010");
		campaignSwitch2.setType(Byte.valueOf("2"));
		campaignSwitch2.setDrawType(Byte.valueOf("0"));
		campaignSwitch2.setColor("#787878");
		campaignSwitch2.setNextItemId("1489028042642");
		campaignSwitch2.setStatus(Byte.valueOf("0"));
		this.campaignSwitchDao.insert(campaignSwitch2);

		CampaignBody body = new CampaignBody();
		body.setId(4604);
		body.setHeadId(-1);
		body.setNodeType(Byte.valueOf("0"));
		body.setItemType(Byte.valueOf("0"));
		body.setItemId("1489028037209");
		body.setTaskId(7692);
		body.setStatus(Byte.valueOf("0"));
		this.campaignBodyDao.insert(body);

		CampaignBody body1 = new CampaignBody();
		body1.setId(4605);
		body1.setHeadId(-1);
		body1.setNodeType(Byte.valueOf("1"));
		body1.setItemType(Byte.valueOf("0"));
		body1.setItemId("1489028040010");
		body1.setTaskId(7693);
		body1.setStatus(Byte.valueOf("0"));
		this.campaignBodyDao.insert(body1);

		CampaignBody body2 = new CampaignBody();
		body2.setId(4606);
		body2.setHeadId(-1);
		body2.setNodeType(Byte.valueOf("3"));
		body2.setItemType(Byte.valueOf("0"));
		body2.setItemId("1489028042642");
		body2.setTaskId(7694);
		body2.setStatus(Byte.valueOf("0"));
		this.campaignBodyDao.insert(body2);

		List<TaskSchedule> schedules = this.taskScheduleDao.selectPreByCampaignIdAndItemId(-1, "1489028040010");
		Assert.assertNotNull(schedules);
	}
}