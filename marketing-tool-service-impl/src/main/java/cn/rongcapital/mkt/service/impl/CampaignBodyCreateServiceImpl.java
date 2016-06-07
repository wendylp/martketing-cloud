package cn.rongcapital.mkt.service.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.AudienceListDao;
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
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.CampaignActionSaveAudience;
import cn.rongcapital.mkt.po.CampaignActionSendH5;
import cn.rongcapital.mkt.po.CampaignActionSendPrivt;
import cn.rongcapital.mkt.po.CampaignActionSendPub;
import cn.rongcapital.mkt.po.CampaignActionSetTag;
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
import cn.rongcapital.mkt.vo.in.CampaignActionSaveAudienceIn;
import cn.rongcapital.mkt.vo.in.CampaignActionSendH5In;
import cn.rongcapital.mkt.vo.in.CampaignActionSendPrivtIn;
import cn.rongcapital.mkt.vo.in.CampaignActionSendPubIn;
import cn.rongcapital.mkt.vo.in.CampaignActionSetTagIn;
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
import cn.rongcapital.mkt.vo.out.CampaignBodyCreateOut;

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
	@Autowired
	AudienceListDao audienceListDao;

	@Override
	public CampaignBodyCreateOut campaignBodyCreate(CampaignBodyCreateIn body, SecurityContext securityContext) {
		CampaignBody campaignBody = initCampaignBody(body);
		if(body.getCampaignNodeChain().getNodeType() == 0){
			switch (body.getCampaignNodeChain().getItemType()) {
			case 0://定时触发
				CampaignTriggerTimer campaignTriggerTimer = initCampaignTriggerTimer(body);
				campaignTriggerTimerDao.insert(campaignTriggerTimer);
				break;
			}
		}
		if(body.getCampaignNodeChain().getNodeType() == 1){
			switch (body.getCampaignNodeChain().getItemType()) {
			case 0://目标人群
				CampaignAudienceTarget campaignAudienceTarget = initCampaignAudienceTarget(body);
				CampaignAudienceTargetDao.insert(campaignAudienceTarget);
				break;
			}
		}
		if(body.getCampaignNodeChain().getNodeType() == 2){
			switch (body.getCampaignNodeChain().getItemType()) {
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
		if(body.getCampaignNodeChain().getNodeType() == 3){
			switch (body.getCampaignNodeChain().getItemType()) {
			case 0://等待
				CampaignActionWait campaignActionWait = initCampaignActionWait(body);
				campaignActionWaitDao.insert(campaignActionWait);
				break;
			case 1://保存当前人群
				CampaignActionSaveAudience campaignActionSaveAudience = initCampaignActionSaveAudience(body);
				//如果audience_id参数为空,则在audience_list表增加1条记录
				if(campaignActionSaveAudience.getAudienceId() == null){
					AudienceList audienceList = new AudienceList();
					audienceList.setAudienceName(campaignActionSaveAudience.getName());
					audienceListDao.insert(audienceList);
				} else {
					//TO DO:检查audience_list表里是否存在audience_id的记录?
				}
				campaignActionSaveAudienceDao.insert(campaignActionSaveAudience);
				break;
			case 2://设置标签
				CampaignActionSetTag campaignActionSetTag = initCampaignActionSetTag(body);
				campaignActionSetTagDao.insert(campaignActionSetTag);
				break;
			case 3://添加到其它活动
				break;
			case 4://转移到其它活动
				break;
			case 5://发送微信图文
				CampaignActionSendPub campaignActionSendPub = initCampaignActionSendPub(body);
				campaignActionSendPubDao.insert(campaignActionSendPub);
				break;
			case 6://发送H5图文
				CampaignActionSendH5 campaignActionSendH5 = initCampaignActionSendH5(body);
				campaignActionSendH5Dao.insert(campaignActionSendH5);
				break;
			case 7://发送个人号消息
				CampaignActionSendPrivt campaignActionSendPrivt = initCampaignActionSendPrivt(body);
				campaignActionSendPrivtDao.insert(campaignActionSendPrivt);
				break;
	        }
		}
		campaignBodyDao.insert(campaignBody);
		CampaignBodyCreateOut out = new CampaignBodyCreateOut(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		return out;
	}

	private CampaignActionSendPrivt initCampaignActionSendPrivt(CampaignBodyCreateIn body) {
		CampaignActionSendPrivt campaignActionSendPrivt = new CampaignActionSendPrivt();
		CampaignActionSendPrivtIn campaignActionSendPrivtIn = (CampaignActionSendPrivtIn)body.getCampaignNodeChain().getInfo();
		campaignActionSendPrivt.setName(campaignActionSendPrivtIn.getName());
		campaignActionSendPrivt.setItemId(campaignActionSendPrivtIn.getItemId());
		campaignActionSendPrivt.setCampaignHeadId(campaignActionSendPrivtIn.getCampaignHeadId());
		campaignActionSendPrivt.setWechatH5Id(campaignActionSendPrivtIn.getWechatH5Id());
		campaignActionSendPrivt.setWechatH5Name(campaignActionSendPrivtIn.getWechatH5Name());
		campaignActionSendPrivt.setPrvtId(campaignActionSendPrivtIn.getPrvtId());
		campaignActionSendPrivt.setPrvtName(campaignActionSendPrivtIn.getPrvtName());
		campaignActionSendPrivt.setPrvtGroupName(campaignActionSendPrivtIn.getPrvtGroupName());
		campaignActionSendPrivt.setTextInfo(campaignActionSendPrivtIn.getTextInfo());
		return campaignActionSendPrivt;
	}
	
	private CampaignActionSendH5 initCampaignActionSendH5(CampaignBodyCreateIn body) {
		CampaignActionSendH5 campaignActionSendH5 = new CampaignActionSendH5();
		CampaignActionSendH5In campaignActionSendH5In = (CampaignActionSendH5In)body.getCampaignNodeChain().getInfo();
		campaignActionSendH5.setName(campaignActionSendH5In.getName());
		campaignActionSendH5.setItemId(campaignActionSendH5In.getItemId());
		campaignActionSendH5.setCampaignHeadId(campaignActionSendH5In.getCampaignHeadId());
		campaignActionSendH5.setWechatH5Id(campaignActionSendH5In.getWechatH5Id());
		campaignActionSendH5.setWechatH5Name(campaignActionSendH5In.getWechatH5Name());
		campaignActionSendH5.setPubId(campaignActionSendH5In.getPubId());
		campaignActionSendH5.setPubName(campaignActionSendH5In.getPubName());
		campaignActionSendH5.setPrvtId(campaignActionSendH5In.getPrvtId());
		campaignActionSendH5.setPrvtName(campaignActionSendH5In.getPrvtName());
		campaignActionSendH5.setPrvtGroupName(campaignActionSendH5In.getPrvtGroupName());
		return campaignActionSendH5;
	}
	
	private CampaignActionSendPub initCampaignActionSendPub(CampaignBodyCreateIn body) {
		CampaignActionSendPub campaignActionSendPub = new CampaignActionSendPub();
		CampaignActionSendPubIn campaignActionSendPubIn = (CampaignActionSendPubIn)body.getCampaignNodeChain().getInfo();
		campaignActionSendPub.setName(campaignActionSendPubIn.getName());
		campaignActionSendPub.setItemId(campaignActionSendPubIn.getItemId());
		campaignActionSendPub.setCampaignHeadId(campaignActionSendPubIn.getCampaignHeadId());
		campaignActionSendPub.setWechatH5Id(campaignActionSendPubIn.getWechatH5Id());
		campaignActionSendPub.setWechatH5Name(campaignActionSendPubIn.getWechatH5Name());
		campaignActionSendPub.setPubId(campaignActionSendPubIn.getPubId());
		campaignActionSendPub.setPubName(campaignActionSendPubIn.getPubName());
		return campaignActionSendPub;
	}
	
	private CampaignActionSetTag initCampaignActionSetTag(CampaignBodyCreateIn body) {
		CampaignActionSetTag campaignActionSetTag = new CampaignActionSetTag();
		CampaignActionSetTagIn campaignActionSetTagIn = (CampaignActionSetTagIn)body.getCampaignNodeChain().getInfo();
		campaignActionSetTag.setName(campaignActionSetTagIn.getName());
		campaignActionSetTag.setItemId(campaignActionSetTagIn.getItemId());
		campaignActionSetTag.setCampaignHeadId(campaignActionSetTagIn.getCampaignHeadId());
		campaignActionSetTag.setTagIds(campaignActionSetTagIn.getTagIds());
		campaignActionSetTag.setTagNames(campaignActionSetTagIn.getTagNames());
		return campaignActionSetTag;
	}
	
	private CampaignActionSaveAudience initCampaignActionSaveAudience(CampaignBodyCreateIn body) {
		CampaignActionSaveAudience campaignActionSaveAudience = new CampaignActionSaveAudience();
		CampaignActionSaveAudienceIn campaignActionSaveAudienceIn = (CampaignActionSaveAudienceIn)body.getCampaignNodeChain().getInfo();
		campaignActionSaveAudience.setName(campaignActionSaveAudienceIn.getName());
		campaignActionSaveAudience.setItemId(campaignActionSaveAudienceIn.getItemId());
		campaignActionSaveAudience.setCampaignHeadId(campaignActionSaveAudienceIn.getCampaignHeadId());
		campaignActionSaveAudience.setAudienceId(campaignActionSaveAudienceIn.getAudienceId());
		campaignActionSaveAudience.setAudienceName(campaignActionSaveAudienceIn.getAudienceName());
		return campaignActionSaveAudience;
	}
	
	private CampaignActionWait initCampaignActionWait(CampaignBodyCreateIn body) {
		CampaignActionWait campaignActionWait = new CampaignActionWait();
		CampaignActionWaitIn campaignActionWaitIn = (CampaignActionWaitIn)body.getCampaignNodeChain().getInfo();
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
		CampaignDecisionTagIn campaignDecisionTagIn = (CampaignDecisionTagIn)body.getCampaignNodeChain().getInfo();
		campaignDecisionTag.setName(campaignDecisionTagIn.getName());
		campaignDecisionTag.setItemId(campaignDecisionTagIn.getItemId());
		campaignDecisionTag.setCampaignHeadId(campaignDecisionTagIn.getCampaignHeadId());
		campaignDecisionTag.setTagIds(campaignDecisionTagIn.getTagIds());
		campaignDecisionTag.setTagNames(campaignDecisionTagIn.getTagNames());
		return campaignDecisionTag;
	}
	
	private CampaignDecisionPrvtFriends initCampaignDecisionPrvtFriends(CampaignBodyCreateIn body) {
		CampaignDecisionPrvtFriends campaignDecisionPrvtFriends = new CampaignDecisionPrvtFriends();
		CampaignDecisionPrvtFriendsIn campaignDecisionPrvtFriendsIn = (CampaignDecisionPrvtFriendsIn)body.getCampaignNodeChain().getInfo();
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
		CampaignDecisionPubFansIn campaignDecisionPubFansIn = (CampaignDecisionPubFansIn)body.getCampaignNodeChain().getInfo();
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
		CampaignDecisionWechatForwardIn campaignDecisionWechatForwardIn = (CampaignDecisionWechatForwardIn)body.getCampaignNodeChain().getInfo();
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
		CampaignDecisionWechatReadIn campaignDecisionWechatReadIn = (CampaignDecisionWechatReadIn)body.getCampaignNodeChain().getInfo();
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
		CampaignDecisionWechatSentIn campaignDecisionWechatSentIn = (CampaignDecisionWechatSentIn)body.getCampaignNodeChain().getInfo();
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
		CampaignDecisionPropCompareIn campaignDecisionPropCompareIn = (CampaignDecisionPropCompareIn)body.getCampaignNodeChain().getInfo();
		campaignDecisionPropCompare.setName(campaignDecisionPropCompareIn.getName());
		campaignDecisionPropCompare.setItemId(campaignDecisionPropCompareIn.getItemId());
		campaignDecisionPropCompare.setCampaignHeadId(campaignDecisionPropCompareIn.getCampaignHeadId());
		campaignDecisionPropCompare.setPropType(campaignDecisionPropCompareIn.getPropType());
		campaignDecisionPropCompare.setRule(campaignDecisionPropCompareIn.getRule());
		return campaignDecisionPropCompare;
	}
	
	private CampaignBody initCampaignBody(CampaignBodyCreateIn body) {
		CampaignBody campaignBody = new CampaignBody();
		campaignBody.setHeadId(body.getCampaignNodeChain().getCampaignHeadId());
		campaignBody.setNodeType(body.getCampaignNodeChain().getNodeType());
		campaignBody.setItemType(body.getCampaignNodeChain().getItemType());
		campaignBody.setItemId(body.getCampaignNodeChain().getIntemId());
		campaignBody.setNextItemId(body.getCampaignNodeChain().getNextItemId());
		campaignBody.setNextNodeType(body.getCampaignNodeChain().getNextNodeType());
		campaignBody.setNextItemType(body.getCampaignNodeChain().getNextItemType());
		campaignBody.setPosX(body.getCampaignNodeChain().getPosX());
		campaignBody.setPosY(body.getCampaignNodeChain().getPoxY());
		return campaignBody;
	}
	
	private CampaignTriggerTimer initCampaignTriggerTimer(CampaignBodyCreateIn body) {
		CampaignTriggerTimer campaignTriggerTimer = new CampaignTriggerTimer();
		CampaignTriggerTimerIn campaignTriggerTimerIn = (CampaignTriggerTimerIn)body.getCampaignNodeChain().getInfo();
		campaignTriggerTimer.setCampaignHeadId(body.getCampaignNodeChain().getCampaignHeadId());
		campaignTriggerTimer.setName(campaignTriggerTimerIn.getName());
		campaignTriggerTimer.setItemId(campaignTriggerTimerIn.getItemId());
		campaignTriggerTimer.setStartTime(DateUtil.getDateFromString(campaignTriggerTimerIn.getStartTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
		campaignTriggerTimer.setEndTime(DateUtil.getDateFromString(campaignTriggerTimerIn.getEndTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
		return campaignTriggerTimer;
	}
	
	private CampaignAudienceTarget initCampaignAudienceTarget(CampaignBodyCreateIn body) {
		CampaignAudienceTarget campaignAudienceTarget = new CampaignAudienceTarget();
		CampaignAudienceTargetIn campaignAudienceTargetIn = (CampaignAudienceTargetIn)body.getCampaignNodeChain().getInfo();
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
