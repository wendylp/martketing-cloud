package cn.rongcapital.mkt.bbx.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class BbxCouponCodeAdd extends BaseQuery{
    /**
     * Description:
     */
    private static final long serialVersionUID = -7416627429507594664L;

    private Integer id;

    private Integer vipId;

    private Integer couponId;

    private Integer actionId;

    private Double couponMoney;

    private String canUseBeginDate;

    private String canUserEndDate;

    private String storeCode;

    private Date createTime;

    private Boolean synchronizeable;

    private Boolean synchSuccess;

    private Date synchronizedTime;

    private String phone;

    private Long couponCodeId;

    private Boolean checked;

    private String mainId;

    private Integer campsignId;

    private String itemId;

    private Long smsTaskHeadId;

    private Boolean smsSended;

    private Long smsTaskDetailId;

    private String errorMsg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Double getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Double couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getCanUseBeginDate() {
        return canUseBeginDate;
    }

    public void setCanUseBeginDate(String canUseBeginDate) {
        this.canUseBeginDate = canUseBeginDate == null ? null : canUseBeginDate.trim();
    }

    public String getCanUserEndDate() {
        return canUserEndDate;
    }

    public void setCanUserEndDate(String canUserEndDate) {
        this.canUserEndDate = canUserEndDate == null ? null : canUserEndDate.trim();
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode == null ? null : storeCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getSynchronizeable() {
        return synchronizeable;
    }

    public void setSynchronizeable(Boolean synchronizeable) {
        this.synchronizeable = synchronizeable;
    }

    public Boolean getSynchSuccess() {
        return synchSuccess;
    }

    public void setSynchSuccess(Boolean synchSuccess) {
        this.synchSuccess = synchSuccess;
    }

    public Date getSynchronizedTime() {
        return synchronizedTime;
    }

    public void setSynchronizedTime(Date synchronizedTime) {
        this.synchronizedTime = synchronizedTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Long getCouponCodeId() {
        return couponCodeId;
    }

    public void setCouponCodeId(Long couponCodeId) {
        this.couponCodeId = couponCodeId;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId == null ? null : mainId.trim();
    }

    public Integer getCampsignId() {
        return campsignId;
    }

    public void setCampsignId(Integer campsignId) {
        this.campsignId = campsignId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public Long getSmsTaskHeadId() {
        return smsTaskHeadId;
    }

    public void setSmsTaskHeadId(Long smsTaskHeadId) {
        this.smsTaskHeadId = smsTaskHeadId;
    }

    public Boolean getSmsSended() {
        return smsSended;
    }

    public void setSmsSended(Boolean smsSended) {
        this.smsSended = smsSended;
    }

    public Long getSmsTaskDetailId() {
        return smsTaskDetailId;
    }

    public void setSmsTaskDetailId(Long smsTaskDetailId) {
        this.smsTaskDetailId = smsTaskDetailId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }
}