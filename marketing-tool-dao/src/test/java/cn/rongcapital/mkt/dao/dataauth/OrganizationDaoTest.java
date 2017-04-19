/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date: 2017.02.08
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.dao.dataauth;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.dataauth.po.Organization;

public class OrganizationDaoTest extends AbstractUnitTest{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private OrganizationDao organizationDao;
    
    private Organization organization = new Organization(null,-21l,"Test",null);
    
    @Before
    public void setUp() throws Exception {
    	logger.info("--------------------- Organization  测试开始 -------------------- ");
    	organizationDao.insert(organization);
    }
    
    @Test
    public void test01() {
    	
    	Organization  org = organizationDao.getNodeById(organization.getOrgId());
    	Assert.assertEquals("Test",org.getName());
    	Assert.assertEquals(-21l,org.getParentId().longValue());
    	
    	List<Organization> list = organizationDao.getChildNodeById(organization.getParentId());
    	Assert.assertEquals(1,list.size());
    	Assert.assertEquals("Test",list.get(0).getName());
    	Assert.assertEquals(-21l,list.get(0).getParentId().longValue());
    }
    
    @After
    public void tearDown() throws Exception {
    	organization.setStatus((byte)1);
    	organizationDao.updateById(organization);
    	logger.info("--------------------- Organization  测试结束 -------------------- ");
    }
}
