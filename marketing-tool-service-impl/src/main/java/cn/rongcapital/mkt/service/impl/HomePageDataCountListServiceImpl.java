package cn.rongcapital.mkt.service.impl;

import static cn.rongcapital.mkt.common.enums.HomePageDataCountListEnum.DATA_PARTY;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.service.HomePageDataCountListService;
import cn.rongcapital.mkt.vo.out.HomePageDataCountListOut;

public class HomePageDataCountListServiceImpl implements HomePageDataCountListService {

    private static final int CHILD_LEVEL = 2;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private CustomTagDao customeTagDao;

    @Autowired
    private TaggroupDao taggroupDao;

    @Override
    public List<HomePageDataCountListOut> getDataCountList() {

        List<HomePageDataCountListOut> dataCountList = new ArrayList<>();

        // 获取data_party的数据
        int dataPartyCount = dataPartyDao.selectListCount(null);
        HomePageDataCountListOut dataPartyCountListObj = new HomePageDataCountListOut();
        dataPartyCountListObj.setId(DATA_PARTY.getId());
        dataPartyCountListObj.setName(DATA_PARTY.getName());
        dataPartyCountListObj.setCount(dataPartyCount);
        dataCountList.add(dataPartyCountListObj);

        // 获取标签的数据
        int customTagCount = customeTagDao.selectListCount(null);
        Taggroup paramTaggroup = new Taggroup();
        paramTaggroup.setLevel(CHILD_LEVEL);
        int taggroupCount = taggroupDao.selectListCount(paramTaggroup);
        int tagCount = customTagCount + taggroupCount;


        return null;
    }

}
