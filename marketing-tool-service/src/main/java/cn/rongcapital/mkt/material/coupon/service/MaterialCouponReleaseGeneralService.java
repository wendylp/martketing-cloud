package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface MaterialCouponReleaseGeneralService {

	public BaseOutput releaseGeneralById(Long id,String userToken,String version);
}
