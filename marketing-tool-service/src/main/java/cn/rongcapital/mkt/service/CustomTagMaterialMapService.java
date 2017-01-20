package cn.rongcapital.mkt.service;

import java.util.List;

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
}
