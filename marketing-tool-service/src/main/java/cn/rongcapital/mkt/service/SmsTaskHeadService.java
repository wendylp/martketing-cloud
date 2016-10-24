package cn.rongcapital.mkt.service;

import java.util.Map;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface SmsTaskHeadService {

	public BaseOutput smsTaskHeadList(String userId, Integer index, Integer size, Integer smsTaskAppType,Integer smsTaskStatus, String smsTaskName);
	
	public Map<String,Integer> countStatusById(long id);
	
	public BaseOutput smsTaskHeadPublish(String userId, Integer id);
	
}
