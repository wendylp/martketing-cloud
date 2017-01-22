package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface CustomtagCategoryLessListService {

    /**
     * 功能描述：未分类标签添加到指定分类，分类列表查询(不显示未分类)
     * 
     * 接口：mkt.customtag.category.less.list
     * 
     * @return
     */
    public BaseOutput customtagCategoryLessListGet();
}
