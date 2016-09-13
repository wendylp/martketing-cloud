package cn.rongcapital.mkt.biz.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagsin.tutils.http.HttpResult;
import com.tagsin.tutils.http.Requester;
import com.tagsin.tutils.http.Requester.Method;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WXServerApi;
import com.tagsin.wechat_sdk.WxComponentServerApi;
import com.tagsin.wechat_sdk.token.Token;
import com.tagsin.wechat_sdk.token.TokenType;
import com.tagsin.wechat_sdk.vo.AuthInfo;

import cn.rongcapital.mkt.biz.WechatPublicAuthBiz;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WebchatAuthInfoDao;
import cn.rongcapital.mkt.dao.WebchatComponentVerifyTicketDao;
import cn.rongcapital.mkt.po.WebchatAuthInfo;
import cn.rongcapital.mkt.po.WebchatComponentVerifyTicket;
import cn.rongcapital.mkt.service.WechatPublicAuthService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.PublicAuthOut;

@Service
public class WechatPublicAuthBizImpl extends BaseBiz implements WechatPublicAuthBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WechatPublicAuthService wechatPublicAuthService;
	
	@Autowired
	private WebchatComponentVerifyTicketDao webchatComponentVerifyTicketDao;
	@Autowired
	private WebchatAuthInfoDao webchatAuthInfoDao;
	
	private String get_access_token_url="https://api.weixin.qq.com/sns/oauth2/access_token?" +
	        "appid=APPID" +
	        "&secret=SECRET&" +
	        "code=CODE&grant_type=authorization_code";
	private String get_userinfo="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";


	
	
	@Override	
	public BaseOutput authWechatPublicAccount() {		
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
		PublicAuthOut publicAuthOut = new PublicAuthOut();
		App app = this.getApp();
    	String pre_auth_code = WxComponentServerApi.getPreAuthCode(app);
        String component_appid = app.getId();
        String redirect_uri = "http://mktsrv.rc.dataengine.com/api?method=mkt.data.inbound.wechat.public.auth.code.callback";
        String url = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid="+component_appid+"&pre_auth_code="+pre_auth_code+"&redirect_uri="+redirect_uri;
        logger.info(url);	
        publicAuthOut.setUrl(url);
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
		String authorizer_appid = authInfo.getAuthorizer_appid();
		String authorizer_access_token = authInfo.getAuthorizer_access_token();
		String authorizer_refresh_token = authInfo.getAuthorizer_refresh_token();
		String expires_in = authInfo.getExpires_in();
		WebchatAuthInfo webchatAuthInfo = new WebchatAuthInfo();
		webchatAuthInfo.setAuthorizerAccessToken(authorizer_access_token);
		webchatAuthInfo.setAuthorizerAppid(authorizer_appid);
		webchatAuthInfo.setAuthorizerRefreshToken(authorizer_refresh_token);
		webchatAuthInfo.setExpiresIn(expires_in);
		webchatAuthInfoDao.insert(webchatAuthInfo);
		logger.info("authorizer_appid:"+authorizer_appid+"authorizer_access_token:"+authorizer_access_token+"authorizer_refresh_token:"+authorizer_refresh_token);
		baseOutput.getData().add(webchatAuthInfo);
		baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
		return baseOutput;
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

}
