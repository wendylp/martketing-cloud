/*************************************************
 * @功能及特点的描述简述: 事件库查询（分页） Service
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 单璟琦
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-01-11 
 * @date(最后修改日期)：2017-01-11 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface EventModelListService {
	BaseOutput getEventModelList(String channel, Long sourceId, String eventName, Integer index, Integer size);
}
