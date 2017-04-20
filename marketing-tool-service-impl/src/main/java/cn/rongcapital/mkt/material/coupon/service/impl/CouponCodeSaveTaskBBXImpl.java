package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponReadyStatusType;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;


@Service
public class CouponCodeSaveTaskBBXImpl implements TaskService{

    private static final Logger logger = LoggerFactory.getLogger(CouponCodeSaveTaskBBXImpl.class);
    
    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;
    
    @Autowired
    private MaterialCouponDao materialCouponDao;
    
    @Override
    public void task(Integer taskId) {
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void task(String taskHeadIdStr) {
        Long couponId = null;
        Integer stockTotal = null;
        MaterialCoupon coupon = new MaterialCoupon();
        try {
        	
            logger.info("MQ消费，起始时间" + System.currentTimeMillis());
            JSONUtils.parse(taskHeadIdStr);
            Date now = new Date();
            JSONObject jsonObject = JSONObject.parseObject(taskHeadIdStr);
            stockTotal = jsonObject.getInteger("stock_total");
            couponId = jsonObject.getLong("id");
            coupon = materialCouponDao.selectOneCoupon(couponId);
            List<MaterialCouponCode> list = new ArrayList<MaterialCouponCode>();
            // 编辑
            materialCouponCodeDao.deleteCodeByCouponId(couponId);
            // 通用码
            JSONObject ruleObject = JSONObject.parseObject(coupon.getRule());
            String code = ruleObject.getString("coupon_code");
            getUniversalCode(code, stockTotal, couponId, list, now);
            
            int totleSize = list.size();
            logger.info("code size is {}", totleSize);
            if (totleSize > 0) {
                int pageSize = 100000;
                int num = totleSize / pageSize;
                int surplus = totleSize % pageSize;
                boolean surplusFlag = false;
                if (surplus > 0) {
                    num = num + 1;
                    surplusFlag = true;
                }
                for (int i = 0; i < num; i++) {
                    List<MaterialCouponCode> codeList = null;
                    if (surplusFlag && (i == num - 1)) {
                        codeList = list.subList(i * pageSize, (i * pageSize + surplus));
                    } else {
                        codeList = list.subList(i * pageSize, (i + 1) * pageSize);
                    }
                    materialCouponCodeDao.batchInsert(codeList);
                }
            }
        } catch (Exception e) {
            logger.error("CouponCodeSaveTaskBBXImpl task error", e);
        } finally {
            coupon.setStockRest(stockTotal);
            coupon.setStockTotal(stockTotal);
            materialCouponDao.updateById(coupon);
            logger.info("MQ消费，结束时间" + System.currentTimeMillis());
        }
    }
    
    /**
     * 通用码
     * @param ruleObject
     * @param stock_total
     * @param couponId
     * @param list
     * @param now
     */
    private void getUniversalCode(String unCode, Integer stock_total, Long couponId, List<MaterialCouponCode> list, Date now){
        for(int i = 0; i< stock_total; i ++){
            MaterialCouponCode code = new MaterialCouponCode();
            code.setCode(unCode);
            code.setCouponId(couponId);
            code.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRELEASED.getCode());
            code.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
            code.setStatus((byte) 0);
            code.setCreateTime(now);
            code.setUpdateTime(now);
            list.add(code);
        }
    }
    
}
