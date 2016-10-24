package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsTempletService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.ColumnsOut;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletIn;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletListIn;

@Service
public class SmsTempletServiceImpl implements SmsTempletService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
	
	@Autowired
	private SmsTempletDao smsTempletDao;
	
	private final String TEMPLETE_CONTENT_= "模板内容";
	
	private final String TEMPLETE_CREATE_TIME ="创建时间";
	
	private final String TEMPLETE_AUDIT_STATUS ="审核状态";
		
	@Override
	public BaseOutput smsTempletList(String userId, Integer index, Integer size, Integer channelType,
			Integer type, String content) {
		BaseOutput output = this.newSuccessBaseOutput();
		SmsTemplet smsTempletTemp = new SmsTemplet();		
		smsTempletTemp.setChannelType(NumUtil.int2OneByte(channelType));
		smsTempletTemp.setType(NumUtil.int2OneByte(type));
		if(StringUtils.isNotEmpty(content)){
			smsTempletTemp.setContent(content);
		}
		smsTempletTemp.setOrderField("create_time");
		smsTempletTemp.setOrderFieldType("DESC");
		smsTempletTemp.setStartIndex((index-1)*index);
		smsTempletTemp.setPageSize(size);
		
		int totalCount = smsTempletDao.selectListCount(smsTempletTemp);
		List<SmsTemplet> dataList = smsTempletDao.selectList(smsTempletTemp);
		if(CollectionUtils.isNotEmpty(dataList)){
			for(Iterator<SmsTemplet> iter = dataList.iterator();iter.hasNext();){
				SmsTemplet smsTemplet = iter.next();
				smsTemplet.setCreateTimeStr(DateUtil.getStringFromDate(smsTemplet.getCreateTime(),FORMAT_STRING));
			}
		}
		output.setTotalCount(totalCount);		
		this.setBaseOut(output, dataList);
		List<Object> colNames = this.getColumnsOutList();
		output.setColNames(colNames);
		return output;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput insertSmsTemplet(SmsTempletIn smsTempletIn) {
		BaseOutput output = this.newSuccessBaseOutput();
		SmsTemplet smsTemplet = this.getSmsTempletBySmsTempletIn(smsTempletIn);
		if(smsTemplet!=null){
			smsTempletDao.insert(smsTemplet);
		}
		return output;
		
	}

	private SmsTemplet getSmsTempletBySmsTempletIn(SmsTempletIn smsTempletIn){
		SmsTemplet smsTemplet = new SmsTemplet();
		if(smsTempletIn!=null){
			if(StringUtils.isNotEmpty(smsTempletIn.getAuditor())){
				smsTemplet.setAuditor(smsTempletIn.getAuditor());
			}			
			smsTemplet.setAuditStatus(NumUtil.int2OneByte(1));
			if(smsTempletIn.getChannelType()!=null){
				smsTemplet.setChannelType(NumUtil.int2OneByte(smsTempletIn.getChannelType()));
			}
			if(smsTempletIn.getType()!=null){
				smsTemplet.setType(NumUtil.int2OneByte(smsTempletIn.getType()));
			}
			if(StringUtils.isNoneEmpty(smsTempletIn.getContent())){
				smsTemplet.setContent(smsTempletIn.getContent());
			}
			if(StringUtils.isNoneEmpty(smsTempletIn.getCreator())){
				smsTemplet.setCreator(smsTempletIn.getCreator());
				smsTemplet.setCreateTime(new Date());
			}			
			smsTemplet.setStatus(NumUtil.int2OneByte(0));
			if(StringUtils.isNoneEmpty(smsTempletIn.getUpdateUser())){
				smsTemplet.setUpdateTime(new Date());
				smsTemplet.setUpdateUser(smsTempletIn.getUpdateUser());
			}			
		}
		return smsTemplet;		
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
	
	private List<Object> getColumnsOutList(){
		List<Object> columnsOutList = new ArrayList<Object>(); 
		//ID
		ColumnsOut columnsOut0 = new ColumnsOut();
		columnsOut0.setColCode("id");
		columnsOut0.setColName("ID");
		columnsOutList.add(columnsOut0);
		//模板内容
		ColumnsOut columnsOut1 = new ColumnsOut();
		columnsOut1.setColCode("content");
		columnsOut1.setColName(this.TEMPLETE_CONTENT_);
		columnsOutList.add(columnsOut1);
		//createTime
		ColumnsOut columnsOut2 = new ColumnsOut();
		columnsOut2.setColCode("createTimeStr");
		columnsOut2.setColName(this.TEMPLETE_CREATE_TIME);
		columnsOutList.add(columnsOut2);
		//审核状态
		ColumnsOut columnsOut3 = new ColumnsOut();
		columnsOut3.setColCode("auditStatus");
		columnsOut3.setColName(this.TEMPLETE_AUDIT_STATUS);
		columnsOutList.add(columnsOut3);
		return columnsOutList;		
	}
	
}
