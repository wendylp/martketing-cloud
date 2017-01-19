package cn.rongcapital.mkt.mongodaoimpl;

import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by byf on 1/15/17.
 */
@Repository
public class MongoCustomTagCategoryDaoImpl implements MongoCustomTagCategoryDao{

    @Autowired
    private MongoTemplate mongoTemplate;
    
	@Override
	public CustomTagCategory findByCategoryId(String categoryId) {
		
		return mongoTemplate.findOne(new Query(Criteria.where("customTagCategoryId").is(categoryId)), CustomTagCategory.class);
	}
}
