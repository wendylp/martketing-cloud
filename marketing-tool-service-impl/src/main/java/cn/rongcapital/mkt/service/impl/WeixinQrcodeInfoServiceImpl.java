package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.service.WeixinQrcodeInfoService;
import cn.rongcapital.mkt.vo.BaseOutput;
/**
 * 
 * 查询二维码详细信息 
 * 
 * @author shuiyangyang
 * @Data 2016.08.24
 */
@Service
public class WeixinQrcodeInfoServiceImpl implements WeixinQrcodeInfoService{

	@Autowired
	private WechatQrcodeDao wechatQrcodeDao;
	
	@Autowired
	private WechatChannelDao wechatChannelDao;
	
	
	@Override
	public BaseOutput getWeiXinQrocdeInfo(String qrcodeId) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ONE,null);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setId(Integer.valueOf(qrcodeId));
		
		List<WechatQrcode> wechatQrcodeLists = wechatQrcodeDao.selectList(wechatQrcode);
		// 如果没有查询结果 设置错误
		if(wechatQrcodeLists == null || wechatQrcodeLists.isEmpty()) {
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("wxmp_name", wechatQrcodeLists.get(0).getWxName());
			
			// 根据渠道号获取渠道名
			WechatChannel wechatChannel = new WechatChannel();
			wechatChannel.setId(wechatQrcodeLists.get(0).getChCode());
			List<WechatChannel> wechatChannelLists = wechatChannelDao.selectList(wechatChannel);
			if(!(wechatChannelLists == null) && !wechatChannelLists.isEmpty()) {
				map.put("ch_name", wechatChannelLists.get(0).getChName());
			}else {
				map.put("ch_name", "");//查询不到的时候默认传空字符串
			}
			
			
			map.put("qrcode_name", wechatQrcodeLists.get(0).getQrcodeName());
			
			// create_time为空检查
			if(wechatQrcodeLists.get(0).getCreateTime() != null) {
				map.put("create_time", format.format(wechatQrcodeLists.get(0).getCreateTime()));
			} else {
				map.put("create_time", "");
			}
			
			map.put("expiration_time", wechatQrcodeLists.get(0).getExpirationTime());
			map.put("fixed_audience", wechatQrcodeLists.get(0).getAudienceName());// 固定人群
			map.put("qrcode_tags", wechatQrcodeLists.get(0).getRelatedTags());
			map.put("comment", wechatQrcodeLists.get(0).getComments());
			
			result.getData().add(map);
		}
		
		return result;
	}

}
