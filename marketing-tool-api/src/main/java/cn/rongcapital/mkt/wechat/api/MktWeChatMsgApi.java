package cn.rongcapital.mkt.wechat.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.ws.rs.Consumes;
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

import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.biz.impl.ProcessReceiveMessageOfWeiXin;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.vo.weixin.SubscribeVO;

@Component
@Path(ApiConstant.API_PATH_APPID)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktWeChatMsgApi {
	private Logger logger = LoggerFactory.getLogger(getClass());
	

	/**
	 * @param componentVerifyTicketIn
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * 测试
	 * @return
	 */
	@POST
	@Path("/mkt.weixin.qrcode.getMsgEvent")
	@Consumes({MediaType.TEXT_XML})
	public String getMsgEvent(String textxml){
		
		logger.info("getMsgEvent:"+textxml+"*******************************");		
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

}
