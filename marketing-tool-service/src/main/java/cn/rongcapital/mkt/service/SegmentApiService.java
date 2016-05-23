package cn.rongcapital.mkt.service;
import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.SegmentTitleIn;
public interface SegmentApiService {
  
      public Object segmentSegmentTitleGet(String name,Integer status,String oper,SecurityContext securityContext);
  
      public Object segmentSegmentTitlePost(SegmentTitleIn body,SecurityContext securityContext);
  
}
