package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CampaignTemplate extends BaseQuery{
    private Long id;

    private Long campaign_id;

    private Long node_id;

    private Long pre_node_id;

    private Long next_node_id;

    private String node_table_name;

    private Byte pre_node_line_type;

    private Byte next_node_line_type;

    private Byte node_type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(Long campaign_id) {
        this.campaign_id = campaign_id;
    }

    public Long getNode_id() {
        return node_id;
    }

    public void setNode_id(Long node_id) {
        this.node_id = node_id;
    }

    public Long getPre_node_id() {
        return pre_node_id;
    }

    public void setPre_node_id(Long pre_node_id) {
        this.pre_node_id = pre_node_id;
    }

    public Long getNext_node_id() {
        return next_node_id;
    }

    public void setNext_node_id(Long next_node_id) {
        this.next_node_id = next_node_id;
    }

    public String getNode_table_name() {
        return node_table_name;
    }

    public void setNode_table_name(String node_table_name) {
        this.node_table_name = node_table_name == null ? null : node_table_name.trim();
    }

    public Byte getPre_node_line_type() {
        return pre_node_line_type;
    }

    public void setPre_node_line_type(Byte pre_node_line_type) {
        this.pre_node_line_type = pre_node_line_type;
    }

    public Byte getNext_node_line_type() {
        return next_node_line_type;
    }

    public void setNext_node_line_type(Byte next_node_line_type) {
        this.next_node_line_type = next_node_line_type;
    }

    public Byte getNode_type() {
        return node_type;
    }

    public void setNode_type(Byte node_type) {
        this.node_type = node_type;
    }
}
