package cn.rongcapital.mkt.job.tag.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.MaterialRelatedEvent;
import cn.rongcapital.mkt.po.mongodb.MaterialRelation;
import cn.rongcapital.mkt.service.CustomTagMaterialMapService;
import cn.rongcapital.mkt.vo.in.CustomTagIn;

/*************************************************
 * @功能简述: 自定义标签同步
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2017/2/4
 * @复审人:
 *************************************************/
@Service
public class CustomTagSynServiceImpl implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String REDIS_IDS_KEY_PREFIX = "tagcoverid:";

	private static final Integer REDIS_DB_INDEX = 2;

	@Autowired
	protected MongoTemplate mongoTemplate;

	@Autowired
	private CustomTagMaterialMapService customTagMaterialMapService;

	@Override
	public void task(Integer taskId) {

		List<MaterialRelation> allData = mongoTemplate.findAll(MaterialRelation.class);
		for (MaterialRelation materialRelation : allData) {
			String materialCode = materialRelation.getMaterialCode();
			String materialType = materialRelation.getMaterialType();
			List<CustomTagIn> customeTagList = customTagMaterialMapService.getCustomTagByMaterialCode(materialCode,
					materialType);
			Set<Integer> midList = new HashSet<>();
			Set<String> valueList = new HashSet<>();
			List<MaterialRelatedEvent> eventList = materialRelation.getEventList();
			for (MaterialRelatedEvent materialRelatedEvent : eventList) {
				Integer mid = materialRelatedEvent.getMid();
				if (mid != null) {
					valueList.add(mid.toString());
					midList.add(mid);
				}
			}
			for (CustomTagIn customTag : customeTagList) {
				for (Integer mid : midList) {
					// 是否包含此标签
					long count = mongoTemplate.count(Query.query(Criteria.where("mid").is(mid)
							.and("custom_tag_list.custom_tag_id").is(customTag.getCustomTagId())), DataParty.class);
					if (count > 0) {
						continue;
					}
					mongoTemplate.updateMulti(Query.query(Criteria.where("mid").is(mid)),
							new Update().push("custom_tag_list", customTag), DataParty.class);
				}
				// 将自定义标签保存到Redis中
				saveDataToReids(customTag, valueList);
				// 计算覆盖人数和覆盖人次
				statCount(customTag, eventList, midList);
			}
		}

	}

	/**
	 * 保存数据到Redis
	 * 
	 * @param dataMap
	 */
	private void saveDataToReids(CustomTagIn customTag, Set<String> midList) {
		try {
			String customTagId = customTag.getCustomTagId();
			String key = REDIS_IDS_KEY_PREFIX + customTagId;
			boolean delete = JedisClient.delete(REDIS_DB_INDEX, key);
			logger.info("删除redis数据方法执行结束，key为------>" + key, ",是否成功标识----->" + delete);
			if (!CollectionUtils.isEmpty(midList)) {
				String[] idArray = (String[]) midList.toArray(new String[midList.size()]);
				JedisClient.sadd(REDIS_DB_INDEX, key, idArray);
			}
		} catch (Exception e) {
			logger.error("保存数据到Redis方法出现异常---------->" + e.getMessage(), e);
		}
	}

	/**
	 * 统计标签覆盖人次和覆盖人数
	 * 
	 * @param customeTagList
	 *            自定义标签集合
	 * @param eventList
	 *            事件集合
	 * @param midList
	 *            Mid集合
	 */
	private void statCount(CustomTagIn customTag, List<MaterialRelatedEvent> eventList, Set<Integer> midList) {
		String customTagId = customTag.getCustomTagId();
		if (!CollectionUtils.isEmpty(eventList) && !CollectionUtils.isEmpty(midList)) {
			Query query = Query.query(Criteria.where("custom_tag_id").is(customTagId));
			mongoTemplate.updateFirst(query,
					new Update().inc("cover_number", midList.size()).inc("cover_frequency", eventList.size()),
					CustomTag.class);
		}
	}

}
