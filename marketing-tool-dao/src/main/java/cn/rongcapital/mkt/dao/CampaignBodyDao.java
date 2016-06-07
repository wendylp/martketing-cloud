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
import cn.rongcapital.mkt.po.CampaignBody;

public interface CampaignBodyDao extends BaseDao<CampaignBody>{

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
	
	//自定义扩�?
	/**
	 * 对campaign_body表中的audience_count做sum
	 * @param 
	 * @return int
	 */
	int selectCampaignAudienceCount();
}