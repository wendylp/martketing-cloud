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
import cn.rongcapital.mkt.po.WechatAsset;

import java.util.List;
import java.util.Map;

public interface WechatAssetDao extends BaseDao<WechatAsset>{
	
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
	 * 根据微信资产种类获取相关的资产
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> getWechatCountByType(Map<String,Object> paramMap);

	/**
	 * 根据资产类型获取资产列表
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectAssetListByType(Map<String, Object> paramMap);

	/**
	 * 根据资产Id获取资产详细信息
	 * @param paramMap
	 * @return list
	 */
	Map<String,Object> selectWechatAssetDetai(Map<String, Object> paramMap);

	/**
	 * 根据资产Id修改资产昵称
	 * @param paramMap
	 * @return int
	 */
	int updateNicknameById(Map<String, Object> paramMap);
}