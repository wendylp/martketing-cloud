package cn.rongcapital.mkt.mongodaoimpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;

import cn.rongcapital.mkt.common.util.CamelNameUtil;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;

/**
 * Created by byf on 1/15/17.
 */
@Repository
public class MongoCustomTagCategoryDaoImpl implements MongoCustomTagCategoryDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    private Logger logger = LoggerFactory.getLogger(MongoCustomTagCategoryDaoImpl.class);
    private final static Class<CustomTagCategory> CUSTOM_TAG_CATEGORY_CLASS = CustomTagCategory.class;

    private static final String CUSTOM_TAG_CATEGORY_ID = "custom_tag_category_id";
    private static final String CUSTOM_TAG_CATEGORY_NAME = "custom_tag_category_name";
    private static final String CUSTOM_TAG_CATEGORY_TYPE = "custom_tag_category_type";
    private static final String LEVEL = "level";
    private static final String IS_DELETED = "is_deleted";
    private static final String PARENT_ID = "parent_id";
    private static final String CREATE_TIME = "create_time";
    private static final String UPDATE_TIME = "update_time";
    private static final String CUSTOM_TAG_CATEGORY_SOURCE = "custom_tag_category_source";
    private static final String CHILDREN_CUSTOM_TAG_CATEGORY_LIST = "children_custom_tag_category_list";
    private static final String CHILDREN_CUSTOM_TAG_LIST = "children_custom_tag_list";

    private static final String[] QUERY_LISTS = {CUSTOM_TAG_CATEGORY_ID, CUSTOM_TAG_CATEGORY_NAME,
            CUSTOM_TAG_CATEGORY_TYPE, LEVEL, IS_DELETED, PARENT_ID, CUSTOM_TAG_CATEGORY_SOURCE};
    
    private static final int DATA_VALID = 0;// 正常数据
    private static final int DATA_INVALID = 1;// 数据已经被删除

    private ConcurrentHashMap<String, Field[]> filedMap = new ConcurrentHashMap<>();

    @Override
    public CustomTagCategory findOne(CustomTagCategory customTagCategory) {

        Query query = generatorQuery(customTagCategory);

        return this.findOne(query);
    }

    @Override
    public CustomTagCategory findOne(Query query) {
        if (query != null) {
            return mongoTemplate.findOne(query, MongoCustomTagCategoryDaoImpl.CUSTOM_TAG_CATEGORY_CLASS);
        }
        return null;
    }

    @Override
    public List<CustomTagCategory> find(CustomTagCategory customTagCategory) {
        Query query = this.generatorQuery(customTagCategory);
        return this.find(query);
    }

    @Override
    public List<CustomTagCategory> find(Query query) {
        if (query != null) {
            return mongoTemplate.find(query, MongoCustomTagCategoryDaoImpl.CUSTOM_TAG_CATEGORY_CLASS);
        }
        return null;
    }

    @Override
    public void insertMongoCustomTagCategory(CustomTagCategory customTagCategory) {
        mongoTemplate.insert(customTagCategory);
    }

    @Override
    public CustomTagCategory findByCategoryId(String categoryId) {
        return findByCategoryId(categoryId, DATA_VALID);
    }

    @Override
    public CustomTagCategory findByCategoryId(String categoryId,Integer isDeleted) {
        return mongoTemplate.findOne(
                new Query(Criteria.where(CUSTOM_TAG_CATEGORY_ID).is(categoryId).and(IS_DELETED).is(isDeleted)),
                CUSTOM_TAG_CATEGORY_CLASS);
    }
    
    @Override
    public CustomTagCategory findByChildrenCustomTagList(String childrenCustomTagList) {
        return mongoTemplate.findOne(new Query(
                Criteria.where(CHILDREN_CUSTOM_TAG_LIST).is(childrenCustomTagList).and(IS_DELETED).is(DATA_VALID)),
                CUSTOM_TAG_CATEGORY_CLASS);
    }

    @Override
    public void updateCustomTagCategoryChildrenListById(String customTagCategoryId, CustomTagCategory customTagCategory) {
        if(customTagCategory == null) return;
        Query query = new Query(Criteria.where(CUSTOM_TAG_CATEGORY_ID).is(customTagCategoryId).and(IS_DELETED).is(DATA_VALID));
        mongoTemplate.updateFirst(query,new Update().set(CHILDREN_CUSTOM_TAG_LIST,customTagCategory.getChildrenCustomTagList()),CustomTagCategory.class);
    }
    
	/**
	 * 功能描述：根据自定义条件查询数量
	 * 
	 * @param customTagCategory
	 * @return
	 */
	@Override
	public long countByCustomTagCategoryName(CustomTagCategory customTagCategory) {
		Query query = new Query(Criteria.where(CUSTOM_TAG_CATEGORY_NAME)
				.is(customTagCategory.getCustomTagCategoryName()).and(IS_DELETED).is(DATA_VALID));
		return mongoTemplate.count(query, CUSTOM_TAG_CATEGORY_CLASS);
	}
	
    @Override
    public boolean updateCategoryNameById(String customTagCategoryId, String customTagCategoryName) {
        boolean flag = false;
        if (StringUtils.isNoneBlank(customTagCategoryId)) {
            Query query = new Query(
                    Criteria.where(CUSTOM_TAG_CATEGORY_ID).is(customTagCategoryId).and(IS_DELETED).is(DATA_VALID));
            Update update = new Update().set(CUSTOM_TAG_CATEGORY_NAME, customTagCategoryName);
            WriteResult writeResult = mongoTemplate.updateFirst(query, update, CUSTOM_TAG_CATEGORY_CLASS);

            if (writeResult.getN() == 1) {
                flag = true;
            } else {
                logger.info("修改自定义标签名出错,customTagCategoryId：{},customTagCategoryName:{},WriteResult:{}",
                        customTagCategoryId, customTagCategoryName, writeResult);
            }
        }

        return flag;
    }

    @Override
    public void logicalDeleteCustomTagCategoryById(String customTagCategoryId) {
        Query query = new Query(Criteria.where(CUSTOM_TAG_CATEGORY_ID).is(customTagCategoryId).and(IS_DELETED).is(DATA_VALID));
        Update update = new Update().set(IS_DELETED,DATA_INVALID);
        mongoTemplate.updateFirst(query,update,CustomTagCategory.class);
    }

    /**
     * 功能描述：根据对象生成对象的mongodb查询条件
     * 
     * @param object
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    private Query generatorQuery(Object object) {
        if (object == null) {
            return null;
        }
        // 默认查询有效数据
        Criteria criteria = new Criteria(this.IS_DELETED).is(this.DATA_VALID);

        Class c = object.getClass();

        for (String queryList : QUERY_LISTS) {
            try {
                // 设置方法名称
                String methodName = "get" + CamelNameUtil.camelName(queryList);
                Method method = c.getMethod(methodName);
                // 如果不为空则设置为查询条件。默认查询有效数据，对时间参数不做处理
                if (method.invoke(object) != null) {
                    criteria.and(queryList).is(method.invoke(object));
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return new Query(criteria);

    }
}
