package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.service.WechatAnalysisDaysListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class WechatAnalysisDaysListServiceImpl implements WechatAnalysisDaysListService {
	
	//关注时间数据库字段
	private static final String FOCUS_FIELDNAME = "focus_datetime";
	//取消关注时间数据库字段
	private static final String UNFOCUS_FIELDNAME = "unfocus_datetime";
	//计算天数
	private static final Long CALCULATE_VARI = 86400000L;

	@Autowired
	private WechatQrcodeFocusDao wechatQrcodeFocusDao;
	
	@Override
	public BaseOutput analysisDaysList(String startDate, String endDate, Integer daysType, String chCode,
			String wxName) {
		  BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                  ApiConstant.INT_ZERO, null);
		try {
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			  //返回结果集
			  Map<String, Object> resultMap = new HashMap<>();
			  //日期格式化
			  Date sDate = sdf.parse(startDate);
			  Date eDate = sdf.parse(endDate);
			  //计算数组长度
			  long s = sDate.getTime()/CALCULATE_VARI; 
			  long e = eDate.getTime()/CALCULATE_VARI;
			  //数组长度
			  int arrayLenth = (int)(e - s)+1;
			  //参数数组定义
			  String[] dateArray = new String[arrayLenth];	//查询日期
			  Integer[] focusCountArray = new Integer[arrayLenth];	//关注数量
			  Integer[] unFocusCountArray = new Integer[arrayLenth];	//取消关注数量
			  Integer[] creFocusCountArray = new Integer[arrayLenth];	//净增关注数量
			  //临时日期变量
			  Date tempDate = sDate;
			  //数量记录
			  int i = 0;
			  Calendar calendar = new GregorianCalendar(); 
			  calendar.setTime(tempDate);
			  while(tempDate.compareTo(eDate) <= 0){
				  Integer foucusCount = getCunt(tempDate, FOCUS_FIELDNAME, chCode, wxName);	//关注数量
				  Integer unFocusCount = getCunt(tempDate, UNFOCUS_FIELDNAME, chCode, wxName);//取消关注数量
				  Integer creFocusCount = foucusCount - unFocusCount;	//净增数量
				  focusCountArray[i] = foucusCount;
				  unFocusCountArray[i] = unFocusCount;
				  creFocusCountArray[i] = creFocusCount;
				  dateArray[i] = sdf.format(tempDate);
				  //向后加一天
				  calendar.add(Calendar.DATE,1);
				  tempDate=calendar.getTime();
				  i++;
			  }
			 List<Map<String, Object>> paramList = new ArrayList<>();
			 paramList.add(getMap("新增关注",ArrayUtils.subarray(focusCountArray, 0, i)));
			 paramList.add(getMap("流失关注",ArrayUtils.subarray(unFocusCountArray, 0, i)));
			 paramList.add(getMap("净增关注",ArrayUtils.subarray(creFocusCountArray, 0, i)));
			 resultMap.put("series",paramList);
			 resultMap.put("date", ArrayUtils.subarray(dateArray, 0, i));
			 resultMap.put("days_type", daysType);
			 resultMap.put("wx_name", wxName);
			 resultMap.put("ch_code", chCode);
			 baseOutput.getData().add(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseOutput;
	}
	
	
	
	
	/**
	 * @Title: getCunt   
	 * @Description: 获取相应数量 
	 * @param: @param searchDate	查询日期
	 * @param: @param fieldName		字段名称
	 * @param: @param chCode		渠道编码
	 * @param: @param wxName		微信名称
	 * @param: @return      
	 * @return: Integer      
	 * @throws
	 */
	private Integer getCunt(Date searchDate,String fieldName,String chCode,String wxName ){
		//参数集合
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("searchDate", searchDate);
		paramMap.put("fieldName", fieldName);
		paramMap.put("chCode", chCode);
		paramMap.put("wxName", wxName);
		return wechatQrcodeFocusDao.getFocusOrUnFocusCount(paramMap);
	}
	
	
	/**
	 * @Title: getMap   
	 * @Description: 封装参数集合  
	 * @param: @param value1
	 * @param: @param value2
	 * @param: @return      
	 * @return: Map<String,Object>      
	 * @throws
	 */
	private Map<String, Object> getMap(String value1,Object value2){
		Map<String, Object> map = new HashMap<>();
		map.put("name", value1);
		map.put("data", value2);
		return map;
	}
	
	

}
