package cn.rongcapital.mkt.wechat.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.biz.ProcessReceiveMessageOfWeiXinBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;

@Component
@Path(ApiConstant.API_PATH_APPID)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktWeChatMsgApi {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProcessReceiveMessageOfWeiXinBiz processReceiveMessageOfWeiXinBiz;
	
	
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
	public String getMsgEvent(String textxml,@QueryParam("msg_signature") String msg_signature,@QueryParam("timestamp") String timestamp, @QueryParam("nonce") String nonce, @QueryParam("signature") String signature, @QueryParam("openid") String openid){
			logger.info(textxml);
			processReceiveMessageOfWeiXinBiz.getMsgLog(textxml, msg_signature, timestamp, nonce, signature, openid);
		return "success";		
	}

}
