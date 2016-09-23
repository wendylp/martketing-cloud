package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.po.base.BaseTag;

import java.util.List;

/**
 * Created by Yunfeng on 2016-9-23.
 */
public interface FindCustomTagInfoService {
    BaseTag findCustomTagByTagNameAndTagPath(String tagName,String tagPath,String tagSource);
    BaseTag findCustomTagByTag(BaseTag baseTag);
    List<BaseTag> findAllCustomTagLeaf();
}
