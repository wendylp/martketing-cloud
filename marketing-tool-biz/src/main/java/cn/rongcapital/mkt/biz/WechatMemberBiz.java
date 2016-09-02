package cn.rongcapital.mkt.biz;

import java.util.List;

//import com.tagsin.wechat_sdk.App;

import cn.rongcapital.mkt.po.WechatMember;

public interface WechatMemberBiz {

	public List<WechatMember> getUserList(String authorizer_appid, String authRefreshToken);
}
