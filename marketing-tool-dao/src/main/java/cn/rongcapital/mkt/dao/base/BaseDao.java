/*
 * Copyright (c) 2016 www.rongcapital.cn All rights reserved.
 */
package cn.rongcapital.mkt.dao.base;
import java.util.List;

/**
 * dao基类
 * @author songshitao
 */
public interface BaseDao<T> {
	/**
	 * 添加对象
	 * @param t
	 * @return 更新影响的条数
	 */
	int insert(T t);
	
	/**
	 * 更新对象,条件主键ID
	 * @param t
	 * @return 更新影响的条数
	 */
	int updateById(T t);
	
	/**
	 * 查询对象list
	 * 只要不为NULL与空则为条件,属性值之间and连接
	 * @param t
	 * @return
	 */
	List<T> selectList(T t);

	/**
	 * 查询对象list
	 * 查询条件:where id in(id1,id2...);非null的id才有效
	 * @param idList
	 * @return
	 */
	List<T> selectListByIdList(List<Integer> idList);

	/**
	 * 查询对象总数
	 * 只要不为NULL与空则为条件,属性值之间and连接
	 * @param t
	 * @return int
	 */
	int selectListCount(T t);
	
}
