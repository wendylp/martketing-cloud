package cn.rongcapital.mkt.biz.impl;

import java.util.List;

import com.tagsin.wechat_sdk.msg.WxMsgHandler;
import com.tagsin.wechat_sdk.msg.in.EventMsg;
import com.tagsin.wechat_sdk.msg.in.MediaMsgs.ImageMsg;
import com.tagsin.wechat_sdk.msg.in.MediaMsgs.ShortVideoMsg;
import com.tagsin.wechat_sdk.msg.in.MediaMsgs.VideoMsg;
import com.tagsin.wechat_sdk.msg.in.MediaMsgs.VoiceMsg;
import com.tagsin.wechat_sdk.msg.in.SimpleMsgs.LinkMsg;
import com.tagsin.wechat_sdk.msg.in.SimpleMsgs.LocationMsg;
import com.tagsin.wechat_sdk.msg.in.SimpleMsgs.TextMsg;
import com.tagsin.wechat_sdk.msg.in.WxMsg;
import com.tagsin.wechat_sdk.msg.out.News;

public class ProcessReceiveMessageOfWeiXin extends WxMsgHandler {

	@Override
	public void enableMsgEncopt(String aesKey) {
		
		super.enableMsgEncopt(aesKey);
	}

	@Override
	public void disableMsgEncopt() {
		// TODO Auto-generated method stub
		super.disableMsgEncopt();
	}

	@Override
	public String process(byte[] requestBody) {
		// TODO Auto-generated method stub
		return super.process(requestBody);
	}

	@Override
	protected String createReply(TextMsg msg) {
		// TODO Auto-generated method stub
		return super.createReply(msg);
	}

	@Override
	protected String createReply(ImageMsg msg) {
		// TODO Auto-generated method stub
		return super.createReply(msg);
	}

	@Override
	protected String createReply(VoiceMsg msg) {
		// TODO Auto-generated method stub
		return super.createReply(msg);
	}

	@Override
	protected String createNewsReply(WxMsg msg, List<News> newsItems) {
		// TODO Auto-generated method stub
		return super.createNewsReply(msg, newsItems);
	}

	@Override
	protected String processMsg(TextMsg msg) throws Exception {
		// TODO Auto-generated method stub
		return super.processMsg(msg);
	}

	@Override
	protected String processMsg(VideoMsg msg) throws Exception {
		// TODO Auto-generated method stub
		return super.processMsg(msg);
	}

	@Override
	protected String processMsg(LinkMsg msg) throws Exception {
		// TODO Auto-generated method stub
		return super.processMsg(msg);
	}

	@Override
	protected String processMsg(LocationMsg msg) throws Exception {
		// TODO Auto-generated method stub
		return super.processMsg(msg);
	}

	@Override
	protected String processMsg(ImageMsg msg) throws Exception {
		// TODO Auto-generated method stub
		return super.processMsg(msg);
	}

	@Override
	protected String processMsg(ShortVideoMsg msg) throws Exception {
		// TODO Auto-generated method stub
		return super.processMsg(msg);
	}

	@Override
	protected String processMsg(VoiceMsg msg) throws Exception {
		// TODO Auto-generated method stub
		return super.processMsg(msg);
	}

	@Override
	protected String onSubscribe(EventMsg event) throws Exception {
		
		return "";
//		return super.onSubscribe(event);
	}

	@Override
	protected String onUnsubscribe(EventMsg event) throws Exception {
		// TODO Auto-generated method stub
		return super.onUnsubscribe(event);
	}

	@Override
	protected String onScanSubscribe(EventMsg event, long qrscene, String ticket) throws Exception {
		// TODO Auto-generated method stub
		return ""; 
	}

	@Override
	protected String onFollowerScan(EventMsg event, String qrScene, String ticket) throws Exception {
		// TODO Auto-generated method stub
		return super.onFollowerScan(event, qrScene, ticket);
	}

	@Override
	protected String onLocationReport(EventMsg event, String latitude, String longitude, String precision)
			throws Exception {
		// TODO Auto-generated method stub
		return super.onLocationReport(event, latitude, longitude, precision);
	}

	@Override
	protected String onMenuClick(EventMsg event, String menuKey) throws Exception {
		// TODO Auto-generated method stub
		return super.onMenuClick(event, menuKey);
	}

	@Override
	protected String onMenuLinkClick(EventMsg event, String url) throws Exception {
		// TODO Auto-generated method stub
		return super.onMenuLinkClick(event, url);
	}

	
	
}
