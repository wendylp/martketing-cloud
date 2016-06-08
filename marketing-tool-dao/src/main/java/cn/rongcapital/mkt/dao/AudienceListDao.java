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

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.AudienceList;

import java.util.List;
import java.util.Map;

public interface AudienceListDao extends BaseDao<AudienceList>{
	
	//自定义扩�?
	/**
	 * 父类方法无法满足�?求时使用,�?在mapper.xml中扩�?
	 * 查询对象list;
	 * 自定义条件查�?,只要不为NULL与空则为条件,属�?��?�之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<AudienceList> selectListBycustomMap(Map<String,Object> t);
	
	//自定义扩�?
	/**
	 * 父类方法无法满足�?求时使用,�?在mapper.xml中扩�?
	 * 查询对象总数
	 * 自定义条件查�?,只要不为NULL与空则为条件,属�?��?�之间and连接
	 * @param paramMap
	 * @return list
	 */
	//List<T> selectListCountBycustomMap(Map<String,Object> paramMap);

	//自定义扩�?
	/**
	 * 微信资产下保存人群的工作
	 * @param paramMap
	 * @return int
	 */
	Integer insertWechatGroups(Map<String,Object> paramMap);

	/**
	 * 主数据查询(主界面搜索栏里面的模糊查询)
	 * @param paramMap
	 * @return int
	 */
	List<Map<String,Object>> searchDataMain(Map<String, Object> paramMap);
}