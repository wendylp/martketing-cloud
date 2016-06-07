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

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.po.CustomTagWithName;

public interface CustomTagMapDao extends BaseDao<CustomTagMap>{
	
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
	
	void batchDeleteUseHeadId(Integer headerId);
	
	List<CustomTagWithName> getTagUseHeadId(Long headerId);
}