package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignDecisionPubFansDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignDecisionPubFans;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignDecisionWechatSubscribeTask extends BaseMQService implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignDecisionPubFansDao campaignDecisionPubFansDao;
	@Autowired
	private WechatMemberDao wechatMemberDao;
	
	public void task (TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		List<CampaignSwitch> campaignSwitchYesList = queryCampaignSwitchYesList(campaignHeadId, itemId);
		List<CampaignSwitch> campaignSwitchNoList = queryCampaignSwitchNoList(campaignHeadId, itemId);
		if(CollectionUtils.isEmpty(campaignSwitchYesList) && 
		   CollectionUtils.isEmpty(campaignSwitchNoList)) {
			logger.info("没有后续节点,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignDecisionPubFans campaignDecisionPubFansT = new CampaignDecisionPubFans();
		campaignDecisionPubFansT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignDecisionPubFansT.setCampaignHeadId(campaignHeadId);
		campaignDecisionPubFansT.setItemId(itemId);
		List<CampaignDecisionPubFans> campaignDecisionPubFansList = campaignDecisionPubFansDao.selectList(campaignDecisionPubFansT);
		if(CollectionUtils.isEmpty(campaignDecisionPubFansList) ||
		   StringUtils.isBlank(campaignDecisionPubFansList.get(0).getPubId())) {
			logger.error("没有设置决策条件,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignDecisionPubFans campaignDecisionPubFans = campaignDecisionPubFansList.get(0);
		String pubId = campaignDecisionPubFans.getPubId();
		String currentQueueName = campaignHeadId + "-" +itemId;
		Queue queue = getDynamicQueue(currentQueueName);
		List<Segment> segmentList = getQueueData(queue);
		if(CollectionUtils.isNotEmpty(segmentList)) {
			List<Segment> segmentListToNextYes = new ArrayList<Segment>();//要传递给下面节点的数据:是粉丝
			List<Segment> segmentListToNextNo = new ArrayList<Segment>();//要传递给下面节点的数据:不是粉丝
			for(Segment segment:segmentList) {
				boolean isFans = false;
				DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(segment.getDataId())), 
													 DataParty.class);
				if(null != dp && StringUtils.isNotBlank(dp.getMdType()) &&
						   StringUtils.isNotBlank(dp.getMappingKeyid()) &&
						   dp.getMdType().equals(ApiConstant.DATA_PARTY_MD_TYPE_WECHAT+"")) {
					String fanOpenId = dp.getMappingKeyid();
					WechatMember wechatMemberT = new WechatMember();
					wechatMemberT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
					wechatMemberT.setWxCode(fanOpenId);
					wechatMemberT.setPubId(pubId);
					List<WechatMember> wechatMemberList = wechatMemberDao.selectList(wechatMemberT);
					if(CollectionUtils.isNotEmpty(wechatMemberList)) {
						Byte subscribeTimeType = campaignDecisionPubFans.getSubscribeTime();
						if(null == subscribeTimeType) {//为空表示不限订阅时间
							isFans = true;
						} else {
							String realSubscribeTime = wechatMemberList.get(0).getSubscribeTime();
							isFans = checkSubscriberTime(subscribeTimeType,realSubscribeTime);
						}
					}
				}
				if(isFans) {
					segmentListToNextYes.add(segment);
				}else{
					segmentListToNextNo.add(segment);
				}
			}
			if(CollectionUtils.isNotEmpty(campaignSwitchYesList)) {
				CampaignSwitch csYes = campaignSwitchYesList.get(0);
				sendDynamicQueue(segmentListToNextYes, csYes.getCampaignHeadId() +"-"+csYes.getNextItemId());
			}
			if(CollectionUtils.isNotEmpty(campaignSwitchNoList)) {
				CampaignSwitch csNo = campaignSwitchNoList.get(0);
				sendDynamicQueue(segmentListToNextNo, csNo.getCampaignHeadId() +"-"+csNo.getNextItemId());
			} else {
				//如果没有非分支，则MQ数据发送给本节点，供本节点下一次刷新的时候再次检测
				sendDynamicQueue(segmentListToNextNo, campaignHeadId +"-"+itemId);
			}
		}
		
	}
	
	private boolean checkSubscriberTime(byte subscribeTimeType,String realSubscribeTime) {
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
	
	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}

}
