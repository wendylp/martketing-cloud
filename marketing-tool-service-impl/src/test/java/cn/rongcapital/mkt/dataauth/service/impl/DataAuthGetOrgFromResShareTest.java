package cn.rongcapital.mkt.dataauth.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import cn.rongcapital.mkt.dao.dataauth.DataAuthMapper;
import cn.rongcapital.mkt.dataauth.po.OutPutOrganization;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class DataAuthGetOrgFromResShareTest {

    @Mock
    private DataAuthMapper dataAuthMapper;
    
    private DataAuthService dataAuthService;
    
    private long resourceId =1L;
    
    private long orgId = 1L;
    
    private String tableName = "a";
    
    private String type1 = "toOrgs";
    
    private String type2 = "orgTo";
    
    private String oprType = "S";
    
    private int index = 0;
    
    private int size = 10;
    
    List<OutPutOrganization> list = new ArrayList<OutPutOrganization>();
    
    @Before
    public void setUp() throws Exception {
        dataAuthService = new DataAuthServiceImpl();
        OutPutOrganization out = new OutPutOrganization();
        out.setName("a");
        out.setOrgId(1L);
        out.setResourceId(1L);
        list.add(out);
    }
    
    @Test
    public void getOrgFromResShare01(){
    	
    	Mockito.when(dataAuthMapper.getOrgs(Mockito.any(long.class), Mockito.any(long.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(int.class), Mockito.any(int.class))).thenReturn(list);
        ReflectionTestUtils.setField(dataAuthService, "dataAuthMapper", dataAuthMapper);
        
        BaseOutput outPut = dataAuthService.getOrgFromResShare(resourceId, orgId, tableName, type1, oprType, index, size);
        Assert.assertEquals(1, outPut.getData().size());
    }
    
    @Test
    public void getOrgFromResShare02(){
    	
    	Mockito.when(dataAuthMapper.getOrg(Mockito.any(long.class), Mockito.any(long.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(list);
        ReflectionTestUtils.setField(dataAuthService, "dataAuthMapper", dataAuthMapper);
        BaseOutput outPut = dataAuthService.getOrgFromResShare(resourceId, orgId, tableName, type2, oprType, index, size);
        Assert.assertEquals(1, outPut.getData().size());
    }
    
    @After
    public void tearDown() throws Exception {
    	
    }
}
