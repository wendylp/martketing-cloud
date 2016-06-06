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

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.CampaignBodyCreateService;
import cn.rongcapital.mkt.service.CampaignBodyGetService;
import cn.rongcapital.mkt.service.CampaignHeaderGetService;
import cn.rongcapital.mkt.service.DataDeleteMainService;
import cn.rongcapital.mkt.service.DataGetMainCountService;
import cn.rongcapital.mkt.service.DataGetMainListService;
import cn.rongcapital.mkt.service.DataGetQualityCountService;
import cn.rongcapital.mkt.service.DataGetQualityListService;
import cn.rongcapital.mkt.service.DataGetUnqualifiedCountService;
import cn.rongcapital.mkt.service.DataGetViewListService;
import cn.rongcapital.mkt.service.DeleteImgTextAssetService;
import cn.rongcapital.mkt.service.GetImgTextAssetService;
import cn.rongcapital.mkt.service.ImgtextHostService;
import cn.rongcapital.mkt.service.LoginService;
import cn.rongcapital.mkt.service.MigrationFileGeneralInfoService;
import cn.rongcapital.mkt.service.MigrationFileTemplateService;
import cn.rongcapital.mkt.service.MigrationFileUploadUrlService;
import cn.rongcapital.mkt.service.ModifyPasswdService;
import cn.rongcapital.mkt.service.SaveWechatAssetListService;
import cn.rongcapital.mkt.service.SegmentBodyUpdateService;
import cn.rongcapital.mkt.service.SegmentHeaderCreateService;
import cn.rongcapital.mkt.service.SegmentHeaderGetService;
import cn.rongcapital.mkt.service.SegmentHeaderUpdateService;
import cn.rongcapital.mkt.service.SegmentPublishStatusCountService;
import cn.rongcapital.mkt.service.SegmentPublishstatusListService;
import cn.rongcapital.mkt.service.UpdateNicknameService;
import cn.rongcapital.mkt.service.UploadFileService;
import cn.rongcapital.mkt.service.WechatAssetListGetService;
import cn.rongcapital.mkt.service.WechatAssetListService;
import cn.rongcapital.mkt.service.WechatTypeCountGetService;
import cn.rongcapital.mkt.service.GetImgtextAssetMenulistService;
import cn.rongcapital.mkt.vo.BaseInput;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgAsset;
import cn.rongcapital.mkt.vo.ImgtextHostIn;
import cn.rongcapital.mkt.vo.LoginInput;
import cn.rongcapital.mkt.vo.ModifyInput;
import cn.rongcapital.mkt.vo.SaveWechatAssetListIn;
import cn.rongcapital.mkt.vo.SegmentHeadCreateIn;
import cn.rongcapital.mkt.vo.SegmentHeadUpdateIn;
import cn.rongcapital.mkt.vo.UpdateNicknameIn;
import cn.rongcapital.mkt.vo.in.CampaignBodyCreateIn;
import cn.rongcapital.mkt.vo.in.SegmentBodyUpdateIn;
import cn.rongcapital.mkt.vo.out.CampaignBodyOut;

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
	private DataGetQualityListService dataGetQualityListService;
	@Autowired
	private DataGetQualityCountService dataGetQualityCountService;
	@Autowired
	private DataGetUnqualifiedCountService dataGetUnqualifiedCountService;
	@Autowired
	private DataGetMainCountService dataGetMainCountService;
	@Autowired
	private DataGetMainListService dataGetMainListService;
	@Autowired
	private DataDeleteMainService dataDeleteMainService;
	@Autowired
	private DataGetViewListService dataGetViewListService;
	@Autowired
	private SegmentHeaderGetService segmentHeaderGetService;
	@Autowired
	private WechatAssetListService wechatAssetListService;
	@Autowired
	private WechatAssetListGetService wechatAssetListGetService;
	@Autowired
	private UpdateNicknameService updateNicknameService;
	@Autowired
	private SaveWechatAssetListService saveWechatAssetListService;
	@Autowired
	private SegmentHeaderUpdateService segmentHeaderUpdateService;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private CampaignHeaderGetService campaignHeaderGetService;
    @Autowired
    private CampaignBodyGetService campaignBodyGetService;
    @Autowired
    private CampaignBodyCreateService campaignBodyCreateService;
    @Autowired
    private SegmentBodyUpdateService segmentBodyUpdateService;
	@Autowired
	private GetImgtextAssetMenulistService getImgtextAssetMenulistService;
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
	 * @功能简述: 获取图文资产
	 * @param:String user_token,String ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.imgtext.menulist.get")
	public Object getImgtextAssetMenulist(@NotEmpty @QueryParam("user_token") String userToken,
								  @NotEmpty @QueryParam("ver") String ver,
								  @DefaultValue("1") @Min(1) @QueryParam("index") int index,
								  @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") int size){
		BaseInput baseInput = new BaseInput();
		baseInput.setIndex(index);
		baseInput.setSize(size);
		return getImgtextAssetMenulistService.getImgTextAssetMenulist(baseInput);
	}

	/**
	 * @功能简述: 创建campaign body
	 * @param: SegmentHeadIn body, SecurityContext securityContext,Integer campaignHeadId 
	 * @return: Object
	 */
	@POST
	@Path("/mkt.campaign.body.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public CampaignBodyOut campaignBodyCreate(@Valid CampaignBodyCreateIn body, @Context SecurityContext securityContext) {
	    return campaignBodyCreateService.campaignBodyCreate(body, securityContext);
	}
	
	/**
	 * @功能简述: 根据campaign_head_id 获取campaign body
	 * @param: SegmentHeadIn body, SecurityContext securityContext,Integer campaignHeadId 
	 * @return: Object
	 */
	@GET
	@Path("/mkt.campaign.body.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object campaignBodyGet(@NotEmpty @QueryParam("user_token") String userToken,
								  @NotEmpty @QueryParam("ver") String ver,
								  @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
	    return campaignBodyGetService.campaignBodyGet(userToken, ver, campaignHeadId);
	}
	
	/**
	 * @功能简述: 根据id获取segment header
	 * @param: SegmentHeadIn body, SecurityContext securityContext,Integer campaignHeadId 
	 * @return: Object
	 */
	@GET
	@Path("/mkt.campaign.header.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object campaignHeaderGet(@NotEmpty @QueryParam("user_token") String userToken,
								   @NotEmpty @QueryParam("ver") String ver,
								   @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
	    return campaignHeaderGetService.campaignHeaderGet(userToken, ver, campaignHeadId);
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
	 * @param: SegmentHeadIn body, SecurityContext securityContext, String segmentId
	 * @return: Object
	 */
	@GET
	@Path("/mkt.segment.header.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput segmentHeaderGet(@NotEmpty @QueryParam("user_token") String userToken,
								   @NotEmpty @QueryParam("ver") String ver,
								   @NotEmpty @QueryParam("segment_head_id") String segmentId) {
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
	
	/**
     * @功能简述: 获取数据质量列表
     * @author nianjun
     * @param: String method, String userToken, String ver, Ingeger index, Integer size
     * @return: Object
     */
	@GET
    @Path("/mkt.data.quality.list.get")
    public Object getQualityList(@NotEmpty @QueryParam("method") String method,
                    @NotEmpty @QueryParam("user_token") String userToken,
                    @QueryParam("index") Integer index, @QueryParam("size") Integer size,
                    @NotEmpty @QueryParam("ver") String ver) {
        return dataGetQualityListService.getQualityList(method, userToken, index, size, ver);
    }

	/**
     * @功能简述 : 获取数据接入条数
     * @param: String method, String userToken, String ver
     * @author nianjun
     * @return: Object
     */
    @GET
    @Path("/mkt.data.quality.count.get")
    public Object getQualityCount(@NotEmpty @QueryParam("method") String method,
                    @NotEmpty @QueryParam("user_token") String userToken,
                    @NotEmpty @QueryParam("ver") String ver) {

        return dataGetQualityCountService.getQualityCount(method, userToken, ver);
    }

    /**
     * @功能简述 : 获取非法数据条数
     * @param: String method, String userToken, String ver, Long batchId
     * @author nianjun
     * @return: Object
     */
    @GET
    @Path("/mkt.data.unqualified.count.get")
    public Object getUnqualifiedCount(@NotEmpty @QueryParam("method") String method,
                    @NotEmpty @QueryParam("user_token") String userToken,
                    @NotEmpty @QueryParam("ver") String ver,
                    @NotNull @QueryParam("batch_id") Long batchId) {

        return dataGetUnqualifiedCountService.getQualityCount(method, userToken, ver, batchId);
    }

    /**
     * @功能简述 : 获取主数据条数
     * @param: String method, String userToken, String ver, Long batchId
     * @author nianjun
     * @return: Object
     */
    @GET
    @Path("/mkt.data.main.count.get")
    public Object getUnqualifiedCount(@NotEmpty @QueryParam("method") String method,
                    @NotEmpty @QueryParam("user_token") String userToken,
                    @NotEmpty @QueryParam("ver") String ver) {

        return dataGetMainCountService.getMainCount(method, userToken, ver);
    }

	/**
     * @功能简述 : 获取主数据列表
     * @param: String method, String userToken, String ver, Ingeger index, Integer size
     * @return: Object
     */
    @GET
    @Path("/mkt.data.main.list.get")
    public Object getDataMainList(@NotEmpty @QueryParam("method") String method,
                    @NotEmpty @QueryParam("user_token") String userToken,
                    @NotNull @QueryParam("data_type") Integer dataType,
                    @QueryParam("index") Integer index, @QueryParam("size") Integer size,
                    @NotEmpty @QueryParam("ver") String ver) {
        return dataGetMainListService.getMainList(method, userToken, dataType, index, size, ver);
    }

    /**
     * @功能简述 : 删除某条主数据
     * @param: String method, String userToken, String ver, dataId
     * @return: Object
     */
    @GET
    @Path("/mkt.data.main.delete")
    public Object deleteDataMain(@NotEmpty @QueryParam("method") String method,
                    @NotEmpty @QueryParam("user_token") String userToken,
                    @NotNull @QueryParam("data_id") Integer dataId,
                    @NotEmpty @QueryParam("ver") String ver) {
        return dataDeleteMainService.deleteMain(method, userToken, ver, dataId);
    }

    /**
     * @功能简述 : 查询自定义视图字段列表
     * @param: String method, String userToken, String ver, Integer mdType
     * @return: Object
     */
    @GET
    @Path("/mkt.data.view.list.get")
    public Object getViewList(@NotEmpty @QueryParam("method") String method,
                    @NotEmpty @QueryParam("user_token") String userToken,
                    @NotNull @QueryParam("md_type") Integer mdType,
                    @NotEmpty @QueryParam("ver") String ver) {
        return dataGetViewListService.getViewList(method, userToken, ver, mdType);
    }

	/**
	 * @功能简述: 获取某个微信账号下的好友/粉丝/群组信息
	 * @param: String userToken, String ver, Ingeger asset_id
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.wechat.list.get")
	public Object getWechatAssetTypeCount(@NotEmpty @QueryParam("user_token") String userToken,
										  @NotEmpty @QueryParam("ver") String ver,
	                                      @NotNull @QueryParam("asset_id") Integer assetId) throws Exception {
		return wechatAssetListGetService.getWechatAssetList(assetId);
	}

	/**
	 * @功能描述:编辑mkt系统中微信的昵称，不是微信中的昵称
	 * @Param: String user_token, String ver, Integer asset_id, String nickname
	 * @return: Object
	 */
	@POST
	@Path("/mkt.asset.wechat.nickname.update")
	@Consumes({MediaType.APPLICATION_JSON})
	public Object updateWechatNickname(@Valid UpdateNicknameIn updateNicknameIn,@Context SecurityContext securityContext){
		return updateNicknameService.updateNickname(updateNicknameIn, securityContext);
	}

	/**
	 * @功能描述:保存微信账号下的人群
	 * @Param: String user_token, String ver, Integer asset_id, String nickname
	 * @return: Object
	 */
	@POST
	@Path("/mkt.asset.wechat.list.save")
	@Consumes({MediaType.APPLICATION_JSON})
	public Object saveWechatAssetList(@Valid SaveWechatAssetListIn saveWechatAssetListIn,@Context SecurityContext securityContext){
		return saveWechatAssetListService.saveWechatAssetList(saveWechatAssetListIn, securityContext);
	}

	/**
	 * @功能描述:文件上传
	 * @Param: String user_token, String ver, Integer asset_id, String nickname
	 * @return: Object
	 */
	@POST
	@Path("/mkt.service.file.upload")
	@Consumes("multipart/form-data")
	public Object fileUpload(
			@QueryParam("file_source") String fileSource,
			@NotEmpty @QueryParam("file_unique") String fileUnique,
			@QueryParam("file_type") int fileType,
			MultipartFormDataInput input, @Context SecurityContext securityContext){
		return uploadFileService.uploadFile(fileSource,fileUnique,fileType,input,securityContext);
	}

	/**
	 * @功能简述: 编辑segment body
	 * @param: SegmentBodyUpdateIn body, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.segment.body.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object segmentBodyUpdate(@Valid SegmentBodyUpdateIn body, @Context SecurityContext securityContext) {
	    return segmentBodyUpdateService.segmentBodyUpdate(body, securityContext);
	}
}
