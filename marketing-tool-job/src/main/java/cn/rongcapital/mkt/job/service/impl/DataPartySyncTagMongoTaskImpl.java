package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.RuleEngineService;

/*************************************************
 * @功能及特点的描述简述: 同步主数据标签到mongodb中
 * 
 * @see （与该类关联的类): TaskService
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.2 @date(创建、开发日期): 2016-09-28 最后修改日期: 2016-09-28
 * @复审人: 丛树林
 *************************************************/
@Service
public class DataPartySyncTagMongoTaskImpl implements TaskService {

	private static Logger logger = LoggerFactory.getLogger(DataPartySyncTagMongoTaskImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final int pageSize = 500;
	@Autowired
	private RuleEngineService ruleEngineService;

	@Override
	public void task(Integer taskId) {
		long totalRecord = mongoTemplate.count(null, DataParty.class);
		long totalPage = (totalRecord + pageSize - 1) / pageSize;
		for (int index = 0; index < totalPage; index++) {
			List<DataParty> dataPartyList = mongoTemplate.find(new Query().skip(index * pageSize).limit(pageSize),
					DataParty.class);
			if (CollectionUtils.isEmpty(dataPartyList)) {
				break;
			}
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			for (DataParty dp : dataPartyList) {
				arrayList.add(dp.getMid());
			}
			Boolean flag = ruleEngineService.requestRuleEngine(arrayList);
			if (flag) {
				ruleEngineService.synchMongoTagData();
			}
		}
	}
}