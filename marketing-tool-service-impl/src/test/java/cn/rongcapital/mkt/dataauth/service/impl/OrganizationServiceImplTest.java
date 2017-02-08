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

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.dataauth.OrganizationDao;
import cn.rongcapital.mkt.dataauth.po.Organization;
import cn.rongcapital.mkt.dataauth.service.OrganizationService;
import cn.rongcapital.mkt.dataauth.vo.OrgChildTreeOutPut;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Mock
    private OrganizationDao organizationDao;
    
    OrganizationService organizationService;
    
    @Before
    public void setUp() throws Exception {
    	logger.info("-------------------------测试开始---------------------------");
    	organizationService = new OrganizationServiceImpl();
    	ReflectionTestUtils.setField(organizationService, "organizationDao", organizationDao);

    }
    
    @Test
    public void testOrg01() {
    	
    	Organization org1 = new Organization(1l,null,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org2 = new Organization(2l,1l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org3 = new Organization(3l,1l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org4 = new Organization(4l,1l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org5 = new Organization(5l,2l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org6 = new Organization(6l,2l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org7 = new Organization(7l,3l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	logger.info(org1.toString(),org2.toString(),org3.toString(),org4.toString(),org5.toString(),org6.toString(),org7.toString());
    	
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
    public void testOrg02() {
    	
    	organizationService.getOrgLineById(17l);
    	
    }
    
    @Test
    public void testOrg03() {
    	
    	organizationService.getOrgLineListById(17l);
    	
    }
    
    @Test
    public void testOrg04() {
    	
    	Organization org1 = new Organization(1l,null,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org2 = new Organization(2l,1l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org3 = new Organization(3l,1l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org4 = new Organization(4l,1l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org5 = new Organization(5l,2l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org6 = new Organization(6l,2l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	Organization org7 = new Organization(7l,3l,"Test数据_"+UUID.randomUUID().toString().substring(0, 5),(byte) 0);
    	logger.info(org1.toString(),org2.toString(),org3.toString(),org4.toString(),org5.toString(),org6.toString(),org7.toString());
    	
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

    @After
    public void tearDown() throws Exception {
    	logger.info("-------------------------测试结束---------------------------");
    }
}
