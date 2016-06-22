/*************************************************
 * @功能简述: 获取标签的柱状图数据 
 * @see MktApi：
 * @author: xu kun
 * @version: 1.0
 * @date：2016-06-06
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface SegmentTagnameTagCountService {
	BaseOutput getTagCountById(String tagIds);
}
