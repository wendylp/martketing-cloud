package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TagSegmentFuzzyListService {
    /**
     * 功能描述：细分分析---标签搜索
     * 
     * 接口:mkt.tag.segment.fuzzy.list
     * 
     * @param name
     * @return
     */
    public BaseOutput tagSegmentFuzzyListService(String name);
}
