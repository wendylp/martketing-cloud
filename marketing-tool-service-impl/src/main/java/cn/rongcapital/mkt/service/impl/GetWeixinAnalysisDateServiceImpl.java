package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.WechatChannelDao;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.po.WechatChannel;
import cn.rongcapital.mkt.po.WechatQrcode;
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

    @Autowired
    private WechatQrcodeDao wechatQrcodeDao;

    @Autowired
    private WechatChannelDao wechatChannelDao;

    @Override
    public BaseOutput getWeixinAnalysisDate(String qrcodeId) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        Date minDateInMysql = null;
        Date maxDateInMysql = null;
        List<WechatQrcodeFocus> wechatQrcodeFocusList;
        if(qrcodeId == null || qrcodeId.length() == 0){
            wechatQrcodeFocusList = wechatQrcodeFocusDao.selectTheEarliestFocus();
        }else{
            wechatQrcodeFocusList = wechatQrcodeFocusDao.selectTheEarliestFocusByQrcodeId(qrcodeId);
        }
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
        Map<String,Map<String,String>> todayMapResult = new HashMap<>();
        todayMap.put("Today",todayDate);
        todayMap.put("StarDate",todayDate);
        todayMap.put("EndDate",todayDate);
        todayMapResult.put("Today", todayMap);
        result.getData().add(todayMapResult);

        calendar.add(Calendar.DAY_OF_MONTH,-1);
        String yesToday = DateUtil.getStringFromDate(calendar.getTime(),"yyyy-MM-dd");
        Map<String,String> yesTodayMap = new HashMap<String,String>();
        Map<String,Map<String,String>> yesTodayMapResult = new HashMap<>();
        yesTodayMap.put("Yestoday",yesToday);
        yesTodayMap.put("StarDate",yesToday);
        yesTodayMap.put("EndDate",todayDate);
        yesTodayMapResult.put("Yestoday", yesTodayMap);
        result.getData().add(yesTodayMapResult);

        calendar.add(calendar.DAY_OF_MONTH,1);
        calendar.add(calendar.DAY_OF_MONTH,-7);
        Map<String,Map<String,String>> daySevenMapResult = new HashMap<>();
        Map<String, String> daySevenMap = generateResultMap(DAY_7_SCOPE,minDateInMysql, calendar, todayDate);
        daySevenMapResult.put(DAY_7_SCOPE, daySevenMap);
        result.getData().add(daySevenMapResult);

        calendar.add(calendar.DAY_OF_MONTH,7);
        calendar.add(calendar.DAY_OF_MONTH,-30);
        Map<String,Map<String,String>> dayMonthMapResult = new HashMap<>();
        Map<String,String> dayMonthMap = generateResultMap(DAY_30_SCOPE,minDateInMysql,calendar,todayDate);
        dayMonthMapResult.put(DAY_30_SCOPE, dayMonthMap);
        result.getData().add(dayMonthMapResult);

        Map<String,String> focusMap = new HashMap<String, String>();
        Map<String,Map<String,String>> focusMapResult = new HashMap<>();
        focusMap.put("RecordScope","");
        focusMap.put("StarDate",DateUtil.getStringFromDate(minDateInMysql,"yyyy-MM-dd"));
        focusMap.put("EndDate",DateUtil.getStringFromDate(maxDateInMysql,"yyyy-MM-dd"));
        focusMapResult.put("RecordScope", focusMap);
        result.getData().add(focusMapResult);

        Map<String,Object> channelMap = new HashMap<String,Object>();
        Map<String,Object> wxInfoMap = new HashMap<String, Object>();
        if(qrcodeId == null || qrcodeId.length()==0){
            constructNullResultForWxInfoAndChannelInfo(result, channelMap, wxInfoMap);
        }else {
            WechatQrcode wechatQrcode = new WechatQrcode();
            wechatQrcode.setId(Integer.valueOf(qrcodeId));
            List<WechatQrcode> wechatQrcodeList = wechatQrcodeDao.selectList(wechatQrcode);
            if(!CollectionUtils.isEmpty(wechatQrcodeList)){
                wxInfoMap.put("wxmp_name",wechatQrcodeList.get(0).getWxName());
                wxInfoMap.put("wx_acct",wechatQrcodeList.get(0).getWxAcct());
                result.getData().add(wxInfoMap);

                WechatChannel wechatChannel = new WechatChannel();
                wechatChannel.setId(wechatQrcode.getChCode());
                List<WechatChannel> wechatChannelList = wechatChannelDao.selectList(wechatChannel);
                if(!CollectionUtils.isEmpty(wechatChannelList)){
                    channelMap.put("ch_name",wechatChannelList.get(0).getChName());
                    channelMap.put("ch_code",wechatChannelList.get(0).getId());
                    result.getData().add(channelMap);
                }else {
                    channelMap.put("ch_name","");
                    channelMap.put("ch_code","");
                    result.getData().add(channelMap);
                }
            }else{
                constructNullResultForWxInfoAndChannelInfo(result, channelMap, wxInfoMap);
            }
        }

        result.setTotal(result.getData().size());
        return result;
    }

    private void constructNullResultForWxInfoAndChannelInfo(BaseOutput result, Map<String, Object> channelMap, Map<String, Object> wxInfoMap) {
        channelMap.put("ch_name","");
        channelMap.put("ch_code","");
        result.getData().add(channelMap);

        wxInfoMap.put("wxmp_name","");
        wxInfoMap.put("wx_acct","");
        result.getData().add(wxInfoMap);
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
