package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignDecisionTagDao;
import cn.rongcapital.mkt.dao.CampaignDecisionTagMapDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignDecisionTag;
import cn.rongcapital.mkt.po.CampaignDecisionTagMap;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignDecisionTagTaskImpl extends BaseMQService implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
			
	@Autowired  
    private MQUtil mqUtil;
	@Autowired
	private CampaignDecisionTagDao campaignDecisionTagDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignDecisionTagMapDao campaignDecisionTagMapDao;
	
	MessageConsumer consumer = null;
	
	@Override
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		List<CampaignSwitch> campaignSwitchList = queryCampaignSwitchList(campaignHeadId, itemId);
		if(CollectionUtils.isEmpty(campaignSwitchList)) {
			return;//标签判断节点下面如果没有分支，则该条活动线路截止
		}
		CampaignDecisionTag campaignDecisionTagT = new CampaignDecisionTag();
		campaignDecisionTagT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignDecisionTagT.setCampaignHeadId(campaignHeadId);
		campaignDecisionTagT.setItemId(itemId);
		List<CampaignDecisionTag> campaignDecisionTagList = campaignDecisionTagDao.selectList(campaignDecisionTagT);
		if(CollectionUtils.isEmpty(campaignDecisionTagList)) {
			logger.error("标签判断规则为空,campagin_head_id:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		Queue queue = getDynamicQueue(campaignHeadId+"-"+itemId);//获取MQ
		consumer = getQueueConsumer(queue);
		MessageListener listner = new MessageListener() {
			@Override
			public void onMessage(Message message) {
				try {
					if(null!=message){
						@SuppressWarnings("unchecked")
						//获取数据对象list
						List<Segment> segmentList = (List<Segment>)((ObjectMessage)message).getObject();
						if(CollectionUtils.isNotEmpty(segmentList)) {
							CampaignDecisionTag campaignDecisionTag = campaignDecisionTagList.get(0);//取出规则对象
							List<Segment> segmentListToMqYes = new ArrayList<Segment>();//是分支的数据对象list
							List<Segment> segmentListToMqNo = new ArrayList<Segment>();//非分支的数据对象list
							for(Segment s:segmentList) {
								String dataIdStr = s.getDataId();
								DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(dataIdStr)), DataParty.class);
								if(null != dp) {
									Byte rule = campaignDecisionTag.getRule();
									List<Integer> idList = new ArrayList<Integer>();
									idList.add(campaignDecisionTag.getId());
									List<CampaignDecisionTagMap> campaignDecisionTagMapList=campaignDecisionTagMapDao.selectListByIdList(idList);
									if(rule != null && CollectionUtils.isNotEmpty(campaignDecisionTagMapList)) {
										List<String> tagIdList = new ArrayList<String>();
										for(CampaignDecisionTagMap cdtm:campaignDecisionTagMapList) {
											tagIdList.add(cdtm.getTagId()+"");
										}
										switch (rule) {
										case ApiConstant.CAMPAIGN_DECISION_TAG_RULE_MATCH_ALL:
											break;
										case ApiConstant.CAMPAIGN_DECISION_TAG_RULE_MATCH_ONE:
											List<DataParty> dpListM = mongoTemplate.find(new Query(Criteria.where("tagList.tagId").in(tagIdList)), DataParty.class);
											if(CollectionUtils.isNotEmpty(dpListM)) {
												segmentListToMqYes.add(s);
											}else{
												segmentListToMqNo.add(s);
											}
											break;
										case ApiConstant.CAMPAIGN_DECISION_TAG_RULE_MATCH_TWO_MORE:
											
											break;
										}
									}
								}else{
									logger.info("mongoDB:data not exist in data_party,mid:"+dataIdStr);
								}
							}
							if(campaignSwitchList.size() > 0) {
								CampaignSwitch csYes = campaignSwitchList.get(0);
								mqUtil.sendDynamicQueue(segmentListToMqYes, csYes.getCampaignHeadId() +"-"+csYes.getNextItemId());
							}
							if(campaignSwitchList.size() > 1) {
								CampaignSwitch csNo = campaignSwitchList.get(1);
								mqUtil.sendDynamicQueue(segmentListToMqYes, csNo.getCampaignHeadId() +"-"+csNo.getNextItemId());
							}
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
		};
		if(null!=consumer){
			try {
				consumer.setMessageListener(listner);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}     
		}
	}
	
	public void cancelInnerTask() {
		if(null != consumer) {
			try {
				consumer.close();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub
		
	}
	
}
