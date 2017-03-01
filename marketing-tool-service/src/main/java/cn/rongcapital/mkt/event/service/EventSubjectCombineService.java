/*************************************************
 * @功能简述: 根据事件编码判断是否进行主数据合并
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/3/1
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service;


/**
 * 根据事件编码判断是否需要主数据合并
 * 
 * @param eventCode 事件编码
 * @return boolean (true:合并,false:不合并)
 * @author zhuxuelong
 * @date: 2017/3/1
 */
public interface EventSubjectCombineService {
    boolean needCombine(String eventCode);
}
