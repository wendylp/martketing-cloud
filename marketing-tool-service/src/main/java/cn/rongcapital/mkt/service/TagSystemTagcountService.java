/*************************************************
 * @功能简述: 获取系统标签总数量 
 * @see MktApi
 * @author: zhangwei
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TagSystemTagcountService {

	/**
	 * mkt.tag.system.tagcount.get
	 * 
	 * @author zhangwei
	 * @功能简述 : 获取系统标签总数量
	 * @param method
	 * @param userToken
	 * @return
	 */
	public BaseOutput getTagcount(String method, String userToken);

}
