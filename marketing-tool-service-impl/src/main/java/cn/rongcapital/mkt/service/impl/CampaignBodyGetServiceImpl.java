package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.CampaignActionSaveAudienceDao;
import cn.rongcapital.mkt.dao.CampaignActionSendH5Dao;
import cn.rongcapital.mkt.dao.CampaignActionSendPrivtDao;
import cn.rongcapital.mkt.dao.CampaignActionSendPubDao;
import cn.rongcapital.mkt.dao.CampaignActionSetTagDao;
import cn.rongcapital.mkt.dao.CampaignActionWaitDao;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPropCompareDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPrvtFriendsDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPubFansDao;
import cn.rongcapital.mkt.dao.CampaignDecisionTagDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatForwardDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatReadDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatSentDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.CampaignNodeItemDao;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.CampaignTriggerTimerDao;
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
import cn.rongcapital.mkt.po.CampaignNodeItem;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.CampaignTriggerTimer;
import cn.rongcapital.mkt.service.CampaignBodyGetService;
import cn.rongcapital.mkt.vo.out.CampaignActionSaveAudienceOut;
import cn.rongcapital.mkt.vo.out.CampaignActionSendH5Out;
import cn.rongcapital.mkt.vo.out.CampaignActionSendPrivtOut;
import cn.rongcapital.mkt.vo.out.CampaignActionSendPubOut;
import cn.rongcapital.mkt.vo.out.CampaignActionSetTagOut;
import cn.rongcapital.mkt.vo.out.CampaignActionWaitOut;
import cn.rongcapital.mkt.vo.out.CampaignAudienceTargetOut;
import cn.rongcapital.mkt.vo.out.CampaignBodyGetOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionPropCompareOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionPrvtFriendsOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionPubFansOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionTagOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionWechatForwardOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionWechatReadOut;
import cn.rongcapital.mkt.vo.out.CampaignDecisionWechatSentOut;
import cn.rongcapital.mkt.vo.out.CampaignNodeChainOut;
import cn.rongcapital.mkt.vo.out.CampaignSwitchOut;
import cn.rongcapital.mkt.vo.out.CampaignTriggerTimerOut;

@Service
public class CampaignBodyGetServiceImpl implements CampaignBodyGetService {

	@Autowired
	CampaignHeadDao campaignHeadDao;
	@Autowired
	CampaignBodyDao campaignBodyDao;
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
	CampaignAudienceTargetDao campaignAudienceTargetDao;
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
	CampaignSwitchDao campaignSwitchDao;
	@Autowired
	CampaignTriggerTimerDao campaignTriggerTimerDao;
	@Autowired
	CampaignNodeItemDao campaignNodeItemDao;

