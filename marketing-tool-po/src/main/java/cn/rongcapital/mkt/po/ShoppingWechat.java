package cn.rongcapital.mkt.po;

import java.math.BigDecimal;
import java.util.Date;

public class ShoppingWechat {

    // 数据量
    private Integer totalCount;

    // data_party的主键, 在mongodb中是mid
    private Integer dataPartyId;

    // openId, 对应数据库中的wx_code
    private String openId;

    // pubId, 对应数据库中的wxmp_id
    private String pubId;

    // 购物记录中单个微信用户（公众号标识＋openid）最后一次购买（取订单号的消费时间记最后一次购买时间）的时间
    private Date lastShoppingTime;

    // 购物记录中单个微信用户（公众号标识＋openid）购买的总次数（订单号）
    private Integer totalShoppingCount;

    // 购物记录中单个微信用户（公众号标识＋openid）单月购买次数（订单号）
    private Integer singleMonthShoppingCount;

    // 支付记录中支付状态成功，单个微信用户（公众号标识＋openid）收入金额总额
    private BigDecimal totalIncome;

    // 支付记录中支付状态成功，单个微信用户（公众号标识＋openid）收入金额总额取平均数
    private BigDecimal averageIncome;

    // 公众号下，openid有购买记录的用户标记为是购买用户，其余的标记为不是购买用户 -- 是/否
    private boolean isShoppingUser;

    // 购物记录－购物渠道－官网字段／分销字段--（新增）交易渠道偏好－微盟－（旺铺、其它）
    private String weimob;

    // 购物记录的订单状态中，交易完成／交易关闭／待支付
    private String orderStatus;

    public Integer getDataPartyId() {
        return dataPartyId;
    }

    public void setDataPartyId(Integer dataPartyId) {
        this.dataPartyId = dataPartyId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public Date getLastShoppingTime() {
        return lastShoppingTime;
    }

    public void setLastShoppingTime(Date lastShoppingTime) {
        this.lastShoppingTime = lastShoppingTime;
    }

    public Integer getTotalShoppingCount() {
        return totalShoppingCount;
    }

    public void setTotalShoppingCount(Integer totalShoppingCount) {
        this.totalShoppingCount = totalShoppingCount;
    }

    public Integer getSingleMonthShoppingCount() {
        return singleMonthShoppingCount;
    }

    public void setSingleMonthShoppingCount(Integer singleMonthShoppingCount) {
        this.singleMonthShoppingCount = singleMonthShoppingCount;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getAverageIncome() {
        return averageIncome;
    }

    public void setAverageIncome(BigDecimal averageIncome) {
        this.averageIncome = averageIncome;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isShoppingUser() {
        return isShoppingUser;
    }

    public void setShoppingUser(boolean isShoppingUser) {
        this.isShoppingUser = isShoppingUser;
    }

    public String getWeimob() {
        return weimob;
    }

    public void setWeimob(String weimob) {
        this.weimob = weimob;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
