package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsMaterialMaterielMapDao;
import cn.rongcapital.mkt.dao.SmsMaterialVariableMapDao;
import cn.rongcapital.mkt.dao.SmsSignatureDao;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailStateDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.dao.SmsTaskTargetAudienceCacheDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.factory.CalcSmsTargetAudienceStrateyFactory;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.material.coupon.service.CouponCodeListService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeStatusUpdateService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponEditDetailService;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SendSmsPrepare;
import cn.rongcapital.mkt.service.SmsSyncCouponService;

/**
 * Created by byf on 10/18/16.
 */
@Service
public class GenerateSmsDetailTask implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private  SendSmsPrepare sendSmsPrepare;
    
	@Autowired
	private SmsSyncCouponService smsSyncCouponService;

    @Autowired
    private MQTopicService mqTopicService;

    @Override
    public void task(Integer taskId) {

    }

    @Override
    public void task(String taskHeadIdStr) {
       
        SmsTaskHead currentTaskHead = sendSmsPrepare.getSmSTaskHead(taskHeadIdStr);
        //4检测TaskHead的发送状态
		if (currentTaskHead!=null && currentTaskHead.getSmsTaskStatus() == SmsTaskStatusEnum.TASK_EXECUTING.getStatusCode()) {
			 mqTopicService.sendSmsByTaskId(taskHeadIdStr);
			 ////////////////////////////去掉同步优惠券流程start @since 1.10.0
//			logger.info("调用SmsSendTaskServiceImpl发送短信， 短信HEAD id {}", currentTaskHead.getId());
//			smsSyncCouponService.beforeProcessSmsStatus(taskHeadIdStr); // @since 1.9.0
            ////////////////////////////去掉同步优惠券流程end @since 1.10.0
        }
    }



}
