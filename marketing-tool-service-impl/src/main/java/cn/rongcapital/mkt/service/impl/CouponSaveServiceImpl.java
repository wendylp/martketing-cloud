package cn.rongcapital.mkt.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponSourceCodeTypeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
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
    
    public static final String PATTERN_STANDARD19H = "yyyy-MM-dd HH:mm:ss";

    private static final String[] DATABASE_LETTER = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    
    private static final String[] DATABASE_NUMBER = {"0","1","2","3","4","5","6","7","8","9"};
    
    private static final String[] DATABASE_BLEND = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
    
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
        Date startTimeNew = dateStart(startTime, null);
        Date endTime = couponInfo.getEnd_time();
        Date endTimeNew = dateEnd(endTime, 23*60*60+59*60+59);
        Date now = new Date();
        try {
            JSONUtils.parse(rule);
        } catch (Exception e) {
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
            return baseOutput;
        }
        JSONObject ruleObject = (JSONObject) JSONUtils.parse(rule);
        if(id == null){
            //保存操作
            MaterialCoupon coupon = new MaterialCoupon();
            coupon.setTitle(title);
            coupon.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
            coupon.setRule(rule);
            coupon.setStockTotal(stock_total);
            coupon.setStockRest(stock_total);
            coupon.setAmount(amount);
            coupon.setSourceCode(SourceCode);
            coupon.setChannelCode(channel_code);
            coupon.setCouponStatus(MaterialCouponStatusEnum.UNUSED.getCode());
            coupon.setCreateTime(now);
            coupon.setUpdateTime(now);
            coupon.setStartTime(startTimeNew);
            coupon.setEndTime(endTimeNew);
            materialCouponDao.insert(coupon);
            List<MaterialCouponCode> list = new ArrayList<MaterialCouponCode>();   
            if(SourceCode.equals(CouponCodeType.UNIVERSALCODE.getCode())){
               //通用码
               //优惠码使用
               String unCode = ruleObject.getString("coupon_code");
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
            }else if(SourceCode.equals(CouponCodeType.GENERATIONCODE.getCode())){
                //平台生成码
                String codeType = ruleObject.getString("type_code");
                int length = ruleObject.getIntValue("length");
                String[] code = null;
                if(MaterialCouponSourceCodeTypeEnum.ALPHA.getCode().equals(codeType)){
                    //字母组合
                    code = getAllLists(DATABASE_LETTER, length);
                }else if(MaterialCouponSourceCodeTypeEnum.NUMBER.getCode().equals(codeType)){
                    //数字组合    
                    code = getAllLists(DATABASE_NUMBER, length);
                }else{
                    //混合组合
                    code = getAllLists(DATABASE_BLEND, length);
                }
                if(code.length > 0){
                    for(int i = 0; i < stock_total; i++){
                       String couponCode =  code[i];
                       MaterialCouponCode materialCouponCode = new MaterialCouponCode();
                       materialCouponCode.setCode(couponCode);
                       materialCouponCode.setCouponId(coupon.getId());
                       materialCouponCode.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRELEASED.getCode());
                       materialCouponCode.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
                       materialCouponCode.setStatus((byte) 0);
                       materialCouponCode.setCreateTime(now);
                       materialCouponCode.setUpdateTime(now);
                       list.add(materialCouponCode);
                    }
                }
            }else{
                //自有码
                
            }
            materialCouponCodeDao.batchInsert(list);
        }else{
            //更新操作
            
            
        }
        return baseOutput;
    }
    
    //生产码
    public static String[] getAllLists(String[] elements, int lengthOfList)
    {
        String[] allLists = new String[(int)Math.pow(elements.length, lengthOfList)];
        if(lengthOfList == 1) return elements; 
        else {
            String[] allSublists = getAllLists(elements, lengthOfList - 1);
            int arrayIndex = 0;

            for(int i = 0; i < elements.length; i++){
                for(int j = 0; j < allSublists.length; j++){
                    allLists[arrayIndex] = elements[i] + allSublists[j];
                    arrayIndex++;
                }
            }
            return allLists;
        }
    }
    
    /**
     * 时间格式转换 yyyy-MM-dd 转化成yyyy-MM-dd 00:00:00
     * @param date
     * @param pattern
     * @return
     */
    public static Date dateStart(Date date, String pattern) {
        if (date == null) {
            throw new java.lang.IllegalArgumentException("timestamp null illegal");
        }
        pattern = (pattern == null || pattern.equals(""))?PATTERN_STANDARD19H:pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateNew = sdf.format(date);
        Date d = new Date();
        try {
            d = sdf.parse(dateNew);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
    
    /**
     * 时间格式转换 yyyy-MM-dd 转化成yyyy-MM-dd 23:59:59
     * @param dateStr
     * @param minute
     * @return
     */
    public static Date dateEnd(Date dateStr,int minute) {
        Date afterDate = new Date(dateStr.getTime()+(1000*minute));
        return afterDate;
    }
    
    /**
     * 时间格式转换 yyyy-MM-dd 转化成yyyy-MM-dd 00:00:00
     * @param date
     * @param pattern
     * @return
     */
    public static String dateStartString(Date date, String pattern) {
        if (date == null) {
            throw new java.lang.IllegalArgumentException("timestamp null illegal");
        }
        pattern = (pattern == null || pattern.equals(""))?PATTERN_STANDARD19H:pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
    
    
    public static void main(String[] args) {
        Date now = new Date();
        SimpleDateFormat startFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String a1 = startFormatter.format(now);
        System.out.println(a1);
        SimpleDateFormat startFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
           Date date = startFormatter1.parse(a1);
           System.out.println(date);
           System.out.println(dateStartString(date, null));
           System.out.println(dateStartString(dateEnd(dateStart(date, null), 23*60*60+59*60+59), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
