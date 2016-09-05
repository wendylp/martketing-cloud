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
import cn.rongcapital.mkt.po.WechatQrcodeFocus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WechatQrcodeFocusDao extends BaseDao<WechatQrcodeFocus>{

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
	 * 选择最早的关注时间
	 * @return list
	 */
	List<WechatQrcodeFocus> selectTheEarliestFocus();
	
	/**
	 * @Title: getFocusOrUnFocusCount   
	 * @Description: 统计微信二维码关注数量和取消关注数量  
	 * @param: @param map
	 * @param: @return      
	 * @return: Integer      
	 * @throws
	 */
	Integer getFocusOrUnFocusCount(Map<String, Object> map);
	
	/**
	 * 获取总浏览次数
	 * @param t
	 * @return
	 */
	List<Map<String, Object>> getTotalFocus(WechatQrcodeFocus t);
	
	/**
	 * 获取新关注的信息
	 * 
	 * @param t
	 * @return
	 */
	List<Map<String, Object>> getNewFocus(WechatQrcodeFocus t);
	
	/**
	 * 获取流失关注的信息
	 * 
	 * @param t
	 * @return
	 */
	List<Map<String, Object>> getLostFocus(WechatQrcodeFocus t);
	
	/**
	 * 获取最大浏览次数
	 * @param t
	 * @return
	 */
	Map<String, Object> getAmountFocusMax(WechatQrcodeFocus t);
	
	/**
	 * 获取最大新关注的信息
	 * @param t
	 * @return
	 */
	Map<String, Object> getNewFocusMax(WechatQrcodeFocus t);
	
	Map<String, Object> getAddFocusMax(WechatQrcodeFocus t);
	
	/**
	 * 获取最大流失关注的信息
	 * @param t
	 * @return
	 */
	Map<String, Object> getLostFocusMax(WechatQrcodeFocus t);
	
	/**
	 * 根据微信名和渠道号获取二维码id
	 */
	List<String> getQrcodeIdList(WechatQrcodeFocus t);

	/**
	 * 根据qrcodeId选取这个二维码的关注时间
	 */
	List<WechatQrcodeFocus> selectTheEarliestFocusByQrcodeId(@Param("qrcodeId") String qrcodeId);
}