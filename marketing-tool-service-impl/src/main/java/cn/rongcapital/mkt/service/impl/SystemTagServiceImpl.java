package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import cn.rongcapital.mkt.service.SystemTagService;
import cn.rongcapital.mkt.service.TagSystemCommonUtilService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SystemTagIn;
import cn.rongcapital.mkt.vo.in.SystemValueIn;
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

	@Override
	public BaseOutput getSystemTagList(String navigateIndex) {
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
		return output;
	}

	@Override
	public BaseOutput getSystemTagValueList(String tagId, Integer index, Integer size) {
		BaseOutput output = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		try {
			TagValueCount tagValueCount = new TagValueCount();
			tagValueCount.setStartIndex(index);
			tagValueCount.setPageSize(size);
			tagValueCount.setTagId(tagId);
			tagValueCount.setIsTag(String.valueOf(ApiConstant.INT_ZERO));
			List<TagValueCount> tagValueList = tagValueCountDao.selectList(tagValueCount);
			if (!CollectionUtils.isEmpty(tagValueList)) {
				output.setTotalCount(tagValueList.size());
				output.getData().add(tagValueList);
			}
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

}
