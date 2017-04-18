package cn.rongcapital.mkt.po.mongodb;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "TBBXCoupon")
public class Coupon {
	
	@Id
	private String id;
	
    @Field("couponid")
    private Long couponId;

    @Field("couponname")
    private String couponName;

    @Field("coupontype")
    private String couponType;

    @Field("couponstate")
    private String couponStatus;

    @Field("couponvalue")
    private Double couponValue;

    @Field("couponbegintime")
    private String beginTime;

    @Field("couponendtime")
    private String endTime;

    private String remark;
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public Double getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(Double couponValue) {
        this.couponValue = couponValue;
    }

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

    public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", couponId=" + couponId + ", couponName=" + couponName + ", couponType="
				+ couponType + ", couponStatus=" + couponStatus + ", couponValue=" + couponValue + ", beginTime="
				+ beginTime + ", endTime=" + endTime + ", remark=" + remark + "]";
	}
    
}
