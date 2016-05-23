package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class NodeDecision extends BaseQuery{
    private Long id;

    private Byte type;

    private String rule_key;

    private String campaign_tpl_id;

    private Byte node_status;

    private String pos_x;

    private String pos_y;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getRule_key() {
        return rule_key;
    }

    public void setRule_key(String rule_key) {
        this.rule_key = rule_key == null ? null : rule_key.trim();
    }

    public String getCampaign_tpl_id() {
        return campaign_tpl_id;
    }

    public void setCampaign_tpl_id(String campaign_tpl_id) {
        this.campaign_tpl_id = campaign_tpl_id == null ? null : campaign_tpl_id.trim();
    }

    public Byte getNode_status() {
        return node_status;
    }

    public void setNode_status(Byte node_status) {
        this.node_status = node_status;
    }

    public String getPos_x() {
        return pos_x;
    }

    public void setPos_x(String pos_x) {
        this.pos_x = pos_x == null ? null : pos_x.trim();
    }

    public String getPos_y() {
        return pos_y;
    }

    public void setPos_y(String pos_y) {
        this.pos_y = pos_y == null ? null : pos_y.trim();
    }
}
