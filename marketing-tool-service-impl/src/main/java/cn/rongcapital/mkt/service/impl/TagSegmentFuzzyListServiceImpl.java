package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.TagSegmentFuzzyListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CustomTagSegmentOut;
import cn.rongcapital.mkt.vo.out.SystemTagSegmentOut;

@Service
public class TagSegmentFuzzyListServiceImpl implements TagSegmentFuzzyListService {

    private static final Integer SIZE = 10;

    @Autowired
    private TagValueCountDao tagValueCountDao;

    @Autowired
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    /**
     * 功能描述：细分分析---标签搜索
     * 
     * 接口:mkt.tag.segment.fuzzy.list
     * 
     * @param name
     * @return
     */
    @Override
    public BaseOutput tagSegmentFuzzyListService(String name) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ONE, null);
        Map<String, Object> map = new HashMap<String, Object>();

        // 设置系统标签搜索结果
        Map<String, Object> systemTagMap = systemTagNameFuzzyListGet(name);
        map.put("system_total", systemTagMap.get("system_total"));
        map.put("system_total_count", systemTagMap.get("system_total_count"));
        map.put("system_tag", systemTagMap.get("system_tag"));

        // 设置自定义标签搜索结果
        Map<String, Object> customTagMap = customTagNameFuzzyListGet(name);
        map.put("custom_total", customTagMap.get("custom_total"));
        map.put("custom_total_count", customTagMap.get("custom_total_count"));
        map.put("custom_tag", customTagMap.get("custom_tag"));

        result.getData().add(map);

        return result;
    }

    /**
     * 功能描述：获取系统标签模糊搜索
     * 
     * @param name
     * @return
     */
    private Map<String, Object> systemTagNameFuzzyListGet(String name) {

        Map<String, Object> result = new HashMap<String, Object>();
        Integer systemTotal = 0;
        Integer systemTotalCount = 0;

        List<SystemTagSegmentOut> data = new ArrayList<SystemTagSegmentOut>();

        TagValueCount tagValueCountSelect = new TagValueCount();
        tagValueCountSelect.setTagValue(name);
        tagValueCountSelect.setPageSize(SIZE);
        List<TagValueCount> tagValueCountLists = tagValueCountDao.selectFuzzyTagValue(tagValueCountSelect);


        if (tagValueCountLists != null && tagValueCountLists.size() > 0) {
            // 设置数量

            systemTotal = tagValueCountLists.size();
            for (TagValueCount tagValueCountList : tagValueCountLists) {
                // 设置输出
                SystemTagSegmentOut systemTagSegmentOut = new SystemTagSegmentOut(tagValueCountList.getTagId(),
                        tagValueCountList.getTagName(), tagValueCountList.getTagValue(), tagValueCountList.getTagPath(),
                        tagValueCountList.getIsTag(), tagValueCountList.getSearchMod(),
                        tagValueCountList.getTagValueSeq());
                data.add(systemTagSegmentOut);
            }
            // 设置总数
            systemTotalCount = tagValueCountDao.selectFuzzyTagValueCount(tagValueCountSelect);
        }

        result.put("system_total", systemTotal);
        result.put("system_total_count", systemTotalCount);
        result.put("system_tag", data);
        return result;
    }

    /**
     * 功能描述： 获取自定义标签模糊搜索
     * 
     * @param name
     * @return
     */
    private Map<String, Object> customTagNameFuzzyListGet(String name) {
        Map<String, Object> result = new HashMap<String, Object>();
        Integer customTotal = 0;
        Integer customTotalCount = 0;

        List<CustomTagSegmentOut> data = new ArrayList<CustomTagSegmentOut>();

        List<CustomTag> customTagLists = mongoCustomTagDao.findByCustomTagNameFuzzy(name, SIZE);

        if (CollectionUtils.isNotEmpty(customTagLists)) {
            customTotal = customTagLists.size();
            for (CustomTag customTagList : customTagLists) {
                CustomTagCategory customTagCategory =
                        mongoCustomTagCategoryDao.findByChildrenCustomTagList(customTagList.getCustomTagId());
                String tagPath = "";
                String customTagCategoryId = "";
                String customTagCategoryName = "";

                if (customTagCategory != null) {
                    tagPath = customTagCategory.getCustomTagCategoryName() + ">";
                    customTagCategoryId = customTagCategory.getCustomTagCategoryId();
                    customTagCategoryName = customTagCategory.getCustomTagCategoryName();
                }

                CustomTagSegmentOut customTagSegmentOut = new CustomTagSegmentOut(customTagList.getCustomTagId(),
                        customTagList.getCustomTagName(), tagPath, customTagCategoryId, customTagCategoryName);
                data.add(customTagSegmentOut);
            }
            customTotalCount = Integer.valueOf((int) mongoCustomTagDao.countByCustomTagNameFuzzy(name));
        }

        result.put("custom_total", customTotal);
        result.put("custom_total_count", customTotalCount);
        result.put("custom_tag", data);
        return result;
    }

}
