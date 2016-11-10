package cn.rongcapital.mkt.job.tag.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.mongodb.WriteResult;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.SystemTagResultDao;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.SysTagView;
import cn.rongcapital.mkt.po.SystemTagResult;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import redis.clients.jedis.Jedis;

/*************************************************
 * @功能简述: 系统标签同步父类
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/10/18
 * @复审人:
 *************************************************/
public class BaseSystemTagSyn {
	
	 private static final String REDIS_IDS_KEY_PREFIX = "tagcoverid:";
	
	 private Logger logger = LoggerFactory.getLogger(getClass());
	 
	 @Autowired
	 protected MongoTemplate mongoTemplate;

	 @Autowired
	 protected SystemTagResultDao systemTagResultDao;

	 @Autowired
	 protected ThreadPoolTaskExecutor threadPoolTaskExecutor;
	 
	 @Autowired
	 private TagValueCountDao tagValueCountDao;
	 
	 /**
	  * 获取标签视图映射集合
	  * @param targetDao	目标表Dao
	  * @return
	  */
	 protected void getTagViewList(BaseDao<SysTagView> targetDao){
		 SysTagView sysTagView = new SysTagView();
		 sysTagView.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		 sysTagView.setPageSize(null);
		 List<SysTagView> tagViewList = targetDao.selectList(sysTagView);
		 for (SysTagView sys : tagViewList) {
				this.getSysTagResult(sys);
			}
	 }
	 
	 
	 protected void getSysTagResult(SysTagView sys) {
			try {
				String viewName = sys.getViewName(); // 视图名称
				String tagName = sys.getTagName(); // 标签名称

				if (StringUtils.isEmpty(viewName) || StringUtils.isEmpty(tagName)){
					return;
				}
				//获取结果
				List<SystemTagResult> resultList = systemTagResultDao.selectListByMap(viewName);
				logger.info("开始同步" + sys.getViewDesc() + "标签，-------->" + tagName);

				// 查询推荐标签
				Criteria criteriaAll = Criteria.where("tag_name_eng").is(tagName);

				TagRecommend tagRecommend = mongoTemplate.findOne(new Query(criteriaAll),
						cn.rongcapital.mkt.po.mongodb.TagRecommend.class);
				
				if(tagRecommend == null){
					return;
				}
				
				//存放List
				Map<String, Vector<String>> paramMap = new HashMap<>();
				for (SystemTagResult systemTagResult : resultList) {

					Runnable thread = new Runnable() {

						@Override
						public void run() {
							//标签Id
							String tagId = tagRecommend.getTagId();
							//标签值
							String tagValue = systemTagResult.getTagValue();
							//keyId
							Integer keyId = systemTagResult.getKeyId();
							// 封装Tag属性
							Tag tag = new Tag(tagId, tagRecommend.getTagName(), tagRecommend.getTagNameEng(),
									tagValue, 1);
							
							//获取redis Key
							String key = getKey(tagId, tagValue);
							Vector<String> vector = paramMap.get(key);
							if(null == vector){
								vector = getVector();
								paramMap.put(key, vector);
							}
							vector.add(String.valueOf(keyId));
							
							startSynchTag(keyId,tag);
						}
					};
					threadPoolTaskExecutor.execute(thread);
				}
				while(true){
					//获取线程池中活动线程数
					int threadActiveCount = threadPoolTaskExecutor.getActiveCount();
					if(threadActiveCount == 0){
						//存入Redis
						saveDataToReids(paramMap);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获取视图结果数据出现异常----------------->" + e.getMessage(), e);
			}
		}
	 
	 
	 
 	/**
	 * 同步标签
	 * 
	 * @param keyId
	 *            人员ID
	 * @param tagValue
	 *            标签名称
	 * @param tagRecommend
	 */
	 protected void startSynchTag(Integer keyId, Tag tag) {
		try {
			Criteria criteria = Criteria.where("mid").is(Integer.valueOf(keyId));
			// 查询Mongo是否存在此数据
			DataParty dataParty = mongoTemplate.findOne(new Query(criteria), DataParty.class);
			if (dataParty == null) {
				return;
			}
			Query query = Query.query(Criteria.where("mid").is(keyId));
			WriteResult updateFirst = mongoTemplate.updateFirst(query, new Update().push("tag_list", tag),
					DataParty.class);
			// 为NULL标签该人不存在
			if (updateFirst == null) {
				logger.info("MID不存在---------------->" + keyId);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("同步Mongo中tag_list字段出现异常-------------->" + e.getMessage(), e);
		}
	}
	 
	 
	
	/**
	 * 初始化Mongo数据，将tag_list字段删除
	 */
	protected void initMongoTagList() {
		logger.info("初始化Mongo tag_list字段方法开始执行---------->");
		try {
			Query query = Query.query(Criteria.where("mid").gt(-1));
			Update update = new Update().unset("tag_list");
			mongoTemplate.updateMulti(query, update, cn.rongcapital.mkt.po.mongodb.DataParty.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("初始化Mongo tag_list字段出现异常---------->" + e.getMessage(), e);
		}
		logger.info("初始化Mongo tag_list字段方法执行结束---------->");
	}
	
	
	/**
	 * 拼接Redis key
	 * @param tagID
	 * @param tagValue
	 * @return
	 */
	private String getKey(String tagID,String tagValue){
		String tagValueId = tagValueCountDao.selectTagValueId(tagID,tagValue);
		return REDIS_IDS_KEY_PREFIX+tagValueId;
	}
	
	/**
	 * 获取集合
	 * @return
	 */
	private Vector<String> getVector(){
		return new Vector<>();
	}
	
	/**
	 * 保存数据到Redis
	 * @param dataMap
	 */
	private void saveDataToReids(Map<String, Vector<String>> dataMap){
		try {
			Set<String> keySet = dataMap.keySet();
			for (String key : keySet) {
				Vector<String> vector = dataMap.get(key);
				String[] idArray = (String[])vector.toArray(new String[vector.size()]);
				Jedis redis = RedistSetDBUtil.getRedisInstance();
				redis.sadd(key, idArray);
				RedistSetDBUtil.closeRedisConnection(redis);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
