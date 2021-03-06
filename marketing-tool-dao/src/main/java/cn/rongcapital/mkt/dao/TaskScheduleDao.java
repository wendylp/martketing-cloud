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
import cn.rongcapital.mkt.po.TaskSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskScheduleDao extends BaseDao<TaskSchedule>{
	
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
	
	public void physicalDeleteById(int taskId);
	
	public void activateTaskByCampaignHeadId(int campaignHeadId);
	
	public void deActivateTaskByCampaignHeadId(int campaignHeadId);

	/**
	 * 根据服务名称选择服务Id
	 * @param paramMap
	 * @return list
	 */
	public int selectIdByServiceName(String serviceName);

	/**
	 * 查询此任务参与活动的上一个节点任务
	 * @param campaignHeadId
	 * @param campaignItemId
	 * @return
	 */
	public List<TaskSchedule> selectPreByCampaignIdAndItemId(@Param("campaignHeadId") int campaignHeadId,@Param("campaignItemId") String campaignItemId);
}