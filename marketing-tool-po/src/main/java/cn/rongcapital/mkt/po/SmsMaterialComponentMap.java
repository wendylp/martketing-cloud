package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;
import java.util.Date;

public class SmsMaterialComponentMap extends BaseQuery {
    private Long id;

    private Long smsMaterialId;

    private Long smsComponentId;

    private Integer smsComponentType;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSmsMaterialId() {
        return smsMaterialId;
    }

    public void setSmsMaterialId(Long smsMaterialId) {
        this.smsMaterialId = smsMaterialId;
    }

    public Long getSmsComponentId() {
        return smsComponentId;
    }

    public void setSmsComponentId(Long smsComponentId) {
        this.smsComponentId = smsComponentId;
    }

    public Integer getSmsComponentType() {
        return smsComponentType;
    }

    public void setSmsComponentType(Integer smsComponentType) {
        this.smsComponentType = smsComponentType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}