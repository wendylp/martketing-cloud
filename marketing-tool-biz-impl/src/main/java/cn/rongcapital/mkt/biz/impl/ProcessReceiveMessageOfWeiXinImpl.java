package cn.rongcapital.mkt.biz.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;


import com.tagsin.wechat_sdk.user.UserInfo;

import cn.rongcapital.mkt.biz.ProcessReceiveMessageOfWeiXinBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.Xml2JsonUtil;
import cn.rongcapital.mkt.dao.WebchatAuthInfoDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.po.WebchatAuthInfo;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.service.QrcodeFocusInsertService;
import cn.rongcapital.mkt.service.WechatAssetService;
import cn.rongcapital.mkt.vo.in.ComponentVerifyTicketIn;


@Service
public class ProcessReceiveMessageOfWeiXinImpl extends BaseBiz implements ProcessReceiveMessageOfWeiXinBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WechatMemberDao wechatMemberDao;
	
	@Autowired
	private WebchatAuthInfoDao webchatAuthInfoDao;
	
	@Autowired
	private QrcodeFocusInsertService qrcodeFocusInsertService;
	
	@Autowired
	private WechatAssetService wechatAssetService;
	
	@Autowired
	private WechatRegisterDao wechatRegisterDao;
	
	private ComponentVerifyTicketIn getComponentVerifyTicketInFromTextXml(String textXml) throws JAXBException{
			JAXBContext context = JAXBContext.newInstance(ComponentVerifyTicketIn.class);  
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream textxmlis = new ByteArrayInputStream(textXml.getBytes());   			
			ComponentVerifyTicketIn componentVerifyTicketIn = (ComponentVerifyTicketIn)unmarshaller.unmarshal(textxmlis);
			return componentVerifyTicketIn;		
	}
	
	/**
	 * @param resultXml
	 *            <xml>
	 *            <ToUserName><![CDATA[gh_4685d4eef135]]></ToUserName>
	 *            <FromUserName><![CDATA[o0gycwIqxXT7DDkwqY-NyqmLb8Pg]]></FromUserName>
	 *            <CreateTime>1473663078</CreateTime>
	 *            <MsgType><![CDATA[event]]></MsgType>
	 *            <Event><![CDATA[SCAN]]></Event>
	 *            <EventKey><![CDATA[196]]></EventKey>
	 *            <Ticket><![CDATA[gQHK8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2JqdEE2MEhsQmh0LW5CZWVaeGNXAAIEDL62VwMEAAAAAA==]]></Ticket>
	 *            </xml>
	 * @return
	 */
	private Map<String,String> parserMsgToMap(String resultXml){	
		Map<String,String> mapBack = new HashMap<String,String>();
		String webchatComponentVerifyTicketJson = Xml2JsonUtil.xml2JSON(resultXml);			
		JSONObject myJsonObject = JSONObject.parseObject(webchatComponentVerifyTicketJson);
		webchatComponentVerifyTicketJson = myJsonObject.get("xml").toString();
		/*webchatComponentVerifyTicketJson:{"CreateTime":["1473071665"],"Event":["subscribe"],"ToUserName":["gh_4685d4eef135"],"FromUserName":["o0gycwIqxXT7DDkwqY-NyqmLb8Pg"],"MsgType":["event"]}*/
		myJsonObject = JSONObject.parseObject(webchatComponentVerifyTicketJson);
		String createTime = myJsonObject.getString("CreateTime");
		if(StringUtils.isNotEmpty(createTime)){
			createTime = createTime.substring(2, createTime.length()-2);			
		}
		mapBack.put("createTime", createTime);
		String event = myJsonObject.getString("Event");
		if(StringUtils.isNotEmpty(event)){
			event = event.substring(2, event.length()-2);			
		}
		mapBack.put("event", event);		
		String toUserName = myJsonObject.getString("ToUserName");
		if(StringUtils.isNotEmpty(toUserName)){
			toUserName = toUserName.substring(2, toUserName.length()-2);
		}		
		mapBack.put("toUserName", toUserName);		
		String fromUserName = myJsonObject.getString("FromUserName");
		if(StringUtils.isNotEmpty(fromUserName)){
			fromUserName = fromUserName.substring(2, fromUserName.length()-2);
		}		
		mapBack.put("fromUserName", fromUserName);
		String msgType = myJsonObject.getString("MsgType");
		if(StringUtils.isNotEmpty(msgType)){
			msgType = msgType.substring(2, msgType.length()-2);
		}
		mapBack.put("msgType", msgType);
		String ticket = myJsonObject.getString("Ticket");
		if(StringUtils.isNotEmpty(ticket)){
			ticket = ticket.substring(2, ticket.length()-2);
		}
		mapBack.put("ticket", ticket);
		String eventKey = myJsonObject.getString("EventKey");
		if(StringUtils.isNotEmpty(eventKey)){
			eventKey = eventKey.substring(2, eventKey.length()-2);
		}
		mapBack.put("eventKey", eventKey);
		
		return mapBack;		
	}
	
	private WechatMember getWechatMemberFromUserInfo(UserInfo userInfo,WechatMember wechatMember){
		wechatMember.setNickname(userInfo.getNickname());
		wechatMember.setSex(userInfo.getSex());
		wechatMember.setCity(userInfo.getCity());
		wechatMember.setCountry(userInfo.getCountry());
		wechatMember.setProvince(userInfo.getProvince());
		wechatMember.setHeadImageUrl(userInfo.getHeadimgurl());
		//关注时间
		wechatMember.setSubscribeTime(userInfo.getSubscribe_time());
		// unionid
		wechatMember.setRemark(userInfo.getRemark());
		return wechatMember;		
	}

	private WebchatAuthInfo getWebchatAuthInfoByAuthAppId(String authAppId){
		WebchatAuthInfo webchatAuthInfoBack = null;
		WebchatAuthInfo webchatAuthInfo = new WebchatAuthInfo();
		webchatAuthInfo.setAuthorizerAppid(authAppId);
		List<WebchatAuthInfo> webchatAuthInfoes = webchatAuthInfoDao.selectList(webchatAuthInfo);
		if(webchatAuthInfoes!=null&&webchatAuthInfoes.size()>0){
			webchatAuthInfoBack = webchatAuthInfoes.get(0);
		}
		return webchatAuthInfoBack;		
	}
	
	private WechatRegister getWechatRegisterByAuthAppId(String authAppId){
		WechatRegister wechatRegisterTemp = new WechatRegister();
		wechatRegisterTemp.setAppId(authAppId);
		List<WechatRegister> wechatRegisters = wechatRegisterDao.selectList(wechatRegisterTemp);
		if(wechatRegisters!=null&&wechatRegisters.size()>0){
			WechatRegister wechatRegisterBack =  wechatRegisters.get(0);
			return wechatRegisterBack;
		}
		return null;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * cn.rongcapital.mkt.biz.ProcessReceiveMessageOfWeiXinBiz#getMsgLog(java.
	 * lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String) 
	 * 以前不在关注状态，这次关注成功的扫描二维码接收消息的xml： 
	 * <xml>
	 * 		<ToUserName><![CDATA[gh_4685d4eef135]]></ToUserName>
	 * 		<FromUserName><![CDATA[o0gycwIqxXT7DDkwqY-NyqmLb8Pg]]></FromUserName>
	 * 		<CreateTime>1473747992</CreateTime>
	 * 		<MsgType><![CDATA[event]]></MsgType>
	 * 		<Event><![CDATA[subscribe]]></Event>
	 * 		<EventKey><![CDATA[qrscene_230]]></EventKey>
	 * 		<Ticket><![CDATA[gQGu8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL05EdGZXcjNsQnh0X2kwMHhlQmNXAAIEEb62VwMEAAAAAA==]]></Ticket>
	 * </xml>
	 * 已经在关注状态的前提下，扫描二维码的xml： 
	 * <xml>
	 * 		<ToUserName><![CDATA[gh_4685d4eef135]]></ToUserName>
	 * 		<FromUserName><![CDATA[o0gycwIqxXT7DDkwqY-NyqmLb8Pg]]></FromUserName>
	 * 		<CreateTime>1473748010</CreateTime> 
	 * 		<MsgType><![CDATA[event]]></MsgType>
	 * 		<Event><![CDATA[SCAN]]></Event> 
	 * 		<EventKey><![CDATA[230]]></EventKey>
	 * 		<Ticket><![CDATA[gQGu8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL05EdGZXcjNsQnh0X2kwMHhlQmNXAAIEEb62VwMEAAAAAA==]]></Ticket>
	 * </xml>
	 * 取消关注 
	 * <xml>
	 * 		<ToUserName><![CDATA[gh_4685d4eef135]]></ToUserName>
	 * 		<FromUserName><![CDATA[o0gycwIqxXT7DDkwqY-NyqmLb8Pg]]></FromUserName>
	 * 		<CreateTime>1473748255</CreateTime> 
	 * 		<MsgType><![CDATA[event]]></MsgType>
	 * 		<Event><![CDATA[unsubscribe]]></Event> 
	 * 		<EventKey><![CDATA[]]></EventKey>
	 * </xml> 
	 * 非扫描二维码关注的xml 
	 * <xml>
	 * 		<ToUserName><![CDATA[gh_4685d4eef135]]></ToUserName>
	 * 		<FromUserName><![CDATA[o0gycwIqxXT7DDkwqY-NyqmLb8Pg]]></FromUserName>
	 * 		<CreateTime>1473748532</CreateTime> 
	 * 		<MsgType><![CDATA[event]]></MsgType>
	 * 		<Event><![CDATA[subscribe]]></Event> 
	 * 		<EventKey><![CDATA[]]></EventKey>
	 * </xml>
	 *
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void getMsgLog(String textXml,String msg_signature,String timestamp,String nonce,String signature,String openid,String authAppId) throws JAXBException, AesException {		
			WXBizMsgCrypt pc = new WXBizMsgCrypt(ApiConstant.TOKEN, ApiConstant.ENCODING_AES_KEY, ApiConstant.APPID); 
			String resultXml = pc.decryptMsg(msg_signature, timestamp, nonce, textXml);		
			logger.info("解密后明文: " + resultXml);
			Map<String, String> msgMap = this.parserMsgToMap(resultXml);
			App app = this.getApp();
			WebchatAuthInfo webchatAuthInfo = this.getWebchatAuthInfoByAuthAppId(authAppId);
			app.setAuthAppId(webchatAuthInfo.getAuthorizerAppid());
			app.setAuthRefreshToken(webchatAuthInfo.getAuthorizerRefreshToken());
			WechatRegister wechatRegister = this.getWechatRegisterByAuthAppId(authAppId);
			int status = 0;
			String event = msgMap.get("event");
			String createTime = msgMap.get("createTime");
			Date date = new Date(Long.parseLong(createTime)*1000);
			switch(event){
			  case "SCAN":{
				  break; 
			  }
			  case "subscribe":{
				  UserInfo userInfo = WxComponentServerApi.getUserInfo(app,openid);//如果openid出错，sdk会直接抛出异常				  
				  if(wechatRegister!=null){
					  wechatAssetService.follow(userInfo, wechatRegister.getWxAcct());
				  }			  
				  String qrCodeTicket = msgMap.get("ticket");
				  status = 0;
				  qrcodeFocusInsertService.insertQrcodeFoucsInfo(qrCodeTicket, openid, date, status, wechatRegister.getWxAcct());
				  break;
			  }
			  case "unsubscribe":{
				  if(wechatRegister!=null){
					  wechatAssetService.unfollow(openid, wechatRegister.getWxAcct());
				  }				  
				  String qrCodeTicket = msgMap.get("ticket");
				  status = 1;
				  qrcodeFocusInsertService.insertQrcodeFoucsInfo(qrCodeTicket, openid, date, status, wechatRegister.getWxAcct());				  
				  break;
			  }
			}
	}
	
	
}
