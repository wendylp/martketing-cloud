package cn.rongcapital.mkt.biz;

import com.tagsin.wechat_sdk.App;

import cn.rongcapital.mkt.po.WechatRegister;

public interface WechatRegisterBiz {

	public WechatRegister getAuthInfo(App app,String authAppId);
}
