package cn.rongcapital.mkt.job.service.impl;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.mongodb.DataPartyRepository;

@Service
public class DemoTaskServiceImpl implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataPartyRepository dataPartyRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		System.out.println(Calendar.getInstance().getTime().toString());
	}
	
	@Override
	public void task(Integer taskId) {
		logger.info("-------"+Calendar.getInstance().getTime().toString());
//		List<DataParty> dpList = null;
////		dpList = dataPartyRepository.findAll();
////		for(DataParty dp:dpList) {
////			System.out.println(JSON.toJSONString(dp));
////		}
////		dpList = dataPartyRepository.findByMid("101");
////		for(DataParty dp:dpList) {
////			System.out.println(JSON.toJSONString(dp));
////		}
////		dpList = mongoTemplate.find(new Query(Criteria.where("mid").is("102")),DataParty.class);
//		dpList = mongoTemplate.find(new Query(Criteria.where("mid").is(102)),DataParty.class);
////		dpList = mongoTemplate.find(new Query(Criteria.where("tagList.tagName").is("男")),DataParty.class);
//		for(DataParty dp:dpList) {
//			System.out.println(JSON.toJSONString(dp));
//		}
	}
}
