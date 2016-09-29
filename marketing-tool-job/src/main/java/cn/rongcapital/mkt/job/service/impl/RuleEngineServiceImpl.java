package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class RuleEngineServiceImpl implements RuleEngineService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BIZ_TYPE_CODE = "biz_type_code";

    private static final String BIZ_CODE = "biz_code";

    private static final String DATAS = "datas";

    private static final String KEY_ID = "keyid";

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate2;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    Environment env;

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
                    dataMap.put(KEY_ID, keyId);
                    requestMap.put(DATAS, dataMap);
                    String url = env.getProperty("rule.engine.url");
                    Response response = OkHttpUtil.requestByPost(url, RequestMediaType.JSON,
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
            // 获取所有bizCode
            List<Map<String, Object>> bizCodes =
                            jdbcTemplate2.queryForList("SELECT DISTINCT bizCode FROM rsk_result");
            // 获取所有bizCode
            List<Map<String, Object>> ruleCodes =
                            jdbcTemplate2.queryForList("SELECT DISTINCT ruleCode FROM rsk_result");
            for (Map<String, Object> bizMap : bizCodes) {
                List<Tag> tagList = new ArrayList<>();
                String bizCode = (String) bizMap.get("bizCode");
                // 查询主数据
                Criteria criteria = Criteria.where("mid").is(Integer.valueOf(bizCode));
                DataParty dataParty = mongoTemplate.findOne(new Query(criteria), DataParty.class);

                for (Map<String, Object> ruleMap : ruleCodes) {
                    String ruleCode = (String) ruleMap.get("ruleCode");
                    // 查询推荐标签
                    Criteria criteriaAll = Criteria.where("tag_name_eng").is(ruleCode);
                    TagRecommend tagRecommend = mongoTemplate.findOne(new Query(criteriaAll),
                                    cn.rongcapital.mkt.po.mongodb.TagRecommend.class);
                    if (dataParty == null || tagRecommend == null) {
                        continue;
                    }
                    // 查询结果
                    String sql = "SELECT result FROM rsk_result WHERE bizCode = ? AND ruleCode = ?";
                    Object[] param = {bizCode, ruleCode};
                    List<Map<String, Object>> queryForList = jdbcTemplate2.queryForList(sql, param);
                    String result = CollectionUtils.isEmpty(queryForList) ? null
                                    : (String) queryForList.get(queryForList.size() - 1)
                                                    .get("result");
                    // 结果为空，不进行后续打标签操作
                    if (result == null) {
                        continue;
                    }
                    // 判断是否数据有误
                    int size = tagRecommend.getTagList().size();
                    int index = Integer.valueOf(result);
                    if (size <= index) {
                        continue;
                    }
                    // 映射标签值
                    String tagValue = tagRecommend.getTagList().get(index);
                    // 封装标签数据
                    // TODO 目前groupId值为1，后续要改为匹配对应的ID
                    Tag tag = new Tag(tagRecommend.getTagId(), tagRecommend.getTagName(),
                                    tagRecommend.getTagNameEng(), tagValue, 1);
                    tagList.add(tag);
                }
                if (!CollectionUtils.isEmpty(tagList)) {
                    // 更新插入
                    Update update = new Update().set("tag_list", tagList);
                    logger.info("同步标签数据方法开始执行：----------->标签属性为，Mid:" + bizCode);
                    mongoTemplate.findAndModify(new Query(criteria), update, DataParty.class);
                }
            }
        } catch (Exception e) {
            logger.error("同步标签数据到MongoDB出现异常：------------------>" + e.getMessage(), e);
        }

    }



}
