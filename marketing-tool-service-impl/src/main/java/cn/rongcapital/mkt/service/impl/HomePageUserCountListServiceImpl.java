package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.HomePageMonthlyCount;
import cn.rongcapital.mkt.service.HomePageUserCountListService;
import cn.rongcapital.mkt.vo.out.HomePageUserCountListOut;

@Service
public class HomePageUserCountListServiceImpl implements HomePageUserCountListService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public List<HomePageUserCountListOut> getHomePageUserCountList() {

        List<HomePageUserCountListOut> resultList = new ArrayList<>(12);

        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        startTime.set(startTime.get(Calendar.YEAR) - 1, startTime.get(Calendar.MONTH) + 2, 1, 0, 0, 0);
        endTime.set(endTime.get(Calendar.YEAR), endTime.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
        // 这里得把MILLISECOND设置为0,不然即便跟下一年的数据比较,也能因为MILLISECOND的微小差距查出下一年的数据
        startTime.set(Calendar.MILLISECOND, 0);
        endTime.set(Calendar.MILLISECOND, 0);

        Map<String, Date> paramMap = new HashMap<>();
        paramMap.put("startTime", startTime.getTime());
        paramMap.put("endTime", endTime.getTime());

        List<HomePageMonthlyCount> homePageMonthlyCountList = dataPartyDao.selectMonthlyCount(paramMap);

        startTime.set(startTime.get(Calendar.YEAR), startTime.get(Calendar.MONTH), 1, 0, 0, 0);
        endTime.set(endTime.get(Calendar.YEAR), endTime.get(Calendar.MONTH) - 1, 1, 0, 0, 0);
        List<String> allMonth = getAllMonthInYear(startTime, endTime);

        // 把取出来的数据放在Map里, 方便查询, 不然每次都要轮训list.明显map效率更高
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
    private List<String> getAllMonthInYear(Calendar startTime, Calendar endTime) {
        List<String> monthList = new ArrayList<>(12);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        while (!startTime.after(endTime)) {
            monthList.add(simpleDateFormat.format(startTime.getTime()));
            startTime.add(Calendar.MONTH, 1);
        }

        return monthList;
    }

}
