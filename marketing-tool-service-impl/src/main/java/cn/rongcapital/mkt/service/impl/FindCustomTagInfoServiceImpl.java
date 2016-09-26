package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDaoImpl;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.po.mongodb.CustomTagTypeLayer;
import cn.rongcapital.mkt.service.FindCustomTagInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Yunfeng on 2016-9-23.
 */
@Service
public class FindCustomTagInfoServiceImpl implements FindCustomTagInfoService {

    @Autowired
    private MongoBaseTagDaoImpl mongoBaseTagDao;

    //如果是叶子节点需要传递tagSource，分支节点tagSource传递Null即可
    @Override
    public BaseTag findCustomTagByTagNameAndTagPath(String tagName, String tagPath, String tagSource) {
        BaseTag baseTag = null;
        if(tagSource != null){
            baseTag = new CustomTagLeaf();
        }else{
            baseTag = new CustomTagTypeLayer();
        }
        baseTag.setTagName(tagName);
        baseTag.setPath(tagPath);
        baseTag.setSource(tagSource);
        return mongoBaseTagDao.findOneBaseTag(baseTag);
    }

    @Override
    public BaseTag findCustomTagByTag(BaseTag baseTag) {
        if(baseTag ==null || !baseTag.validateTag()) return null;
        return mongoBaseTagDao.findOneBaseTag(baseTag);
    }

    @Override
    public List<BaseTag> findAllCustomTagLeaf() {
        return mongoBaseTagDao.findBaseTagListByTagType(ApiConstant.CUSTOM_TAG_LEAF_TYPE);
    }

    @Override
    public BaseTag findCustomTagInfoByTagId(String tagId) {
        if(StringUtils.isEmpty(tagId)) return null;
        BaseTag targetTag = mongoBaseTagDao.findCustomTagLeafByTagId(tagId);
        if(targetTag != null && targetTag instanceof CustomTagLeaf){
            return targetTag;
        }
        return null;
    }
}
