/**
 * 描述：返回渠道名，渠道大类，和市的tag
 * 
 * @author shuiyangyang
 * @date 2016.09.28
 */
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.ChannelTypeMappingDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.SynchroMongodbCityService;

@Service
public class SynchroMongodbCityServiceImpl implements SynchroMongodbCityService{
    
    private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static String CITY = "市";
	private static String MEDIA_TRENCH_GENERA = "媒体渠道大类";
	private static String MEDIA_NAME = "媒体名称";
	
	@Autowired
	MongoOperations mongoOperations;
	
	@Autowired
	DataPopulationDao dataPopulationDao;
	
	@Autowired
	ChannelTypeMappingDao channelTypeMappingDao;
	
	
	/**
	 * 描述：返回渠道名，渠道大类，和市的tag
	 * 
	 * @param mid 
	 * @author shuiyangyang
	 * @return Map<String, Object>
	 * @date 2016.09.28
	 */
	@Override
	public Map<String, Object> synchroMongodbCity(DataParty dataParty) {
	    
	    logger.info("判断dataParty是否为空:-------------------------------------->" + dataParty.toString());
	    
        Map<String, Object> map = new HashMap<String, Object>();

        Integer keyId = dataParty.getMid();
        
        logger.info("同步属性标签方法开始执行, kayId:-----------------------》" + keyId);
        
        // 根据tag_name查询TagRecommend
        TagRecommend tagRecommend = getTagRecommend(CITY);
        if (dataParty != null && tagRecommend != null && dataParty.getCity() != null
                        && !"".equals(dataParty.getCity())) {

            // 设置tag
            Tag tag = new Tag(tagRecommend.getTagId(), CITY, tagRecommend.getTagNameEng(),
                            dataParty.getCity(), 1);
            map.put(tagRecommend.getTagNameEng(), tag);
        }

        // 获取"媒体名称"的TagRecommend
        tagRecommend = getTagRecommend(MEDIA_NAME);
        DataPopulation dataPopulation = new DataPopulation();
        dataPopulation.setKeyid(keyId);
        
        // 查询渠道名和渠道大类
        Map<String, Object> mapChannel = dataPopulationDao.selectMediaChannel(keyId);

        if (mapChannel != null) {
            tagRecommend = getTagRecommend(MEDIA_TRENCH_GENERA);
            if (tagRecommend != null && mapChannel.get("media_channel") != null) {
                // 设置tag
                Tag tag = new Tag(tagRecommend.getTagId(), MEDIA_TRENCH_GENERA, tagRecommend.getTagNameEng(),
                                mapChannel.get("media_channel").toString(), 1);
                map.put(tagRecommend.getTagNameEng(), tag);
            }

            tagRecommend = getTagRecommend(MEDIA_NAME);
            if (tagRecommend != null && mapChannel.get("source") != null) {
                // 设置tag
                Tag tag = new Tag(tagRecommend.getTagId(), MEDIA_NAME, tagRecommend.getTagNameEng(),
                                mapChannel.get("source").toString(), 1);
                map.put(tagRecommend.getTagNameEng(), tag);
            }
        }
        logger.info("同步属性标签方法执行结束，返回值为--------------->" + map);
        return map;
		
	}
	
	/**
	 * 根据tag_name获取TagRcommend
	 * @param tag_name
	 * @return
	 */
    private TagRecommend getTagRecommend(String tag_name) {
        return mongoOperations.findOne(new Query(Criteria.where("tag_name").is(tag_name)),
                        TagRecommend.class);
    }

}
