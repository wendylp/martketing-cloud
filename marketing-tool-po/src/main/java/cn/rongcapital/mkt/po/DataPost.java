package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class DataPost extends BaseQuery {
    private Integer id;

    private String accountName;

    private Date time;

    private String store;

    private BigDecimal monetary;

    private String skuList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store == null ? null : store.trim();
    }

    public BigDecimal getMonetary() {
        return monetary;
    }

    public void setMonetary(BigDecimal monetary) {
        this.monetary = monetary;
    }

    public String getSkuList() {
        return skuList;
    }

    public void setSkuList(String skuList) {
        this.skuList = skuList == null ? null : skuList.trim();
    }
}
