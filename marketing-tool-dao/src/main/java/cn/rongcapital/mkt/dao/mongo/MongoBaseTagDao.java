package cn.rongcapital.mkt.dao.mongo;

import cn.rongcapital.mkt.po.base.BaseTag;

import java.util.List;

/**
 * Created by Yunfeng on 2016-9-21.
 */
public interface MongoBaseTagDao {

    boolean insertBaseTagDao(BaseTag baseTag);

    BaseTag findOneBaseTag(BaseTag baseTag);

    List<BaseTag> findBaseTagListByTagType(Integer tagType);

    BaseTag updateBaseTag(BaseTag baseTag);

}
