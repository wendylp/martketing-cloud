package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.HomePageSourceGroupCount;
import cn.rongcapital.mkt.service.HomePageDataSourceListService;
import cn.rongcapital.mkt.vo.out.HomePageDataSourceListOut;

public class HomePageDataSourceListServiceImpl implements HomePageDataSourceListService {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public List<HomePageDataSourceListOut> getHomePageUserCountList() {
        List<HomePageDataSourceListOut> resultList = new ArrayList<>();
        List<HomePageSourceGroupCount> homePageSourceGroupCounts = dataPartyDao.selectSourceGroupCount();

        if (!CollectionUtils.isEmpty(homePageSourceGroupCounts)) {
            for (HomePageSourceGroupCount homePageSourceGroupCountObj : homePageSourceGroupCounts) {
                HomePageDataSourceListOut HomePageDataSourceListOutObj = new HomePageDataSourceListOut();
                HomePageDataSourceListOutObj.setSource(homePageSourceGroupCountObj.getSource());
                HomePageDataSourceListOutObj.setSourceCount(homePageSourceGroupCountObj.getSourceCount());

                resultList.add(HomePageDataSourceListOutObj);
            }
        }

        return resultList;
    }

}
