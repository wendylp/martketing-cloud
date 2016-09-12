package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * 粉丝取消关注
 * @author lijinkai
 * @version 1.2  2016-9-9
 *
 */
public interface WechatAssetService {
	/**
	 * 取消关注公众号
	 * @param wxCode 粉丝Code
	 * @param pubId 公众号ID
	 * @return
	 */
	public BaseOutput unfollow(String wxCode, String pubId);
	
}
