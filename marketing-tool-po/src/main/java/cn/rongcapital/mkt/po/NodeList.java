package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class NodeList extends BaseQuery{
    private Long id;

    private Long list_id;

    private Long campaign_tpl_id;

    private Byte node_status;

    private String pos_x;

    private String pos_y;

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

    public Long getCampaign_tpl_id() {
        return campaign_tpl_id;
    }

    public void setCampaign_tpl_id(Long campaign_tpl_id) {
        this.campaign_tpl_id = campaign_tpl_id;
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