	@Override
	public CampaignBodyGetOut campaignBodyGet(String userToken, String ver, int campaignHeadId) {
		CampaignBodyGetOut campaignBodyGetOut = new CampaignBodyGetOut(0,ApiErrorCode.SUCCESS.getMsg(),0,null);
		CampaignBody campaignBodyQuery = new CampaignBody();
		campaignBodyQuery.setHeadId(campaignHeadId);
		campaignBodyQuery.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		List<CampaignBody> campaignBodyList = campaignBodyDao.selectList(campaignBodyQuery);
		
		if(CollectionUtils.isNotEmpty(campaignBodyList)) {
			for(CampaignBody c:campaignBodyList) {
				CampaignNodeChainOut campaignNodeChainOut = new CampaignNodeChainOut();
				campaignNodeChainOut.setItemId(c.getItemId());
				campaignNodeChainOut.setItemType(c.getItemType());
				campaignNodeChainOut.setNodeType(c.getNodeType());
				campaignNodeChainOut.setPosX(c.getPosX());
				campaignNodeChainOut.setPosY(c.getPosY());
				campaignNodeChainOut.setPosZ(c.getPosZ());
				//获取code、icon值
				CampaignNodeItem campaignNodeItem = new CampaignNodeItem();
				campaignNodeItem.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				campaignNodeItem.setType(c.getItemType());
				campaignNodeItem.setPtype(c.getNodeType());
				List<CampaignNodeItem> campaignNodeItemList = campaignNodeItemDao.selectList(campaignNodeItem);
				if(CollectionUtils.isNotEmpty(campaignNodeItemList)) {
					campaignNodeChainOut.setCode(campaignNodeItemList.get(0).getCode());
					campaignNodeChainOut.setIcon(campaignNodeItemList.get(0).getIcon());
				}
				
				List<CampaignSwitchOut> campaignSwitchList = queryCampaignSwitchList(campaignHeadId,c.getItemId());
				List<CampaignSwitchOut> campaignEndsList = queryCampaignEndsList(campaignHeadId,c.getItemId());
				campaignNodeChainOut.setCampaignEndsList(campaignEndsList);
				campaignNodeChainOut.setCampaignSwitchList(campaignSwitchList);
				
				if(campaignNodeChainOut.getNodeType() == ApiConstant.CAMPAIGN_NODE_TRIGGER){
					switch (campaignNodeChainOut.getItemType()) {
					case ApiConstant.CAMPAIGN_ITEM_TRIGGER_TIMMER://定时触发
						CampaignTriggerTimerOut campaignTriggerTimerOut = queryCampaignTriggerTimer(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignTriggerTimerOut);
						break;
					}
				}
				if(campaignNodeChainOut.getNodeType() == ApiConstant.CAMPAIGN_NODE_AUDIENCE){
					switch (campaignNodeChainOut.getItemType()) {
					case ApiConstant.CAMPAIGN_ITEM_AUDIENCE_TARGET://目标人群
						CampaignAudienceTargetOut campaignAudienceTargetOut = queryCampaignAudienceTarget(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignAudienceTargetOut);
						break;
					}
				}
				if(campaignNodeChainOut.getNodeType() == ApiConstant.CAMPAIGN_NODE_DECISION){
					switch (campaignNodeChainOut.getItemType()) {
					case ApiConstant.CAMPAIGN_ITEM_DECISION_PROP_COMPARE://联系人属性比较
						CampaignDecisionPropCompareOut campaignDecisionPropCompareOut = queryCampaignDecisionPropCompare(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionPropCompareOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_SENT://微信图文是否发送
						CampaignDecisionWechatSentOut campaignDecisionWechatSentOut = queryCampaignDecisionWechatSent(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionWechatSentOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_READ://微信图文是否查看
						CampaignDecisionWechatReadOut campaignDecisionWechatReadOut = queryCampaignDecisionWechatRead(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionWechatReadOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_FORWARD://微信图文是否转发
						CampaignDecisionWechatForwardOut campaignDecisionWechatForwardOut = queryCampaignDecisionWechatForward(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionWechatForwardOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_IS_SUBSCRIBE://是否订阅公众号
						CampaignDecisionPubFansOut campaignDecisionPubFansOut = queryCampaignDecisionPubFans(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionPubFansOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_IS_PRIVT_FRIEND://是否个人号好友
						CampaignDecisionPrvtFriendsOut campaignDecisionPrvtFriendsOut = queryCampaignDecisionPrvtFriends(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionPrvtFriendsOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_DECISION_TAG://标签判断
						CampaignDecisionTagOut campaignDecisionTagOut = queryCampaignDecisionTag(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignDecisionTagOut);
						break;
					}
				}
				if(campaignNodeChainOut.getNodeType() == ApiConstant.CAMPAIGN_NODE_ACTION){
					switch (campaignNodeChainOut.getItemType()) {
					case ApiConstant.CAMPAIGN_ITEM_ACTION_WAIT://等待
						CampaignActionWaitOut campaignActionWaitOut = queryCampaignActionWait(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionWaitOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_SAVE_AUDIENCE://保存当前人群
						CampaignActionSaveAudienceOut campaignActionSaveAudienceOut = queryCampaignActionSaveAudience(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionSaveAudienceOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_SET_TAG://设置标签
						CampaignActionSetTagOut campaignActionSetTagOut = queryCampaignActionSetTag(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionSetTagOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_ADD_CAMPAIGN://添加到其它活动
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_MOVE_CAMPAIGN://转移到其它活动
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_WECHAT_H5://发送微信图文
						CampaignActionSendPubOut campaignActionSendPubOut = queryCampaignActionSendPub(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionSendPubOut);
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_H5://发送H5图文
						CampaignActionSendH5Out campaignActionSendH5Out = queryCampaignActionSendH5(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionSendH5Out);
						break;
					case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_PRVT_INFO://发送个人号消息
						CampaignActionSendPrivtOut campaignActionSendPrivtOut = queryCampaignActionSendPrivt(campaignNodeChainOut,campaignHeadId);
						campaignNodeChainOut.setInfo(campaignActionSendPrivtOut);
						break;
					}
				}
				campaignBodyGetOut.getData().add(campaignNodeChainOut);
			}
		}
		campaignBodyGetOut.setTotal(campaignBodyGetOut.getData().size());
		return campaignBodyGetOut;
	}
	
	private CampaignActionSendPrivtOut queryCampaignActionSendPrivt(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignActionSendPrivt t = new CampaignActionSendPrivt();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionSendPrivt> resList = campaignActionSendPrivtDao.selectList(t);
		CampaignActionSendPrivtOut campaignActionSendPrivtOut = new CampaignActionSendPrivtOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignActionSendPrivt campaignActionSendPrivt = resList.get(0);
			campaignActionSendPrivtOut.setName(campaignActionSendPrivt.getName());
			campaignActionSendPrivtOut.setPrvtGroupName(campaignActionSendPrivt.getPrvtGroupName());
			campaignActionSendPrivtOut.setPrvtId(campaignActionSendPrivt.getPrvtId());
			campaignActionSendPrivtOut.setPrvtName(campaignActionSendPrivt.getPrvtName());
			campaignActionSendPrivtOut.setWechatH5Id(campaignActionSendPrivt.getWechatH5Id());
			campaignActionSendPrivtOut.setWechatH5Name(campaignActionSendPrivt.getWechatH5Name());
			campaignActionSendPrivtOut.setTextInfo(campaignActionSendPrivt.getTextInfo());
		}
		return campaignActionSendPrivtOut;
	}
	
	private CampaignActionSendH5Out queryCampaignActionSendH5(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignActionSendH5 t = new CampaignActionSendH5();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionSendH5> resList = campaignActionSendH5Dao.selectList(t);
		CampaignActionSendH5Out campaignActionSendH5Out = new CampaignActionSendH5Out();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignActionSendH5 campaignActionSendH5 = resList.get(0);
			campaignActionSendH5Out.setName(campaignActionSendH5.getName());
			campaignActionSendH5Out.setPrvtGroupName(campaignActionSendH5.getPrvtGroupName());
			campaignActionSendH5Out.setPrvtId(campaignActionSendH5.getPrvtId());
			campaignActionSendH5Out.setPrvtName(campaignActionSendH5.getPrvtName());
			campaignActionSendH5Out.setPubId(campaignActionSendH5.getPubId());
			campaignActionSendH5Out.setPubName(campaignActionSendH5.getPubName());
			campaignActionSendH5Out.setWechatH5Id(campaignActionSendH5.getWechatH5Id());
			campaignActionSendH5Out.setWechatH5Name(campaignActionSendH5.getWechatH5Name());
		}
		return campaignActionSendH5Out;
	}
	
	private CampaignActionSendPubOut queryCampaignActionSendPub(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignActionSendPub t = new CampaignActionSendPub();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionSendPub> resList = campaignActionSendPubDao.selectList(t);
		CampaignActionSendPubOut campaignActionSendPubOut = new CampaignActionSendPubOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignActionSendPub campaignActionSendPub = resList.get(0);
			campaignActionSendPubOut.setName(campaignActionSendPub.getName());
			campaignActionSendPubOut.setPubId(campaignActionSendPub.getPubId());
			campaignActionSendPubOut.setPubName(campaignActionSendPub.getPubName());
			campaignActionSendPubOut.setWechatH5Id(campaignActionSendPub.getWechatH5Id());
			campaignActionSendPubOut.setWechatH5Name(campaignActionSendPub.getWechatH5Name());
		}
		return campaignActionSendPubOut;
	}
	
	private CampaignActionSetTagOut queryCampaignActionSetTag(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignActionSetTag t = new CampaignActionSetTag();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionSetTag> resList = campaignActionSetTagDao.selectList(t);
		CampaignActionSetTagOut campaignActionSetTagOut = new CampaignActionSetTagOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignActionSetTag campaignActionSetTag = resList.get(0);
			campaignActionSetTagOut.setName(campaignActionSetTag.getName());
//			campaignActionSetTagOut.setTagIds(campaignActionSetTag.getTagIds());//TO DO:查询custom_tag_map表
//			campaignActionSetTagOut.setTagNames(campaignActionSetTag.getTagNames());
		}
		return campaignActionSetTagOut;
	}
	
	private CampaignActionSaveAudienceOut queryCampaignActionSaveAudience(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignActionSaveAudience t = new CampaignActionSaveAudience();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionSaveAudience> resList = campaignActionSaveAudienceDao.selectList(t);
		CampaignActionSaveAudienceOut campaignActionSaveAudienceOut = new CampaignActionSaveAudienceOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignActionSaveAudience campaignActionSaveAudience = resList.get(0);
			campaignActionSaveAudienceOut.setName(campaignActionSaveAudience.getName());
			campaignActionSaveAudienceOut.setAudienceId(campaignActionSaveAudience.getAudienceId());
			campaignActionSaveAudienceOut.setAudienceName(campaignActionSaveAudience.getAudienceName());
		}
		return campaignActionSaveAudienceOut;
	}
	
	private CampaignActionWaitOut queryCampaignActionWait(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignActionWait t = new CampaignActionWait();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignActionWait> resList = campaignActionWaitDao.selectList(t);
		CampaignActionWaitOut campaignActionWaitOut = new CampaignActionWaitOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignActionWait campaignActionWait = resList.get(0);
			campaignActionWaitOut.setName(campaignActionWait.getName());
			campaignActionWaitOut.setRelativeType(campaignActionWait.getRelativeType());
			campaignActionWaitOut.setRelativeValue(campaignActionWait.getRelativeValue());
			campaignActionWaitOut.setSpecificTime(DateUtil.getStringFromDate(campaignActionWait.getSpecificTime(),ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
			campaignActionWaitOut.setType(campaignActionWait.getType());
		}
		return campaignActionWaitOut;
	}
	
	private CampaignDecisionTagOut queryCampaignDecisionTag(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignDecisionTag t = new CampaignDecisionTag();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionTag> resList = campaignDecisionTagDao.selectList(t);
		CampaignDecisionTagOut campaignDecisionTagOut = new CampaignDecisionTagOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionTag campaignDecisionTag = resList.get(0);
			campaignDecisionTagOut.setName(campaignDecisionTag.getName());
			campaignDecisionTagOut.setRule(campaignDecisionTag.getRule());
		}
		return campaignDecisionTagOut;
	}
	
	private CampaignDecisionPrvtFriendsOut queryCampaignDecisionPrvtFriends(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignDecisionPrvtFriends t = new CampaignDecisionPrvtFriends();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionPrvtFriends> resList = campaignDecisionPrvtFriendsDao.selectList(t);
		CampaignDecisionPrvtFriendsOut campaignDecisionPrvtFriendsOut = new CampaignDecisionPrvtFriendsOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionPrvtFriends campaignDecisionPrvtFriends = resList.get(0);
			campaignDecisionPrvtFriendsOut.setName(campaignDecisionPrvtFriends.getName());
			campaignDecisionPrvtFriendsOut.setPrvtId(campaignDecisionPrvtFriends.getPrvtId());
			campaignDecisionPrvtFriendsOut.setPrvtName(campaignDecisionPrvtFriends.getPrvtName());
			campaignDecisionPrvtFriendsOut.setRefreshInterval(campaignDecisionPrvtFriends.getRefreshInterval());
			campaignDecisionPrvtFriendsOut.setRefreshIntervalType(campaignDecisionPrvtFriends.getRefreshIntervalType());
		}
		return campaignDecisionPrvtFriendsOut;
	}
	
	private CampaignDecisionPubFansOut queryCampaignDecisionPubFans(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignDecisionPubFans t = new CampaignDecisionPubFans();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionPubFans> resList = campaignDecisionPubFansDao.selectList(t);
		CampaignDecisionPubFansOut campaignDecisionPubFansOut = new CampaignDecisionPubFansOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionPubFans campaignDecisionPubFans = resList.get(0);
			campaignDecisionPubFansOut.setName(campaignDecisionPubFans.getName());
			campaignDecisionPubFansOut.setPubId(campaignDecisionPubFans.getPubId());
			campaignDecisionPubFansOut.setPubName(campaignDecisionPubFans.getPubName());
			campaignDecisionPubFansOut.setRefreshInterval(campaignDecisionPubFans.getRefreshInterval());
			campaignDecisionPubFansOut.setRefreshIntervalType(campaignDecisionPubFans.getRefreshIntervalType());
			campaignDecisionPubFansOut.setSubscribeTime(campaignDecisionPubFans.getSubscribeTime());
		}
		return campaignDecisionPubFansOut;
	}
	
	private CampaignDecisionWechatForwardOut queryCampaignDecisionWechatForward(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignDecisionWechatForward t = new CampaignDecisionWechatForward();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionWechatForward> resList = campaignDecisionWechatForwardDao.selectList(t);
		CampaignDecisionWechatForwardOut campaignDecisionWechatForwardOut = new CampaignDecisionWechatForwardOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionWechatForward campaignDecisionWechatForward = resList.get(0);
			campaignDecisionWechatForwardOut.setName(campaignDecisionWechatForward.getName());
			campaignDecisionWechatForwardOut.setPubId(campaignDecisionWechatForward.getPubId());
			campaignDecisionWechatForwardOut.setPubName(campaignDecisionWechatForward.getPubName());
			campaignDecisionWechatForwardOut.setRefreshInterval(campaignDecisionWechatForward.getRefreshInterval());
			campaignDecisionWechatForwardOut.setRefreshIntervalType(campaignDecisionWechatForward.getRefreshIntervalType());
			campaignDecisionWechatForwardOut.setWechatH5Id(campaignDecisionWechatForward.getWechatH5Id());
			campaignDecisionWechatForwardOut.setWechatH5Name(campaignDecisionWechatForward.getWechatH5Name());
			campaignDecisionWechatForwardOut.setForwardTimes(campaignDecisionWechatForward.getForwardTimes());
		}
		return campaignDecisionWechatForwardOut;
	}
	
	private CampaignDecisionWechatReadOut queryCampaignDecisionWechatRead(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignDecisionWechatRead t = new CampaignDecisionWechatRead();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionWechatRead> resList = campaignDecisionWechatReadDao.selectList(t);
		CampaignDecisionWechatReadOut campaignDecisionWechatReadOut = new CampaignDecisionWechatReadOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionWechatRead campaignDecisionWechatRead = resList.get(0);
			campaignDecisionWechatReadOut.setName(campaignDecisionWechatRead.getName());
			campaignDecisionWechatReadOut.setPubId(campaignDecisionWechatRead.getPubId());
			campaignDecisionWechatReadOut.setPubName(campaignDecisionWechatRead.getPubName());
			campaignDecisionWechatReadOut.setRefreshInterval(campaignDecisionWechatRead.getRefreshInterval());
			campaignDecisionWechatReadOut.setRefreshIntervalType(campaignDecisionWechatRead.getRefreshIntervalType());
			campaignDecisionWechatReadOut.setWechatH5Id(campaignDecisionWechatRead.getWechatH5Id());
			campaignDecisionWechatReadOut.setWechatH5Name(campaignDecisionWechatRead.getWechatH5Name());
			campaignDecisionWechatReadOut.setReadTime(campaignDecisionWechatRead.getReadTime());
			campaignDecisionWechatReadOut.setReadPercent(campaignDecisionWechatRead.getReadPercent());
		}
		return campaignDecisionWechatReadOut;
	}
	
