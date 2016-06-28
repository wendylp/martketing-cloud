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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.OriginalDataArchPoint;

public interface OriginalDataArchPointDao extends BaseDao<OriginalDataArchPoint>{

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
	 * 批量插入数据
	 * @param List
	 * @return int
	 */
	int batchInsertUploadFileData(ArrayList<Map<String, Object>> insertList);
	
	/**
     * @功能简述 : 批量清洗且合并数据到DataArchPoint表
     * @author nianjun
     * @return map
     */
	int batchCleanData(List<OriginalDataArchPoint> originalDataArchPoints);

	/**
	 * 根据FileUnique获取id列表
	 * @param paramMap
	 * @return int
	 */
	List<Long> selelctIdListByFileUnique(Map<String, Object> paramMap);
}