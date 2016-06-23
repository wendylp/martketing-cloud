/*************************************************
 * @功能简述: 接口mkt.segment.tag.get的service接口类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/6/7
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service;


import java.util.List;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentFilterCountIn;


public interface SegmentFilterGetService {
  
    public BaseOutput getSegmentFilterCount(SegmentFilterCountIn body, SecurityContext securityContext);    
    
}
