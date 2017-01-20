package cn.rongcapital.mkt.mongodao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;

/**
 * Created by byf on 1/15/17.
 */
public interface MongoCustomTagCategoryDao {
    
    /**
     * 功能描述：根据对象中的条件查询一条数据
     * 
     * @param customTag
     * @return
     */
    CustomTagCategory findOne(CustomTagCategory customTag);
    
    /**
     * 功能描述：根据自定义条件查询一条数据
     * 
     * @param query
     * @return
     */
    CustomTagCategory findOne(Query query);
    
    /**
     * 
     * 功能描述：根据对象中的条件查询多条记录
     * 
     * @param customTag
     * @return
     */
    List<CustomTagCategory> find(CustomTagCategory customTag);
    
    /**
     * 功能描述：根据自定义条件查询多条记录
     * 
     * @param query
     * @return
     */
    List<CustomTagCategory> find(Query query);
    
    

    void insertMongoCustomTagCategory(CustomTagCategory customTagCategory);

	public CustomTagCategory findByCategoryId(String categoryId);

}
