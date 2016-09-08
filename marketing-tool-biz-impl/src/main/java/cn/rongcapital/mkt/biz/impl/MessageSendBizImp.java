package cn.rongcapital.mkt.biz.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;

import cn.rongcapital.mkt.biz.MessageSendBiz;

@Service
public class MessageSendBizImp extends BaseBiz implements MessageSendBiz {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.rongcapital.mkt.biz.MessageSendBiz#send(com.tagsin.wechat_sdk.App,
	 * java.lang.String, java.lang.String, java.lang.String)
	 * 图片消息体: 
	 * {
	 * 		"touser":"OPENID", "msgtype":"image", "image": { "media_id":"MEDIA_ID" }
	 * } 
	 * 文本消息体： 
	 * { 
	 * 		"touser":"OPENID", "msgtype":"text", "text": {"content":"Hello World" }
	 * }
	 */
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	@Override
	public Boolean send(App app,String touser, String content, String media_id) {
		Boolean issended = false;
		
		// 发送文字消息
		if(content != null && content.length() > 0) {
			String msg = "{\"touser\":\"" + touser + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + content + "\"}}";
			issended = WxComponentServerApi.getBaseWxSdk().send(app, msg);
			if(issended == false) {
				logger.info("发送文字消息失败， msg内容为：{}", msg);
			}
			
		}
		
		// 发送图片消息
		if(media_id != null && media_id.length() > 0) {
			String msg = "{\"touser\":\"" + touser + "\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"" + media_id + "\"}}";
			issended = WxComponentServerApi.getBaseWxSdk().send(app, msg);
			if(issended == false) {
				logger.info("发送图片消息失败， msg内容为：{}", msg);
			}
			
		}
		
		return (issended);
	}

	/* (non-Javadoc)
	 * @see cn.rongcapital.mkt.biz.MessageSendBiz#sendAll(com.tagsin.wechat_sdk.App, boolean, java.lang.String, java.lang.String, java.lang.String)
	 * 发送消息样式：
	 * 文本消息：{"filter":{"is_to_all":false,"tag_id":2},"text":{"content":"ZGmSwfoayacvqtasO_W58OgIlD_ayGsB1LVkAAtNCqA"},"msgtype":"text"}
	 * 图文消息：{"filter":{"is_to_all":false,"tag_id":2},"mpnews":{"media_id":"-8KrDZ9iuLst-DXJl-s6EYEbyafPAw1OTJY59lS7P6g"},"msgtype":"mpnews"}
	 * 返回成功样式：{"errcode":0,"errmsg":"send job submission success","msg_id":1000000001}
	 */
	@Override
	public Boolean sendAll(App app, boolean isToAll, String tagId, String msgType, String media_id) {
		
		Boolean issended = false;
		try {
			String issendedBack =  WxComponentServerApi.getBaseWxSdk().sendAll(app, isToAll, Long.parseLong(tagId), msgType, media_id);
			//获取errcode
			JSONObject userJson = JSON.parseObject(issendedBack);
			Integer jsonInt = userJson.getInteger("errcode");
			if(0 == jsonInt)issended = true;
			if(issended == false) {
				logger.info("群发消息发送图片消息失败，tagId:"+tagId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("批量发送图文方法出现异常："+e.getMessage());
		} 
		return issended;
	}
	
	
}
