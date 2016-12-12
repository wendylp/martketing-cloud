package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CouponInfoIn;

public interface CouponSaveService {

    BaseOutput save(CouponInfoIn couponInfo);

}
