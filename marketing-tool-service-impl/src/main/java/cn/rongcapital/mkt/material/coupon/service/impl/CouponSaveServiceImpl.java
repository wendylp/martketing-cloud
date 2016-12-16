/*************************************************
 * @功能简述: CouponSaveService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: guozhenchao
 * @version: 1.0
 * @date: 2016/12/13
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponTypeEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.CouponSaveService;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CouponInfoIn;

import com.alibaba.fastjson.JSONObject;

@Service
public class CouponSaveServiceImpl implements CouponSaveService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MaterialCouponDao materialCouponDao;
    
    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;
    
    @Autowired
    private MQTopicService mqTopicService;
    
    public static final String PATTERN_STANDARD19H = "yyyy-MM-dd 00:00:00";

    private static final String MQ_CODE_SERVICE = "couponCodeSaveTaskImpl";
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput save(CouponInfoIn couponInfo, String user_token) {
        JSONObject json = new JSONObject();
        BaseOutput baseOutput =
                new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
                        null);
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
        Date endTimeNew = dateEnd(endTime, 23 * 60 * 60 + 59 * 60 + 59);
        Date now = new Date();
        json.put("rule", rule);
        json.put("source_code", SourceCode);
        json.put("stock_total", stock_total);
        json.put("user_token", user_token);
        if (id == null) {
            json.put("edit", false);
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
            json.put("coupon_id", coupon.getId());
        } else {
            json.put("edit", true);
            json.put("coupon_id", id);
            MaterialCoupon coupon = materialCouponDao.selectOneCoupon(id);
            if (coupon == null) {
                baseOutput.setCode(ApiErrorCode.DB_ERROR.getCode());
                baseOutput.setMsg(ApiErrorCode.DB_ERROR.getMsg());
                return baseOutput;
            }
            // 只有未投放才能编辑
            if (!MaterialCouponStatusEnum.UNUSED.getCode().equals(coupon.getCouponStatus())) {
                baseOutput.setCode(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_UPDATE_ERROR.getCode());
                baseOutput.setMsg(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_UPDATE_ERROR.getMsg());
                return baseOutput;
            }

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
            materialCouponDao.updateById(coupon);
        }

        try {
            ActiveMqMessageVO message = new ActiveMqMessageVO();
            message.setTaskName("优惠码生成");
            message.setServiceName(MQ_CODE_SERVICE);
            message.setMessage(json.toString());
            mqTopicService.senderMessage(MQ_CODE_SERVICE, message);
        } catch (JMSException e) {
            baseOutput.setCode(ApiErrorCode.SYSTEM_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.SYSTEM_ERROR.getMsg());
            return baseOutput;
        }

        return baseOutput;
    }


    
    
    /**
     * 生成码
     * @param elements
     * @param lengthOfList
     * @return
     */
    public List<String> getAllLists(String[] elements, int lengthOfList, int size) {
        
        List<String> codes = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        while(set.size() < size){
            Random rand = new Random();
            String str = "";
            int index;
            boolean[] flags = new boolean[elements.length];// 默认为false
            for (int j = 0; j < lengthOfList; j++) {
                do {
                    index = rand.nextInt(elements.length);
                } while (flags[index] == true);
                String c = elements[index];
                str += c;
                flags[index] = true;
            }
            if (!set.contains(str)) {
                set.add(str);
            }
        }
        codes.addAll(set);
        return codes;
    }
    
    
    /**
     * 时间格式转换 yyyy-MM-dd 转化成yyyy-MM-dd 00:00:00
     * @param date
     * @param pattern
     * @return
     */
    private Date dateStart(Date date, String pattern) {
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
    private Date dateEnd(Date dateStr,int minute) {
        Date afterDate = new Date(dateStart(dateStr, null).getTime()+(1000*minute));
        return afterDate;
    }
  
}
