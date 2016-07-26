package cn.rongcapital.mkt.service;
import java.util.List;

import cn.rongcapital.mkt.vo.out.HomePageUserCountListOut;

public interface HomePageUserCountListService {

    /**
     * @author nianjun
     * 
     * mkt.homepage.usercount.list
     * 
     * 按月份按公众号统计每月的用户增加数量   
     * 
     * @return List<HomePageUserCountListOut>
     */
    public List<HomePageUserCountListOut> getHomePageUserCountList();

}
