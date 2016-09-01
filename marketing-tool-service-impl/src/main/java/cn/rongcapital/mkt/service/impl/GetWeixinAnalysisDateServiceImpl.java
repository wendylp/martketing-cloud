package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.po.WechatQrcodeFocus;
import cn.rongcapital.mkt.service.GetWeixinAnalysisDateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Yunfeng on 2016-8-31.
 */
@Service
public class GetWeixinAnalysisDateServiceImpl implements GetWeixinAnalysisDateService {

    private static final String DAY_7_SCOPE = "Day7";
    private static final String DAY_30_SCOPE = "Day30";

    @Autowired
    private WechatQrcodeFocusDao wechatQrcodeFocusDao;

//    "data": [  {"Today":"2016-1-1","StarDate":"2016-1-1","EndDate":"2016-1-1"},
//
//    {"Yestoday":"2015-12-31","StarDate":"2015-12-31","EndDate":"2015-12-31"},
//
//    {"Day7":"","StarDate":"2015-12-24","EndDate":"2016-1-1"},
//
//    {"Day30":"","StarDate":"2015-12-1","EndDate":"2016-1-1"},
//
//    {"RecordScope":"","StarDate":"2014-12-1","EndDate":"2016-1-1"} ]

    @Override
    public BaseOutput getWeixinAnalysisDate() {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        Date minDateInMysql = null;
        Date maxDateInMysql = null;
        List<WechatQrcodeFocus> wechatQrcodeFocusList = wechatQrcodeFocusDao.selectTheEarliestFocus();
        if(!CollectionUtils.isEmpty(wechatQrcodeFocusList)){
            for(WechatQrcodeFocus wechatQrcodeFocus: wechatQrcodeFocusList){
                if(wechatQrcodeFocus.getFocusDatetime() != null){
                    minDateInMysql = wechatQrcodeFocus.getFocusDatetime();
                }
            }
            if(wechatQrcodeFocusList.get(wechatQrcodeFocusList.size()-1).getFocusDatetime() != null){
                maxDateInMysql = wechatQrcodeFocusList.get(wechatQrcodeFocusList.size()-1).getFocusDatetime();
            }
        }

        Calendar calendar = Calendar.getInstance();
        String todayDate = DateUtil.getStringFromDate(calendar.getTime(),"yyyy-MM-dd");
        Map<String,String> todayMap = new HashMap<String, String>();
        todayMap.put("Today",todayDate);
        todayMap.put("StarDate",todayDate);
        todayMap.put("EndDate",todayDate);
        result.getData().add(todayMap);

        calendar.add(Calendar.DAY_OF_MONTH,-1);
        String yesToday = DateUtil.getStringFromDate(calendar.getTime(),"yyyy-MM-dd");
        Map<String,String> yesTodayMap = new HashMap<String,String>();
        yesTodayMap.put("Yestoday",yesToday);
        yesTodayMap.put("StarDate",yesToday);
        yesTodayMap.put("EndDate",todayDate);
        result.getData().add(yesTodayMap);

        calendar.add(calendar.DAY_OF_MONTH,1);
        calendar.add(calendar.DAY_OF_MONTH,-7);
        Map<String, String> daySevenMap = generateResultMap(DAY_7_SCOPE,minDateInMysql, calendar, todayDate);
        result.getData().add(daySevenMap);

        calendar.add(calendar.DAY_OF_MONTH,7);
        calendar.add(calendar.DAY_OF_MONTH,-30);
        Map<String,String> dayMonthMap = generateResultMap(DAY_30_SCOPE,minDateInMysql,calendar,todayDate);
        result.getData().add(dayMonthMap);

        Map<String,String> focusMap = new HashMap<String, String>();
        focusMap.put("RecordScope","");
        focusMap.put("StarDate",DateUtil.getStringFromDate(minDateInMysql,"yyyy-MM-dd"));
        focusMap.put("EndDate",DateUtil.getStringFromDate(maxDateInMysql,"yyyy-MM-dd"));
        result.getData().add(focusMap);

        result.setTotal(result.getData().size());
        return result;
    }

    private Map<String, String> generateResultMap(String scope,Date minDateInMysql, Calendar calendar, String todayDate) {
        Map<String,String> daySevenMap = new HashMap<String,String>();
        daySevenMap.put(scope,"");
        if(minDateInMysql != null){
            daySevenMap.put("StarDate", DateUtil.getStringFromDate((minDateInMysql.before(calendar.getTime())? calendar.getTime():minDateInMysql),"yyyy-MM-dd"));
        }else{
            daySevenMap.put("StarDate",DateUtil.getStringFromDate(calendar.getTime(),"yyyy-MM-dd"));
        }
        daySevenMap.put("EndDate",todayDate);
        return daySevenMap;
    }

}
