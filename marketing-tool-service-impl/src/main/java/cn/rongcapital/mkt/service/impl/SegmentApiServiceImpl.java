package cn.rongcapital.mkt.service.impl;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.service.SegmentApiService;
import cn.rongcapital.mkt.vo.SegmentTitleIn;
import cn.rongcapital.mkt.vo.SegmentTitleOut;

@Service
public class SegmentApiServiceImpl implements SegmentApiService {
  
      @Override
      public Object segmentSegmentTitleGet(String name,Integer status,String oper,SecurityContext securityContext){
          // do some magic!
//	      return Response.ok().entity("success").build();
    	  SegmentTitleOut s = new SegmentTitleOut(SegmentTitleOut.OK, "magic!");
    	  s.setName("Jessie");
          return Response.ok().entity(s).build();
      }
  
      @Override
      public Object segmentSegmentTitlePost(SegmentTitleIn body,SecurityContext securityContext){
          // do some magic!
	      return Response.ok().entity("success").build();
          //return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
      }
  
}
