/*************************************************
 * @功能简述: 批量回写优惠码状态
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016/12/7
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service;

import java.util.List;

import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCodeStatusUpdateVO;


public interface MaterialCouponCodeStatusUpdateService {

    /**
     * 批量回写优惠码状态
     * 
     * @param voList
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    public void updateMaterialCouponCodeStatus(List<MaterialCouponCodeStatusUpdateVO> voList);
    
    /**
     * 批量获取MaterialCouponCodeStatusUpdateVO对象，通过List<MaterialCouponCode>
     * @param codeList
     * @return
     */
    public List<MaterialCouponCodeStatusUpdateVO> getReleasedMaterialCouponCodeStatusUpdateVOes(List<Object> codeList);

}
