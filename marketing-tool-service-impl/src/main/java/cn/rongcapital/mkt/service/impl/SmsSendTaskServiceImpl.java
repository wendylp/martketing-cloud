package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.bbx.service.BbxCouponCodeAddService;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.common.enums.SmsDetailSendStateEnum;
import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsMaterialMaterielMapDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailStateDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeStatusUpdateService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponStatusUpdateService;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCodeStatusUpdateVO;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponStatusUpdateVO;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.po.SmsMaterialMaterielMap;
import cn.rongcapital.mkt.po.SmsTaskDetail;
import cn.rongcapital.mkt.po.SmsTaskDetailState;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.impl.vo.Sms;
import cn.rongcapital.mkt.service.impl.vo.SmsApi;
import cn.rongcapital.mkt.service.impl.vo.SmsStatus;
import cn.rongcapital.mkt.service.impl.vo.SmsStatusResponse;

@Service
public class SmsSendTaskServiceImpl implements TaskService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${sms.batch.count}")
	private Integer SMS_SEND_BACTH_COUNT;
	private final Integer SMS_MATERIEL_TYPE = 0;

	private final static Byte SMS_TYPE_DYNAMICS = 1;

	@Autowired
	private SmsTaskHeadDao smsTaskHeadDao;

	@Autowired
	private SmsTaskDetailDao smsTaskDetailDao;

	@Autowired
	private SmsTaskDetailStateDao smsTaskDetailStateDao;

	@Autowired
	private SmsMaterialMaterielMapDao smsMaterialMaterielMapDao;

	@Autowired
	private MaterialCouponStatusUpdateService materialCouponStatusUpdateService;

	@Autowired
	private MaterialCouponCodeStatusUpdateService materialCouponCodeStatusUpdateService;

	@Autowired
	private SmsMaterialDao smsMaterialDao;

	@Autowired
	private BbxCouponCodeAddService bbxCouponCodeAddService;

	private ResteasyClient client = new ResteasyClientBuilder()//
			.connectionPoolSize(1000)//
			.maxPooledPerRoute(1000)//
			.socketTimeout(30000, TimeUnit.MILLISECONDS).build();

	@Value("${sms.url.service}")
	private String smsUrlService;

	private SmsApi smsApi;

	@Override
	public void task(Integer taskId) {

	}

	@Override
	public void task(String jsonMessage) {
		if (this.smsApi == null) {
			ResteasyWebTarget target = client.target(smsUrlService);
			this.smsApi = target.proxy(SmsApi.class);
		}

		logger.info("mq task coming, id is {}", jsonMessage);
		// 根据任务ID查询sms_task_head表 判断 sms_task_status 是否是执行中状态
		Long taskId = Long.valueOf(jsonMessage);
		SmsTaskHead smsHead = new SmsTaskHead();
		smsHead.setSmsTaskStatus(SmsTaskStatusEnum.TASK_EXECUTING.getStatusCode());// 短信任务执行中
		smsHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);// 短信任务有效性：未删除
		smsHead.setId(taskId);
		try {
			List<SmsTaskHead> smsHeadList = smsTaskHeadDao.selectList(smsHead);
			if (CollectionUtils.isEmpty(smsHeadList)) {
				logger.warn("task state is not sending(2) or No corresponding task ,taskId：{}", jsonMessage);
				return;
			}
			smsHead = smsHeadList.get(0);
			Integer sms_task_trigger = smsHead.getSmsTaskTrigger(); // 发送类型：0多条，1单条

			// 回写优惠券的状态，改为投放中---v1.6
			MaterialCouponStatusUpdateVO StatusUpdateVO = this.initMaterialCoupon(smsHead, MaterialCouponStatusEnum.RELEASING);
			if (StatusUpdateVO != null) {
				materialCouponStatusUpdateService.updateMaterialCouponStatus(StatusUpdateVO);
			}

			// 根据任务ID查询sms_task_detail表获取要发送的短信集合
			SmsTaskDetail smsDetail = new SmsTaskDetail();
			smsDetail.setSmsTaskHeadId(taskId);
			smsDetail.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			smsDetail.setPageSize(null);
			smsDetail.setStartIndex(null);
			smsDetail.setSendStatus(ApiConstant.SMS_TASK_PROCESS_STATUS_WRITING);
			List<SmsTaskDetail> smsDetailList = smsTaskDetailDao.selectList(smsDetail);
			if (CollectionUtils.isEmpty(smsDetailList)) {
				logger.warn("no send message,taskId is: {}", jsonMessage);
				// return;
			}

			Integer smsCount = smsDetailList.size();
			logger.info("sms count is {}", smsCount);

			Integer count = 0;

			Map<Long, String> SmsBatchMap = new LinkedHashMap<Long, String>();
			List<Sms> smsList = new ArrayList<Sms>();
			SmsStatusResponse response = null;
			for (SmsTaskDetail detail : smsDetailList) {

				detail.setSendStatus(ApiConstant.SMS_TASK_PROCESS_STATUS_DONE);
				smsTaskDetailDao.updateById(detail); // 更新发送状态为完成

				Long id = detail.getId();
				String receiveMobile = detail.getReceiveMobile();
				String sendMessage = detail.getSendMessage();

				smsList.add(new Sms(receiveMobile, sendMessage));
				// 对短短信进行封装
				count++;
				// logger.info("receive_mobile is {}, send_message is {} id is {}", receiveMobile, sendMessage, id);

				SmsBatchMap.put(id, receiveMobile);

				if (count >= SMS_SEND_BACTH_COUNT) {
					// 调用发送API接口（批量）
					try {
						response = this.smsApi.sendMultSms(smsList);
					} catch (Exception e) {
						logger.error("短信服务器连接失败", e);
						response = new SmsStatusResponse("-1", "短信服务器连接失败");
					}

					// Map<Long, Double> sendSmsResult = SmsSendUtilByIncake.sendSms(SmsBatchMap);
					// 统计一批短信的成功和失败的个数,根据短信API判断状态回写sms_task_detail_state表和head表
					updateSmsDetailState(response, SmsBatchMap, smsHead);
					// 修改当前任务这一批短信的成功和失败个数
					smsTaskHeadDao.updateById(smsHead);
					count = 0;
					SmsBatchMap.clear();
					continue;
				}
			}
			// Map<String, SmsResponse> rs = null;
			if (sms_task_trigger == 1 && smsList.size() == 1) {// 事件类型
				try {
					response = this.smsApi.sendSms(smsList.get(0));
				} catch (Exception e) {
					logger.error("短信服务器连接失败", e);
					response = new SmsStatusResponse("-1", "短信服务器连接失败");
				}
			} else {
				try {
					response = this.smsApi.sendMultSms(smsList);
				} catch (Exception e) {
					logger.error("短信服务器连接失败", e);
					response = new SmsStatusResponse("-1", "短信服务器连接失败");
				}
			}

			logger.info("短信返回状态码：{}，消息内容：{}，response：{} ", response.getErrorCode(), response.getErrorMsg(), response);

			// 处理不满一批的短信
			updateSmsDetailState(response, SmsBatchMap, smsHead);
			// 最后把任务状态改为已完成,把等待的个数置为0
			if (sms_task_trigger == 0) { // 非事件类型的短信
				smsHead.setSmsTaskStatus(SmsTaskStatusEnum.TASK_FINISH.getStatusCode()); // 短信发送状态：已结束
				smsHead.setWaitingNum(0);
			}

			smsTaskHeadDao.updateById(smsHead);

			// 回写优惠券的状态，改为已投放---v1.6
			StatusUpdateVO = this.initMaterialCoupon(smsHead, MaterialCouponStatusEnum.RELEASED);
			if (StatusUpdateVO != null) {
				materialCouponStatusUpdateService.updateMaterialCouponStatus(StatusUpdateVO);
			}

		} catch (Exception e) {
			logger.error("exception {}" + e.getMessage());
			e.printStackTrace();
		}

	}

	public void updateSmsDetailState(SmsStatusResponse response, Map<Long, String> SmsBatchMap, SmsTaskHead smsHead) {
		Map<String, SmsStatus> data = this.listConvertMap(response.getSmsList());

		SmsTaskDetailState smsTaskDetailState = null;
		List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<>(); // 优惠券状态
		MaterialCouponCodeStatusUpdateVO materialCouponCodeStatusUpdateVO = null;
		int successCount = 0;
		int failCount = 0;

		boolean isSuccess = "200".equals(response.getErrorCode()); // 本次推送短信请求是否成功
		for (Entry<Long, String> entry : SmsBatchMap.entrySet()) {
			Long taskDetailId = entry.getKey();

			// 根据短信优惠码回写状态
			SmsTaskDetail detail = new SmsTaskDetail();
			detail.setId(taskDetailId);
			List<SmsTaskDetail> SmsTaskDetailList = smsTaskDetailDao.selectList(detail);
			detail = SmsTaskDetailList.get(0);

			double gatewayState = -1;
			if (isSuccess) {
				SmsStatus smsStatus = data.get(entry.getValue()); // 本条短信返回状态
				if (smsStatus != null) {
					gatewayState = Double.parseDouble(smsStatus.getErrorCode());
				}
			}

			smsTaskDetailState = new SmsTaskDetailState();
			smsTaskDetailState.setSmsTaskDetailId(taskDetailId);
			if (gatewayState > 0) {
				// 根据api大于0代表发送成功
				successCount++;
				smsTaskDetailState.setSmsTaskSendStatus(SmsDetailSendStateEnum.SMS_DETAIL_SEND_SUCCESS.getStateCode());
				materialCouponCodeStatusUpdateVO = this.initMaterialCouponCode(detail, MaterialCouponCodeReleaseStatusEnum.RECEIVED);
			} else {
				// 其他情况则代表发送失败
				failCount++;
				smsTaskDetailState.setSmsTaskSendStatus(SmsDetailSendStateEnum.SMS_DETAIL_SEND_FAILURE.getStateCode());
				materialCouponCodeStatusUpdateVO = this.initMaterialCouponCode(detail, MaterialCouponCodeReleaseStatusEnum.UNRECEIVED);
			}
			smsTaskDetailStateDao.updateDetailState(smsTaskDetailState);
			voList.add(materialCouponCodeStatusUpdateVO);
		}

		// 只有变量短信回写状态
		List<Integer> smsMaterialIdLists = new ArrayList<Integer>();
		smsMaterialIdLists.add(smsHead.getSmsTaskMaterialId().intValue());
		List<SmsMaterial> smsMaterialLists = smsMaterialDao.selectListByIdList(smsMaterialIdLists);
		if (CollectionUtils.isNotEmpty(smsMaterialLists) && SMS_TYPE_DYNAMICS.equals(smsMaterialLists.get(0).getSmsType())) {
			// 修改一批优惠码的状态---v1.6
			materialCouponCodeStatusUpdateService.updateMaterialCouponCodeStatus(voList);
		}
		// 计算每批的成功和失败的个数
		Integer smsSuccessCount = smsHead.getSendingSuccessNum();
		Integer smsFailCount = smsHead.getSendingFailNum();

		if (smsSuccessCount == null || smsSuccessCount == 0) {
			smsHead.setSendingSuccessNum(successCount);
		} else {
			smsHead.setSendingSuccessNum(smsSuccessCount + successCount);
		}
		if (smsFailCount == null || smsFailCount == 0) {
			smsHead.setSendingFailNum(failCount);
		} else {
			smsHead.setSendingFailNum(smsFailCount + failCount);
		}
		// 每批都更新成功失败以及等待的个数
		Integer smsCoverNum = smsHead.getTotalCoverNum();
		Integer success = smsHead.getSendingSuccessNum();
		Integer fail = smsHead.getSendingFailNum();
		smsHead.setWaitingNum(smsCoverNum - (success + fail));
	}

	private MaterialCouponStatusUpdateVO initMaterialCoupon(SmsTaskHead smsHead, MaterialCouponStatusEnum status) {
		Long smsMaterialId = smsHead.getSmsTaskMaterialId();
		SmsMaterialMaterielMap materielMap = new SmsMaterialMaterielMap();
		materielMap.setSmsMaterialId(smsMaterialId);
		materielMap.setSmsMaterielType(SMS_MATERIEL_TYPE);// 优惠券
		List<SmsMaterialMaterielMap> materielMapList = smsMaterialMaterielMapDao.selectList(materielMap);
		if (CollectionUtils.isEmpty(materielMapList)) {
			logger.info("materielMapList is null!");
			return null;
		}

		materielMap = materielMapList.get(0);
		MaterialCouponStatusUpdateVO materialCouponStatusUpdatevo = new MaterialCouponStatusUpdateVO();
		materialCouponStatusUpdatevo.setId(materielMap.getSmsMaterielId());
		materialCouponStatusUpdatevo.setTaskId(smsHead.getId());
		materialCouponStatusUpdatevo.setTaskName(smsHead.getSmsTaskName());
		materialCouponStatusUpdatevo.setStatus(status.getCode());
		logger.info("smsMaterielId is {},taskId is {}, taskName is {}, status is {}", materielMap.getSmsMaterielId(), smsHead.getId(),
				smsHead.getSmsTaskName(), status.getCode());
		return materialCouponStatusUpdatevo;
	}

	private MaterialCouponCodeStatusUpdateVO initMaterialCouponCode(SmsTaskDetail detail, MaterialCouponCodeReleaseStatusEnum status) {
		MaterialCouponCodeStatusUpdateVO materialCouponCodevo = new MaterialCouponCodeStatusUpdateVO();
		materialCouponCodevo.setId(detail.getMaterielCouponCodeId());
		materialCouponCodevo.setUser(detail.getReceiveMobile());
		materialCouponCodevo.setStatus(status.getCode());
		logger.info("couponCodeId is {}, mobile is {}, ststus is {}", detail.getMaterielCouponCodeId(), detail.getReceiveMobile(), status.getCode());
		return materialCouponCodevo;
	}

	private Map<String, SmsStatus> listConvertMap(List<SmsStatus> list) {
		Map<String, SmsStatus> map = new HashMap<String, SmsStatus>();
		if (list != null && !list.isEmpty()) {
			for (SmsStatus cur : list) {
				map.put(cur.getReceive(), cur);
			}
		}
		return map;
	}
}
