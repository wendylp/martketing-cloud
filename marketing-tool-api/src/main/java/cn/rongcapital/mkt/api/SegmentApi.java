package cn.rongcapital.mkt.api;

import cn.rongcapital.mkt.service.SegmentApiService;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import cn.rongcapital.mkt.vo.SegmentTitleOut;
import cn.rongcapital.mkt.vo.SegmentTitleIn;


@Component
@Path("/segment")
public class SegmentApi  {
   @Autowired   
   private SegmentApiService segmentApiService;
    @GET
    @Path("/segmentTitle")
    @Produces({ "application/json" })
    public Object segmentSegmentTitleGet( @QueryParam("name") String name, @QueryParam("status") Integer status, @QueryParam("oper") String oper,@Context SecurityContext securityContext) {
        return segmentApiService.segmentSegmentTitleGet(name,status,oper,securityContext);
    }
    @POST
    @Path("/segmentTitle")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Object segmentSegmentTitlePost( SegmentTitleIn body,@Context SecurityContext securityContext) {
        return segmentApiService.segmentSegmentTitlePost(body,securityContext);
    }
}
