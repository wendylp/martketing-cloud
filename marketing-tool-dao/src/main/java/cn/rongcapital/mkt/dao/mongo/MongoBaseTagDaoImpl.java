package cn.rongcapital.mkt.dao.mongo;

import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.po.mongodb.CustomTagTypeLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yunfeng on 2016-9-21.
 */
@Repository
public class MongoBaseTagDaoImpl implements MongoBaseTagDao{

    private Logger logger = LoggerFactory.getLogger(MongoBaseTagDaoImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    private ConcurrentHashMap<String, Field[]> filedMap = new ConcurrentHashMap<>();

    @Override
    public boolean insertBaseTagDao(BaseTag baseTag) {
        mongoTemplate.insert(baseTag);
        BaseTag insertedTag = null;
        Query query = new Query(Criteria.where("tag_name").is(baseTag.getTagName()).and("path").is(baseTag));
        insertedTag = mongoTemplate.findOne(query,BaseTag.class);
        if(insertedTag == null) return false;
        return true;
    }

    @Override
    public BaseTag findOneBaseTag(BaseTag baseTag) {
        BaseTag targetTag = null;
        if(baseTag instanceof CustomTagTypeLayer){
            Query query = null;
            if(baseTag.getPath() != null){
                query = new Query(Criteria.where("tag_name").is(baseTag.getTagName()).and("path").is(baseTag.getPath()));
            }else{
                query = new Query(Criteria.where("tag_name").is(baseTag.getTagName()));
            }
            targetTag = mongoTemplate.findOne(query,BaseTag.class);
        }else if(baseTag instanceof CustomTagLeaf){
            Query query = new Query(Criteria.where("tag_name").is(baseTag.getTagName()).and("path").is(baseTag.getPath()).and("source").is(baseTag.getSource()));
            targetTag = mongoTemplate.findOne(query,BaseTag.class);
        }
        return targetTag;
    }

    @Override
    public List<BaseTag> findBaseTagListByTagType(Integer tagType) {
        Query query = new Query(Criteria.where("tag_type").is(tagType));
        List<BaseTag> baseTags = mongoTemplate.find(query,BaseTag.class);
        return baseTags;
    }

    @Override
    public BaseTag updateBaseTag(BaseTag baseTag) {
        BaseTag updatedTag = null;
        Query query = new Query(Criteria.where("tag_name").is(baseTag.getTagName()).and("path").is(baseTag.getPath()));
        Update update = buildBaseUpdate(baseTag);
        mongoTemplate.updateFirst(query,update,baseTag.getClass());
        return null;
    }

    //Todo:这个方法要改的可以获取父类的属性,并且去除掉static final这样的属性
    private Update buildBaseUpdate(BaseTag t) {
        Update update = new Update();
        String className = BaseTag.class.getName();
        Field[] fields = filedMap.get(className);
        if (fields == null) {
            fields = BaseTag.class.getDeclaredFields();
            filedMap.putIfAbsent(className, fields);
        }
        for (Field field : fields) {
            if(field.getName().equals("serialVersionUID")) continue;
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (value != null) {
                    update.set(field.getName(), value);
                }
            } catch (Exception e) {
                logger.error("buildBaseUpdate failed", e);
            }
        }
        return update;
    }
}
