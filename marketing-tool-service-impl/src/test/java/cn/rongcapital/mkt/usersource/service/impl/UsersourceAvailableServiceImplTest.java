/*************************************************
 * @功能简述: UsersourceService 实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date:	2017.03.07
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.usersource.service.impl;

import java.util.List;

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
import cn.rongcapital.mkt.dao.usersource.UsersourceDao;
import cn.rongcapital.mkt.usersource.service.UsersourceService;
import cn.rongcapital.mkt.usersource.vo.in.UsersourceAvailableIn;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class UsersourceAvailableServiceImplTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	UsersourceService service;
	
	UsersourceAvailableIn in;
	
	@Mock
	private UsersourceDao dao;
	
	@Before
	public void setUp() throws Exception {
		logger.info("测试：UsersourceService 开始---------------------");
		service = new UsersourceServiceImpl();
		ReflectionTestUtils.setField(service, "usersourceDao", dao);
		in = new UsersourceAvailableIn();
		in.setId(1L);
		in.setAvailable(0L);
	}
	
	@Test
	public void testUsersourceAvailable01_1(){
		
		Mockito.when(dao.selectListCount(Mockito.any())).thenReturn(1);
		Mockito.when(dao.updateById(Mockito.any())).thenReturn(1);
		
		BaseOutput result = service.usersourceAvailable(in);
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
	}
	
	@Test
	public void testUsersourceAvailable01_2(){
		
		Mockito.when(dao.selectListCount(Mockito.any())).thenReturn(1);
		Mockito.when(dao.updateById(Mockito.any())).thenReturn(1);
		
		in.setAvailable(1L);
		BaseOutput result = service.usersourceAvailable(in);
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
	}
	
	@Test
	public void testUsersourceAvailable02(){
		
		Mockito.when(dao.selectListCount(Mockito.any())).thenReturn(0);
		Mockito.when(dao.updateById(Mockito.any())).thenReturn(1);
		
		BaseOutput result = service.usersourceAvailable(in);
		Assert.assertEquals(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
	}
	
	@After
	public void tearDown() throws Exception {
		logger.info("测试：UsersourceService 结束---------------------");
	}
}
