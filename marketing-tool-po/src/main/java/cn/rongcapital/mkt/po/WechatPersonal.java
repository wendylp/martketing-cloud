package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class WechatPersonal extends BaseQuery {
    private Integer id;

    private String accountId;

    private String friendCount;

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

    public String getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(String friendCount) {
        this.friendCount = friendCount == null ? null : friendCount.trim();
    }
}
