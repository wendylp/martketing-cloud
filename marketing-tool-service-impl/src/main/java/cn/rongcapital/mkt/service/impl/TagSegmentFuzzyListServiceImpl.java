package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

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
    public BaseOutput tagSegmentFuzzyListGet(String name) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ONE, null);
        Map<String, Object> map = new HashMap<String, Object>();

        // 设置系统标签搜索结果
        BaseOutput systemTagBaseOutput = systemTagNameFuzzyListGet(name);
        map.put("system_total", systemTagBaseOutput.getTotal());
        map.put("system_total_count", systemTagBaseOutput.getTotalCount());
        map.put("system_tag", systemTagBaseOutput.getData());

        // 设置自定义标签搜索结果
        BaseOutput customTagBaseOutput = customTagNameFuzzyListGet(name);
        map.put("custom_total", customTagBaseOutput.getTotal());
        map.put("custom_total_count", customTagBaseOutput.getTotalCount());
        map.put("custom_tag", customTagBaseOutput.getData());

        result.getData().add(map);

        return result;
    }

    /**
     * 功能描述：获取系统标签模糊搜索
     * 
     * @param name
     * @return
     */
    private BaseOutput systemTagNameFuzzyListGet(String name) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        TagValueCount tagValueCountSelect = new TagValueCount();
        tagValueCountSelect.setTagValue(name);
        tagValueCountSelect.setPageSize(SIZE);
        List<TagValueCount> tagValueCountLists = tagValueCountDao.selectFuzzyTagValue(tagValueCountSelect);


        if (tagValueCountLists != null && tagValueCountLists.size() > 0) {
            // 设置数量

            result.setTotal(tagValueCountLists.size());
            for (TagValueCount tagValueCountList : tagValueCountLists) {
                // 设置输出
                SystemTagSegmentOut systemTagSegmentOut = new SystemTagSegmentOut(tagValueCountList.getTagId(),
                        tagValueCountList.getTagName(), tagValueCountList.getTagValue(), tagValueCountList.getTagPath(),
                        tagValueCountList.getIsTag(), tagValueCountList.getSearchMod(),
                        tagValueCountList.getTagValueSeq());
                result.getData().add(systemTagSegmentOut);
            }
            // 设置总数
            result.setTotalCount(tagValueCountDao.selectFuzzyTagValueCount(tagValueCountSelect));
        }

        return result;
    }

    /**
     * 功能描述： 获取自定义标签模糊搜索
     * 
     * @param name
     * @return
     */
    private BaseOutput customTagNameFuzzyListGet(String name) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        List<CustomTag> customTagLists = mongoCustomTagDao.findByCustomTagNameFuzzyAndCoverNumber(name, SIZE);

        if (CollectionUtils.isNotEmpty(customTagLists)) {
            result.setTotal(customTagLists.size());
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
                result.getData().add(customTagSegmentOut);
            }
            result.setTotalCount(Integer.valueOf((int) mongoCustomTagDao.countByCustomTagNameFuzzyAndCoverNumber(name)));
        }

        return result;
    }

}
