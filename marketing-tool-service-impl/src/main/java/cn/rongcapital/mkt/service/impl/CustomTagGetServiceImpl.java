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
//        if (customTagList != null && !customTagList.isEmpty()) {
//            for (CustomTag tag : customTagList) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("tag_id", tag.getId());
//                map.put("tag_name", tag.getName());
//                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                String creatTime = "";
//                if(null != tag.getCreateTime()) {
//                	creatTime = format.format(tag.getCreateTime());
//                }
//                map.put("create_time", creatTime);
//                map.put("cover_audience_count", tag.getCoverAudienceCount());
//                map.put("tag_source","上传文件");
//                result.getData().add(map);
//            }
//        }
//
//        Map<String, Object> columnNameA = new HashMap<>();
//        Map<String, Object> columnNameB = new HashMap<>();
//        Map<String, Object> columnNameC = new HashMap<>();
//        Map<String, Object> columnNameD = new HashMap<>();
//        columnNameA.put("col_id", 1);
//        columnNameB.put("col_id", 2);
//        columnNameC.put("col_id", 4);
//        columnNameD.put("col_id", 3);
//
//
//        columnNameA.put("col_code", "tag_name");
//        columnNameB.put("col_code", "create_time");
//        columnNameC.put("col_code", "cover_audience_count");
//        columnNameD.put("col_code", "tag_source");
//
//        columnNameA.put("col_name", "标签名称");
//        columnNameB.put("col_name", "添加时间");
//        columnNameC.put("col_name", "覆盖人群");
//        columnNameD.put("col_name", "标签来源");
//
//        List colNameList = new ArrayList();
//        colNameList.add(columnNameA);
//        colNameList.add(columnNameB);
//        colNameList.add(columnNameC);
//        colNameList.add(columnNameD);
//
//        result.setTotal(result.getData().size());
//        result.setTotalCount(totalCount);
//        result.setColNames(colNameList);
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
