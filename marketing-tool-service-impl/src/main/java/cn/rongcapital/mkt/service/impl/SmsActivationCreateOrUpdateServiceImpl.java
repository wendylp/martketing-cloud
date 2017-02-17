package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.CampaignActionSendSmsService;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SmsActivationCreateOrUpdateService;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;
import cn.rongcapital.mkt.vo.in.SmsTargetAudienceIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.jms.JMSException;

/**
 * Created by byf on 10/18/16.
 */

@Service
public class SmsActivationCreateOrUpdateServiceImpl implements SmsActivationCreateOrUpdateService{

    @Autowired
    private SmsTaskHeadDao smsTaskHeadDao;

    @Autowired
    private SmsTaskBodyDao smsTaskBodyDao;

    @Autowired
    private MQTopicService mqTopicService;
    
	@Autowired
	private CampaignActionSendSmsService campaignActionSendSmsService;

    private static final String MQ_SMS_GENERATE_DETAIL_SERVICE = "generateSmsDetailTask";
    
	public static final Integer SMS_TASK_TYPE_CAMPAIGN=1;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput createOrUpdateSmsActivation(SmsActivationCreateIn smsActivationCreateIn) throws JMSException {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        if(smsActivationCreateIn.getTaskId() == null){
            //1将task_head的信息存到head表中
            SmsTaskHead insertSmsTaskHead = new SmsTaskHead();
            
            if(smsActivationCreateIn.getSmsTaskType()==SMS_TASK_TYPE_CAMPAIGN){
            	SmsTaskHead insertSmsTaskHeadTemp = new SmsTaskHead();
            	insertSmsTaskHeadTemp.setSmsTaskCode(smsActivationCreateIn.getSmsTaskCode());
            	int smsTaskBatch = smsTaskHeadDao.selectListCount(insertSmsTaskHeadTemp);
            	smsTaskBatch= smsTaskBatch+1;
            	insertSmsTaskHead.setSmsTaskBatch(smsTaskBatch);
            	insertSmsTaskHead.setSmsTaskName(smsActivationCreateIn.getTaskName()+"第"+smsTaskBatch+"批");
            	insertSmsTaskHead.setSmsTaskStatus(SmsTaskStatusEnum.TASK_EXECUTING.getStatusCode());
            }else{
            	insertSmsTaskHead.setSmsTaskBatch(ApiConstant.INT_ZERO);
            	insertSmsTaskHead.setSmsTaskName(smsActivationCreateIn.getTaskName());
            	insertSmsTaskHead.setSmsTaskStatus(SmsTaskStatusEnum.TASK_UNSTART.getStatusCode());
            }           
            insertSmsTaskHead.setSmsTaskSignatureId(smsActivationCreateIn.getTaskSignatureId());
            insertSmsTaskHead.setSmsTaskMaterialId(smsActivationCreateIn.getTaskMaterialId());
            insertSmsTaskHead.setSmsTaskMaterialContent(smsActivationCreateIn.getTaskMaterialContent());
            
            insertSmsTaskHead.setSmsTaskSendType(smsActivationCreateIn.getTaskSendType());
            insertSmsTaskHead.setSmsTaskAppType(smsActivationCreateIn.getTaskAppType());
            insertSmsTaskHead.setTotalCoverNum(ApiConstant.INT_ZERO);
            insertSmsTaskHead.setWaitingNum(ApiConstant.INT_ZERO);
            insertSmsTaskHead.setSendingSuccessNum(ApiConstant.INT_ZERO);
            insertSmsTaskHead.setSendingFailNum(ApiConstant.INT_ZERO);
            insertSmsTaskHead.setAudienceGenerateStatus(ApiConstant.INT_ONE);
            insertSmsTaskHead.setSmsTaskType(smsActivationCreateIn.getSmsTaskType().byteValue());
            insertSmsTaskHead.setSmsTaskCode(smsActivationCreateIn.getSmsTaskCode());
            smsTaskHeadDao.insert(insertSmsTaskHead);
            /**
             * 活动编排发短信节点 存储增量dataPartyId
             */
            if(smsActivationCreateIn.getSmsTaskType()==SMS_TASK_TYPE_CAMPAIGN){
            	campaignActionSendSmsService.storeDataPartyIds(smsActivationCreateIn.getDataPartyIds(),insertSmsTaskHead.getId());
            }
            //2获取task_head的Id,然后将相应得信息分条存储到body表中
            if(!CollectionUtils.isEmpty(smsActivationCreateIn.getSmsTargetAudienceInArrayList())){
                for(SmsTargetAudienceIn smsTargetAudienceIn : smsActivationCreateIn.getSmsTargetAudienceInArrayList()){
                    SmsTaskBody insertSmsTaskBody = new SmsTaskBody();
                    insertSmsTaskBody.setSmsTaskHeadId(insertSmsTaskHead.getId());                   
                    if(smsActivationCreateIn.getSmsTaskType()==SMS_TASK_TYPE_CAMPAIGN){
                    	insertSmsTaskBody.setTargetId(insertSmsTaskHead.getId());
                    }else{
                    	insertSmsTaskBody.setTargetId(smsTargetAudienceIn.getTargetAudienceId());
                    }                    
                    insertSmsTaskBody.setTargetName(smsTargetAudienceIn.getTargetAudienceName());
                    insertSmsTaskBody.setTargetType(smsTargetAudienceIn.getTargetAudienceType());
                    smsTaskBodyDao.insert(insertSmsTaskBody);
                }
            }

            //3将head_id做为参数发送一个Message.
            ActiveMqMessageVO message = new ActiveMqMessageVO();
            message.setTaskName("生成具体短信消息任务");
            message.setServiceName(MQ_SMS_GENERATE_DETAIL_SERVICE);
            message.setMessage(String.valueOf(insertSmsTaskHead.getId()));
            mqTopicService.senderMessage(MQ_SMS_GENERATE_DETAIL_SERVICE,message);

            return baseOutput;
        }

        baseOutput.setCode(ApiErrorCode.BIZ_ERROR.getCode());
        baseOutput.setMsg(ApiErrorCode.BIZ_ERROR.getMsg());
        return baseOutput;
    }

}
