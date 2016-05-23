package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class Resource extends BaseQuery{
    private Long id;

    private String resource_action;

    private String description;

    private Byte status;

    private byte[] resource_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResource_action() {
        return resource_action;
    }

    public void setResource_action(String resource_action) {
        this.resource_action = resource_action == null ? null : resource_action.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public byte[] getResource_id() {
        return resource_id;
    }

    public void setResource_id(byte[] resource_id) {
        this.resource_id = resource_id;
    }
}
