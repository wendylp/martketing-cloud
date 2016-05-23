package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ListNode extends BaseQuery{
    private Long id;

    private Long list_id;

    private Long campaign_tmpl_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getList_id() {
        return list_id;
    }

    public void setList_id(Long list_id) {
        this.list_id = list_id;
    }

    public Long getCampaign_tmpl_id() {
        return campaign_tmpl_id;
    }

    public void setCampaign_tmpl_id(Long campaign_tmpl_id) {
        this.campaign_tmpl_id = campaign_tmpl_id;
    }
}
