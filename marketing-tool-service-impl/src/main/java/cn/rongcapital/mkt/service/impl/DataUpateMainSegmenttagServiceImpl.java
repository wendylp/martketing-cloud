package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.enums.CustomTagMapEnum;
import cn.rongcapital.mkt.common.enums.DeleteStatusEnum;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.service.DataUpateMainSegmenttagService;

@Service
public class DataUpateMainSegmenttagServiceImpl implements DataUpateMainSegmenttagService {

    @Autowired
    private CustomTagDao customTagDao;

    @Autowired
    private CustomTagMapDao customTagMapDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updateMainSegmenttag(String method, String userToken, String ver, String tagName,
                    Integer contactId) {

        // step 1 检查tag是否已经存在数据库中
        CustomTag paramCustomTag = new CustomTag();
        Date createTime = new Date();
        paramCustomTag.setName(tagName);
        List<CustomTag> customTags = customTagDao.selectListforUpdate(paramCustomTag);
        // 不存在则入库
        if (customTags.isEmpty()) {
            paramCustomTag.setStatus(Byte.valueOf(DeleteStatusEnum.ACTIVE.getStatusCode() + ""));
            paramCustomTag.setCreateTime(createTime);
            // 覆盖人群,目前有一个覆盖
            paramCustomTag.setCoverAudienceCount(1);
            customTagDao.insert(paramCustomTag);
        } else {
            // 标签已经存在, 更新受覆盖人群数量
            customTagDao.increaseCoverAudienceCount(paramCustomTag);
        }

        // step 2 将用户id与tag关联起来
        // step 2.1 查询用户是否已经关联该tag
        CustomTagMap paramCustomTagMap = new CustomTagMap();
        paramCustomTagMap.setTagId(paramCustomTag.getId());
        paramCustomTagMap.setMapId(contactId);

        List<CustomTagMap> customTagMaps = customTagMapDao.selectList(paramCustomTagMap);

        // step 2.2 如果没有将该tag关联用户 , 则插入数据库数据
        if (customTagMaps.isEmpty()) {
            paramCustomTagMap.setType(Byte.valueOf(CustomTagMapEnum.AUDIENCE.getCode() + ""));
            paramCustomTagMap.setTagId(paramCustomTag.getId());
            paramCustomTagMap.setStatus(Byte.valueOf(DeleteStatusEnum.ACTIVE.getStatusCode() + ""));
            paramCustomTagMap.setCreateTime(createTime);
            customTagMapDao.insert(paramCustomTagMap);

            return true;
        }

        return false;
    }

}
