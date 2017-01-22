/*************************************************
 * @功能及特点的描述简述: 事件行为Service Implement
 * 
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: guozhenchao
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-01-10 
 * @date(最后修改日期)：2017-01-10 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.dao.event.EventSourceDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.po.EventSource;
import cn.rongcapital.mkt.event.service.EventBehaviorListService;
import cn.rongcapital.mkt.event.vo.in.EventBehavierListIn;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorOut;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorsOut;
import cn.rongcapital.mkt.material.coupon.service.impl.CouponSaveServiceImpl;
import cn.rongcapital.mkt.po.mongodb.event.EventBehaviors;
import cn.rongcapital.mkt.vo.BaseOutput;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class EventBehaviorListServiceImpl implements EventBehaviorListService {

	private static final String ATTRIBUTES = "object.attributes.";

	private static final Logger logger = LoggerFactory.getLogger(CouponSaveServiceImpl.class);

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private EventDao eventDao;

	@Autowired
	private EventSourceDao eventSourceDao;

	@Autowired
	private EventObjectDao eventObjectDao;

	@Override
	public EventBehaviorOut getEventBehavierList(EventBehavierListIn eventBehavierListIn) {

		EventBehaviorOut eventBehaviorOut = new EventBehaviorOut(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);
		JSONArray attribute = eventBehavierListIn.getAttributes();
		Long eventId = eventBehavierListIn.getEventId();
		Integer index = eventBehavierListIn.getIndex();
		Integer size = eventBehavierListIn.getSize();
		Event event = new Event();
		event.setId(eventId);
		event.setSubscribed(true);
		Event eventDB = eventDao.getEvent(event);
		if (eventDB == null) {
			logger.error("该事件不存在，事件id为：{}", eventId);
			eventBehaviorOut.setCode(ApiErrorCode.EVENT_ERROR_NOT_FOUND_ERROR.getCode());
			eventBehaviorOut.setMsg(ApiErrorCode.EVENT_ERROR_NOT_FOUND_ERROR.getMsg());
			return eventBehaviorOut;
		}
		// 事件源
		Long sourceId = eventDB.getSourceId();
		if (sourceId == null) {
			logger.error("该事件源ID不存在");
			eventBehaviorOut.setCode(ApiErrorCode.EVENT_SOURCE_ID_NOT_FOUND.getCode());
			eventBehaviorOut.setMsg(ApiErrorCode.EVENT_SOURCE_ID_NOT_FOUND.getMsg());
			return eventBehaviorOut;
		}
		EventSource es = new EventSource();
		es.setId(sourceId);
		EventSource esDb = eventSourceDao.getEventSource(es);
		if (esDb == null) {
			logger.error("该事件源不存在，事件源id为：{}", sourceId);
			eventBehaviorOut.setCode(ApiErrorCode.EVENT_SOURCE_ERROR_NOT_FOUND_ERROR.getCode());
			eventBehaviorOut.setMsg(ApiErrorCode.EVENT_SOURCE_ERROR_NOT_FOUND_ERROR.getMsg());
			return eventBehaviorOut;
		}
		// 事件客体
		Long objectId = eventDB.getObjectId();
		if (objectId == null) {
			logger.error("该事件客体不存在");
			eventBehaviorOut.setCode(ApiErrorCode.EVENT_OBJECT_ID_NOT_FOUND.getCode());
			eventBehaviorOut.setMsg(ApiErrorCode.EVENT_OBJECT_ID_NOT_FOUND.getMsg());
			return eventBehaviorOut;
		}
		EventObject eo = new EventObject();
		eo.setId(objectId);
		EventObject eoDb = eventObjectDao.getEventObject(eo);
		if (eoDb == null) {
			logger.error("该事件客体不存在，事件id为：{}", eventId);
			eventBehaviorOut.setCode(ApiErrorCode.EVENT_OBJECT_ERROR_NOT_FOUND_ERROR.getCode());
			eventBehaviorOut.setMsg(ApiErrorCode.EVENT_OBJECT_ERROR_NOT_FOUND_ERROR.getMsg());
			return eventBehaviorOut;
		}
		
		Query query = new Query();
		Query queryAll = new Query();
		Criteria cri = null;
		try {
			if(attribute != null){
				cri = getCriteria(attribute);
				query.addCriteria(cri);
				queryAll.addCriteria(cri);
			}
		} catch (Exception e) {
			logger.error("JSON validation error", e);
			eventBehaviorOut.setCode(ApiErrorCode.VALIDATE_JSON_ERROR.getCode());
			eventBehaviorOut.setMsg(ApiErrorCode.VALIDATE_JSON_ERROR.getMsg());
			return eventBehaviorOut;
		}
		int proIndex = (index == null || index.intValue() == 0) ? 1 : index;
		int proSize = (size == null || size.intValue() == 0) ? 10 : size;
		query.skip((proIndex - 1) * proSize);
		query.limit(proSize);
		List<EventBehaviors> list = mongoOperations.find(query, EventBehaviors.class);
		long totle = mongoOperations.count(queryAll, EventBehaviors.class);
		EventBehaviorsOut out = null;
		for (EventBehaviors eventBehavior : list) {
			eventBehavior.setEventName(eventDB.getName());
			eventBehavior.setSourceName(esDb.getName());
			eventBehavior.setObjectName(eoDb.getName());
			out = new EventBehaviorsOut();
			BeanUtils.copyProperties(eventBehavior, out);
			eventBehaviorOut.getListItems().add(out);
		}
		eventBehaviorOut.setTotal(eventBehaviorOut.getListItems().size());
		eventBehaviorOut.setTotalCount(new Long(totle).intValue());

		return eventBehaviorOut;
	}

	private Criteria getCriteria(JSONArray attribute) {
		Criteria cri = null;
		cri = Criteria.where("subscribed").is(true);
		int conditionSize = attribute.size();
		if (conditionSize > 0) {
			for (int i = 0; i < conditionSize; i++) {
				JSONObject jsObject = attribute.getJSONObject(i);
				String nameKey = jsObject.getString("name");
				String field = ATTRIBUTES + nameKey;
				JSONArray jsonArry = jsObject.getJSONArray("values");
				if (jsonArry.size() > 0) {
					List<String> list = JSONArray.parseArray(jsonArry.toString(), String.class);
					cri.and(field).in(list);
				}
			}
		}
		return cri;
	}

	@Override
	public BaseOutput getEventBehavierListGet(String objectCode, Long qrcodeId, Long beginTime, Long endTime, Integer index, Integer size) {
		
		BaseOutput eventBehaviorOut = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
                null);
		if(beginTime.compareTo(endTime) > 0){
			logger.error("起始时间大于结束时间");
			eventBehaviorOut.setCode(ApiErrorCode.DATE_VALIDATE_ERROR.getCode());
			eventBehaviorOut.setMsg(ApiErrorCode.DATE_VALIDATE_ERROR.getMsg());
			return eventBehaviorOut;
		}
		
		Criteria cri = getCriteria(objectCode, qrcodeId, beginTime, endTime);
		int proIndex = (index == null || index.intValue() == 0) ? 1 : index;
		int proSize = (size == null || size.intValue() == 0) ? 10 : size;
		Query query = new Query();
		query.addCriteria(cri);
		Query queryAll = new Query();
		queryAll.addCriteria(cri);
		query.skip((proIndex - 1) * proSize);
		query.limit(proSize);
		List<EventBehaviors> list = mongoOperations.find(query, EventBehaviors.class);
		long totle = mongoOperations.count(queryAll, EventBehaviors.class);
		for (EventBehaviors eventBehavior : list) {
			eventBehaviorOut.getData().add(eventBehavior);
		}
		eventBehaviorOut.setTotal(eventBehaviorOut.getData().size());
		eventBehaviorOut.setTotalCount(new Long(totle).intValue());

		return eventBehaviorOut;
	}
	
	private Criteria getCriteria(String objectCode, Long qrcodeId,Long startTime, Long endTime) {
		Criteria cri = null;
		cri = Criteria.where("subscribed").is(true);
		cri.and("object.code").is(objectCode).and("object.attributes.qrcode_id").is(qrcodeId).and("time").gte(startTime).lte(endTime);
		return cri;
	}
}
