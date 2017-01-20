package cn.rongcapital.mkt.mongodao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import cn.rongcapital.mkt.po.mongodb.CustomTag;

/**
 * Created by byf on 1/15/17.
 */
public interface MongoCustomTagDao {
    
    /**
     * 功能描述：根据对象中的条件查询一条数据
     * 
     * @param customTag
     * @return
     */
    CustomTag findOne(CustomTag customTag);
    
    /**
     * 功能描述：根据自定义条件查询一条数据
     * 
     * @param query
     * @return
     */
    CustomTag findOne(Query query);
    
    /**
     * 
     * 功能描述：根据对象中的条件查询多条记录
     * 
     * @param customTag
     * @return
     */
    List<CustomTag> find(CustomTag customTag);
    
    /**
     * 功能描述：根据自定义条件查询多条记录
     * 
     * @param query
     * @return
     */
    List<CustomTag> find(Query query);
    
    /**
     * 功能描述：根据自定义标签id列表统计其中有效数据的个数
     * 
     * @param customTagId
     * @return
     */
    long validCustomTagCount(List<String> customTagId);

    void insertCustomTag(CustomTag paramCustomTag);

    List<String> findCustomTagNames(CustomTag paramCustomTag);

    CustomTag findByCustomTagId(String customTagId);
}
