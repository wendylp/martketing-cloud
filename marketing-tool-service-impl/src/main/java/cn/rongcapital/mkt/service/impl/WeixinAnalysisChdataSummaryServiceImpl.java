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
import cn.rongcapital.mkt.po.WechatQrcodeFocus;
import cn.rongcapital.mkt.service.WeixinAnalysisChdataListService;
import cn.rongcapital.mkt.service.WeixinAnalysisChdataSummaryService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
@Service
public class WeixinAnalysisChdataSummaryServiceImpl implements WeixinAnalysisChdataSummaryService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	WechatQrcodeFocusDao wechatQrcodeFocusDao;

	@Autowired
	WechatChannelDao wechatChannelDao;

	@Autowired
	WechatQrcodeDao wechatQrcodeDao;

	@Autowired
	WechatQrcodeScanDao wechatQrcodeScanDao;
	
	@Autowired
	WeixinAnalysisChdataListService weixinAnalysisChdataListService;

	/**
	 * 获取平均、汇总、历史最高关注数据(扫码、关注、新增...) 
	 * 接口：mkt.weixin.analysis.chdata.summary
	 * @author shuiyangyang
	 */
	@Override
	public BaseOutput getAnalysisChdataSummary(String wxName, String chCode, String startDate, String endDate) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ONE, null);
		
		WechatQrcodeFocus wechatQrcodeFocus = new WechatQrcodeFocus();
		if(chCode != null && chCode.length() > 0 && !chCode.equals("0")) {
			wechatQrcodeFocus.setChCode(Integer.valueOf(chCode));
		}
		if(wxName != null && wxName.length() > 0 && !wxName.equals("0")) {
			wechatQrcodeFocus.setWxName(wxName);
		}
		// 根据微信名和渠道号获取二维码id
		List<String> qrcodeIdLists = wechatQrcodeFocusDao.getQrcodeIdList(wechatQrcodeFocus);
		qrcodeIdLists.add("0");
		// 用两个关注时间来存储设置的开始和结束时间
		wechatQrcodeFocus.setFocusDatetime(DateUtil.getDateFromString(startDate, "yyyy-MM-dd"));
		wechatQrcodeFocus.setUnfocusDatetime(DateUtil.getDateFromString(endDate, "yyyy-MM-dd"));
		
		BaseOutput baseOutput = weixinAnalysisChdataListService.getAnalysisChdata(wxName, chCode, startDate, endDate);
		
		List<Object> analysisChdataMap = baseOutput.getData();
		long[] analysisChdataInt = { 0, 0, 0, 0, 0 };
		int analysisChdataMapSize = analysisChdataMap.size();
		if(analysisChdataMapSize == 0) {
			analysisChdataMapSize = 1; 
		} else {
			for (int i = 0; i < analysisChdataMapSize; i++) {
				Map<String, Object> map = (Map<String, Object>) analysisChdataMap.get(i);
				analysisChdataInt[0] += Integer.valueOf(map.get("total_scan").toString());
				analysisChdataInt[1] += Integer.valueOf(map.get("total_scan_user").toString());
				analysisChdataInt[2] += Integer.valueOf(map.get("total_focus").toString());
				analysisChdataInt[3] += Integer.valueOf(map.get("new_focus").toString());
				analysisChdataInt[4] += Integer.valueOf(map.get("lost_focus").toString());
				
			}
		}
		
		
		// 获取总扫码次数最大值
		Map<String, Object> amountScanMax = wechatQrcodeScanDao.getAmountScanMax(qrcodeIdLists);
		// 获取总扫码人数最大值 
		Map<String, Object> amountScanUserMax = wechatQrcodeScanDao.getamountScanUserMax(qrcodeIdLists);
		// 获取最大浏览次数
		Map<String, Object> amountFocusMax = wechatQrcodeFocusDao.getAmountFocusMax(wechatQrcodeFocus);
		//获取最大新关注的信息
		Map<String, Object> newFocusMax = wechatQrcodeFocusDao.getNewFocusMax(wechatQrcodeFocus);
		// 获取最大净增关注信息
		Map<String, Object> AddFocusMax = wechatQrcodeFocusDao.getAddFocusMax(wechatQrcodeFocus);
		// 获取最大流失关注的信息
		Map<String, Object> lostFocusMax = wechatQrcodeFocusDao.getLostFocusMax(wechatQrcodeFocus);
		
		Map<String, Object> Alldata = new HashMap<String, Object>(); 
		
		Map<String, Object> average = new HashMap<String, Object>();
		Map<String, Object> averageData = new HashMap<String, Object>();
		averageData.put("amount_scan", analysisChdataInt[0] / analysisChdataMapSize);
		averageData.put("amount_scan_user", analysisChdataInt[1] / analysisChdataMapSize);
		averageData.put("amount_focus", analysisChdataInt[2] / analysisChdataMapSize);
		averageData.put("new_focus", analysisChdataInt[3] / analysisChdataMapSize);
		averageData.put("add_focus", (analysisChdataInt[3] - analysisChdataInt[4]) / analysisChdataMapSize);
		averageData.put("lost_focus", analysisChdataInt[4] / analysisChdataMapSize);
		Alldata.put("average", averageData);
		
		Map<String, Object> sum = new HashMap<String, Object>();
		Map<String, Object> sumData = new HashMap<String, Object>();
		sumData.put("amount_scan", analysisChdataInt[0]);
		sumData.put("amount_scan_user", analysisChdataInt[1]);
		sumData.put("amount_focus", analysisChdataInt[2]);
		sumData.put("new_focus", analysisChdataInt[3]);
		sumData.put("add_focus", analysisChdataInt[3] - analysisChdataInt[4]);
		sumData.put("lost_focus", analysisChdataInt[4]);
		Alldata.put("sum", sumData);
		
		
		Map<String, Object> maxData = new HashMap<String, Object>();
		if(amountScanMax != null) {
			maxData.put("amount_scan", amountScanMax.get("value"));
			maxData.put("amount_scan_date", amountScanMax.get("time"));
		} else {
			maxData.put("amount_scan", "");
			maxData.put("amount_scan_date", "");
		}
		
		if(amountScanUserMax != null) {
			maxData.put("amount_scan_user", amountScanUserMax.get("value"));
			maxData.put("amount_scan_user_date", amountScanUserMax.get("time"));
		} else {
			maxData.put("amount_scan_user", "");
			maxData.put("amount_scan_user_date", "");
		}
		
		if(amountFocusMax != null) {
			maxData.put("amount_focus", amountFocusMax.get("value"));
			maxData.put("amount_focus_date", amountFocusMax.get("time"));
		} else {
			maxData.put("amount_focus", "");
			maxData.put("amount_focus_date", "");
		}
		
		if(newFocusMax != null) {
			maxData.put("new_focus", newFocusMax.get("value"));
			maxData.put("new_focus_date", newFocusMax.get("time"));
		} else {
			maxData.put("new_focus", "");
			maxData.put("new_focus_date", "");
		}
		
		if(AddFocusMax != null) {
			maxData.put("add_focus", AddFocusMax.get("value"));
			maxData.put("add_focus_date", AddFocusMax.get("time"));
		} else {
			maxData.put("add_focus", "");
			maxData.put("add_focus_date", "");
		}
		
		if(lostFocusMax != null) {
			maxData.put("lost_focus", lostFocusMax.get("value"));
			maxData.put("lost_focus_date", lostFocusMax.get("time"));
		} else {
			maxData.put("lost_focus", "");
			maxData.put("lost_focus_date", "");
		}
		
		Alldata.put("max", maxData);
		
		result.getData().add(Alldata);
		
		return result;
	}
	
	
 
}
