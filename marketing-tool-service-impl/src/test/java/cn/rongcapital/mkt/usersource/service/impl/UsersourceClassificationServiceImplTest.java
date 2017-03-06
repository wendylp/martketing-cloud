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
import cn.rongcapital.mkt.dao.usersource.UsersourceDao;
import cn.rongcapital.mkt.usersource.po.UsersourceClassification;
import cn.rongcapital.mkt.usersource.service.UsersourceClassificationService;
import cn.rongcapital.mkt.usersource.vo.in.UsersourceClassificationIn;
import cn.rongcapital.mkt.usersource.vo.out.UsersourceClassificationOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class UsersourceClassificationServiceImplTest {
	

	private Logger logger = LoggerFactory.getLogger(getClass());

	UsersourceClassificationService uscService;
	
	UsersourceClassificationIn in;
	
	List<UsersourceClassification> list = new ArrayList<UsersourceClassification>();
	
	List<UsersourceClassificationOut> rootList = new ArrayList<>();
	List<UsersourceClassificationOut> list2 = new ArrayList<>();
	List<UsersourceClassificationOut> list3 = new ArrayList<>();
	List<UsersourceClassificationOut> list4 = new ArrayList<>();
	
	UsersourceClassificationOut node1 = new UsersourceClassificationOut(1L,-1L,"node1",0L);
	UsersourceClassificationOut node2 = new UsersourceClassificationOut(2L,-1L,"node2",0L);
	UsersourceClassificationOut node3 = new UsersourceClassificationOut(3L,1L,"node3",0L);
	UsersourceClassificationOut node4 = new UsersourceClassificationOut(4L,1L,"node4",0L);
	UsersourceClassificationOut node5 = new UsersourceClassificationOut(5L,3L,"node5",0L);
	UsersourceClassificationOut node6 = new UsersourceClassificationOut(6L,2L,"node6",0L);
	
	@Mock
	private UsersourceClassificationDao dao;
	
	@Mock
	private UsersourceDao usersourceDao;
	
	@Before
	public void setUp() throws Exception {
		logger.info("测试：UsersourceClassificationService 开始---------------------");
		uscService = new UsersourceClassificationServiceImpl();
		ReflectionTestUtils.setField(uscService, "classificationDao", dao);
		ReflectionTestUtils.setField(uscService, "usersourceDao", usersourceDao);
		in = new UsersourceClassificationIn();
		
		rootList.add(node1);
		rootList.add(node2);
		list2.add(node3);
		list2.add(node4);
		list3.add(node5);
		list4.add(node6);
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
	
	@Test
	public void testClassificationList01() {
		
		Mockito.when(dao.selectClassificationList(Mockito.any())).thenReturn(rootList,list2,list3,null,null,list4,null);
		Mockito.when(usersourceDao.selectListCount(Mockito.any())).thenReturn(2,3,4);
		
		BaseOutput result = uscService.classificationList();
		List<Object> list = result.getData();
		Assert.assertEquals(2, result.getTotal());
		for (int i = 0; i < list.size(); i++) {
			UsersourceClassificationOut temp = (UsersourceClassificationOut)list.get(i);
			Assert.assertEquals(i+1, temp.getId().intValue());
			Assert.assertEquals("node"+(i+1), temp.getName());
			Assert.assertEquals(0, temp.getCount().intValue());
		}
		
		UsersourceClassificationOut n1 = (UsersourceClassificationOut)list.get(0);
		Assert.assertEquals(2, n1.getList().size());
		for (int i = 0; i < n1.getList().size(); i++) {
			UsersourceClassificationOut temp = (UsersourceClassificationOut)n1.getList().get(i);
			Assert.assertEquals(i+3, temp.getId().intValue());
			Assert.assertEquals("node"+(i+3), temp.getName());
		}
		Assert.assertEquals(3, n1.getList().get(1).getCount().intValue());
		
		UsersourceClassificationOut n3 = (UsersourceClassificationOut)n1.getList().get(0);
		UsersourceClassificationOut n5 = (UsersourceClassificationOut)n3.getList().get(0);
		Assert.assertEquals(5, n5.getId().intValue());
		Assert.assertEquals(2, n5.getCount().intValue());
		
		UsersourceClassificationOut n2 = (UsersourceClassificationOut)list.get(1);
		UsersourceClassificationOut n6 = (UsersourceClassificationOut)n2.getList().get(0);
		Assert.assertEquals(6, n6.getId().intValue());
		Assert.assertEquals(4, n6.getCount().intValue());
		
	}
	
	@Test
	public void testClassificationList02() {
		
		Mockito.when(dao.selectClassificationList(Mockito.any())).thenReturn(rootList,null);
		Mockito.when(usersourceDao.selectListCount(Mockito.any())).thenReturn(2,0);
		
		BaseOutput result = uscService.classificationList();
		List<Object> list = result.getData();
		Assert.assertEquals(2, result.getTotal());
		UsersourceClassificationOut n1 = (UsersourceClassificationOut)list.get(0);
		Assert.assertEquals(1, n1.getId().intValue());
		Assert.assertEquals(2, n1.getCount().intValue());
		
		UsersourceClassificationOut n2 = (UsersourceClassificationOut)list.get(1);
		Assert.assertEquals(2, n2.getId().intValue());
		Assert.assertEquals(0, n2.getCount().intValue());
		Assert.assertEquals(0, n2.getList().size());
		
	}
	
	@Test
	public void testClassificationList03() {
		
		Mockito.when(dao.selectClassificationList(Mockito.any())).thenReturn(null);
		Mockito.when(usersourceDao.selectListCount(Mockito.any())).thenReturn(2,0);
		
		BaseOutput result = uscService.classificationList();
		List<Object> list = result.getData();
		Assert.assertEquals(0, result.getTotal());
		Assert.assertEquals(0, list.size());
		
	}
	
	@After
	public void tearDown() throws Exception {
		logger.info("测试：UsersourceClassificationService 结束---------------------");
	}
}
