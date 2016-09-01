package cn.rongcapital.mkt.biz.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;

import cn.rongcapital.mkt.biz.WechatRegisterBiz;
import cn.rongcapital.mkt.po.WechatRegister;

@Service
public class WechatRegisterBizImpl extends BaseBiz implements WechatRegisterBiz  {

	/* (non-Javadoc)
	 * @see cn.rongcapital.mkt.biz.WechatRegisterBiz#getAuthInfo(com.tagsin.wechat_sdk.App, java.lang.String)
	 * authInfoString 的格式
	 * {
		"authorizer_info": {
		"nick_name": "微信SDK Demo Special", 
		"head_img": "http://wx.qlogo.cn/mmopen/GPyw0pGicibl5Eda4GmSSbTguhjg9LZjumHmVjybjiaQXnE9XrXEts6ny9Uv4Fk6hOScWRDibq1fI0WOkSaAjaecNTict3n6EjJaC/0", 
		"service_type_info": { "id": 2 }, 
		"verify_type_info": { "id": 0 },
		"user_name":"gh_eb5e3a772040",
		"business_info": {"open_store": 0, "open_scan": 0, "open_pay": 0, "open_card": 0, "open_shake": 0},
		"alias":"paytest01"
		}, 
		"qrcode_url":"URL",    
		"authorization_info": {
		"appid": "wxf8b4f85f3a794e77", 
		"func_info": [
		{ "funcscope_category": { "id": 1 } }, 
		{ "funcscope_category": { "id": 2 } }, 
		{ "funcscope_category": { "id": 3 } }
		]
		}
		}*
	 */
	@Override
	public WechatRegister getAuthInfo(App app, String authAppId) {
		String authInfoString =  WxComponentServerApi.getAuthInfo(app,authAppId);
		WechatRegister  wechatRegister =  this.getWechatRegisterFromAuthInfo(authInfoString);		
		return wechatRegister;
	}

	/**
	 * @param authInfoString
	 * {
		    "authorizer_info": {
		        "nick_name": "闷骚恒恒",
		        "service_type_info": {
		            "id": 1
		        },
		        "verify_type_info": {
		            "id": -1
		        },
		        "user_name": "gh_0752ccda639c",
		        "alias": "",
		        "qrcode_url": "http://mmbiz.qpic.cn/mmbiz_jpg/Us3ouRjaS79cVluzgKaeRcibQYwNyVB0QbdxibTWZcHRtFuFUYX9At5y0mhlYRw2JwFeUicFftw4e1UhicW9ONUjag/0",
		        "business_info": {
		            "open_pay": 0,
		            "open_shake": 0,
		            "open_scan": 0,
		            "open_card": 0,
		            "open_store": 0
		        },
		        "idc": 1
		    },
		    "authorization_info": {
		        "authorizer_appid": "wxa5fb7dea54673299",
		        "func_info": [
		            {
		                "funcscope_category": {
		                    "id": 1
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 15
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 4
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 7
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 2
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 3
		                }
		            },
		            {
		                "funcscope_category": {
		                    "id": 11
		                }
		            }
		        ]
		    }
		}
	 * @return
	 */
	public WechatRegister getWechatRegisterFromAuthInfo(String authInfoString){
		WechatRegister  wechatRegister = new WechatRegister();
		JSONObject jsonObject = JSON.parseObject(authInfoString);
		JSONObject authorizerInfo = jsonObject.getJSONObject("authorizer_info");
		String nick_name = authorizerInfo.getString("nick_name");
		String user_name = authorizerInfo.getString("user_name");
		wechatRegister.setName(user_name);
		wechatRegister.setNickname(nick_name);
		return wechatRegister;
		
	}
	
}
