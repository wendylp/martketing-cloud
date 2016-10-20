package cn.rongcapital.mkt.sms.api;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.SmsMessageSendRecordGetService;
import cn.rongcapital.mkt.service.SmsTaskDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsTaskDeleteIn;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MKSmsApi {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SmsMessageSendRecordGetService smsMessageSendRecordGetService;
    
    @Autowired
    private SmsTaskDeleteService smsTaskDeleteService;
    
    /**
     * 返回当前任务的发送记录列表（可以按照手机号查询）
     * 
     * @param smsTaskHeadId
     * @param receiveMobile
     * @param index
     * @param size
     * @return
     * @author shuiyangyang
     * @Date 2016.10.18
     */
    @GET
    @Path("/mkt.sms.message.send.record.get")
    public BaseOutput messageSendRecordGet(@NotNull @QueryParam("sms_task_head_id") Long smsTaskHeadId,
                                        @QueryParam("receive_mobile") String receiveMobile,
                                        @DefaultValue("0") @Min(0) @QueryParam("index") Integer index,
                                        @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
        return smsMessageSendRecordGetService.messageSendRecordGet(smsTaskHeadId, receiveMobile, index, size);
    }
    
    
    
    /**
     * 任务删除接口
     * 
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016.10.19
     */
    @POST
    @Path("/mkt.sms.task.delete")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput smsTaskDelete(@Valid SmsTaskDeleteIn body,
            @Context SecurityContext securityContext) {
        return smsTaskDeleteService.smsTaskDelete(body, securityContext);
    }

}
