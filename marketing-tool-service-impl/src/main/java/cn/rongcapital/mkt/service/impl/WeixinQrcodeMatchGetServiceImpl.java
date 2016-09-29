/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.WeixinQrcodeMatchGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 * @Date 2016.08.25
 */
@Service
public class WeixinQrcodeMatchGetServiceImpl implements WeixinQrcodeMatchGetService {

	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;

	/**
	 * 精确查询微信二维码名称是否存在 接口：mkt.weixin.qrcode.match.get
	 * 
	 * @author shuiyangyang
	 * @Date 2016.08.25
	 */
	@Override
	public BaseOutput weixinQrcodeMatchGet(String qrcodeName, String channelId, String wxName) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ONE, null);

		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setWxName(wxName);
		wechatQrcode.setChCode(Integer.parseInt(channelId));
		wechatQrcode.setQrcodeName(qrcodeName);

		List<WechatQrcode> wechatQrcodeLists = wechatQrcodeDao.selectList(wechatQrcode);

		Map<String, Object> map = new HashMap<String, Object>();

		if (wechatQrcodeLists == null || wechatQrcodeLists.isEmpty()) {
			map.put("is_match", 0);
			map.put("id", "");
		} else {
			map.put("is_match", 1);
			map.put("id", wechatQrcodeLists.get(0).getId());
		}

		result.getData().add(map);

		return result;
	}

}
