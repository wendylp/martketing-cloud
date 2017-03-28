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
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.caas.agent.spring.CaasAuth;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.SmsTempletCancelShareService;
import cn.rongcapital.mkt.service.SmsTempletService;
import cn.rongcapital.mkt.service.SmsTempletShareService;
import cn.rongcapital.mkt.service.SmstempletCountGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SmsTempletOut;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletCancelShareIn;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletCloneIn;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletIn;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletShareIn;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktSmsTempletApi {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SmsTempletService smsTempletService;
	
	@Autowired
	private SmstempletCountGetService smstempletCountGetService;
	
	@Autowired
    private SmsTempletShareService smsTempletShareService;
	
    @Autowired
    private SmsTempletCancelShareService smsTempletCancelShareService;
	
	
	/**
	 * 根据模板名称模糊查询模板列表
	 * @param userToken
	 * @param userId
	 * @param ver
	 * @param index
	 * @param size
	 * @param channelType
	 * @param type
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/mkt.sms.smstemplet.list.get")
    @CaasAuth(res = "#orgId", oper = "'#{T(cn.rongcapital.mkt.common.constant.ApiConstant).CAAS_READ}'", type = CaasAuth.Type.SpEl)
	public SmsTempletOut smsTempletList(@NotEmpty @QueryParam("user_token") String userToken,@NotEmpty @QueryParam("user_id") String userId, @NotEmpty @QueryParam("ver") String ver,			
			@DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size,
			@QueryParam("channel_type") String channelType,
			@QueryParam("type") String type,
			@QueryParam("name") String name,
			@QueryParam("content") String content,
			@QueryParam("org_id") Integer orgId,
			@QueryParam("firsthand")Boolean firsthand) throws Exception {

		return smsTempletService.smsTempletList(userId, index, size, channelType, type, name,content,orgId, firsthand);
	}
	
	/**
	 * 模板的增加或者修改
	 * @param smsTempletIn
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/mkt.sms.smstemplet.saveorupdate")
	@CaasAuth(res = "#{smsTempletIn.orgid}", oper = "'#{T(cn.rongcapital.mkt.common.constant.ApiConstant).CAAS_WRITE}'", type = CaasAuth.Type.SpEl)
	public BaseOutput saveOrUpdateSmsTemplet(@Valid SmsTempletIn smsTempletIn) throws Exception {		
		return smsTempletService.saveOrUpdateSmsTemplet(smsTempletIn);
	}
	
    /**
     * 分类获取模板数量
     * 
     * @return
     * @throws Exception
     */
    @GET
    @Path("/mkt.sms.smstemplet.count.get")
    @CaasAuth(res = "#orgId", oper = "'#{T(cn.rongcapital.mkt.common.constant.ApiConstant).CAAS_READ}'", type = CaasAuth.Type.SpEl)
    public BaseOutput smsTempletCountGet(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("user_id") String userId, @NotEmpty @QueryParam("ver") String ver,
            @QueryParam("channel_type") String channelType, @QueryParam("org_id") Long orgId,
            @QueryParam("firsthand") Boolean firsthand) throws Exception {
        return smstempletCountGetService.getSmsTempletCount(channelType, orgId, firsthand);
    }
	
	/**
	 * 短信模板克隆
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/mkt.sms.smstemplet.clone")
	@CaasAuth(res = "#{clone.orgid}", oper = "'#{T(cn.rongcapital.mkt.common.constant.ApiConstant).CAAS_CLONE}'", type = CaasAuth.Type.SpEl)
	public BaseOutput smsTempletClone(@Valid SmsTempletCloneIn clone) throws Exception {		
		return smsTempletService.smsTempletClone(clone);
	}
	
    /**
     * 模板分享
     * @param smsTempletShareIn
     * @return
     * @throws Exception
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/mkt.sms.smstemplet.share")
    @CaasAuth(res = "#{smsTempletShareIn.orgid}", oper = "'#{T(cn.rongcapital.mkt.common.constant.ApiConstant).CAAS_SHARE}'", type = CaasAuth.Type.SpEl)
    public BaseOutput shareSmsTemple(@Valid SmsTempletShareIn smsTempletShareIn) throws Exception {       
        return smsTempletShareService.shareSmsTemplet(smsTempletShareIn);
    }
    
    /**
     * 取消模板分享
     * @param smsTempleCancelShareIn
     * @return
     * @throws Exception
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/mkt.sms.smstemplet.share.cancel")
    @CaasAuth(res = "#{smsTempleCancelShareIn.orgid}", oper = "'#{T(cn.rongcapital.mkt.common.constant.ApiConstant).CAAS_SHARE}'", type = CaasAuth.Type.SpEl)
    public BaseOutput unShareSmsTemple(@Valid SmsTempletCancelShareIn smsTempleCancelShareIn) throws Exception {       
        return smsTempletCancelShareService.cancelShareSmsTemplet(smsTempleCancelShareIn);
    }
    
	/**
	 * 当前资源查询分享给了哪些组织、及分享时间
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/mkt.sms.smstemplet.orgs.list")
	public BaseOutput getOrgs(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("user_id") String userId, @NotEmpty @QueryParam("ver") String ver,
			@NotNull @QueryParam("resource_id") long resourceId, @NotNull @QueryParam("org_id") long orgId,
			@NotEmpty @QueryParam("opr_type") String oprType,
			@DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {		
		return smsTempletService.getOrgs(resourceId, orgId, oprType, index, size);
	}
    
	/**
	 * 当前被分享资源查询是从哪个组织分享来的、及分享时间；
	 * @param userToken
	 * @param userId
	 * @param ver
	 * @param resourceId
	 * @param orgId
	 * @param oprType
	 * @return
	 */
	@GET
	@Path("/mkt.sms.smstemplet.org.get")
	public BaseOutput getOrg(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("user_id") String userId, @NotEmpty @QueryParam("ver") String ver,
			@NotNull @QueryParam("resource_id") long resourceId, @NotNull @QueryParam("org_id") long orgId,
			@NotEmpty @QueryParam("opr_type") String oprType) {		
		return smsTempletService.getOrg(resourceId, orgId, oprType);
	}
	
}
