package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class DataPersonal extends BaseQuery {
    private Integer id;

    private Integer openId;

    private String personalName;

    private String nickName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOpenId() {
        return openId;
    }

    public void setOpenId(Integer openId) {
        this.openId = openId;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName == null ? null : personalName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }
}
