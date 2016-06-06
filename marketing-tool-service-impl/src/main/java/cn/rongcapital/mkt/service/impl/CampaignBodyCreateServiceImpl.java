package cn.rongcapital.mkt.service.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignActionSaveAudienceDao;
import cn.rongcapital.mkt.dao.CampaignActionSendH5Dao;
import cn.rongcapital.mkt.dao.CampaignActionSendPrivtDao;
import cn.rongcapital.mkt.dao.CampaignActionSendPubDao;
import cn.rongcapital.mkt.dao.CampaignActionSetTagDao;
import cn.rongcapital.mkt.dao.CampaignActionWaitDao;
import cn.rongcapital.mkt.dao.CampaignAssetRelationDao;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPropCompareDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPrvtFriendsDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPubFansDao;
import cn.rongcapital.mkt.dao.CampaignDecisionTagDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatForwardDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatReadDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatSentDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.CampaignTriggerTimerDao;
import cn.rongcapital.mkt.po.CampaignActionWait;
import cn.rongcapital.mkt.po.CampaignAudienceTarget;
import cn.rongcapital.mkt.po.CampaignBody;
import cn.rongcapital.mkt.po.CampaignDecisionPropCompare;
import cn.rongcapital.mkt.po.CampaignDecisionPrvtFriends;
import cn.rongcapital.mkt.po.CampaignDecisionPubFans;
import cn.rongcapital.mkt.po.CampaignDecisionTag;
import cn.rongcapital.mkt.po.CampaignDecisionWechatForward;
import cn.rongcapital.mkt.po.CampaignDecisionWechatRead;
import cn.rongcapital.mkt.po.CampaignDecisionWechatSent;
import cn.rongcapital.mkt.po.CampaignTriggerTimer;
import cn.rongcapital.mkt.service.CampaignBodyCreateService;
import cn.rongcapital.mkt.vo.in.CampaignActionWaitIn;
import cn.rongcapital.mkt.vo.in.CampaignAudienceTargetIn;
import cn.rongcapital.mkt.vo.in.CampaignBodyCreateIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionPropCompareIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionPrvtFriendsIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionPubFansIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionTagIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionWechatForwardIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionWechatReadIn;
import cn.rongcapital.mkt.vo.in.CampaignDecisionWechatSentIn;
import cn.rongcapital.mkt.vo.in.CampaignTriggerTimerIn;
import cn.rongcapital.mkt.vo.out.CampaignBodyOut;

@Service
public class CampaignBodyCreateServiceImpl implements CampaignBodyCreateService {

	@Autowired
	CampaignHeadDao campaignHeadDao;
	@Autowired
	CampaignBodyDao campaignBodyDao;
	@Autowired
	CampaignTriggerTimerDao campaignTriggerTimerDao;
	@Autowired
	CampaignActionSaveAudienceDao campaignActionSaveAudienceDao;
	@Autowired
	CampaignActionSendH5Dao campaignActionSendH5Dao;
	@Autowired
	CampaignActionSendPrivtDao campaignActionSendPrivtDao;
	@Autowired
	CampaignActionSendPubDao campaignActionSendPubDao;
	@Autowired
	CampaignActionSetTagDao campaignActionSetTagDao;
	@Autowired
	CampaignActionWaitDao campaignActionWaitDao;
	@Autowired
	CampaignAssetRelationDao campaignAssetRelationDao;
	@Autowired
	cn.rongcapital.mkt.dao.CampaignAudienceTargetDao CampaignAudienceTargetDao;
	@Autowired
	CampaignDecisionPropCompareDao campaignDecisionPropCompareDao;
	@Autowired
	CampaignDecisionPrvtFriendsDao campaignDecisionPrvtFriendsDao;
	@Autowired
	CampaignDecisionPubFansDao campaignDecisionPubFansDao;
	@Autowired
	CampaignDecisionTagDao campaignDecisionTagDao;
	@Autowired
	CampaignDecisionWechatForwardDao campaignDecisionWechatForwardDao;
	@Autowired
	CampaignDecisionWechatReadDao campaignDecisionWechatReadDao;
	@Autowired
	CampaignDecisionWechatSentDao campaignDecisionWechatSentDao;

