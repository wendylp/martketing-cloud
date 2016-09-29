package cn.rongcapital.mkt.biz.impl;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;
import com.tagsin.wechat_sdk.token.Token;
import com.tagsin.wechat_sdk.vo.AuthInfo;

import cn.rongcapital.mkt.biz.WechatPublicAuthBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.dao.WebchatAuthInfoDao;
import cn.rongcapital.mkt.po.WebchatAuthInfo;
import cn.rongcapital.mkt.po.WechatInterfaceLog;
import cn.rongcapital.mkt.service.MQTopicService;

import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.PublicAuthOut;

@Service
public class WechatPublicAuthBizImpl extends BaseBiz implements WechatPublicAuthBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WebchatAuthInfoDao webchatAuthInfoDao;
	@Autowired
	private MQTopicService mqTopicService;
	
	public static final String GET_PUB_LIST ="GetPubList";
	
	public static final String GET_PUB_FANS_LIST="GetPubFansList";
	
	public static final String GET_IMG_TEXT_ASSET="GetImgtextAsset";
	
	@Override	
	public BaseOutput authWechatPublicAccount() throws JedisException {		
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
		PublicAuthOut publicAuthOut = new PublicAuthOut();
		App app = this.getApp();
		/**
		 * 保证预授权的TOKEN是最新的
		 */
		Token token = WxComponentServerApi.accessToken(app);
    	String tokenStr = JSONObject.toJSONString(token);
    	/**
    	 * 失效时间1小时50分钟：6600秒
    	 */
		JedisClient.set(app.getId(), tokenStr,6600);
		String pre_auth_code = WxComponentServerApi.getPreAuthCode(app);

    	/**
    	 * 记入接口日志到数据库
    	 */
		WechatInterfaceLog wechatInterfaceLog = new WechatInterfaceLog("WechatPublicAuthBizImpl","authWechatPublicAccount",pre_auth_code,new Date());
		wechatInterfaceLogService.insert(wechatInterfaceLog);
		/**
		 * 组装授权二维码页面
		 */
        String component_appid = app.getId();
        StringBuffer sbUrl = new StringBuffer(ApiConstant.WEIXIN_AUTH_COMPONENT_LOGIN_PAGE);
        sbUrl.append("component_appid=").append(component_appid);
        sbUrl.append("&pre_auth_code=").append(pre_auth_code);
        sbUrl.append("&redirect_uri=").append(ApiConstant.WEIXIN_AUTH_CALLBACK_URI);
        logger.info(sbUrl.toString());	
        publicAuthOut.setUrl(sbUrl.toString());
        baseOutput.getData().add(publicAuthOut);
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
		return baseOutput;
	}
	
	@Override
	public BaseOutput authWechatPublicCodeAccount(String authorizationCode) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
		App app = this.getApp();
		AuthInfo authInfo = WxComponentServerApi.queryAuthByAuthCode(app, authorizationCode);
    	/**
    	 * 记入接口日志到数据库
    	 */
		WechatInterfaceLog wechatInterfaceLog = new WechatInterfaceLog("WechatPublicAuthBizImpl","authWechatPublicCodeAccount",authInfoToString(authInfo),new Date());
		wechatInterfaceLogService.insert(wechatInterfaceLog);
		/**
		 * 记入授权公众号到数据库
		 */
		String authorizer_appid = authInfo.getAuthorizer_appid();
		String authorizer_access_token = authInfo.getAuthorizer_access_token();
		String authorizer_refresh_token = authInfo.getAuthorizer_refresh_token();
		String expires_in = authInfo.getExpires_in();
		
		WebchatAuthInfo webchatAuthInfo = new WebchatAuthInfo();
		webchatAuthInfo.setAuthorizerAppid(authorizer_appid);
		List<WebchatAuthInfo> webchatAuthInfoes = webchatAuthInfoDao.selectList(webchatAuthInfo);
		if(CollectionUtils.isNotEmpty(webchatAuthInfoes)){
			WebchatAuthInfo webchatAuthInfoBack = webchatAuthInfoes.get(0);
			webchatAuthInfoBack.setAuthorizerAccessToken(authorizer_access_token);
			webchatAuthInfoBack.setAuthorizerRefreshToken(authorizer_refresh_token);
			webchatAuthInfoBack.setExpiresIn(expires_in);
			webchatAuthInfoDao.updateById(webchatAuthInfoBack);
		}else{
			webchatAuthInfo.setAuthorizerAccessToken(authorizer_access_token);
			webchatAuthInfo.setAuthorizerRefreshToken(authorizer_refresh_token);
			webchatAuthInfo.setExpiresIn(expires_in);
			webchatAuthInfoDao.insert(webchatAuthInfo);
		}		
		baseOutput.getData().add(webchatAuthInfo);
		baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
		/**
		 * 授权之后启动job同步 公众号基本信息，标签集合、粉丝集合，图文集合
		 */
		try {
			this.startJob();
		} catch (JMSException e) {
			baseOutput.setCode(ApiErrorCode.BIZ_ERROR.getCode());
	        baseOutput.setMsg(e.getMessage());
		}
		return baseOutput;
	}
	
	/**
	 * @throws JMSException 
	 * 授权之后启动job同步 公众号基本信息，标签集合、粉丝集合，图文集合
	 */
	public void startJob() throws JMSException{
        ActiveMqMessageVO message1 = new ActiveMqMessageVO();
        message1.setTaskName("同步微信公众号基本信息和标签任务");
        message1.setServiceName("GetH5PubListServiceImpl");
        mqTopicService.senderMessage(GET_PUB_LIST, message1);
        
        ActiveMqMessageVO message2 = new ActiveMqMessageVO();
        message2.setTaskName("同步微信公众号下的粉丝列表任务");
        message2.setServiceName("GetPubFansListServiceImpl");
        mqTopicService.senderMessage(GET_PUB_FANS_LIST, message2);
        
        ActiveMqMessageVO message3 = new ActiveMqMessageVO();
        message3.setTaskName("同步微信公众号下图文信息任务");
        message3.setServiceName("ImgtextAssetSyncServiceImpl");
        mqTopicService.senderMessage(GET_IMG_TEXT_ASSET, message3);

	}
	
	
	@Override
	public Boolean isPubIdGranted(String authAppid) {
		Boolean isGranted = false;
		WebchatAuthInfo webchatAuthInfo = new WebchatAuthInfo();
		webchatAuthInfo.setAuthorizerAppid(authAppid);
		List<WebchatAuthInfo> webchatAuthInfos = webchatAuthInfoDao.selectList(webchatAuthInfo);		
		if(webchatAuthInfos!=null&&webchatAuthInfos.size()>0){
			WebchatAuthInfo webchatAuthInfoTemp = webchatAuthInfos.get(0);
			if(webchatAuthInfoTemp!=null){
				App app = this.getApp();
				try {
					WxComponentServerApi.getAuthAccessToken(app, webchatAuthInfoTemp.getAuthorizerAppid(), webchatAuthInfoTemp.getAuthorizerRefreshToken());
					isGranted = true;
				} catch (Exception e) {
					logger.info("公众号"+webchatAuthInfoTemp.getAuthorizerAppid()+"已经取消了授权");					
				}
			}			
		}		
		return isGranted;
	}
	
	private String authInfoToString(AuthInfo authInfo){		
		if(authInfo!=null){
			String authInfoString = JSONObject.toJSONString(authInfo);
			return authInfoString;
		}
		return null;
	}

}
