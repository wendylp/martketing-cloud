package cn.rongcapital.mkt.mongodaoimpl;

import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by byf on 1/15/17.
 */
@Repository
public class MongoCustomTagDaoImpl implements MongoCustomTagDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String TAG_ID = "tag_id";

    @Override
    public void insertCustomTag(CustomTag paramCustomTag) {
        mongoTemplate.insert(paramCustomTag);
    }

    @Override
    public List<String> findCustomTagNames(CustomTag paramCustomTag) {
        Query query = new Query(Criteria.where(""));

        return null;
    }

}
