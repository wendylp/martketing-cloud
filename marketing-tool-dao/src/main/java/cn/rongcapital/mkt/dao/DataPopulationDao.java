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
import cn.rongcapital.mkt.po.DataPopulation;

public interface DataPopulationDao extends BaseDao<DataPopulation>, BaseDataFilterDao<DataPopulation>{
	
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
     * @功能简述 : 根据原始数据更新DataPopulation的数据
     * @author xukun
     */
    void cleanAndUpdateByOriginal(Map<String, List<DataPopulation>> paramMap);

    /**
     * 批量更新数据状态
     * @param idList
     * @return
     */
	int updateStatusByIds(@Param("list") List<Integer> idList, @Param("status") Integer status);
	
	
    /**
     * @功能简述 : 根据手机号查询data_population的数据
     * @param mobile
     * 				手机号
     * @return DataPopulation
     * 				data_population对象
     * 
     */
	DataPopulation getDataPopulationByMobile(@Param("mobile") String mobile);
	
    /**
     * @功能简述 : 根据手机号更新data_population的数据
     * @param dataPopulation
     * 				data_population对象
     * @return int
     * 				更新记录条数
     * 
     */
	int updateDataPopulation(DataPopulation dataPopulation);

	/**
	 * @功能简述 : 根据IdList获取KeyidList(keyidList即dataparty的Idlist)
	 * @param memberKeyidList
	 * @return List
	 */
	List<Integer> selectKeyidListByIdList(@Param("idList") List<Integer> memberKeyidList);
}