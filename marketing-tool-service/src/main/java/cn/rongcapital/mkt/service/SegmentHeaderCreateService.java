/*************************************************
 * @功能简述: 接口mkt.segment.header.create的service接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service;
import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.SegmentHeadIn;

public interface SegmentHeaderCreateService {
  
    public Object segmentHeaderCreate(SegmentHeadIn body,SecurityContext securityContext);
    
}
