/*************************************************
 * @功能及特点的描述简述: 事件Service
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-1-7
 * @date(最后修改日期)：2017-1-7
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.event.vo.in.EventBehavierIn;
import cn.rongcapital.mkt.event.vo.in.EventBehavierListIn;
import cn.rongcapital.mkt.event.vo.in.EventObjectVo;
import cn.rongcapital.mkt.event.vo.in.EventRegisterIn;
import cn.rongcapital.mkt.event.vo.in.EventSourceVo;
import cn.rongcapital.mkt.event.vo.in.EventSubscribeInput;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Path(ApiConstant.EVENT_API_PATH)
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@ValidateRequest
public interface EventWebService {

    /**
     * @功能描述: 事件订阅与取消订阅
     * @param input
     * @return BaseOutput
     * @author xie.xiaoliang
     * @since 2017-01-10
     */
    @POST
    @Path("/mkt.event.subscribe")
    BaseOutput eventSubscribe(@Valid EventSubscribeInput input);

    @POST
    @Path("/mkt.event.register")
    BaseOutput eventRegister(@Valid EventRegisterIn registerIn);
    
    /**
     * @功能简述: 获取事件概要信息
     * @param eventId
     * @return BaseOutput
     * @author zhuxuelong
     */
    @GET
    @Path("/mkt.event.general.get")
    BaseOutput getEventGeneral(@NotNull @QueryParam("event_id") Long eventId);

    /**
     * @功能简述: 获取客体属性值列表
     * @param eventObjectId
     * @return BaseOutput
     * @author zhuxuelong
     */
    @GET
    @Path("/mkt.event.object.prop.list")
    BaseOutput getEventObjProps(@NotNull @QueryParam("object_id") Long eventObjectId);

    /**
     * @功能简述: 事件客体注册
     * @param event
     * @return BaseOutput
     * @author zhuxuelong
     */
    @POST
    @Path("/mkt.event.object.register")
    BaseOutput saveEventObj(@NotNull @Valid EventObjectVo event);

    /**
     * @功能简述: 事件来源注册
     * @param source
     * @return BaseOutput
     * @author zhuxuelong
     */
    @POST
    @Path("/mkt.event.source.register")
    BaseOutput saveEventSource(@NotNull @Valid EventSourceVo source);
    
    /**
     * @author guozhenchao
     * @功能简述: 事件行为查询列表
     * @param eventBehavierListIn
     * @return
     */
    @POST
    @Path("/mkt.event.behavior.list")
	EventBehaviorOut getEventBehavierList(@Valid EventBehavierListIn eventBehavierListIn);
    
    /**
     * @author guozhenchao
     * @功能简述: 通过物料的相关属性，返回物料、事件、主体的相关关联关系及相关具体信息。通过物料的唯一标识，查询该物料在指定事件段内所发生的事件以及相关的主体信息。(API调用)
     * @param eventBehavierListIn
     * @return
     */
	 @POST
	 @Path("/mkt.event.behavior.list.get")
	 BaseOutput getEventBehavierListGet(@Valid EventBehavierIn in);
}
