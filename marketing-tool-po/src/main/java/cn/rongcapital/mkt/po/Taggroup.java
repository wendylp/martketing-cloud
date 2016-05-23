package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class Taggroup extends BaseQuery{
    private Long id;

    private String name;

    private Long parent_group_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getParent_group_id() {
        return parent_group_id;
    }

    public void setParent_group_id(Long parent_group_id) {
        this.parent_group_id = parent_group_id;
    }
}
