package cn.rongcapital.mkt.mongodao;

import cn.rongcapital.mkt.po.mongodb.CustomTag;

/**
 * Created by byf on 1/15/17.
 */
public interface MongoCustomTagDao {

    void insertCustomTag(CustomTag customTag);
    
    CustomTag findByCustomTagId(String customTagId);
}
