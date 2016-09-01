/*************************************************
 * @功能简述: DAO接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.ContactList;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ContactListDao extends BaseDao<ContactList>{

	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象list;
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListBycustomMap(Map<String,Object> paramMap);
	
	//自定义扩展
	/**
	 * 父类方法无法满足需求时使用,需在mapper.xml中扩展
	 * 查询对象总数
	 * 自定义条件查询,只要不为NULL与空则为条件,属性值之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListCountBycustomMap(Map<String,Object> paramMap);

	/**
	 * 获取不同的keyId的总数
	 * @param contactList
	 * @return list
	 */
	List<Integer> selectDistinctKeyidList(ContactList contactList);

	/**
	 * 获取今天的访问次数
	 * @param paramMap
	 * @return list
	 */
	Integer selectTodayCommitCount(Map<String, Object> paramMap);
	
	/**
	 * 根据表单id更新
	 * @param t
	 * @return
	 */
	int updateByContactId(ContactList t);
	
	/**
	 * 
	 * @param contactList
	 * @return
	 */
	List<ContactList> selectListByContactIdAndCommitTime(ContactList contactList);
}