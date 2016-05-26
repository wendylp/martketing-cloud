package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class Resource extends BaseQuery {
    private Long id;

    private String resourceAction;

    private String description;

    private Byte status;

    private byte[] resourceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceAction() {
        return resourceAction;
    }

    public void setResourceAction(String resourceAction) {
        this.resourceAction = resourceAction == null ? null : resourceAction.trim();
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

    public byte[] getResourceId() {
        return resourceId;
    }

    public void setResourceId(byte[] resourceId) {
        this.resourceId = resourceId;
    }
}
