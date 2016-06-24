package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.Hashtable;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.Tenement;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class BaseMQService {
	
	private static Logger logger = LoggerFactory.getLogger(BaseMQService.class);
	
	private static Context jndiContext = null;
	
	private static ConnectionFactory connectionFactory = null;
	
	@Value("${spring.activemq.broker-url}")
	private String providerUrl;
	
	private volatile boolean isJndiInited = false;
	
	@Autowired
	private TenementDao tenementDao;

	public void initJndiEvironment() {
		if(isJndiInited){
			return;
		}
		isJndiInited = true;
		try {
			Hashtable<Object,Object> environment = new Hashtable<Object,Object>();   
	        environment.put(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");   
	        environment.put(Context.PROVIDER_URL, providerUrl);   
			jndiContext = new InitialContext(environment);
			connectionFactory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
		} catch (NamingException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
    @Autowired
    private CampaignSwitchDao campaignSwitchDao;
    
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
			Connection conn = connectionFactory.createConnection();
			conn.start();

			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer = session.createConsumer(queue);
			Message msg = consumer.receiveNoWait();
			if(null!=msg){
				myDomainObj = (List<Segment>)((ObjectMessage)msg).getObject();
			}
			consumer.close();
			session.close();
			conn.close();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return myDomainObj;
	}
	
	protected MessageConsumer getQueueConsumer(Queue queue) {
		MessageConsumer consumer = null;
		try {
			Connection conn = connectionFactory.createConnection();
			conn.start();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumer = session.createConsumer(queue);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return consumer;
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