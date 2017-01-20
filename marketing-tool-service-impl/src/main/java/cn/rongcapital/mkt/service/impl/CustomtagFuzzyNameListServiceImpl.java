package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomtagFuzzyNameListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CustomtagFuzzyNameListServiceImpl implements CustomtagFuzzyNameListService {

    @Autowired
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    /**
     * 功能描述：添加标签时， 查询已存在类似标签列表
     * 
     * 接口：mkt.customtag.fuzzy.name.list
     * 
     * @param customTagCategoryId
     * @param customTagName
     * @return
     */
    @Override
    public BaseOutput customtagFuzzyNameListGet(String customTagCategoryId, String customTagName) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        CustomTagCategory customTagCategory = mongoCustomTagCategoryDao.findByCategoryId(customTagCategoryId);
        if (customTagCategory != null) {
            List<String> customTagIdLists = customTagCategory.getChildrenCustomTagList();
            if (CollectionUtils.isNotEmpty(customTagIdLists)) {
                // 如果customTagName为空则设置为空
                customTagName = customTagName == null ? "" : customTagName;
                List<CustomTag> customTagLists =
                        mongoCustomTagDao.findByCustomTagIdListAndNameFuzzy(customTagIdLists, customTagName);
                if (CollectionUtils.isNotEmpty(customTagIdLists)) {
                    List<String> data = new ArrayList<String>();
                    for (CustomTag customTagList : customTagLists) {

                        data.add(customTagList.getCustomTagName());
                    }
                    // 把完全匹配的置顶（注意：如果自定义标签重名这里将会导致只能显示一个）
                    if (data.contains(customTagName)) {
                        data.remove(customTagName);
                        data.add(0, customTagName);
                    }
                    result.getData().addAll(data);
                    result.setTotal(data.size());
                }
            }
        }

        return result;
    }

}
