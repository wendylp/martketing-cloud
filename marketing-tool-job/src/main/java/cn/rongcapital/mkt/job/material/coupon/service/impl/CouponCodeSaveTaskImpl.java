package cn.rongcapital.mkt.job.material.coupon.service.impl;

import org.eclipse.jetty.util.log.Log;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.job.service.base.TaskService;
@Service
public class CouponCodeSaveTaskImpl implements TaskService{

    
    @Override
    public void task(Integer taskId) {
        Log.info("优惠码保存 -------------start");
        
        Log.info("优惠码保存 -------------end");
    }

}