	@Override
	public CampaignBodyOut campaignBodyCreate(CampaignBodyCreateIn body, SecurityContext securityContext) {
		CampaignBody campaignBody = initCampaignBody(body);
		if(body.getNodeType() == 0){
			switch (body.getItemType()) {
			case 0://定时触发
				CampaignTriggerTimer campaignTriggerTimer = initCampaignTriggerTimer(body);
				campaignTriggerTimerDao.insert(campaignTriggerTimer);
				break;
			}
		}
		if(body.getNodeType() == 1){
			switch (body.getItemType()) {
			case 0://目标人群
				CampaignAudienceTarget campaignAudienceTarget = initCampaignAudienceTarget(body);
				CampaignAudienceTargetDao.insert(campaignAudienceTarget);
				break;
			}
		}
		if(body.getNodeType() == 2){
			switch (body.getItemType()) {
			case 0://联系人属性比较
				CampaignDecisionPropCompare campaignDecisionPropCompare = initCampaignDecisionPropCompare(body);
				campaignDecisionPropCompareDao.insert(campaignDecisionPropCompare);
				break;
			case 1://微信图文是否发送
				CampaignDecisionWechatSent campaignDecisionWechatSent = initCampaignDecisionWechatSent(body);
				campaignDecisionWechatSentDao.insert(campaignDecisionWechatSent);
				break;
			case 2://微信图文是否查看
				CampaignDecisionWechatRead campaignDecisionWechatRead = initCampaignDecisionWechatRead(body);
				campaignDecisionWechatReadDao.insert(campaignDecisionWechatRead);
				break;
			case 3://微信图文是否转发
				CampaignDecisionWechatForward campaignDecisionWechatForward = initCampaignDecisionWechatForward(body);
				campaignDecisionWechatForwardDao.insert(campaignDecisionWechatForward);
				break;
			case 4://是否订阅公众号
				CampaignDecisionPubFans campaignDecisionPubFans = initCampaignDecisionPubFans(body);
				campaignDecisionPubFansDao.insert(campaignDecisionPubFans);
				break;
			case 5://是否个人号好友
				CampaignDecisionPrvtFriends campaignDecisionPrvtFriends = initCampaignDecisionPrvtFriends(body);
				campaignDecisionPrvtFriendsDao.insert(campaignDecisionPrvtFriends);
				break;
			case 6://标签判断
				CampaignDecisionTag campaignDecisionTag = initCampaignDecisionTag(body);
				campaignDecisionTagDao.insert(campaignDecisionTag);
				break;
	        }
		}
		if(body.getNodeType() == 3){
			switch (body.getItemType()) {
			case 0://等待
				CampaignActionWait campaignActionWait = initCampaignActionWait(body);
				campaignActionWaitDao.insert(campaignActionWait);
				break;
			case 1://保存当前人群
				break;
			case 2://设置标签
				break;
			case 3://添加到其它活动
				break;
			case 4://转移到其它活动
				break;
			case 5://发送微信图文
				break;
			case 6://发送H5图文
				break;
			case 7://发送个人号消息
				break;
	        }
		}
		campaignBodyDao.insert(campaignBody);
		CampaignBodyOut out = new CampaignBodyOut(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		return out;
	}

	private CampaignActionWait initCampaignActionWait(CampaignBodyCreateIn body) {
		CampaignActionWait campaignActionWait = new CampaignActionWait();
		CampaignActionWaitIn campaignActionWaitIn = (CampaignActionWaitIn)body.getInfo();
		campaignActionWait.setName(campaignActionWaitIn.getName());
		campaignActionWait.setItemId(campaignActionWaitIn.getItemId());
		campaignActionWait.setCampaignHeadId(campaignActionWaitIn.getCampaignHeadId());
		campaignActionWait.setType(campaignActionWaitIn.getType());
		campaignActionWait.setRelativeValue(campaignActionWaitIn.getRelativeValue());
		campaignActionWait.setRelativeType(campaignActionWaitIn.getRelativeType());
		campaignActionWait.setSpecificTime(campaignActionWait.getSpecificTime());
		return campaignActionWait;
	}
	
	private CampaignDecisionTag initCampaignDecisionTag(CampaignBodyCreateIn body) {
		CampaignDecisionTag campaignDecisionTag = new CampaignDecisionTag();
		CampaignDecisionTagIn campaignDecisionTagIn = (CampaignDecisionTagIn)body.getInfo();
		campaignDecisionTag.setName(campaignDecisionTagIn.getName());
		campaignDecisionTag.setItemId(campaignDecisionTagIn.getItemId());
		campaignDecisionTag.setCampaignHeadId(campaignDecisionTagIn.getCampaignHeadId());
		campaignDecisionTag.setTagIds(campaignDecisionTagIn.getTagIds());
		campaignDecisionTag.setTagNames(campaignDecisionTagIn.getTagNames());
		return campaignDecisionTag;
	}
	
	private CampaignDecisionPrvtFriends initCampaignDecisionPrvtFriends(CampaignBodyCreateIn body) {
		CampaignDecisionPrvtFriends campaignDecisionPrvtFriends = new CampaignDecisionPrvtFriends();
		CampaignDecisionPrvtFriendsIn campaignDecisionPrvtFriendsIn = (CampaignDecisionPrvtFriendsIn)body.getInfo();
		campaignDecisionPrvtFriends.setName(campaignDecisionPrvtFriendsIn.getName());
		campaignDecisionPrvtFriends.setItemId(campaignDecisionPrvtFriendsIn.getItemId());
		campaignDecisionPrvtFriends.setCampaignHeadId(campaignDecisionPrvtFriendsIn.getCampaignHeadId());
		campaignDecisionPrvtFriends.setPrvtId(campaignDecisionPrvtFriendsIn.getPrvtId());
		campaignDecisionPrvtFriends.setPrvtName(campaignDecisionPrvtFriendsIn.getPrvtName());
		campaignDecisionPrvtFriends.setGroupName(campaignDecisionPrvtFriendsIn.getGroupName());
		campaignDecisionPrvtFriends.setRefreshInterval(campaignDecisionPrvtFriendsIn.getRefreshInterval());
		campaignDecisionPrvtFriends.setRefreshIntervalType(campaignDecisionPrvtFriendsIn.getRefreshIntervalType());
		return campaignDecisionPrvtFriends;
	}
	
	private CampaignDecisionPubFans initCampaignDecisionPubFans(CampaignBodyCreateIn body) {
		CampaignDecisionPubFans campaignDecisionPubFans = new CampaignDecisionPubFans();
		CampaignDecisionPubFansIn campaignDecisionPubFansIn = (CampaignDecisionPubFansIn)body.getInfo();
		campaignDecisionPubFans.setName(campaignDecisionPubFansIn.getName());
		campaignDecisionPubFans.setItemId(campaignDecisionPubFansIn.getItemId());
		campaignDecisionPubFans.setCampaignHeadId(campaignDecisionPubFansIn.getCampaignHeadId());
		campaignDecisionPubFans.setPubId(campaignDecisionPubFansIn.getPubId());
		campaignDecisionPubFans.setPubName(campaignDecisionPubFansIn.getPubName());
		campaignDecisionPubFans.setSubscribeTime(campaignDecisionPubFansIn.getSubscribeTime());
		campaignDecisionPubFans.setRefreshInterval(campaignDecisionPubFansIn.getRefreshInterval());
		campaignDecisionPubFans.setRefreshIntervalType(campaignDecisionPubFansIn.getRefreshIntervalType());
		return campaignDecisionPubFans;
	}
	
	private CampaignDecisionWechatForward initCampaignDecisionWechatForward(CampaignBodyCreateIn body) {
		CampaignDecisionWechatForward campaignDecisionWechatForward = new CampaignDecisionWechatForward();
		CampaignDecisionWechatForwardIn campaignDecisionWechatForwardIn = (CampaignDecisionWechatForwardIn)body.getInfo();
		campaignDecisionWechatForward.setName(campaignDecisionWechatForwardIn.getName());
		campaignDecisionWechatForward.setItemId(campaignDecisionWechatForwardIn.getItemId());
		campaignDecisionWechatForward.setCampaignHeadId(campaignDecisionWechatForwardIn.getCampaignHeadId());
		campaignDecisionWechatForward.setPubId(campaignDecisionWechatForwardIn.getPubId());
		campaignDecisionWechatForward.setPubName(campaignDecisionWechatForwardIn.getPubName());
		campaignDecisionWechatForward.setRefreshInterval(campaignDecisionWechatForwardIn.getRefreshInterval());
		campaignDecisionWechatForward.setRefreshIntervalType(campaignDecisionWechatForwardIn.getRefreshIntervalType());
		campaignDecisionWechatForward.setWechatH5Id(campaignDecisionWechatForwardIn.getWechatH5Id());
		campaignDecisionWechatForward.setWechatH5Name(campaignDecisionWechatForwardIn.getWechatH5Name());
		campaignDecisionWechatForward.setForwardTimes(campaignDecisionWechatForwardIn.getForwardTimes());
		return campaignDecisionWechatForward;
	}
	
	private CampaignDecisionWechatRead initCampaignDecisionWechatRead(CampaignBodyCreateIn body) {
		CampaignDecisionWechatRead campaignDecisionWechatRead = new  CampaignDecisionWechatRead();
		CampaignDecisionWechatReadIn campaignDecisionWechatReadIn = (CampaignDecisionWechatReadIn)body.getInfo();
		campaignDecisionWechatRead.setName(campaignDecisionWechatReadIn.getName());
		campaignDecisionWechatRead.setItemId(campaignDecisionWechatReadIn.getItemId());
		campaignDecisionWechatRead.setCampaignHeadId(campaignDecisionWechatReadIn.getCampaignHeadId());
		campaignDecisionWechatRead.setPubId(campaignDecisionWechatReadIn.getPubId());
		campaignDecisionWechatRead.setPubName(campaignDecisionWechatReadIn.getPubName());
		campaignDecisionWechatRead.setRefreshInterval(campaignDecisionWechatReadIn.getRefreshInterval());
		campaignDecisionWechatRead.setRefreshIntervalType(campaignDecisionWechatReadIn.getRefreshIntervalType());
		campaignDecisionWechatRead.setWechatH5Id(campaignDecisionWechatReadIn.getWechatH5Id());
		campaignDecisionWechatRead.setWechatH5Name(campaignDecisionWechatReadIn.getWechatH5Name());
		campaignDecisionWechatRead.setReadTime(campaignDecisionWechatReadIn.getReadTime());
		campaignDecisionWechatRead.setReadPercent(campaignDecisionWechatReadIn.getReadPercent());
		return campaignDecisionWechatRead;
	}
	
	private CampaignDecisionWechatSent initCampaignDecisionWechatSent(CampaignBodyCreateIn body) {
		CampaignDecisionWechatSent campaignDecisionWechatSent = new  CampaignDecisionWechatSent();
		CampaignDecisionWechatSentIn campaignDecisionWechatSentIn = (CampaignDecisionWechatSentIn)body.getInfo();
		campaignDecisionWechatSent.setName(campaignDecisionWechatSentIn.getName());
		campaignDecisionWechatSent.setItemId(campaignDecisionWechatSentIn.getItemId());
		campaignDecisionWechatSent.setCampaignHeadId(campaignDecisionWechatSentIn.getCampaignHeadId());
		campaignDecisionWechatSent.setPubId(campaignDecisionWechatSentIn.getPubId());
		campaignDecisionWechatSent.setPubName(campaignDecisionWechatSentIn.getPubName());
		campaignDecisionWechatSent.setRefreshInterval(campaignDecisionWechatSentIn.getRefreshInterval());
		campaignDecisionWechatSent.setRefreshIntervalType(campaignDecisionWechatSentIn.getRefreshIntervalType());
		campaignDecisionWechatSent.setWechatH5Id(campaignDecisionWechatSentIn.getWechatH5Id());
		campaignDecisionWechatSent.setWechatH5Name(campaignDecisionWechatSent.getWechatH5Name());
		return campaignDecisionWechatSent;
	}
	
	private CampaignDecisionPropCompare initCampaignDecisionPropCompare(CampaignBodyCreateIn body) {
		CampaignDecisionPropCompare campaignDecisionPropCompare = new  CampaignDecisionPropCompare();
		CampaignDecisionPropCompareIn campaignDecisionPropCompareIn = (CampaignDecisionPropCompareIn)body.getInfo();
		campaignDecisionPropCompare.setName(campaignDecisionPropCompareIn.getName());
		campaignDecisionPropCompare.setItemId(campaignDecisionPropCompareIn.getItemId());
		campaignDecisionPropCompare.setCampaignHeadId(campaignDecisionPropCompareIn.getCampaignHeadId());
		campaignDecisionPropCompare.setPropType(campaignDecisionPropCompareIn.getPropType());
		campaignDecisionPropCompare.setRule(campaignDecisionPropCompareIn.getRule());
		return campaignDecisionPropCompare;
	}
	
	private CampaignBody initCampaignBody(CampaignBodyCreateIn body) {
		CampaignBody campaignBody = new CampaignBody();
		campaignBody.setHeadId(body.getCampaignHeadId());
		campaignBody.setNodeType(body.getNodeType());
		campaignBody.setItemType(body.getItemType());
		campaignBody.setItemId(body.getIntemId());
		campaignBody.setNextItemId(body.getNextItemId());
		campaignBody.setNextNodeType(body.getNextNodeType());
		campaignBody.setNextItemType(body.getNextItemType());
		campaignBody.setPosX(body.getPosX());
		campaignBody.setPosY(body.getPoxY());
		return campaignBody;
	}
	
	private CampaignTriggerTimer initCampaignTriggerTimer(CampaignBodyCreateIn body) {
		CampaignTriggerTimer campaignTriggerTimer = new CampaignTriggerTimer();
		CampaignTriggerTimerIn campaignTriggerTimerIn = (CampaignTriggerTimerIn)body.getInfo();
		campaignTriggerTimer.setCampaignHeadId(body.getCampaignHeadId());
		campaignTriggerTimer.setName(campaignTriggerTimerIn.getName());
		campaignTriggerTimer.setItemId(campaignTriggerTimerIn.getItemId());
		campaignTriggerTimer.setStartTime(campaignTriggerTimerIn.getStartTime());
		campaignTriggerTimer.setEndTime(campaignTriggerTimerIn.getEndTime());
		return campaignTriggerTimer;
	}
	
	private CampaignAudienceTarget initCampaignAudienceTarget(CampaignBodyCreateIn body) {
		CampaignAudienceTarget campaignAudienceTarget = new CampaignAudienceTarget();
		CampaignAudienceTargetIn campaignAudienceTargetIn = (CampaignAudienceTargetIn)body.getInfo();
		campaignAudienceTarget.setName(campaignAudienceTargetIn.getName());
		campaignAudienceTarget.setCampaignHeadId(campaignAudienceTargetIn.getCampaignHeadId());
		campaignAudienceTarget.setItemId(campaignAudienceTargetIn.getItemId());
		campaignAudienceTarget.setSegmentationId(campaignAudienceTargetIn.getSegmentationId());
		campaignAudienceTarget.setSegmentationName(campaignAudienceTargetIn.getSegmentationName());
		campaignAudienceTarget.setAllowedNew(campaignAudienceTargetIn.getAllowedNew());
		campaignAudienceTarget.setRefreshInterval(campaignAudienceTargetIn.getRefreshInterval());
		campaignAudienceTarget.setRefreshIntervalType(campaignAudienceTargetIn.getRefreshIntervalType());
		return campaignAudienceTarget;
	}
}
