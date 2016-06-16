package cn.rongcapital.mkt.job.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.job.service.base.TaskService;

@Service
public class DemoTaskServiceImpl implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void task(Integer taskId) {
		logger.info(taskId.toString());
	}
}
