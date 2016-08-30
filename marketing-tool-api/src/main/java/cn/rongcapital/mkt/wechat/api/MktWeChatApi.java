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

import java.util.Date;

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

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.AssetWechatAudiencelistMatchGetService;
import cn.rongcapital.mkt.service.QrcodeCreateCountService;
import cn.rongcapital.mkt.service.QrcodePicDownloadService;
import cn.rongcapital.mkt.service.QrcodePicsZipDownloadService;
import cn.rongcapital.mkt.service.QrcodeUsedCountService;
import cn.rongcapital.mkt.service.TagUpdateService;
import cn.rongcapital.mkt.service.WeixinQrcodeBatchSaveService;
//import cn.rongcapital.mkt.service.WeixinQrcodeBatchSaveService;
import cn.rongcapital.mkt.service.WeixinQrcodeDelService;
import cn.rongcapital.mkt.service.WeixinQrcodeErrorDownloadService;
import cn.rongcapital.mkt.service.WeixinQrcodeInfoService;
import cn.rongcapital.mkt.service.WeixinQrcodeListService;
import cn.rongcapital.mkt.service.WeixinQrcodeMatchGetService;
import cn.rongcapital.mkt.service.WeixinQrcodeSaveOrUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagBodyUpdateIn;
import cn.rongcapital.mkt.vo.in.WechatQrcodeInData;

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
			@NotEmpty @QueryParam("ver") String ver, 
			@QueryParam("wxmp_name") String wxmpName,
			@QueryParam("expiration_time") Integer expirationTime,
			@DefaultValue("0") @QueryParam("qrcode_status") Byte qrcodeStatus,
			@DefaultValue("1") @Min(1) @QueryParam("index") int index,
			@DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") int size) {

		return weixinQrcodeListService.getWeixinQrcodeList(wxmpName, expirationTime, qrcodeStatus,index, size);
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

		return weixinQrcodeListService.getWeixinQrcodeListQrname(qrcodeName,index,size);

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
	public BaseOutput getCreateCount(@NotNull @QueryParam("batch_id") Integer batch_id) {
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
			@NotNull @QueryParam("batch_id") int batch_id) {
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
	 * @param id
	 * @return
	 * @author shuiyangyang
	 * @Data 2016.08.25
	 */
	@POST
	@Path("/mkt.weixin.qrcode.del")
	public BaseOutput weixinQrocdeDel(
			@NotNull @QueryParam("id") Integer id) {
		return weixinQrcodeDelService.weixinQrocdeDel(id);
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
	public BaseOutput weixinQrcodeMatchGet(
			@NotEmpty @QueryParam("qrcode_name") String qrcodeName,
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
	public BaseOutput weixinQrcodeRecordsDel(
			@NotNull @QueryParam("id") int id) {
		return weixinQrcodeDelService.weixinQrcodeRecordsDel(id);
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
	public BaseOutput assetWechatAudiencelistMatchGet(
			@NotEmpty @QueryParam("audience_name") String audienceName) {
		return assetWechatAudiencelistMatchGetService.assetWechatAudiencelistMatchGet(audienceName);
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
	public BaseOutput weixinQrcodeErrorDownload(
			@NotNull @QueryParam("batch_id") Integer batchId) {
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
	@GET
	@Path("/mkt.weixin.qrcode.batch.save")
	public BaseOutput weixinQrcodeBatchSave(
			@NotNull @QueryParam("batch_id") Integer batchId,
			@NotNull @QueryParam("expiration_time") String expirationTime,
			@NotEmpty @QueryParam("qrcode_tag_ids") String qrcodeTagIds,
			@NotNull @QueryParam("qrcode_status") Integer qrcodeStatus) {
		return weixinQrcodeBatchSaveService.weixinQrcodeBatchSave(batchId, expirationTime, qrcodeTagIds, qrcodeStatus);
	}
	
	@POST
	@Path("/mkt.weixin.qrcode.saveorupdate")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput weixinSaveOrUpdate(@Valid WechatQrcodeInData body){
		return weixinQrcodeSaveOrUpdateService.weixinSaveOrUpdate(body);
	}

}
