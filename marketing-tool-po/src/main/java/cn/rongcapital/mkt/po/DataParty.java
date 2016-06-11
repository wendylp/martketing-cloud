package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class DataParty extends BaseQuery {
    private Integer id;

    private Integer taskId;

    private String accountName;

    private String name;

    private Integer gender;

    private Integer age;

    private String homeAddress;

    private String workAddress;

    private String homeStatus;

    private String workStatus;

    private String memberLevel;

    private Long mobile;

    private String email;

    private String wechat;

    private Integer qq;

    private String weibo;

    private Integer orderCount;

    private Long orderAmount;

    private Integer cartItemCount;

    private Integer favoriteItemCount;

    private Long salary;

    private Integer offlineActivityAttendence;

    private Byte childAmount;

    private BigDecimal childAnnualBudget;

    private Boolean deleted;

    private Date deleteTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress == null ? null : homeAddress.trim();
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress == null ? null : workAddress.trim();
    }

    public String getHomeStatus() {
        return homeStatus;
    }

    public void setHomeStatus(String homeStatus) {
        this.homeStatus = homeStatus == null ? null : homeStatus.trim();
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus == null ? null : workStatus.trim();
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel == null ? null : memberLevel.trim();
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public Integer getQq() {
        return qq;
    }

    public void setQq(Integer qq) {
        this.qq = qq;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo == null ? null : weibo.trim();
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getCartItemCount() {
        return cartItemCount;
    }

    public void setCartItemCount(Integer cartItemCount) {
        this.cartItemCount = cartItemCount;
    }

    public Integer getFavoriteItemCount() {
        return favoriteItemCount;
    }

    public void setFavoriteItemCount(Integer favoriteItemCount) {
        this.favoriteItemCount = favoriteItemCount;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Integer getOfflineActivityAttendence() {
        return offlineActivityAttendence;
    }

    public void setOfflineActivityAttendence(Integer offlineActivityAttendence) {
        this.offlineActivityAttendence = offlineActivityAttendence;
    }

    public Byte getChildAmount() {
        return childAmount;
    }

    public void setChildAmount(Byte childAmount) {
        this.childAmount = childAmount;
    }

    public BigDecimal getChildAnnualBudget() {
        return childAnnualBudget;
    }

    public void setChildAnnualBudget(BigDecimal childAnnualBudget) {
        this.childAnnualBudget = childAnnualBudget;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}
