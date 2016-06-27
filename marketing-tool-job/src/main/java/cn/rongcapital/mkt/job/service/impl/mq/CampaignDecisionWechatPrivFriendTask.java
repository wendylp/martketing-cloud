package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignDecisionPrvtFriendsDao;
import cn.rongcapital.mkt.dao.WechatGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignDecisionPrvtFriends;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.WechatGroup;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignDecisionWechatPrivFriendTask extends BaseMQService implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignDecisionPrvtFriendsDao campaignDecisionPrvtFriendsDao;
	@Autowired
	private WechatMemberDao wechatMemberDao;
	@Autowired
	private WechatGroupDao wechatGroupDao;
	
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
		CampaignDecisionPrvtFriends campaignDecisionPrvtFriendsT = new CampaignDecisionPrvtFriends();
		campaignDecisionPrvtFriendsT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignDecisionPrvtFriendsT.setCampaignHeadId(campaignHeadId);
		campaignDecisionPrvtFriendsT.setItemId(itemId);
		List<CampaignDecisionPrvtFriends> campaignDecisionPrvtFriendsList = campaignDecisionPrvtFriendsDao.selectList(campaignDecisionPrvtFriendsT);
		if(CollectionUtils.isEmpty(campaignDecisionPrvtFriendsList) || 
		   null == campaignDecisionPrvtFriendsList.get(0).getAssetId()) {
			logger.error("没有设置决策条件,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignDecisionPrvtFriends campaignDecisionPrvFans = campaignDecisionPrvtFriendsList.get(0);
		String uin = campaignDecisionPrvFans.getUin();
		String currentQueueName = campaignHeadId + "-" +itemId;
		Queue queue = getDynamicQueue(currentQueueName);
		List<Segment> segmentList = getQueueData(queue);
		if(CollectionUtils.isNotEmpty(segmentList)) {
			List<Segment> segmentListToNextYes = new ArrayList<Segment>();//要传递给下面节点的数据:是好友
			List<Segment> segmentListToNextNo = new ArrayList<Segment>();//要传递给下面节点的数据:不是好友
			for(Segment segment:segmentList) {
				boolean isFriend = false;
				DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(segment.getDataId())), DataParty.class);
				if(null != dp && StringUtils.isNotBlank(dp.getMdType()) &&
				   StringUtils.isNotBlank(dp.getMappingKeyid()) &&
				   dp.getMdType().equals(ApiConstant.DATA_PARTY_MD_TYPE_WECHAT+"")) {
					
					String friendUcode = dp.getMappingKeyid();
					WechatMember wechatMemberT = new WechatMember();
					wechatMemberT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
					wechatMemberT.setWxCode(friendUcode);
					wechatMemberT.setUin(uin);
					List<WechatMember> wechatMemberList = wechatMemberDao.selectList(wechatMemberT);
					if(CollectionUtils.isNotEmpty(wechatMemberList)) {
						String groupUcode = campaignDecisionPrvFans.getUcode();
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
					if(isFriend) {
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
	
	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}

}
