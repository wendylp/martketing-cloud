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
    long countValidCustomTag(List<String> customTagId);

    void insertCustomTag(CustomTag paramCustomTag);

    List<String> findCustomTagNames(CustomTag paramCustomTag);

    /**
     * 功能描述：根据自定义标签id查询有效数据
     * 
     * @param customTagId
     * @return
     */
    public CustomTag findByCustomTagId(String customTagId);
    
    /**
     * 功能描述：根据自定义标签Id及删除状态查询数据
     * 
     * @param customTagId
     * @param isDeleted
     * @return
     */
    public CustomTag findByCustomTagId(String customTagId, Integer isDeleted);
    
    /**
     * 功能描述：根据自定义标签id list查询有效数据
     * 
     * @param customTagList
     * @return
     */
    public List<CustomTag> findByCustomTagIdList(List<String> customTagList);
    
    /**
     * 功能描述：根据自定义标签id list查询有效数据,按创建时间倒序排序，带有分页功能
     * 
     * @param customTagList
     * @param index
     * @param size
     * @return
     */
    public List<CustomTag> findByCustomTagIdList(List<String> customTagList, Integer index, Integer size);
    
    /**
     * 功能描述：根据自定义标签id list查询有效数据并模糊搜索自定义标签name
     * 
     * @param customTagList
     * @param customTagName
     * @return
     */
    public List<CustomTag> findByCustomTagIdListAndNameFuzzy(List<String> customTagList, String customTagName);
    
    /**
     * 功能描述：模糊查询自定义标签name，有效数据 过滤覆盖人数小于零
     * 
     * @param customTagName
     * @return
     */
    public List<CustomTag> findByCustomTagNameFuzzyAndCoverNumber(String customTagName, Integer size);
    
    /**
     * 功能描述：模糊查询自定义标签name，有效数据 
     * 
     * @param customTagName
     * @return
     */
    public List<CustomTag> findByCustomTagNameFuzzyAndCoverNumberAll(String customTagName, Integer size);

    /**
     * 功能描述：统计模糊查询自定义标签name的数据条数 过滤覆盖人数小于零
     * 
     * @param customTagName
     * @return
     */
    public long countByCustomTagNameFuzzyAndCoverNumber(String customTagName);
    
    /**
     * 功能描述：统计模糊查询自定义标签name的数据条数 
     * 
     * @param customTagName
     * @return
     */
    public long countByCustomTagNameFuzzyAndCoverNumberAll(String customTagName);
    
    /**
     * 功能描述：模糊搜索自定义标签名，如果index和size有一个为空则返回全部数据
     * @param customTagName
     * @param index
     * @param size
     * @return
     */
    public List<CustomTag> findByCustomTagNameFuzzy(String customTagName, Integer index, Integer size);
    
    /**
     * 功能描述：统计总数 ：模糊搜索自定义标签名，如果index和size有一个为空则返回全部数据
     * 
     * @param customTagName
     * @return
     */
    public long countByCustomTagNameFuzzy(String customTagName);

    /**
     * 功能描述：计算自定义标签的有效个数
     * 
     * @return
     */
    public long countAll();
    
    /**
     * 功能描述：根据自定义标签id查询自定义标签
     * 
     * @param customTagId	自定义标签ID
     * @return
     */
    public CustomTag getCustomTagByTagId(String customTagId);

    /**
     * 功能描述：根据自定义标签id更新自定义标签的父Id
     *
     * @param customTagId	自定义标签ID
     * @return
     */
    void updateCustomTagParentIdByCustomTagId(String customTagId,String parentId);

    /**
     * 功能描述：根据自定义标签id更新自定义标签的名称
     *
     * @param customTagId	自定义标签ID
     * @return
     */
    void updateCustomTagNameByCustomTagId(String customTagId,String CustomTagName);

    /**
     * 功能描述：根据自定义标签id逻辑删除自定义标签
     *
     * @param customTagId	自定义标签ID
     * @return
     */
    void logicalDeleteCustomTagByCustomTagId(String customTagId);

    /**
     * 功能描述：根据自定义标签id列表统计其中有效数据且覆盖人数大于零 的个数
     *
     * @param customTagId
     * @return
     */
    long countValidCustomTagAndCoverNumber(List<String> customTagId);



}
