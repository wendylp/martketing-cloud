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
    
    /**
     * 功能描述：细分中查询自定义标签列表
     * 
     * 接口：mkt.segment.customtag.get
     * 
     * @param customTagCategoryId
     * 					自定义标签分类id
     * @return BaseOutput
     */
    public BaseOutput getCustomtagList(String customTagCategoryId);
}
