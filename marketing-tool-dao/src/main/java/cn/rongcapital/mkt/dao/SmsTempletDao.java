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

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.vo.sms.out.SmsTempletCountVo;

public interface SmsTempletDao extends BaseDao<SmsTemplet>{
	
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
	 * 根据模板类型统计数据
	 * @param smsTemplet
	 * @return
	 */
	public List<SmsTempletCountVo> selectListCountGroupByType(SmsTemplet smsTemplet);
	
	/**
	 * 根据审核状态统计数据
	 * @param smsTemplet
	 * @return
	 */
	public List<SmsTempletCountVo> selectListCountGroupByAuditStatus(SmsTemplet smsTemplet);
	
    /**
     * 分组计算数量
     * 
     * @return
     */
    public List<Map<String, Object>> getTempletCountByType(@Param(value = "channelType") String channelType,
            @Param(value = "orgId") Long orgId);
    
	/**
	 * 查询短信模板
	 * @param map
	 * @return
	 */
	public List<SmsTemplet> selectByIdAndOrgId(Map<String, Integer> map);
}