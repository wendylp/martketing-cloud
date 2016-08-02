package cn.rongcapital.mkt.po;

import java.math.BigDecimal;
import java.util.Date;

public class ShoppingWechat {

    private Integer dataPartyId;

    private String openId;

    private String pubId;

    private Date lastShoppingTime;

    private Integer totalShoppingCount;

    private Integer singleMonthShoppingCount;

    private BigDecimal totalIncome;

    private BigDecimal averageIncome;

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

}