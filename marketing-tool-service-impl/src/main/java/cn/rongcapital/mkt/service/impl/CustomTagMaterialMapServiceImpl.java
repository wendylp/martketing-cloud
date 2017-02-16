package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CustomTagMaterialMapDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.CustomTagMaterialMap;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.MaterialRelatedEvent;
import cn.rongcapital.mkt.po.mongodb.MaterialRelation;
import cn.rongcapital.mkt.service.CustomTagActionService;
import cn.rongcapital.mkt.service.CustomTagMaterialMapService;
import cn.rongcapital.mkt.vo.in.CustomTagIn;

@Service
public class CustomTagMaterialMapServiceImpl implements CustomTagMaterialMapService {

	@Autowired
	private CustomTagMaterialMapDao customTagMaterialMapDao;

	@Autowired
	private CustomTagActionService customTagActionService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MongoCustomTagDao customTagDao;

	@Autowired
	private MongoCustomTagCategoryDao customTagCategoryDao;

	@Override
	public void buildTagMaterialRealation(List<CustomTagIn> customTagInList, String materialCode, String materialType) {
		List<String> customTagIdList = new ArrayList<>();
		Date currentDate = new Date();
		for (CustomTagIn customTagIn : customTagInList) {
			String customTagId = customTagIn.getCustomTagId(); // 自定义标签ID
			String customTagName = customTagIn.getCustomTagName(); // 自定义标签名称
			if (StringUtils.isEmpty(customTagId)) {
				// 标签插入
				List<String> customTags = new ArrayList<>();
				customTags.add(customTagName);
				customTagId = customTagActionService
						.insertCustomTagListIntoDefaultCategory((ArrayList<String>) customTags).get(0).getCustomTagId();
			}
			// 封装
			CustomTagMaterialMap customTagMaterialMap = new CustomTagMaterialMap(customTagId, customTagName,
					materialCode, materialType, ApiConstant.INT_ZERO, currentDate);
			if (CollectionUtils.isEmpty(customTagMaterialMapDao.selectList(
					new CustomTagMaterialMap(customTagId, materialCode, materialType, ApiConstant.INT_ZERO)))) {
				// 插入数据
				customTagMaterialMapDao.insert(customTagMaterialMap);
			}
			customTagIdList.add(customTagId);
		}
		// 查询解绑标签
		List<CustomTagMaterialMap> unbindTagList = new ArrayList<>();
		if (CollectionUtils.isEmpty(customTagIdList)) {
			unbindTagList = customTagMaterialMapDao
					.selectList(new CustomTagMaterialMap(null, materialCode, materialType, null));
		} else {
			unbindTagList = customTagMaterialMapDao.getUnbindTag(materialCode, materialType, customTagIdList);
		}

		// 数据初始化
		initData(unbindTagList);
	}

	@Override
	public List<CustomTagIn> getCustomTagByMaterialCode(String materialCode, String materialType) {
		List<CustomTagIn> resultList = new ArrayList<>();
		List<String> customTagIdList = customTagMaterialMapDao.getCustomTagIdByMaterialParam(materialCode,
				materialType);
		for (String customTagId : customTagIdList) {
			CustomTag customTag = customTagDao.getCustomTagByTagId(customTagId);
			CustomTagCategory customTagCategory = customTagCategoryDao.findByChildrenCustomTagList(customTagId);
			if (customTagCategory == null) { // 为Null表示错误数据
				continue;
			}
			resultList.add(new CustomTagIn(customTagId, customTag.getCustomTagName(), customTag.getParentId(),
					customTagCategory.getCustomTagCategoryName(), customTag.getIsDeleted(), materialCode,
					materialType));
		}
		return resultList;
	}

	@Override
	public List<CustomTagMaterialMap> getAllData() {
		return customTagMaterialMapDao.getAllData();
	}

	/**
	 * 数据初始化（解绑标签人数初始化及数据清理）
	 * 
	 * @param unbindTagList
	 *            解绑标签
	 */
	private void initData(List<CustomTagMaterialMap> unbindTagList) {
		for (CustomTagMaterialMap customTagMaterialMap : unbindTagList) {
			String customTagId = customTagMaterialMap.getCustomTagId(); // 自定义标签ID
			String materialCode = customTagMaterialMap.getMaterialCode(); // 物料code
			String materialType = customTagMaterialMap.getMaterialType(); // 物料类型
			// 将标签从人身上移除
			CustomTagIn customTagIn = new CustomTagIn();
			customTagIn.setCustomTagId(customTagId);
			customTagIn.setMaterialCode(materialCode);
			customTagIn.setMaterialType(materialType);
			mongoTemplate.updateMulti(null, new Update().pull("custom_tag_list", customTagIn), DataParty.class);
			// 初始化人数
			// 计算覆盖人数
			Long coverNumber = mongoTemplate.count(new Query(
					Criteria.where("custom_tag_list").elemMatch(Criteria.where("custom_tag_id").is(customTagId))),
					DataParty.class);
			// 计算覆盖人次
			int frequencyCount = 0;
			MaterialRelation materialRelation = mongoTemplate.findOne(
					Query.query(Criteria.where("material_code").is(materialCode).and("material_type").is(materialType)),
					MaterialRelation.class);
			if (materialRelation != null) {
				List<MaterialRelatedEvent> eventList = materialRelation.getEventList();
				frequencyCount = CollectionUtils.isEmpty(eventList) ? 0 : eventList.size();
			}
			// TODO 计算覆盖人次
			Query query = Query.query(Criteria.where("custom_tag_id").is(customTagId));
			mongoTemplate.updateMulti(query,
					new Update().set("cover_number", coverNumber).set("cover_frequency", frequencyCount),
					CustomTag.class);
			// 数据清理
			customTagMaterialMapDao.deleteById(customTagMaterialMap);
		}
	}

}
