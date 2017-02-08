package cn.rongcapital.mkt.dataauth.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dao.dataauth.OrganizationDao;
import cn.rongcapital.mkt.dataauth.po.Organization;
import cn.rongcapital.mkt.dataauth.service.OrganizationService;
import cn.rongcapital.mkt.dataauth.vo.OrgChildTreeOutPut;
import cn.rongcapital.mkt.dataauth.vo.OrgParentLineOutPut;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Mock
    private OrganizationDao organizationDao;
    
    OrganizationService organizationService;
    
	Organization org1 = new Organization(1l,null,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
	Organization org2 = new Organization(2l,1l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
	Organization org3 = new Organization(3l,1l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
	Organization org4 = new Organization(4l,1l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
	Organization org5 = new Organization(5l,2l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
	Organization org6 = new Organization(6l,2l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
	Organization org7 = new Organization(7l,3l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    
    @Before
    public void setUp() throws Exception {
    	logger.info("-------------------------测试开始---------------------------");
    	organizationService = new OrganizationServiceImpl();
    	ReflectionTestUtils.setField(organizationService, "organizationDao", organizationDao);

    }
    
	@Test
    public void getOrgTreeByIdTest() {
    	logger.info(org1.toString()+"_"+org2.toString()+"_"+org3.toString()+"_"+org4.toString()+"_"+org5.toString()+"_"+org6.toString()+"_"+org7.toString());
    	
    	List<Organization> clist = new ArrayList<>();
    	clist.add(org2);
    	clist.add(org3);
    	clist.add(org4);
    	
    	List<Organization> clist2 = new ArrayList<>();
    	clist2.add(org5);
    	clist2.add(org6);
    	
    	List<Organization> clist3 = new ArrayList<>();
    	clist3.add(org7);
    	
    	List<Organization> clist4 = new ArrayList<>();
    	
    	Mockito.when(organizationDao.getNodeById(Mockito.anyLong())).thenReturn(org1,org2,org5,org6,org3,org7,org4);
    	Mockito.when(organizationDao.getChildNodeById(Mockito.anyLong())).thenReturn(clist,clist2,clist4,clist4,clist3,clist4,clist4);
    	
    	OrgChildTreeOutPut ocop = organizationService.getOrgTreeById(1l);
    	
    	Assert.assertEquals(3, ocop.getOrgList().size());
    	for (int i = 0; i < ocop.getOrgList().size(); i++) {
    		Assert.assertEquals(i+2, ocop.getOrgList().get(i).getOrgId().intValue());
		}
    	
    	for (int i = 0; i < ocop.getOrgList().get(0).getOrgList().size(); i++) {
    		Assert.assertEquals(i+5, ocop.getOrgList().get(0).getOrgList().get(i).getOrgId().intValue());
    	}
    	
    	Assert.assertEquals(7, ocop.getOrgList().get(1).getOrgList().get(0).getOrgId().intValue());
    }
    
    @Test
    public void getOrgLineByIdTest() {
    	logger.info(org1.toString()+"_"+org2.toString()+"_"+org3.toString()+"_"+org4.toString()+"_"+org5.toString()+"_"+org6.toString()+"_"+org7.toString());
    	
    	Mockito.when(organizationDao.getNodeById(Mockito.anyLong())).thenReturn(org5,org2,org1,null);
    	OrgParentLineOutPut orgFL = organizationService.getOrgLineById(17l);
    	
    	Assert.assertEquals(5l,orgFL.getOrgId().longValue());
    	Assert.assertEquals(2l,orgFL.getOrganization().getOrgId().longValue());
    	Assert.assertEquals(1l,orgFL.getOrganization().getOrganization().getOrgId().longValue());
    }
    
    @Test
    public void getOrgLineListByIdTest() {
    	logger.info(org1.toString()+"_"+org2.toString()+"_"+org3.toString()+"_"+org4.toString()+"_"+org5.toString()+"_"+org6.toString()+"_"+org7.toString());
    	
    	Mockito.when(organizationDao.getNodeById(Mockito.anyLong())).thenReturn(org5,org2,org1,null);
    	List<Organization> orgList = organizationService.getOrgLineListById(17l);
    	
    	Assert.assertEquals(3,orgList.size());
    	Assert.assertEquals(5l,orgList.get(0).getOrgId().longValue());
    	Assert.assertEquals(2l,orgList.get(1).getOrgId().longValue());
    	Assert.assertEquals(1l,orgList.get(2).getOrgId().longValue());
    }
    
    @Test
    public void getChildOrgListByIdTest() {
    	
    	logger.info(org1.toString()+"_"+org2.toString()+"_"+org3.toString()+"_"+org4.toString()+"_"+org5.toString()+"_"+org6.toString()+"_"+org7.toString());
    	
    	List<Organization> clist = new ArrayList<>();
    	clist.add(org2);
    	clist.add(org3);
    	clist.add(org4);
    	
    	List<Organization> clist2 = new ArrayList<>();
    	clist2.add(org5);
    	clist2.add(org6);
    	
    	List<Organization> clist3 = new ArrayList<>();
    	clist3.add(org7);
    	
    	List<Organization> clist4 = new ArrayList<>();
    	
    	Mockito.when(organizationDao.getNodeById(Mockito.anyLong())).thenReturn(org1,org2,org5,org6,org3,org7,org4);
    	Mockito.when(organizationDao.getChildNodeById(Mockito.anyLong())).thenReturn(clist,clist2,clist4,clist4,clist3,clist4,clist4);
    	
    	
    	List<Organization> list = organizationService.getChildOrgListById(13l);
    	
    	Assert.assertEquals(6, list.size());
    	for (int i = 0; i < list.size(); i++) {
    		Assert.assertEquals(i+2, list.get(i).getOrgId().intValue());
		}
    	
    }
    
    @Test
    public void getOrgListByIdTest() {
    	
    	logger.info(org1.toString()+"_"+org2.toString()+"_"+org3.toString()+"_"+org4.toString()+"_"+org5.toString()+"_"+org6.toString()+"_"+org7.toString());
    	
    	List<Organization> clist = new ArrayList<>();
    	clist.add(org2);
    	clist.add(org3);
    	clist.add(org4);
    	
    	List<Organization> clist2 = new ArrayList<>();
    	clist2.add(org5);
    	clist2.add(org6);
    	
    	List<Organization> clist3 = new ArrayList<>();
    	clist3.add(org7);
    	
    	List<Organization> clist4 = new ArrayList<>();
    	
    	Mockito.when(organizationDao.getNodeById(Mockito.anyLong())).thenReturn(org1,org1,org2,org5,org6,org3,org7,org4);
    	Mockito.when(organizationDao.getChildNodeById(Mockito.anyLong())).thenReturn(clist,clist2,clist4,clist4,clist3,clist4,clist4);
    	
    	
    	BaseOutput result = organizationService.getOrgListById(13l);
    	List<Object> list = result.getData();
    	
    	Assert.assertEquals(7, list.size());
    	for (int i = 0; i < list.size(); i++) {
    		Organization org = (Organization)list.get(i);
    		Assert.assertEquals(i+1, org.getOrgId().longValue());
		}
    	
    }

    @After
    public void tearDown() throws Exception {
    	logger.info("-------------------------测试结束---------------------------");
    }
}
