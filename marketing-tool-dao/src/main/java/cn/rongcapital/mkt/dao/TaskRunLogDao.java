/*************************************************
 * @功能�?�?: DAO接口�?
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世�?
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审�?: 
*************************************************/

package cn.rongcapital.mkt.dao;

import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.TaskRunLog;

public interface TaskRunLogDao extends BaseDao<TaskRunLog>{
	
	//自定义扩�?
	/**
	 * 父类方法无法满足�?求时使用,�?在mapper.xml中扩�?
	 * 查询对象list;
	 * 自定义条件查�?,只要不为NULL与空则为条件,属�?��?�之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListBycustomMap(Map<String,Object> paramMap);
	
	//自定义扩�?
	/**
	 * 父类方法无法满足�?求时使用,�?在mapper.xml中扩�?
	 * 查询对象总数
	 * 自定义条件查�?,只要不为NULL与空则为条件,属�?��?�之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListCountBycustomMap(Map<String,Object> paramMap);
	
	List<TaskRunLog> selectLastOne(Map<String,Object> paramMap);

	/**
	 * 查询�?近发生的10个任�?
	 * @param
	 * @return list
	 */
	List<Object> taskRunLogList();
}