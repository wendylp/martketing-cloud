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
import cn.rongcapital.mkt.vo.SegmentHeadCreateIn;

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
	@Autowired
	private ImgtextHostService imgtextHostService;
	@Autowired
	private WechatTypeCountGetService wechatTypeCountGetService;
	@Autowired
	private MigrationFileGeneralInfoService migrationFileGeneralInfoService;
	@Autowired
	private MigrationFileTemplateService migrationFileTemplateService;
	@Autowired
	private MigrationFileUploadUrlService migrationFileUploadUrlService;
	@Autowired
	private DataGetMainListService dataGetMainListService;
	@Autowired
	private SegmentHeaderGetService segmentHeaderGetService;
	@Autowired
	private WechatAssetListService wechatAssetListService;
	@Autowired
	private SegmentHeaderUpdateService segmentHeaderUpdateService;

	/**
	 * @功能简述: For testing, will remove later
	 * @param:String userToken,String ver
	 * @return: Object
	 */
	@GET
	@Path("/test.demo")
	public Object testGet(@NotEmpty @QueryParam("user_token") String userToken,
						  @QueryParam("ver") String ver) throws Exception {
		BaseOutput ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),userToken,0,null);
		return Response.ok().entity(ur).build();
	}

	/**
	 * @功能简述: 获取图文资产
	 * @param:String user_token,String ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.imgtext.get")
	public Object getImgTextAsset(@NotEmpty @QueryParam("user_token") String userToken,
								  @NotEmpty @QueryParam("ver") String ver,
								  @NotNull @QueryParam("type") Integer type,
								  @QueryParam("owner_name") String ownerName,
								  @DefaultValue("1") @Min(1) @QueryParam("index") int index,
								  @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") int size){
		ImgAsset imgAsset = new ImgAsset();
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
	 * @功能简述: 编辑segment header
	 * @param: SegmentHeadIn body, SecurityContext securityContext 
	 * @return: Object
	 */
	@POST
	@Path("/mkt.segment.header.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object segmentHeaderUpdate(@Valid SegmentHeadUpdateIn body, @Context SecurityContext securityContext) {
	    return segmentHeaderUpdateService.segmentHeaderUpdate(body,securityContext);
	}
	/**
	 * @功能简述: 根据id获取segment header
	 * @param: SegmentHeadIn body, SecurityContext securityContext 
	 * @return: Object
	 */
	@GET
	@Path("/mkt.segment.header.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object segmentHeaderGet(@NotEmpty @QueryParam("user_token") String userToken,
								   @NotEmpty @QueryParam("ver") String ver,
								   @NotEmpty @QueryParam("segment_id") String segmentId) {
	    return segmentHeaderGetService.segmentHeaderGet(userToken, ver,segmentId);
	}
	
	/**
	 * @功能简述: 创建segment header
	 * @param: SegmentHeadIn body, SecurityContext securityContext 
	 * @return: Object
	 */
	@POST
	@Path("/mkt.segment.header.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object segmentHeaderCreate(@Valid SegmentHeadCreateIn body, @Context SecurityContext securityContext) {
	    return segmentHeaderService.segmentHeaderCreate(body, securityContext);
	}
	
    /**
	 * @功能简述: 查询不同发布状态下的segment数量
	 * @param: String userToken, String ver 
	 * @return: Object
	 */
	@GET
	@Path("/mkt.segment.publishstatus.count.get")
	public Object segmentPublishstatusCount(@NotEmpty @QueryParam("user_token") String userToken,
						  					@NotEmpty @QueryParam("ver") String ver) throws Exception {
		return segmentPublishStatusCountService.segmentPublishstatusCount(userToken, ver);
	}
	
    /**
	 * @功能简述: 获取某个发布状态下的segemnt列表
	 * @param: String userToken, String ver, String publishStatus 
	 * @return: Object
	 */
	@GET
	@Path("/mkt.segment.publishstatus.list.get")
	public Object segmentPublishstatusList(@NotEmpty @QueryParam("user_token") String userToken,
						  				   @NotNull @QueryParam("publish_status") Integer publishStatus,
						  				   @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
						  				   @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size,
						  				   @NotEmpty @QueryParam("ver") String ver,
						  				   @QueryParam("keyword") String keyword) throws Exception {
		return segmentPublishstatusListService.segmentPublishstatusList(userToken,publishStatus,index,size,ver,keyword);
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
		return loginService.validateLoginPassword(input, securityContext);
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

	/**
	 * @功能描述:托管图文资产(这个功能暂时先不做) mkt.asset.imgtext.host
	 * @Param: String asset_url, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.asset.imgtext.host")
	@Consumes({MediaType.APPLICATION_JSON})
	public Object imgtextHostAsset(@Valid ImgtextHostIn imgtextHostIn,@Context SecurityContext securityContext){
		return imgtextHostService.hostImgtextAsset(imgtextHostIn, securityContext);
	}

	/**
	 * @功能简述: 获取不同类型微信资产的数量
	 * @param: String userToken, String ver
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.wechat.type.count.get")
	public Object getWechatAssetTypeCount(@NotEmpty @QueryParam("user_token") String userToken,
										  @NotEmpty @QueryParam("ver") String ver) throws Exception {
		return wechatTypeCountGetService.getWechatTypeCount();
	}

	/**
	 * @功能简述: 获取某个类型下的资产列表
	 * @param: String method, String userToken, String ver, int asset_type, int index, int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.wechat.type.list.get")
	public Object getWechatAssetListByType(@NotEmpty @QueryParam("method") String method,
	                                       @NotEmpty @QueryParam("user_token") String userToken,
	                                       @NotEmpty @QueryParam("ver") String ver,
	                                       @NotNull @QueryParam("asset_type") Integer assetType,
										   @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
										   @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) throws Exception {
		return wechatAssetListService.getWechatAssetListByType(assetType, index, size);
	}

	/**
	 * @功能简述: 获取文件接入的总览信息
	 * @param: String userToken, String ver
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.inbound.file.generalinfo.get")
	public Object getMigrationFileGeneralInfo(@NotEmpty @QueryParam("user_token") String userToken,
										   	  @NotEmpty @QueryParam("ver") String ver) throws Exception {
		return migrationFileGeneralInfoService.getMigrationFileGeneralInfo(null);
	}

	/**
	 * @功能简述: 获取文件模板下载列表
	 * @param: String userToken, String ver
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.inbound.file.template.list.get")
	public Object getMigrationFileTemplateList(@NotEmpty @QueryParam("user_token") String userToken,
											   @NotEmpty @QueryParam("ver") String ver) throws Exception {
		return migrationFileTemplateService.getMigrationFileTemplateList(null);
	}

	/**
	 * @功能简述: 获取文件上传url
	 * @param: String userToken, String ver
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.inbound.file.uploadurl.get")
	public Object getMigrationFileUploadUrl(@NotEmpty @QueryParam("user_token") String userToken,
											@NotEmpty @QueryParam("ver") String ver) throws Exception {
		return migrationFileUploadUrlService.getMigrationFileUploadUrl(null);
	}
	
    @GET
    @Path("/mkt.data.main.list.get")
    public Object getDataMainList(@NotEmpty @QueryParam("method") String method,
                    @NotEmpty @QueryParam("user_token") String userToken,
                    @QueryParam("index") Integer index, @QueryParam("size") Integer size,
                    @NotEmpty @QueryParam("ver") String ver) {
        return dataGetMainListService.getMainList(method, userToken, index, size, ver);
    }
}
