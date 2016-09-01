package cn.rongcapital.mkt.service.impl;

import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.tagsin.tutils.codec.AES;
import com.thoughtworks.xstream.XStream;

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
		logger.info("WebchatComponentVerifyTicketServiceImpl: 开始调试。。。。。。。。。。。。。。。。。。。。。。。" );
		String appId= componentVerifyTicketIn.getAppId();
		String encrypt = componentVerifyTicketIn.getEncrypt();
		String encodingAesKey = "abcdefghijklmnopqrstuvwxyz12345678900987654";
		String token = "ruixuemarketingcloud";				
		try {
			logger.info("000000000000000000000000000000000000000000000000" );
/*			JAXBContext context = JAXBContext.newInstance(ComponentVerifyTicketIn.class);  
	        Marshaller marshaller = context.createMarshaller();  
	        
	        marshaller.marshal(jaxbElement, os);.marshal(componentVerifyTicketIn, System.out);  
*/	        
	        XStream xstream = new XStream( ); 
	        //xstream.alias("policy", Policy.class);
	        //xstream.alias("item", Item.class);
	        encrypt = xstream.toXML(componentVerifyTicketIn);
	        encrypt = "<xml>"+
	        		"<AppId><![CDATA["+componentVerifyTicketIn.getAppId()+"]]></AppId>"+
	        		"<ToUserName><![CDATA["+componentVerifyTicketIn.getToUserName()+"]]></ToUserName>"+
	    	        "<Encrypt><![CDATA["+componentVerifyTicketIn.getEncrypt()+"]]></Encrypt>"+
	    	        "</xml>";
/*	        encrypt = "<xml>"+
	        "<AppId><![CDATA[wx00f7d56d549f82ce]]></AppId>"+
	        "<ToUserName><![CDATA[gh_4685d4eef135]]></ToUserName>"+
	        "<Encrypt><![CDATA[7YpiOZUA5rWSOiJUv9ouVEuT7DEqv2xat+blfLq+88+8CKgIvakhuwTwjtZAyyj8HMffM3hFfoYWEznIaPbs4Cj/FnddTNw5UZ6qO7xN+lKJ7NIU8oKCIHwq6YIUKcsOoVFXwSG7wMISHwEt42REHlHbriXuOf2nnfZhWVzGeRl4OcZ2kNNJoROp/A65t81eo/zh6skUUaQVO0pBnGn42c5RhhKHlZCeKKTeruPQRCphqX6EHEai10L41vpHHu8MPLtinHS7Mzg0xC52Yb1pPHiIYUp4M0BlcBUM/jAi4/+fj/elMiRAwvVNonBId3VXf6x1lRQtrG5eMG8OPhEGn5BSSva8hjxhnOdfqAcSioDjTR/1DFCiDYCVU999WeWCrjRphNauYaMohxqdO9kjI+Aiu2gS7Uc0fev/sq29BzSCaTOloL4Pj82Qfn+xBulAa1udVxHO7D7mcDRNdIrZHw==]]></Encrypt>"+
	        "</xml>";
*/			logger.info("11111111111111111111111111111111111111111111" );
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			logger.info("333333333333333333333333333333333333333333333" );
//			String newencrypt = new String(encrypt.getBytes(), "UTF-8");  
			// 第三方收到公众号平台发送的消息
			logger.info(encrypt );
			String result2 = pc.decryptMsg(msg_signature, timestamp, nonce, encrypt);
			logger.info("444444444444444444444444444444444444444444444444" );
			System.out.println("解密后明文: " + result2);
			logger.info("解密后明文: " + result2);
/*			Object obj = xstream.fromXML(result2);
			JAXBContext context = JAXBContext.newInstance(WebchatComponentVerifyTicket.class);  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            WebchatComponentVerifyTicket webchatComponentVerifyTicket = (WebchatComponentVerifyTicket)unmarshaller.unmarshal(new StringReader(result2));
*/   
			
			String webchatComponentVerifyTicketJson = Xml2JsonUtil.xml2JSON(result2);
			logger.info("555555555555555555555555555555555555555555555555555" );
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
				logger.info("插入数据库开始。。。。。。。。。。。。。。webchatComponentVerifyTicketDao ");
				webchatComponentVerifyTicketDao.insert(webchatComponentVerifyTicket);
				ApiConstant.component_verify_ticket = webchatComponentVerifyTicket.getComponentVerifyTicket();
				logger.info("插入数据库成功。。。。。。。。。。。。。。webchatComponentVerifyTicketDao ");
//				System.out.println("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info(e.getMessage());
			}
 //           WebchatComponentVerifyTicket webchatComponentVerifyTicket = new WebchatComponentVerifyTicket();
 //   		BeanUtils.copyProperties(componentVerifyTicketIn, webchatComponentVerifyTicket);
 //   		webchatComponentVerifyTicketDao.insert(webchatComponentVerifyTicket);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
			
	}

	
	
	
}
