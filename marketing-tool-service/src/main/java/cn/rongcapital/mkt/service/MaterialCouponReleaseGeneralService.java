package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface MaterialCouponReleaseGeneralService {

	public BaseOutput releaseGeneralById(Long id,String userToken,String version);
}
