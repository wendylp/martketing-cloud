package cn.rongcapital.mkt.biz.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;

import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.tagsin.tutils.json.JsonUtils;
import com.tagsin.tutils.okhttp.OkHttpUtil;
import com.tagsin.tutils.okhttp.OkHttpUtil.RequestMediaType;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WXServerApiException;
import com.tagsin.wechat_sdk.WxComponentServerApi;

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Unmarshaller;
//import javax.xml.stream.XMLInputFactory;

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
import com.tagsin.wechat_sdk.token.Token;
import com.tagsin.wechat_sdk.token.TokenType;
import com.tagsin.wechat_sdk.user.UserInfo;

import cn.rongcapital.mkt.biz.ProcessReceiveMessageOfWeiXinBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.Xml2JsonUtil;
import cn.rongcapital.mkt.dao.WebchatComponentVerifyTicketDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.po.WebchatComponentVerifyTicket;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.vo.in.ComponentVerifyTicketIn;
import okhttp3.Response;

//import cn.rongcapital.mkt.vo.weixin.SubscribeVO;

@Service
public class ProcessReceiveMessageOfWeiXin extends WxMsgHandler implements ProcessReceiveMessageOfWeiXinBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WebchatComponentVerifyTicketDao webchatComponentVerifyTicketDao;

	@Autowired
	private WechatMemberDao wechatMemberDao;
	
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void getMsgLog(String textXml,String msg_signature,String timestamp,String nonce,String signature,String openid) {		
		logger.info("WebchatComponentVerifyTicketServiceImpl: 开始调试。。。。。。。。。。。。。。。。。。。。。。。" );
		App app = this.getApp();
		if(app==null){
			logger.info("app is null ******************************" );
		}
//		app.setAuthRefreshToken(authRefreshToken);
//		app.setAuthAppId(authorizer_appid);
		app.setAuthRefreshToken("refreshtoken@@@gcxmruaeql5C84jx-VHSnt99pOxbEWycsHz7tKgL-ao");
		app.setAuthAppId("wx1f363449a14a1ad8");
		logger.info("7777777777777777777777777777777" );
		
		String encodingAesKey = "abcdefghijklmnopqrstuvwxyz12345678900987654";
		String token = "ruixuemarketingcloud";				
		try {
			JAXBContext context = JAXBContext.newInstance(ComponentVerifyTicketIn.class);  
			Unmarshaller unmarshaller = context.createUnmarshaller(); 
			InputStream   textxmlis   =   new   ByteArrayInputStream(textXml.getBytes());   			
			ComponentVerifyTicketIn componentVerifyTicketIn = (ComponentVerifyTicketIn)unmarshaller.unmarshal(textxmlis);	
			String appId= componentVerifyTicketIn.getAppId();
			String encrypt = componentVerifyTicketIn.getEncrypt();

			appId ="wx00f7d56d549f82ce";			
			encrypt=textXml;			
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);			
//			String newencrypt = new String(encrypt.getBytes(), "UTF-8");  
			// 第三方收到公众号平台发送的消息
			logger.info(encrypt );
			String result2 = pc.decryptMsg(msg_signature, timestamp, nonce, encrypt);		
			logger.info("解密后明文: " + result2);			
			String webchatComponentVerifyTicketJson = Xml2JsonUtil.xml2JSON(result2);			
				JSONObject myJsonObject = JSONObject.parseObject(webchatComponentVerifyTicketJson);
				webchatComponentVerifyTicketJson = myJsonObject.get("xml").toString();
				myJsonObject = JSONObject.parseObject(webchatComponentVerifyTicketJson);
				String appIdTemp = myJsonObject.getString("CreateTime");
				/*appIdTemp:{"CreateTime":["1473071665"],"Event":["subscribe"],"ToUserName":["gh_4685d4eef135"],"FromUserName":["o0gycwIqxXT7DDkwqY-NyqmLb8Pg"],"MsgType":["event"]}*/
				appIdTemp = appIdTemp.substring(2, appIdTemp.length()-2);
				String createTime = myJsonObject.getString("CreateTime");
				createTime = createTime.substring(2, createTime.length()-2);
				String event = myJsonObject.getString("Event");
				event = event.substring(2, event.length()-2);
				String toUserName = myJsonObject.getString("ToUserName");
				toUserName = toUserName.substring(2, toUserName.length()-2);
				String fromUserName = myJsonObject.getString("FromUserName");
				fromUserName = fromUserName.substring(2, fromUserName.length()-2);
				String msgType = myJsonObject.getString("MsgType");
				msgType = msgType.substring(2, msgType.length()-2);				

				WechatMember wechatMemberTemp = new WechatMember();
				wechatMemberTemp.setWxCode(openid);				
				List<WechatMember> wechatMemberTemps =  wechatMemberDao.selectList(wechatMemberTemp);
				logger.info("88888888888888888888888888888888888888888888" );
				if(wechatMemberTemps!=null&&wechatMemberTemps.size()>0){
					WechatMember wechatMemberBack = wechatMemberTemps.get(0);
					if(event.equals("subscribe")){
						wechatMemberBack.setSubscribeYn("Y");
					}else{
						wechatMemberBack.setSubscribeYn("N");
					}
					wechatMemberDao.updateById(wechatMemberBack);
					logger.info("9999999999999999999999999999999999999999999999" );
				}else{
					logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
					UserInfo userInfo = WxComponentServerApi.getUserInfo(app,openid);//如果openid出错，sdk会直接抛出异常
					if(userInfo==null){
						logger.info("userInfo is null ******************************" );
					}else{
						logger.info("userInfo is not null ******************************" );
					}

					WechatMember wechatMember = new WechatMember();
					// subscribe 无对应
					// openid
					wechatMember.setWxCode(openid);
					wechatMember.setNickname(userInfo.getNickname());
					wechatMember.setSex(userInfo.getSex());
					wechatMember.setCity(userInfo.getCity());
					wechatMember.setCountry(userInfo.getCountry());
					wechatMember.setProvince(userInfo.getProvince());
					// language
					wechatMember.setHeadImageUrl(userInfo.getHeadimgurl());
					//关注时间
					wechatMember.setSubscribeTime(userInfo.getSubscribe_time());
					// unionid
					wechatMember.setRemark(userInfo.getRemark());
					wechatMember.setSubscribeYn("Y");
					wechatMember.setSelected(int2OneByte(0));
					logger.info("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" );
					wechatMemberDao.insert(wechatMember);
					logger.info("cccccccccccccccccccccccccccccccccccccccccccccccccc" );
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.getMessage(),e);
		}
		
			
	}

	public App getApp(){
		 App app = new App(ApiConstant.APPID,ApiConstant.SECRET);
	        WebchatComponentVerifyTicket webchatComponentVerifyTicketq = new WebchatComponentVerifyTicket();
	        webchatComponentVerifyTicketq.setOrderField("id");
	        webchatComponentVerifyTicketq.setOrderFieldType("desc");
	        webchatComponentVerifyTicketq.setStartIndex(0);
	        webchatComponentVerifyTicketq.setPageSize(1);
	        logger.info("00000000000000000000000000000000000000" );
	        List<WebchatComponentVerifyTicket> list = webchatComponentVerifyTicketDao.selectList(webchatComponentVerifyTicketq);
	        logger.info("1111111111111111111111111111111111111" );
	        if(list!=null&&list.size()>0){
	        	logger.info("**************************" +list.size());
	        	WebchatComponentVerifyTicket webchatComponentVerifyTicket = list.get(0);
	        	logger.info("22222222222222222222222222");
	        	String componentTicket = webchatComponentVerifyTicket.getComponentVerifyTicket();
	        	logger.info("*************************componentTicket:"+componentTicket+"componentTicketId:"+webchatComponentVerifyTicket.getId());
	        	app.setComponentTicket(componentTicket);	        	
	        }
	        logger.info("44444444444444444444444444444444444444444");
	        try {
//				WxComponentServerApi.accessToken(app);
	        	this.accessToken(app);
			} catch (Exception e) {
				logger.info("66666666666666666666666666666666666666666");
				logger.info(e.getMessage(),e);
				logger.error("Failed to format {}",e.getMessage(), e);
				logger.info("77777777777777777777777777777777777777777");
				e.printStackTrace();
			}
	        logger.info("5555555555555555555555555555555555555");
		return app;		
	}

	
	public static byte int2OneByte(int num) {  
        return (byte) (num & 0x000000ff);  
    }
	
	/**
	 * 获取ComponentAccessToken
	 * @param app
	 * @return
	 */
	public Token accessToken(App app){
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("component_appid", app.getId());
			params.put("component_appsecret", app.getSecret());
			params.put("component_verify_ticket", app.getComponentTicket());
			Response response = OkHttpUtil.requestByPost(url, RequestMediaType.JSON, JsonUtils.toJson(params));
			if(response.code() == 200){
				
				String bodystr = response.body().string();
				logger.info(bodystr);
				ObjectNode jsonObj = JsonUtils.readJsonObject(bodystr);
//				System.out.println("component_access_token:"+jsonObj.toString());
				logger.info("component_access_token:"+jsonObj.toString());
				String access_token = jsonObj.get("component_access_token").getTextValue();
				long expires_in = jsonObj.get("expires_in").getLongValue();
				return new Token(TokenType.COMPONENT_ACCESS_TOKEN, access_token, System.currentTimeMillis() + (expires_in-60) * 1000);
			}else{
				throw new WXServerApiException("Invalid statu code : " + response.code() + " , url : " + url);
			}
		} catch (Exception e) {
			throw new WXServerApiException(e);
		}
	}

}
