package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
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

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpClientUtil;
import cn.rongcapital.mkt.common.util.HttpUrl;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WechatGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.dao.WechatPersonalUuidDao;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.Tenement;
import cn.rongcapital.mkt.po.WechatGroup;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.po.WechatPersonalUuid;
import cn.rongcapital.mkt.po.mongodb.DataParty;
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
	@Value("${runxue.h5.api.base.url}")
	protected String h5BaseUrl;
	@Autowired
	protected WechatPersonalUuidDao wechatPersonalUuidDao;
	@Autowired
	private WechatMemberDao wechatMemberDao;
	@Autowired
	private WechatGroupDao wechatGroupDao;
	
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
	
	protected boolean isPubWechatFans(Segment segment,String pubId,Byte subscribeTimeType) {
		boolean isFans = false;
		DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(segment.getDataId())), 
											 DataParty.class);
		if(null != dp && null != dp.getMdType() &&
				   StringUtils.isNotBlank(dp.getMappingKeyid()) &&
				   dp.getMdType() == ApiConstant.DATA_PARTY_MD_TYPE_WECHAT) {
			String fanOpenId = dp.getMappingKeyid();
			WechatMember wechatMemberT = new WechatMember();
			wechatMemberT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatMemberT.setWxCode(fanOpenId);
			wechatMemberT.setPubId(pubId);
			List<WechatMember> wechatMemberList = wechatMemberDao.selectList(wechatMemberT);
			if(CollectionUtils.isNotEmpty(wechatMemberList)) {
				if(null == subscribeTimeType) {//为空表示不限订阅时间
					isFans = true;
				} else {
					String realSubscribeTime = wechatMemberList.get(0).getSubscribeTime();
					isFans = checkSubscriberTime(subscribeTimeType,realSubscribeTime);
				}
			}
		}
		return isFans;
	}
	
	protected boolean checkSubscriberTime(byte subscribeTimeType,String realSubscribeTime) {
		boolean isSubscribe = false;
		DateTime realSubscribeTimeDate = DateTime.parse(realSubscribeTime);  
		if(null != realSubscribeTimeDate) {
			DateTime now = new DateTime();
			switch (subscribeTimeType) {
			case 0://一天
				Interval interval1 = new Interval(realSubscribeTimeDate, now);
				long interv1 = interval1.toDuration().getStandardDays();
				if(interv1 <= 1) {
					isSubscribe = true;
				}
				break;
			case 1://一周
				Interval interval2 = new Interval(realSubscribeTimeDate, now);
				long interv2 = interval2.toDuration().getStandardDays();
				if(interv2 <= 7) {
					isSubscribe = true;
				}
				break;
			case 2://一个月
				Interval interval3 = new Interval(realSubscribeTimeDate, now);
				long interv3 = interval3.toDuration().getStandardDays();
				if(interv3 <= 30) {
					isSubscribe = true;
				}
				break;
			case 3://三个月
				Interval interval4 = new Interval(realSubscribeTimeDate, now);
				long interv4 = interval4.toDuration().getStandardDays();
				if(interv4 <= 90) {
					isSubscribe = true;
				}				
				break;
			case 4://六个月
				Interval interval5 = new Interval(realSubscribeTimeDate, now);
				long interv5 = interval5.toDuration().getStandardDays();
				if(interv5 <= 180) {
					isSubscribe = true;
				}				
				break;
			case 5:
				Interval interval6 = new Interval(realSubscribeTimeDate, now);
				long interv6 = interval6.toDuration().getStandardDays();
				if(interv6 <= 365) {
					isSubscribe = true;
				}				
				break;
			case 6:
				Interval interval7 = new Interval(realSubscribeTimeDate, now);
				long interv7 = interval7.toDuration().getStandardDays();
				if(interv7 > 365) {
					isSubscribe = true;
				}							
				break;
			}
		}
		return isSubscribe;
	}
	
	protected boolean isPrvWechatFriend(Segment segment,String uin,String groupUcode) {
		boolean isFriend = false;
		DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(segment.getDataId())), DataParty.class);
		if(null != dp && null != dp.getMdType() &&
		   StringUtils.isNotBlank(dp.getMappingKeyid()) &&
		   dp.getMdType() == ApiConstant.DATA_PARTY_MD_TYPE_WECHAT) {
			String friendUcode = dp.getMappingKeyid();
			WechatMember wechatMemberT = new WechatMember();
			wechatMemberT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			wechatMemberT.setWxCode(friendUcode);
			wechatMemberT.setUin(uin);
			List<WechatMember> wechatMemberList = wechatMemberDao.selectList(wechatMemberT);
			if(CollectionUtils.isNotEmpty(wechatMemberList)) {
				if(StringUtils.isBlank(groupUcode)) {//groupid为空表示不限群组
					isFriend = true;
				} else {
					for(WechatMember wm:wechatMemberList) {
						if(StringUtils.isNotBlank(wm.getWxGroupId()) &&
						   StringUtils.isNumeric(wm.getWxGroupId())) {
							Integer idOfWechatGroup = Integer.parseInt(wm.getWxGroupId());
							WechatGroup wechatGroupT = new WechatGroup();
							wechatGroupT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
							wechatGroupT.setGroupId(groupUcode);
							List<WechatGroup> wechatGroupList = wechatGroupDao.selectList(wechatGroupT);
							if(CollectionUtils.isNotEmpty(wechatGroupList) && 
							   wechatGroupList.get(0).getId()==idOfWechatGroup) {
								  isFriend = true;
								  break;
								}
							}
						}
						
					}
				}
			}
		return isFriend;
	}
	
	protected boolean sendPubWechatByH5Interface(String pubId,Integer materialId,String fansWeixinId) {
		boolean isSent = false;
		HttpUrl httpUrl = new HttpUrl();
		httpUrl.setHost(h5BaseUrl);
		httpUrl.setPath(ApiConstant.DL_PUB_SEND_API_PATH+getPid());
		HashMap<Object , Object> params = new HashMap<Object , Object>();
		params.put("pub_id", pubId);
		List<String> fansWeixinIds = new ArrayList<String>();
		fansWeixinIds.add(fansWeixinId);
		params.put("fans_weixin_ids",fansWeixinIds);
		params.put("message_type","news");
		params.put("material_id", materialId);
		httpUrl.setRequetsBody(JSON.toJSONString(params));
		httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
		try {
			PostMethod postResult = HttpClientUtil.getInstance().postExt(httpUrl);
			String postResStr = postResult.getResponseBodyAsString();
			String status = JSON.parseObject(postResStr).getJSONObject("hfive_mkt_pub_send_response").getString("status");
			if(StringUtils.isNotBlank(status) && status.equalsIgnoreCase("true")) {
				isSent = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			isSent = false;
		}
		return isSent;
	}
	
	protected boolean sendPrvWechatByH5Interface(String uin,String textInfo,String ucode) {
		boolean isSent = false;
		HashMap<Object , Object> params = new HashMap<Object , Object>();
		WechatPersonalUuid wechatPersonalUuidT = new WechatPersonalUuid();
		wechatPersonalUuidT.setStatus((int)ApiConstant.TABLE_DATA_STATUS_VALID);
		wechatPersonalUuidT.setUin(uin);
		List<WechatPersonalUuid> wechatPersonalUuidList = wechatPersonalUuidDao.selectList(wechatPersonalUuidT);
		if(CollectionUtils.isNotEmpty(wechatPersonalUuidList)) {
			WechatPersonalUuid wechatPersonalUuid = new WechatPersonalUuid();
			String uuid = wechatPersonalUuid.getUuid();
			params.put("uuid", uuid);
		} else {
			logger.error("没有有效的uuid,无法发送,uin:"+uin);
			return isSent;//没有有效的uuid,无法发送
		}
		HttpUrl httpUrl = new HttpUrl();
		httpUrl.setHost(h5BaseUrl);
		httpUrl.setPath(ApiConstant.DL_PRV_SEND_API_PATH+getPid());
		params.put("ucode", ucode);
		params.put("message",textInfo);
		httpUrl.setRequetsBody(JSON.toJSONString(params));
		httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
		try {
			PostMethod postResult = HttpClientUtil.getInstance().postExt(httpUrl);
			String postResStr = postResult.getResponseBodyAsString();
			String status = JSON.parseObject(postResStr).getJSONObject("hfive_mkt_personal_send_response").getString("status");
			if(StringUtils.isNotBlank(status) && status.equalsIgnoreCase("true")) {
				isSent = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			isSent = false;
		}
		return isSent;
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
	
	protected void insertNodeAudience(int campaignHeadId,String itemId,int dataId,String name,String mappingKeyId) {
		NodeAudience nodeAudience = new NodeAudience();
		nodeAudience.setCampaignHeadId(campaignHeadId);
		nodeAudience.setItemId(itemId);
		nodeAudience.setDataId(dataId);
		nodeAudience.setName(name);
		nodeAudience.setMappingKeyid(mappingKeyId);
		nodeAudience.setStatus(0);
		mongoTemplate.insert(nodeAudience);//插入mongo的node_audience表
	}
	
	protected boolean checkNodeAudienceExist (int campaignId,String itemId,int dataId,String mappingKeyid) {
		boolean exist = false;
		Criteria criteria = Criteria.where("campaignHeadId").is(campaignId)
									.and("itemId").is(itemId)
									.and("dataId").is(dataId)
								    .and("mappingKeyid").is(mappingKeyid);
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
