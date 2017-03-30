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
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.HomePageSourceGroupCount;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.dao.DataPartyDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DataPartyDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private DataPartyDao dataPartyDao;
    
    @Before  
    public void setUp() throws Exception {
        logger.info("测试: DataPartyDao 开始---------------------");
    }
    
	@Test
	public void testGetDataById() {
	    logger.info("测试方法: getDataById ");    
	}
	
	@Test
	public void testGetPubUserCount() {
	    logger.info("测试方法: getPubUserCount ");    
	}
	
	@Test
	public void testSelectMainCount() {
	    logger.info("测试方法: selectMainCount ");    
	}
	
	@Test
	public void testBatchInsert() {
	    logger.info("测试方法: batchInsert ");    
	}
	
	@Test
	public void testGetDataPartyIdByKey() {
	    logger.info("测试方法: getDataPartyIdByKey ");    
	}
	
	@Test
	public void testSelectSourceGroupCount() {
	    logger.info("测试方法: selectSourceGroupCount ");
		DataParty dataParty = new DataParty();
		dataParty.setMobile("17010236548");
		dataParty.setName("沈小姐");
		dataParty.setGender(Byte.valueOf("2"));
		dataParty.setBirthday(new Date());
		dataParty.setProvice("湖北省");
		this.dataPartyDao.insert(dataParty);
		List<HomePageSourceGroupCount> homePageSourceGroupCounts = this.dataPartyDao.selectSourceGroupCount();
		Assert.assertNotNull(homePageSourceGroupCounts);

		logger.info("测试方法: selectSourceGroupCount ");

	}
	
	@Test
	public void testSelectIdByMappingId() {
	    logger.info("测试方法: selectIdByMappingId ");    
	}
	
	@Test
	public void testSelectListByKeyName() {
	    logger.info("测试方法: selectListByKeyName ");    
	}
	
	@Test
	public void testSelectAudienceDetail() {
	    logger.info("测试方法: selectAudienceDetail ");    
	}
	
	@Test
	public void testSelectDataPartyIdsByMappinKeyIds() {
	    logger.info("测试方法: selectDataPartyIdsByMappinKeyIds ");    
	}
	
	@Test
	public void testSelectDataSourceAndSourceCount() {
	    logger.info("测试方法: selectDataSourceAndSourceCount ");    
	}
	
	@Test
	public void testSelectNotNullMobile() {
	    logger.info("测试方法: selectNotNullMobile ");    
	}
	
	@Test
	public void testSelectCustomAudiencesByTagId() {
	    logger.info("测试方法: selectCustomAudiencesByTagId ");    
	}
	
	@Test
	public void testSelectListByNameInList() {
	    logger.info("测试方法: selectListByNameInList ");    
	}
	
	@Test
	public void testBatchInsertWechatDatas() {
	    logger.info("测试方法: batchInsertWechatDatas ");    
	}
	
	@Test
	public void testUpdateDataPartyInfo() {
	    logger.info("测试方法: updateDataPartyInfo ");    
	}
	
	@Test
	public void testSelectMonthlyCount() {
	    logger.info("测试方法: selectMonthlyCount ");    
	}
	
	@Test
	public void testSelectTotalOriginalCount() {
	    logger.info("测试方法: selectTotalOriginalCount ");    
	}
	
	@Test
	public void testSelectDataPartyIdbyWechatInfo() {
	    logger.info("测试方法: selectDataPartyIdbyWechatInfo ");    
	}
	
	@Test
	public void testUpdateStatusByIds() {
	    logger.info("测试方法: updateStatusByIds ");    
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
	
	@Test
	public void testSelectMobileById() {
	    logger.info("测试方法: selectMobileById ");    
	}
	
	@Test
	public void testSelectByBatchId() {
	    logger.info("测试方法: selectByBatchId ");    
	}
	
	@Test
	public void testSelectColumns() {
	    logger.info("测试方法: selectColumns ");    
	}
	
	@Test
	public void testSelectObjectById() {
	    logger.info("测试方法: selectObjectById ");    
	}
	
	@Test
	public void testSelectMappingKeyIds() {
	    logger.info("测试方法: selectMappingKeyIds ");    
	}
	
	@Test
	public void testSelectByMappingKeyId() {
	    logger.info("测试方法: selectByMappingKeyId ");    
	}
	
	@Test
	public void testSelectCountByBatchId() {
	    logger.info("测试方法: selectCountByBatchId ");    
	}
	
	@Test
	public void testGetAudiencesCount() {
	    logger.info("测试方法: getAudiencesCount ");    
	}
	
	@Test
	public void testSelectMappingKeyId() {
	    logger.info("测试方法: selectMappingKeyId ");    
	}
	
	@Test
	public void testSelectListByContactId() {
		
		List<DataParty> dataPartyList;
		dataPartyList = dataPartyDao.selectListByContactId(4);
		Assert.assertEquals(1, dataPartyList.size());
	    logger.info("测试方法: selectListByContactId ");    
	}
    
    @After
    public void tearDown() throws Exception {
        logger.info("测试: DataPartyDao 结束---------------------");
    }
    

}