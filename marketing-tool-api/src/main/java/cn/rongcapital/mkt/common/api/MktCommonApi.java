/*************************************************
 * @功能简述: API接口通用主类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.common.api;

import java.net.URI;
import java.net.URISyntaxException;
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
import cn.rongcapital.mkt.service.AudienceIdListService;
import cn.rongcapital.mkt.service.AudienceListDeleteService;
import cn.rongcapital.mkt.service.AudienceListService;
import cn.rongcapital.mkt.service.AudienceNameListService;
import cn.rongcapital.mkt.service.AudienceSearchService;
import cn.rongcapital.mkt.service.CampaignBodyCreateService;
import cn.rongcapital.mkt.service.CampaignBodyGetService;
import cn.rongcapital.mkt.service.CampaignBodyItemAudienceSearchService;
import cn.rongcapital.mkt.service.CampaignDeleteService;
import cn.rongcapital.mkt.service.CampaignHeaderCreateService;
import cn.rongcapital.mkt.service.CampaignHeaderGetService;
import cn.rongcapital.mkt.service.CampaignHeaderUpdateService;
import cn.rongcapital.mkt.service.CampaignNodeItemListGetService;
import cn.rongcapital.mkt.service.CampaignProgressStatusCountService;
import cn.rongcapital.mkt.service.CampaignProgressStatusListService;
import cn.rongcapital.mkt.service.CampaignSummaryGetService;
import cn.rongcapital.mkt.service.CustomTagDeleteService;
import cn.rongcapital.mkt.service.CustomTagGetService;
import cn.rongcapital.mkt.service.DataDeleteMainService;
import cn.rongcapital.mkt.service.DataDownloadMainListService;
import cn.rongcapital.mkt.service.DataDownloadQualityIllegalDataService;
import cn.rongcapital.mkt.service.DataDownloadQualityLogService;
import cn.rongcapital.mkt.service.DataGetFilterAudiencesService;
import cn.rongcapital.mkt.service.DataGetFilterContactwayService;
import cn.rongcapital.mkt.service.DataGetFilterRecentTaskService;
import cn.rongcapital.mkt.service.DataGetMainCountService;
import cn.rongcapital.mkt.service.DataGetMainListService;
import cn.rongcapital.mkt.service.DataGetQualityCountService;
import cn.rongcapital.mkt.service.DataGetQualityListService;
import cn.rongcapital.mkt.service.DataGetUnqualifiedCountService;
import cn.rongcapital.mkt.service.DataGetViewListService;
import cn.rongcapital.mkt.service.DataMainBasicInfoUpdateService;
import cn.rongcapital.mkt.service.DataMainRadarInfoGetService;
import cn.rongcapital.mkt.service.DataUpateMainSegmenttagService;
import cn.rongcapital.mkt.service.DeleteImgTextAssetService;
import cn.rongcapital.mkt.service.FileTagUpdateService;
import cn.rongcapital.mkt.service.FileTemplateDownloadService;
import cn.rongcapital.mkt.service.GetCampaignConvertChartListService;
import cn.rongcapital.mkt.service.GetCampaignCustomerSourceListService;
import cn.rongcapital.mkt.service.GetDataMainSearchByIdService;
import cn.rongcapital.mkt.service.GetDataMainSearchService;
import cn.rongcapital.mkt.service.GetImgTextAssetService;
import cn.rongcapital.mkt.service.GetImgtextAssetMenulistService;
import cn.rongcapital.mkt.service.GetImgtextCountService;
import cn.rongcapital.mkt.service.GetWechatUserListService;
import cn.rongcapital.mkt.service.GroupTagsSearchService;
import cn.rongcapital.mkt.service.HomePageCalendarListService;
import cn.rongcapital.mkt.service.HomePageCalendarPopService;
import cn.rongcapital.mkt.service.HomePageDataCountListService;
import cn.rongcapital.mkt.service.HomePageDataSourceListService;
import cn.rongcapital.mkt.service.HomePageUserCountListService;
import cn.rongcapital.mkt.service.ImgtextHostService;
import cn.rongcapital.mkt.service.LoginService;
import cn.rongcapital.mkt.service.MainActionInfoGetService;
import cn.rongcapital.mkt.service.MainBasicInfoGetService;
import cn.rongcapital.mkt.service.MigrationFileGeneralInfoService;
import cn.rongcapital.mkt.service.MigrationFileTemplateService;
import cn.rongcapital.mkt.service.MigrationFileUploadUrlService;
import cn.rongcapital.mkt.service.ModifyPasswdService;
import cn.rongcapital.mkt.service.ReauthWechatAccountService;
import cn.rongcapital.mkt.service.RegisterListService;
import cn.rongcapital.mkt.service.SaveCampaignAudienceService;
import cn.rongcapital.mkt.service.SaveWechatAssetListService;
import cn.rongcapital.mkt.service.SegmentBodyGetService;
import cn.rongcapital.mkt.service.SegmentBodyUpdateService;
import cn.rongcapital.mkt.service.SegmentFilterGetService;
import cn.rongcapital.mkt.service.SegmentHeaderCreateService;
import cn.rongcapital.mkt.service.SegmentHeaderGetService;
import cn.rongcapital.mkt.service.SegmentHeaderUpdateService;
import cn.rongcapital.mkt.service.SegmentPublishStatusCountService;
import cn.rongcapital.mkt.service.SegmentPublishstatusListService;
import cn.rongcapital.mkt.service.SegmentTagGetService;
import cn.rongcapital.mkt.service.SegmentTagUpdateService;
import cn.rongcapital.mkt.service.SegmentTagkeyTagListService;
import cn.rongcapital.mkt.service.SegmentTagnameTagCountService;
import cn.rongcapital.mkt.service.SegmentTagnameTagListService;
import cn.rongcapital.mkt.service.SegmentTagnameTagValueService;
import cn.rongcapital.mkt.service.TagDownloadCustomAudienceService;
import cn.rongcapital.mkt.service.TagGetCustomService;
import cn.rongcapital.mkt.service.TagSystemListGetService;
import cn.rongcapital.mkt.service.TagSystemTagcountService;
import cn.rongcapital.mkt.service.TaggroupSystemListGetService;
import cn.rongcapital.mkt.service.TaggroupSystemMenulistGetService;
import cn.rongcapital.mkt.service.TaskGetListService;
import cn.rongcapital.mkt.service.TaskListGetService;
import cn.rongcapital.mkt.service.UpdateNicknameService;
import cn.rongcapital.mkt.service.UploadFileService;
import cn.rongcapital.mkt.service.WechatAssetListGetService;
import cn.rongcapital.mkt.service.WechatAssetListService;
import cn.rongcapital.mkt.service.WechatAssetMemberSearchService;
import cn.rongcapital.mkt.service.WechatChanellUpdateService;
import cn.rongcapital.mkt.service.WechatChannelListService;
import cn.rongcapital.mkt.service.WechatPeopleDetailDownloadService;
import cn.rongcapital.mkt.service.WechatPersonalAuthService;
import cn.rongcapital.mkt.service.WechatPublicAuthCallbackService;
import cn.rongcapital.mkt.service.WechatPublicAuthService;
import cn.rongcapital.mkt.service.WechatTypeCountGetService;
import cn.rongcapital.mkt.vo.BaseInput;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgAsset;
import cn.rongcapital.mkt.vo.ImgtextHostIn;
import cn.rongcapital.mkt.vo.LoginInput;
import cn.rongcapital.mkt.vo.ModifyInput;
import cn.rongcapital.mkt.vo.SaveWechatAssetListIn;
import cn.rongcapital.mkt.vo.UpdateNicknameIn;
import cn.rongcapital.mkt.vo.in.Audience;
import cn.rongcapital.mkt.vo.in.AudienceListDeleteIn;
import cn.rongcapital.mkt.vo.in.CampaignBodyCreateIn;
import cn.rongcapital.mkt.vo.in.CampaignDeleteIn;
import cn.rongcapital.mkt.vo.in.CampaignHeadCreateIn;
import cn.rongcapital.mkt.vo.in.CampaignHeadUpdateIn;
import cn.rongcapital.mkt.vo.in.CustomTagDeleteIn;
import cn.rongcapital.mkt.vo.in.DataGetFilterAudiencesIn;
import cn.rongcapital.mkt.vo.in.DataMainBaseInfoUpdateIn;
import cn.rongcapital.mkt.vo.in.DataMainSearchIn;
import cn.rongcapital.mkt.vo.in.DataUpdateMainSegmenttagIn;
import cn.rongcapital.mkt.vo.in.FileTagUpdateIn;
import cn.rongcapital.mkt.vo.in.SegmentBodyUpdateIn;
import cn.rongcapital.mkt.vo.in.SegmentCountFilterIn;
import cn.rongcapital.mkt.vo.in.SegmentFilterCountIn;
import cn.rongcapital.mkt.vo.in.SegmentFilterSumIn;
import cn.rongcapital.mkt.vo.in.SegmentHeadCreateIn;
import cn.rongcapital.mkt.vo.in.SegmentHeadDeleteIn;
import cn.rongcapital.mkt.vo.in.SegmentHeadUpdateIn;
import cn.rongcapital.mkt.vo.in.SegmentTagUpdateIn;
import cn.rongcapital.mkt.vo.in.WechatChanellUpdateIn;
import cn.rongcapital.mkt.vo.in.WechatPersonalAuthIn;
import cn.rongcapital.mkt.vo.in.WechatPublicAuthCallbackIn;
import cn.rongcapital.mkt.vo.out.CampaignBodyCreateOut;
import cn.rongcapital.mkt.vo.out.CampaignBodyGetOut;
import cn.rongcapital.mkt.vo.out.CampaignBodyItemAudienceSearchOut;
import cn.rongcapital.mkt.vo.out.CampaignConvertChartListOut;
import cn.rongcapital.mkt.vo.out.CampaignCustomSourceListOut;
import cn.rongcapital.mkt.vo.out.CampaignHeaderGetOut;
import cn.rongcapital.mkt.vo.out.CampaignNodeItemListOut;
import cn.rongcapital.mkt.vo.out.CampaignProfileOut;
import cn.rongcapital.mkt.vo.out.CampaignProgressStatusCountOut;
import cn.rongcapital.mkt.vo.out.DataGetFilterContactwayOut;
import cn.rongcapital.mkt.vo.out.DataGetFilterRecentTaskOut;
import cn.rongcapital.mkt.vo.out.SegmentPublishstatusListOut;
import cn.rongcapital.mkt.vo.out.SerarchTagGroupTagsOut;
import cn.rongcapital.mkt.vo.out.WechatUserListOut;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktCommonApi {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RegisterListService regListService;

	@Autowired
	private WechatChannelListService wechatChannelListService;

	@Autowired
	private WechatChanellUpdateService wechatChanellUpdateService;

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
	 * 获取微信接入渠道列表
	 *
	 * @param userToken
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.weixin.channel.list")
	public BaseOutput wechannelListGet(@NotEmpty @QueryParam("user_token") String userToken) {
		return wechatChannelListService.channelList();
	}

	/**
	 * 获取微信接入渠道列表
	 *
	 * @param userToken
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.weixin.channel.name.get")
	public BaseOutput wechannelListGet(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ch_name") String ch_name) {
		WechatChannel wechatChannel = new WechatChannel();
		wechatChannel.setChName(ch_name);
		return wechatChannelListService.chanelExitLike(wechatChannel);
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
	public BaseOutput wechannelUpdate(@Valid WechatChanellUpdateIn body, @Context SecurityContext securityContext) {
		return wechatChanellUpdateService.wechatChannelUpdate(body, securityContext);
	}
}
