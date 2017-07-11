package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.CampaignActionSendSmsService;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SmsActivationCreateOrUpdateService;
import cn.rongcapital.mkt.service.SmsTaskHeadAndBodyPrepare;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;

/**
 * Created by byf on 10/18/16.
 */

@Service
public class SmsActivationCreateOrUpdateServiceImpl implements SmsActivationCreateOrUpdateService {

	@Autowired
	private MQTopicService mqTopicService;

	private static final String MQ_SMS_GENERATE_DETAIL_SERVICE = "generateSmsDetailTask";

	public static final Integer SMS_TASK_TYPE_CAMPAIGN = 1;
	public static final Integer SMS_TASK_SEND_TYPE = 3;

	@Autowired
	private SmsTaskHeadAndBodyPrepare smsTaskHeadAndBodyPrepare;
	
	/**
	 * @since 1.8
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput createOrUpdateSmsActivation(SmsActivationCreateIn smsActivationCreateIn) throws JMSException {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

		if (smsActivationCreateIn.getTaskId() == null) {
			SmsTaskHead insertSmsTaskHead = null;
			Integer smsTaskTrigger = 0; // 事件类型的活动
			boolean isEventTask = false;
			
			insertSmsTaskHead = smsTaskHeadAndBodyPrepare.smsTaskHeadAndBodyFullFill(smsActivationCreateIn,
					insertSmsTaskHead, smsTaskTrigger, isEventTask);
			
			// 3将head_id做为参数发送一个Message.
			ActiveMqMessageVO message = new ActiveMqMessageVO();
			message.setTaskName("生成具体短信消息任务");
			message.setServiceName(MQ_SMS_GENERATE_DETAIL_SERVICE);
			message.setMessage(String.valueOf(insertSmsTaskHead.getId()));
			mqTopicService.senderMessage(MQ_SMS_GENERATE_DETAIL_SERVICE, message);

			List<Object> smsTaskHeadList = new ArrayList<Object>();
			smsTaskHeadList.add(insertSmsTaskHead);
			baseOutput.setData(smsTaskHeadList);
			return baseOutput;
		}

		baseOutput.setCode(ApiErrorCode.BIZ_ERROR.getCode());
		baseOutput.setMsg(ApiErrorCode.BIZ_ERROR.getMsg());
		return baseOutput;
	}
}
