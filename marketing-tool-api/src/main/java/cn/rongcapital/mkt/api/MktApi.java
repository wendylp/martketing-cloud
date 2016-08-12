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

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cn.rongcapital.mkt.vo.in.*;
import cn.rongcapital.mkt.vo.out.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.ContactWay;
import cn.rongcapital.mkt.po.TaskRunLog;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.BaseInput;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgAsset;
import cn.rongcapital.mkt.vo.ImgtextHostIn;
import cn.rongcapital.mkt.vo.LoginInput;
import cn.rongcapital.mkt.vo.ModifyInput;
import cn.rongcapital.mkt.vo.SaveWechatAssetListIn;
import cn.rongcapital.mkt.vo.UpdateNicknameIn;

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
	private DataGetFilterContactwayService dataGetFilterContactwayService;

	@Autowired
	private DataGetFilterRecentTaskService dataGetFilterRecentTaskService;

	@Autowired
	private DataGetFilterAudiencesService dataGetFilterAudiencesService;

	@Autowired
	private DataUpateMainSegmenttagService dataUpateMainSegmenttagService;

	@Autowired
	private DataDownloadQualityLogService dataDownloadQualityLogService;

	@Autowired
	private DataDownloadMainListService dataDownloadMainListService;

	@Autowired
	private TagGetCustomService tagGetCustomService;

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
	private SaveCampaignAudienceService saveCampaignAudienceService;

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
	private AudienceListService audienceListService;

	@Autowired
	private AudienceNameListService audienceNameListService;

	@Autowired
	private AudienceIdListService audienceIdListService;

	@Autowired
	private AudienceSearchService audienceSearchService;

	@Autowired
	private AudienceListDeleteService audienceListDeleteService;

	@Autowired
	private SegmentBodyUpdateService segmentBodyUpdateService;

	@Autowired
	private GetImgtextAssetMenulistService getImgtextAssetMenulistService;

	@Autowired
	private TaskListGetService taskListGetService;

	@Autowired
	private CampaignDeleteService campaignDeleteService;

	@Autowired
	private CampaignSummaryGetService campaignSummaryGetService;

	@Autowired
	private CampaignProgressStatusCountService campaignProgressStatusCountService;

	@Autowired
	private CampaignProgressStatusListService campaignProgressStatusListService;

	@Autowired
	private SegmentTagnameTagListService segmentTagnameTagListService;

	@Autowired
	private SegmentTagkeyTagListService segmentTagkeyTagListService;

	@Autowired
	private SegmentTagnameTagValueService segmentTagnameTagValueService;

	@Autowired
	private SegmentTagnameTagCountService segmentTagnameTagCountService;

	@Autowired
	private TagSystemTagcountService tagSystemTagcountService;

	@Autowired
	private SegmentBodyGetService segmentBodyGetService;

	@Autowired
	private CustomTagGetService customTagGetService;

	@Autowired
	private CustomTagDeleteService customTagDeleteService;

	@Autowired
	private MainActionInfoGetService mainActionInfoGetService;

	@Autowired
	private SegmentTagGetService segmentTagGetService;

	@Autowired
	private SegmentFilterGetService segmentFilterGetService;

	@Autowired
	private SegmentTagUpdateService segmentTagUpdateService;

	@Autowired
	private GetImgtextCountService getImgtextCountService;

	@Autowired
	private DataMainRadarInfoGetService dataMainRadarInfoGetService;

	@Autowired
	private GetDataMainSearchService getDataMainSearchService;

	@Autowired
	private GetDataMainSearchByIdService getDataMainSearchByIdService;

	@Autowired
	private DataMainBasicInfoUpdateService dataMainBasicInfoUpdateService;

	@Autowired
	private TagSystemListGetService tagSystemListGetService;

	@Autowired
	private TaggroupSystemListGetService taggroupSystemListGetService;

	@Autowired
	private MainBasicInfoGetService mainBasicInfoGetService;

	@Autowired
	private TaggroupSystemMenulistGetService taggroupSystemMenulistGetService;

	@Autowired
	private CampaignNodeItemListGetService campaignNodeItemListGetService;

	@Autowired
	private CampaignHeaderCreateService campaignHeaderCreateService;

	@Autowired
	private CampaignHeaderUpdateService campaignHeaderUpdateService;

	@Autowired
	private GroupTagsSearchService groupTagsSearchService;

	@Autowired
	private WechatPublicAuthService wechatPublicAuthService;

	@Autowired
	private WechatPublicAuthCallbackService wechatPublicAuthCallbackService;

	@Autowired
	private WechatPersonalAuthService wechatPersonalAuthService;

	@Autowired
	private ReauthWechatAccountService reauthWechatAccountService;

	@Autowired
	private TaskGetListService taskGetListService;

	@Autowired
	private FileTemplateDownloadService fileTemplateDownloadService;

	@Autowired
	private WechatPeopleDetailDownloadService wechatPeopleDetailDownloadService;

	@Autowired
	private FileTagUpdateService fileTagUpdateService;

	@Autowired
	private CampaignBodyItemAudienceSearchService campaignBodyItemAudienceSearchService;

	@Autowired
	private WechatAssetMemberSearchService wechatAssetMemberSearchService;

	@Autowired
	private TagDownloadCustomAudienceService tagDownloadCustomAudienceService;

	@Autowired
	private DataDownloadQualityIllegalDataService dataDownloadQualityIllegalDataService;

	@Autowired
	private HomePageUserCountListService homePageUserCountListService;

	@Autowired
	private HomePageDataCountListService homePageDataCountListService;

	@Autowired
	private GetCampaignConvertChartListService getCampaignConvertChartListService;

	@Autowired
	private GetCampaignCustomerSourceListService getCampaignCustomerSourceListService;

	@Autowired
	private GetWechatUserListService getWechatUserListService;

	@Autowired
	private HomePageDataSourceListService homePageDataSourceListService;

	@Autowired
	private HomePageCalendarListService homePageCalendarListService;

	@Autowired
	private HomePageCalendarPopService homePageCalendarPopService;
	
	@Autowired
	private WechatChannelListService wechatChannelListService;
	
	@Autowired
	private WechatChanellUpdateService wechatChanellUpdateService;

	@Autowired
	private RegisterListService regListService;
	@Autowired
	private TagUpdateService tagService;	

	@Autowired
	private ContactTemplateServer contactTemplateServer;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
   
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
	 * @功能简述: 获取图文资产
	 * @param:String user_token,String
	 *                   ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.imgtext.get")
	public Object getImgTextAsset(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("type") Integer type,
			@QueryParam("owner_name") String ownerName, @DefaultValue("1") @Min(1) @QueryParam("index") int index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") int size) {
		ImgAsset imgAsset = new ImgAsset();
		imgAsset.setAssetType(type);
		imgAsset.setVer(ver);
		if (ownerName != null) {
			imgAsset.setOwnerName(ownerName);
		}
		if (index != 0) {
			imgAsset.setIndex(index);
		} else {
			imgAsset.setIndex(1);
		}
		if (size != 0) {
			imgAsset.setSize(size);
		} else {
			imgAsset.setSize(10);
		}
		return getImgTextAssetService.getImgTextAssetService(imgAsset);
	}

	/**
	 * @功能简述: 获取图文资产
	 * @param:String user_token,String
	 *                   ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.imgtext.menulist.get")
	public Object getImgtextAssetMenulist(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver) {
		BaseInput baseInput = new BaseInput();
		return getImgtextAssetMenulistService.getImgTextAssetMenulist(baseInput);
	}

	/**
	 * @功能简述: 获取图文资产
	 * @param:String user_token,String
	 *                   ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.imgtext.count.get")
	public Object getImgtextAssetCount(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver) {
		return getImgtextCountService.getImgtextAssetCount();
	}

	/**
	 * @功能简述: 获取活动编排页面左侧的节点和子节点列表
	 * @param: SegmentHeadIn
	 *             body, SecurityContext securityContext
	 * @return: Object
	 */
	@GET
	@Path("/mkt.campaign.nodeitem.list.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public CampaignNodeItemListOut campaignBodyGet(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver) {
		return campaignNodeItemListGetService.campaignNodeItemListGet(userToken, ver);
	}

	/**
	 * @功能简述: 创建/编辑campaign body
	 * @param: SegmentHeadIn
	 *             body, SecurityContext securityContext,Integer campaignHeadId
	 * @return: Object
	 */
	@POST
	@Path("/mkt.campaign.body.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public CampaignBodyCreateOut campaignBodyCreate(@Valid CampaignBodyCreateIn body,
			@Context SecurityContext securityContext) {
		return campaignBodyCreateService.campaignBodyCreate(body, securityContext);
	}

	/**
	 * @功能简述: 根据campaign_head_id 获取campaign body
	 * @param: SegmentHeadIn
	 *             body, SecurityContext securityContext,Integer campaignHeadId
	 * @return: Object
	 */
	@GET
	@Path("/mkt.campaign.body.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public CampaignBodyGetOut campaignBodyGet(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
		return campaignBodyGetService.campaignBodyGet(userToken, ver, campaignHeadId);
	}

	/**
	 * @功能简述: 编辑campaign header
	 * @param: CampaignHeadUpdateIn
	 *             body, SecurityContext securityContext
	 * @return: BaseOutput
	 */
	@POST
	@Path("/mkt.campaign.header.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput campaignHeaderUpdate(@Valid CampaignHeadUpdateIn body, @Context SecurityContext securityContext) {
		return campaignHeaderUpdateService.campaignHeaderUpdate(body, securityContext);
	}

	/**
	 * @功能简述: 创建campaign header
	 * @param: CampaignHeadCreateIn
	 *             body, SecurityContext securityContext
	 * @return: BaseOutput
	 */
	@POST
	@Path("/mkt.campaign.header.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput campaignHeaderCreate(@Valid CampaignHeadCreateIn body, @Context SecurityContext securityContext) {
		return campaignHeaderCreateService.campaignHeaderCreate(body, securityContext);
	}

	/**
	 * @功能简述: 根据id获取segment header
	 * @param: SegmentHeadIn
	 *             body, SecurityContext securityContext,Integer campaignHeadId
	 * @return: Object
	 */
	@GET
	@Path("/mkt.campaign.header.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public CampaignHeaderGetOut campaignHeaderGet(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
		return campaignHeaderGetService.campaignHeaderGet(userToken, ver, campaignHeadId);
	}

	/**
	 * 活动概要查询
	 * 
	 * @param userToken
	 * @param ver
	 * @param campaignHeadId
	 * @return
	 */
	@GET
	@Path("/mkt.campaign.profile.list")
	@Consumes({ MediaType.APPLICATION_JSON })
	public CampaignProfileOut campaignProfileList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
		return campaignHeaderGetService.campaignProfileList(userToken, ver, campaignHeadId);
	}

	/**
	 * @功能简述: 编辑segment header
	 * @param: SegmentHeadIn
	 *             body, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.segment.header.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput segmentHeaderUpdate(@Valid SegmentHeadUpdateIn body, @Context SecurityContext securityContext) {
		return segmentHeaderUpdateService.segmentHeaderUpdate(body, securityContext);
	}

	/**
	 * @功能简述: 根据id获取segment header
	 * @param: SegmentHeadIn
	 *             body, SecurityContext securityContext, String segmentId
	 * @return: Object
	 */
	@GET
	@Path("/mkt.segment.header.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput segmentHeaderGet(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("segment_head_id") String segmentId) {
		return segmentHeaderGetService.segmentHeaderGet(userToken, ver, segmentId);
	}

	/**
	 * @功能简述: 创建segment header
	 * @param: SegmentHeadIn
	 *             body, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.segment.header.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput segmentHeaderCreate(@Valid SegmentHeadCreateIn body, @Context SecurityContext securityContext) {
		return segmentHeaderService.segmentHeaderCreate(body, securityContext);
	}

	/**
	 * @功能简述: 创建segment header
	 * @param: SegmentHeadIn
	 *             body, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.segment.header.delete")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput segmentHeaderDelete(@Valid SegmentHeadDeleteIn body, @Context SecurityContext securityContext) {
		return segmentHeaderUpdateService.deleteSegmentHeader(body, securityContext);
	}

	/**
	 * @功能简述: 查询不同发布状态下的segment数量
	 * @param: String
	 *             userToken, String ver
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
	 * @param: String
	 *             userToken, String ver, String publishStatus
	 * @return: Object
	 */
	@GET
	@Path("/mkt.segment.publishstatus.list.get")
	public SegmentPublishstatusListOut segmentPublishstatusList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotNull @QueryParam("publish_status") Integer publishStatus,
			@DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size,
			@NotEmpty @QueryParam("ver") String ver, @QueryParam("keyword") String keyword) throws Exception {
		return segmentPublishstatusListService.segmentPublishstatusList(userToken, publishStatus, index, size, ver,
				keyword);
	}

	@POST
	@Path("/mkt.segment.gendercount.list")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput segmentGenderCountList(@Valid SegmentCountFilterIn input) {
		return segmentFilterGetService.segmentGenderCountList(input);
	}

	@POST
	@Path("/mkt.segment.provincecount.list")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput segmentProvinceCountList(@Valid SegmentCountFilterIn input) {
		return segmentFilterGetService.segmentProvinceCountList(input);
	}

	@POST
	@Path("/mkt.segment.receivecount.list")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput segmentReceiveCountList(@Valid SegmentCountFilterIn input) {
		return segmentFilterGetService.segmentReceiveCountList(input);
	}

	/**
	 * @功能描述: 登录接口
	 * @Param: LoginIn loginIn, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.user.login")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object login(@Valid LoginInput input, @Context SecurityContext securityContext) {
		return loginService.validateLoginPassword(input, securityContext);
	}

	/**
	 * @功能描述: 修改密码
	 * @Param: ModifyIn modifyIn, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.user.modifypasswd")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object modifyPasswd(@Valid ModifyInput input, @Context SecurityContext securityContext) {
		return modifyPasswdService.modifyPasswd(input, securityContext);
	}

	/**
	 * @功能描述:删除图文资产 mkt.asset.imgtext.delete
	 * @Param: LoginIn loginIn, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.asset.imgtext.delete")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object deleteImgTextAsset(@Valid ImgAsset imgAsset, @Context SecurityContext securityContext) {
		return deleteImgTextAssetService.deleteImgTextService(imgAsset.getImgtextId());
	}

	/**
	 * @功能描述:托管图文资产(这个功能暂时先不做) mkt.asset.imgtext.host
	 * @Param: String asset_url, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.asset.imgtext.host")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object imgtextHostAsset(@Valid ImgtextHostIn imgtextHostIn, @Context SecurityContext securityContext) {
		return imgtextHostService.hostImgtextAsset(imgtextHostIn, securityContext);
	}

	/**
	 * @功能简述: 获取不同类型微信资产的数量
	 * @param: String
	 *             userToken, String ver
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
	 * @param: String
	 *             method, String userToken, String ver, int asset_type, int
	 *             index, int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.wechat.type.list.get")
	public Object getWechatAssetListByType(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver,
			@NotNull @QueryParam("asset_type") Integer assetType,
			@DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) throws Exception {
		return wechatAssetListService.getWechatAssetListByType(assetType, index, size);
	}

	/**
	 * @功能简述: 获取文件接入的总览信息
	 * @param: String
	 *             userToken, String ver
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
	 * @param: String
	 *             userToken, String ver
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
	 * @param: String
	 *             userToken, String ver
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
	 * @param: String
	 *             method, String userToken, String ver, Integer index, Integer
	 *             size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.quality.list.get")
	public Object getQualityList(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @QueryParam("index") Integer index,
			@QueryParam("size") Integer size, @NotEmpty @QueryParam("ver") String ver) {
		return dataGetQualityListService.getQualityList(method, userToken, index, size, ver);
	}

	/**
	 * @功能简述 : 获取数据接入条数
	 * @param: String
	 *             method, String userToken, String ver
	 * @author nianjun
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.quality.count.get")
	public Object getQualityCount(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver) {

		return dataGetQualityCountService.getQualityCount(method, userToken, ver);
	}

	/**
	 * @功能简述 : 获取非法数据条数
	 * @param: String
	 *             method, String userToken, String ver, Long batchId
	 * @author nianjun
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.unqualified.count.get")
	public Object getUnqualifiedCount(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver,
			@NotNull @QueryParam("batch_id") Long batchId) {

		return dataGetUnqualifiedCountService.getQualityCount(method, userToken, ver, batchId);
	}

	/**
	 * @功能简述 : 获取主数据条数
	 * @param: String
	 *             method, String userToken, String ver, Long batchId
	 * @author nianjun
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.main.count.get")
	public Object getUnqualifiedCount(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver) {

		return dataGetMainCountService.getMainCount(method, userToken, ver);
	}

	/**
	 * @功能简述 : 获取主数据列表
	 * @param: String
	 *             method, String userToken, String ver, Integer index, Integer
	 *             size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.main.list.get")
	public Object getDataMainList(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @NotNull @QueryParam("md_type") Integer dataType,
			@QueryParam("index") Integer index, @QueryParam("size") Integer size,
			@NotEmpty @QueryParam("ver") String ver) {
		return dataGetMainListService.getMainList(method, userToken, dataType, index, size, ver);
	}

	/**
	 * @功能简述 : 删除某条主数据
	 * @param: String
	 *             method, String userToken, String ver, dataId
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.main.delete")
	public Object deleteDataMain(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @NotNull @QueryParam("data_id") Integer dataId,
			@NotEmpty @QueryParam("ver") String ver) {
		return dataDeleteMainService.deleteMain(method, userToken, ver, dataId);
	}

	/**
	 * @功能简述 : 查询自定义视图字段列表
	 * @param: String
	 *             method, String userToken, String ver, Integer mdType
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.view.list.get")
	public Object getViewList(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @NotNull @QueryParam("md_type") Integer mdType,
			@NotEmpty @QueryParam("ver") String ver) {
		return dataGetViewListService.getViewList(method, userToken, ver, mdType);
	}

	/**
	 * @功能简述 : 查询联系方式
	 * @param: String
	 *             method, String userToken, String ver
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.filter.contactway.get")
	public Object getFilterContactway(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver) {

		List<ContactWay> contactWayList = dataGetFilterContactwayService.getFilterContactway(method, userToken, ver);
		DataGetFilterContactwayOut result = new DataGetFilterContactwayOut(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		if (contactWayList != null && !contactWayList.isEmpty()) {
			for (ContactWay contactWay : contactWayList) {
				Map<String, Object> map = new HashMap<>();
				map.put("contact_id", contactWay.getId());
				map.put("contact_way", contactWay.getName());
				result.getData().add(map);
			}
		}

		result.setTotal(result.getData().size());
		return Response.ok().entity(result).build();
	}

	/**
	 * @功能简述 : 查询最近完成的数据接入任务
	 * @param: String
	 *             method, String userToken, String ver, String condition
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.filter.recenttask.get")
	public Object getFilterContactway(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver,
			@NotEmpty @QueryParam("condition") String condition) {

		List<TaskRunLog> taskRunLogList = dataGetFilterRecentTaskService.getFilterRecentTask(method, userToken, ver,
				condition);
		DataGetFilterRecentTaskOut result = new DataGetFilterRecentTaskOut(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		if (taskRunLogList != null && !taskRunLogList.isEmpty()) {
			for (TaskRunLog taskRunLog : taskRunLogList) {
				Map<String, Object> map = new HashMap<>();
				map.put("task_id", taskRunLog.getId());
				map.put("filename", taskRunLog.getTaskName());
				result.getData().add(map);
			}
		}

		result.setTotal(result.getData().size());
		return Response.ok().entity(result).build();
	}

	/**
	 * @功能简述 : 根据快捷筛选查询某类型的主数据
	 * @param: String
	 *             method, String userToken, String ver, String mdType, List
	 *             taskIdList
	 * @return: Object
	 */
	@POST
	@Path("/mkt.data.filter.audiences.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object getFilterAudiences(@Valid DataGetFilterAudiencesIn dataGetFilterAudiencesIn) {
		return dataGetFilterAudiencesService.getFilterAudiences(dataGetFilterAudiencesIn.getMethod(),
				dataGetFilterAudiencesIn.getUserToken(), dataGetFilterAudiencesIn.getVer(),
				dataGetFilterAudiencesIn.getIndex(), dataGetFilterAudiencesIn.getSize(),
				dataGetFilterAudiencesIn.getMdType(), dataGetFilterAudiencesIn.getMdTypes(),
				dataGetFilterAudiencesIn.getContactIds(), dataGetFilterAudiencesIn.getCustomizeViews(),
				dataGetFilterAudiencesIn.getTimeCondition());
	}

	/**
	 * @功能简述 : 根据联系人id更新联系人标签
	 */
	@POST
	@Path("/mkt.data.main.segmenttag.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object updateMainSegmenttag(@Valid DataUpdateMainSegmenttagIn dataUpdateMainSegmenttagIn) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		dataUpateMainSegmenttagService.updateMainSegmenttag(dataUpdateMainSegmenttagIn.getMethod(),
				dataUpdateMainSegmenttagIn.getUserToken(), dataUpdateMainSegmenttagIn.getVer(),
				dataUpdateMainSegmenttagIn.getTagName(), dataUpdateMainSegmenttagIn.getContactId());

		return result;
	}

	/**
	 * @功能简述 : 根据联系人id获取联系人标签
	 */
	@GET
	@Path("/mkt.data.main.segmenttag.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object getMainSegmenttagNames(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("map_id") Integer map_id) {
		return dataUpateMainSegmenttagService.getMainSegmenttagNames(map_id);

	}

	@GET
	@Path("/mkt.tag.user.custom.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object getCustomTag(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("contact_id") Integer contactId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		List<String> customTags = tagGetCustomService.getCustomizeTagByContactId(ver, contactId);

		result.getData().addAll(customTags);
		result.setTotal(result.getData().size());
		return result;
	}

	/**
	 * @功能简述: 获取某个微信账号下的好友/粉丝/群组信息
	 * @param: String
	 *             userToken, String ver, Integer asset_id
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.wechat.list.get")
	public Object getWechatAssetTypeCount(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("asset_id") Integer assetId)
			throws Exception {
		return wechatAssetListGetService.getWechatAssetList(assetId);
	}

	/**
	 * @功能描述:编辑mkt系统中微信的昵称，不是微信中的昵称
	 * @Param: String user_token, String ver, Integer asset_id, String nickname
	 * @return: Object
	 */
	@POST
	@Path("/mkt.asset.wechat.nickname.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object updateWechatNickname(@Valid UpdateNicknameIn updateNicknameIn,
			@Context SecurityContext securityContext) {
		return updateNicknameService.updateNickname(updateNicknameIn, securityContext);
	}

	/**
	 * @功能描述:保存微信账号下的人群
	 * @Param: String user_token, String ver, Integer asset_id, String nickname
	 * @return: Object
	 */
	@POST
	@Path("/mkt.asset.wechat.list.save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object saveWechatAssetList(@Valid SaveWechatAssetListIn saveWechatAssetListIn,
			@Context SecurityContext securityContext) {
		return saveWechatAssetListService.saveWechatAssetList(saveWechatAssetListIn, securityContext);
	}

	/**
	 * @功能描述:活动编排，保存人群
	 * @Param: String user_token, String ver, String audience_name
	 * @return: Object
	 */
	@POST
	@Path("/mkt.campaign.node.audience.save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object saveCampaignAudience(@Valid Audience audience, @Context SecurityContext securityContext) {
		return saveCampaignAudienceService.saveCampaignAudience(audience, securityContext);
	}

	/**
	 * @功能描述:重新授权或者重新登录
	 * @Param: String user_token, String ver, Integer asset_id, String nickname
	 * @return: Object
	 */
	// @POST
	// @Path("/mkt.asset.wechat.reauth")
	// @Consumes({MediaType.APPLICATION_JSON})
	// public Object reauthWechatAccount(@Valid ReauthWechatAccountIn
	// reauthWechatAccountIn,@Context SecurityContext securityContext){
	// return
	// reauthWechatAccountService.reauthWechatAccount(reauthWechatAccountIn);
	// }

	/**
	 * @功能描述:文件上传
	 * @Param: String user_token, String ver, Integer asset_id, String nickname
	 * @return: Object
	 */
	@POST
	@Path("/mkt.service.file.upload")
	@Consumes("multipart/form-data")
	public Object fileUpload(@NotEmpty @QueryParam("file_unique") String fileUnique, MultipartFormDataInput input) {
		return uploadFileService.uploadFile(fileUnique, input);
	}

	/**
	 * @功能描述:文件上传
	 * @Param: String user_token, String ver, Integer asset_id, String nickname
	 * @return: Object
	 */
	@POST
	@Path("/mkt.service.file.repiar.upload")
	@Consumes("multipart/form-data")
	public Object uploadRepiarFile(@NotEmpty @QueryParam("file_unique") String fileUnique,
			MultipartFormDataInput input) {
		return uploadFileService.uploadRepairFile(fileUnique, input);
	}

	/**
	 * @功能简述: 数据分析，获取人群名称列表
	 * @param: String
	 *             userToken
	 * @return: Object
	 */
	@GET
	@Path("/mkt.dataanalysis.audname.list.get")
	public BaseOutput audienceNameList(@NotEmpty @QueryParam("user_token") String userToken) {
		return audienceNameListService.audienceNameList(userToken);
	}

	/**
	 * @功能简述: 数据分析，获取联系人ID列表
	 * @param: String
	 *             userToken
	 * @return: Object
	 */
	@GET
	@Path("/mkt.dataanalysis.audiences.get")
	public BaseOutput audienceIdList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("audience_ids") String audience_ids,
			@NotEmpty @QueryParam("audience_type") String audience_type) {
		return audienceIdListService.audienceIdList(userToken, audience_ids, audience_type);
	}

	/**
	 * @功能简述: 获取人群list列表
	 * @param: String
	 *             userToken
	 * @return: Object
	 */
	@GET
	@Path("/mkt.audience.list.get")
	public BaseOutput audienceList(@NotEmpty @QueryParam("user_token") String userToken,
			@DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
		return audienceListService.audienceList(userToken, size, index);
	}

	/**
	 *
	 * @param userToken
	 * @return
	 */
	@GET
	@Path("/mkt.audience.count.get")
	public BaseOutput audienceCount(@NotEmpty @QueryParam("user_token") String userToken) {
		return audienceListService.audienceCount(userToken);
	}

	/**
	 * @功能简述: 在人群中查找
	 * @param: String
	 *             userToken
	 * @param: String
	 *             audience_id 人群ID
	 * @param: String
	 *             audience_name 人群名字
	 * @return: Object
	 */
	@GET
	@Path("/mkt.audience.search.get")
	public BaseOutput audienceByName(@NotEmpty @QueryParam("user_token") String userToken,
			@QueryParam("audience_type") String audience_type, @QueryParam("audience_id") int audience_id,
			@QueryParam("search_field") String audience_name,
			@DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
		return audienceSearchService.audienceByName(userToken, audience_type, audience_id, audience_name, size, index);
	}

	/**
	 * @功能描述:删除人群list
	 * @Param: body
	 * @param securityContext
	 * @return: BaseOutput
	 */
	@POST
	@Path("/mkt.audience.list.delete")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput audienceListDel(@Valid AudienceListDeleteIn body, @Context SecurityContext securityContext) {
		return audienceListDeleteService.audienceListDel(body.getAudienceListId(), securityContext);
	}

	/**
	 * @功能描述:查询系统推荐标签列表
	 * @Param: String method, String userToken
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.segment.tagname.taglist.get")
	public BaseOutput getSysRecommendedTagList(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken) {
		return segmentTagnameTagListService.getSysRecommendedTagList();
	}

	/**
	 * @功能简述: 编辑segment body
	 * @param: SegmentBodyUpdateIn
	 *             body, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.segment.body.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object segmentBodyUpdate(@Valid SegmentBodyUpdateIn body, @Context SecurityContext securityContext) {
		return segmentBodyUpdateService.segmentBodyUpdate(body, securityContext);
	}

	/**
	 * @功能简述: 获取后台任务列表
	 * @author nianjun
	 * @param:
	 * @return: Object
	 */
	@GET
	@Path("/mkt.task.list.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput taskListGet(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken) {
		return taskGetListService.getTaskList();
	}

	/**
	 * @功能简述: 删除campaign
	 * @param: campaign_head_id
	 *             营销活动id
	 * @return: Object
	 */
	@POST
	@Path("/mkt.campaign.delete")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput campaignDelete(@Valid CampaignDeleteIn campaignDeleteIn,
			@Context SecurityContext securityContext) {
		return campaignDeleteService.campaignDelete(campaignDeleteIn.getCampaignId());
	}

	/**
	 * @功能简述: 查询营销活动个数和触达人数
	 * @param:
	 * @return: Object
	 */
	@GET
	@Path("/mkt.campaign.summary.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput campaignSummaryGet(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken) {
		return campaignSummaryGetService.campaignSummaryGet();
	}

	/**
	 * @功能简述: 获取不同状态下的campaign数量
	 * @param:
	 * @return: Object
	 */
	@GET
	@Path("/mkt.campaign.progressstatus.count.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public CampaignProgressStatusCountOut campaignProgressStatusCount(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken) {
		return campaignProgressStatusCountService.campaignProgressStatusCountGet();
	}

	/**
	 * @功能简述: 获取不同状态下的campaign列表
	 * @param: publish_status
	 *             0:未发布,1:已发布,2:活动中,3:已结束,4:全部 campaign_name
	 *             活动名称摘要，如果有代表作前端界面的模糊查询 index 开始页索引，默认为1 size
	 *             分页大小，默认为10，最大值为100
	 * @return: Object
	 */
	@GET
	@Path("/mkt.campaign.progressstatus.list.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput campaignProgressStatusListGet(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken,
			@NotNull @QueryParam("publish_status") Byte publishStatus, @QueryParam("campaign_name") String campaignName,
			@DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
		return campaignProgressStatusListService.campaignProgressStatusList(publishStatus, campaignName, index, size);
	}

	/*
	 * @功能简述: 根据关键字查询出系统最末级标签名称列表
	 * 
	 * @param method
	 * 
	 * @param userToken
	 * 
	 * @param tagGroupName
	 * 
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.segment.tagkey.taglist.get")
	public BaseOutput getLastTagByKey(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @QueryParam("tag_group_name") String tagGroupName) {
		return segmentTagkeyTagListService.getLastTagByKey(tagGroupName);
	}

	/*
	 * @功能简述: 根据关键字查询出系统最末级标签组名称和所关联的标签列表
	 * 
	 * @param method
	 * 
	 * @param userToken
	 * 
	 * @param tagGroupName
	 * 
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.tag.search.grouptags.get")
	public SerarchTagGroupTagsOut getGroupTagsByKey(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @QueryParam("tag_group_name") String tagGroupName) {
		return groupTagsSearchService.groupTagsSearch(method, userToken, tagGroupName);
	}

	/**
	 * @功能简述: 根据系统最末级标签组ID查询出标签内容列表
	 * @param method
	 * @param userToken
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.segment.tagname.tagvalue.get")
	public BaseOutput getTagValueByGroupId(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("tag_group_id") String tagGroupId) {
		return segmentTagnameTagValueService.getTagValueByGroupId(tagGroupId);
	}

	/**
	 * @功能简述: 获取标签的柱状图数据
	 * @param method
	 * @param userToken
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.segment.tagname.tagcount.get")
	public BaseOutput getTagCountByGroupId(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("tag_ids") String tagIds) {
		return segmentTagnameTagCountService.getTagCountById(tagIds);
	}

	/**
	 * @功能简述: 获取受众细分body信息
	 * @param userToken
	 * @param segmentHeadId
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.segment.body.get")
	public BaseOutput getSegmentBody(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("segment_head_id") String segmentHeadId) {
		return segmentBodyGetService.getSegmentBody(userToken, segmentHeadId);
	}

	/**
	 * @功能简述: 获取某联系人行为信息
	 * @param userToken
	 * @param contactId
	 * @param behaviorType
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.main.actioninfo.get")
	public BaseOutput getPartyBehaviorByCondition(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("contact_id") String contactId,
			@NotEmpty @QueryParam("behavior_type") String behaviorType) {
		return mainActionInfoGetService.getMainActionInfo(contactId, behaviorType);
	}

	/**
	 * @功能简述: 统计联系人行为数量
	 * @param userToken
	 * @param contactId
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.main.action.count.get")
	public BaseOutput getPartyBehaviorCountById(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("contact_id") String contactId) {
		return mainActionInfoGetService.getPartyBehaviorCountById(contactId);
	}

	/**
	 * @功能简述: 获取系统标签总数量
	 * @param method
	 * @param userToken
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.tag.system.tagcount.get")
	public BaseOutput getTagcount(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken) {
		return tagSystemTagcountService.getTagcount(method, userToken);
	}

	/**
	 * @功能简述 : 获取自定义标签列表
	 * @param: String
	 *             method, String userToken, Integer index, Integer size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.tag.custom.list.get")
	public BaseOutput getCustomTagList(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @QueryParam("index") Integer index,
			@QueryParam("size") Integer size) {
		return customTagGetService.getCustomTagList(method, userToken, index, size);
	}

	/**
	 * @功能简述 : 删除某个自定义标签
	 * @param: String
	 *             method, String userToken, Integer tag_id
	 * @return: Object
	 */
	@POST
	@Path("mkt.tag.custom.delete")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput deleteCustomTag(@Valid CustomTagDeleteIn body, @Context SecurityContext securityContext) {
		return customTagDeleteService.deleteCustomTag(body);
	}

	/**
	 * @功能简述 : 根据自定义标签下载覆盖的人群
	 * @param: String
	 *             userToken, Integer tag_id
	 * @return: Object
	 */
	@GET
	@Path("mkt.tag.custom.audience.download")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput downloadCustomAudience(@NotEmpty @QueryParam("method") String method,
			@NotNull @QueryParam("tag_id") Integer tagId) {
		return tagDownloadCustomAudienceService.downloadCustomAudience(tagId);
	}

	/**
	 * @功能简述 : 下载非法数据
	 * @param: String
	 *             userToken, Integer batchId
	 * @return: Object
	 */
	@GET
	@Path("mkt.data.quality.illegaldata.download")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput downloadIllegalData(@NotEmpty @QueryParam("file_type") String fileType,
			@NotNull @QueryParam("batch_id") Long batchId) {
		return dataDownloadQualityIllegalDataService.downloadIllegalData(batchId, fileType);
	}

	/**
	 * @功能简述: 获取受众细分关联的tag
	 * @param userToken
	 * @param segmentHeadId
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.segment.tag.get")
	public BaseOutput getSegmentHeaderTag(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("segment_head_id") String segmentHeadId) {
		return segmentTagGetService.getSegmentTag(userToken, segmentHeadId);
	}

	/**
	 * @功能简述: 获取受众细分漏斗计算结果
	 * @param body
	 * @param securityContext
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.segment.filter.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput getSegmentFilterCount(@Valid SegmentFilterCountIn body,
			@Context SecurityContext securityContext) {
		return segmentFilterGetService.getSegmentFilterCount(body, securityContext);
	}

	@POST
	@Path("/mkt.segment.filter.sum.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput getSegmentFilterSum(@Valid SegmentFilterSumIn body, @Context SecurityContext securityContext) {
		return segmentFilterGetService.getSegmentFilterSum(body);
	}

	/**
	 * @功能简述: 打标签，增加或修改受众细分关联的tag
	 * @param: SegmentTagUpdateIn
	 *             body, SecurityContext securityContext
	 * @return: Object
	 */
	@POST
	@Path("/mkt.segment.tag.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput segmentBodyUpdate(@Valid SegmentTagUpdateIn body, @Context SecurityContext securityContext) {
		return segmentTagUpdateService.updateSegmentTag(body, securityContext);
	}

	/**
	 * @功能简述: 获取某联系人雷达图数据
	 * @param userToken
	 * @param contactId
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.main.radarinfo.get")
	public BaseOutput getRadarInfoByContactId(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("contact_id") String contactId) {
		return dataMainRadarInfoGetService.getRadarInfoByContactId(contactId);
	}

	/**
	 * @功能简述: 编辑某条主数据详细信息
	 * @param body
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.data.main.basicinfo.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput updateBaseInfoByContactId(@Valid DataMainBaseInfoUpdateIn body) {
		return dataMainBasicInfoUpdateService.updateBaseInfoByContactId(body);
	}

	/**
	 * 获取系统标签内容列表
	 * 
	 * @param method
	 * @param userToken
	 * @param tagGroupId
	 * @param index
	 * @param size
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.tag.system.list.get")
	public BaseOutput getTagcountByParentGroupId(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken,
			@NotNull @QueryParam("tag_group_id") Integer tagGroupId, @QueryParam("index") Integer index,
			@QueryParam("size") Integer size) {
		return tagSystemListGetService.getTagcount(method, userToken, tagGroupId, index, size);
	}

	/**
	 * 获取系统标签组列表
	 * 
	 * @param method
	 * @param userToken
	 * @param tagGroupId
	 * @param index
	 * @param size
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.taggroup.system.list.get")
	public BaseOutput getTagGroupByParentGroupId(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken,
			@NotNull @QueryParam("tag_group_id") Integer tagGroupId, @QueryParam("index") Integer index,
			@QueryParam("size") Integer size) {
		return taggroupSystemListGetService.getTagGroupByParentGroupId(method, userToken, tagGroupId, index, size);
	}

	/**
	 * @功能简述: 获取某条主数据详细信息
	 * @param userToken
	 * @param contactId
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.main.basicinfo.get")
	public BaseOutput getPartyBehaviorByCondition(@NotEmpty @QueryParam("user_token") String userToken,
			@NotNull @QueryParam("contact_id") Integer contactId, @NotNull @QueryParam("md_type") Integer dataType) {
		return mainBasicInfoGetService.getMainBasicInfo(contactId, dataType, userToken);
	}

	/**
	 * @功能简述: 获取系统标签组列表
	 * @param method
	 * @param userToken
	 * @param index
	 * @param size
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.taggroup.system.menulist.get")
	public BaseOutput getTaggroupSystemMenulist(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @QueryParam("index") Integer index,
			@QueryParam("size") Integer size) {
		return taggroupSystemMenulistGetService.getTaggroupSystemMenulist(method, userToken, index, size);
	}

	/**
	 * @功能简述: 主界面上的搜索栏模糊查询数据
	 * @param:String user_token,String
	 *                   ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.main.search.get")
	public BaseOutput getDataMainSearch(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("name") String name, @NotEmpty @QueryParam("ver") String ver) {
		DataMainSearchIn dataMainSearchIn = new DataMainSearchIn();
		dataMainSearchIn.setName(name);
		return getDataMainSearchService.searchDataMain(dataMainSearchIn);
	}

	/**
	 * @功能简述: 根据类型和编号，搜索获取主数据(细分、活动和人群)
	 * @param:String user_token,String
	 *                   ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.main.searchbyid.get")
	public BaseOutput getDataMainSearchById(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("type") Integer type,
			@NotNull @QueryParam("id") Integer id) {
		return getDataMainSearchByIdService.searchDataMainById(id, type);
	}

	/**
	 * @功能简述: 微信公众号授权
	 * @param:String user_token,String
	 *                   ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.inbound.wechat.public.auth")
	public BaseOutput authWechatPublicAccount(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver) {
		return wechatPublicAuthService.authWechatPublicAccount();
	}

	/**
	 * @功能简述: 微信公众号授权时大连那边所调用的回调接口
	 * @param wechatPublicAuthCallbackIn
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.data.inbound.wechat.public.auth.callback")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response wechatPublicAuthCallback(@Valid WechatPublicAuthCallbackIn wechatPublicAuthCallbackIn) {

		wechatPublicAuthCallbackService.authWechatPublicCallback(wechatPublicAuthCallbackIn);

		URI location = null;

		try {
			logger.info("redirect : before location!");
			location = new java.net.URI("http://mktpro.rc.dataengine.com/html/data-access/weixin.html");
			logger.info("redirect : after location!");
		} catch (URISyntaxException e) {
			logger.info("redirect : before exception");
			e.printStackTrace();
			logger.info("redirect : after exception");
		}

		return Response.temporaryRedirect(location).build();
	}

	/**
	 * @功能简述: 获取微信个人号授权时产生的uuid
	 * @param wechatPersonalAuthIn
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.data.inbound.wechat.personal.auth")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput wechatPersonalAuth(WechatPersonalAuthIn wechatPersonalAuthIn) {
		return wechatPersonalAuthService.authPersonWechat(wechatPersonalAuthIn);
	}

	/**
	 * @功能简述: 获取文件模板下载列表
	 * @param: String
	 *             userToken
	 * @return: Object
	 */
	@GET
	@Path("/mkt.data.inbound.file.template.download")
	public Object fileTemplateDownload(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("template_id_list") String templateIdList) {
		return fileTemplateDownloadService.downloadFileTemplate(templateIdList);
	}

	/**
	 * @功能简述: 获取微信人群的明细下载
	 * @param: String
	 *             userToken
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.wechat.people.detail.download")
	public Object downloadWechatPeopleDetail(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("group_ids") String group_ids) {
		return wechatPeopleDetailDownloadService.downloadWechatPeopleDetail(group_ids);
	}

	/**
	 * @功能简述: 文件上传时为上传的人群打标签
	 * @param fileTagUpdateIn
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.data.inbound.file.tag.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput fileTagUpdate(FileTagUpdateIn fileTagUpdateIn) {
		return fileTagUpdateService.updateFileTag(fileTagUpdateIn);
	}

	/**
	 * @功能简述: 下载某条主数据详细信息
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.quality.log.download")
	public Object downloadQualityLog(@NotEmpty @QueryParam("user_token") String userToken,
			@NotNull @QueryParam("import_data_id") Long importDataId) {
		return dataDownloadQualityLogService.downloadQualityLog(importDataId);
	}

	/**
	 * @功能简述: 下载某个主数据分类的数据
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.main.list.download")
	public Object downloadMainList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotNull @QueryParam("md_type") Integer dataType) {
		return dataDownloadMainListService.downloadMainList(userToken, dataType);
	}

	/**
	 * 搜索活动节点上的人
	 * 
	 * @param userToken
	 * @param ver
	 * @param name
	 * @param campaignHeadId
	 * @param itemId
	 * @return
	 */
	@GET
	@Path("/mkt.campaign.body.item.audience.search")
	public CampaignBodyItemAudienceSearchOut campaignBodyItemAudienceSearch(
			@NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver,
			@QueryParam("search_field") String name, @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId,
			@NotNull @QueryParam("item_id") String itemId) {
		return campaignBodyItemAudienceSearchService.campaignBodyItemAudienceSearch(name, campaignHeadId, itemId);
	}

	/**
	 * 查询微信小组中的人群
	 * 
	 * @param userToken
	 * @param ver
	 * @param groupIds
	 * @return
	 */
	@GET
	@Path("/mkt.asset.wechat.member.search")
	public BaseOutput wechatAssetMemberSearch(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("group_ids") String groupIds,
			@QueryParam("search_field") String searchField) {
		return wechatAssetMemberSearchService.searchWechatAssetMember(groupIds, searchField);
	}

	/**
	 * 查询活动转化图表的相关数据
	 * 
	 * @param userToken
	 * @param ver
	 * @param campaignHeadId
	 * @return
	 */
	@GET
	@Path("/mkt.campaign.conversion.list")
	public CampaignConvertChartListOut getCompaignConversionList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
		return getCampaignConvertChartListService.getCompaignConvertChartList(campaignHeadId);
	}

	/**
	 * 查询活动转化图表的相关数据
	 * 
	 * @param userToken
	 * @param ver
	 * @param campaignHeadId
	 * @return
	 */
	@GET
	@Path("/mkt.campaign.customer.source.list")
	public CampaignCustomSourceListOut getCampaignCustomerSourceList(
			@NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver,
			@NotNull @QueryParam("campaign_head_id") Integer campaignHeadId) {
		return getCampaignCustomerSourceListService.getCampaignCustomSourceInfo(campaignHeadId);
	}

	/**
	 * 返回当前订阅号，服务号，个人号按月累计的粉丝数量
	 * 
	 * @param userToken
	 * @param ver
	 * @return
	 */
	@GET
	@Path("/mkt.wechat.user.list")
	public WechatUserListOut getWechatUserList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver) {
		return getWechatUserListService.getWechatUserListByType();
	}

	/**
	 * 按月份按公众号统计每月的用户增加数量
	 *
	 * @param userToken
	 * @param ver
	 * @author nianjun
	 */
	@GET
	@Path("/mkt.homepage.usercount.list")
	public BaseOutput homePageUserCountList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		result.getData().add(homePageUserCountListService.getHomePageUserCountList());

		return result;
	}

	/**
	 * 统计出当前用户、细分、活动、标签、接入数据个数
	 *
	 * @param userToken
	 * @param ver
	 * @author nianjun
	 */
	@GET
	@Path("/mkt.homepage.datacount.list")
	public BaseOutput homePageDataCountList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		result.getData().add(homePageDataCountListService.getDataCountList());

		return result;
	}

	/**
	 * 统计出用户的各类来源，如微信、CRM、POS(以数据文件填写的来源为准)
	 *
	 * @param userToken
	 * @param ver
	 * @author nianjun
	 */
	@GET
	@Path("/mkt.homepage.datasource.list")
	public BaseOutput homePageDataSourceList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		result.getData().add(homePageDataSourceListService.getHomePageDataSourceList());

		return result;
	}

	/**
	 * 统计出当月日历日被客户标记当月定时的活动，按启动时间算
	 *
	 * @param userToken
	 * @param ver
	 * @author nianjun
	 */
	@GET
	@Path("/mkt.homepage.calendar.list")
	public BaseOutput homePageCalendarList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @QueryParam("date") String date) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		result.getData().add(homePageCalendarListService.getCalendarList(date));

		return result;
	}

	/**
	 * 当月日历日被客户标记当日定时的活动的弹窗
	 *
	 * @param userToken
	 * @param ver
	 * @author nianjun
	 */
	@GET
	@Path("/mkt.homepage.calendar.pop")
	public BaseOutput homePageCalendarPop(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("date") String date) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		result.getData().add(homePageCalendarPopService.getCalendarPop(date));

		return result;
	}

	@GET
	@Path("/mkt.campaign.analysis.list")
	public BaseOutput campaignAnalysisList() {
		BaseOutput out = new BaseOutput(ApiConstant.INT_ZERO, ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
		out.getData().add(campaignHeaderGetService.campaignAnalysisList(null, null, null, null));
		return out;
	}
	
	/**
	 * 获取微信接入渠道列表
	 *
	 * @param userToken
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.weixin.channel.list")
	public BaseOutput wechannelListGet(@NotEmpty @QueryParam("user_token") String userToken)
	{
		return wechatChannelListService.channelList();
	}

	/**
	 * 更新微信渠道详细信息
	 *
	 * @param
	 * @param ver
	 * @author zhaoguoying
	 */
	@POST
	@Path("/mkt.weixin.channel.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput wechannelUpdate(@Valid WechatChanellUpdateIn body, @Context SecurityContext securityContext)
	{
		return wechatChanellUpdateService.wechatChannelUpdate(body, securityContext);
	}
	
	@GET
	@Path("/mkt.weixin.channel.name.get")
	public BaseOutput wechannelListGet(@NotEmpty @QueryParam("user_token") String userToken,@NotEmpty @QueryParam("ch_name") String ch_name)
	{
		WechatChannel wechatChannel = new WechatChannel();
		wechatChannel.setChName(ch_name);
		return wechatChannelListService.chanelExitLike(wechatChannel);
	}
	
	/**
	 * 获取公众号列表
	 *
	 * @param userToken
	 * @param ver
	 * @author chengjincheng
	 */
	@GET
	@Path("/mkt.weixin.register.list")
	public BaseOutput registerListGet(@NotEmpty @QueryParam("user_token") String userToken) {

		return regListService.getRegisterList();
	}
	
	/**
	 * @功能简述 : 根据id更新标签信息
	 */
	@POST
	@Path("/mkt.weixin.tag.update")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput updateTagInfo(@Valid TagBodyUpdateIn body) {
		
		return tagService.tagInfoUpdate(body);
	}
	/***
	 * 新建联系人表单 
	 * @param body
	 * @param securityContext
	 * @return baseOutput
	 */
	@POST
	@Path("/mkt.contact.list.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput ContactListCreate(@Valid ContactTemplateIn body, @Context SecurityContext securityContext)
	{
		return contactTemplateServer.ContactListCreate(body);
	}
	
	/**
	 * 删除联系人表单 
	 *
	 * @param userToken
	 * @param ver
	 * @author yyl
	 */
	@GET
	@Path("/mkt.contact.list.del")
	public BaseOutput updateContextTempById(@NotEmpty @QueryParam("user_token") String userToken,@NotNull @QueryParam("id") int id)
	{
		return contactTemplateServer.updateContextTempById(id);
	}

}
