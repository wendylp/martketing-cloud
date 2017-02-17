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
import cn.rongcapital.mkt.service.TagCampaignFuzzyListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CustomTagCampaignOut;
import cn.rongcapital.mkt.vo.out.SystemTagCampaignOut;

@Service
public class TagCampaignFuzzyListServiceImpl implements TagCampaignFuzzyListService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final Integer SIZE = 10;

    @Autowired
    private TagValueCountDao tagValueCountDao;

    @Autowired
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    /**
     * 功能描述：标签查询、 自定义标签查询 活动编排
     * 
     * 接口：mkt.tag.campaign.fuzzy.list
     * 
     * @param name
     * @return
     */
    @Override
    public BaseOutput tagCampaignFuzzyListGet(String name) {
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
        // 设置只搜索标签值
        tagValueCountSelect.setIsTag("0");
        tagValueCountSelect.setPageSize(SIZE);
        List<TagValueCount> tagValueCountLists = tagValueCountDao.selectFuzzyTagValueAll(tagValueCountSelect);


        if (tagValueCountLists != null && tagValueCountLists.size() > 0) {
            // 设置数量
            result.setTotal(tagValueCountLists.size());

            for (TagValueCount tagValueCountList : tagValueCountLists) {
                // 设置输出
                SystemTagCampaignOut systemTagCampaignOut = new SystemTagCampaignOut(tagValueCountList.getTagId(),
                        tagValueCountList.getTagName(), tagValueCountList.getTagValue(), tagValueCountList.getTagPath(),
                        tagValueCountList.getTagValueSeq());
                result.getData().add(systemTagCampaignOut);
            }

            // 设置总数
            result.setTotalCount(tagValueCountDao.selectFuzzyTagValueCountAll(tagValueCountSelect));
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

        List<CustomTag> customTagLists = mongoCustomTagDao.findByCustomTagNameFuzzyAndCoverNumberAll(name, SIZE);

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

                CustomTagCampaignOut customTagCampaignOut = new CustomTagCampaignOut(customTagList.getCustomTagId(),
                        customTagList.getCustomTagName(), tagPath, customTagCategoryId, customTagCategoryName);
                result.getData().add(customTagCampaignOut);
            }
            result.setTotalCount(Integer.valueOf((int) mongoCustomTagDao.countByCustomTagNameFuzzyAndCoverNumberAll(name)));
        }

        return result;
    }
}
