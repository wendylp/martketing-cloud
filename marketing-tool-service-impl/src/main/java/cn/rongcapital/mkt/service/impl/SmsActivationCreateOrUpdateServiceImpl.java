package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.SmsActivationCreateOrUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;
import cn.rongcapital.mkt.vo.in.SmsTargetAudienceIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Created by byf on 10/18/16.
 */

@Service
public class SmsActivationCreateOrUpdateServiceImpl implements SmsActivationCreateOrUpdateService{

    @Autowired
    private SmsTaskHeadDao smsTaskHeadDao;

    @Autowired
    private SmsTaskBodyDao smsTaskBodyDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput createOrUpdateSmsActivation(SmsActivationCreateIn smsActivationCreateIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        if(smsActivationCreateIn.getTaskId() == null){
            //Todo:1将task_head的信息存到head表中
            SmsTaskHead insertSmsTaskHead = new SmsTaskHead();
            insertSmsTaskHead.setSmsTaskName(smsActivationCreateIn.getTaskName());
            insertSmsTaskHead.setSmsTaskSignatureId(smsActivationCreateIn.getTaskSignatureId());
            insertSmsTaskHead.setSmsTaskTemplateId(smsActivationCreateIn.getTaskTemplateId());
            insertSmsTaskHead.setSmsTaskTemplateContent(smsActivationCreateIn.getTaskTemplateContent());
            insertSmsTaskHead.setSmsTaskAppType(smsActivationCreateIn.getTaskSendType());
            insertSmsTaskHead.setSmsTaskAppType(smsActivationCreateIn.getTaskAppType());
            smsTaskHeadDao.insert(insertSmsTaskHead);
            //Todo:2获取task_head的Id,然后将相应得信息分条存储到body表中
            if(!CollectionUtils.isEmpty(smsActivationCreateIn.getSmsTargetAudienceInArrayList())){
                for(SmsTargetAudienceIn smsTargetAudienceIn : smsActivationCreateIn.getSmsTargetAudienceInArrayList()){
                    SmsTaskBody insertSmsTaskBody = new SmsTaskBody();
                    insertSmsTaskBody.setSmsTaskHeadId(insertSmsTaskHead.getId());
                    insertSmsTaskBody.setTargetId(smsTargetAudienceIn.getTargetAudienceId());
                    insertSmsTaskBody.setTargetName(smsTargetAudienceIn.getTargetAudienceName());
                    insertSmsTaskBody.setTargetType(smsTargetAudienceIn.getTargetAudienceType());
                    smsTaskBodyDao.insert(insertSmsTaskBody);
                }
            }
            //Todo:3将head_id做为参数发送一个Message.

        }


        return baseOutput;
    }

}
