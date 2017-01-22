package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomtagCategoryLessListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CustomtagCategoryLessListServiceImpl implements CustomtagCategoryLessListService {

    @Autowired
    MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    /**
     * 功能描述：未分类标签添加到指定分类，分类列表查询(不显示未分类)
     * 
     * 接口：mkt.customtag.category.less.list
     * 
     * @return
     */
    @Override
    public BaseOutput customtagCategoryLessListGet() {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        CustomTagCategory customTagCategory = new CustomTagCategory();
        List<CustomTagCategory> customTagCategoryLists = mongoCustomTagCategoryDao.find(customTagCategory);

        if (CollectionUtils.isNotEmpty(customTagCategoryLists)) {
            for (CustomTagCategory customTagCategoryList : customTagCategoryLists) {

                // 过滤未分类
                if (!ApiConstant.CUSTOM_TAG_DEFAULT_CATEGORY_ID
                        .equals(customTagCategoryList.getCustomTagCategoryId())) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("custom_tag_category_id", customTagCategoryList.getCustomTagCategoryId());
                    map.put("custom_tag_category_name", customTagCategoryList.getCustomTagCategoryName());
                    result.getData().add(map);
                }
            }
            result.setTotal(result.getData().size());
        }
        return result;
    }

}
