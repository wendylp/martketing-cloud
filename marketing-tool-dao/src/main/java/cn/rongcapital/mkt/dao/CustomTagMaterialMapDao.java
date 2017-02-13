/*************************************************
 * @功能简述: 自定义标签与物料关系映射DAO接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2017/1/18
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.CustomTagMaterialMap;

public interface CustomTagMaterialMapDao extends BaseDao<CustomTagMaterialMap>{
	
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
	 * 通过参数进行物理删除
	 * @param materialCode	物料code
	 * @param materialType	物料类型
	 * @return
	 */
	List<CustomTagMaterialMap> getUnbindTag(@Param("materialCode") String materialCode,@Param("materialType")String materialType,@Param("customTagIdList") List<String> customTagIdList);
	
	/**
	 * 通过物料参数获取标签ID
	 * @param materialCode
	 * @param materialType
	 * @return
	 */
	List<String> getCustomTagIdByMaterialParam(@Param("materialCode") String materialCode,@Param("materialType")String materialType);
	/**
	 * 获取所有数据，物料分组
	 * @return
	 */
	List<CustomTagMaterialMap> getAllData();
	
	/**
	 * 删除数据
	 * @param customTagMaterialMap
	 * @return
	 */
	int deleteById(CustomTagMaterialMap customTagMaterialMap);
}