package cn.rongcapital.mkt.mongodaoimpl;

import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by byf on 1/15/17.
 */
@Repository
public class MongoCustomTagDaoImpl implements MongoCustomTagDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insertCustomTag(CustomTag customTag) {
        mongoTemplate.insert(customTag);
    }

	@Override
	public CustomTag findByCustomTagId(String customTagId) {
		
		return mongoTemplate.findOne(new Query(Criteria.where("customTagId").is(customTagId)), CustomTag.class);
	}

}
