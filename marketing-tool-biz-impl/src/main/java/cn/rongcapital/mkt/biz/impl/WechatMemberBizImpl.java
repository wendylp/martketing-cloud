package cn.rongcapital.mkt.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;
import com.tagsin.wechat_sdk.user.UserInfo;

import cn.rongcapital.mkt.biz.WechatMemberBiz;
import cn.rongcapital.mkt.po.WechatMember;

@Service
public class WechatMemberBizImpl extends BaseBiz implements WechatMemberBiz {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<WechatMember> getUserList(String authorizer_appid, String authRefreshToken) {
		
		// 获取并设置app
		App app = getApp();
		app.setAuthRefreshToken(authRefreshToken);
		app.setAuthAppId(authorizer_appid);
		
		
		String nextOpenId="";//此出应该设置为空
		String userString = WxComponentServerApi.getBaseWxSdk().getUserList(app,nextOpenId);
		logger.debug("根据app获取的粉丝列表内容为：{}", userString);
		JSONObject userJson = JSON.parseObject(userString);
		
		List<String> openidLists = new ArrayList<String>();
		if(userJson.getIntValue("count") > 0 ) {
			JSONObject userDataJson = userJson.getJSONObject("data");
			String openidString = userDataJson.getString("openid");
			openidLists = JSONArray.parseArray(openidString, String.class);
		}
		
		// 如果粉丝大于1000 继续获取粉丝
		while(userJson.getIntValue("count") >= 1000) {
			nextOpenId = userJson.getString("next_openid");
			userString = WxComponentServerApi.getBaseWxSdk().getUserList(app,nextOpenId);
			logger.debug("根据app获取的粉丝列表内容为：{}", userString);
			userJson = JSON.parseObject(userString);
			
			if(userJson.getIntValue("count") > 0 ) {
				JSONObject userDataJson = userJson.getJSONObject("data");
				String openidString = userDataJson.getString("openid");
				openidLists.addAll(JSONArray.parseArray(openidString, String.class));
			}
		}
		
		logger.debug("获取到的粉丝数量为 ： {} 条", openidLists.size());
		
		// 根据粉丝的openid获取粉丝信息
		if(openidLists == null || openidLists.size() <= 0) {
			return null;
		}
		
		List<WechatMember> wechatMemberLists = new ArrayList<WechatMember>();
		// 根据粉丝的openid获取粉丝信息
		for(String openid : openidLists) {
			UserInfo userInfo = WxComponentServerApi.getUserInfo(app,openid);//如果openid出错，sdk会直接抛出异常
			WechatMember wechatMember = new WechatMember();
			// subscribe 无对应
			// openid
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
			// groupid
			// privilege
			// tagid_list
			
			wechatMemberLists.add(wechatMember);
		}
		
		logger.debug("获取的粉丝信息共：{} 条", wechatMemberLists.size());
		
		return wechatMemberLists;
	}
	

}
