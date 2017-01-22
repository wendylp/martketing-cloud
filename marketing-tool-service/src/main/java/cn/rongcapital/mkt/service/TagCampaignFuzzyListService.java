package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface TagCampaignFuzzyListService {
    /**
     * 功能描述：标签查询、 自定义标签查询 活动编排
     * 
     * 接口：mkt.tag.campaign.fuzzy.list
     * 
     * @param name
     * @return
     */
    public BaseOutput tagCampaignFuzzyListGet(String name);
}
