/*************************************************
 * @功能简述: UsersourceClassificationService 实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date:	2017.03.01
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.usersource.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
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
import cn.rongcapital.mkt.dao.usersource.UsersourceClassificationDao;
import cn.rongcapital.mkt.usersource.po.UsersourceClassification;
import cn.rongcapital.mkt.usersource.service.UsersourceClassificationService;
import cn.rongcapital.mkt.usersource.vo.in.UsersourceClassificationIn;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class UsersourceClassificationServiceImplTest {
	

	private Logger logger = LoggerFactory.getLogger(getClass());

	UsersourceClassificationService uscService;
	
	UsersourceClassificationIn in;
	
	List<UsersourceClassification> list = new ArrayList<UsersourceClassification>();
	
	@Mock
	private UsersourceClassificationDao dao;
	
	@Before
	public void setUp() throws Exception {
		logger.info("测试：UsersourceClassificationService 开始---------------------");
		uscService = new UsersourceClassificationServiceImpl();
		ReflectionTestUtils.setField(uscService, "classificationDao", dao);
		in = new UsersourceClassificationIn();
	}
	
	@Test
	public void testSaveUsersourceClassification01() {
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
		}).when(dao).insert(Mockito.any());
		
		Mockito.when(dao.selectList(Mockito.any())).thenReturn(null);
		in.setName("AAA");
		BaseOutput result = uscService.saveUsersourceClassification(in);
		
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
	}
	
	@Test
	public void testSaveUsersourceClassification02() {
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
		}).when(dao).insert(Mockito.any());
		
		Mockito.when(dao.selectList(Mockito.any())).thenReturn(null);
		in.setName("1*a");
		BaseOutput result = uscService.saveUsersourceClassification(in);
		
		Assert.assertEquals(4015, result.getCode());
		Assert.assertEquals(0, result.getTotal());
	}

	@Test
	public void testSaveUsersourceClassification03() {
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				return null;
			}
		}).when(dao).insert(Mockito.any());
		
		list.add(new UsersourceClassification());
		Mockito.when(dao.selectList(Mockito.any())).thenReturn(list);
		in.setName("AAA");
		BaseOutput result = uscService.saveUsersourceClassification(in);
		
		Assert.assertEquals(4016, result.getCode());
		Assert.assertEquals(0, result.getTotal());
	}
	
	@After
	public void tearDown() throws Exception {
		logger.info("测试：UsersourceClassificationService 结束---------------------");
	}
}
