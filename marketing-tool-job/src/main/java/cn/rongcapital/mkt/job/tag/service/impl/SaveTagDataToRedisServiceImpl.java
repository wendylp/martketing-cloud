package cn.rongcapital.mkt.job.tag.service.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.TagValueCount;
import redis.clients.jedis.Jedis;

/*************************************************
 * @功能简述: 将标签信息保存到Redis中
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/11/09
 * @复审人:
 *************************************************/
@Service
public class SaveTagDataToRedisServiceImpl implements TaskService{
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());
 
	private static final String REDIS_COVER_KEY_PREFIX = "tagcover:";
	
	private static final String REDIS_IDS_KEY_PREFIX = "tagcoverid:";
	
	private static final String IS_TAG = "1";
	
	private static final String TAG_DEFUALT_ORDER = "-1";
	
	@Autowired
	Environment env;
 
	 
	@Autowired
	private TagValueCountDao tagValueCountDao;
	 
	@Override
	public void task(Integer taskId) {
		logger.info("保存标签信息数据到Redis方法开始执行----------->");
		try {
			TagValueCount tagValueCount = new TagValueCount();
			tagValueCount.setPageSize(null);
			//获取标签信息
			List<TagValueCount> tagValueCountList = tagValueCountDao.selectList(tagValueCount);
			for (TagValueCount tagInfo : tagValueCountList) {
				Map<String, String> paramMap = getParamMap(tagInfo);
				String redisKey = tagInfo.getTagValueSeq();
				redisKey = REDIS_COVER_KEY_PREFIX+redisKey;
				Jedis redis = RedistSetDBUtil.getRedisInstance();
				redis.hmset(redisKey, paramMap);
				RedistSetDBUtil.closeRedisConnection(redis);
				
				test();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存标签信息数据到Redis方法出现异常-------------->"+e.getMessage(),e);
		}
		logger.info("保存标签信息数据到Redis方法执行结束----------->");
	}
	
	/**
	 * 获取参数集合
	 * @param tagInfo
	 * @return
	 */
	private Map<String, String> getParamMap(TagValueCount tagInfo){
		//日期格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd MM:hh:ss");
		
		Map<String, String> paramMap = new HashMap<>(); 
		try {
			String tagValueSeq = tagInfo.getTagValueSeq();
			//标签标识
			String isTag = tagInfo.getIsTag();
			if(isTag.equals(IS_TAG)){
				paramMap.put(RedisKey.TAG_VALUE_ORDER, TAG_DEFUALT_ORDER);
			}else{
				//标签排序
				String order = tagValueSeq.substring(tagValueSeq.indexOf("_")+1,tagValueSeq.length());
				paramMap.put(RedisKey.TAG_VALUE_ORDER, order);
			}
			//封装参数
			paramMap.put(RedisKey.TAG_ID, tagInfo.getTagId());
			paramMap.put(RedisKey.TAG_NAME, tagInfo.getTagName());
			paramMap.put(RedisKey.TAG_PATH, tagInfo.getTagPath());
			paramMap.put(RedisKey.TAG_VALUE, tagInfo.getTagValue());
			paramMap.put(RedisKey.TAG_VALUE_ID, tagValueSeq);
			paramMap.put(RedisKey.COVER_COUNT, tagInfo.getValueCount().toString());
			paramMap.put(RedisKey.IS_TAG_VALUE, isTag);
			paramMap.put(RedisKey.OVER_UPDATE_TIME, sdf.format(new Date()));
			paramMap.put(RedisKey.TAG_COVER_ID, REDIS_IDS_KEY_PREFIX+tagValueSeq);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Redis存放标签信息，获取标签信息数据出现异常------------->"+e.getMessage(), e);
		}
		
		return paramMap;
	}
	
	private void test(){
		try {
			String url = env.getProperty("test.connect");
			logger.info("测试读取外部配置文件+++++++++++++++++++++++++++++++"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}


