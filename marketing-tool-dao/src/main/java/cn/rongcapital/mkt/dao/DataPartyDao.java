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
    public Map<String, Object> selectMainCount();
    
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
    List<Map<String,Object>> selectListByNameInList(List<Integer> idList,String audience_name);

    
	/**
	 * @功能简述 : 获取dataPartyIds
	 * @author nianjun
	 * @return map
	 */
	List<Long> selectDataPartyIdsByMappinKeyIds(List<String> wxCodes);
	
	
	
}