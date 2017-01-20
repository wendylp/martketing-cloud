package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface CustomtagListService {
    /**
     * 功能描述：标签列表(分页， 分类名称展示)
     * 
     * 接口：mkt.customtag.list
     * 
     * @param customTagCategoryId
     * @param index
     * @param size
     * @return
     */
    public BaseOutput customtagListGet(String customTagCategoryId, Integer index, Integer size);
}
