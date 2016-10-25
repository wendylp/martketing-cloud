package cn.rongcapital.mkt.service;

import java.util.Map;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface SmsTaskHeadService {

	public BaseOutput smsTaskHeadList(String userId, Integer index, Integer size, String smsTaskAppType,String smsTaskStatus, String smsTaskName);
	
	public BaseOutput smsTaskHeadPublish(String userId, Integer id);
	
}
