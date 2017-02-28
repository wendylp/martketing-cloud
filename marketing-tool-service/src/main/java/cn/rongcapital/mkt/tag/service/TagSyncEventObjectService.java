package cn.rongcapital.mkt.tag.service;

import cn.rongcapital.mkt.tag.vo.in.TagSyncEventObjectIn;
import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能及特点的描述简述: 通过事件客体打标签
 *
 * @see （与该类关联的类): CustomtagCreateServiceImpl
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.7 @date(创建、开发日期): 2017-1-20 最后修改日期: 2017-1-20
 * @复审人: 丛树林
 *************************************************/
public interface TagSyncEventObjectService {
	/**
	 * 功能描述：根据客体标示获得事件客体对象
	 * 
	 * @param TagSyncEventObjectIn
	 *            事件客体标示
	 * 
	 * @return BaseOutput
	 */
	BaseOutput getEventBehavierListGet(TagSyncEventObjectIn tagSyncEventObjectIn);
}
