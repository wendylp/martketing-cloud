package cn.rongcapital.mkt.biz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
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
import cn.rongcapital.mkt.po.WechatInterfaceLog;
import cn.rongcapital.mkt.service.WechatPublicAuthService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.PublicAuthOut;

@Service
public class WechatPublicAuthBizImpl extends BaseBiz implements WechatPublicAuthBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WebchatAuthInfoDao webchatAuthInfoDao;
	
	@Override	
	public BaseOutput authWechatPublicAccount() {		
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
		PublicAuthOut publicAuthOut = new PublicAuthOut();
		App app = this.getApp();
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
		webchatAuthInfo.setAuthorizerAccessToken(authorizer_access_token);
		webchatAuthInfo.setAuthorizerAppid(authorizer_appid);
		webchatAuthInfo.setAuthorizerRefreshToken(authorizer_refresh_token);
		webchatAuthInfo.setExpiresIn(expires_in);
		webchatAuthInfoDao.insert(webchatAuthInfo);
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
	
	private String authInfoToString(AuthInfo authInfo){		
		if(authInfo!=null){
			String authInfoString = JSONObject.toJSONString(authInfo);
			return authInfoString;
		}
		return null;
	}

}
