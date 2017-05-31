package cn.rongcapital.mkt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.WechatInterfaceLogDao;
import cn.rongcapital.mkt.po.WechatInterfaceLog;
import cn.rongcapital.mkt.service.WechatInterfaceLogService;

@Service
public class WechatInterfaceLogServiceImpl implements WechatInterfaceLogService {

	@Autowired
	private WechatInterfaceLogDao wechatInterfaceLogDao;
	
	@Override
	public void insert(WechatInterfaceLog wechatInterfaceLog) {
//		update by xiexiaoliang  2017-05-31,记录日志的数据量太大，不记录在数据库中
//		wechatInterfaceLogDao.insert(wechatInterfaceLog);
	}

	
}