	private CampaignDecisionWechatSentOut queryCampaignDecisionWechatSent(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignDecisionWechatSent t = new CampaignDecisionWechatSent();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionWechatSent> resList = campaignDecisionWechatSentDao.selectList(t);
		CampaignDecisionWechatSentOut campaignDecisionWechatSentOut = new CampaignDecisionWechatSentOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionWechatSent campaignDecisionWechatSent = resList.get(0);
			campaignDecisionWechatSentOut.setName(campaignDecisionWechatSent.getName());
			campaignDecisionWechatSentOut.setPubId(campaignDecisionWechatSent.getPubId());
			campaignDecisionWechatSentOut.setPubName(campaignDecisionWechatSent.getPubName());
			campaignDecisionWechatSentOut.setRefreshInterval(campaignDecisionWechatSent.getRefreshInterval());
			campaignDecisionWechatSentOut.setRefreshIntervalType(campaignDecisionWechatSent.getRefreshIntervalType());
			campaignDecisionWechatSentOut.setWechatH5Id(campaignDecisionWechatSent.getWechatH5Id());
			campaignDecisionWechatSentOut.setWechatH5Name(campaignDecisionWechatSent.getWechatH5Name());
		}
		return campaignDecisionWechatSentOut;
	}
	
	private CampaignDecisionPropCompareOut queryCampaignDecisionPropCompare(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignDecisionPropCompare t = new CampaignDecisionPropCompare();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignDecisionPropCompare> resList = campaignDecisionPropCompareDao.selectList(t);
		CampaignDecisionPropCompareOut campaignDecisionPropCompareOut = new CampaignDecisionPropCompareOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignDecisionPropCompare campaignDecisionPropCompare = resList.get(0);
			campaignDecisionPropCompareOut.setName(campaignDecisionPropCompare.getName());
			campaignDecisionPropCompareOut.setPropType(campaignDecisionPropCompare.getPropType());
			campaignDecisionPropCompareOut.setRule(campaignDecisionPropCompare.getRule());
		}
		return campaignDecisionPropCompareOut;
	}

	private CampaignAudienceTargetOut queryCampaignAudienceTarget(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignAudienceTarget t = new CampaignAudienceTarget();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignAudienceTarget> resList = campaignAudienceTargetDao.selectList(t);
		CampaignAudienceTargetOut campaignAudienceTargetOut = new CampaignAudienceTargetOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignAudienceTarget campaignAudienceTarget = resList.get(0);
			campaignAudienceTargetOut.setName(campaignAudienceTarget.getName());
			campaignAudienceTargetOut.setAllowedNew(campaignAudienceTarget.getAllowedNew());
			campaignAudienceTargetOut.setRefreshInterval(campaignAudienceTarget.getRefreshInterval());
			campaignAudienceTargetOut.setRefreshIntervalType(campaignAudienceTarget.getRefreshIntervalType());
			campaignAudienceTargetOut.setSegmentationId(campaignAudienceTarget.getSegmentationId());
			campaignAudienceTargetOut.setSegmentationName(campaignAudienceTarget.getSegmentationName());

		}
		return campaignAudienceTargetOut;
	}
	
	private CampaignTriggerTimerOut queryCampaignTriggerTimer(CampaignNodeChainOut campaignNodeChainOut, int campaignHeadId) {
		CampaignTriggerTimer t = new CampaignTriggerTimer();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		t.setCampaignHeadId(campaignHeadId);
		t.setItemId(campaignNodeChainOut.getItemId());
		List<CampaignTriggerTimer> resList = campaignTriggerTimerDao.selectList(t);
		CampaignTriggerTimerOut campaignTriggerTimerOut = new CampaignTriggerTimerOut();
		if(CollectionUtils.isNotEmpty(resList)) {
			CampaignTriggerTimer campaignTriggerTimer = resList.get(0);
			campaignTriggerTimerOut.setName(campaignTriggerTimer.getName());
			campaignTriggerTimerOut.setStartTime(DateUtil.getStringFromDate(campaignTriggerTimer.getStartTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
			campaignTriggerTimerOut.setEndTime(DateUtil.getStringFromDate(campaignTriggerTimer.getEndTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));

		}
		return campaignTriggerTimerOut;
	}
	
	private List<CampaignSwitchOut> queryCampaignSwitchList(int campaignHeadId,String itemId) {
		CampaignSwitch campaignSwitch = new CampaignSwitch();
		campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_SWITCH);
		campaignSwitch.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignSwitch.setCampaignHeadId(campaignHeadId);
		campaignSwitch.setItemId(itemId);
		List<CampaignSwitch> campaignSwitchList = campaignSwitchDao.selectList(campaignSwitch);
		List<CampaignSwitchOut> campaignSwitchOutList = new ArrayList<CampaignSwitchOut>();
		if(CollectionUtils.isNotEmpty(campaignSwitchList)){
			for(CampaignSwitch cs:campaignSwitchList) {
				CampaignSwitchOut cso = new CampaignSwitchOut();
				cso.setColor(cs.getColor());
				cso.setDrawType(cs.getDrawType());
				cso.setNextItemId(cs.getNextItemId());
			}
		}
		return campaignSwitchOutList;
	}
	
	private List<CampaignSwitchOut> queryCampaignEndsList(int campaignHeadId,String itemId) {
		CampaignSwitch campaignSwitch = new CampaignSwitch();
		campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_ENDS);
		campaignSwitch.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignSwitch.setCampaignHeadId(campaignHeadId);
		campaignSwitch.setItemId(itemId);
		List<CampaignSwitch> campaignEndsList = campaignSwitchDao.selectList(campaignSwitch);
		List<CampaignSwitchOut> campaignSwitchOutList = new ArrayList<CampaignSwitchOut>();
		if(CollectionUtils.isNotEmpty(campaignEndsList)){
			for(CampaignSwitch cs:campaignEndsList) {
				CampaignSwitchOut cso = new CampaignSwitchOut();
				cso.setColor(cs.getColor());
				cso.setDrawType(cs.getDrawType());
				cso.setNextItemId(cs.getNextItemId());
			}
		}
		return campaignSwitchOutList;
	}

}
