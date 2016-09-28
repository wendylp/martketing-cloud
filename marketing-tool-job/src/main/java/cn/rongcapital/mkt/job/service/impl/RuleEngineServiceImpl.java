package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tagsin.tutils.json.JsonUtils;
import com.tagsin.tutils.okhttp.OkHttpUtil;
import com.tagsin.tutils.okhttp.OkHttpUtil.RequestMediaType;

import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.RuleEngineService;
import okhttp3.Response;

@Service
public class RuleEngineServiceImpl implements RuleEngineService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String URL = "http://ruleengine.cssrv.dataengine.com/api/v1/match";

    private static final String BIZ_TYPE_CODE = "biz_type_code";

    private static final String BIZ_CODE = "biz_code";

    private static final String DATAS = "datas";

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate2;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Boolean requestRuleEngine(List<Integer> keyIds) {
        boolean requestStatus = false;
        try {
            if (!CollectionUtils.isEmpty(keyIds)) {
                for (Integer keyId : keyIds) {
                    // 请求参数集合
                    Map<String, Object> requestMap = new HashMap<String, Object>();
                    requestMap.put(BIZ_TYPE_CODE, "maketing-system-tag");
                    requestMap.put(BIZ_CODE, keyId);
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("keyid", keyId);
                    requestMap.put(DATAS, dataMap);
                    Response response = OkHttpUtil.requestByPost(URL, RequestMediaType.JSON,
                                    JsonUtils.toJson(requestMap));
                    // 返回码
                    int code = response.code();
                    requestStatus = (code > 200 && code <= 206);
                    logger.info("请求规则引擎状态：------------>" + (requestStatus ? "成功" : "失败"));
                }
            }
        } catch (Exception e) {
            logger.error("请求规则引擎出现异常：---------------->" + e.getMessage(), e);
        }
        return requestStatus;
    }


    @Override
    public void synchMongoTagData() {
        try {
            // 初始化数据
            List<Tag> tagList = new ArrayList<>();
            String value = "";
            // 查询结果
            List<Map<String, Object>> queryForList = jdbcTemplate2.queryForList(
                            "SELECT * FROM rsk_result GROUP BY bizCode,ruleCode ORDER BY ruleVersion DESC");
            for (Map<String, Object> map : queryForList) {
                String keyId = (String) map.get("bizCode"); // Mongo中的mId
                String tagName = (String) map.get("ruleCode"); // Mongo中的tag_name
                String result = (String) map.get("result"); // Mongo中三级标签id

                // 查询主数据
                Criteria criteria = Criteria.where("mid").is(Integer.valueOf(keyId));
                DataParty dataParty = mongoTemplate.findOne(new Query(criteria), DataParty.class);
                // 查询推荐标签
                Criteria criteriaAll = Criteria.where("tag_name_eng").is(tagName);
                TagRecommend tagRecommend = mongoTemplate.findOne(new Query(criteriaAll),
                                cn.rongcapital.mkt.po.mongodb.TagRecommend.class);

                if (dataParty != null && tagRecommend != null) {
                    tagList = dataParty.getTagList();
                    // 判断是否数据有误
                    int size = tagRecommend.getTagList().size();
                    int index = Integer.valueOf(result);
                    if (size < index)
                        return;

                    // 标签
                    Tag tag = new Tag();
                    tag.setTagId(tagRecommend.getTagId());
                    tag.setTagName(tagName);
                    value = tagRecommend.getTagList().get(index);
                    tag.setTagValue(value);
                    // TODO groupID需要进行统一匹配
                    tag.setTagGroupId(1);
                    tagList.add(tag);
                }
                if (!CollectionUtils.isEmpty(tagList)) {
                    // 更新插入
                    Update update = new Update().set("tag_list", tagList);
                    logger.info("同步标签数据方法开始执行：----------->标签属性为，Mid:" + keyId + ",标签名称：" + tagName
                                    + "标签值:" + value);
                    mongoTemplate.findAndModify(new Query(criteria), update, DataParty.class);
                }

            }

        } catch (Exception e) {
            logger.error("同步标签数据到MongoDB出现异常：------------------>" + e.getMessage(), e);
        }

    }



}
