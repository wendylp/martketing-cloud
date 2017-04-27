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
import org.springframework.beans.factory.annotation.Value;
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
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.SyncETLMaterialCouponDataService;
import cn.rongcapital.mkt.po.mongodb.Coupon;

@Service
public class SyncETLMaterialCouponDataServiceImpl implements SyncETLMaterialCouponDataService ,TaskService{
	private static final Logger logger = LoggerFactory.getLogger(SyncETLMaterialCouponDataServiceImpl.class);
    
	@Autowired
	private MongoTemplate mongoTemplate;
	
    @Autowired
    private MaterialCouponDao materialCouponDao;
    
	@Override
	public void task(Integer taskId) {
		this.sync();
	}
    
    public int sync(){
    	
		Query query = Query.query(Criteria.where("SyncFlag").exists(false));  
	    List<Coupon> nodeAudienceList = mongoTemplate.find(query, Coupon.class);
	    
	    JSONObject json = new JSONObject();
        MaterialCoupon coupon = new MaterialCoupon();
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
	                coupon.setStockRest(0); //贝贝熊逻辑为 页面弹出框输入券码数量。
	                coupon.setStockTotal(0);//贝贝熊逻辑为 页面弹出框输入券码数量。
	                coupon.setAmount(temp.getCouponValue() !=null ? BigDecimal.valueOf(temp.getCouponValue()) : BigDecimal.valueOf(0));
	                coupon.setSourceCode(CouponCodeType.COMMON.getCode());
	                coupon.setChannelCode(MaterialCouponChannelCodeEnum.SMS.getCode());
	                coupon.setCouponStatus(MaterialCouponStatusEnum.UNUSED.getCode());
	                coupon.setReadyStatus(MaterialCouponReadyStatusType.READY.getCode()); // 按照之前逻辑，码没有生成应该为 unready，但是unready不能修改优惠券码数量，所以这里做单独适配，为ready
	                coupon.setCreateTime( new Date());
	                coupon.setUpdateTime( new Date());
					coupon.setStartTime(temp.getBeginTime());
					coupon.setEndTime(temp.getEndTime());
				} catch (NullPointerException e){
					logger.debug(e.getMessage());
					procced = false;
				}
	            
	            if(procced){
	            	materialCouponDao.insert(coupon);
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
    	if(con.getBeginTime()== null){
    		flag = false;
    	}
    	if(con.getEndTime()== null){
    		flag = false;
    	}
    	if(con.getCouponId() == null){ //核销用
    		flag = false;
    	}
    	
    	return flag;
    }

}
