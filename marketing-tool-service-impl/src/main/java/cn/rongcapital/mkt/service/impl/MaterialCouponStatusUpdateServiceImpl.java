/*************************************************
 * @功能简述: MaterialCouponStatusUpdateService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.service.MaterialCouponStatusUpdateService;
import cn.rongcapital.mkt.vo.MaterialCouponStatusUpdateVO;

@Service
public class MaterialCouponStatusUpdateServiceImpl implements MaterialCouponStatusUpdateService {

    /**
     * 回写优惠券状态
     * 
     * @param vo
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    @Override
    @ReadWrite(type = ReadWriteType.WRITE)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateMaterialCouponStatus(MaterialCouponStatusUpdateVO vo) {
        // TODO Auto-generated method stub

    }

}
