/*************************************************
 * @功能简述: API接口 主类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.api;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.SegmentHeaderCreateService;
import cn.rongcapital.mkt.service.SegmentPublishStatusCountService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.SegmentHeadIn;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktApi {

	@Autowired
	private SegmentHeaderCreateService segmentHeaderService;
	@Autowired
	private SegmentPublishStatusCountService segmentPublishStatusCountService;

	/**
	 * @功能简述: For testing, will remove later
	 * @param: String method,String userToken,String ver
	 * @return: Object
	 */
	@GET
	@Path("/test")
	public Object testGet(@NotEmpty @QueryParam("method") String method,
						  @NotEmpty @QueryParam("user_token") String userToken,
						  @QueryParam("ver") String ver) throws Exception {
		BaseOutput ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),0,null);
		return Response.ok().entity(ur).build();
	}

	/**
	 * @功能简述: 创建segment header
	 * @param: SegmentHeadIn body, SecurityContext securityContext 
	 * @return: Object
	 */
	@POST
	@Path("/mkt.segment.header.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object segmentHeaderCreate(@Valid SegmentHeadIn body, @Context SecurityContext securityContext) {
	    return segmentHeaderService.segmentHeaderCreate(body, securityContext);
	}
	
    /**
	 * @功能简述: 查询不同发布状态下的segment数量
	 * @param: String method, String userToken, String ver 
	 * @return: Object
	 */
	@GET
	@Path("/mkt.segment.publishstatus.count.get")
	public Object segmentPublishstatusCount(@NotEmpty @QueryParam("method") String method,
						  @NotEmpty @QueryParam("user_token") String userToken,
						  @QueryParam("ver") String ver) throws Exception {
		return segmentPublishStatusCountService.segmentPublishstatusCount(method, userToken, ver);
	}
}
