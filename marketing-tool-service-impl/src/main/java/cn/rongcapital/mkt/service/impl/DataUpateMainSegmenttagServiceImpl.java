package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.service.DataUpateMainSegmenttagService;

public class DataUpateMainSegmenttagServiceImpl implements DataUpateMainSegmenttagService {

    @Autowired
    private CustomTagDao customTagDao;

    @Autowired
    private CustomTagMapDao customTagMapDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updateMainSegmenttag(String method, String userToken, Integer index, Integer size, String ver,
                    String tagName, Integer contactId) {

        // step 1 检查tag是否已经存在数据库中
        List<CustomTag> customTags = customTagDao.selectList(null);
        // 不存在则入库
        if (customTags.isEmpty()) {
            
        }


        return false;
    }

}
