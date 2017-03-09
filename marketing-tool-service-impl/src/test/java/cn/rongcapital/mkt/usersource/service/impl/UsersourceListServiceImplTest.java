/*************************************************
 * @功能简述: UsersourceService 实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date:	2017.03.06
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.usersource.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import cn.rongcapital.mkt.usersource.po.Usersource;
import cn.rongcapital.mkt.usersource.service.UsersourceService;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class UsersourceListServiceImplTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	UsersourceService service;
	
	List<Usersource> list = new ArrayList<>();
	
	@Mock
	private UsersourceDao dao;
	
	@Before
	public void setUp() throws Exception {
		logger.info("测试：UsersourceService 开始---------------------");
		service = new UsersourceServiceImpl();
		ReflectionTestUtils.setField(service, "usersourceDao", dao);
		
		Usersource po = new Usersource();
		po.setId(1L);
		po.setName("Test");
		po.setIdentityId("ASDFGHJ");
		po.setAvailable(false);
		po.setDescription("description");
		list.add(po);
		
		Usersource po2 = new Usersource();
		po2.setId(1L);
		po2.setName("Test");
		po2.setIdentityId("ASDFGHJ");
		po2.setAvailable(true);
		po2.setDescription(null);
		list.add(po2);
		
	}
	
	/**
	 * 正常流程
	 */
	@Test
	public void testGetUsersourceList01_1() {
		
		Mockito.when(dao.selectListCount(Mockito.any())).thenReturn(20);
		Mockito.when(dao.selectList(Mockito.any())).thenReturn(list);
		
		BaseOutput result = service.getUsersourceList(1L, 2, 3);
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(2, result.getTotal());
		Assert.assertEquals(20, result.getTotalCount());

		Map<String,Object> po = (Map<String,Object>)result.getData().get(0);
		Assert.assertEquals(1L, po.get("id"));
		Assert.assertEquals("Test", po.get("name"));
		Assert.assertEquals("ASDFGHJ", po.get("identity_id"));
		Assert.assertEquals(0L, po.get("available"));
		Assert.assertEquals("description", po.get("description"));
	}
	
	/**
	 * 正常流程
	 */
	@Test
	public void testGetUsersourceList01_2() {
		
		Mockito.when(dao.selectListCount(Mockito.any())).thenReturn(20);
		Mockito.when(dao.selectList(Mockito.any())).thenReturn(list);
		
		BaseOutput result = service.getUsersourceList(1L, 2, 3);
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(2, result.getTotal());
		Assert.assertEquals(20, result.getTotalCount());

		Map<String,Object> po = (Map<String,Object>)result.getData().get(1);
		Assert.assertEquals(1L, po.get("id"));
		Assert.assertEquals("Test", po.get("name"));
		Assert.assertEquals("ASDFGHJ", po.get("identity_id"));
		Assert.assertEquals(1L, po.get("available"));
		Assert.assertEquals("", po.get("description"));
	}
	
	/**
	 * count 为0
	 */
	@Test
	public void testGetUsersourceList02() {
		
		Mockito.when(dao.selectListCount(Mockito.any())).thenReturn(0);
		Mockito.when(dao.selectList(Mockito.any())).thenReturn(list);
		
		BaseOutput result = service.getUsersourceList(1L, 2, 3);
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
		Assert.assertEquals(0, result.getTotalCount());

	}
	
	
	/**
	 * list为空
	 */
	@Test
	public void testGetUsersourceList03() {
		
		Mockito.when(dao.selectListCount(Mockito.any())).thenReturn(20);
		Mockito.when(dao.selectList(Mockito.any())).thenReturn(null);
		
		BaseOutput result = service.getUsersourceList(1L, 2, 3);
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
		Assert.assertEquals(0, result.getTotal());
		Assert.assertEquals(20, result.getTotalCount());
	}
}
