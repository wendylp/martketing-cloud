package cn.rongcapital.mkt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.Xml2JsonUtil;
import cn.rongcapital.mkt.dao.WebchatComponentVerifyTicketDao;
import cn.rongcapital.mkt.po.WebchatComponentVerifyTicket;
import cn.rongcapital.mkt.service.WebchatComponentVerifyTicketService;
import cn.rongcapital.mkt.vo.in.ComponentVerifyTicketIn;

@Service
public class WebchatComponentVerifyTicketServiceImpl implements WebchatComponentVerifyTicketService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WebchatComponentVerifyTicketDao webchatComponentVerifyTicketDao;

	@Override
	public void insert(String componentVerifyTicketXml) {
		String componentVerifyTicketJson = Xml2JsonUtil.xml2JSON(componentVerifyTicketXml);
		WebchatComponentVerifyTicket webchatComponentVerifyTicket = JSONObject.parseObject(componentVerifyTicketJson, WebchatComponentVerifyTicket.class);
		webchatComponentVerifyTicketDao.insert(webchatComponentVerifyTicket);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void insert(ComponentVerifyTicketIn componentVerifyTicketIn,String msg_signature,String timestamp,String nonce) {
		String appId= componentVerifyTicketIn.getAppId();
		String encrypt = componentVerifyTicketIn.getEncrypt();
		String encodingAesKey = "abcdefghijklmnopqrstuvwxyz12345678900987654";
		String token = "ruixuemarketingcloud";				
		try {
	        encrypt = "<xml>"+
	        		"<AppId><![CDATA["+componentVerifyTicketIn.getAppId()+"]]></AppId>"+
	        		"<ToUserName><![CDATA["+componentVerifyTicketIn.getToUserName()+"]]></ToUserName>"+
	    	        "<Encrypt><![CDATA["+componentVerifyTicketIn.getEncrypt()+"]]></Encrypt>"+
	    	        "</xml>";
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId); 
			// 第三方收到公众号平台发送的消息
			String result2 = pc.decryptMsg(msg_signature, timestamp, nonce, encrypt);
			String webchatComponentVerifyTicketJson = Xml2JsonUtil.xml2JSON(result2);
			try {
				JSONObject myJsonObject = JSONObject.parseObject(webchatComponentVerifyTicketJson);
				webchatComponentVerifyTicketJson = myJsonObject.get("xml").toString();
				myJsonObject = JSONObject.parseObject(webchatComponentVerifyTicketJson);
				String appIdTemp = myJsonObject.getString("AppId");
				appIdTemp = appIdTemp.substring(2, appIdTemp.length()-2);
				String createTime = myJsonObject.getString("CreateTime");
				createTime = createTime.substring(2, createTime.length()-2);
				String componentVerifyTicket = myJsonObject.getString("ComponentVerifyTicket");
				componentVerifyTicket = componentVerifyTicket.substring(2, componentVerifyTicket.length()-2);
				String infoType = myJsonObject.getString("InfoType");
				infoType = infoType.substring(2, infoType.length()-2);
				WebchatComponentVerifyTicket webchatComponentVerifyTicket = new WebchatComponentVerifyTicket();
				webchatComponentVerifyTicket.setAppId(appIdTemp);
				webchatComponentVerifyTicket.setCreateTime(Long.parseLong(createTime));
				webchatComponentVerifyTicket.setComponentVerifyTicket(componentVerifyTicket);
				webchatComponentVerifyTicket.setInfoType(infoType);	
				webchatComponentVerifyTicketDao.insert(webchatComponentVerifyTicket);
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
 		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
}
