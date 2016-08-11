package cn.rongcapital.mkt.service.impl;

import java.util.Date;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.service.WechatChanellUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatChanellUpdateIn;
import cn.rongcapital.mkt.vo.out.WechatChanellUpdateOut;

/**
 * Created by zhaoguoying on 2016-08-11.
 */
@Service
public class WechatChanellUpdateServiceImpl implements WechatChanellUpdateService {

	@Autowired
	WechatChannelDao wechatChannelDao;

	public BaseOutput wechatChannelUpdate(WechatChanellUpdateIn wechatChanellUpdateIn, SecurityContext securityContext) {
		WechatChannel wechatChannel = new WechatChannel();
		wechatChannel.setId(wechatChanellUpdateIn.getChannelId());
		wechatChannel.setChName(wechatChanellUpdateIn.getChName());
		wechatChannel.setStatus(Integer.toString(wechatChanellUpdateIn.getStatus()));
		wechatChannel.setUpdateTime(new Date());

		wechatChannelDao.updateById(wechatChannel);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		WechatChanellUpdateOut wechatChanellUpdateOut = new WechatChanellUpdateOut();
		wechatChanellUpdateOut.setId(wechatChanellUpdateIn.getChannelId());
		wechatChanellUpdateOut
				.setUpdatetime(DateUtil.getStringFromDate(wechatChannel.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
		result.getData().add(wechatChanellUpdateOut);
		return result;
	}
}
