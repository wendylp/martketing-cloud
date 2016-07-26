package cn.rongcapital.mkt.service;

import java.util.List;

import cn.rongcapital.mkt.vo.out.HomePageCalendarListOut;

public interface HomePageCalendarListService {

    /**
     * @author
     * 
     * mkt.homepage.datacount.list
     * 
     * 统计出当月日历日被客户标记当月定时的活动，按启动时间算。<br>
     * 
     * @return List<HomePageDataCountListOut>
     */
    public List<HomePageCalendarListOut> getCalendarList(String date);

}
