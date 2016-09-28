/**
 * 描述：把mongodb中data_paty表市的值插入到tag_list
 * 
 * @author shuiyangyang
 * @date 2016.09.28
 */
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.SynchroMongodbCityService;

@Service
public class SynchroMongodbCityServiceImpl implements SynchroMongodbCityService{
	
	private static String CITY = "市";
	
	@Autowired
	MongoOperations mongoOperations;
	
	
	/**
	 * 根据mid把市的值写入到tag_list
	 * 
	 * @param mid 
	 * @author shuiyangyang
	 * @date 2016.09.28
	 */
	@Override
	public void synchroMongodbCity(Integer mid) {
		
		DataParty dataParty = mongoOperations.findOne(new Query(Criteria.where("mid").is(mid)), DataParty.class);
		TagRecommend tagRecommend = mongoOperations.findOne(new Query(Criteria.where("tag_name").is(CITY)), TagRecommend.class);
		
		if(dataParty != null && tagRecommend != null) {
			
			List<Tag> tagLists = dataParty.getTagList();
			
			if(tagLists != null) {
				
				// 查找是否重复
				for(int i = 0; i < tagLists.size(); i++) {
					if(CITY.equals(tagLists.get(i).getTagName())) {
						tagLists.remove(i);
						i--;
					}
				}
			} else {
				tagLists = new ArrayList<Tag>();
			}
			
			
			Tag tag = new Tag();
			tag.setTagId(tagRecommend.getTagId());
			tag.setTagName(CITY);
			tag.setTagNameEng(tagRecommend.getTagNameEng());
			tag.setTagValue(dataParty.getCity());
			tag.setTagGroupId(1);
			tagLists.add(tag);
			
			dataParty.setTagList(tagLists);
			mongoOperations.save(dataParty);
		}
		
	}

}
