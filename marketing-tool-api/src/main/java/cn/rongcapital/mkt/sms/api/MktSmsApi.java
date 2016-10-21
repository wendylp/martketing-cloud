package cn.rongcapital.mkt.sms.api;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.SmsActivationCreateOrUpdateService;
import cn.rongcapital.mkt.service.SmsSignatureListGetService;
import cn.rongcapital.mkt.service.SmsTargetAudienceListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;
import cn.rongcapital.mkt.vo.out.SmsSignatureListOut;
import cn.rongcapital.mkt.vo.out.SmsTargetAudienceListOut;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by byf on 10/18/16.
 */

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class MktSmsApi {

    @Autowired
    private SmsSignatureListGetService smsSignatureListGetService;

    @Autowired
    private SmsTargetAudienceListGetService smsTargetAudienceListGetService;

    @Autowired
    private SmsActivationCreateOrUpdateService smsActivationCreateOrUpdateService;

    /**
     * @功能简述: For testing, will remove later
     * @param:String userToken,String
     *                   ver
     * @return: Object
     */
    @GET
    @Path("/test.demo")
    public Object testGet(@NotEmpty @QueryParam("user_token") String userToken, @QueryParam("ver") String ver)
            throws Exception {
        BaseOutput ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), userToken, 0, null);
        return Response.ok().entity(ur).build();
    }

    /**
     * @功能简述: 获取短信签名列表
     * @param:String userToken,String
     *                   ver  ,String
     * @return: Signature
     */
    @GET
    @Path("/mkt.sms.signature.get")
    public SmsSignatureListOut smsSignatureListGet(@NotEmpty @QueryParam("user_token") String userToken,
                                                   @QueryParam("ver") String ver) throws Exception {
        return smsSignatureListGetService.getSmsSignatureList();
    }


    /**
     * @功能简述: 获取短信目标人群列表
     * @param:String userToken,
     *        String ver
     * @return: Object
     */
    @GET
    @Path("/mkt.sms.audienct.get")
    public SmsTargetAudienceListOut smsAudienceListGet(@NotEmpty @QueryParam("user_token") String userToken,
                                                       @QueryParam("ver") String ver) throws Exception {
        return smsTargetAudienceListGetService.getSmsTargetAudienceList();
    }

    /**
     * @功能简述: 保存短信活动
     * @param:String userToken,
     *        String ver
     * @return: Object
     */
    @POST
    @Path("/mkt.sms.message.createorupdate")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput createOrUpdateSmsMessage (@Valid SmsActivationCreateIn smsActivationCreateIn,
                                          @Context SecurityContext securityContext) throws JMSException {
        return smsActivationCreateOrUpdateService.createOrUpdateSmsActivation(smsActivationCreateIn);
    }
}
