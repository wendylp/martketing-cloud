package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.CustomTagMapEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.service.DataUpateMainSegmenttagService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

/**
 * @author nianjun
 */
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

        boolean result = false;

        if (StringUtils.isEmpty(tagName)) {
            return result;
        }

        Set<String> tagNames = new HashSet<>();

        if (tagName.contains(",")) {
            String[] tagNameArray = tagName.split(",");
            if (tagNameArray.length < 1) {
                return result;
            }

            // 做个简单的去重操作
            for (String tag : tagNameArray) {
                tagNames.add(tag);
            }
        } else {
            tagNames.add(tagName);
        }

        for (String tag : tagNames) {
            result = result & updateTag(tag, contactId);
        }

        return result;
    }

    private boolean updateTag(String tagName, Integer contactId) {
        // step 1 检查tag是否已经存在数据库中
        CustomTag paramCustomTag = new CustomTag();
        Date createTime = new Date();
        paramCustomTag.setName(tagName);
        List<CustomTag> customTags = customTagDao.selectListforUpdate(paramCustomTag);
        // 不存在则入库
        if (customTags.isEmpty()) {
            paramCustomTag.setStatus(Byte.valueOf(StatusEnum.ACTIVE.getStatusCode().toString()));
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
            paramCustomTagMap.setStatus(Byte.valueOf(StatusEnum.ACTIVE.getStatusCode().toString()));
            paramCustomTagMap.setCreateTime(createTime);
            customTagMapDao.insert(paramCustomTagMap);

            return true;
        }
        return false;
    }
    
    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput getMainSegmenttagNames(Integer map_id) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);

        CustomTagMap customTagMap = new CustomTagMap();
        customTagMap.setMapId(map_id);
        customTagMap.setStatus(new Byte("0"));
        List<CustomTagMap> customTagMapList = customTagMapDao.selectList(customTagMap);

        for (CustomTagMap customTagMap2 : customTagMapList) {


            List<Integer> idList = new ArrayList<Integer>();
            idList.add(customTagMap2.getTagId());
            List<CustomTag> customTagList = customTagDao.selectListByIdList(idList);

            Map<String, Object> map = new HashMap<String, Object>();

            map.put("tag_name", customTagList.get(0).getName());

            result.getData().add(map);


        }

        result.setTotal(customTagMapList.size());


        return result;
    }


}
