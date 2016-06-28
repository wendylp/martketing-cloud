package cn.rongcapital.mkt.job.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.mongodb.DataPartyRepository;
import cn.rongcapital.mkt.po.mongodb.DataParty;

@Service
public class DataPartySyncTaskServiceImpl implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataPartyRepository dataPartyRepository;
	
    //主数据data_xxx
	@Autowired
    private DataPartyDao dataPartyDao;
    
    @Autowired
    private DataPaymentDao dataPaymentDao;
    
    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;
    
    @Autowired
    private DataShoppingDao dataShoppingDao;
    
    @Autowired
    private DataPopulationDao dataPopulationDao;
    
    @Autowired
    private DataArchPointDao dataArchPointDao;
    
    @Autowired
    private DataMemberDao dataMemberDao;
    
    @Autowired
    private DataLoginDao dataLoginDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void task(Integer taskId) {
	    		
	    //1.从mysql data_party表中读取所有mid,keyid
	    //Tag tag=new Tag();
	    //tag.setStatus(0);
	    //List<String> tags=tagDao.selectList(tag); 
	    
	    //2.根据keyid在data_xxx表中查出所有字段插入mongodb data_party表中
	    
	    	    
	    
	    logger.info(taskId.toString());
		List<DataParty> dpList = null;
		dpList = dataPartyRepository.findAll();
		for(DataParty dp:dpList) {
			System.out.println(JSON.toJSONString(dp));
		}
		dpList = dataPartyRepository.findByMid("101");
		for(DataParty dp:dpList) {
			System.out.println(JSON.toJSONString(dp));
		}
		dpList = mongoTemplate.find(new Query(Criteria.where("mid").is("101")),DataParty.class);
		for(DataParty dp:dpList) {
			System.out.println(JSON.toJSONString(dp));
		}
	}
}
