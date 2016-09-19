/*************************************************
 * @功能简述: API接口通用主类
 * @项目名称: marketing cloud
 * @see:
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人:
*************************************************/

package cn.rongcapital.mkt.wechat.api;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.msg.WxMsgHandler;

import cn.rongcapital.mkt.biz.ImgTextAssetBiz;
import cn.rongcapital.mkt.biz.MessageSendBiz;
import cn.rongcapital.mkt.biz.WechatGroupBiz;
import cn.rongcapital.mkt.biz.WechatMemberBiz;
import cn.rongcapital.mkt.biz.WechatQrcodeBiz;
import cn.rongcapital.mkt.biz.WechatRegisterBiz;
import cn.rongcapital.mkt.biz.impl.BaseBiz;
import cn.rongcapital.mkt.biz.impl.ProcessReceiveMessageOfWeiXinImpl;
//import cn.rongcapital.mkt.biz.impl.ProcessReceiveMessageOfWeiXin;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.WebchatAuthInfo;
import cn.rongcapital.mkt.service.AssetWechatAudiencelistMatchGetService;
import cn.rongcapital.mkt.service.GetWeixinAnalysisDateService;
import cn.rongcapital.mkt.service.GetWxImgTextAssetService;
import cn.rongcapital.mkt.service.QrcodeCreateCountService;
import cn.rongcapital.mkt.service.QrcodePicDownloadService;
import cn.rongcapital.mkt.service.QrcodePicsZipDownloadService;
import cn.rongcapital.mkt.service.QrcodeUsedCountService;
import cn.rongcapital.mkt.service.TagUpdateService;
import cn.rongcapital.mkt.service.UploadFileService;
import cn.rongcapital.mkt.service.WebchatComponentVerifyTicketService;
import cn.rongcapital.mkt.service.WechatAnalysisDaysListService;
import cn.rongcapital.mkt.service.WechatAssetListService;
import cn.rongcapital.mkt.service.WechatQrcodeActivateService;
import cn.rongcapital.mkt.service.WeixinAnalysisChdataListService;
import cn.rongcapital.mkt.service.WeixinAnalysisChdataSummaryService;
import cn.rongcapital.mkt.service.WeixinAnalysisQrcodeScanService;
import cn.rongcapital.mkt.service.WeixinQrcodeBatchSaveService;
import cn.rongcapital.mkt.service.WeixinQrcodeDelService;
import cn.rongcapital.mkt.service.WeixinQrcodeErrorDownloadService;
import cn.rongcapital.mkt.service.WeixinQrcodeInfoService;
import cn.rongcapital.mkt.service.WeixinQrcodeListService;
import cn.rongcapital.mkt.service.WeixinQrcodeMatchGetService;
import cn.rongcapital.mkt.service.WeixinQrcodeSaveOrUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgAsset;
import cn.rongcapital.mkt.vo.in.ComponentVerifyTicketIn;
import cn.rongcapital.mkt.vo.in.TagBodyUpdateIn;
import cn.rongcapital.mkt.vo.in.WechatQrcodeBatchSaveIn;
import cn.rongcapital.mkt.vo.in.WechatQrcodeIn;
import cn.rongcapital.mkt.vo.in.WechatQrcodeInData;
import cn.rongcapital.mkt.vo.in.WechatQrcodeInId;
import cn.rongcapital.mkt.vo.in.WechatQrcodeScanIn;
//import cn.rongcapital.mkt.vo.weixin.SubscribeVO;
import cn.rongcapital.mkt.vo.weixin.SubscribeVO;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktWeChatApi {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WeixinQrcodeListService weixinQrcodeListService;

	@Autowired
	private TagUpdateService tagService;

	@Autowired
	private QrcodeCreateCountService qrcodeCreateCountService;

	@Autowired
	private QrcodePicDownloadService qrcodePicDownloadService;

	@Autowired
	private QrcodePicsZipDownloadService qrcodePicsZipDownloadService;

	@Autowired
	private QrcodeUsedCountService qrcodeUsedCountService;

	@Autowired
	private WeixinQrcodeInfoService weixinQrcodeInfoService;

	@Autowired
	private WeixinQrcodeDelService weixinQrcodeDelService;

	@Autowired
	private WeixinQrcodeMatchGetService weixinQrcodeMatchGetService;

	@Autowired
	private AssetWechatAudiencelistMatchGetService assetWechatAudiencelistMatchGetService;

	@Autowired
	private WeixinQrcodeErrorDownloadService weixinQrcodeErrorDownloadService;

	@Autowired
	private WeixinQrcodeBatchSaveService weixinQrcodeBatchSaveService;

	@Autowired
	private WeixinQrcodeSaveOrUpdateService weixinQrcodeSaveOrUpdateService;

	@Autowired
	private WechatQrcodeActivateService wechatQrcodeActivateService;

	@Autowired
	private UploadFileService uploadFileService;

	@Autowired
	private GetWeixinAnalysisDateService getWeixinAnalysisDateService;

	@Autowired
	WeixinAnalysisQrcodeScanService weixinAnalysisQrcodeScanService;

	@Autowired
	private WechatAnalysisDaysListService analysisDaysList;

	@Autowired
	private WeixinAnalysisChdataListService weixinAnalysisChdataListService;

	@Autowired
	private WechatQrcodeBiz wechatQrcodeBiz;

	@Autowired
	private WebchatComponentVerifyTicketService webchatComponentVerifyTicketService;

	@Autowired
	private BaseBiz baseBiz;

	@Autowired
	private WechatRegisterBiz wechatRegisterBiz;

	@Autowired
	private WechatMemberBiz wechatMemberBiz;

	@Autowired
	private WechatGroupBiz wechatGroupBiz;

	@Autowired
	private ImgTextAssetBiz imgTextAssetBiz;

	@Autowired
	private MessageSendBiz messageSendBiz;

	@Autowired
	WeixinAnalysisChdataSummaryService weixinAnalysisChdataSummaryService;

	@Autowired
	private WechatAssetListService wechatAssetListService;

	@Autowired
	private GetWxImgTextAssetService etWxImgTextAssetService;

	/**
	 * 根据公众号名称、失效时间、状态、二维码名称查询二维码列表
	 *
	 * @param userToken
	 * @param ver
	 * @param wxmpName
	 * @param expirationTime
	 * @param qrcodeStatus
	 * @return
	 * @author shuiyangyang
	 */
	@GET
	@Path("mkt.weixin.qrcode.list")
	public BaseOutput getWeixinQrcodeList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @QueryParam("wxmp_name") String wxmpName,
			@QueryParam("expiration_time") Integer expirationTime,
			@DefaultValue("0") @QueryParam("qrcode_status") Byte qrcodeStatus,
			@DefaultValue("1") @Min(1) @QueryParam("index") int index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") int size) {

		return weixinQrcodeListService.getWeixinQrcodeList(wxmpName, expirationTime, qrcodeStatus, index, size);
	}

	/**
	 *
	 * 根据输入的二维码名称模糊查询表wechat_qrcode
	 *
	 * @param userToken
	 * @param ver
	 * @param qrcodeName
	 * @return
	 * @author shuiyangyang
	 */
	@GET
	@Path("mkt.weixin.qrcode.list.qrname")
	public BaseOutput getWeixinQrcodeListQrname(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @QueryParam("qrcode_name") String qrcodeName,
			@DefaultValue("1") @Min(1) @QueryParam("index") int index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") int size) {

		return weixinQrcodeListService.getWeixinQrcodeListQrname(qrcodeName, index, size);

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

	/**
	 * 批量生成二维码时，统计成功和失败个数
	 *
	 * @param
	 * @param ver
	 * @author chengjincheng
	 */
	@GET
	@Path("/mkt.weixin.qrcode.create.count")
	public BaseOutput getCreateCount(@NotNull @QueryParam("batch_id") String batch_id) {
		return qrcodeCreateCountService.getCreateCount(batch_id);
	}

	/**
	 * 下载单个微信二维码图片文件
	 *
	 * @param
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.weixin.qrcode.pic.download")
	public BaseOutput getQrcodePicDownload(@NotEmpty @QueryParam("user_token") String user_token,
			@NotNull @QueryParam("qrcode_id") int qrcode_id) {
		return qrcodePicDownloadService.getQrcodePicDownload(qrcode_id);
	}

	/**
	 * 下载批量生成的二维码图片文件(zip)
	 *
	 * @param
	 * @param ver
	 * @author zhaoguoying
	 */
	@GET
	@Path("/mkt.weixin.qrcode.pics.zip.download")
	public BaseOutput getQrcodePicsZipDownload(@NotEmpty @QueryParam("user_token") String user_token,
			@NotNull @QueryParam("batch_id") String batch_id) {
		return qrcodePicsZipDownloadService.getQrcodePicsZipDownload(batch_id);
	}

	/**
	 * 统计公众号已用二维码个数
	 *
	 * @param
	 * @param ver
	 * @author chengjincheng
	 */
	@GET
	@Path("/mkt.weixin.qrcode.used.count")
	public BaseOutput getListCount(@NotEmpty @QueryParam("wx_name") String wx_name) {
		return qrcodeUsedCountService.getListCount(wx_name);
	}

	/**
	 *
	 * 查询二维码详细信息
	 *
	 * @param qrcodeId
	 * @return
	 * @author shuiyangyang
	 * @Data 2016.08.24
	 */
	@GET
	@Path("/mkt.weixin.qrcode.info")
	public BaseOutput getWeiXinQrocdeInfo(@NotEmpty @QueryParam("qrcode_id") String qrcodeId) {
		return weixinQrcodeInfoService.getWeiXinQrocdeInfo(qrcodeId);
	}

	/**
	 * 删除二维码接口 （逻辑删除，状态改为2）
	 *
	 * @param id
	 * @return
	 * @author shuiyangyang
	 * @Data 2016.08.25
	 */
	@POST
	@Path("/mkt.weixin.qrcode.del")
	public BaseOutput weixinQrocdeDel(@Valid WechatQrcodeInId body) {
		return weixinQrcodeDelService.weixinQrocdeDel(body);
	}

	/**
	 * 精确查询微信二维码名称是否存在
	 *
	 * @param qrcode_name
	 * @param wxName
	 * @return
	 * @author shuiyangyang
	 * @Data 2016.08.25
	 */

	@GET
	@Path("/mkt.weixin.qrcode.match.get")
	public BaseOutput weixinQrcodeMatchGet(@NotEmpty @QueryParam("qrcode_name") String qrcodeName,
			@NotEmpty @QueryParam("wx_name") String wxName) {
		return weixinQrcodeMatchGetService.weixinQrcodeMatchGet(qrcodeName, wxName);
	}

	/**
	 * 删除二维码接口 （微信记录物理删除）
	 *
	 * @param id
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	@POST
	@Path("/mkt.weixin.qrcode.records.del")
	public BaseOutput weixinQrcodeRecordsDel(@Valid WechatQrcodeInId body) {
		return weixinQrcodeDelService.weixinQrcodeRecordsDel(body);
	}

	/**
	 * 精确查询固定人群是否存在
	 *
	 * @param audienceName
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	@GET
	@Path("/mkt.asset.wechat.audiencelist.match.get")
	public BaseOutput assetWechatAudiencelistMatchGet(@NotEmpty @QueryParam("audience_name") String audienceName) {
		return assetWechatAudiencelistMatchGetService.assetWechatAudiencelistMatchGet(audienceName);
	}

	/**
	 * 生效微信二维码
	 *
	 * @param id
	 * @return
	 * @author zhouqi
	 * @Date 2016.08.29
	 */
	@POST
	@Path("/mkt.weixin.qrcode.activate")
	public BaseOutput wechatQrcodeActivate(@NotNull @QueryParam("id") Integer id) {
		return wechatQrcodeActivateService.weChatQrocdeActivate(id);
	}

	/**
	 * 下载导入失败的数据
	 *
	 *
	 * @param batchId
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.26
	 */
	@GET
	@Path("/mkt.weixin.qrcode.error.download")
	public BaseOutput weixinQrcodeErrorDownload(@NotNull @QueryParam("batch_id") String batchId) {
		return weixinQrcodeErrorDownloadService.weixinQrcodeErrorDownload(batchId);
	}

	/**
	 *
	 * 根据batch_id，在表wechat_qrcode 中查找到对应二维码记录，更新：失效时间、标签、备注信息、状态(status=0)
	 *
	 * @param batchId
	 * @param expirationTime
	 * @param qrcodeTagIds
	 * @param qrcodeStatus
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.26
	 */
	/*@GET
	@Path("/mkt.weixin.qrcode.batch.save")
	public BaseOutput weixinQrcodeBatchSave(@NotNull @QueryParam("batch_id") String batchId,
			@NotNull @QueryParam("expiration_time") String expirationTime,
			@NotEmpty @QueryParam("qrcode_tag_ids") String qrcodeTagIds,
			@NotNull @QueryParam("qrcode_status") Integer qrcodeStatus) {
		return weixinQrcodeBatchSaveService.weixinQrcodeBatchSave(batchId, expirationTime, qrcodeTagIds, qrcodeStatus);
	}*/

	@POST
	@Path("/mkt.weixin.qrcode.batch.save")
	public BaseOutput weixinQrcodeBatchSave(@Valid WechatQrcodeBatchSaveIn body){
		return weixinQrcodeBatchSaveService.weixinQrcodeBatchSave(body);
	}

	@POST
	@Path("/mkt.weixin.qrcode.saveorupdate")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput weixinSaveOrUpdate(@Valid WechatQrcodeInData body) {
		return weixinQrcodeSaveOrUpdateService.weixinSaveOrUpdate(body);
	}

	/**
	 * 获取微信Analysis选择的时间
	 */
	@GET
	@Path("/mkt.weixin.analysis.date")
	public BaseOutput weixinQrcodeBatchSave(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver,
	        @QueryParam("qrcode_id") String qrcodeId) {
		return getWeixinAnalysisDateService.getWeixinAnalysisDate(qrcodeId);
	}

	/**
	 * 保存扫描微信二维码次数和人数 接口：mkt.weixin.analysis.qrcode.scan
	 *
	 * @param userId
	 * @param userHost
	 * @param qrcodeId
	 * @return
	 * @author shuiyangyang
	 * @date 2016.09.01
	 */
	@POST
	@Path("/mkt.weixin.analysis.qrcode.scan")
	public BaseOutput instertToWechatQrcodeScan(@Valid WechatQrcodeScanIn body) {
		return weixinAnalysisQrcodeScanService.instertToWechatQrcodeScan(body);
	}

	/**
	 * @Title: analysisDaysList
	 * @Description: 微信二维码关注数据分析
	 * @param: @param startDate
	 * @param: @param endDate
	 * @param: @param daysType
	 * @param: @param chCode
	 * @param: @param wxName
	 * @param: @return
	 * @return: BaseOutput
	 * @throws
	 */
	@GET
	@Path("mkt.weixin.analysis.days.list")
	public BaseOutput analysisDaysList(@NotEmpty @QueryParam("start_date")String startDate,
			@NotEmpty @QueryParam("end_date")String endDate,@NotEmpty @QueryParam("ch_code")String chCode,
			@NotEmpty @QueryParam("wx_name")String wxName,@QueryParam("days_type") String daysType) {
		return analysisDaysList.analysisDaysList(startDate, endDate, daysType, chCode, wxName);
	}

	/**
	 *  按公众号和渠道,以及时间区间内获取关注数据(扫码、关注、新增...), 支持分页
	 * @param wxName
	 * @param chCode
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author shuiyangyang
	 * @date 2016.09.01
	 */
	@GET
	@Path("/mkt.weixin.analysis.chdata.list")
	public BaseOutput getAnalysisChdata(
			@NotNull @QueryParam("wx_name") String wxName,
			@NotEmpty @QueryParam("ch_code") String chCode,
			@NotEmpty @QueryParam("start_date") String startDate,
			@NotEmpty @QueryParam("end_date") String endDate) {
		return weixinAnalysisChdataListService.getAnalysisChdata(wxName, chCode, startDate, endDate);
	}

	/**
	 * @功能简述 : 根据id更新标签信息
	 */
	@POST
	@Path("/mkt.weixin.qrcode.getWechatQrcodeTicket")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput getWechatQrcodeTicket() {
		BaseOutput baseOutput = wechatQrcodeBiz.getWechatQrcodeTicket();
		return baseOutput;
	}

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @功能简述 : 根据id更新标签信息
	 */
	@POST
	@Path("/mkt.weixin.qrcode.pics.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput getQrcodes(@NotEmpty @QueryParam("action_name") String actionName,@DefaultValue("1") @Min(1) @Max(100000) @QueryParam("start_scene_id") int startSceneId,@DefaultValue("100000") @Min(1) @Max(100000) @QueryParam("end_scene_id") int endSceneId) throws FileNotFoundException, IOException {
		BaseOutput baseOutput = wechatQrcodeBiz.getQrcodes(startSceneId, endSceneId, actionName);
		return baseOutput;
	}

	@POST
	@Path("/mkt.weixin.qrcode.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput createQrcode(@Valid WechatQrcodeIn body) {
		BaseOutput baseOutput = wechatQrcodeBiz.createQrcode(body);
		return baseOutput;
	}

	@POST
	@Path("/mkt.weixin.qrcode.enable")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput enableQrcode(@Valid WechatQrcodeIn body) {
		BaseOutput baseOutput = wechatQrcodeBiz.enableQrcode(body);
		return baseOutput;
	}
	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @功能简述 : 根据id更新标签信息
	 */
	@POST
	@Path("/mkt.weixin.qrcode.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput getQrcode(@NotEmpty @QueryParam("action_name") String actionName,@DefaultValue("10") @Min(1) @Max(100000) @QueryParam("scene_id") int sceneId) throws FileNotFoundException, IOException {
		BaseOutput baseOutput = wechatQrcodeBiz.getQrcode(sceneId,actionName);
		return baseOutput;
	}


	/**
	 * @param componentVerifyTicketIn
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * 测试
	 * @return
	 */
	@POST
	@Path("/mkt.weixin.qrcode.getComponentVerifyTicket")
	@Consumes({MediaType.TEXT_XML})
	public String getComponentVerifyTicket( ComponentVerifyTicketIn componentVerifyTicketIn,@QueryParam("msg_signature") String msg_signature,@QueryParam("timestamp") String timestamp, @QueryParam("nonce") String nonce){
		webchatComponentVerifyTicketService.insert(componentVerifyTicketIn,msg_signature, timestamp, nonce);
		return "success";
	}

	/**
	 * @Title: analysisHoursList
	 * @Description: 按小时查询关注数据
	 * @param: @param date
	 * @param: @param chCode
	 * @param: @param wxName
	 * @param: @return
	 * @return: BaseOutput
	 * @throws
	 */
	@GET
	@Path("mkt.weixin.analysis.hours.list")
	public BaseOutput analysisHoursList(@NotEmpty @QueryParam("date")String date,@NotEmpty @QueryParam("ch_code")String chCode,@NotEmpty @QueryParam("wx_name")String wxName) {
		return analysisDaysList.analysisHoursList(date, chCode, wxName);
	}

	/**
	 * 获取平均、汇总、历史最高关注数据(扫码、关注、新增...)
	 * @param wxName
	 * @param chCode
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author shuiyangyang
	 */
	@GET
	@Path("/mkt.weixin.analysis.chdata.summary")
	public BaseOutput getAnalysisChdataSummary(
			@QueryParam("wx_name") String wxName,
			@QueryParam("ch_code") String chCode,
			@NotEmpty @QueryParam("start_date") String startDate,
			@NotEmpty @QueryParam("end_date") String endDate) {
		return weixinAnalysisChdataSummaryService.getAnalysisChdataSummary(wxName, chCode, startDate, endDate);
	}

	/**
	 * @功能简述: 获取公众号资产列表
	 * @param: String
	 *             method, String userToken, String ver, int
	 *             index, int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.wechat.register.list.get")
	public Object getWechatAssetListByType(@NotEmpty @QueryParam("method") String method,
			@NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver,
			@DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) throws Exception {
		return wechatAssetListService.getWechatAssetList( index, size);
	}


	/**
	 * @功能简述: 获取公众号下的图文资产列表
	 * @param:String user_token,String
	 *                   ver,Integer type,String ownerName,int index,int size
	 * @return: Object
	 */
	@GET
	@Path("/mkt.asset.register.imgtext.get")
	public Object getImgTextAsset(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver,
			@NotNull @QueryParam("type") Integer type,
			@NotNull @QueryParam("wx_type") String wxType,
			@NotNull @QueryParam("pub_id") String pubId,
			@QueryParam("search_key") String searchKey,
			@DefaultValue("1") @Min(1) @QueryParam("index") int index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") int size) {
		ImgAsset imgAsset = new ImgAsset();
		imgAsset.setAssetType(type);
		imgAsset.setVer(ver);
		imgAsset.setWxType(wxType);
		imgAsset.setPubId(pubId);
		if (searchKey != null && !"".equals(searchKey)) {
			imgAsset.setSearchKey(searchKey);
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
		return etWxImgTextAssetService.getWxImgTextAssetService(imgAsset);
	}
}
