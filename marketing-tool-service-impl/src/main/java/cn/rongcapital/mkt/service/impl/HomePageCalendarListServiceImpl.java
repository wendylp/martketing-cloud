package cn.rongcapital.mkt.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.service.HomePageCalendarListService;
import cn.rongcapital.mkt.vo.out.HomePageCalendarListOut;

public class HomePageCalendarListServiceImpl implements HomePageCalendarListService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CampaignHeadDao campaignHeadDao;

    @Override
    public List<HomePageCalendarListOut> getCalendarList(String date) {
        List<HomePageCalendarListOut> resultList = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        if (!StringUtils.isEmpty(date)) {
            try {
                now.setTime(simpleDateFormat.parse(date));
            } catch (ParseException e) {
                logger.error("活动日历传送了错误的格式 : {}", date);
            }
        }

        startTime.setTime(now.getTime());
        endTime.setTime(now.getTime());

        // 初始化下开始结束时间
        initParamTime(startTime, endTime);

        Map<String, Date> paramMap = new HashMap<>();
        campaignHeadDao.selectInProgressandPrepareStatusCampaignHead(paramMap);

        return resultList;
    }

    private void initParamTime(Calendar startTime, Calendar endTime) {

    }

}
