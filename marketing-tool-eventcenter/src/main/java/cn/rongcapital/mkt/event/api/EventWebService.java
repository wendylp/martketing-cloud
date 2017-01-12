/*************************************************
 * @功能及特点的描述简述: 事件Service
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-1-7
 * @date(最后修改日期)：2017-1-7
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import cn.rongcapital.mkt.event.vo.in.EventRegisterIn;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.vo.out.EventListOut;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;

@Path(ApiConstant.API_PATH)
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public interface EventWebService {

    @GET
    @Path("/mkt.event.list")
    EventListOut getEventListByKeyword(@NotEmpty @QueryParam("user_token") String userToken,
                                                       @NotEmpty @QueryParam("ver") String ver,
                                                       @QueryParam("keyword") String keyword,
                                                       @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
                                                       @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size
                                                       ) throws Exception ;
    
    @GET
    @Path("/mkt.event.behavior.list")
    List<EventBehavior> getEventBehaviorListByKeyword(@NotEmpty @QueryParam("user_token") String userToken,
                                                       @NotEmpty @QueryParam("ver") String ver,
                                                       @QueryParam("keyword") String keyword,
                                                       @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
                                                       @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size
                                                       ) throws Exception ;
    
    @GET
    @Path("/mkt.event.object")
    EventObject selectById(@QueryParam("event_object_id")Integer eventObjectId);

    @POST
    @Path("/mkt.event.register")
    BaseOutput eventRegister(@Valid EventRegisterIn registerIn);
}
