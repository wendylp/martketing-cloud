package cn.rongcapital.mkt.tag.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.tag.vo.in.CustomTagCategoryIn;
import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能及特点的描述简述: 创建自定义标签分类
 *
 * @see （与该类关联的类): CustomtagCreateServiceImpl
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.7 @date(创建、开发日期): 2017-1-20 最后修改日期: 2017-1-20
 * @复审人: 丛树林
 *************************************************/
public interface CustomtagCategoryCreateService {
	/**
	 * 功能描述：创建、编辑自定义标签的分类
	 * 
	 * 接口：mkt.customtag.category.create
	 * 
	 * @param body
	 *            json对象
	 * @param securityContext
	 *            用户登录信息
	 * 
	 * @return BaseOutput
	 */
	public BaseOutput updateCustomtagCategory(CustomTagCategoryIn body, SecurityContext securityContext);

}
