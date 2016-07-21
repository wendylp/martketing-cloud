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
import cn.rongcapital.mkt.dao.base.BaseDataFilterDao;
import cn.rongcapital.mkt.po.DataParty;

public interface DataPartyDao extends BaseDao<DataParty>, BaseDataFilterDao<DataParty>{
	
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
     * mkt.data.main.count.get
     * 
     * @功能简述 : 获取主数据条数
     * @author nianjun
     * @return map
     */
    public Map<String, Object> selectMainCount(Map<String, Object> paramMap);
    
//    /**
//     * mkt.data.main.delete
//     * 
//     * @功能简述 : 删除某条主数据
//     * @author nianjun
//     * @return map
//     */
//    public int logicDeleteById(DataParty dataParty);

    /**
     *
     * @功能简述 : 根据主键获取某条主数据
     * @param id
     * @author zhuxuelong
     * @return DataParty
     */
    public DataParty getDataById(Integer id);

    /**
     * mkt.data.main.searchbyid.get
     *
     * @功能简述 : 根据类型和编号，搜索获取主数据(人群)
     * @author nianjun
     * @return map
     */
    Map<String,Object> selectAudienceDetail(Map<String, Object> map);

    
    /**
     * mkt.audience.search.get
     *
     * @功能简述 : 在人群中查找
     * @author xukun
     * @return map
     */
    List<Map<String,Object>> selectListByKeyName(String audience_name);

	/**
	 * mkt.audience.search.get
	 *
	 * @功能简述 : 在指定人群中查找
	 * @author xukun
	 * @return map
	 */
    List<Map<String,Object>> selectListByNameInList(Map<String,Object> paramMap);

    
	/**
	 * @功能简述 : 获取dataPartyIds
	 * @author nianjun
	 * @return map
	 */
	List<Long> selectDataPartyIdsByMappinKeyIds(List<Long> mappingIdList);
	
	/**
     * @功能简述 : 根据自定义标签获取覆盖人群
     * @author nianjun
     * @return map
     */
	List<DataParty> selectCustomAudiencesByTagId(Map<String, Integer> paramMap);
	

    /**
     * 批量插入数据
     * @param list
     * @return
     */
	int batchInsert(@Param("list") List<DataParty> list);

    /**
     * 批量更新数据状态
     * @param idList
     * @return
     */
    int updateStatusByIds(@Param("list") List<Integer> idList, @Param("status") Integer status);

	/**
	 * 同步微信数据到主数据
	 * @param idList
	 * @return
	 */
	Integer batchInsertWechatDatas(List<Map<String, Object>> notSyncWechatMemberList);

	/**
	 * 根据mappingId选择dataPartyId
	 * @param idList
	 * @return
	 */
	Integer selectIdByMappingId(@Param("mapping_keyid") Long id);
	
	/**
	 * 根据手机号更新data_party表数据
	 * @param dataParty 
	 * @return 更新数据条数
	 */
	int updateDataPartyInfo(DataParty dataParty);
}