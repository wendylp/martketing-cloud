package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface SegmentCustomtagCategoryListService {
    /**
     * 功能描述：自定义分类列表 去除覆盖人数为零的列表
     * 
     * 接口：mkt.segment.customtag.category.list
     * 
     * @return
     */
    public BaseOutput getSegmentCustomTagCategoryList();
}
