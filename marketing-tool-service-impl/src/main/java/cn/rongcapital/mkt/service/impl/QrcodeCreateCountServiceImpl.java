package cn.rongcapital.mkt.service.impl;

import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.WechatQrcodeLog;
import cn.rongcapital.mkt.service.QrcodeCreateCountService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class QrcodeCreateCountServiceImpl implements QrcodeCreateCountService {

	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput getCreateCount(Long batch_id)
	{
		WechatQrcodeLog qlog = new WechatQrcodeLog();
		qlog.setId(batch_id);
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO,
				null);
		
		return result;
	}
}
