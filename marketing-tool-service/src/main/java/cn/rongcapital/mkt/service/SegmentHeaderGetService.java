/*************************************************
 * @功能简述: 接口mkt.segment.header.get的service接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/
package cn.rongcapital.mkt.service;

public interface SegmentHeaderGetService {

	public Object segmentHeaderGet(String userToken,String ver,String segmentId);
}