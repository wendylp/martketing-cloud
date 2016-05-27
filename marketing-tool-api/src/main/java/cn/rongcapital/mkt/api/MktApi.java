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

import cn.rongcapital.mkt.service.LoginService;
import cn.rongcapital.mkt.service.ModifyPasswdService;
import cn.rongcapital.mkt.vo.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.SegmentHeaderService;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktApi {

	@Autowired
	private SegmentHeaderService segmentHeaderService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private ModifyPasswdService modifyPasswdService;

	/**
	 * @功能简述: For testing, will remove later
	 * @param: String method,String userToken
	 * @return: Object
	 */
	@GET
	@Path("/test")
	public Object testGet(@NotEmpty @QueryParam("method") String method,
						  @NotEmpty @QueryParam("user_token") String userToken,
						  @QueryParam("ver") String ver) throws Exception {
		UpdateResult ur = new UpdateResult(0, "success");
		return Response.ok().entity(ur).build();
	}

	/**	"method": "${同接口名称}",
			"user_token": "6200819d9366af1383023a19907ZZf9048e4c14fd56333b263685215",
			"asset_type":2,
			"asset_name":"MAKA",
			"index":"1",
			"size":"4"
	*/
	@GET
	@Path("/mkt.asset.imgtext.get")
	public Object getImgTextAsset(@NotEmpty @QueryParam("method") String method,
								  @NotEmpty @QueryParam("user_token") String userToken,
								  @QueryParam("asset_type") int assetType,
								  @QueryParam("asset_name") String assetName,
								  @QueryParam("index") int index,
								  @QueryParam("size") int size){
		return null;

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
		System.out.println("already here haha!");
	    return segmentHeaderService.segmentHeaderCreate(body, securityContext);
	}

	/**
	 * @功能描述: 登录接口
	 * @Param: LoginIn loginIn, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.user.login")
	@Consumes({MediaType.APPLICATION_JSON})
	public Object login(@Valid LoginInput input, @Context SecurityContext securityContext){
		return loginService.validateLoginPassword(input,securityContext);
	}

	/**
	 * @功能描述: 修改密码
	 * @Param: ModifyIn modifyIn, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.user.modifypasswd")
	@Consumes({MediaType.APPLICATION_JSON})
	public Object modifyPasswd(@Valid ModifyInput input, @Context SecurityContext securityContext){
		return modifyPasswdService.modifyPasswd(input,securityContext);
	}
}
