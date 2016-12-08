/*************************************************
 * @功能简述: MaterialCouponCodeStatusUpdateService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.MaterialCouponCodeDao;
import cn.rongcapital.mkt.po.MaterialCouponCode;
import cn.rongcapital.mkt.service.MaterialCouponCodeStatusUpdateService;
import cn.rongcapital.mkt.vo.MaterialCouponCodeStatusUpdateVO;

@Service
public class MaterialCouponCodeStatusUpdateServiceImpl implements MaterialCouponCodeStatusUpdateService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    /**
     * 批量回写优惠码状态
     * 
     * @param voList
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    @Override
    public void updateMaterialCouponCodeStatus(List<MaterialCouponCodeStatusUpdateVO> voList) {
        if (CollectionUtils.isEmpty(voList)) {
            for (MaterialCouponCodeStatusUpdateVO vo : voList) {
                logger.debug("process coupon code param is {}.", vo);
                processMaterialCouponCode(vo);
            }
        }
    }


    private void processMaterialCouponCode(MaterialCouponCodeStatusUpdateVO vo) {
        if (vo.getId() == null || StringUtils.isBlank(vo.getUser()) || StringUtils.isBlank(vo.getStatus())) {
            logger.error("param {} is illegal.", vo);
            return;
        }
        MaterialCouponCode po = new MaterialCouponCode();
        po.setId(vo.getId());
        po.setUser(vo.getUser());
        po.setReleaseStatus(vo.getStatus());
        materialCouponCodeDao.updateByIdAndStatus(po);
    }

}
