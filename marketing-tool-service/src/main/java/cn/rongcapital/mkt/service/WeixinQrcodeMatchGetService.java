/**
 * 
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
public interface WeixinQrcodeMatchGetService {
	/**
	 * 精确查询微信二维码名称是否存在
	 * 接口：mkt.weixin.qrcode.match.get
	 * 
	 * @param wxAcct
	 * @param wxName
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	BaseOutput weixinQrcodeMatchGet(String wxAcct, String wxName);
}
