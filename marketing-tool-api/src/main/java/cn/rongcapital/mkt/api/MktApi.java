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
	private SegmentBodyUpdateService segmentBodyUpdateService;

	@Autowired
	private GetImgtextAssetMenulistService getImgtextAssetMenulistService;

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
	private TaskGetListService taskGetListService;

	@Autowired
	private TagDownloadCustomAudienceService tagDownloadCustomAudienceService;

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


}
