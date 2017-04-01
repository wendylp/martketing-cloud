package cn.rongcapital.mkt.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.CampaignBody;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.CampaignActionSendSmsService;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SmsActivationCreateOrUpdateService;
import cn.rongcapital.mkt.vo.ActiveMqMessageVO;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;
import cn.rongcapital.mkt.vo.in.SmsTargetAudienceIn;

/**
 * Created by byf on 10/18/16.
 */

@Service
public class SmsActivationCreateOrUpdateServiceImpl implements SmsActivationCreateOrUpdateService{

	@Autowired
	private CampaignBodyDao campaignBodyDao;

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
	public static final Integer SMS_TASK_SEND_TYPE = 3;

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
			if (smsActivationCreateIn.getSmsTaskType() == SMS_TASK_TYPE_CAMPAIGN) { // 1:活动编排中的任务
				// 检查是否是事件类型的活动
				CampaignBody campaignBody = new CampaignBody();
				Byte nodeType = 0; // 第一个节点
				Byte itemType = 3; // 触发类型的活动
				campaignBody.setHeadId(smsActivationCreateIn.getCampaignHeadId()); // 活动ID
				campaignBody.setNodeType(nodeType);
				campaignBody.setItemType(itemType);
				List<CampaignBody> campaignBodyList = this.campaignBodyDao.selectList(campaignBody);
				boolean isEventTask = campaignBodyList != null && !campaignBodyList.isEmpty(); // 事件类型活动
				List<SmsTaskHead> SmsTaskHeadList = null;
				if (isEventTask) { // 事件类型活动
					smsTaskTrigger = 1;
					SmsTaskHead selectSmsTaskHeadTemp = new SmsTaskHead();
					selectSmsTaskHeadTemp.setCampaignHeadId(smsActivationCreateIn.getCampaignHeadId());
					SmsTaskHeadList = this.smsTaskHeadDao.selectListByCampaignHeadId(selectSmsTaskHeadTemp);
					boolean isExistSmsTask = SmsTaskHeadList != null && !SmsTaskHeadList.isEmpty() && SmsTaskHeadList.size() == 1; // 事件活动，有且只有一个短信任务
					if (isExistSmsTask) {
						insertSmsTaskHead = SmsTaskHeadList.get(0);
						insertSmsTaskHead.setSmsTaskStatus(SmsTaskStatusEnum.TASK_EXECUTING.getStatusCode()); // 更新短信任务状态为：执行中
						this.smsTaskHeadDao.updateById(insertSmsTaskHead);
					}
				}
			}

