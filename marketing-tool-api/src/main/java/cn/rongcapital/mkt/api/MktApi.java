package cn.rongcapital.mkt.api;
import javax.ws.rs.Consumes;
//import cn.rongcapital.mkt.service.SegmentApiService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.service.SegmentHeaderService;
import cn.rongcapital.mkt.vo.SegmentHeadIn;

@Component
@Path("/api")
public class MktApi  {
   @Autowired   
   private SegmentHeaderService segmentHeaderService;
   
//   @POST
//   @Path("/")
//   public Object apiRouter(@NotEmpty @QueryParam("method") String method, @NotEmpty @QueryParam("userToken") String userToken){
//	
//	   return null;
//   }
   @POST
   @Consumes({ "application/json" })
   @Produces({ "application/json" })
   public Object segmentHeaderPost( SegmentHeadIn body,@Context SecurityContext securityContext) {
       return segmentHeaderService.segmentHeaderPost(body,securityContext);
   }
}
