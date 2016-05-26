package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class DecisionNode extends BaseQuery {
    private Integer id;

    private String type;

    private String ruleKey;

    private String campaignTmplId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRuleKey() {
        return ruleKey;
    }

    public void setRuleKey(String ruleKey) {
        this.ruleKey = ruleKey == null ? null : ruleKey.trim();
    }

    public String getCampaignTmplId() {
        return campaignTmplId;
    }

    public void setCampaignTmplId(String campaignTmplId) {
        this.campaignTmplId = campaignTmplId == null ? null : campaignTmplId.trim();
    }
}
