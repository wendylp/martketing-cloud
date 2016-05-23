package cn.rongcapital.mkt.service;
import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.SegmentHeadIn;
public interface SegmentHeaderService extends BaseService{
  
      public Object segmentHeaderPost(SegmentHeadIn body,SecurityContext securityContext);
  
}
