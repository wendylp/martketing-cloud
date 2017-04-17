/*************************************************
 * @功能简述: SyncETLMaterialCouponDataService实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: 单璟琦
 * @version: 0.0.1
 * @date: 2017/4/17
 * @复审人:

 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.common.enums.CouponCodeType;
import cn.rongcapital.mkt.common.enums.MaterialCouponChannelCodeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponReadyStatusType;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponTypeEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.SyncETLMaterialCouponDataService;
import cn.rongcapital.mkt.po.mongodb.Coupon;

@Service
public class SyncETLMaterialCouponDataServiceImpl implements SyncETLMaterialCouponDataService{
	private static final Logger logger = LoggerFactory.getLogger(SyncETLMaterialCouponDataServiceImpl.class);
    
	@Autowired
	private MongoTemplate mongoTemplate;
	
    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;
    
    @Autowired
    private MaterialCouponDao materialCouponDao;
    
    public int sync(){
    	
		Query query = Query.query(Criteria.where("SyncFlag").exists(false));  
	    List<Coupon> nodeAudienceList = mongoTemplate.find(query, Coupon.class);
	    
	    JSONObject json = new JSONObject();
        MaterialCoupon coupon = new MaterialCoupon();
        List<MaterialCouponCode> list = null;
        MaterialCouponCode code = null;
        boolean procced ;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        int result = 0;
        
        if(!CollectionUtils.isEmpty(nodeAudienceList)){
		    for (Coupon temp : nodeAudienceList) {
		    	procced = true;
	            try {
	            	procced = vaildateMandary(temp);
	            	coupon.setTitle(temp.getCouponName());
	                coupon.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
	                json.put("coupon_code", temp.getCouponName());
	                json.put("couponid", temp.getCouponId());
	                json.put("StockRest", "0");
	                json.put("StockTotal", "0");
	                coupon.setRule(json.toJSONString());
	                //TODO coupon.setStockRest(0); coupon.setStockTotal(0);
	                coupon.setStockRest(0);
	                coupon.setStockTotal(0);
	                coupon.setAmount(temp.getCouponValue() !=null ? BigDecimal.valueOf(temp.getCouponValue()) : BigDecimal.valueOf(0));
	                coupon.setSourceCode(CouponCodeType.COMMON.getCode());
	                coupon.setChannelCode(MaterialCouponChannelCodeEnum.SMS.getCode());
	                coupon.setCouponStatus(MaterialCouponStatusEnum.UNUSED.getCode());
	                coupon.setReadyStatus(MaterialCouponReadyStatusType.UNREADY.getCode());
	                coupon.setCreateTime( new Date());
	                coupon.setUpdateTime( new Date());
					coupon.setStartTime(sdf.parse(temp.getBeginTime()) );
					coupon.setEndTime(sdf.parse(temp.getEndTime()));
				} catch (ParseException e) {
					logger.debug("Convert beginTime{} , endTime{} failed",temp.getBeginTime(),temp.getEndTime());
					logger.debug(e.getMessage());
					procced = false;
				} catch (NullPointerException e){
					logger.debug(e.getMessage());
					procced = false;
				}
	            
	            if(procced){
	            	materialCouponDao.insert(coupon);
	                
	                String unCode = json.getString("coupon_code");
	                list = new ArrayList<MaterialCouponCode>(); 
	                //TODO 2 stock_total
	                for(int i = 0; i< 2; i ++){
	                	code = new MaterialCouponCode();
	                    code.setCode(unCode);
	                    code.setCouponId(coupon.getId());
	                    code.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.UNRELEASED.getCode());
	                    code.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode());
	                    code.setStatus((byte) 0);
	                    code.setCreateTime(new Date());
	                    code.setUpdateTime(new Date());
	                    list.add(code);
	                }
	                
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
	                //标记mongo中已同步到MySQL的优惠券
	                //Query query2 = Query.query(Criteria.where("couponid").is(temp.getCouponId()).and("couponstate").is("1"));
	                //Update update = new Update().update("couponstate", 2).update("AA", 0);
					mongoTemplate.updateFirst(
							new Query(new Criteria("couponid").is(temp.getCouponId()).and("SyncFlag").exists(false)),
							new Update().update("SyncFlag", "Y"), Coupon.class);
	            }else{
	            	result ++;
	            	logger.info("sync coupon{} from mongo to mysql failed", temp);
	            }
			}
        }else{
        	logger.info("This time doesnt have coupon data been synchronized from mongo to mysql ");
        }
	    
	    return result;
    }
    
    
    private boolean vaildateMandary(Coupon con){
    	boolean flag = true;
    	
    	if(StringUtils.isBlank(con.getCouponName())){ // 数据库必填字段
    		flag = false;
    	}
    	if(StringUtils.isBlank(con.getBeginTime())){
    		flag = false;
    	}
    	if(StringUtils.isBlank(con.getEndTime())){
    		flag = false;
    	}
    	if(con.getCouponId() == null){ //核销用
    		flag = false;
    	}
    	
    	return flag;
    }
    
}
