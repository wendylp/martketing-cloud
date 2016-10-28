package cn.rongcapital.mkt.job.tag.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;

/*************************************************
 * @功能简述: 初始化标签值数量
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/10/24
 * @复审人:
 *************************************************/

@Service
public class InitTagValueCountServiceImpl implements TaskService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private TagValueCountDao tagValueCountDao;
	
	@Override
	public void task(Integer taskId) {
		try {
			//清空存量数据
			tagValueCountDao.clearStockData();
			//从Mongo中获取所有Tag
			List<TagRecommend> tagRecommendList = mongoTemplate.find(new Query(Criteria.where("tagId").ne(null)), TagRecommend.class);
			for (TagRecommend tagRecommend : tagRecommendList) {
				//tagId
				String tagId = tagRecommend.getTagId();
				//tagName
				String tagName = tagRecommend.getTagName();
				//标签值集合
				List<String> tagValues = tagRecommend.getTagList();
				for (String tagValue : tagValues) {
					//获取标签值数量
					Long valueCount = mongoTemplate.count(
							new Query(Criteria.where("tagList").elemMatch(
									Criteria.where("tagId").is(tagId).and("tagValue").is(tagValue))),DataParty.class);
					//封装对象
					TagValueCount tagValueCount = new TagValueCount(tagId, tagName, tagValue, valueCount);
					tagValueCountDao.insert(tagValueCount);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("初始化标签值数量出现异常"+e.getMessage(),e);
		}
	}
	
	
	

}
