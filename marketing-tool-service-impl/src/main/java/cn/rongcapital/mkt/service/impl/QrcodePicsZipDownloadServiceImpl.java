package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.QrcodePicsZipDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * 下载批量生成的二维码图片文件(zip)
 * Created by zhaoguoying on 2016-08-12.
 */
@Service
public class QrcodePicsZipDownloadServiceImpl implements QrcodePicsZipDownloadService {

	@Autowired
	WechatQrcodeDao wechatQrcodeDao;
	
	@Override
	public BaseOutput getQrcodePicsZipDownload(Integer batchId) {
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setBatchId(batchId);
		
		List<WechatQrcode> wechatQrcodes = wechatQrcodeDao.selectList(wechatQrcode);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		if (CollectionUtils.isNotEmpty(wechatQrcodes)) {
			result.setTotal(wechatQrcodes.size());
			
			for (WechatQrcode w : wechatQrcodes) {
				Map<String, Object> wechatQrcodetMap = new HashMap<String, Object>();
				wechatQrcodetMap.put("qrcode_pic", w.getQrcodePic());
				wechatQrcodetMap.put("qrcode_url", w.getQrcodeUrl());
				result.getData().add(wechatQrcodetMap);
			}
		}
		return result;
	}

}
