package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ContactWay extends BaseQuery {
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}