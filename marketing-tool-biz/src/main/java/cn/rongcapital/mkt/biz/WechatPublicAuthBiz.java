package cn.rongcapital.mkt.biz;

import com.tagsin.wechat_sdk.App;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface WechatPublicAuthBiz {

	public BaseOutput authWechatPublicAccount();
	
	public BaseOutput authWechatPublicCodeAccount(String authorizationCode);
	
	public Boolean isPubIdGranted(String authAppid);
	
}
