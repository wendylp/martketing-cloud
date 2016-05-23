package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class DecisionNode extends BaseQuery{
    private Integer id;

    private String type;

    private String rule_key;

    private String campaign_tmpl_id;

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

    public String getRule_key() {
        return rule_key;
    }

    public void setRule_key(String rule_key) {
        this.rule_key = rule_key == null ? null : rule_key.trim();
    }

    public String getCampaign_tmpl_id() {
        return campaign_tmpl_id;
    }

    public void setCampaign_tmpl_id(String campaign_tmpl_id) {
        this.campaign_tmpl_id = campaign_tmpl_id == null ? null : campaign_tmpl_id.trim();
    }
}
