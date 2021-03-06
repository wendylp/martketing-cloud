package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.CampaignTagTypeEnum;
import cn.rongcapital.mkt.common.jedis.JedisConnectionManager;
import cn.rongcapital.mkt.dao.CampaignDecisionTagDao;
import cn.rongcapital.mkt.po.CampaignDecisionTag;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;

import com.alibaba.fastjson.JSON;
//import cn.rongcapital.mkt.dao.DataPartyDao;

@Service
public class CampaignDecisionTagTask extends CampaignAutoCancelTaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
			
	@Autowired
	private CampaignDecisionTagDao campaignDecisionTagDao;
	
//	@Autowired
//    private MongoTemplate mongoTemplate;
//	@Autowired
//	private DataPartyDao dataPartyDao;
	
	@Override
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		String queueKey = campaignHeadId+"-"+itemId;
		List<CampaignSwitch> campaignSwitchYesList = queryCampaignSwitchYesList(campaignHeadId, itemId);
		List<CampaignSwitch> campaignSwitchNoList = queryCampaignSwitchNoList(campaignHeadId, itemId);
		if(CollectionUtils.isEmpty(campaignSwitchYesList) && 
		   CollectionUtils.isEmpty(campaignSwitchNoList)) {
			return;//标签判断节点下面如果没有分支，则该条活动线路截止
		}
		CampaignDecisionTag campaignDecisionTagT = new CampaignDecisionTag();
		campaignDecisionTagT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignDecisionTagT.setCampaignHeadId(campaignHeadId);
		campaignDecisionTagT.setItemId(itemId);
		List<CampaignDecisionTag> campaignDecisionTagList = campaignDecisionTagDao.selectList(campaignDecisionTagT);
		if(CollectionUtils.isEmpty(campaignDecisionTagList) || 
		   null == campaignDecisionTagList.get(0).getRule() ||
		   null == campaignDecisionTagList.get(0).getTagIds()
		  ){
			logger.info("标签判断规则为空,campagin_head_id:"+campaignHeadId+",itemId:"+itemId);
			return;//该条活动线路截止
		}
		CampaignDecisionTag campaignDecisionTag = campaignDecisionTagList.get(0);
		Byte rule = campaignDecisionTag.getRule();//标签判断规则
		//查询该规则对应的标签list
		String tagIdsStr =  campaignDecisionTag.getTagIds();
		List<String> tagIdsStrListTemp = null;
		if(StringUtils.isNotEmpty(tagIdsStr)){
			String[] tagIdsStrListTemp1 = tagIdsStr.split(",");
			tagIdsStrListTemp = Arrays.asList(tagIdsStrListTemp1);
		}
		List<String> tagIdsStrList = tagIdsStrListTemp;
		//查询该规则对应的标签类型list
		String tagTypesStr = campaignDecisionTag.getTagTypes();
		List<String> tagTypesStrListTemp = null;
		if(StringUtils.isNotEmpty(tagTypesStr)){
			String[] tagTypesStrListTemp1 = tagTypesStr.split(",");
			tagTypesStrListTemp = Arrays.asList(tagTypesStrListTemp1);
		}		
		List<String> tagTypesStrList =tagTypesStrListTemp;
		
		Queue queue = getDynamicQueue(campaignHeadId+"-"+itemId);//获取MQ中的当前节点对应的queue
		MessageConsumer consumer = getQueueConsumer(queue);//获取queue的消费者对象
		//监听MQ的listener
		MessageListener listener = new MessageListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onMessage(Message message) {
				if(message!=null) {
					try {
						//获取segment list数据对象
						List<Segment> segmentList = (List<Segment>)((ObjectMessage)message).getObject();
						if(CollectionUtils.isNotEmpty(segmentList)) {
							processMqMessage(message,segmentList,tagIdsStrList,tagTypesStrList,campaignSwitchYesList,campaignSwitchNoList,rule,queueKey);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
				}
			}
		};
		if(null != consumer){
			try {
				//设置监听器
				consumer.setMessageListener(listener);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}     
		}
		
		/**
		 * 测试用代码
		 */
/*		List<String> segmentIdList = new ArrayList<String>();
		segmentIdList.add("1");
		List<Segment> segmentListUnique = dataPartyDao.selectSegmentByIdList(segmentIdList);
		sendDynamicQueue(segmentListUnique, campaignHeadId + "-" + itemId);
*/	}
	
    /**
     * 贝贝熊系统中关于自定义标签的处理逻辑被移除2017-06-06
     */
	//处理listener接收到的数据
	private void processMqMessage(Message message,List<Segment> segmentList,
								  List<String> tagIdList,
								  List<String> tagTypesStrList,
								  List<CampaignSwitch> campaignSwitchYesList,
								  List<CampaignSwitch> campaignSwitchNoList,
								  byte rule,String queueKey) throws Exception{
		List<Segment> segmentListToMqYes = new ArrayList<Segment>();//初始化"是"分支的数据对象list
		List<Segment> segmentListToMqNo = new ArrayList<Segment>();//初始化"非"分支的数据对象list
		for(Segment s:segmentList) {
			Integer campaignHeadId = null; // 活动id
			String itemId = null; // item id
			if (CollectionUtils.isNotEmpty(campaignSwitchYesList)) {
				CampaignSwitch csYes = campaignSwitchYesList.get(0);
				campaignHeadId = csYes.getCampaignHeadId();
				itemId = csYes.getItemId();
			} else if (CollectionUtils.isNotEmpty(campaignSwitchNoList)) {
				CampaignSwitch csNo = campaignSwitchNoList.get(0);
				campaignHeadId = csNo.getCampaignHeadId();
				itemId = csNo.getItemId();
			}

			if (campaignHeadId != null && StringUtils.isNotBlank(itemId)) {
				if (!checkNodeAudienceExist(campaignHeadId, itemId, s.getDataId())) {// @since 1.9
					logger.debug("保存流过节点的数据，活动id：{}， 节点id：{}", campaignHeadId, itemId);
					insertNodeAudience(campaignHeadId, itemId, s);
				}
			} else {
				logger.error("无效是数据，活动id：{}， 节点id：{}", campaignHeadId, itemId);
			}
			switch (rule) {
			//全部匹配
			case ApiConstant.CAMPAIGN_DECISION_TAG_RULE_MATCH_ALL:
				boolean isAllMatch = true;
				for(int i=0;i<tagIdList.size();i++){
//					Integer dataId = s.getDataId();
//					Criteria criteria = Criteria.where("mid").is(dataId);
//					if(CollectionUtils.isNotEmpty(tagTypesStrList)){
//						String tagTypeStr = tagTypesStrList.get(i);
//						if(CampaignTagTypeEnum.CAMPAIGN_TAG_TYPE_SYSTEM.getCode().equals(Integer.parseInt(tagTypeStr))){
//							String tagIdStr = tagIdList.get(i);
//							String[] tagIdsStr = tagIdStr.split(":");
//							String tagId0Str = tagIdsStr[0];
//							//tagId0Str="BrTJgfab_0";
//							String[] tagId0Strs = tagId0Str.split("_");							
//							criteria = criteria.and("tag_list")
//									.elemMatch(Criteria.where("tagId").is(tagId0Strs[0]).and("value").is(tagIdsStr[2]));
//						
//						}else{
//							criteria = criteria.and("custom_tag_list")
//									.elemMatch(Criteria.where("custom_tag_id").is(tagIdList.get(i)));
//						}
//					}else{
//						criteria = criteria.and("custom_tag_list")
//								.elemMatch(Criteria.where("custom_tag_id").is(tagIdList.get(i)));
//					}					
//					List<DataParty> dpListM1 = mongoTemplate.find(new Query(criteria), DataParty.class);
//					if(CollectionUtils.isEmpty(dpListM1)) {
//						isAllMatch = false;
//						break;
//					}
                    Integer dataId = s.getDataId();
                    if (CollectionUtils.isNotEmpty(tagTypesStrList)) {
                        String tagTypeStr = tagTypesStrList.get(i);
                        if (CampaignTagTypeEnum.CAMPAIGN_TAG_TYPE_SYSTEM.getCode().equals(
                                Integer.parseInt(tagTypeStr))) {
                            String tagIdStr = tagIdList.get(i);
                            String[] tagIdsStr = tagIdStr.split(":");
                            // tagId0Str="BrTJgfab_0";
                            if (!isTagCovered(dataId, tagIdsStr[0])) {
                                isAllMatch = false;
                                break;
                            }
                        } else {
                            //自定义标签的处理逻辑被移除
                        }
                    } else {
                        //自定义标签的处理逻辑被移除
                    }
                }
				if(isAllMatch) {
					segmentListToMqYes.add(s);
				}else{
					segmentListToMqNo.add(s);
				}
				break;
			//匹配其一
			case ApiConstant.CAMPAIGN_DECISION_TAG_RULE_MATCH_ONE:
				boolean isMatchOne = false;
				for(int i=0;i<tagIdList.size();i++){
//					Integer dataId = s.getDataId();
//					Criteria criteria = Criteria.where("mid").is(dataId);
//					if(CollectionUtils.isNotEmpty(tagTypesStrList)){
//						String tagTypeStr = tagTypesStrList.get(i);
//						if(CampaignTagTypeEnum.CAMPAIGN_TAG_TYPE_SYSTEM.getCode().equals(Integer.parseInt(tagTypeStr))){
//							String tagIdStr = tagIdList.get(i);
//							String[] tagIdsStr = tagIdStr.split(":");
//							String tagId0Str = tagIdsStr[0];
//							//tagId0Str="BrTJgfab_0";
//							String[] tagId0Strs = tagId0Str.split("_");							
//							criteria = criteria.and("tag_list")
//									.elemMatch(Criteria.where("tagId").is(tagId0Strs[0]).and("value").is(tagIdsStr[2]));
//						}else{
//							criteria = criteria.and("custom_tag_list")
//									.elemMatch(Criteria.where("custom_tag_id").is(tagIdList.get(i)));
//						}
//					}else{
//						criteria = criteria.and("custom_tag_list")
//								.elemMatch(Criteria.where("custom_tag_id").is(tagIdList.get(i)));
//
//					}					
//					List<DataParty> dpListM1 = mongoTemplate.find(new Query(criteria), DataParty.class);
//					if(CollectionUtils.isNotEmpty(dpListM1)) {
//						isMatchOne = true;
//						break;
//					}
				    
                    Integer dataId = s.getDataId();
                    if (CollectionUtils.isNotEmpty(tagTypesStrList)) {
                        String tagTypeStr = tagTypesStrList.get(i);
                        if (CampaignTagTypeEnum.CAMPAIGN_TAG_TYPE_SYSTEM.getCode().equals(
                                Integer.parseInt(tagTypeStr))) {
                            String tagIdStr = tagIdList.get(i);
                            String[] tagIdsStr = tagIdStr.split(":");
                            // tagId0Str="BrTJgfab_0";
                            if (isTagCovered(dataId, tagIdsStr[0])) {
                                isMatchOne = true;
                                break;
                            }
                        } else {
                            // 自定义标签的处理逻辑被移除
                        }
                    } else {
                        // 自定义标签的处理逻辑被移除
                    }                
				}
				if(isMatchOne) {
					segmentListToMqYes.add(s);
				}else{
					segmentListToMqNo.add(s);
				}
				break;
			//匹配2个及以上
			case ApiConstant.CAMPAIGN_DECISION_TAG_RULE_MATCH_TWO_MORE:
				int matchCount = 0;
				boolean matchTwoMore = false;
				for(int i=0;i<tagIdList.size();i++){
//					Integer dataIdStr3 = s.getDataId();
//					Criteria criteria3 = Criteria.where("mid").is(dataIdStr3);
//					if(CollectionUtils.isNotEmpty(tagTypesStrList)){
//						String tagTypeStr = tagTypesStrList.get(i);
//						if(CampaignTagTypeEnum.CAMPAIGN_TAG_TYPE_SYSTEM.getCode().equals(Integer.parseInt(tagTypeStr))){
//							String tagIdStr = tagIdList.get(i);
//							String[] tagIdsStr = tagIdStr.split(":");
//							String tagId0Str = tagIdsStr[0];
//							//tagId0Str="BrTJgfab_0";
//							String[] tagId0Strs = tagId0Str.split("_");							
//							criteria3 = criteria3.and("tag_list")
//									.elemMatch(Criteria.where("tagId").is(tagId0Strs[0]).and("value").is(tagIdsStr[2]));
//
//						}else{
//							criteria3 = criteria3.and("custom_tag_list")
//									.elemMatch(Criteria.where("custom_tag_id").is(tagIdList.get(i)));
//						}
//					}else{
//						criteria3 = criteria3.and("custom_tag_list")
//								.elemMatch(Criteria.where("custom_tag_id").is(tagIdList.get(i)));
//					}
//					
//					List<DataParty> dpListM1 = mongoTemplate.find(new Query(criteria3), DataParty.class);
//					if(CollectionUtils.isNotEmpty(dpListM1)) {
//						++matchCount;
//					}
//					if(matchCount >= 2) {
//						matchTwoMore = true;
//						break;
//					}
                    Integer dataId = s.getDataId();
                    if (CollectionUtils.isNotEmpty(tagTypesStrList)) {
                        String tagTypeStr = tagTypesStrList.get(i);
                        if (CampaignTagTypeEnum.CAMPAIGN_TAG_TYPE_SYSTEM.getCode().equals(
                                Integer.parseInt(tagTypeStr))) {
                            String tagIdStr = tagIdList.get(i);
                            String[] tagIdsStr = tagIdStr.split(":");
                            // tagId0Str="BrTJgfab_0";
                            if (isTagCovered(dataId, tagIdsStr[0])) {
                                ++matchCount;
                            }
                            if (matchCount >= 2) {
                                matchTwoMore = true;
                                break;
                            }
                        } else {
                            // 自定义标签的处理逻辑被移除
                        }
                    } else {
                        // 自定义标签的处理逻辑被移除
                    }
				}
				if(matchTwoMore) {
					segmentListToMqYes.add(s);
				}else{
					segmentListToMqNo.add(s);
				}
				break;
			}
		}
		if(CollectionUtils.isNotEmpty(campaignSwitchYesList)) {
			CampaignSwitch csYes = campaignSwitchYesList.get(0);
			sendDynamicQueue(segmentListToMqYes, csYes.getCampaignHeadId() +"-"+csYes.getNextItemId());
			logger.info(queueKey+"-out-yes:"+JSON.toJSONString(segmentListToMqYes));
		}
		if(CollectionUtils.isNotEmpty(campaignSwitchNoList)) {
			CampaignSwitch csNo = campaignSwitchNoList.get(0);
			sendDynamicQueue(segmentListToMqNo, csNo.getCampaignHeadId() +"-"+csNo.getNextItemId());
			logger.info(queueKey+"-out-no:"+JSON.toJSONString(segmentListToMqNo));
		}
	}
	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		super.cancelInnerTask(taskSchedule);
	}

	@Override
	public void task(Integer taskId) {
	}
	
	
    private boolean isTagCovered(Integer mid, String tagSeq) {
        if (mid == null || StringUtils.isBlank(tagSeq)) {
            return false;
        }
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = JedisConnectionManager.getConnection();
            jedis.select(2);
            result = jedis.sismember("tagcoverid:" + tagSeq, mid.toString());
        } catch (Exception e) {
            logger.info("redis Exception", e);
        } finally {
            if (jedis != null) {
                JedisConnectionManager.closeConnection(jedis);
            }
        }
        return result;
    }
	
    public static void main(String[] args) {
    	String tagTypesStr = "1,2,3,4";
		List<String> tagTypesStrListTemp = null;
		if(StringUtils.isNotEmpty(tagTypesStr)){
			tagTypesStrListTemp = Arrays.asList(tagTypesStr);
			String[] tagTypesStrListTemp1 = tagTypesStr.split(",");
			tagTypesStrListTemp = Arrays.asList(tagTypesStrListTemp1);
		}
    }
}