			if (insertSmsTaskHead == null) {

				insertSmsTaskHead = new SmsTaskHead();
				insertSmsTaskHead.setSmsTaskTrigger(smsTaskTrigger);

				if (smsActivationCreateIn.getSmsTaskType() == SMS_TASK_TYPE_CAMPAIGN) { // 1:活动编排中的任务
					SmsTaskHead insertSmsTaskHeadTemp = new SmsTaskHead();
					insertSmsTaskHeadTemp.setSmsTaskCode(smsActivationCreateIn.getSmsTaskCode()); // 根据sms_task_code查询
					int smsTaskBatch = smsTaskHeadDao.selectListCount(insertSmsTaskHeadTemp);
					smsTaskBatch = smsTaskBatch + 1;
					insertSmsTaskHead.setSmsTaskBatch(smsTaskBatch);
					insertSmsTaskHead.setSmsTaskName(smsActivationCreateIn.getTaskName() + "第" + smsTaskBatch + "批");
					insertSmsTaskHead.setSmsTaskStatus(SmsTaskStatusEnum.TASK_EXECUTING.getStatusCode()); // 设置短信任务状态：执行中
				} else {// 0:短信平台短信任务
					insertSmsTaskHead.setSmsTaskBatch(ApiConstant.INT_ZERO);
					insertSmsTaskHead.setSmsTaskName(smsActivationCreateIn.getTaskName());
					insertSmsTaskHead.setSmsTaskStatus(SmsTaskStatusEnum.TASK_UNSTART.getStatusCode()); // 短信任务状态：未启动
				}

				insertSmsTaskHead.setSmsTaskSignatureId(smsActivationCreateIn.getTaskSignatureId()); // 短信接口
				insertSmsTaskHead.setSmsTaskMaterialId(smsActivationCreateIn.getTaskMaterialId());
				insertSmsTaskHead.setSmsTaskMaterialContent(smsActivationCreateIn.getTaskMaterialContent());
				insertSmsTaskHead.setSmsTaskSendType(smsActivationCreateIn.getTaskSendType()); // 任务类型：营销、服务通知、验证码
				insertSmsTaskHead.setSmsTaskAppType(smsActivationCreateIn.getTaskAppType()); // 短信类型：0:固定短信,1:变量短信
				insertSmsTaskHead.setTotalCoverNum(ApiConstant.INT_ZERO);
				insertSmsTaskHead.setWaitingNum(ApiConstant.INT_ZERO);
				insertSmsTaskHead.setSendingSuccessNum(ApiConstant.INT_ZERO);
				insertSmsTaskHead.setSendingFailNum(ApiConstant.INT_ZERO);
				insertSmsTaskHead.setAudienceGenerateStatus(ApiConstant.INT_ONE);
				insertSmsTaskHead.setSmsTaskType(smsActivationCreateIn.getSmsTaskType().byteValue());
				insertSmsTaskHead.setSmsTaskCode(smsActivationCreateIn.getSmsTaskCode());
				insertSmsTaskHead.setCampaignHeadId(smsActivationCreateIn.getCampaignHeadId()); // 保存活动头id
				smsTaskHeadDao.insert(insertSmsTaskHead); // 短信头部
			}

			/**
			 * 活动编排发短信节点存储增量dataPartyId
			 */
			if (smsActivationCreateIn.getSmsTaskType() == SMS_TASK_TYPE_CAMPAIGN) {
				campaignActionSendSmsService.storeDataPartyIds(smsActivationCreateIn.getDataPartyIds(), insertSmsTaskHead.getId());
			}
			// 2获取task_head的Id,然后将相应得信息分条存储到body表中
			if (!CollectionUtils.isEmpty(smsActivationCreateIn.getSmsTargetAudienceInArrayList())) {
				for (SmsTargetAudienceIn smsTargetAudienceIn : smsActivationCreateIn.getSmsTargetAudienceInArrayList()) {
					SmsTaskBody insertSmsTaskBody = new SmsTaskBody();
					insertSmsTaskBody.setSmsTaskHeadId(insertSmsTaskHead.getId());
					if (smsActivationCreateIn.getSmsTaskType() == SMS_TASK_TYPE_CAMPAIGN) {
						insertSmsTaskBody.setTargetId(insertSmsTaskHead.getId());
						insertSmsTaskBody.setSendStatus(ApiConstant.SMS_TASK_PROCESS_STATUS_WRITING);
					} else {
						insertSmsTaskBody.setTargetId(smsTargetAudienceIn.getTargetAudienceId());
					}
					insertSmsTaskBody.setTargetName(smsTargetAudienceIn.getTargetAudienceName());
					insertSmsTaskBody.setTargetType(smsTargetAudienceIn.getTargetAudienceType());
					smsTaskBodyDao.insert(insertSmsTaskBody);
				}
			}

			// 3将head_id做为参数发送一个Message.
			ActiveMqMessageVO message = new ActiveMqMessageVO();
			message.setTaskName("生成具体短信消息任务");
			message.setServiceName(MQ_SMS_GENERATE_DETAIL_SERVICE);
			message.setMessage(String.valueOf(insertSmsTaskHead.getId()));
			mqTopicService.senderMessage(MQ_SMS_GENERATE_DETAIL_SERVICE, message);

			return baseOutput;
		}

		baseOutput.setCode(ApiErrorCode.BIZ_ERROR.getCode());
		baseOutput.setMsg(ApiErrorCode.BIZ_ERROR.getMsg());
		return baseOutput;
	}

	private void createSmsTask() {

	}

}
