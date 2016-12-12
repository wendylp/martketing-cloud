/*************************************************
 * @功能简述: MaterialCouponCodeStatusUpdateService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 1.0
 * @date: 2016/12/7
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.material.coupon.dao.MaterialCouponCodeDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeStatusUpdateService;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCodeStatusUpdateVO;

@Service
public class MaterialCouponCodeStatusUpdateServiceImpl implements MaterialCouponCodeStatusUpdateService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 批量处理最大数量
     */
    public static final int BATCH_PROCESS_CNT = 50;

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
        // 数据拆分
        List<List<MaterialCouponCodeStatusUpdateVO>> batchList = splitList(voList);
        if (CollectionUtils.isNotEmpty(batchList)) {
            for (List<MaterialCouponCodeStatusUpdateVO> batch : batchList) {
                logger.debug("process coupon code param is {}.", batch);
                processMaterialCouponCode(batch);
            }
        }
    }


    /**
     * 数据持久化处理
     * 
     * @param voList
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    private void processMaterialCouponCode(List<MaterialCouponCodeStatusUpdateVO> voList) {
        // 数据清洗
        List<MaterialCouponCodeStatusUpdateVO> voListFilter = new ArrayList<MaterialCouponCodeStatusUpdateVO>();
        for (MaterialCouponCodeStatusUpdateVO vo : voList) {
            if (vo.getId() == null || StringUtils.isBlank(vo.getUser()) || StringUtils.isBlank(vo.getStatus())) {
                logger.error("param {} is illegal.", vo);
                continue;
            }
            voListFilter.add(vo);
        }
        // 数据更新
        if (CollectionUtils.isNotEmpty(voListFilter)) {
            List<MaterialCouponCode> poList = new ArrayList<MaterialCouponCode>();
            MaterialCouponCode po = null;
            for (MaterialCouponCodeStatusUpdateVO vo : voListFilter) {
                po = new MaterialCouponCode();
                po.setId(vo.getId());
                po.setUser(vo.getUser());
                po.setReleaseStatus(vo.getStatus());
                poList.add(po);
            }
            try {
                materialCouponCodeDao.batchUpdateByIdAndStatus(poList);
            } catch (Exception e) {
                logger.error(String.format("param [%s] process occur error.", voList), e);
            }
        }
    }

    /**
     * 数据拆分
     * 
     * @param voList
     * @author zhuxuelong
     * @date: 2016/12/7
     */
    private List<List<MaterialCouponCodeStatusUpdateVO>> splitList(List<MaterialCouponCodeStatusUpdateVO> voList) {
        List<List<MaterialCouponCodeStatusUpdateVO>> list = null;
        if (CollectionUtils.isNotEmpty(voList)) {
            list = new ArrayList<List<MaterialCouponCodeStatusUpdateVO>>();
            int listSize = voList.size();
            int pageSize = (int) Math.ceil((float) listSize / BATCH_PROCESS_CNT);
            for (int i = 0; i < pageSize; i++) {
                int begin = (i) * BATCH_PROCESS_CNT;
                int end = (i + 1) * BATCH_PROCESS_CNT;
                end = end > listSize ? listSize : end;
                list.add(voList.subList(begin, end));
            }
        }
        return list;
    }
}
