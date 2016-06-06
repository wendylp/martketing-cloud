/*************************************************
 * @功能简述: 查询系统推荐标签列表的业务类接口 
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-06
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.SegmentTagnameTagList;

public interface SegmentTagnameTagListService {
	SegmentTagnameTagList getSysRecommendedTagList();
}
