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
import java.io.InputStream;
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
import cn.rongcapital.mkt.biz.impl.ProcessReceiveMessageOfWeiXin;
//import cn.rongcapital.mkt.biz.impl.ProcessReceiveMessageOfWeiXin;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.AssetWechatAudiencelistMatchGetService;
import cn.rongcapital.mkt.service.GetWeixinAnalysisDateService;
import cn.rongcapital.mkt.service.QrcodeCreateCountService;
import cn.rongcapital.mkt.service.QrcodePicDownloadService;
import cn.rongcapital.mkt.service.QrcodePicsZipDownloadService;
import cn.rongcapital.mkt.service.QrcodeUsedCountService;
import cn.rongcapital.mkt.service.TagUpdateService;
import cn.rongcapital.mkt.service.UploadFileService;
import cn.rongcapital.mkt.service.WebchatComponentVerifyTicketService;
import cn.rongcapital.mkt.service.WechatAnalysisDaysListService;
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
	 * @功能简述 : 根据id更新标签信息
	 */
	@POST
	@Path("/mkt.weixin.qrcode.pics.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput getQrcodes(@NotEmpty @QueryParam("action_name") String actionName,@DefaultValue("1") @Min(1) @Max(100000) @QueryParam("start_scene_id") int startSceneId,@DefaultValue("100000") @Min(1) @Max(100000) @QueryParam("end_scene_id") int endSceneId) {
		BaseOutput baseOutput = wechatQrcodeBiz.getQrcodes(startSceneId, endSceneId, actionName);
		return baseOutput;
	}
	
	@POST
	@Path("/mkt.weixin.qrcode.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput createQrcode(@Valid WechatQrcodeIn body) {
		logger.info("createQrcode is start .....................");
//		String wechatQrcodeStr="{\"wx_name\": \"果倍爽\",     \"ch_code\": 112,     \"is_audience\": 0,     \"audience_name\": \"90后\",     \"related_tags\": \"101;103;112\",     \"comments\": \"备注1\",     \"status\": 1,     \"qrcode_pic\": \"果倍爽\",     \"qrcode_url\": \"https://www.baidu.com\",\"ticket\":\"gQFH7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xLzNqdDh5SXZsRnh0dXhhZVFXeGNXAAIEEH61VwMEAAAAAA==\"}";
//		String wechatQrcodeStr="{\"wxName\":\"sfasfa\"}";
		BaseOutput baseOutput = wechatQrcodeBiz.createQrcode(body);
		//@NotEmpty @QueryParam("user_token") String userToken,@NotNull @QueryParam("wechat_qrcode") String wechatQrcodeStr
//		return tagService.tagInfoUpdate(body);
		return baseOutput;
	}
	
	/**
	 * @功能简述 : 根据id更新标签信息
	 */
	@POST
	@Path("/mkt.weixin.qrcode.get")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput getQrcode(@NotEmpty @QueryParam("action_name") String actionName,@DefaultValue("10") @Min(1) @Max(100000) @QueryParam("scene_id") int sceneId) {
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
//	public String getComponentVerifyTicket(@Valid ComponentVerifyTicketIn componentVerifyTicketIn,@QueryParam("msg_signature") String msg_signature,@QueryParam("timestamp") String timestamp, @QueryParam("nonce") String nonce){		
	public String getComponentVerifyTicket( ComponentVerifyTicketIn componentVerifyTicketIn,@QueryParam("msg_signature") String msg_signature,@QueryParam("timestamp") String timestamp, @QueryParam("nonce") String nonce){		
	logger.info(System.currentTimeMillis()+"mkt.weixin.qrcode.getComponentVerifyTicket satrt 0000000000000000000000000000000000000000000000"+msg_signature+":"+timestamp+":"+nonce);
	logger.info("0000000000000000000000000000000000000000000000"+componentVerifyTicketIn.getAppId()+":"+componentVerifyTicketIn.getToUserName()+":"+componentVerifyTicketIn.getEncrypt());
		webchatComponentVerifyTicketService.insert(componentVerifyTicketIn,msg_signature, timestamp, nonce);
		logger.info("0000000000000000000000000000000000000000000000getComponentVerifyTicket");	
	//	System.out.println(System.currentTimeMillis()+"mkt.weixin.qrcode.getComponentVerifyTicket end 0000000000000000000000000000000000000000000000");
		return "success";		
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
	@Path("/mkt.weixin.qrcode.getComponentVerifyTicket1")
	@Consumes({MediaType.TEXT_XML})
	public String getComponentVerifyTicket1( String textxml,@QueryParam("msg_signature") String msg_signature,@QueryParam("timestamp") String timestamp, @QueryParam("nonce") String nonce){
		logger.info("getComponentVerifyTicket1:"+textxml+"*******************************");		
		try {			
			if(textxml.contentEquals("Event")){}
			
			JAXBContext context = JAXBContext.newInstance(SubscribeVO.class);  
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			XMLInputFactory xmlFactory  = XMLInputFactory.newInstance();  

			InputStream   textxmlis   =   new   ByteArrayInputStream(textxml.getBytes());   
			
			SubscribeVO subscribeVO = (SubscribeVO)unmarshaller.unmarshal(textxmlis);
			
			ProcessReceiveMessageOfWeiXin handler = new ProcessReceiveMessageOfWeiXin();
			String textxmlBack = handler.process(textxml.getBytes());	
			
			System.out.println(textxmlBack);
		} catch (JAXBException e) {			
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {			
			e.printStackTrace();
		}

		
//		webchatComponentVerifyTicketService.insert(componentVerifyTicketIn,msg_signature, timestamp, nonce);
		return "success";		
	}
	
	/**
	 * @param userToken
	 * @param ver
	 * 测试
	 * @return
	 */
	@GET
	@Path("/shui")
	public BaseOutput shui(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
		App app = baseBiz.getApp();
		String authAppId = "wxa5fb7dea54673299";
		String authRefreshToken = "refreshtoken@@@kkMVjC90JS9ooW_zsUUhfvjFbwAlfvB9pmBTZArGYMM";
		String authorizer_appid = "wxeb10897c0cd98e36";
		//wechatMemberBiz.getUserList(authorizer_appid, authRefreshToken);
		messageSendBiz.send(app, "", "hahhhh", "12233");
//		return wechatPublicAuthService.authWechatPublicAccount();
//		return wechatPublicAuthBiz.authWechatPublicAccount();
	    
	    baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
	    baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
		return baseOutput;
	}
	
	




	/**
	 * @return
	 * 测试
	 */
	@GET
	@Path("/testInterface")
	public BaseOutput testInterface() {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
		App app = baseBiz.getApp();
		app.setAuthAppId("wx1f363449a14a1ad8");
		app.setAuthRefreshToken("refreshtoken@@@gcxmruaeql5C84jx-VHSnt99pOxbEWycsHz7tKgL-ao");
		/*
		 * 测试获取监测微信公众号下群组(标签)信息
		 */
//		List<WechatGroup> wechatGroups = wechatGroupBiz.getTags(app.getAuthAppId(), app.getAuthRefreshToken());
		/*
		 * 获取监测微信公众号下图文信息    图片（image）、视频（video）、语音 （voice）、图文（news）
		 */
//		List<ImgTextAsset> imgTextAssetes = imgTextAssetBiz.getMaterialList(app.getAuthAppId(), app.getAuthRefreshToken(), "news");
		/*
		 * 发送文字图文信息给群组(标签)  ZGmSwfoayacvqtasO_W58OgIlD_ayGsB1LVkAAtNCqA    ZGmSwfoayacvqtasO_W58JMM4Szs9TuUR755HUw3hP8
		 */
		messageSendBiz.sendAll(app, false, "101", "mpnews", "-8KrDZ9iuLst-DXJl-s6EYEbyafPAw1OTJY59lS7P6g");
		
//		wechatMemberBiz.getUserList("", "");
//		return wechatPublicAuthService.authWechatPublicAccount();
//		return wechatPublicAuthBiz.authWechatPublicAccount();
//		baseOutput.setData(wechatGroups);
	    baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
	    baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
		return baseOutput;
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
			@NotNull @QueryParam("wx_name") String wxName,
			@NotEmpty @QueryParam("ch_code") String chCode,
			@NotEmpty @QueryParam("start_date") String startDate,
			@NotEmpty @QueryParam("end_date") String endDate) {
		return weixinAnalysisChdataSummaryService.getAnalysisChdataSummary(wxName, chCode, startDate, endDate);
	}
}
