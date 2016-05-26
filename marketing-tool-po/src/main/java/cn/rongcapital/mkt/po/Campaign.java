package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class Campaign extends BaseQuery {
    private Long id;

    private String name;

    private String assetIdList;

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

    public String getAssetIdList() {
        return assetIdList;
    }

    public void setAssetIdList(String assetIdList) {
        this.assetIdList = assetIdList == null ? null : assetIdList.trim();
    }
}
