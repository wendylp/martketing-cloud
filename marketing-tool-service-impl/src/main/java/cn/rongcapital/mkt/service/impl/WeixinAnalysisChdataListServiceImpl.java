/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.dao.WechatQrcodeScanDao;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.WechatQrcodeFocus;
import cn.rongcapital.mkt.service.WeixinAnalysisChdataListService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
@Service
public class WeixinAnalysisChdataListServiceImpl implements WeixinAnalysisChdataListService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	WechatQrcodeFocusDao wechatQrcodeFocusDao;
	
	@Autowired
	WechatChannelDao wechatChannelDao;
	
	@Autowired
	WechatQrcodeDao wechatQrcodeDao;
	
	@Autowired
	WechatQrcodeScanDao wechatQrcodeScanDao;
	
	/**
	 * 按公众号和渠道,以及时间区间内获取关注数据(扫码、关注、新增...), 支持分页 
	 * 接口：mkt.weixin.analysis.chdata.list
	 * 
	 * @author shuiyangyang
	 * @date 2016.09.01
	 */
	@Override
	public BaseOutput getAnalysisChdata(String wxName, String chCode, String startDate, String endDate) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
		
		WechatQrcodeFocus wechatQrcodeFocus = new WechatQrcodeFocus();
		if(chCode != null && chCode.length() > 0 && !chCode.equals("0")) {
			wechatQrcodeFocus.setChCode(Integer.valueOf(chCode));
		}
		if(wxName != null && wxName.length() > 0 && !wxName.equals("0")) {
			wechatQrcodeFocus.setWxName(wxName);
		}
		// 用两个关注时间来存储设置的开始和结束时间
		wechatQrcodeFocus.setFocusDatetime(DateUtil.getDateFromString(startDate, "yyyy-MM-dd"));
		wechatQrcodeFocus.setUnfocusDatetime(DateUtil.getDateFromString(endDate, "yyyy-MM-dd"));
		
		List<Map<String, Object>> allFocusDataLists = wechatQrcodeFocusDao.getAllFocusData(wechatQrcodeFocus);
		
		if(allFocusDataLists != null && allFocusDataLists.size() > 0) {
			int total = allFocusDataLists.size();
		
			for(Map<String, Object> allFocusDataList : allFocusDataLists){
				String qrcodeId = allFocusDataList.get("qrcodeId").toString();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("wx_name", getWechatQrcode(qrcodeId).getWxName());
				map.put("ch_name", getChName(getWechatQrcode(qrcodeId)));
				map.put("total_scan", wechatQrcodeScanDao.getTotalScan(qrcodeId));
				map.put("total_scan_user", wechatQrcodeScanDao.getTotalScanUser(qrcodeId));
				map.put("total_focus", allFocusDataList.get("totalFocus"));
				map.put("new_focus", allFocusDataList.get("newFocus") == null ? 0 : allFocusDataList.get("newFocus"));
				map.put("lost_focus", allFocusDataList.get("lostFocus") == null ? 0 : allFocusDataList.get("lostFocus"));
				map.put("add_focus", 
						Integer.valueOf(map.get("new_focus").toString()) 
							- Integer.valueOf(map.get("lost_focus").toString()));
				result.getData().add(map);
			}
			result.setTotal(total);
		}
		
		return result;
	}
	
	/**
	 * 根据二维码id查询二维码名和渠道号
	 * @param qrcodeId
	 * @return
	 */
	private WechatQrcode getWechatQrcode(String qrcodeId) {
		WechatQrcode wechatQrcode = new WechatQrcode();
		wechatQrcode.setId(Integer.valueOf(qrcodeId));
		List<WechatQrcode> WechatQrcodeLists = wechatQrcodeDao.selectList(wechatQrcode);
		return WechatQrcodeLists.get(0);
	}
	
	/**
	 * 根据二维码中的渠道号获取渠道名
	 * @param chCode
	 * @return
	 */
	private String getChName(WechatQrcode wechatQrcode) {
		
		WechatChannel wechatChannel = new WechatChannel();
		wechatChannel.setId(wechatQrcode.getChCode());
		List<WechatChannel> wechatChannelLists = wechatChannelDao.selectList(wechatChannel);
		String chName;
		if(wechatChannelLists == null || wechatChannelLists.isEmpty()) {
			chName = "";
			logger.debug("根据渠道号：{} 找不到渠道名", wechatQrcode.getChCode());
		} else {
			chName = wechatChannelLists.get(0).getChName();
		}
		
		return chName;
	}
	
}
