package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface WeixinQrcodeListService {
	
	/**
	 * 根据公众号名称、失效时间、状态、二维码名称查询二维码列表 
	 * 接口：mkt.weixin.qrcode.list
	 * @param wxmpName
	 * @param expirationTime
	 * @param qrcodeStatus
	 * @return
	 * @author shuiyangyang
	 */
	BaseOutput getWeixinQrcodeList(String wxmpName, Integer expirationTime, Byte qrcodeStatus);
	
	/**
	 * 根据输入的二维码名称模糊查询表wechat_qrcode
	 * 接口：mkt.weixin.qrcode.list.qrname
	 * @param qrcodeName
	 * @return
	 * @author shuiyangyang
	 */
	BaseOutput getWeixinQrcodeListQrname(String qrcodeName);
}
