package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CouponInfoIn;

public interface CouponSaveService {

    BaseOutput save(CouponInfoIn couponInfo);

}
