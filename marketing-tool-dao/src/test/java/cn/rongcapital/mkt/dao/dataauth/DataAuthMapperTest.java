package cn.rongcapital.mkt.dao.dataauth;

import java.util.List;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.dataauth.po.OutPutOrganization;

@RunWith(SpringJUnit4ClassRunner.class)
public class DataAuthMapperTest extends AbstractUnitTest{

	@Autowired
    private DataAuthMapper dataAuthMapper;
	
	Long resourceId = 1L;
	
	Long orgId = 15L;
	
	String tableName ="sms_templet";
	
	@Before
    public void init()
    {
        
    }
	
	@Test
	public void test01(){
		List<OutPutOrganization>  list = dataAuthMapper.getOrg(resourceId, orgId, tableName, "s");
		Assert.assertEquals(1, list.size());
	}
	@Test
	public void test02(){
		List<OutPutOrganization>  list = dataAuthMapper.getOrg(resourceId, orgId, tableName, null);
		Assert.assertEquals(0, list.size());
	}
	
	public void test03(){
		List<OutPutOrganization> list = dataAuthMapper.getOrgs(0, 0, null, null, 0, 0);
		Assert.assertEquals(0, list.size());
	}
	
	public void test04(){
		int size =dataAuthMapper.getOrgsTotleCount(resourceId, orgId, tableName, null);
		Assert.assertEquals(0, size);
	}
	
	
}
