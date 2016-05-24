package cn.rongcapital.mkt.api;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
//import cn.rongcapital.mkt.service.SegmentApiService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.service.SegmentHeaderService;
import cn.rongcapital.mkt.vo.BaseInput;
import cn.rongcapital.mkt.vo.SegmentHeadIn;
import cn.rongcapital.mkt.vo.UpdateResult;

@Component
@Path("/api")
@ValidateRequest
public class MktApi {
   @Autowired   
   private SegmentHeaderService segmentHeaderService;
   
   @GET
   @Path("/test")
   @Produces({ "application/json" })
   public Object testGet(@NotEmpty @QueryParam("method") String method, 
		   @NotEmpty @QueryParam("userToken") String userToken) throws Exception{
	   UpdateResult ur = new UpdateResult(0,"success");
	   return Response.ok().entity(ur).build();
   }
   
   @POST
   @Path("/")
   @Consumes({ "application/json" })
   @Produces({ "application/json" })
   public Object apiRoute(BaseInput body,@Context SecurityContext securityContext){
	   String method = body.getMethod();
	   System.out.println(method);
	   return segmentHeaderPost((SegmentHeadIn)body, securityContext);
   }
   @GET
   @Path("/")
   @Produces({ "application/json" })
   public Object apiRoute(@NotEmpty @QueryParam("method") String method){
	   System.out.println(method);
	   UpdateResult ur = new UpdateResult(0,"success");
	   return Response.ok().entity(ur).build();
//	   return segmentHeaderPost((SegmentHeadIn)body, securityContext);
   }
   @Consumes({ "application/json" })
   @Produces({ "application/json" })
   public Object segmentHeaderPost(SegmentHeadIn body,@Context SecurityContext securityContext){
       return segmentHeaderService.segmentHeaderPost(body,securityContext);
   }
}
