package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class Campaign extends BaseQuery{
    private Long id;

    private String name;

    private String asset_id_list;

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

    public String getAsset_id_list() {
        return asset_id_list;
    }

    public void setAsset_id_list(String asset_id_list) {
        this.asset_id_list = asset_id_list == null ? null : asset_id_list.trim();
    }
}
