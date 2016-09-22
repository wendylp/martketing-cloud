package cn.rongcapital.mkt.dao.mongo;

import cn.rongcapital.mkt.po.base.BaseTag;

/**
 * Created by Yunfeng on 2016-9-21.
 */
public interface MongoBaseTagDao {

    boolean insertBaseTagDao(BaseTag baseTag);

    BaseTag findOneBaseTag(BaseTag baseTag);

    BaseTag updateBaseTag(BaseTag baseTag);

}
