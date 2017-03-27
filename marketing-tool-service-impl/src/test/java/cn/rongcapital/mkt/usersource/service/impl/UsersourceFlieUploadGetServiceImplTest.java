package cn.rongcapital.mkt.usersource.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.common.serialize.SerializeUtil;
import cn.rongcapital.mkt.dao.usersource.UsersourceClassificationDao;
import cn.rongcapital.mkt.dao.usersource.UsersourceDao;
import cn.rongcapital.mkt.usersource.UsersourceFlieUploadGetServiceImpl;
import cn.rongcapital.mkt.usersource.po.Usersc;
import cn.rongcapital.mkt.usersource.po.Usersource;
import cn.rongcapital.mkt.usersource.po.UsersourceClassification;
import cn.rongcapital.mkt.vo.BaseOutput;


@RunWith(PowerMockRunner.class)
@PrepareForTest({JedisClient.class}) 
@PowerMockIgnore("javax.management.*")
public class UsersourceFlieUploadGetServiceImplTest {
	 
	@Mock
	private UsersourceDao usersourceDao;
	
	@InjectMocks
	private UsersourceFlieUploadGetServiceImpl service;
	
	@Mock
	private UsersourceClassificationDao usersourceClassificationDao;
	
	Usersource us = new Usersource();
	
	UsersourceClassification uc = new UsersourceClassification();
	
	List<Usersource> usList = new ArrayList<Usersource>();
	
	List<UsersourceClassification> ucList = new ArrayList<UsersourceClassification>();
	
	byte[] byt = null;
	
	@Before
    public void init(){
		us.setInitialData(true);
		usList.add(us);
		uc.setInitialData(true);
		ucList.add(uc);
		PowerMockito.mockStatic(JedisClient.class);
		List<Usersc> list = new ArrayList<Usersc>();
		Usersc usc = new Usersc();
		usc.setName("用户来源");
		usc.setPrimaryClassification("一级分类");
		usc.setTwoLevelClassification("二级分类");
		usc.setThreeLevelClassification("三级分类");
		list.add(usc);
		byt = SerializeUtil.serialize(list);
    }
	
	@Test
	public void test00() throws Exception{
		String fileId = "1";
		PowerMockito.when(JedisClient.get(fileId.getBytes())).thenReturn(null);
		BaseOutput output = service.importUsersourceDate(fileId);
		Assert.assertEquals(ApiErrorCode.ID_NOTFOUND_ERROR.getCode(), output.getCode());
	}
	
	@Test
	public void test01() throws Exception{
		String fileId = "1";
		PowerMockito.when(JedisClient.get(fileId.getBytes())).thenReturn(byt);
		PowerMockito.when(usersourceDao.selectListByInitialData(Mockito.any())).thenReturn(usList);
		PowerMockito.when(usersourceClassificationDao.selectListByInitialData(Mockito.any())).thenReturn(ucList);
		BaseOutput output = service.importUsersourceDate(fileId);
		Assert.assertEquals(ApiErrorCode.USERSOURCE_CLASSIFICATION_IMP_ERROR.getCode(), output.getCode());
	}
	
	@Test
	public void test02() throws JedisException{
		String fileId = "1";
		List<Usersource> usList1 = new ArrayList<Usersource>();
		List<UsersourceClassification> ucList1 = new ArrayList<UsersourceClassification>();
		PowerMockito.when(JedisClient.get(fileId.getBytes())).thenReturn(byt);
		PowerMockito.when(usersourceDao.selectListByInitialData(Mockito.any())).thenReturn(usList1);
		PowerMockito.when(usersourceClassificationDao.selectListByInitialData(Mockito.any())).thenReturn(ucList1);
		PowerMockito.when(usersourceClassificationDao.selectList(Mockito.any())).thenReturn(ucList);
		Mockito.verify(usersourceClassificationDao, Mockito.never()).insert(uc);
		Mockito.verify(usersourceDao, Mockito.never()).insert(us);
		BaseOutput output = service.importUsersourceDate(fileId);
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
	}
	
}
