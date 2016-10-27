/*************************************************
 * @功能简述: 获取细分标签漏斗计算
 * @see MktApi：
 * @author: xukun
 * @version: 1.0 @date：2016-06-28
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.GenderEnum;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.SegmentFilterGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.*;
import cn.rongcapital.mkt.vo.out.SegmentAreaCountOut;
import cn.rongcapital.mkt.vo.out.SegmentDimensionCountOut;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.ws.rs.core.SecurityContext;
import java.util.*;

@Service
public class SegmentFilterGetServiceImpl implements SegmentFilterGetService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private String FRONT_PROVINCE_NAME = "北京,天津,上海,重庆,河北,河南,云南,辽宁,黑龙江,湖南,安徽,"
			+ "山东,新疆,江苏,浙江,江西,湖北,广西,甘肃,山西,内蒙古,陕西,吉林,福建,贵州," + "广东,青海,西藏,四川,宁夏,海南,台湾,香港,澳门";

	private final static int selectBatchSize  = 10000;

	@Autowired
	private MongoTemplate mongoTemplate;

	@ReadWrite(type = ReadWriteType.READ)
	@Override
	public BaseOutput getSegmentFilterCount(SegmentFilterCountIn body, SecurityContext securityContext) {
		List<SegmentFilterCondition> conditions = body.getConditions();
		int count = conditions.size();
		BaseOutput result = newSuccessBaseOutput();
		result.setTotal(count);
		for (int i = 0; i < count; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tag_id", conditions.get(i).getTagId());
			map.put("tag_name", conditions.get(i).getTagName());
			String tag_count = getPeopleCount(i, conditions);
			map.put("tag_count", tag_count);
			result.getData().add(map);
		}
		return result;
	}

	@Override
	public BaseOutput getSegmentFilterSum(SegmentFilterSumIn body) {
		BaseOutput result = newSuccessBaseOutput();
		List<SegmentFilterSumCondition> orGroups = body.getGroups();
		if (CollectionUtils.isEmpty(orGroups)) {
			return result;
		}

		List<Criteria> orCriteriaList = new ArrayList<>();
		for (SegmentFilterSumCondition segmentFilterSumCondition : orGroups) {
			List<SegmentFilterCondition> conditions = segmentFilterSumCondition.getConditions();
			if (CollectionUtils.isEmpty(conditions)) {
				continue;
			}
			orCriteriaList.add(getConditionCriteria(conditions));
		}
		if (CollectionUtils.isEmpty(orCriteriaList)) {
			return result;
		}
		Criteria[] orCriteriaArray = new Criteria[orCriteriaList.size()];
		orCriteriaList.toArray(orCriteriaArray);
		Criteria allCriteria = new Criteria();
		allCriteria.orOperator(orCriteriaArray);
		long totalCount = mongoTemplate.count(new Query(allCriteria), DataParty.class);
		result.setTotal(Integer.parseInt(String.valueOf(totalCount)));
		return result;
	}

	private Criteria getConditionCriteria(List<SegmentFilterCondition> conditions) {
		Criteria criteria = new Criteria();
		if (CollectionUtils.isEmpty(conditions)) {
			return criteria;
		}
		List<Criteria> andCriterias = new ArrayList<>();
		for (SegmentFilterCondition tempSegmentFilterCondition : conditions) {
			String tagId = tempSegmentFilterCondition.getTagId();
			String tagGroupId = tempSegmentFilterCondition.getTagGroupId();
			String excule = tempSegmentFilterCondition.getExclude();
			Criteria oneCriteria = null;
			if ("1".equals(excule)) {
				if ("0".equals(tagId)) {// 不限
					oneCriteria = Criteria.where("tagList").elemMatch(Criteria.where("tagId").ne(tagGroupId));
				} else {

					String tagIndex = tagId.substring(tagId.indexOf("_") + 1);

					TagRecommend findOne = mongoTemplate.findOne(new Query(Criteria.where("tagId").is(tagGroupId)),
							TagRecommend.class);
					List<String> tagList = findOne.getTagList();
					String tagValue = tagList.get(Integer.valueOf(tagIndex));
					oneCriteria = Criteria.where("tagList")
							.elemMatch(Criteria.where("tagId").ne(tagGroupId).and("tagValue").ne(tagValue));
				}
			} else {
				if ("0".equals(tagId)) {// 不限
					oneCriteria = Criteria.where("tagList").elemMatch(Criteria.where("tagId").is(tagGroupId));
				} else {

					String tagIndex = tagId.substring(tagId.indexOf("_") + 1);

					TagRecommend findOne = mongoTemplate.findOne(new Query(Criteria.where("tagId").is(tagGroupId)),
							TagRecommend.class);
					List<String> tagList = findOne.getTagList();
					String tagValue = tagList.get(Integer.valueOf(tagIndex));

					oneCriteria = Criteria.where("tagList")
							.elemMatch(Criteria.where("tagId").is(tagGroupId).and("tagValue").is(tagValue));
				}
			}

			if (andCriterias != null) {
				andCriterias.add(oneCriteria);
			}
		}

		if (!CollectionUtils.isEmpty(andCriterias)) {
			Criteria[] criteriaArray = new Criteria[andCriterias.size()];
			andCriterias.toArray(criteriaArray);
			criteria.andOperator(criteriaArray);
		}
		return criteria;
	}

	@Override
	public BaseOutput segmentGenderCountList(SegmentCountFilterIn input) {
		BaseOutput result = newSuccessBaseOutput();
		Criteria midCriteria = getDataPartyCriteria(input.getSegmentHeadIds());
		if (midCriteria == null) {
			return result;
		}
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(midCriteria),
				Aggregation.group("gender").count().as("populationCount").first("gender").as("dimensionName"));
		AggregationResults<SegmentDimensionCountOut> aggregationResults = mongoTemplate.aggregate(aggregation,
				DataParty.class, SegmentDimensionCountOut.class);
		List<SegmentDimensionCountOut> segmentDimensionCountOutList = aggregationResults.getMappedResults();
		int maleCount = 0;
		int femaleCount = 0;
		int otherCount = 0;
		if (!CollectionUtils.isEmpty(segmentDimensionCountOutList)) {
			for (SegmentDimensionCountOut dimensionCountOut : segmentDimensionCountOutList) {
				Integer populationCount = dimensionCountOut.getPopulationCount();
				if (populationCount == null || populationCount.intValue() < 1) {
					continue;
				}
				String gender = dimensionCountOut.getDimensionName();
				if (StringUtils.hasText(gender)) {
					int genderInt = Integer.parseInt(gender);
					if (GenderEnum.MALE.getStatusCode().intValue() == genderInt) {
						maleCount += populationCount.intValue();
					} else if (GenderEnum.FEMALE.getStatusCode().intValue() == genderInt) {
						femaleCount += populationCount.intValue();
					} else {
						otherCount += populationCount.intValue();
					}
				} else {
					otherCount += populationCount.intValue();
				}
			}
		}

		List<SegmentDimensionCountOut> formatSegmentDimensionOut = new ArrayList<>();
		formatSegmentDimensionOut.add(new SegmentDimensionCountOut(GenderEnum.MALE.getDescription(), maleCount));
		formatSegmentDimensionOut.add(new SegmentDimensionCountOut(GenderEnum.FEMALE.getDescription(), femaleCount));
		formatSegmentDimensionOut.add(new SegmentDimensionCountOut(GenderEnum.UNSURE.getDescription(), otherCount));
		setBaseOut(result, formatSegmentDimensionOut);
		return result;
	}

	@Override
	public BaseOutput segmentProvinceCountList(SegmentCountFilterIn input) {
		BaseOutput result = newSuccessBaseOutput();
		Criteria midCriteria = getDataPartyCriteria(input.getSegmentHeadIds());
		if (midCriteria == null) {
			return result;
		}
		
		long start = System.currentTimeMillis();
		logger.info("=============细分管理地区查询开始=========================");
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(midCriteria),
				Aggregation.group("provice").count().as("populationCount").first("provice").as("dimensionName"),
				Aggregation.sort(Sort.Direction.DESC, "populationCount"));
		AggregationResults<SegmentDimensionCountOut> aggregationResults = mongoTemplate.aggregate(aggregation,
				DataParty.class, SegmentDimensionCountOut.class);
		List<SegmentDimensionCountOut> provinceCountOutList = aggregationResults.getMappedResults();
		List<SegmentDimensionCountOut> formartedProvinceCountOutList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(provinceCountOutList)) {
			for (SegmentDimensionCountOut tempSegmentDimensionCountOut : provinceCountOutList) {
				String dimensionName = tempSegmentDimensionCountOut.getDimensionName();
				if (!StringUtils.hasText(dimensionName) || dimensionName.trim().length() < 2) {
					continue;
				}
				int startIndex = FRONT_PROVINCE_NAME.indexOf(dimensionName.substring(0, 2));
				if (startIndex == -1) {
					continue;
				}
				int endIdex = FRONT_PROVINCE_NAME.indexOf(",", startIndex);
				if (endIdex == -1) {
					endIdex = FRONT_PROVINCE_NAME.length();
				}
				String frontProvinceName = FRONT_PROVINCE_NAME.substring(startIndex, endIdex);
				SegmentDimensionCountOut validProvinceCountOut = new SegmentDimensionCountOut();
				validProvinceCountOut.setDimensionName(frontProvinceName);
				validProvinceCountOut.setPopulationCount(tempSegmentDimensionCountOut.getPopulationCount());
				formartedProvinceCountOutList.add(validProvinceCountOut);
			}
		}

		Aggregation areaAggregation = Aggregation.newAggregation(Aggregation.match(midCriteria), Aggregation
				.group("citizenship").count().as("populationCount").first("citizenship").as("dimensionName"));
		AggregationResults<SegmentDimensionCountOut> areaAggregationResults = mongoTemplate.aggregate(areaAggregation,
				DataParty.class, SegmentDimensionCountOut.class);
		List<SegmentDimensionCountOut> areaCountOutList = areaAggregationResults.getMappedResults();

		// merge area and province result
		int chinaCount = 0;
		int foreignCount = 0;
		if (!CollectionUtils.isEmpty(areaCountOutList)) {
			for (SegmentDimensionCountOut tempSegmentAreaCountOut : areaCountOutList) {
				String citizenship = tempSegmentAreaCountOut.getDimensionName();
				Integer populationCount = tempSegmentAreaCountOut.getPopulationCount();
				if (populationCount == null) {
					continue;
				}
				if (citizenship != null && ApiConstant.NATIONALITY_CHINA.equals(citizenship.trim())) {
					chinaCount += populationCount.intValue();
				} else {
					foreignCount += populationCount.intValue();
				}
			}
		}

		SegmentAreaCountOut segmentAreaCountOut = new SegmentAreaCountOut();
		segmentAreaCountOut.setChinaPopulationCount(chinaCount);
		segmentAreaCountOut.setForeignPopulationCount(foreignCount);
		segmentAreaCountOut.setProvinceList(formartedProvinceCountOutList);
		result.setTotal(1);
		result.getData().add(segmentAreaCountOut);
		long end = System.currentTimeMillis();
		
		logger.info("=============细分管理地区查询结束,用时:"+(end-start)+"毫秒");
		return result;
	}

	@Override
	public BaseOutput segmentReceiveCountList(SegmentCountFilterIn input) {
		BaseOutput result = newSuccessBaseOutput();
		Criteria midCriteria = getDataPartyCriteria(input.getSegmentHeadIds());
		if (midCriteria == null) {
			return result;
		}
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(midCriteria), Aggregation
				.group("receiveCount").count().as("populationCount").first("receiveCount").as("dimensionName"));
		AggregationResults<SegmentDimensionCountOut> aggregationResults = mongoTemplate.aggregate(aggregation,
				DataParty.class, SegmentDimensionCountOut.class);
		List<SegmentDimensionCountOut> receiveCountList = aggregationResults.getMappedResults();
		int[] countArray = new int[5];
		if (!CollectionUtils.isEmpty(receiveCountList)) {
			for (SegmentDimensionCountOut tempSegmentDimensionCountOut : receiveCountList) {
				Integer populationCount = tempSegmentDimensionCountOut.getPopulationCount();
				if (populationCount == null) {
					continue;
				}
				String receiveCountStr = tempSegmentDimensionCountOut.getDimensionName();
				if (StringUtils.hasText(receiveCountStr)) {
					int receiveCount = Integer.parseInt(receiveCountStr);
					if (receiveCount == 1) {
						countArray[0] += populationCount.intValue();
					} else if (receiveCount == 2) {
						countArray[1] += populationCount.intValue();
					} else if (receiveCount == 3) {
						countArray[2] += populationCount.intValue();
					} else if (receiveCount == 4) {
						countArray[3] += populationCount.intValue();
					} else if (receiveCount >= 5) {
						countArray[4] += populationCount.intValue();
					}
				}
			}
		}
		List<SegmentDimensionCountOut> formatedDimensionCountOut = new ArrayList<>();
		for (int i = 0; i < countArray.length; i++) {
			String timesStr = (i + 1) + "次";
			if (i == countArray.length - 1) {
				timesStr = (i + 1) + "+次";
			}
			formatedDimensionCountOut.add(new SegmentDimensionCountOut(timesStr, countArray[i]));
		}
		setBaseOut(result, formatedDimensionCountOut);
		return result;
	}

	private Criteria getDataPartyCriteria(List<Integer> segmentHeadIds) {
		if (CollectionUtils.isEmpty(segmentHeadIds)) {
			return null;
		}
		
		long start = System.currentTimeMillis();
		logger.info("=================================查询细分管理主数据开始================================");

		List<Segment> segmentLists = new ArrayList<Segment>();

		Query query = new Query(Criteria.where("segmentationHeadId").in(segmentHeadIds));

		int totalSize = (int) mongoTemplate.count(query, Segment.class);
		int pageSize = selectBatchSize;
		int totalPages = (totalSize + pageSize - 1) / pageSize;

		logger.info("=========分批查询细分管理主数据共" + totalSize + "条，分为" + totalPages+ "页===============");

		for (int i = 0; i < totalPages; i++) {

			Query queryChild = new Query(Criteria.where("segmentationHeadId").in(segmentHeadIds)).skip(i * pageSize).limit(pageSize);
			List<Segment> segmentList = mongoTemplate.find(queryChild, Segment.class);
			segmentLists.addAll(segmentList);

		}

		long end = System.currentTimeMillis();
		logger.info("=========查询细分管理主数据结束,查询数据总:" + segmentLists.size() + "条,用时:" + (end - start) + "毫秒=================");

		Criteria midCriteria = Criteria.where("mid");
		Set<Integer> dataPartyIdSet = new HashSet<>();
		for (Segment segment : segmentLists) {
			dataPartyIdSet.add(segment.getDataId());
		}
		midCriteria.in(dataPartyIdSet);
		return midCriteria;
	}

	private String getPeopleCount(int idx, List<SegmentFilterCondition> conditions) {
		Query query = new Query();
		Criteria[] criterialist = new Criteria[idx + 1];
		for (int i = 0; i < idx + 1; i++) {
			String tagId = conditions.get(i).getTagId();
			String tagGroupId = conditions.get(i).getTagGroupId();
			// 根据getExclude()拼条件
			if ("1".equals(conditions.get(i).getExclude())) {
				Criteria criteriaCond = null;
				if ("0".equals(tagId)) {// 不限
					criteriaCond = Criteria.where("tagList").elemMatch(Criteria.where("tagId").ne(tagGroupId));
				} else {
					String tagIndex = tagId.substring(tagId.indexOf("_") + 1);

					TagRecommend findOne = mongoTemplate.findOne(new Query(Criteria.where("tagId").is(tagGroupId)),
							TagRecommend.class);
					List<String> tagList = findOne.getTagList();
					String tagValue = tagList.get(Integer.valueOf(tagIndex));

					criteriaCond = Criteria.where("tagList")
							.elemMatch(Criteria.where("tagId").ne(tagGroupId).and("tagValue").ne(tagValue));
				}
				criterialist[i] = criteriaCond;
			} else {
				Criteria criteriaCond = null;
				if ("0".equals(tagId)) {// 不限
					criteriaCond = Criteria.where("tagList").elemMatch(Criteria.where("tagId").is(tagGroupId));
				} else {
					String tagIndex = tagId.substring(tagId.indexOf("_") + 1);
					TagRecommend findOne = mongoTemplate.findOne(new Query(Criteria.where("tagId").is(tagGroupId)),
							TagRecommend.class);
					List<String> tagList = findOne.getTagList();
					String tagValue = tagList.get(Integer.valueOf(tagIndex));

					criteriaCond = Criteria.where("tagList")
							.elemMatch(Criteria.where("tagId").is(tagGroupId).and("tagValue").is(tagValue));
				}
				criterialist[i] = criteriaCond;
			}
		}

		query.addCriteria(new Criteria().andOperator(criterialist));
		long tag_count = mongoTemplate.count(query, DataParty.class);
		return Long.toString(tag_count);
	}

	private BaseOutput newSuccessBaseOutput() {
		return new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
	}

	private <O> void setBaseOut(BaseOutput out, List<O> dataList) {
		if (CollectionUtils.isEmpty(dataList)) {
			return;
		}

		out.setTotal(dataList.size());
		out.getData().addAll(dataList);
	}

}
