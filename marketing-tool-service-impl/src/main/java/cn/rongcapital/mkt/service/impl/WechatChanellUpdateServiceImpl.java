package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.service.WechatChanellUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatChanellUpdateIn;

/**
 * Created by zhaoguoying on 2016-08-11.
 */
@Service
public class WechatChanellUpdateServiceImpl implements WechatChanellUpdateService {

	@Autowired
	WechatChannelDao wechatChannelDao;

	public BaseOutput wechatChannelUpdate(WechatChanellUpdateIn wechatChanellUpdateIn,
			SecurityContext securityContext) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		//获取渠道名称数组
		String[] chaNames = wechatChanellUpdateIn.getChaNames();
		for (String chaName : chaNames) {
			WechatChannel wechatChannel = new WechatChannel();

			wechatChannel.setChName(chaName);
			wechatChannel.setType(ApiConstant.WECHAT_CHANNEL_TYPE_CUSTOM);
			wechatChannel.setIsRemoved(ApiConstant.TABLE_DATA_REMOVED_DEL);
			int count = wechatChannelDao.selectListCount(wechatChannel);
			if(count == 0){
				wechatChannelDao.insert(wechatChannel);
			}
		}

		WechatChannel wechatChannel = new WechatChannel();
		wechatChannel.setStatus(Byte.toString(ApiConstant.TABLE_DATA_STATUS_VALID));
		List<WechatChannel> wechatChannels = wechatChannelDao.selectList(wechatChannel);
		
		if (CollectionUtils.isNotEmpty(wechatChannels)) {
			result.setTotal(wechatChannels.size());
			for (WechatChannel w : wechatChannels) {
				Map<String, Object> channelMap = new HashMap<String, Object>();
				channelMap.put("channel_id", w.getId());
				channelMap.put("channel_name", w.getChName());
				channelMap.put("channel_type", w.getType());
				channelMap.put("channel_removed", w.getIsRemoved());
				result.getData().add(channelMap);
			}
		}
		
//		List<WechatChannel> wechatChaList = wechatChannelDao.selectWechatChaList(chaNames);
//		if(CollectionUtils.isNotEmpty(wechatChaList)){
//			result.setTotal(wechatChaList.size());
//			WechatChanellUpdateOut wechatChanellUpdateOut = new WechatChanellUpdateOut();
//			wechatChanellUpdateOut.setId(wechatChanellUpdateIn.getChannelId());
//			wechatChanellUpdateOut
//					.setUpdatetime(DateUtil.getStringFromDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
//			result.getData().add(wechatChanellUpdateOut);
//		}
		
		
//		wechatChannel.setId(wechatChanellUpdateIn.getChannelId());
//
//		Integer count = wechatChannelDao.selectListCount(wechatChannel);
//		
//
//		if (count > 0) {
//			wechatChannel.setChName(wechatChanellUpdateIn.getChName());
//			//wechatChannel.setStatus(Integer.toString(wechatChanellUpdateIn.getStatus()));
//			wechatChannel.setUpdateTime(new Date());
//			wechatChannelDao.updateById(wechatChannel);
//			result.setTotal(count);
//			WechatChanellUpdateOut wechatChanellUpdateOut = new WechatChanellUpdateOut();
//			wechatChanellUpdateOut.setId(wechatChanellUpdateIn.getChannelId());
//			wechatChanellUpdateOut	
//					.setUpdatetime(DateUtil.getStringFromDate(wechatChannel.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
//			result.getData().add(wechatChanellUpdateOut);
//		} else {
//			wechatChannel = new WechatChannel();
//			wechatChannel.setChName(wechatChanellUpdateIn.getChName());
//			List<WechatChannel> wechatChannels = wechatChannelDao.selectList(wechatChannel);
//			if (CollectionUtils.isEmpty(wechatChannels) || wechatChannels.size() == 0) {
//				wechatChannelDao.insert(wechatChannel);
//				wechatChannel.setId(null);
//				wechatChannels = wechatChannelDao.selectList(wechatChannel);
//				if (CollectionUtils.isNotEmpty(wechatChannels) || wechatChannels.size() > 0) {
//					wechatChannel.setId(wechatChannels.get(0).getId());
//					result.setTotal(1);
//					WechatChanellUpdateOut wechatChanellUpdateOut = new WechatChanellUpdateOut();
//					wechatChanellUpdateOut.setId(wechatChannels.get(0).getId());
//					wechatChanellUpdateOut.setUpdatetime(
//							DateUtil.getStringFromDate(wechatChannels.get(0).getUpdateTime(), "yyy-MM-dd HH:mm:ss"));
//					result.getData().add(wechatChanellUpdateOut);
//				}
//			}
//		}
		return result;
	}
}
