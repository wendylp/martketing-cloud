package cn.rongcapital.mkt.job.tag.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorsOut;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CustomTagMaterialMap;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.MaterialRelatedEvent;
import cn.rongcapital.mkt.po.mongodb.MaterialRelation;
import cn.rongcapital.mkt.service.CustomTagMaterialMapService;
import cn.rongcapital.mkt.tag.service.TagSyncEventObjectService;
import cn.rongcapital.mkt.tag.vo.in.TagSyncEventObjectIn;
import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能简述: 物料事件映射处理
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2017/2/4
 * @复审人:
 *************************************************/
@Service
public class MaterialEventHandleServiceImpl implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String FIELD_CODE = "code";

	private static final String FIELD_ATTRIBUTES = "attributes";

	private static final String FIELD_PUB_ID = "to_user_name";

	private static final String FIELD_OPENID = "openid";

	private static final String NOT_NULL_FIELD = "1";

	private static final String BIT_MAP = "00000011000000000";

	private static final Integer MD_TYPE = 1;

	@Autowired
	private CustomTagMaterialMapService customTagMaterialMapService;

	@Autowired
	private TagSyncEventObjectService eventObjectService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private DataPartyDao dataPartyDao;

	@Override
	public void task(Integer taskId) {
		dataPreprocessing();
	}

	@SuppressWarnings("static-access")
	private void dataPreprocessing() {
		logger.info("MaterialEventHandleServiceImpl dataPreprocessing method is starting------------->");
		try {
			List<MaterialRelation> materialMongoList = new ArrayList<>();
			// 获取所有物料
			List<CustomTagMaterialMap> materialList = customTagMaterialMapService.getAllData();
			for (CustomTagMaterialMap customTagMaterialMap : materialList) {
				String materialCode = customTagMaterialMap.getMaterialCode(); // 物料code
				String materialType = customTagMaterialMap.getMaterialType(); // 物料类型
				Long beginTime = customTagMaterialMap.getCreateTime().getTime();
				Long endTime = new Date().getTime();
				// 事件集合
				List<MaterialRelatedEvent> events = new ArrayList<>();
				// 查询数据是否存在
				Query query = Query
						.query(Criteria.where("material_code").is(materialCode).and("material_type").is(materialType));
				MaterialRelation material = mongoTemplate.findOne(query, MaterialRelation.class);
				if (material != null) {
					beginTime = material.getLastTime();
				}
				TagSyncEventObjectIn eventObjectIn = new TagSyncEventObjectIn();
				eventObjectIn.setUserId(NOT_NULL_FIELD);
				eventObjectIn.setUserToken(NOT_NULL_FIELD);
				eventObjectIn.setVer(NOT_NULL_FIELD);
				eventObjectIn.setQrcodeId(materialCode);
				eventObjectIn.setObjectCode(materialType);
				eventObjectIn.setBeginTime(beginTime);
				eventObjectIn.setEndTime(endTime);

				BaseOutput output = eventObjectService.getEventBehavierListGet(eventObjectIn);
				List<Object> data = output.getData();

				for (Object object : data) {
					// 数据解析
					JSONObject jsonObject = new JSONObject();
					String jsonString = jsonObject.toJSONString(object);
					EventBehaviorsOut eventBehaviorsOut = jsonObject.parseObject(jsonString, EventBehaviorsOut.class);
					String eventCode = (String) eventBehaviorsOut.getEvent().get(FIELD_CODE);
					JSONObject jo = (JSONObject) eventBehaviorsOut.getObject().get(FIELD_ATTRIBUTES);
					String pubId = (String) jo.get(FIELD_PUB_ID);
					String openId = (String) eventBehaviorsOut.getSubject().get(FIELD_OPENID);
					String eventName = eventBehaviorsOut.getEventName();
					String id = eventBehaviorsOut.getId();
					String sourceName = eventBehaviorsOut.getSourceName();
					Long time = eventBehaviorsOut.getTime();
					MaterialRelatedEvent materialRelatedEvent = new MaterialRelatedEvent(id, eventCode, eventName,
							sourceName, midMap(openId, pubId), time);
					events.add(materialRelatedEvent);
				}
				if (material == null) { // 不存在执行插入
					MaterialRelation materialRelation = new MaterialRelation(null, materialCode, materialType, endTime,
							ApiConstant.INT_ZERO, events);
					mongoTemplate.insert(materialRelation);
					materialMongoList.add(materialRelation);
				} else { // 存在执行更新
					mongoTemplate.updateFirst(query,
							new Update().pushAll("event_list", events.toArray()).set("last_time", endTime),
							MaterialRelation.class);
					materialMongoList.add(material);
				}
				// 计算覆盖人次
				MaterialRelation materialRelation = mongoTemplate.findOne(
						Query.query(
								Criteria.where("material_code").is(materialCode).and("material_type").is(materialType)),
						MaterialRelation.class);
				int number = 0;
				if (materialRelation != null) {
					List<MaterialRelatedEvent> eventList = materialRelation.getEventList();
					number = CollectionUtils.isEmpty(eventList) ? number : eventList.size();
				}
				mongoTemplate.updateFirst(
						Query.query(Criteria.where("custom_tag_id").is(customTagMaterialMap.getCustomTagId())),
						new Update().set("cover_frequency", number), CustomTag.class);
			}
			// 数据清理
			clearData(materialMongoList);
			logger.info("MaterialEventHandleServiceImpl dataPreprocessing method was the end------------->");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MaterialEventHandleServiceImpl dataPreprocessing method Exception-------->" + e.getMessage(),
					e);
		}
	}

	/**
	 * mid映射
	 * 
	 * @param openId
	 * @return
	 */
	private Integer midMap(String openId, String pubId) {
		Integer mid = dataPartyDao.getIdByWechartInformation(openId, pubId);
		// 如果数据不存在，执行插入
		if (mid == null) {
			DataParty dataParty = new DataParty();
			dataParty.setWxCode(openId);
			dataParty.setWxmpId(pubId);
			dataParty.setMdType(MD_TYPE);
			dataParty.setBitmap(BIT_MAP);
			dataPartyDao.insert(dataParty);
			mid = dataParty.getId();
		}
		return mid;
	}

	/**
	 * 数据清理
	 * 
	 * @param materialMongoList
	 */
	private void clearData(List<MaterialRelation> materialMongoList) {
		try {
			List<MaterialRelation> allData = mongoTemplate.findAll(MaterialRelation.class);
			List<MaterialRelation> tempList = new ArrayList<>();
			tempList.addAll(allData);
			for (MaterialRelation materialRelation : allData) {
				for (MaterialRelation m : materialMongoList) {
					if (StringUtils.equals(materialRelation.getMaterialCode(), m.getMaterialCode())
							&& StringUtils.equals(materialRelation.getMaterialType(), m.getMaterialType())) {
						tempList.remove(materialRelation);
					}
				}
			}
			for (MaterialRelation materialRelation : tempList) {
				mongoTemplate.remove(materialRelation);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MaterialEventHandleServiceImpl clearData method Exception-------->" + e.getMessage(), e);
		}
	}

}
