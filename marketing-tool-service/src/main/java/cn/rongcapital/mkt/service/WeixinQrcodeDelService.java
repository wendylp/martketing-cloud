/**
 * 
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 * @Date 2016.08.25
 */
public interface WeixinQrcodeDelService {
	/**
	 * 删除二维码接口 （逻辑删除，状态改为2）
	 * 接口：mkt.weixin.qrcode.del
	 * 
	 * @param qrcodeId
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	BaseOutput weixinQrocdeDel(String qrcodeId);

	/**
	 * 删除二维码接口 （微信记录物理删除）
	 * 接口：mkt.weixin.qrcode.records.del
	 * 
	 * @param qrcodeId
	 * @return
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	BaseOutput weixinQrcodeRecordsDel(String qrcodeId);
	
	
}
