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
import cn.rongcapital.mkt.po.WechatMember;

import java.util.List;
import java.util.Map;

public interface WechatMemberDao extends BaseDao<WechatMember>{

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
	 * 批量插入粉丝人数
	 * @param paramMap
	 * @return list
	 */
	void batchInsertFans(List<Map<String, Object>> fansList);

	/**
	 * 判断这个人是否已经被保存
	 * @param paramMap
	 * @return list
	 */
	Long selectIdByGroupIdAndWxAcct(Map<String, Object> paramMap);

	/**
	 * 批量插入微信好友
	 * @param paramMap
	 * @return list
	 */
	void batchInsertContacts(List<Map<String, Object>> paramContacts);

	/**
	 * 根据组名获取组员的唯一标识
	 * @param paramMap
	 * @return list
	 */
	List<String> selectWxCodeByGroupId(List<Long> importGroudIds);

	/**
	 * 选取微信人群的详细信息
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectPeopleDetails(List<Long> importGroupIds);

	/**
	 * 批量插入微信组成员信息
	 * @param paramMap
	 * @return list
	 */
	void batchInsertGroupMember(List<Map<String, Object>> paramGroupMembers);

	/**
	 * 获取一个组好下的总人数
	 * @param paramMap
	 * @return list
	 */
	Integer selectGroupMemeberCount(Map<String,Object> paramMap);

	/**
	 * 搜索微信小组中人的信息
	 * @param paramMap
	 * @return list
	 */
	List<Map<String,Object>> selectSearchInfo(Map<String,Object> paramMap);

	/**
	 * 根据pub_id和open_id判断这个人是否已经被保存
	 * @param paramMap
	 * @return list
	 */
	Long selectIdByPubIdAndOpenId(Map<String, Object> paramMap);

	/**
	 * 删掉与这个uin相关的所有数据
	 * @param paramMap
	 * @return list
	 */
	void deleteRecordByUin(String uin);
}