/*************************************************
 * @功能简述: UsersourceClassificationService 实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date:	2017.03.02
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.usersource.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.usersource.UsersourceDao;
import cn.rongcapital.mkt.usersource.po.Usersource;
import cn.rongcapital.mkt.usersource.service.UsersourceService;
import cn.rongcapital.mkt.usersource.vo.in.UsersourceIn;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class UsersourceServiceImplTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	UsersourceService service;
	
	UsersourceIn in;
	
	List<Usersource> list = new ArrayList<>();
	
	@Mock
	private UsersourceDao dao;
	
	@Before
	public void setUp() throws Exception {
		logger.info("测试：UsersourceClassificationService 开始---------------------");
		service = new UsersourceServiceImpl();
		ReflectionTestUtils.setField(service, "usersourceDao", dao);
		in = new UsersourceIn();
	}
	
	@Test
	public void testSaveUsersource01() {
		
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
		}).when(dao).insert(Mockito.any());
		Mockito.when(dao.selectList(Mockito.any())).thenReturn(null);
		in.setName("123");
		BaseOutput result = service.saveUsersource(in);
		
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
	}
	
	@Test
	public void testSaveUsersource02() {
		
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
		}).when(dao).insert(Mockito.any());
		
		Mockito.when(dao.selectList(Mockito.any())).thenReturn(list);
		in.setName("123");
		BaseOutput result = service.saveUsersource(in);
		
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
	}
	
	@Test
	public void testSaveUsersource03() {
		
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
		}).when(dao).insert(Mockito.any());
		
		Mockito.when(dao.selectList(Mockito.any())).thenReturn(list);
		in.setName("12\3");
		BaseOutput result = service.saveUsersource(in);
		
		Assert.assertEquals(4015, result.getCode());
		Assert.assertEquals(0, result.getTotal());
	}
	
	@Test
	public void testSaveUsersource04() {
		
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
		}).when(dao).insert(Mockito.any());
		
		list.add(new Usersource());
		Mockito.when(dao.selectList(Mockito.any())).thenReturn(list);
		in.setName("123");
		BaseOutput result = service.saveUsersource(in);
		
		Assert.assertEquals(4016, result.getCode());
		Assert.assertEquals(0, result.getTotal());
	}
}
