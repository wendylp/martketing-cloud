package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.service.CustomtagQrcodeFuzzyListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CustomtagQrcodeFuzzyListOut;

@Service
public class CustomtagQrcodeFuzzyListServiceImpl implements CustomtagQrcodeFuzzyListService {

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    @Autowired
    private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

    /**
     * 功能描述：微信二维码，搜索自定义标签列表
     * 
     * 接口：mkt.customtag.qrcode.fuzzy.list
     * 
     * @param customTagName
     * @param index
     * @param size
     * @return
     */
    @Override
    public BaseOutput customtagQrcodeFuzzyListGet(String customTagName, Integer index, Integer size) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);


        List<CustomTag> customTagLists = mongoCustomTagDao.findByCustomTagNameFuzzy(customTagName, index, size);
        if (CollectionUtils.isNotEmpty(customTagLists)) {
            for (CustomTag customTagList : customTagLists) {

                // 获取分类id和name
                CustomTagCategory customTagCategory =
                        mongoCustomTagCategoryDao.findByChildrenCustomTagList(customTagList.getCustomTagName());
                String customTagCategoryId = "";
                String customTagCategoryName = "";
                if (customTagCategory != null) {
                    customTagCategoryId = customTagCategory.getCustomTagCategoryId();
                    customTagCategoryName = customTagCategory.getCustomTagCategoryName();
                }
                CustomtagQrcodeFuzzyListOut customtagQrcodeFuzzyListOut =
                        new CustomtagQrcodeFuzzyListOut(customTagList.getCustomTagId(),
                                customTagList.getCustomTagName(), customTagCategoryId, customTagCategoryName);
                result.getData().add(customtagQrcodeFuzzyListOut);
            }
            result.setTotal(customTagLists.size());
            result.setTotalCount((int) mongoCustomTagDao.countByCustomTagNameFuzzy(customTagName));
        }
        return result;
    }

}
