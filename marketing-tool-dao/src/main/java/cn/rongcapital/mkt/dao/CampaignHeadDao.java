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
import cn.rongcapital.mkt.po.CampaignHead;

public interface CampaignHeadDao extends BaseDao<CampaignHead>{
	
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
	
	/**
	 * 查询compaign_head表，对status=0(代表本条数据有效)的数据，
	 * 根据publish_status(各个值得含义下表中已经给�?)做统�?
	 * @param 
	 * @return list
	 */
	public List<Object> selectCampaignHeadCountGroupByPublishStatus();
	
	/**
	 * 对campaign_head表做count统计，获取�?�有多少个活�?
	 * @param 
	 * @return int
	 */
	public int selectCampaignCount();
	
	/**
	 * 根据publish_status以及campaign_name(如果有要用like做模糊查�?)从campaign_head表中查询
	 * @param paramMap
	 * @return list
	 */
	public List<CampaignHead> selectCampaignProgressStatusListByPublishStatus(Map<String,Object> paramMap);

	/**
	 * 主数据查询(主界面搜索栏里面的模糊查询)
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> searchDataMain(Map<String, Object> paramMap);
}