package cn.rongcapital.mkt.mongodaoimpl;

import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by byf on 1/15/17.
 */
@Repository
public class MongoCustomTagCategoryDaoImpl implements MongoCustomTagCategoryDao{

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void insertMongoCustomTagCategory(CustomTagCategory customTagCategory) {
        mongoTemplate.insert(customTagCategory);
    }
}
