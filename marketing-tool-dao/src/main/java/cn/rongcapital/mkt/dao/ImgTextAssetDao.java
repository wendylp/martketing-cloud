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
import cn.rongcapital.mkt.po.ImgTextAsset;

import java.util.List;
import java.util.Map;

public interface ImgTextAssetDao extends BaseDao<ImgTextAsset>{
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
	 * 通过ownerName查询图文资产
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectListByName(Map<String,Object> paramMap);

	/**
	 * 通过ownerName查询图文资产
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectListByNameAndType(Map<String,Object> paramMap);

	/**
	 * 通过imgtext_id删除图文资产
	 * @param paramMap
	 * @return list
	 */
	int logicDeleteAssetById(Map<String, Object> paramMap);

	/**
	 * 查询所有图文资产
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectList(Map<String, Object> paramMap);
	/**
	 * 根据type查询所有资产
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectListByType(Map<String, Object> paramMap);
}