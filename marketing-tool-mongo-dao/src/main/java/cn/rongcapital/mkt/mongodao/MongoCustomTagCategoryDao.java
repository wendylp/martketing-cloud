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
    public CustomTagCategory findOne(CustomTagCategory customTag);
    
    /**
     * 功能描述：根据自定义条件查询一条数据
     * 
     * @param query
     * @return
     */
    public CustomTagCategory findOne(Query query);
    
    /**
     * 
     * 功能描述：根据对象中的条件查询多条记录
     * 
     * @param customTag
     * @return
     */
    public List<CustomTagCategory> find(CustomTagCategory customTag);
    
    /**
     * 功能描述：根据自定义条件查询多条记录
     * 
     * @param query
     * @return
     */
    public List<CustomTagCategory> find(Query query);
    
    

    void insertMongoCustomTagCategory(CustomTagCategory customTagCategory);

    /**
     * 功能描述：根据自定义分类id查询有效数据
     * 
     * @param categoryId
     * @return
     */
	public CustomTagCategory findByCategoryId(String categoryId);
	
	/**
	 * 功能描述：根据自定义分类Id及删除状态查询数据
	 * 
	 * @param categoryId
	 * @param isDeleted
	 * @return
	 */
	public CustomTagCategory findByCategoryId(String categoryId, Integer isDeleted);
	
    /**
     * 功能描述：根据自定义标签id查询父分类
     * 
     * @param childrenCustomTagList
     * @return
     */
    public CustomTagCategory findByChildrenCustomTagList(String childrenCustomTagList);

}
