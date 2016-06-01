package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class WechatAssetGroup extends BaseQuery {
    private Long id;

    private String name;

    private Integer members;

    private String wxAcct;

    private Integer isSysGroup;

    private Integer assetId;

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

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public String getWxAcct() {
        return wxAcct;
    }

    public void setWxAcct(String wxAcct) {
        this.wxAcct = wxAcct == null ? null : wxAcct.trim();
    }

    public Integer getIsSysGroup() {
        return isSysGroup;
    }

    public void setIsSysGroup(Integer isSysGroup) {
        this.isSysGroup = isSysGroup;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }
}
