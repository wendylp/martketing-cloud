package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.enums.CustomTagShownEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.FindCustomTagInfoService;
import cn.rongcapital.mkt.vo.out.CustomTagColumnShown;
import cn.rongcapital.mkt.vo.out.CustomTagOutput;
import cn.rongcapital.mkt.vo.out.CustomTagShown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.CustomTagGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CustomTagGetServiceImpl implements CustomTagGetService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FindCustomTagInfoService findCustomTagInfoService;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public BaseOutput getCustomTagList(String method, String userToken, Integer index, Integer size) {
        CustomTagOutput customTagOutput = new CustomTagOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO);
        getShownColumnsInfo(customTagOutput);

        Query countQuery = new Query(Criteria.where("tag_type").is(ApiConstant.CUSTOM_TAG_LEAF_TYPE));
        Long totalTagCount = mongoTemplate.count(countQuery,BaseTag.class);
        customTagOutput.setTotal(totalTagCount.intValue());

        Query tagQuery = new Query(Criteria.where("tag_type").is(ApiConstant.CUSTOM_TAG_LEAF_TYPE)).skip((index -1 ) * size).limit(size);
        List<BaseTag> customTagLeafs = mongoTemplate.find(tagQuery,BaseTag.class);
        if(!CollectionUtils.isEmpty(customTagLeafs)){
            for(BaseTag baseTag : customTagLeafs){
                CustomTagShown customTagShown = new CustomTagShown();
                customTagShown.setTagId(baseTag.getTagId());
                customTagShown.setTagName(baseTag.getTagName());
                customTagShown.setTagSource(baseTag.getSource());
                customTagShown.setCreateTime(DateUtil.getStringFromDate(baseTag.getCreateTime(),"yyyy-MM-dd hh:mm:ss"));
                Query query = new Query(Criteria.where("custom_tag_list").is(baseTag.getTagId()));
                Long audienceCoverCount = mongoTemplate.count(query, DataParty.class);
                customTagShown.setCoverAudienceCount(audienceCoverCount.intValue());
                customTagOutput.getCustomTagShownList().add(customTagShown);
            }
        }

        return customTagOutput;
    }

    private void getShownColumnsInfo(CustomTagOutput customTagOutput) {
        for(CustomTagShownEnum customTagShownEnum : CustomTagShownEnum.values()){
            CustomTagColumnShown customTagColumnShown = new CustomTagColumnShown();
            customTagColumnShown.setColId(customTagShownEnum.getColumnId());
            customTagColumnShown.setColCode(customTagShownEnum.getColumnCode());
            customTagColumnShown.setColName(customTagShownEnum.getColumnName());
            customTagOutput.getCustomTagColumnShownList().add(customTagColumnShown);
        }
    }
}
