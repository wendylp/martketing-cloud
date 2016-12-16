package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能简述: 系统标签相关接口(前端使用)
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/12/6
 * @复审人:
 *************************************************/
public interface SystemTagService {
	
	/**
	 * 获取标签列表
	 * @param navigateIndex	导航坐标
	 * @return
	 */
	BaseOutput getSystemTagList(String navigateIndex,Integer pageSourceType);
	
	/**
	 * 获取标签值列表
	 * @param tagId	标签ID
	 * @return
	 */
	BaseOutput getSystemTagValueList(String tagId,Integer index,Integer size);
	
	
	BaseOutput getNativeList();

}
