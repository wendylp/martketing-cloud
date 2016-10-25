package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.SmsDetailSendStateEnum;
import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailStateDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.SmsTaskDetail;
import cn.rongcapital.mkt.po.SmsTaskDetailState;
import cn.rongcapital.mkt.po.SmsTaskHead;
/*
 * 大于0	提交成功，该数字为本批次的任务ID，提交成功后请自行保存发送记录。
-1	余额不足
-2	帐号或密码错误
-3	连接服务商失败
-4	超时
-5	其他错误，一般为网络问题，IP受限等
-6	短信内容为空
-7	目标号码为空
-8	用户通道设置不对，需要设置三个通道
-9	捕获未知异常
-10	超过最大定时时间限制
-11	目标号码在黑名单里
-13	没有权限使用该网关
*/
@Service
public class SmsSendTaskServiceImpl implements TaskService{
	 private Logger logger = LoggerFactory.getLogger(getClass());
	 private Integer SMS_SEND_BACTH_COUNT = 500;
	@Autowired
	private SmsTaskHeadDao smsTaskHeadDao;
	
	@Autowired
	private SmsTaskDetailDao smsTaskDetailDao;

	@Autowired
	private SmsTaskDetailStateDao smsTaskDetailStateDao;
	
	@Override
	public void task(Integer taskId) {}

	@Override
	public void task(String jsonMessage) {
		logger.info("mq task coming, id is {}", jsonMessage);
		//根据任务ID查询sms_task_head表 判断 sms_task_status 是否是执行中状态
		Long taskId = Long.valueOf(jsonMessage);
		SmsTaskHead smsHead = new SmsTaskHead();
		smsHead.setSmsTaskStatus(SmsTaskStatusEnum.TASK_EXECUTING.getStatusCode());//执行中
		smsHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);//未删除
		smsHead.setId(taskId);
		try {
			List<SmsTaskHead> smsHeadList = smsTaskHeadDao.selectList(smsHead);
			if(CollectionUtils.isEmpty(smsHeadList)){
				logger.warn("task state is not sending(2) or No corresponding task ,taskId：{}", jsonMessage);
				return;
			}
			smsHead = smsHeadList.get(0);
			//根据任务ID查询sms_task_detail表获取要发送的短信集合
			SmsTaskDetail smsDetail = new SmsTaskDetail();
			smsDetail.setSmsTaskHeadId(taskId);
			smsDetail.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			smsDetail.setPageSize(null);
			smsDetail.setStartIndex(null);
			List<SmsTaskDetail> smsDetailList = smsTaskDetailDao.selectList(smsDetail);
			if(CollectionUtils.isEmpty(smsDetailList)){
				logger.warn("no send message,taskId is: {}" , jsonMessage);
				return;
			}
			
			Integer smsCount = smsDetailList.size();
			logger.info("sms count is {}" , smsCount);
			
			Integer count = 0;
			
			Map<Long, String[]> SmsBatchMap = new HashMap<>();
			for(SmsTaskDetail detail : smsDetailList) {
				//对短短信进行封装
				count++;
				Long id = detail.getId();
				String receiveMobile = detail.getReceiveMobile();
				String sendMessage = detail.getSendMessage();
				//logger.info("receive_mobile is {}, send_message is {} id is {}", receiveMobile, sendMessage, id);
				String[] sms = new String[2];
				sms[0] = receiveMobile;
				sms[1] = sendMessage;
				SmsBatchMap.put(id, sms);
				
				if(count >= SMS_SEND_BACTH_COUNT) {
					//调用发送API接口（批量）
					Map<Long, Integer> sendSmsResult = sendSmsBatchApi(SmsBatchMap);
					//统计一批短信的成功和失败的个数,根据短信API判断状态回写sms_task_detail_state表和head表
					updateSmsDetailState(sendSmsResult, smsHead);
					//修改当前任务这一批短信的成功和失败个数
					smsTaskHeadDao.updateById(smsHead);
					count = 0;
					SmsBatchMap.clear();
					continue;
				}
			}
			//处理不满一批的短信
			updateSmsDetailState(sendSmsBatchApi(SmsBatchMap), smsHead);
			//最后把任务状态改为已完成,把等待的个数置为0
			smsHead.setSmsTaskStatus(SmsTaskStatusEnum.TASK_FINISH.getStatusCode());
			smsHead.setWaitingNum(0);
			smsTaskHeadDao.updateById(smsHead);
		} catch (Exception e) {
			logger.error("exception {}" + e.getMessage());
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 批量发送短信并返回网关状态
	 * @param SmsBatchMap
	 * @return Map<Long, Integer> 返回一个任务id， 和 短信调用网关的状态
	 */
	public  Map<Long, Integer> sendSmsBatchApi(Map<Long, String[]> SmsBatchMap){
		Map<Long, Integer> sendResultMap = new HashMap<>();
		for(Entry<Long, String[]> entry : SmsBatchMap.entrySet()){
			Long id = entry.getKey();
			String[] sms = entry.getValue();
			//logger.info("phone is {}", sms[0]);
			//logger.info("message is {}", sms[1]);
			int n = (int)(Math.random()*100);
			
			if(n%2 >0){
				sendResultMap.put(id, (int)(Math.random()*-13));
			}else{
				sendResultMap.put(id, 1);
			}
		}
		return sendResultMap;
		
	}
	
	public void updateSmsDetailState(Map<Long, Integer> sendSmsResult, SmsTaskHead smsHead){
		SmsTaskDetailState  smsTaskDetailState = null;
		int successCount = 0;
		int failCount = 0;
		for(Entry<Long, Integer> entry : sendSmsResult.entrySet()) {
			Long taskDetailId = entry.getKey();
			Integer gatewayState = entry.getValue();
			smsTaskDetailState = new SmsTaskDetailState();
			smsTaskDetailState.setSmsTaskDetailId(taskDetailId);
			if(gatewayState > 0) {
				//根据api大于0代表发送成功
				successCount++;
				smsTaskDetailState.setSmsTaskSendStatus(SmsDetailSendStateEnum.SMS_DETAIL_SEND_SUCCESS.getStateCode());
			}else {
				//其他情况则代表发送失败
				failCount++;
				smsTaskDetailState.setSmsTaskSendStatus(SmsDetailSendStateEnum.SMS_DETAIL_SEND_FAILURE.getStateCode());
			}
			
			smsTaskDetailStateDao.updateDetailState(smsTaskDetailState);
			
		}
		
		//计算每批的成功和失败的个数
		Integer smsSuccessCount = smsHead.getSendingSuccessNum();
		Integer smsFailCount = smsHead.getSendingFailNum();
		Integer smsWaitingNum = smsHead.getWaitingNum();
		if(smsSuccessCount== null || smsSuccessCount == 0) {
			smsHead.setSendingSuccessNum(successCount);
			smsWaitingNum = smsWaitingNum - successCount;
		}else {
			smsHead.setSendingSuccessNum(smsSuccessCount + successCount);
			smsWaitingNum = smsWaitingNum - (smsSuccessCount + successCount);
		}
		if(smsFailCount == null || smsFailCount == 0){
			smsHead.setSendingFailNum(failCount);
			smsWaitingNum = smsWaitingNum - failCount;
		}else {
			smsHead.setSendingFailNum(smsFailCount + failCount);
			smsWaitingNum = smsWaitingNum - (smsFailCount + failCount);
		}
		//每批都更新成功失败以及等待的个数
		smsHead.setWaitingNum(smsWaitingNum);
	}
}
