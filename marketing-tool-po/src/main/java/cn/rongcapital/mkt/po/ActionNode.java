package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ActionNode extends BaseQuery {
    private Long id;

    private Long actionId;

    private Long campaignTmplId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getCampaignTmplId() {
        return campaignTmplId;
    }

    public void setCampaignTmplId(Long campaignTmplId) {
        this.campaignTmplId = campaignTmplId;
    }
}
