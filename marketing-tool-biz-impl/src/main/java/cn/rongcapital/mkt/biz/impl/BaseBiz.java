package cn.rongcapital.mkt.biz.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.tagsin.tutils.json.JsonUtils;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;
import com.tagsin.wechat_sdk.token.Token;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.WebchatComponentVerifyTicketDao;
import cn.rongcapital.mkt.jedis.JedisClient;
import cn.rongcapital.mkt.jedis.JedisException;
import cn.rongcapital.mkt.po.WebchatComponentVerifyTicket;
import cn.rongcapital.mkt.service.WechatInterfaceLogService;

@Service
public class BaseBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	WechatInterfaceLogService wechatInterfaceLogService;
	
	@Autowired
	private WebchatComponentVerifyTicketDao webchatComponentVerifyTicketDao;
	
	public App getApp(){
		 	App app = new App(ApiConstant.APPID,ApiConstant.SECRET);
	        WebchatComponentVerifyTicket webchatComponentVerifyTicketq = new WebchatComponentVerifyTicket();
	        webchatComponentVerifyTicketq.setOrderField("id");
	        webchatComponentVerifyTicketq.setOrderFieldType("desc");
	        webchatComponentVerifyTicketq.setStartIndex(0);
	        webchatComponentVerifyTicketq.setPageSize(1);
	        List<WebchatComponentVerifyTicket> list = webchatComponentVerifyTicketDao.selectList(webchatComponentVerifyTicketq);
	        if(list!=null&&list.size()>0){
	        	WebchatComponentVerifyTicket webchatComponentVerifyTicket = list.get(0);
	        	String componentTicket = webchatComponentVerifyTicket.getComponentVerifyTicket();
	        	app.setComponentTicket(componentTicket);
	        }
	        
	        try {
	        	
	        	String tokenStrBack = JedisClient.get(app.getId());
	        	if(StringUtils.isEmpty(tokenStrBack)){
	        		Token token = WxComponentServerApi.accessToken(app);
		        	String tokenStr = JSONObject.toJSONString(token);
		        	/**
		        	 * 失效时间1小时50分钟：6600秒
		        	 */
					JedisClient.set(app.getId(), tokenStr,6600);
	        	}
			} catch (JedisException e) {
				logger.info(e.getMessage());
			}
		return app;		
	}
	
	public App getAppAddComponentTicket(){
		 App app = new App(ApiConstant.APPID,ApiConstant.SECRET);
	        WebchatComponentVerifyTicket webchatComponentVerifyTicketq = new WebchatComponentVerifyTicket();
	        webchatComponentVerifyTicketq.setOrderField("id");
	        webchatComponentVerifyTicketq.setOrderFieldType("desc");
	        webchatComponentVerifyTicketq.setStartIndex(0);
	        webchatComponentVerifyTicketq.setPageSize(1);
	        List<WebchatComponentVerifyTicket> list = webchatComponentVerifyTicketDao.selectList(webchatComponentVerifyTicketq);
	        if(list!=null&&list.size()>0){
	        	WebchatComponentVerifyTicket webchatComponentVerifyTicket = list.get(0);
	        	String componentTicket = webchatComponentVerifyTicket.getComponentVerifyTicket();
	        	app.setComponentTicket(componentTicket);
	        }	       
		return app;		
	}
	
	public void setWechatInterfaceLogService(WechatInterfaceLogService wechatInterfaceLogService) {
		this.wechatInterfaceLogService = wechatInterfaceLogService;
	}
	
}
