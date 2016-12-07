/*************************************************
 * @功能简述: 回写优惠券状态
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016/12/7
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.MaterialCouponStatusUpdateVO;


public interface MaterialCouponStatusUpdateService {

    /**
     * 回写优惠券状态
     * 
     * @param vo
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    public void updateMaterialCouponStatus(MaterialCouponStatusUpdateVO vo);

}
