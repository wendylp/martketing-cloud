package cn.rongcapital.mkt.mongodao;

import cn.rongcapital.mkt.po.mongodb.CustomTag;

import java.util.List;

/**
 * Created by byf on 1/15/17.
 */
public interface MongoCustomTagDao {

    void insertCustomTag(CustomTag paramCustomTag);

    List<String> findCustomTagNames(CustomTag paramCustomTag);

    CustomTag findByCustomTagId(String customTagId);
}
