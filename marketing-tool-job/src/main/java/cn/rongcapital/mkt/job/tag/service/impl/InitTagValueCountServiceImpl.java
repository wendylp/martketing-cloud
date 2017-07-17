package cn.rongcapital.mkt.job.tag.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.job.service.SystemTagSynchService;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.po.mongodb.TagTree;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/*************************************************
 * @功能简述: 初始化标签值数量
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.2
 * @date: 2016/11/09
 * @复审人:
 *************************************************/

@Service
public class InitTagValueCountServiceImpl implements TaskService,SystemTagSynchService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String IS_TAGVALUE = "0";
	
	private static final String IS_TAG = "1";
	
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private TagValueCountDao tagValueCountDao;
	
	@Override
	public void task(Integer taskId) {
		logger.info("同步MySQL中tag_value_count标签统计表，任务开始执行------------>");
		initTagValueCount(null);
		logger.info("同步MySQL中tag_value_count标签统计表，任务执行结束------------>");
	}

	@Override
	public void initTagValueCount(String targetTagId) {
		// TODO Auto-generated method stub
		try {
			List<TagRecommend> tagRecommendList = new ArrayList<>();
			if(targetTagId == null){
				//清空存量数据
				tagValueCountDao.clearStockData();
				//从Mongo中获取所有Tag
				tagRecommendList = mongoTemplate.find(new Query(Criteria.where("tagId").ne(null)), TagRecommend.class);
			}else{
				//清除指定数据
				tagValueCountDao.deleteTagByTagId(targetTagId);
				//从Mongo中获取所有Tag
				tagRecommendList = mongoTemplate.find(new Query(Criteria.where("tagId").is(targetTagId)), TagRecommend.class);
			}
			for (TagRecommend tagRecommend : tagRecommendList) {
				//tagId
				String tagId = tagRecommend.getTagId();
				//tagName
				String tagName = tagRecommend.getTagName();
				
				Integer searchMod = tagRecommend.getSearchMod();
				
				String tagDesc = tagRecommend.getTagDesc();
				Integer updateFlag = tagRecommend.getUpdateFlag();
				//标签值集合
				List<String> tagValues = tagRecommend.getTagList();
				
//				List<TagTree> tagTreeList = mongoTemplate.find(new Query(Criteria.where("children").in(tagId)), TagTree.class);
//				TagTree tagTree = tagTreeList.get(0);
//				String tName = tagTree.getTagName();
//				String root = tagTree.getParent();
//				String tagPath = root+">"+tName+">";

				String tagPath = this.getTagPath(tagId);
				
				//标签数量
				Long tagCount = mongoTemplate.count(
						new Query(Criteria.where("tagList").elemMatch(
								Criteria.where("tagId").is(tagId))),DataParty.class);
				if(targetTagId != null){
					tagCount = 0L;
				}
				TagValueCount tagVo = new TagValueCount(tagId,tagName,tagName,tagCount, tagId, tagPath,IS_TAG,searchMod,updateFlag,tagDesc);
				tagValueCountDao.insert(tagVo);
				int sort = 0;
				for (String tagValue : tagValues) {
					
					//获取标签值数量
					Long valueCount = mongoTemplate.count(
							new Query(Criteria.where("tagList").elemMatch(
									Criteria.where("tagId").is(tagId).and("tagValue").is(tagValue))),DataParty.class);
					
					TagValueCount tagValueCount = new TagValueCount(tagId,tagName,tagValue,valueCount, tagId+"_"+sort, tagPath,IS_TAGVALUE,searchMod);
					tagValueCountDao.insert(tagValueCount);
					sort++;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("初始化标签值数量出现异常"+e.getMessage(),e);
		}
	}

	/**
	 * 根据叶子节点tagId 获得该标签的全路径，样例为：leve1_name>level2_name>.......>selfTagName>
	 * @param leafTagId
	 * @return
	 */
	private String getTagPath(String leafTagId) {

		//获取该标签的父分级，一定存在
		List<TagTree> tagTreeList = mongoTemplate.find(new Query(Criteria.where("children").in(leafTagId)), TagTree.class);
		TagTree tagTree = tagTreeList.get(0);
		String tName = tagTree.getTagName();
		String curTagParent = tagTree.getParent();
		if (StringUtils.isEmpty(curTagParent) || StringUtils.isEmpty(curTagParent)) {
			logger.error("tagId为: "+leafTagId+" 的标签，标签名称或父分级parent为空");
			return "";
		}
		StringBuilder tagPath = new StringBuilder(curTagParent).append(">").append(tName).append(">");

		//递归获取该标签父节点 以上所有的分级，并拼接path
		while(true) {
			//获取当前分级 curTagParent 的父分级 parentTagName
			StringBuilder parentTagName = this.getParentLevelName(curTagParent);

			//当  当前分级的 父分级为空时，说明当前分级为根，停止递归
			if (StringUtils.isEmpty(parentTagName)) {
				break;
			}
			tagPath = parentTagName.append(">").append(tagPath);
			curTagParent = parentTagName.toString();
		}
		return tagPath.toString();
	}

	/**
	 * 根据子节点tag名称获取父层 level_name
	 * @param childTagName
	 * @return
	 */
	private StringBuilder getParentLevelName (String childTagName) {
		//获取当前节点的tagId
		List<TagTree> tagsList = mongoTemplate.find(new Query(Criteria.where("tag_name").is(childTagName)), TagTree.class);

		if (tagsList == null || tagsList.size() == 0) {
			return new StringBuilder("");
		}

		String tagId = tagsList.get(0).getTagId();
		//获取父节点
		List<TagTree> tagTreeList = mongoTemplate.find(new Query(Criteria.where("children").in(tagId)), TagTree.class);

		return CollectionUtils.isEmpty(tagTreeList) ? null : new StringBuilder(tagTreeList.get(0).getTagName());

	}

}
