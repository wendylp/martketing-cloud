package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class WechatPublic extends BaseQuery{
    private Integer id;

    private String account_id;

    private String fans_count;

    private String is_authorized;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id == null ? null : account_id.trim();
    }

    public String getFans_count() {
        return fans_count;
    }

    public void setFans_count(String fans_count) {
        this.fans_count = fans_count == null ? null : fans_count.trim();
    }

    public String getIs_authorized() {
        return is_authorized;
    }

    public void setIs_authorized(String is_authorized) {
        this.is_authorized = is_authorized == null ? null : is_authorized.trim();
    }
}
