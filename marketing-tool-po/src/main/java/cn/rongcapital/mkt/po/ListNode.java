package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ListNode extends BaseQuery {
    private Long id;

    private Long listId;

    private Long campaignTmplId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public Long getCampaignTmplId() {
        return campaignTmplId;
    }

    public void setCampaignTmplId(Long campaignTmplId) {
        this.campaignTmplId = campaignTmplId;
    }
}
