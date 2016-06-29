package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.Tenement;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class BaseMQService {
	private static Logger logger = LoggerFactory.getLogger(BaseMQService.class);
	private static Context jndiContext = null;
	private static ConnectionFactory connectionFactory = null;
	private static Connection conn = null;
	@Autowired  
    private JmsMessagingTemplate jmsMessagingTemplate; 
	@Value("${spring.activemq.broker-url}")
	private String providerUrl;
	@Autowired
	private TenementDao tenementDao;
	@Autowired
	private CampaignSwitchDao campaignSwitchDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	protected static ConcurrentHashMap<String, MessageConsumer> consumerMap = new ConcurrentHashMap<String, MessageConsumer>();
	
	private static volatile boolean isJndiInited = false;
	
	public synchronized void initJndiEvironment() {
		if(isJndiInited) {
			return;
		}
		isJndiInited = true;
		try {
			Hashtable<Object,Object> environment = new Hashtable<Object,Object>();   
	        environment.put(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");   
	        environment.put(Context.PROVIDER_URL, providerUrl);   
			jndiContext = new InitialContext(environment);
			connectionFactory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
			conn = connectionFactory.createConnection();
			conn.start();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	protected void cancelCampaignInnerTask(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		String consumerKey = campaignHeadId+"-"+itemId;
		MessageConsumer consumer = consumerMap.get(consumerKey);
		if(null != consumer) {
			try {
				consumer.close();
				consumerMap.remove(consumerKey);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 传递给后面节点的数据，从当前节点的mongo库里逻辑删除
	 * @param segmentListToNext
	 */
	protected void logicDeleteNodeAudience(Integer campaignHeadId,String itemId,List<Segment> segmentListToNext) {
		for(Segment cs:segmentListToNext) {
			List<Criteria> criteriasList = new ArrayList<Criteria>();
			Criteria criteria1 = Criteria.where("campaignHeadId").is(campaignHeadId);
			criteriasList.add(criteria1);
			Criteria criteria2 = Criteria.where("itemId").is(itemId);
			criteriasList.add(criteria2);
			Criteria criteria3 = Criteria.where("dataId").is(cs.getDataId());
			criteriasList.add(criteria3);
			Criteria criteria4 = Criteria.where("status").is(0);
			criteriasList.add(criteria4);
			Criteria criteriaAll = new Criteria().andOperator(criteriasList.toArray(new Criteria[criteriasList.size()]));
			Update update = new Update().inc("status", 1);//逻辑删除
			mongoTemplate.findAndModify(new Query(criteriaAll), update, NodeAudience.class);
		}
	}
	/**
	 * 传递给后面节点的数据，从当前节点的mongo库里删除
	 * @param segmentListToNext
	 */
	protected void deleteNodeAudience(Integer campaignHeadId,String itemId,List<Segment> segmentListToNext) {
		for(Segment cs:segmentListToNext) {
			List<Criteria> criteriasList = new ArrayList<Criteria>();
			Criteria criteria1 = Criteria.where("campaignHeadId").is(campaignHeadId);
			criteriasList.add(criteria1);
			Criteria criteria2 = Criteria.where("itemId").is(itemId);
			criteriasList.add(criteria2);
			Criteria criteria3 = Criteria.where("dataId").is(cs.getDataId());
			criteriasList.add(criteria3);
			Criteria criteriaAll = new Criteria().andOperator(criteriasList.toArray(new Criteria[criteriasList.size()]));
			mongoTemplate.findAllAndRemove(new Query(criteriaAll), NodeAudience.class);
		}
	}
	
	protected boolean checkNodeAudienceExist (int campaignId,String itemId,int dataId) {
		boolean exist = false;
		Criteria criteria = Criteria.where("campaignHeadId").is(campaignId)
									.and("itemId").is(itemId)
									.and("dataId").is(dataId);
		Query query = new Query(criteria);
		List<NodeAudience> nodeAudienceExistList = mongoTemplate.find(query, NodeAudience.class);
		if(CollectionUtils.isNotEmpty(nodeAudienceExistList)) {
			exist = true;
		}
		return exist;
	}
	
    protected List<CampaignSwitch> queryCampaignEndsList(int campaignHeadId,String itemId) {
		CampaignSwitch campaignSwitch = new CampaignSwitch();
		campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_ENDS);
		campaignSwitch.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignSwitch.setCampaignHeadId(campaignHeadId);
		campaignSwitch.setItemId(itemId);
		List<CampaignSwitch> campaignEndsList = campaignSwitchDao.selectList(campaignSwitch);
		return campaignEndsList;
	}
    
    protected List<CampaignSwitch> queryCampaignSwitchYesList(int campaignHeadId,String itemId) {
		CampaignSwitch campaignSwitch = new CampaignSwitch();
		campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_SWITCH_YES);
		campaignSwitch.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignSwitch.setCampaignHeadId(campaignHeadId);
		campaignSwitch.setItemId(itemId);
		List<CampaignSwitch> campaignSwitchListYes = campaignSwitchDao.selectList(campaignSwitch);
		return campaignSwitchListYes;
	}
	
    protected List<CampaignSwitch> queryCampaignSwitchNoList(int campaignHeadId,String itemId) {
		CampaignSwitch campaignSwitch = new CampaignSwitch();
		campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_SWITCH_NO);
		campaignSwitch.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignSwitch.setCampaignHeadId(campaignHeadId);
		campaignSwitch.setItemId(itemId);
		List<CampaignSwitch> campaignSwitchListNo = campaignSwitchDao.selectList(campaignSwitch);
		return campaignSwitchListNo;
	}
    
	protected Queue getDynamicQueue(String name) {
		Queue myqueue = null;
		try {
			myqueue = (Queue)jndiContext.lookup("dynamicQueues/"+name);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return myqueue;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Segment> getQueueData(Queue queue) {
		List<Segment> myDomainObj = null;
		try {
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer = session.createConsumer(queue);
			Message msg = consumer.receiveNoWait();
			if(null!=msg){
				myDomainObj = (List<Segment>)((ObjectMessage)msg).getObject();
			}
			consumer.close();
			session.close();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return myDomainObj;
	}
	
	protected void sendDynamicQueue(List<Segment> campaignSegmentList,String dest) { 
		Session session = null;
    	try {
    		session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
    		Destination destination = session.createQueue(dest);
    		jmsMessagingTemplate.convertAndSend(destination, campaignSegmentList); 
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			try {
				session.close();
			} catch (JMSException e) {
				logger.error(e.getMessage(),e);
			}
		}
    } 
	
	protected MessageConsumer getQueueConsumer(Queue queue) {
		MessageConsumer consumer = null;
		Session session = null;
		try {
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumer = session.createConsumer(queue);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return consumer;
	}
	
	protected void deleteQueueByName(String queueName) {
		//TO DO
	}
	
	protected String getPid() {
		String pid = null;
		Tenement t = new Tenement();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		List<Tenement> tenementList = tenementDao.selectList(t);
		if(CollectionUtils.isNotEmpty(tenementList)) {
			Tenement  tenement = tenementList.get(0);
			pid = tenement.getPid();
		}
		return pid;
	}
	
}
