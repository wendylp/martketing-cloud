package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author guozhenchao
 *
 */
public interface CouponCodeListService {
    BaseOutput couponCodeList(Long id, Integer index, Integer size);

    BaseOutput couponIssuedCodeList(Long id, Integer index, Integer size);
}
