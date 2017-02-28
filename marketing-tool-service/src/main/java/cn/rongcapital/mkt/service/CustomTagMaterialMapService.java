package cn.rongcapital.mkt.service;

import java.util.List;

import cn.rongcapital.mkt.po.CustomTagMaterialMap;
import cn.rongcapital.mkt.vo.in.CustomTagIn;

/**
 * 标签物料映射关系相关接口
 * @author 王伟强
 *
 */
public interface CustomTagMaterialMapService {
	
	/**
	 * 建立标签物料关系
	 * @param CustomTagMaterialMapList
	 */
	public void buildTagMaterialRealation(List<CustomTagIn> customTagInList,String materialCode,String materialType);
	
	/**
	 *  通过物料Code查询相关标签
	 * @param materialCode	物料Code
	 * @param materialType	物料类型
	 * @return
	 */
	public List<CustomTagIn> getCustomTagByMaterialCode(String materialCode,String materialType);
	
	/**
	 * 获取所有数据
	 * @return
	 */
	public List<CustomTagMaterialMap> getAllData();
	
	/**
	 * 获取所有
	 */
}
