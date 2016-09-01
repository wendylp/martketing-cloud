package cn.rongcapital.mkt.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tagsin.wechat_sdk.App;
import com.tagsin.wechat_sdk.WxComponentServerApi;

import cn.rongcapital.mkt.biz.WechatGroupBiz;
import cn.rongcapital.mkt.po.WechatGroup;
import cn.rongcapital.mkt.vo.weixin.WXTag;

@Service
public class WechatGroupBizImpl extends BaseBiz implements WechatGroupBiz {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.rongcapital.mkt.biz.WechatGroupBiz#getTags(java.lang.String) \
	 * 获取公众号的标签
	 * {
		    "tags": [
		        {
		            "id": 2,
		            "name": "星标组",
		            "count": 1
		        },
		        {
		            "id": 100,
		            "name": "王华初测试",
		            "count": 1
		        },
		        {
		            "id": 101,
		            "name": "闫俊测试组",
		            "count": 0
		        },
		        {
		            "id": 103,
		            "name": "开发测试组",
		            "count": 1
		        },
		        {
		            "id": 104,
		            "name": "不明粉丝",
		            "count": 1
		        }
		    ]
		}
	 */
	@Override
	public List<WechatGroup> getTags(String authAppId,String authorizer_refresh_token) {
		List<WechatGroup> list = new ArrayList<WechatGroup>();
		App app = this.getApp();
		app.setAuthAppId(authAppId);
		app.setAuthRefreshToken(authorizer_refresh_token);
		String tagsString = WxComponentServerApi.getBaseWxSdk().getTags(app);		
		list = this.getTagsFromTagsString(authAppId,tagsString);
		return list;
	}

	public List<WechatGroup> getTagsFromTagsString(String authAppId,String tagsString) {
		List<WechatGroup> list = new ArrayList<WechatGroup>();
		JSONObject jsonObject =  JSONObject.parseObject(tagsString);
		JSONArray jsonArray = jsonObject.getJSONArray("tags");
		List<WXTag> wxTags = JSONArray.parseArray(jsonArray.toJSONString(), WXTag.class);
		if(wxTags!=null&&wxTags.size()>0){
			for(Iterator<WXTag> iter = wxTags.iterator();iter.hasNext();){
				WXTag wxTag = iter.next();
				if(wxTag!=null){
					WechatGroup wechatGroup = new WechatGroup();
					wechatGroup.setGroupName(wxTag.getTagName());
					wechatGroup.setGroupId(String.valueOf(wxTag.getTagId()));
					wechatGroup.setWxAcct(authAppId);
					wechatGroup.setCreateTime(new Date());
//					BeanUtils.copyProperties(wxTag, wechatGroup);
					list.add(wechatGroup);
				}
				
			}
		}
		return list;		
	}
	
}
