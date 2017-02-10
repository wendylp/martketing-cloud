package cn.rongcapital.mkt.mongodaoimpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import cn.rongcapital.mkt.common.util.CamelNameUtil;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;

/**
 * Created by byf on 1/15/17.
 */
@Repository
public class MongoCustomTagDaoImpl implements MongoCustomTagDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String TAG_ID = "tag_id";

    private final static Class<CustomTag> CUSTOM_TAG_CLASS = CustomTag.class;

    private static final String CUSTOM_TAG_ID = "custom_tag_id";
    private static final String CUSTOM_TAG_NAME = "custom_tag_name";
    private static final String CUSTOM_TAG_TYPE = "custom_tag_type";
    private static final String PARENT_ID = "parent_id";
    private static final String IS_DELETED = "is_deleted";
    private static final String RECOMMEND_FLAG = "recommend_flag";
    private static final String CREATE_TIME = "create_time";
    private static final String UPDATE_TIME = "update_time";
    private static final String CUSTOM_TAG_SOURCE = "custom_tag_source";
    private static final String COVER_NUMBER = "cover_number";
    private static final String COVER_FREQUENCY = "cover_frequency";

    // 设置需要查询的字段
    private static final String[] QUERY_LISTS =
            {CUSTOM_TAG_ID, CUSTOM_TAG_NAME, CUSTOM_TAG_TYPE, PARENT_ID, IS_DELETED, RECOMMEND_FLAG, CUSTOM_TAG_SOURCE};

    private static final int DATA_VALID = 0;// 正常数据
    private static final int DATA_INVALID = 1;// 数据已经被删除

    @Override
    public CustomTag findOne(CustomTag customTag) {

        Query query = generatorQuery(customTag);

        return this.findOne(query);
    }

    @Override
    public CustomTag findOne(Query query) {
        if (query != null) {
            return mongoTemplate.findOne(query, MongoCustomTagDaoImpl.CUSTOM_TAG_CLASS);
        }
        return null;
    }

    @Override
    public List<CustomTag> find(CustomTag customTag) {
        Query query = this.generatorQuery(customTag);
        return this.find(query);
    }

    @Override
    public List<CustomTag> find(Query query) {
        if (query != null) {
            return mongoTemplate.find(query, MongoCustomTagDaoImpl.CUSTOM_TAG_CLASS);
        }
        return null;
    }

    @Override
    public long countValidCustomTag(List<String> customTagId) {
        if (CollectionUtils.isNotEmpty(customTagId)) {
            Criteria criteria = Criteria.where(MongoCustomTagDaoImpl.IS_DELETED).is(MongoCustomTagDaoImpl.DATA_VALID)
                    .and(CUSTOM_TAG_ID).in(customTagId);
            return mongoTemplate.count(new Query(criteria), CUSTOM_TAG_CLASS);
        }
        return 0;
    }

    @Override
    public void insertCustomTag(CustomTag paramCustomTag) {
        mongoTemplate.insert(paramCustomTag);
    }

    @Override
    public List<String> findCustomTagNames(CustomTag paramCustomTag) {
        Query query = new Query(Criteria.where(""));

        return null;
    }

    @Override
    public CustomTag findByCustomTagId(String customTagId) {

        return findByCustomTagId(customTagId, DATA_VALID);
    }

    @Override
    public CustomTag findByCustomTagId(String customTagId, Integer isDeleted) {
        return mongoTemplate.findOne(
                new Query(Criteria.where(CUSTOM_TAG_ID).is(customTagId).and(IS_DELETED).is(isDeleted)),
                CUSTOM_TAG_CLASS);
    }

    @Override
    public List<CustomTag> findByCustomTagIdList(List<String> customTagList) {
        return mongoTemplate.find(
                new Query(Criteria.where(CUSTOM_TAG_ID).in(customTagList).and(IS_DELETED).is(DATA_VALID)),
                CUSTOM_TAG_CLASS);
    }
    
    @Override
    public List<CustomTag> findByCustomTagIdList(List<String> customTagList, Integer index, Integer size) {
        Query query = new Query(Criteria.where(CUSTOM_TAG_ID).in(customTagList).and(IS_DELETED).is(DATA_VALID));
        // 设置排序规则
        query.with(new Sort(Direction.DESC, COVER_NUMBER, COVER_FREQUENCY));
        if (index != null && size != null) {
            // 设置分页规则
            query.skip((index - 1) * size);
            query.limit(size);
        }

        return mongoTemplate.find(query, CUSTOM_TAG_CLASS);
    }
    
    @Override
    public List<CustomTag> findByCustomTagIdListAndNameFuzzy(List<String> customTagList, String customTagName) {
        Query query = new Query(Criteria.where(IS_DELETED).is(DATA_VALID).and(CUSTOM_TAG_ID).in(customTagList)
                .and(CUSTOM_TAG_NAME).regex(customTagName));
        return mongoTemplate.find(query, CUSTOM_TAG_CLASS);
    }
    
    @Override
    public List<CustomTag> findByCustomTagNameFuzzyAndCoverNumber(String customTagName, Integer size) {
        Query query = new Query(Criteria.where(IS_DELETED).is(DATA_VALID).and(CUSTOM_TAG_NAME).regex(customTagName)
                .and(COVER_NUMBER).gt(0));
        if (size != null) {
            query.limit(size);
        }
        return mongoTemplate.find(query, CUSTOM_TAG_CLASS);
    }

    @Override
    public long countByCustomTagNameFuzzyAndCoverNumber(String customTagName) {
        Query query = new Query(Criteria.where(IS_DELETED).is(DATA_VALID).and(CUSTOM_TAG_NAME).regex(customTagName)
                .and(COVER_NUMBER).gt(0));
        return mongoTemplate.count(query, CUSTOM_TAG_CLASS);
    }
    
    @Override
    public List<CustomTag> findByCustomTagNameFuzzy(String customTagName, Integer index, Integer size) {
        
        Query query = new Query(Criteria.where(IS_DELETED).is(DATA_VALID).and(CUSTOM_TAG_NAME).regex(customTagName));
        
        if (index != null && size != null) {
            Integer skip = (index - 1) * size;
            Integer limit = size;
            query.skip(skip).limit(limit);
        }
        return mongoTemplate.find(query, CUSTOM_TAG_CLASS);
    }
    
    @Override
    public long countByCustomTagNameFuzzy(String customTagName) {
        Query query = new Query(Criteria.where(IS_DELETED).is(DATA_VALID).and(CUSTOM_TAG_NAME).regex(customTagName));
        return mongoTemplate.count(query, CUSTOM_TAG_CLASS);
    }

    @Override
    public long countAll() {
        Query query = new Query(Criteria.where(IS_DELETED).is(DATA_VALID));
        return mongoTemplate.count(query, CUSTOM_TAG_CLASS);
    }
    
    /**
     * 功能描述：根据对象生成对象的mongodb查询条件
     * 
     * @param customTag
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    private Query generatorQuery(CustomTag customTag) {
        if (customTag == null) {
            return null;
        }

        // 默认查询有效数据
        Criteria criteria = new Criteria(MongoCustomTagDaoImpl.IS_DELETED).is(MongoCustomTagDaoImpl.DATA_VALID);

        Class<CustomTag> customTagClass = CustomTag.class;

        for (String queryList : QUERY_LISTS) {
            try {
                // 设置方法名称
                String methodName = "get" + CamelNameUtil.camelName(queryList);
                Method method = customTagClass.getMethod(methodName);
                // 如果不为空则设置为查询条件。默认查询有效数据，对时间参数不做处理
                if (method.invoke(customTag) != null) {
                    criteria.and(queryList).is(method.invoke(customTag));
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return new Query(criteria);

    }

	@Override
	public CustomTag getCustomTagByTagId(String customTagId) {
		 return mongoTemplate.findOne(
	                new Query(Criteria.where(CUSTOM_TAG_ID).is(customTagId)),
	                CUSTOM_TAG_CLASS);
	}

    @Override
    public void updateCustomTagParentIdByCustomTagId(String customTagId, String parentId) {
        Query query = new Query(Criteria.where(CUSTOM_TAG_ID).is(customTagId).and(IS_DELETED).is(DATA_VALID));
        Update update = new Update().set(PARENT_ID,parentId);
        mongoTemplate.updateFirst(query,update,CustomTag.class);
    }

    @Override
    public void updateCustomTagNameByCustomTagId(String customTagId, String customTagName) {
       Query query = new Query(Criteria.where(CUSTOM_TAG_ID).is(customTagId).and(IS_DELETED).is(DATA_VALID));
       Update update = new Update().set(CUSTOM_TAG_NAME,customTagName);
       mongoTemplate.updateFirst(query,update,CustomTag.class);
    }

    @Override
    public void logicalDeleteCustomTagByCustomTagId(String customTagId) {
        Query query = new Query(Criteria.where(CUSTOM_TAG_ID).is(customTagId).and(IS_DELETED).is(DATA_VALID));
        Update update = new Update().set(IS_DELETED,DATA_INVALID);
        mongoTemplate.updateFirst(query,update,CustomTag.class);
    }

}
