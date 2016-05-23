package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ActionNode extends BaseQuery{
    private Long id;

    private Long action_id;

    private Long campaign_tmpl_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAction_id() {
        return action_id;
    }

    public void setAction_id(Long action_id) {
        this.action_id = action_id;
    }

    public Long getCampaign_tmpl_id() {
        return campaign_tmpl_id;
    }

    public void setCampaign_tmpl_id(Long campaign_tmpl_id) {
        this.campaign_tmpl_id = campaign_tmpl_id;
    }
}
