package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class WechatQrcodeFocus extends BaseQuery {
    private Integer id;

    private String qrcodeId;

    private String wxName;

    private Integer chCode;

    private String openid;

    private String focusDatetime;

    private String unfocusDatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQrcodeId() {
        return qrcodeId;
    }

    public void setQrcodeId(String qrcodeId) {
        this.qrcodeId = qrcodeId == null ? null : qrcodeId.trim();
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName == null ? null : wxName.trim();
    }

    public Integer getChCode() {
        return chCode;
    }

    public void setChCode(Integer chCode) {
        this.chCode = chCode;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getFocusDatetime() {
        return focusDatetime;
    }

    public void setFocusDatetime(String focusDatetime) {
        this.focusDatetime = focusDatetime == null ? null : focusDatetime.trim();
    }

    public String getUnfocusDatetime() {
        return unfocusDatetime;
    }

    public void setUnfocusDatetime(String unfocusDatetime) {
        this.unfocusDatetime = unfocusDatetime == null ? null : unfocusDatetime.trim();
    }
}
