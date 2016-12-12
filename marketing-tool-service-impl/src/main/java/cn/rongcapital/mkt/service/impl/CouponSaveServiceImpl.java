package cn.rongcapital.mkt.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.CouponCodeType;
import cn.rongcapital.mkt.common.enums.CouponStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponTypeEnum;
import cn.rongcapital.mkt.dao.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.MaterialCouponDao;
import cn.rongcapital.mkt.po.MaterialCoupon;
import cn.rongcapital.mkt.po.MaterialCouponCode;
import cn.rongcapital.mkt.service.CouponSaveService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CouponInfoIn;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;

@Service
public class CouponSaveServiceImpl implements CouponSaveService{

    @Autowired
    private MaterialCouponDao materialCouponDao;
    
    private MaterialCouponCodeDao materialCouponCodeDao;
    
    private static final String[] database = {"a","b","c",};
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput save(CouponInfoIn couponInfo) {
        
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
        Long id = couponInfo.getId();
        String title = couponInfo.getTitle();
        String SourceCode = couponInfo.getSource_code();
        String rule = couponInfo.getRule();
        Integer stock_total = couponInfo.getStock_total();
        BigDecimal amount = couponInfo.getAmount();
        String channel_code = couponInfo.getChannel_code();
        Date startTime = couponInfo.getStart_time();
        Date endTime = couponInfo.getEnd_time();
        Date now = new Date();
        try {
            JSONUtils.parse(rule);
        } catch (Exception e) {
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
            return baseOutput;
        }
        
        if(id == null){
            //保存操作
            MaterialCoupon coupon = new MaterialCoupon();
            coupon.setTitle(title);
            coupon.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
            if(SourceCode.equals(CouponCodeType.UNIVERSALCODE.getCode())){
               //通用码
               coupon.setRule(rule);
               JSONObject ruleObject = (JSONObject) JSONUtils.parse(rule);
               //优惠码使用
               String unCode = ruleObject.getString("coupon_code");
               coupon.setStockTotal(stock_total);
               coupon.setStockRest(stock_total);
               coupon.setAmount(amount);
               coupon.setSourceCode(SourceCode);
               coupon.setChannelCode(channel_code);
               coupon.setCouponStatus(CouponStatusEnum.COUPONSTATUS_UNUSED.getCode());
               coupon.setCreateTime(now);
               coupon.setUpdateTime(now);
               coupon.setStartTime(startTime);
               coupon.setEndTime(endTime);
               materialCouponDao.insert(coupon);
               List<MaterialCouponCode> list = new ArrayList<MaterialCouponCode>();            
               for(int i = 0; i< stock_total; i ++){
                   MaterialCouponCode code = new MaterialCouponCode();
                   code.setCode(unCode);
                   code.setCouponId(coupon.getId());
                   code.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRELEASED.getCode());
                   code.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
                   code.setStatus((byte) 0);
                   code.setCreateTime(now);
                   code.setUpdateTime(now);
                   list.add(code);
               }
               materialCouponCodeDao.batchInsert(list);
            }else if(SourceCode.equals(CouponCodeType.GENERATIONCODE.getCode())){
                //平台生成码
                
                
            }
        }else{
            //更新操作
            
            
        }
        return baseOutput;
    }
}
