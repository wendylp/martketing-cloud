package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.SegmentCustomtagCategoryListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CustomTagCategoryOut;

@Service
public class SegmentCustomtagCategoryListServiceImpl implements SegmentCustomtagCategoryListService {

    @Autowired
    MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Autowired
    MongoCustomTagDao mongoCustomTagDao;

    /**
     * 功能描述：自定义分类列表 去除覆盖人数为零的列表
     * 
     * 接口：mkt.segment.customtag.category.list
     * 
     * @return
     */
    @Override
    public BaseOutput getSegmentCustomTagCategoryList() {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        CustomTagCategory customTagCategory = new CustomTagCategory();

        List<CustomTagCategory> customTagCategoryLists = mongoCustomTagCategoryDao.find(customTagCategory);

        if (CollectionUtils.isNotEmpty(customTagCategoryLists)) {

            for (CustomTagCategory cTagCategory : customTagCategoryLists) {
                // 获取有效且覆盖人数大于零的自定义标签个数
                long customTagCount =
                        mongoCustomTagDao.countValidCustomTagAndCoverNumber(cTagCategory.getChildrenCustomTagList());

                if (customTagCount > 0) {
                    CustomTagCategoryOut customTagCategoryOut =
                            new CustomTagCategoryOut(cTagCategory.getCustomTagCategoryId(),
                                    cTagCategory.getCustomTagCategoryName(), cTagCategory.getLevel(), customTagCount);
                    // 把未分类置顶
                    if (ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID
                            .equals(customTagCategoryOut.getCustomTagCategoryId())) {
                        result.getData().add(0, customTagCategoryOut);
                    } else {
                        result.getData().add(customTagCategoryOut);
                    }
                }
            }
            result.setTotal(result.getData().size());
        }

        return result;
    }

}
