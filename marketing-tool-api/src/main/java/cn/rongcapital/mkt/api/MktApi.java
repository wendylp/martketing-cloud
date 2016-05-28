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
import javax.validation.constraints.NotNull;
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

import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.SegmentHeadIn;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktApi {

	@Autowired
	private LoginService loginService;
	@Autowired
	private ModifyPasswdService modifyPasswdService;
	@Autowired
	private SegmentHeaderCreateService segmentHeaderService;
	@Autowired
	private SegmentPublishStatusCountService segmentPublishStatusCountService;
	@Autowired
	private SegmentPublishstatusListService segmentPublishstatusListService;
	@Autowired
	private DeleteImgTextAssetService deleteImgTextAssetService;
	@Autowired
	private GetImgTextAssetService getImgTextAssetService;

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
	 * @功能简述: 获取图文资产
	 * @Param: String method,String user_token,String ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.imgtext.get")
	public Object getImgTextAsset(@NotEmpty @QueryParam("method") String method,
								  @NotEmpty @QueryParam("user_token") String userToken,
								  @NotEmpty @QueryParam("ver") String ver,
								  @NotNull @QueryParam("type") Integer type,
								  @QueryParam("owner_name") String ownerName,
								  @QueryParam("index") int index,
								  @QueryParam("size") int size){
		ImgAsset imgAsset = new ImgAsset();
		imgAsset.setMethod(method);
		imgAsset.setAssetType(type);
		imgAsset.setVer(ver);
		if(ownerName != null) {
			imgAsset.setOwnerName(ownerName);
		}
		if(index != 0){
			imgAsset.setIndex(index);
		}else{
			imgAsset.setIndex(1);
		}
		if(size != 0){
			imgAsset.setSize(size);
		}else{
			imgAsset.setSize(10);
		}
		return getImgTextAssetService.getImgTextAssetService(imgAsset);
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
						  					@NotEmpty @QueryParam("ver") String ver) throws Exception {
		return segmentPublishStatusCountService.segmentPublishstatusCount(method, userToken, ver);
	}
	
    /**
	 * @功能简述: 获取某个发布状态下的segemnt列表
	 * @param: String method, String userToken, String ver, String publishStatus 
	 * @return: Object
	 */
	@GET
	@Path("/mkt.segment.publishstatus.list.get")
	public Object segmentPublishstatusList(@NotEmpty @QueryParam("method") String method,
						  				   @NotEmpty @QueryParam("user_token") String userToken,
						  				   @NotNull @QueryParam("publish_status") Integer publishStatus,
						  				   @QueryParam("index") Integer index,
						  				   @QueryParam("size") Integer size,
						  				   @NotEmpty @QueryParam("ver") String ver) throws Exception {
		return segmentPublishstatusListService.segmentPublishstatusList(method, userToken,publishStatus,index,size,ver);
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
		return modifyPasswdService.modifyPasswd(input, securityContext);
	}

	/**
	 * @功能描述:删除图文资产 mkt.asset.imgtext.delete
	 * @Param: LoginIn loginIn, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.asset.imgtext.delete")
	@Consumes({MediaType.APPLICATION_JSON})
	public Object deleteImgTextAsset(@Valid ImgAsset imgAsset,@Context SecurityContext securityContext){
		return deleteImgTextAssetService.deleteImgTextService(imgAsset.getImgtextId());
	}
}
