package cn.rongcapital.mkt.job.tag.service.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.TagValueCount;

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
public class SaveTagDataToRedis implements TaskService{
	
	
 private Logger logger = LoggerFactory.getLogger(getClass());
	 
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
				JedisClient.hmset(redisKey, paramMap);
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
			//标签值
			String tagValue = tagInfo.getTagValue();
			//标签值id
			String tagValueSeq = tagInfo.getTagValueSeq();
			//标签id
			String tagId = tagInfo.getTagId();
			//标签标识
			String isTag = tagInfo.getIsTag();
			//标签路径
			String tagPath = tagInfo.getTagPath();
			//标签名称
			String tagName = tagInfo.getTagName();
			//标签数量
			String tagCount = tagInfo.getValueCount().toString();
			if(isTag.equals("1")){
				paramMap.put("tagValueOrder", "-1");
			}else{
				//标签排序
				String order = tagValueSeq.substring(tagValueSeq.indexOf("_")+1,tagValueSeq.length());
				paramMap.put("tagValueOrder", order);
			}
			//封装参数
			paramMap.put("tagid", tagValueSeq);
			paramMap.put("tagname", tagName);
			paramMap.put("tagpath", tagPath);
			paramMap.put("tagValue", tagValue);
			paramMap.put("tagvalueid", tagValueSeq);
			paramMap.put("coverCount", tagCount);
			paramMap.put("IsTagValue", isTag);
			paramMap.put("overUpdateTime", sdf.format(new Date()));
			paramMap.put("TagCoverID", tagId +":"+tagValueSeq);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Redis存放标签信息，获取标签信息数据出现异常------------->"+e.getMessage(), e);
		}
		
		return paramMap;
	}
	
}


