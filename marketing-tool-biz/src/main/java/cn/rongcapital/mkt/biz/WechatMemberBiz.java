package cn.rongcapital.mkt.biz;

import java.util.List;

//import com.tagsin.wechat_sdk.App;

import cn.rongcapital.mkt.po.WechatMember;

public interface WechatMemberBiz {

	/**
	 * 获取公众号下的粉丝列表：1：获取粉丝openID集合，2：轮询获取粉丝详细信息
	 * @param authorizer_appid
	 * @param authRefreshToken
	 * @return
	 */
	public List<WechatMember> getUserList(String authorizer_appid, String authRefreshToken);

}
