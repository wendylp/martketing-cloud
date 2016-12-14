/*************************************************
 * @功能简述: 新建固定人群
 * @see CouponApi：
 * @author: shanjingqi
 * @version: 1.0 @date：2016-12-13
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface MaterialCouponAudienceCreateService {

    public BaseOutput createTargetAudienceGroup(Long id,String audienceName,String blurSearch, String releaseStatus, String verifyStatus, String expireStatus);
}
