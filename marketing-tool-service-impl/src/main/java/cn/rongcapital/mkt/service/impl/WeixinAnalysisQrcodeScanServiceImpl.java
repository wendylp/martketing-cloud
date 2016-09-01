/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeScanDao;
import cn.rongcapital.mkt.po.WechatQrcodeScan;
import cn.rongcapital.mkt.service.WeixinAnalysisQrcodeScanService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
@Service
public class WeixinAnalysisQrcodeScanServiceImpl implements WeixinAnalysisQrcodeScanService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	WechatQrcodeScanDao wechatQrcodeScanDao;

	/**
	 * 保存扫描微信二维码次数和人数 接口：mkt.weixin.analysis.qrcode.scan
	 * 
	 * @author shuiyangyang
	 * @date 2016.09.01
	 */
	@Override
	public BaseOutput instertToWechatQrcodeScan(String userId, String userHost, String qrcodeId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		WechatQrcodeScan wechatQrcodeScan = new WechatQrcodeScan();

		wechatQrcodeScan.setUserId(userId);
		wechatQrcodeScan.setUserHost(userHost);
		wechatQrcodeScan.setQrcodeId(Integer.valueOf(qrcodeId));
		wechatQrcodeScan.setCreateTime(new Date());

		int num = wechatQrcodeScanDao.insert(wechatQrcodeScan);

		if (num != 1) {
			result.setCode(ApiErrorCode.DB_ERROR.getCode());
			result.setMsg("插入数据失败");
			logger.info("插入数据失败userId = {}, userHost = {}, qrcodeId = {}", userId, userHost, qrcodeId);
		}
		return result;
	}

}
