package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.dao.CustomTagOriginalDataMapDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.po.CustomTagOriginalDataMap;
import cn.rongcapital.mkt.po.DataParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-7-13.
 */
@Service
public class CustomTagMappingSyncTaskServiceImpl implements TaskService{

    @Autowired
    private DataPartyDao dataPartyDao;
    @Autowired
    private CustomTagOriginalDataMapDao customTagOriginalDataMapDao;
    @Autowired
    private CustomTagMapDao customTagMapDao;
    @Autowired
    private CustomTagDao customTagDao;


    @Override
    public void task(Integer taskId) {
        //1.选取状态有效的tagOriginal表中的数据
        CustomTagOriginalDataMap paramCustomTagOriginalDataMap = new CustomTagOriginalDataMap();
        paramCustomTagOriginalDataMap.setStatus(ApiConstant.CUSTOM_TAG_ORIGINAL_DATA_MAP_VALIDATE);
        paramCustomTagOriginalDataMap.setPageSize(Integer.MAX_VALUE);
        List<CustomTagOriginalDataMap> customTagOriginalDataMapList = customTagOriginalDataMapDao.selectList(paramCustomTagOriginalDataMap);

        //Todo:1.获取尚未同步的标签
        List<Integer> unhandledCustomTagIdList = customTagOriginalDataMapDao.selectDintinctUnhandleIdList(paramCustomTagOriginalDataMap);
        //2.通过表中的唯一标识字段查询dataParty表中的Id字段
        if(CollectionUtils.isEmpty(customTagOriginalDataMapList)) return;
        List<CustomTagMap> customTagMapList = new ArrayList<CustomTagMap>();
        for(CustomTagOriginalDataMap customTagOriginalDataMap : customTagOriginalDataMapList){
            DataParty paramDataParty = new DataParty();
            paramDataParty.setMobile(customTagOriginalDataMap.getDataUniqueIdentifier());
            List<DataParty> dataParties = dataPartyDao.selectList(paramDataParty);
            if(!CollectionUtils.isEmpty(dataParties)){
                //4.跟新原始的tagOriginal表的字段
                customTagOriginalDataMap.setStatus(ApiConstant.CUSTOM_TAG_ORIGINAL_DATA_MAP_INVALIDATE);
                customTagOriginalDataMapDao.updateById(customTagOriginalDataMap);
                //5.构造出CustomTagMap
                CustomTagMap customTagMap = new CustomTagMap();
                customTagMap.setTagId(customTagOriginalDataMap.getTagId());
                customTagMap.setType(customTagOriginalDataMap.getOriginalDataType().byteValue());
                customTagMap.setMapId(dataParties.get(0).getId());
                customTagMap.setStatus(new Integer(0).byteValue());
                customTagMapList.add(customTagMap);
            }
        }

        //5.将构造号的数据插入CustomTagMap表中
        if(!CollectionUtils.isEmpty(customTagMapList)){
            for(CustomTagMap customTagMap : customTagMapList){
                customTagMapDao.insert(customTagMap);
            }
        }

        //6遍历尚未同步的标签，将同步完成的标签状态置为可用状态
        if(!CollectionUtils.isEmpty(unhandledCustomTagIdList)){
            for(Integer tagId : unhandledCustomTagIdList){
                Integer unhandledDataCount = getUnhandledDataByTagId(tagId);
                if(unhandledDataCount != null && unhandledDataCount == 0){
                    Integer dataCountInTagId = getHandledDataByTagId(tagId);
                    updateCustomTagToNormal(tagId, dataCountInTagId);
                }
            }
        }
    }

    private void updateCustomTagToNormal(Integer tagId, Integer dataCountInTagId) {
        CustomTag customTag = new CustomTag();
        customTag.setId(tagId);
        customTag.setCoverAudienceCount(dataCountInTagId);
        customTag.setStatus(ApiConstant.CUSTOM_TAG_VALIDATE);
        customTagDao.updateById(customTag);
    }

    private Integer getHandledDataByTagId(Integer tagId) {
        CustomTagOriginalDataMap customTagOriginalDataMap = new CustomTagOriginalDataMap();
        customTagOriginalDataMap.setTagId(tagId);
        customTagOriginalDataMap.setStatus(ApiConstant.CUSTOM_TAG_ORIGINAL_DATA_MAP_INVALIDATE);
        return customTagOriginalDataMapDao.selectHandledDataCountByTagId(customTagOriginalDataMap);
    }

    private Integer getUnhandledDataByTagId(Integer tagId) {
        CustomTagOriginalDataMap customTagOriginalDataMap = new CustomTagOriginalDataMap();
        customTagOriginalDataMap.setTagId(tagId);
        customTagOriginalDataMap.setStatus(ApiConstant.CUSTOM_TAG_ORIGINAL_DATA_MAP_VALIDATE);
        return customTagOriginalDataMapDao.selectUnhandledDataCountByTagId(customTagOriginalDataMap);
    }
}
