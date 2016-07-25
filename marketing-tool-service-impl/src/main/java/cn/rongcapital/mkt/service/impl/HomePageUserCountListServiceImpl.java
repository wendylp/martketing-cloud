package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.HomePageMonthlyCount;
import cn.rongcapital.mkt.service.HomePageUserCountListService;
import cn.rongcapital.mkt.vo.out.HomePageUserCountListOut;

@Service
public class HomePageUserCountListServiceImpl implements HomePageUserCountListService {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public List<HomePageUserCountListOut> getHomePageUserCountList() {

        List<HomePageUserCountListOut> resultList = new ArrayList<>(12);

        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        startTime.set(startTime.get(Calendar.YEAR), 0, 1, 0, 0, 0);
        endTime.set(startTime.get(Calendar.YEAR) + 1, 0, 1, 0, 0, 0);

        Map<String, Date> paramMap = new HashMap<>();
        paramMap.put("startTime", startTime.getTime());
        paramMap.put("endTime", endTime.getTime());

        List<HomePageMonthlyCount> homePageMonthlyCountList = dataPartyDao.selectMonthlyCount(paramMap);
        List<String> allMonth = getAllMonthInYear(startTime.get(Calendar.YEAR));

        // 把取出来的数据放在list里 ,方便查询
        Map<String, Integer> homePageMonthlyCountMap = new HashMap<>(12);
        for (HomePageMonthlyCount HomePageMonthlyCount : homePageMonthlyCountList) {
            homePageMonthlyCountMap.put(HomePageMonthlyCount.getMonth(), HomePageMonthlyCount.getMonthlyCount());
        }

        for (String month : allMonth) {
            HomePageUserCountListOut monthlyCountObj = new HomePageUserCountListOut();
            monthlyCountObj.setMonth(month);
            Integer monthlyCount = homePageMonthlyCountMap.get(month);
            if (monthlyCount != null) {
                monthlyCountObj.setMonthlyCount(monthlyCount);
            } else {
                monthlyCountObj.setMonthlyCount(0);
            }

            resultList.add(monthlyCountObj);
        }

        return resultList;
    }

    // 默认为yyyy-MM的格式
    private List<String> getAllMonthInYear(int year) {
        List<String> monthList = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) {
            monthList.add(year + "-" + (i + 1));
        }

        return monthList;
    }

}
