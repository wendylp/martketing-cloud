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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tagsin.tutils.json.JsonUtils;
import com.tagsin.tutils.okhttp.OkHttpUtil;
import com.tagsin.tutils.okhttp.OkHttpUtil.RequestMediaType;

import cn.rongcapital.mkt.po.RuleEngineResult;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.RuleEngineService;
import cn.rongcapital.mkt.service.SynchroMongodbCityService;
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

	@Autowired
	private SynchroMongodbCityService synchroMongodbCityService;

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
					logger.info("----------调用规则规则引擎----id:" + keyId + "返回code:" + code);
					requestStatus = (code > 200 && code <= 206);
					logger.info("----------调用规则规则引擎----requestStatus:" + requestStatus);

					logger.info("");
					if (requestStatus) {
						// 同步标签
						sychMongoData(keyId.toString());
					}
				}
			}
		} catch (Exception e) {
			logger.error("请求规则引擎出现异常：---------------->" + e.getMessage(), e);
		}
		logger.info("----------requestRuleEngine----end----");
		return requestStatus;
	}

	private void sychMongoData(String keyId) {
		try {
			List<Tag> tagList = new ArrayList<>(); // 标签集合
			// 查询主数据
			Criteria criteria = Criteria.where("mid").is(Integer.valueOf(keyId));
			//获取规则引擎结果请求路径
			String resultUrl = env.getProperty("rule.engine.result.url");

			Response response = OkHttpUtil.requestByGet(resultUrl + keyId);
			String responseString = response.body().string();

			JSONObject parseObject = JSONObject.parseObject(responseString);
			JSONObject jsonObject = parseObject.getJSONObject("score");
			JSONArray jsonArray = jsonObject.getJSONArray("matchresults");
			List<RuleEngineResult> resultList = JSONArray.parseArray(jsonArray.toString(), RuleEngineResult.class);
			for (RuleEngineResult res : resultList) {
				String ruleCode = res.getRule_code();
				Integer result = res.getResult();
				if (result == null) {
					continue;
				}
				// 查询推荐标签
				Criteria criteriaAll = Criteria.where("tag_name_eng").is(ruleCode);
				TagRecommend tagRecommend = mongoTemplate.findOne(new Query(criteriaAll),
						cn.rongcapital.mkt.po.mongodb.TagRecommend.class);
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
				Tag tag = new Tag(tagRecommend.getTagId(), tagRecommend.getTagName(), tagRecommend.getTagNameEng(),
						tagValue, 1);
				tagList.add(tag);
			}
			// 获取人员的属性标签
			Map<String, Object> tagMap = synchroMongodbCityService.synchroMongodbCity(Integer.valueOf(keyId));
			if (tagMap != null) {
				Tag cityTag = (Tag) tagMap.get("city");
				if (cityTag != null) {
					tagList.add(cityTag);
				}
				Tag sexTag = (Tag) tagMap.get("sex");
				if (sexTag != null) {
					tagList.add(sexTag);
				}
				Tag mediaTrenchGeneraTag = (Tag) tagMap.get("mediaTrenchGenera");
				if (mediaTrenchGeneraTag != null) {
					tagList.add(mediaTrenchGeneraTag);
				}
				Tag mediaNameTag = (Tag) tagMap.get("mediaName");
				if (mediaNameTag != null) {
					tagList.add(mediaNameTag);
				}
				logger.info("属性类标签查询结果为：------------------->" + tagMap);
			}
			if (!CollectionUtils.isEmpty(tagList)) {
				// 更新插入
				Update update = new Update().set("tag_list", tagList);
				// logger.info("同步标签数据方法开始执行：----------->标签属性为，Mid:" + bizCode);
				DataParty dp = mongoTemplate.findAndModify(new Query(criteria), update, DataParty.class);
				logger.info("打标签操作执行结束：-------------------------->更新的人员为：" + dp.getMid());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
