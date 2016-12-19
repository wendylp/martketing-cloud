package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SysTagViewDao;
import cn.rongcapital.mkt.dao.TagSqlParamDao;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagSqlParam;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import cn.rongcapital.mkt.service.SystemTagService;
import cn.rongcapital.mkt.service.TagSystemCommonUtilService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagValueElement;
import cn.rongcapital.mkt.vo.in.TagValueUpdateIn;
import cn.rongcapital.mkt.vo.out.TagSystemTreeOut;
import cn.rongcapital.mkt.vo.out.TagSystemTreeTagOut;

@Service
public class SystemTagServiceImpl implements SystemTagService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String ALL_TAG_FLAG = "0"; // 全部标签

	private static final String RECOMMEND_TAG_FLAG = "1"; // 推荐标签

	private static final String ALLTAG_TAB_NAME = "全部标签";

	private static final String RECOMMEND_TAB_NAME = "推荐标签";

	@Autowired
	protected MongoTemplate mongoTemplate;

	@Autowired
	private TagSystemCommonUtilService commonUtilService;

	@Autowired
	private TagValueCountDao tagValueCountDao;

	@Autowired
	private TagSqlParamDao tagSqlParamDao;

	@Autowired
	private SysTagViewDao sysTagViewDao;

	@Override
	public BaseOutput getSystemTagList(String navigateIndex, Integer pageSourceType) {
		BaseOutput output = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		switch (navigateIndex) {
		case ALL_TAG_FLAG:
			output.getData().add(getTagSystemTreeTagOutList(null));
			break;
		case RECOMMEND_TAG_FLAG:
			output.getData().add(getTagSystemTreeTagOutList(Query.query(Criteria.where("flag").is(true))));
			break;
		default:
			output.getData().add(getTagTree(navigateIndex));
			break;
		}

		if(pageSourceType != null && pageSourceType == 1){
			filterTagCoverNonData(output);
		}
		return output;
	}

	private void filterTagCoverNonData(BaseOutput output) {
		if(output == null) return;
		if(CollectionUtils.isNotEmpty(output.getData())){
			List<Object> filteredList = new LinkedList<>();
			for(Object object : output.getData()){
				if(object instanceof TagSystemTreeTagOut){
					if(commonUtilService.isTagCoverData(((TagSystemTreeTagOut) object).getTagId())){
						filteredList.add(object);
					}
				}
			}
			output.setData(filteredList);
		}
	}

	@Override
	public BaseOutput getSystemTagValueList(String tagId, Integer index, Integer size) {
		BaseOutput output = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		List<Object> data = output.getData();
		try {

			TagValueCount tagValueCount = new TagValueCount();
			tagValueCount.setStartIndex(index);
			tagValueCount.setPageSize(size);
			tagValueCount.setTagId(tagId);
			List<TagValueCount> tagList = tagValueCountDao.selectList(tagValueCount);
			List<Map<String, Object>> tagValueList = new ArrayList<>();
			Map<String, Object> tagMap = new HashMap<>();
			for (TagValueCount tag : tagList) {
				String isTag = tag.getIsTag();
				if("0".equals(isTag)){
					Map<String, Object> tagValueMap = new HashMap<>();
					tagValueMap.put("tag_value", tag.getTagValue());
					tagValueMap.put("value_count", tag.getValueCount());
					tagValueList.add(tagValueMap);
				}else{
					tagMap.put("tag_id", tag.getTagId());
					tagMap.put("tag_name", tag.getTagName());
					tagMap.put("tag_desc", tag.getTagDesc());
					tagMap.put("update_flag", tag.getUpdateFlag());
					data.add(tagMap);
				}

			}
			output.setTotalCount(tagValueList.size());
			data.add(tagValueList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取标签值列表方法出现异常--------->"+e.getMessage(),e);
		}
		return output;
	}

	@Override
	public BaseOutput getNativeList() {
		BaseOutput output = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		try {
			List<TagTree> treeList = mongoTemplate.find(Query.query(Criteria.where("level").is(ApiConstant.INT_ONE)),
					TagTree.class);
			List<TagSystemTreeTagOut> resultList = new ArrayList<>();
			resultList.add(new TagSystemTreeTagOut(ALL_TAG_FLAG, ALLTAG_TAB_NAME, null, null));
			resultList.add(new TagSystemTreeTagOut(RECOMMEND_TAG_FLAG, RECOMMEND_TAB_NAME, null, null));
			for (TagTree tagTree : treeList) {
				TagSystemTreeTagOut systemTreeTagOut = new TagSystemTreeTagOut();
				systemTreeTagOut.setTagId(tagTree.getId());
				resultList.add(new TagSystemTreeTagOut(tagTree.getTagId(), tagTree.getTagName(), null, null));
			}
			output.getData().add(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取标签根节点列表方法出现异常---------->"+e.getMessage(),e);
		}
		return output;
	}

	/**
	 * 全部标签展示
	 * 
	 * @param query
	 *            查询条件
	 * @return
	 */
	private List<TagSystemTreeTagOut> getTagSystemTreeTagOutList(Query query) {
		List<TagRecommend> tagRecommendList = mongoTemplate.find(query, TagRecommend.class);
		// 返回结果
		List<TagSystemTreeTagOut> resultList = new ArrayList<>();
		for (TagRecommend tagRecommend : tagRecommendList) {
			String tagId = tagRecommend.getTagId();
			String tagName = tagRecommend.getTagName();
			Boolean flag = tagRecommend.getFlag();
			String tagCover = commonUtilService.getTagCover(tagId);

			resultList.add(new TagSystemTreeTagOut(tagId, tagName, flag, tagCover));
		}
		return resultList;
	}

	/**
	 * 树形结构展示
	 * 
	 * @param rootTagId
	 *            一级分类ID
	 * @return
	 */
	private List<TagSystemTreeOut> getTagTree(String rootTagId) {
		List<TagSystemTreeOut> resultList = new ArrayList<>();
		try {
			// 获取二级标签分类
			TagTree root = mongoTemplate.findOne(
					new Query(Criteria.where("tag_id").is(rootTagId).and("status").is(ApiConstant.INT_ZERO)),
					TagTree.class);
			if (root == null) {
				return null;
			}
			// 获取tag的tag_id列表
			List<String> tagIdLists = root.getChildren();
			for (String parentTagId : tagIdLists) {
				TagTree parentTag = mongoTemplate.findOne(
						new Query(Criteria.where("tag_id").is(parentTagId).and("status").is(ApiConstant.INT_ZERO)),
						TagTree.class);
				List<String> childrenTagIdList = parentTag.getChildren();
				TagSystemTreeOut tagTreeSecondOut = new TagSystemTreeOut(parentTag.getTagId(), parentTag.getTagName(),
						parentTag.getLevel(), new ArrayList<Object>());
				for (String tagId : childrenTagIdList) {
					// 根据tag_id获取tag
					TagRecommend tag = mongoTemplate.findOne(
							new Query(Criteria.where("tag_id").is(tagId).and("status").is(ApiConstant.INT_ZERO)),
							TagRecommend.class);
					if (tag != null) {
						//覆盖率计算
						String tagCover = commonUtilService.getTagCover(tagId);
						TagSystemTreeTagOut tagOut = new TagSystemTreeTagOut(tagId, tag.getTagName(), tag.getFlag(), tag.getTagList(), tagCover);
						tagTreeSecondOut.getChildren().add(tagOut);
					}
				}
				tagTreeSecondOut.setIncludeCount(tagTreeSecondOut.getChildren().size());
				resultList.add(tagTreeSecondOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SystemTagServiceImpl getTagTree method Exception-------->"+e.getMessage(),e);
		}
		return resultList;
	}

	@Override
	public BaseOutput saveUpdateTagValue(TagValueUpdateIn tagValueUpdateIn) {
		BaseOutput output = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		//获取标签Id
		String tagId = tagValueUpdateIn.getTagId();
		List<String> tagList = new ArrayList<>();
		//获取标签值集合
		List<TagValueElement> elements = tagValueUpdateIn.getElements();
		for (TagValueElement tagValueElement : elements) {
			String startValue = tagValueElement.getStartValue();
			String endValue = tagValueElement.getEndValue();
			if(StringUtils.isEmpty(startValue)){
				tagList.add("<"+endValue);
			}else if(StringUtils.isEmpty(endValue)){
				tagList.add(">"+startValue);
			}else{
				tagList.add(startValue + "-" + endValue);
			}
		}

		Query query = new Query(Criteria.where("tag_id").is(tagId));
		TagRecommend tagInformation = mongoTemplate.find(query, TagRecommend.class).get(0);
		Integer tagVersion = tagInformation.getTagVersion();
		List<String> defualtTagList = tagInformation.getTagList();

		 Update update = new Update().set("tag_version", tagVersion+1).set("v"+tagVersion, defualtTagList).set("tag_list", tagList);
	     mongoTemplate.findAndModify(query, update, TagRecommend.class);
	     //更新参数表
	     String tagName = tagInformation.getTagNameEng();
	     int count = tagSqlParamDao.saveOrUpdateData(capsulationParam(tagId, elements,tagName));
	     output.setTotal(count);
	     sysTagViewDao.updateField2ByTagName(tagName);
		return output;
	}

	/**
	 * 封装参数
	 * @param tagId	标签ID
	 * @param elements 修改值集合
	 * @return
	 */
	private TagSqlParam capsulationParam(String tagId,List<TagValueElement> elements,String tagName){
		TagSqlParam tagSqlParam = new TagSqlParam();
		tagSqlParam.setTagId(tagId);
		StringBuilder sb = new StringBuilder();
		for (TagValueElement tagValueElement : elements) {
			String startValue = tagValueElement.getStartValue();
			String endValue = tagValueElement.getEndValue();
			if(StringUtils.isEmpty(startValue)){
				tagSqlParam.setLowerLimitValue(" < "+endValue);
			}else if(StringUtils.isEmpty(endValue)){
				tagSqlParam.setUpperLimitValue(" > "+startValue);
			}else{
				sb.append("BETWEEN " + startValue + " AND " + endValue+",");
			}
		}
		tagSqlParam.setScopeValue(sb.toString());
		return tagSqlParam;
	}





}
