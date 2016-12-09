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
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.BaseInput;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgAsset;
import cn.rongcapital.mkt.vo.ImgtextHostIn;
import cn.rongcapital.mkt.vo.LoginInput;
import cn.rongcapital.mkt.vo.ModifyInput;
import cn.rongcapital.mkt.vo.in.Audience;
import cn.rongcapital.mkt.vo.in.CampaignBodyCreateIn;
import cn.rongcapital.mkt.vo.in.CampaignDeleteIn;
import cn.rongcapital.mkt.vo.in.CampaignHeadCreateIn;
import cn.rongcapital.mkt.vo.in.CampaignHeadUpdateIn;
import cn.rongcapital.mkt.vo.in.CustomTagDeleteIn;
import cn.rongcapital.mkt.vo.in.SegmentBodyUpdateIn;
import cn.rongcapital.mkt.vo.in.SegmentCountFilterIn;
import cn.rongcapital.mkt.vo.in.SegmentCreUpdateIn;
import cn.rongcapital.mkt.vo.in.SegmentFilterSumIn;
import cn.rongcapital.mkt.vo.in.SegmentHeadCreateIn;
import cn.rongcapital.mkt.vo.in.SegmentHeadDeleteIn;
import cn.rongcapital.mkt.vo.in.SegmentHeadUpdateIn;
import cn.rongcapital.mkt.vo.in.SegmentTagUpdateIn;
import cn.rongcapital.mkt.vo.in.TagGroupsListIn;
import cn.rongcapital.mkt.vo.in.TagListSecondarySearchIn;
import cn.rongcapital.mkt.vo.in.TagSystemFlagSetIn;
import cn.rongcapital.mkt.vo.out.CampaignBodyCreateOut;
import cn.rongcapital.mkt.vo.out.CampaignBodyGetOut;
import cn.rongcapital.mkt.vo.out.CampaignBodyItemAudienceSearchOut;
import cn.rongcapital.mkt.vo.out.CampaignConvertChartListOut;
import cn.rongcapital.mkt.vo.out.CampaignCustomSourceListOut;
import cn.rongcapital.mkt.vo.out.CampaignHeaderGetOut;
import cn.rongcapital.mkt.vo.out.CampaignNodeItemListOut;
import cn.rongcapital.mkt.vo.out.CampaignProfileOut;
import cn.rongcapital.mkt.vo.out.CampaignProgressStatusCountOut;
import cn.rongcapital.mkt.vo.out.SegmentPublishstatusListOut;
import cn.rongcapital.mkt.vo.out.SerarchTagGroupTagsOut;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
@PropertySource("classpath:${conf.dir}/application-api.properties")
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
	private DeleteImgTextAssetService deleteImgTextAssetService;

	@Autowired
	private GetImgTextAssetService getImgTextAssetService;

	@Autowired
	private ImgtextHostService imgtextHostService;

	@Autowired
	private SegmentHeaderGetService segmentHeaderGetService;

	@Autowired
	private SaveCampaignAudienceService saveCampaignAudienceService;

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
	private TagSystemTagcountService tagSystemTagcountService;

	@Autowired
	private SegmentBodyGetService segmentBodyGetService;

	@Autowired
	private CustomTagGetService customTagGetService;

	@Autowired
	private CustomTagDeleteService customTagDeleteService;

	@Autowired
	private GetImgtextCountService getImgtextCountService;

	@Autowired
	private TagSystemListGetService tagSystemListGetService;

	@Autowired
	private CampaignNodeItemListGetService campaignNodeItemListGetService;

	@Autowired
	private CampaignHeaderCreateService campaignHeaderCreateService;

	@Autowired
	private CampaignHeaderUpdateService campaignHeaderUpdateService;

	@Autowired
	private GroupTagsSearchService groupTagsSearchService;

	@Autowired
	private TaskGetListService taskGetListService;

	@Autowired
	private CampaignBodyItemAudienceSearchService campaignBodyItemAudienceSearchService;

	@Autowired
	private TagDownloadCustomAudienceService tagDownloadCustomAudienceService;

	@Autowired
	private GetCampaignConvertChartListService getCampaignConvertChartListService;

	@Autowired
	private GetCampaignCustomerSourceListService getCampaignCustomerSourceListService;

	@Autowired
	private HomePageCalendarListService homePageCalendarListService;

	@Autowired
	private HomePageCalendarPopService homePageCalendarPopService;

	@Autowired
	private SegmentDetailGetService segmentDetailGetService;

	@Autowired
	private SegmentSecondaryTaglistSearchService segmentSecondaryTaglistSearchService;

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
	 * @功能描述:查询系统推荐标签列表
	 * @Param: String method, String userToken
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.segment.tagname.taglist.get")
	public BaseOutput getSysRecommendedTagList(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken) {
		// segmentTagnameTagListService.getSysRecommendedTagList();
		return segmentTagnameTagListService.getMongoTagRecommendList(method, userToken);
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
	 * @功能描述:查询细分的详细信息
	 * @Param: String method, String userToken
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.segment.detail.get")
	public BaseOutput getSegmentDetail(@NotEmpty @QueryParam("method") String method,
									   @NotEmpty @QueryParam("user_token") String userToken,
	                                   @NotEmpty @QueryParam("ver") String ver,
	                                   @NotNull  @QueryParam("id") Long id) {
		return segmentDetailGetService.getSegmentDetail(id);
	}

	/**
	 * @功能简述: 模糊搜索受众细分标签值的列表(二级搜索)
	 * @param:
	 * @return: Object
	 */
	@POST
	@Path("/mkt.segment.secondary.taglist.search")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput searchSegmentSecondaryTaglist(TagListSecondarySearchIn tagListSecondarySearchIn, @Context SecurityContext securityContext) {
		return segmentSecondaryTaglistSearchService.searchSegmentSecondaryTaglist(tagListSecondarySearchIn);
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
     * @功能简述: 检查后台任务列表的状态
     * @author lihaiguang
     * @param:
     * @return: Object
     */
	@GET
    @Path("/mkt.task.list.check")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput taskListCheck(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        return taskGetListService.checkTaskList();
    }
	
	/**
     * @功能简述: 更改后台任务列表的状态把未查看的全部更新为已查看
     * @author lihaiguang
     * @param:
     * @return: Object
     */
	@GET
    @Path("/mkt.task.list.check.update")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput taskListCheckUpdate(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        return taskGetListService.updateTaskListStatus();
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
		//segmentTagkeyTagListService.getLastTagByKey(tagGroupName);
		return segmentTagkeyTagListService.getMongoTagRecommendByLike(tagGroupName);
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
		//segmentTagnameTagValueService.getTagValueByGroupId(tagGroupId);
		return segmentTagnameTagValueService.getMongoTagValueByTagId(tagGroupId);
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
	 * @功能简述: 获取系统标签总数量
	 * @param method
	 * @param userToken
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.tag.system.tagcount.get")
	public BaseOutput getTagcount(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken) {
		//tagSystemTagcountService.getTagcount(method, userToken);
		return tagSystemTagcountService.getMonggTagcount(method, userToken);
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
			@NotNull @QueryParam("tag_id") String tagId) {
		return tagDownloadCustomAudienceService.downloadCustomAudience(tagId);
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
			@NotNull @QueryParam("tag_group_id") String tagGroupId, @QueryParam("index") Integer index,
			@QueryParam("size") Integer size) {
		//tagSystemListGetService.getTagcount(method, userToken, tagGroupId, index, size);
		return tagSystemListGetService.getMongoTagList(method, userToken, tagGroupId, index, size);
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
	 * 统计出当月日历日被客户标记当月定时的活动，按启动时间算
	 *
	 * @param userToken
	 * @param ver
	 * @author nianjun
	 */
	@GET
	@Path("/mkt.homepage.calendar.list")
	public BaseOutput homePageCalendarList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver,@NotEmpty @QueryParam("date") String date) {
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
	

}
