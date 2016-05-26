package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class WechatPublic extends BaseQuery {
    private Integer id;

    private String accountId;

    private String fansCount;

    private String isAuthorized;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getFansCount() {
        return fansCount;
    }

    public void setFansCount(String fansCount) {
        this.fansCount = fansCount == null ? null : fansCount.trim();
    }

    public String getIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(String isAuthorized) {
        this.isAuthorized = isAuthorized == null ? null : isAuthorized.trim();
    }
}
