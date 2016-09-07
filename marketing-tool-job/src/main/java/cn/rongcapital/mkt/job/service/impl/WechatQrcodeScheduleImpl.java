package cn.rongcapital.mkt.job.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.biz.WechatQrcodeBiz;
import cn.rongcapital.mkt.job.service.base.TaskService;

@Service
public class WechatQrcodeScheduleImpl implements TaskService {

	@Autowired
	private WechatQrcodeBiz wechatQrcodeBiz;
	
	@Override
	public void task(Integer taskId) {
		//给每个授权的账号每天批量生成二维码2000个
		wechatQrcodeBiz.getQrcodes(1, 2, "QR_LIMIT_SCENE");
	}

}
