/*************************************************
 * @功能简述: 获取系统标签内容列表 
 * @see MktApi
 * @author: zhangwei
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TagSystemListGetService {

    /**
     * mkt.tag.system.list.get
     * 
     * @author zhangwei
     * @功能简述 : 获取系统标签内容列表
     * @param method
     * @param userToken
     * @param tagGroupId
     * @param index
     * @param size
     * @return
     */
    public BaseOutput getTagcount(String method, String userToken, int tagGroupId,
    		int index, int size);

}
