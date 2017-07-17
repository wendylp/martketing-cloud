package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.po.CampaignBody;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.CampaignActionSendSmsService;
import cn.rongcapital.mkt.service.SmsTaskHeadAndBodyPrepare;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;
import cn.rongcapital.mkt.vo.in.SmsTargetAudienceIn;

@Service
public class SmsTaskHeadAndBodyPrepareImpl implements SmsTaskHeadAndBodyPrepare{

	@Autowired
	private CampaignBodyDao campaignBodyDao;

	@Autowired
	private SmsTaskHeadDao smsTaskHeadDao;

	@Autowired
	private SmsTaskBodyDao smsTaskBodyDao;

	@Autowired
	private CampaignActionSendSmsService campaignActionSendSmsService;
	
	@Autowired
	private DataAuthService dataAuthService;	

	public static final Integer SMS_TASK_TYPE_CAMPAIGN = 1;
	public static final Integer SMS_TASK_SEND_TYPE = 3;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public SmsTaskHead smsTaskHeadAndBodyFullFill(SmsActivationCreateIn smsActivationCreateIn,
			SmsTaskHead insertSmsTaskHead, Integer smsTaskTrigger, boolean isEventTask) {
		if (smsActivationCreateIn.getSmsTaskType() == SMS_TASK_TYPE_CAMPAIGN) { // 1:活动编排中的任务
			// 检查是否是事件类型的活动
			CampaignBody campaignBody = new CampaignBody();
			Byte nodeType = 0; // 第一个节点
			Byte itemType = 3; // 触发类型的活动
			campaignBody.setHeadId(smsActivationCreateIn.getCampaignHeadId()); // 活动ID
			campaignBody.setNodeType(nodeType);
			campaignBody.setItemType(itemType);
			List<CampaignBody> campaignBodyList = this.campaignBodyDao.selectList(campaignBody);
			isEventTask = campaignBodyList != null && !campaignBodyList.isEmpty(); // 事件类型活动
			List<SmsTaskHead> SmsTaskHeadList = null;
			if (isEventTask) { // 事件类型活动
				smsTaskTrigger = 1;
				SmsTaskHead selectSmsTaskHeadTemp = new SmsTaskHead();
				selectSmsTaskHeadTemp.setCampaignHeadId(smsActivationCreateIn.getCampaignHeadId());
				selectSmsTaskHeadTemp.setSmsTaskCode(smsActivationCreateIn.getSmsTaskCode());
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
			dataAuthService.put(smsActivationCreateIn.getOrgId(), "sms_task_head", insertSmsTaskHead.getId()); //数据权限插入语句
			
			// 2:获取task_head的Id,然后将相应得信息分条存储到body表中
			if (!CollectionUtils.isEmpty(smsActivationCreateIn.getSmsTargetAudienceInArrayList())) {
				for (SmsTargetAudienceIn smsTargetAudienceIn : smsActivationCreateIn.getSmsTargetAudienceInArrayList()) {
					SmsTaskBody insertSmsTaskBody = new SmsTaskBody();
					insertSmsTaskBody.setSmsTaskHeadId(insertSmsTaskHead.getId());
					if (smsActivationCreateIn.getSmsTaskType() == SMS_TASK_TYPE_CAMPAIGN) {
						insertSmsTaskBody.setTargetId(insertSmsTaskHead.getId());
						// insertSmsTaskBody.setSendStatus(ApiConstant.SMS_TASK_PROCESS_STATUS_WRITING);
					} else {
						insertSmsTaskBody.setTargetId(smsTargetAudienceIn.getTargetAudienceId());
					}
					insertSmsTaskBody.setTargetName(smsTargetAudienceIn.getTargetAudienceName());
					insertSmsTaskBody.setTargetType(smsTargetAudienceIn.getTargetAudienceType());
					smsTaskBodyDao.insert(insertSmsTaskBody);
				}
			}
		}

		/**
		 * 活动编排发短信节点存储增量dataPartyId
		 */
		if (smsActivationCreateIn.getSmsTaskType() == SMS_TASK_TYPE_CAMPAIGN) {
			campaignActionSendSmsService.storeDataPartyIds(smsActivationCreateIn.getDataPartyIds(), insertSmsTaskHead.getId());
			if (isEventTask) {
				for (Integer dataPartyId : smsActivationCreateIn.getDataPartyIds()) {
					campaignActionSendSmsService.storeDataPartyId(dataPartyId, insertSmsTaskHead.getId());
				}
			}
		}
		return insertSmsTaskHead;
	
	}


}
