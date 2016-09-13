package cn.rongcapital.mkt.biz.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tagsin.tutils.http.HttpResult;
import com.tagsin.tutils.http.Requester;
import com.tagsin.tutils.http.Requester.Method;
import com.tagsin.tutils.json.JsonUtils;
import com.tagsin.wechat_sdk.App;

import com.tagsin.wechat_sdk.token.TokenType;

import cn.rongcapital.mkt.biz.WechatQrcodeBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.ImageCompressUtil;
import cn.rongcapital.mkt.dao.WebchatAuthInfoDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeTicketDao;
import cn.rongcapital.mkt.po.WebchatAuthInfo;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.WechatQrcodeTicket;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.AssociationTag;
import cn.rongcapital.mkt.vo.in.WechatQrcodeIn;

@Service
public class WechatQrcodeBizImpl extends BaseBiz implements WechatQrcodeBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	WechatQrcodeDao wechatQrcodeDao;
	@Autowired
	WechatQrcodeTicketDao wechatQrcodeTicketDao;	
	@Autowired
	WebchatAuthInfoDao webchatAuthInfoDao;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput getQrcode(int sceneId,String actionName) throws FileNotFoundException, IOException {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
			App app = this.getApp();
			long expireSeconds = 0;
			if(actionName.equals("QR_LIMIT_SCENE")){
				expireSeconds=0;
			}
			Requester req = Requester.builder().setMethod(Method.POST)
					.setUrl(ApiConstant.weixin_qrcode_create)
					.addUrlParm("access_token", app.tokenManager.getAuthToken(TokenType.AUTHORIZER_ACCESS_TOKEN));					
			Map<String,Object> reqData = convertPathValueToMap(sceneId, "action_info","scene","scene_id");
			reqData.put("action_name", actionName);		
			req.setBody(JsonUtils.toJson(reqData));
			HttpResult result = req.execute();			
			if(result.getCode()==200){
				ObjectNode objNode = JsonUtils.readJsonObject(result.getRespBody());
				String ticket = objNode.get("ticket").getTextValue();
				String url = objNode.get("url").getTextValue();
				WechatQrcodeTicket wechatQrcodeTicket = new WechatQrcodeTicket();
				wechatQrcodeTicket.setSceneId(sceneId);
				wechatQrcodeTicket.setTicket(ticket);
				wechatQrcodeTicket.setUrl(url);
				wechatQrcodeTicketDao.insert(wechatQrcodeTicket);
				long id = wechatQrcodeTicket.getId();
				wechatQrcodeTicket.setSceneId(Integer.parseInt(String.valueOf(id)));
				
				baseOutput.setTotal(1);
				List<Object> data = new ArrayList<Object>();
				Map<String,Object> mapBack = new HashMap<String,Object>();
				mapBack.put("ticket", ticket);
				mapBack.put("url", url);
				data.add(mapBack);
				baseOutput.setData(data);
				
				req = Requester.builder()
						.setUrl(ApiConstant.weixin_qrcode_show)
						.addUrlParm("ticket", ticket);				
				result = req.execute();
				if(result.getCode()==200){
					String fileType = result.getContentType().toLowerCase().replace("image/", "");
					StringBuilder sb = new StringBuilder();
					String respBodyString = Base64.encodeBase64String(result.getRespBody());
					sb.append("data:image/").append(fileType).append(";base64, ").append(respBodyString);
					byte[] streamByte = Base64.decodeBase64(respBodyString);
					logger.info(sb.toString());					
					this.byte2image(streamByte,ApiConstant.upload_img_path_large+wechatQrcodeTicket.getSceneId()+".jpg",1200,1200);
					this.byte2image(streamByte,ApiConstant.upload_img_path_middle+wechatQrcodeTicket.getSceneId()+".jpg",800,800);
					this.byte2image(streamByte,ApiConstant.upload_img_path_small+wechatQrcodeTicket.getSceneId()+".jpg",200,200);
				}				
			}
		return baseOutput;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput getQrcodes(int startSceneId, int endSceneId, String actionName) throws FileNotFoundException, IOException {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
			List<Object> data = new ArrayList<Object>();
			int totalSucc=0;
			WebchatAuthInfo webchatAuthInfo = new WebchatAuthInfo();		
			List<WebchatAuthInfo> webchatAuthInfos = webchatAuthInfoDao.selectList(webchatAuthInfo);			
			if(webchatAuthInfos!=null&&webchatAuthInfos.size()>0){
				List<WebchatAuthInfo> webchatAuthInfosTemp = new ArrayList<WebchatAuthInfo>();
				App app = this.getApp();
				for(Iterator<WebchatAuthInfo> iter = webchatAuthInfos.iterator();iter.hasNext();){
					WebchatAuthInfo webchatAuthInfoTemp = iter.next();
					app.setAuthAppId(webchatAuthInfoTemp.getAuthorizerAppid());
					app.setAuthRefreshToken(webchatAuthInfoTemp.getAuthorizerRefreshToken());
					for(int i=startSceneId;i<=endSceneId;i++){
						long expireSeconds = 0;
						if(actionName.equals("QR_LIMIT_SCENE")){
							expireSeconds=0;
						}
						Requester req = Requester.builder().setMethod(Method.POST)
								.setUrl(ApiConstant.weixin_qrcode_create)
								.addUrlParm("access_token",app.tokenManager.getAuthToken(TokenType.AUTHORIZER_ACCESS_TOKEN));	
						
						Map<String,Object> reqData = convertPathValueToMap(i, "action_info","scene","scene_id");
						reqData.put("action_name", actionName);		
						req.setBody(JsonUtils.toJson(reqData));
						HttpResult result = req.execute();			
						if(result.getCode()==200){
							ObjectNode objNode = JsonUtils.readJsonObject(result.getRespBody());
							JsonNode jsonNode = objNode.get("ticket");
							if(jsonNode==null){
								break;
							}
							String ticket = jsonNode.getTextValue();
							String url = objNode.get("url").getTextValue();
							WechatQrcodeTicket wechatQrcodeTicket = new WechatQrcodeTicket();
							wechatQrcodeTicket.setSceneId(i);
							wechatQrcodeTicket.setTicket(ticket);
							wechatQrcodeTicket.setUrl(url);
							wechatQrcodeTicket.setState(0);
							wechatQrcodeTicketDao.insert(wechatQrcodeTicket);
							long id = wechatQrcodeTicket.getId();
							wechatQrcodeTicket.setSceneId(Integer.parseInt(String.valueOf(id)));
							
							Map<String,Object> mapBack = new HashMap<String,Object>();
							mapBack.put("ticket", ticket);
							mapBack.put("url", url);
							data.add(mapBack);
							totalSucc++;
							
							req = Requester.builder()
									.setUrl(ApiConstant.weixin_qrcode_show)
									.addUrlParm("ticket", ticket);				
							result = req.execute();
							if(result.getCode()==200){
								String fileType = result.getContentType().toLowerCase().replace("image/", "");
								StringBuilder sb = new StringBuilder();
								String respBodyString = Base64.encodeBase64String(result.getRespBody());
								sb.append("data:image/").append(fileType).append(";base64, ").append(respBodyString);
								byte[] streamByte = Base64.decodeBase64(respBodyString);									
								this.byte2image(streamByte,ApiConstant.upload_img_path_large+wechatQrcodeTicket.getSceneId()+".jpg",1200,1200);
								this.byte2image(streamByte,ApiConstant.upload_img_path_middle+wechatQrcodeTicket.getSceneId()+".jpg",800,800);
								this.byte2image(streamByte,ApiConstant.upload_img_path_small+wechatQrcodeTicket.getSceneId()+".jpg",200,200);
							}	
						}
					}
				}
			}			
			baseOutput.setTotal(totalSucc);
			baseOutput.setData(data);
		return baseOutput;
	}

	@Override
	public BaseOutput getWechatQrcodeTicket() {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);		
			WechatQrcodeTicket  wechatQrcodeTicket  = new WechatQrcodeTicket();
			List<WechatQrcodeTicket> wechatQrcodeTickets = wechatQrcodeTicketDao.selectList(wechatQrcodeTicket);
			if(wechatQrcodeTickets!=null&&wechatQrcodeTickets.size()>0){
				WechatQrcodeTicket wechatQrcodeTicketBack = wechatQrcodeTickets.get(0);
				
				baseOutput.setTotal(1);
				List<Object> data = new ArrayList<Object>();
				Map<String,Object> mapBack = new HashMap<String,Object>();
				mapBack.put("scene_id", ApiConstant.return_img_path_small+wechatQrcodeTicketBack.getId()+".jpg");
				mapBack.put("url", wechatQrcodeTicketBack.getUrl());
				data.add(mapBack);
				baseOutput.setData(data);
			}

		return baseOutput;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput createQrcode(WechatQrcodeIn wechatQrcodeIn) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
			WechatQrcode wechatQrcode = new WechatQrcode();
			
			if(wechatQrcodeIn.getId()!=0){
				wechatQrcode.setId(Integer.valueOf(wechatQrcodeIn.getId()+""));
			}else{
				//同一公众号下,微信二维码名称重复校验
				wechatQrcode.setWxAcct(wechatQrcodeIn.getWx_acct());
				wechatQrcode.setQrcodeName(wechatQrcodeIn.getQrcode_name());
				wechatQrcode.setStatus(int2OneByte(1));
				List<WechatQrcode> selectList = wechatQrcodeDao.selectList(wechatQrcode);
				
				if(selectList != null && selectList.size() > 0){
					
					baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
					baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
					return baseOutput;
				}
			}
			
			if(StringUtils.isNotEmpty(wechatQrcodeIn.getWx_acct())){
				wechatQrcode.setWxAcct(wechatQrcodeIn.getWx_acct());
			}
			
			if(StringUtils.isNotEmpty(wechatQrcodeIn.getWx_name())){
				wechatQrcode.setWxName(wechatQrcodeIn.getWx_name());
			}			
			if(wechatQrcodeIn.getCh_code()!=null){
				wechatQrcode.setChCode(wechatQrcodeIn.getCh_code());
			}
			if(StringUtils.isNotEmpty(wechatQrcodeIn.getFixed_audience())){
				wechatQrcode.setAudienceName(wechatQrcodeIn.getFixed_audience());
				wechatQrcode.setIsAudience(int2OneByte(1));
			}else{
				wechatQrcode.setIsAudience(int2OneByte(0));
			}			
			//失效时间补足时分秒00:00:00
			if(wechatQrcodeIn.getExpiration_time() !=null && !"".equals(wechatQrcodeIn.getExpiration_time())){
				wechatQrcode.setExpirationTime(DateUtil.getDateFromString(wechatQrcodeIn.getExpiration_time()+" 00:00:00","yyyy-MM-dd hh:mm:ss"));
			 }
			
			if(StringUtils.isNotEmpty(wechatQrcodeIn.getQrcode_name())){
				wechatQrcode.setQrcodeName(wechatQrcodeIn.getQrcode_name());
			}
			
			if(StringUtils.isNotEmpty(wechatQrcodeIn.getComments())){
				wechatQrcode.setComments(wechatQrcodeIn.getComments());
			}			
			List<AssociationTag> associationTags = wechatQrcodeIn.getAssociation_tags();
			if(associationTags!=null&&associationTags.size()>0){
				StringBuffer tagIdsb = new StringBuffer();
				for(Iterator<AssociationTag> iter = associationTags.iterator();iter.hasNext();){
					AssociationTag associationTag = iter.next();
					if(associationTag!=null){
						long tagId = associationTag.getId();						
						tagIdsb.append(tagId).append(";");
					}					
				}
				tagIdsb.substring(0, tagIdsb.length()-1);
				wechatQrcode.setRelatedTags(tagIdsb.toString());
			}			
			
			wechatQrcode.setStatus(int2OneByte(1));
			WechatQrcodeTicket wechatQrcodeTicket = new WechatQrcodeTicket();
			wechatQrcodeTicket.setState(0);
			wechatQrcodeTicket.setOrderField("id");
			wechatQrcodeTicket.setOrderFieldType("ASC");
			List<WechatQrcodeTicket> wechatQrcodeTickets = wechatQrcodeTicketDao.selectList(wechatQrcodeTicket);
			if(wechatQrcodeTickets!=null&&wechatQrcodeTickets.size()>0){
				WechatQrcodeTicket wechatQrcodeTicketTemp = wechatQrcodeTickets.get(0);
				wechatQrcode.setQrcodePic(String.valueOf(wechatQrcodeTicketTemp.getId())+".jpg");
				wechatQrcode.setQrcodeUrl(wechatQrcodeTicketTemp.getUrl());				
				wechatQrcode.setTicket(String.valueOf(wechatQrcodeTicketTemp.getId()));
				wechatQrcodeTicketTemp.setState(1);
				wechatQrcodeTicketDao.updateById(wechatQrcodeTicketTemp); 
			}			
			wechatQrcode.setCreateTime(new Date());
			//有Id更新无id新增
			if(wechatQrcode.getId() != null && wechatQrcode.getId() != 0){
				wechatQrcodeDao.updateById(wechatQrcode);
			}else{
				wechatQrcodeDao.insert(wechatQrcode);
			}
			
			baseOutput.setTotal(1);
			List<Object> data = new ArrayList<Object>();
			Map<String,Object> mapBack = new HashMap<String,Object>();
			mapBack.put("id", wechatQrcode.getId());
			mapBack.put("qrcodePic", ApiConstant.return_img_path_small+wechatQrcode.getQrcodePic());
			Date createTime = wechatQrcode.getCreateTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			mapBack.put("createtime", sdf.format(createTime));
			
			data.add(mapBack);
			baseOutput.setData(data);
		return baseOutput;
	}

	public static byte int2OneByte(int num) {  
        return (byte) (num & 0x000000ff);  
    }
	
	public static Map<String,Object> convertPathValueToMap(Object pathValue , String ... path){
		Map<String,Object> root = new HashMap<String,Object>();
		Map<String,Object> current = root;
		for(int i=0;i<path.length;i++){
			String key = path[i];
			if(i==path.length-1){
				current.put(key, pathValue);
			}else{
				Map<String,Object> sub = new HashMap<String,Object>();
				current.put(key,sub);
				current = sub;
			}
		}
		return root;
	}
	
	
	//byte数组到图片
	public void byte2image(byte[] data,String path,int width,int height) throws FileNotFoundException, IOException{
	    if(data.length<3||path.equals("")){
	    	 return;
	    }	   
	    FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
	    imageOutput.write(data, 0, data.length);
	    imageOutput.close();
	    ImageCompressUtil.zipImageFile(path, width, height, 1f, "");
	  }

}
