package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface CustomtagFuzzyNameListService {
    /**
     * 功能描述：添加标签时， 查询已存在类似标签列表
     * 
     * 接口：mkt.customtag.fuzzy.name.list
     * 
     * @param customTagCategoryId
     * @param customTagName
     * @return
     */
    public BaseOutput customtagFuzzyNameListGet(String customTagCategoryId, String customTagName);
}
