package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTaskAppEnum;
import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SmsTaskHeadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.ColumnsOut;

@Service
public class SmsTaskHeadServiceImpl implements SmsTaskHeadService {

	private final String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
	
	@Autowired
	private SmsTaskHeadDao smsTaskHeadDao;
	@Autowired
	private MQTopicService mqTopicService;

	@Override
	public BaseOutput smsTaskHeadList(String userId, Integer index, Integer size, Integer smsTaskAppType,
			Integer smsTaskStatus, String smsTaskName) {
		BaseOutput output = this.newSuccessBaseOutput();
		SmsTaskHead smsTaskHeadTemp = new SmsTaskHead();
		if(smsTaskAppType!=null){
			smsTaskHeadTemp.setSmsTaskAppType(smsTaskAppType);
		}
		if(smsTaskStatus!=null){
			smsTaskHeadTemp.setSmsTaskStatus(smsTaskStatus);
		}
		if(StringUtils.isNotEmpty(smsTaskName)){
			smsTaskHeadTemp.setSmsTaskName(smsTaskName);
		}
		smsTaskHeadTemp.setOrderField("create_time");
		smsTaskHeadTemp.setOrderFieldType("DESC");
		smsTaskHeadTemp.setStartIndex((index-1)*index);
		smsTaskHeadTemp.setPageSize(size);		
		
		int totalCount = smsTaskHeadDao.selectListCount(smsTaskHeadTemp);
		List<SmsTaskHead> dataList = smsTaskHeadDao.selectList(smsTaskHeadTemp);
		dataList = this.getSmsTaskHeads(dataList);	
		output.setTotalCount(totalCount);		
		this.setBaseOut(output, dataList);
		List<Object> colNames = this.getColumnsOutList();
		output.setColNames(colNames);
		return output;
	}
	
	private List<SmsTaskHead> getSmsTaskHeads(List<SmsTaskHead> dataList){
		if(CollectionUtils.isNotEmpty(dataList)){
			SmsTaskStatusEnum smsTaskStatusEnum = SmsTaskStatusEnum.TASK_EXECUTING;
			Integer status = smsTaskStatusEnum.getStatusCode();			
			for(Iterator<SmsTaskHead> iter = dataList.iterator();iter.hasNext();){
				SmsTaskHead smsTaskHead = iter.next();
				if(smsTaskHead!=null){
					Integer smsTaskStatus = smsTaskHead.getSmsTaskStatus();
					smsTaskHead.setSmsTaskStatusStr(SmsTaskStatusEnum.getDescriptionByStatus(smsTaskStatus));
					Integer smsTaskAppType = smsTaskHead.getSmsTaskAppType();
					smsTaskHead.setSmsTaskAppTypeStr(SmsTaskAppEnum.getDescriptionByStatus(NumUtil.int2OneByte(smsTaskAppType)));					
					smsTaskHead.setCreateTimeStr(DateUtil.getStringFromDate(smsTaskHead.getCreateTime(),FORMAT_STRING));
					/**
					 * 统计执行中的总数
					 */
					if(smsTaskStatus.byteValue()==status){
						this.setSmsTaskHeadNumsById(smsTaskHead);
					}
					this.setSmsTaskHeadPers(smsTaskHead);
				}
			}
		}		
		return dataList;		
	}
	
	private void setSmsTaskHeadPers(SmsTaskHead smsTaskHead){
		Integer totalCoverNum = smsTaskHead.getTotalCoverNum();
		Integer sendingFailNum = smsTaskHead.getSendingFailNum();
		Integer sendingSuccessNum = smsTaskHead.getSendingSuccessNum();
		Integer waitingNum = smsTaskHead.getWaitingNum();
		Integer sendingFailNumPer = sendingFailNum*100/totalCoverNum;
		Integer sendingSuccessNumPer = sendingSuccessNum*100/totalCoverNum;
		Integer waitingNumPer = waitingNum*100/totalCoverNum;
		smsTaskHead.setSendingFailNumPer(sendingFailNumPer);
		smsTaskHead.setSendingSuccessNumPer(sendingSuccessNumPer);
		smsTaskHead.setWaitingNumPer(waitingNumPer);
	}
	
	private void setSmsTaskHeadNumsById(SmsTaskHead smsTaskHead){
		Map<Integer, Integer> smsTaskStatusCountmap = smsTaskHeadDao.countStatusById(smsTaskHead.getId());
		if(smsTaskStatusCountmap!=null&smsTaskStatusCountmap.size()>0){
			int waitingNum = smsTaskStatusCountmap.get(0);
			int sendingSuccessNum = smsTaskStatusCountmap.get(1);
			int sendingFailNum = smsTaskStatusCountmap.get(2);
			int totalCoverNum = waitingNum+sendingSuccessNum+sendingFailNum;
			smsTaskHead.setWaitingNum(waitingNum);
			smsTaskHead.setSendingSuccessNum(sendingSuccessNum);
			smsTaskHead.setSendingFailNum(sendingFailNum);
			smsTaskHead.setTotalCoverNum(totalCoverNum);
		}
	}
			
	@Override
	public Map<String, Integer> countStatusById(long id) {
		
		return null;
	}

	private List<Object> getColumnsOutList(){
		List<Object> columnsOutList = new ArrayList<Object>(); 
		//总覆盖
		ColumnsOut columnsOut0 = new ColumnsOut();
		columnsOut0.setColCode("totalCoverNum");
		columnsOut0.setColName("总覆盖");
		columnsOutList.add(columnsOut0);
		//已成功
		ColumnsOut columnsOut1 = new ColumnsOut();
		columnsOut1.setColCode("sendingSuccessNum");
		columnsOut1.setColName("已成功");
		columnsOutList.add(columnsOut1);
		//等待中
		ColumnsOut columnsOut2 = new ColumnsOut();
		columnsOut2.setColCode("waitingNum");
		columnsOut2.setColName("等待中");
		columnsOutList.add(columnsOut2);
		//已失败
		ColumnsOut columnsOut3 = new ColumnsOut();
		columnsOut3.setColCode("sendingFailNum");
		columnsOut3.setColName("已失败");
		columnsOutList.add(columnsOut3);
		return columnsOutList;		
	}
	
	private BaseOutput newSuccessBaseOutput() {
		return new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
	}

	private <O> void setBaseOut(BaseOutput out, List<O> dataList) {
		if (CollectionUtils.isEmpty(dataList)) {
			return;
		}
		out.setTotal(dataList.size());
		out.getData().addAll(dataList);
	}

	@Override
	public BaseOutput smsTaskHeadPublish(String userId, Integer id) {
		BaseOutput output = this.newSuccessBaseOutput();
		SmsTaskHead smsTaskHeadTemp = new SmsTaskHead();
		List<SmsTaskHead> smsTaskHeadList = smsTaskHeadDao.selectList(smsTaskHeadTemp);
		if(CollectionUtils.isNotEmpty(smsTaskHeadList)){
			SmsTaskHead smsTaskHeadBack = smsTaskHeadList.get(0);
			if(smsTaskHeadBack!=null){
				Integer audienceGenerateStatus = smsTaskHeadBack.getAudienceGenerateStatus();
				smsTaskHeadBack.setSmsTaskStatus(2);
				smsTaskHeadDao.updateById(smsTaskHeadBack);
				if(audienceGenerateStatus!=1){					
					mqTopicService.sendSmsByTaskId(String.valueOf(id));
				}
			}			
		}		
		return output;
	}
	
}
