package cn.rongcapital.mkt.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.WebchatComponentVerifyTicketDao;
import cn.rongcapital.mkt.po.WebchatComponentVerifyTicket;

@Service
public class BaseBiz {

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
	        WxComponentServerApi.accessToken(app);
		return app;		
	}
	
}
