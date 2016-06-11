package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.codehaus.jackson.map.ObjectMapper;
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
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPropCompareDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPrvtFriendsDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPubFansDao;
import cn.rongcapital.mkt.dao.CampaignDecisionTagDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatForwardDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatReadDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatSentDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
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
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.CampaignSwitch;
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
import cn.rongcapital.mkt.vo.in.CampaignNodeChainIn;
import cn.rongcapital.mkt.vo.in.CampaignSwitchIn;
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
	CampaignAudienceTargetDao CampaignAudienceTargetDao;
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
	@Autowired
	CampaignSwitchDao campaignSwitchDao;
	
	private static final ObjectMapper jacksonObjectMapper = new ObjectMapper();
	
	@Override
	public CampaignBodyCreateOut campaignBodyCreate(CampaignBodyCreateIn body, SecurityContext securityContext) {
		int campaignHeadId = body.getCampaignHeadId();

		CampaignBodyCreateOut out = checkCampaignBiz(campaignHeadId);
		if(null != out) {
			return out;
		}
		deleteOldCampaignData(campaignHeadId);//删除旧数据 
		
		for(CampaignNodeChainIn campaignNodeChainIn:body.getCampaignNodeChain()){
			List<CampaignSwitch> campaignSwitchList = initCampaignSwitchList(campaignNodeChainIn,campaignHeadId);
			List<CampaignSwitch> campaignEndsList = initCampaignEndsList(campaignNodeChainIn,campaignHeadId);
			if(null != campaignSwitchList && campaignSwitchList.size() > 0) {
				for(CampaignSwitch c:campaignSwitchList) {
					campaignSwitchDao.insert(c);
				}
			} else if(null != campaignEndsList && campaignEndsList.size() > 0) {
				for(CampaignSwitch c:campaignEndsList) {
					campaignSwitchDao.insert(c);
				}
			}
			if(campaignNodeChainIn.getNodeType() == ApiConstant.CAMPAIGN_NODE_TRIGGER){
				switch (campaignNodeChainIn.getItemType()) {
				case ApiConstant.CAMPAIGN_ITEM_TRIGGER_TIMMER://定时触发
					CampaignTriggerTimer campaignTriggerTimer = initCampaignTriggerTimer(campaignNodeChainIn,campaignHeadId);
					campaignTriggerTimerDao.insert(campaignTriggerTimer);
					break;
				}
			}
			if(campaignNodeChainIn.getNodeType() == ApiConstant.CAMPAIGN_NODE_AUDIENCE){
				switch (campaignNodeChainIn.getItemType()) {
				case ApiConstant.CAMPAIGN_ITEM_AUDIENCE_TARGET://目标人群
					CampaignAudienceTarget campaignAudienceTarget = initCampaignAudienceTarget(campaignNodeChainIn,campaignHeadId);
					CampaignAudienceTargetDao.insert(campaignAudienceTarget);
					break;
				}
			}
			if(campaignNodeChainIn.getNodeType() == ApiConstant.CAMPAIGN_NODE_DECISION){
				switch (campaignNodeChainIn.getItemType()) {
				case ApiConstant.CAMPAIGN_ITEM_DECISION_PROP_COMPARE://联系人属性比较
					CampaignDecisionPropCompare campaignDecisionPropCompare = initCampaignDecisionPropCompare(campaignNodeChainIn,campaignHeadId);
					campaignDecisionPropCompareDao.insert(campaignDecisionPropCompare);
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_SENT://微信图文是否发送
					CampaignDecisionWechatSent campaignDecisionWechatSent = initCampaignDecisionWechatSent(campaignNodeChainIn,campaignHeadId);
					campaignDecisionWechatSentDao.insert(campaignDecisionWechatSent);
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_READ://微信图文是否查看
					CampaignDecisionWechatRead campaignDecisionWechatRead = initCampaignDecisionWechatRead(campaignNodeChainIn,campaignHeadId);
					campaignDecisionWechatReadDao.insert(campaignDecisionWechatRead);
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_WECHAT_FORWARD://微信图文是否转发
					CampaignDecisionWechatForward campaignDecisionWechatForward = initCampaignDecisionWechatForward(campaignNodeChainIn,campaignHeadId);
					campaignDecisionWechatForwardDao.insert(campaignDecisionWechatForward);
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_IS_SUBSCRIBE://是否订阅公众号
					CampaignDecisionPubFans campaignDecisionPubFans = initCampaignDecisionPubFans(campaignNodeChainIn,campaignHeadId);
					campaignDecisionPubFansDao.insert(campaignDecisionPubFans);
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_IS_PRIVT_FRIEND://是否个人号好友
					CampaignDecisionPrvtFriends campaignDecisionPrvtFriends = initCampaignDecisionPrvtFriends(campaignNodeChainIn,campaignHeadId);
					campaignDecisionPrvtFriendsDao.insert(campaignDecisionPrvtFriends);
					break;
				case ApiConstant.CAMPAIGN_ITEM_DECISION_TAG://标签判断
					CampaignDecisionTag campaignDecisionTag = initCampaignDecisionTag(campaignNodeChainIn,campaignHeadId);
					campaignDecisionTagDao.insert(campaignDecisionTag);
					break;
				}
			}
			if(campaignNodeChainIn.getNodeType() == ApiConstant.CAMPAIGN_NODE_ACTION){
				switch (campaignNodeChainIn.getItemType()) {
				case ApiConstant.CAMPAIGN_ITEM_ACTION_WAIT://等待
					CampaignActionWait campaignActionWait = initCampaignActionWait(campaignNodeChainIn,campaignHeadId);
					campaignActionWaitDao.insert(campaignActionWait);
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_SAVE_AUDIENCE://保存当前人群
					CampaignActionSaveAudience campaignActionSaveAudience = initCampaignActionSaveAudience(campaignNodeChainIn,campaignHeadId);
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
				case ApiConstant.CAMPAIGN_ITEM_ACTION_SET_TAG://设置标签
					CampaignActionSetTag campaignActionSetTag = initCampaignActionSetTag(campaignNodeChainIn,campaignHeadId);
					campaignActionSetTagDao.insert(campaignActionSetTag);
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_ADD_CAMPAIGN://添加到其它活动
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_MOVE_CAMPAIGN://转移到其它活动
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_WECHAT_H5://发送微信图文
					CampaignActionSendPub campaignActionSendPub = initCampaignActionSendPub(campaignNodeChainIn,campaignHeadId);
					campaignActionSendPubDao.insert(campaignActionSendPub);
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_H5://发送H5图文
					CampaignActionSendH5 campaignActionSendH5 = initCampaignActionSendH5(campaignNodeChainIn,campaignHeadId);
					campaignActionSendH5Dao.insert(campaignActionSendH5);
					break;
				case ApiConstant.CAMPAIGN_ITEM_ACTION_SEND_PRVT_INFO://发送个人号消息
					CampaignActionSendPrivt campaignActionSendPrivt = initCampaignActionSendPrivt(campaignNodeChainIn,campaignHeadId);
					campaignActionSendPrivtDao.insert(campaignActionSendPrivt);
					break;
				}
			}
			CampaignBody campaignBody = initCampaignBody(campaignNodeChainIn,campaignHeadId);
			campaignBodyDao.insert(campaignBody);
		}
		out = new CampaignBodyCreateOut(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		return out;
	}

	/**
	 * 校验业务逻辑:
	 * 1.活动头必须存在且只存在1个
	 * 2.只有未发布的活动才能编辑/创建 body
	 * @return
	 */
	private CampaignBodyCreateOut checkCampaignBiz(int campaignHeadId) {
		CampaignBodyCreateOut out = null;
		CampaignHead ch = new CampaignHead();
		ch.setStatus((byte)0);
		ch.setId(campaignHeadId);
		List<CampaignHead> campaignHeadList = campaignHeadDao.selectList(ch);
		
		if(null!=campaignHeadList && campaignHeadList.size()==1){
			CampaignHead campaignHead = campaignHeadList.get(0);
			if(campaignHead.getPublishStatus() != 0){
				out = new CampaignBodyCreateOut(ApiErrorCode.BUSINESS_ERROR.getCode(),"只有未发布状态的活动才能编辑",ApiConstant.INT_ZERO,null);
			}
		}else{
			out = new CampaignBodyCreateOut(ApiErrorCode.DB_ERROR.getCode(),"活动头不存在或存在多个活动头",ApiConstant.INT_ZERO,null);
		}
		return out;
	}
	
	private void deleteOldCampaignData (int campaignHeadId) {
		campaignSwitchDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionWechatSentDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionWechatReadDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionWechatForwardDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionTagDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionPubFansDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionPrvtFriendsDao.deleteByCampaignHeadId(campaignHeadId);
		campaignDecisionPropCompareDao.deleteByCampaignHeadId(campaignHeadId);
		CampaignAudienceTargetDao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionWaitDao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionSetTagDao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionSendPubDao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionSendPrivtDao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionSendH5Dao.deleteByCampaignHeadId(campaignHeadId);
		campaignActionSaveAudienceDao.deleteByCampaignHeadId(campaignHeadId);
		campaignTriggerTimerDao.deleteByCampaignHeadId(campaignHeadId);
		campaignBodyDao.deleteByCampaignHeadId(campaignHeadId);
	}
	
	private List<CampaignSwitch> initCampaignSwitchList(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		List<CampaignSwitchIn> campaignSwitchInList = campaignNodeChainIn.getCampaignSwitchList();
		List<CampaignSwitch> campaignSwitchList = new ArrayList<CampaignSwitch>();
		if(null != campaignSwitchInList && campaignSwitchInList.size() > 0) {
			for(CampaignSwitchIn campaignSwitchIn:campaignSwitchInList){
				CampaignSwitch campaignSwitch = new CampaignSwitch();
				campaignSwitch.setCampaignHeadId(campaignHeadId);
				campaignSwitch.setItemId(campaignNodeChainIn.getItemId());
				campaignSwitch.setColor(campaignSwitchIn.getColor());
				campaignSwitch.setNextItemId(campaignSwitchIn.getNextItemId());
				campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_SWITCH);
				campaignSwitch.setDrawType(campaignSwitchIn.getDrawType());
				campaignSwitchList.add(campaignSwitch);
			}
		}
		return campaignSwitchList;
	}
	
	private List<CampaignSwitch> initCampaignEndsList(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		List<CampaignSwitchIn> campaignEndsInList = campaignNodeChainIn.getCampaignEndsList();
		List<CampaignSwitch> campaignSwitchList = new ArrayList<CampaignSwitch>();
		if(null != campaignEndsInList && campaignEndsInList.size() > 0) {
			for(CampaignSwitchIn campaignSwitchIn:campaignEndsInList){
				CampaignSwitch campaignSwitch = new CampaignSwitch();
				campaignSwitch.setCampaignHeadId(campaignHeadId);
				campaignSwitch.setItemId(campaignNodeChainIn.getItemId());
				campaignSwitch.setColor(campaignSwitchIn.getColor());
				campaignSwitch.setNextItemId(campaignSwitchIn.getNextItemId());
				campaignSwitch.setType(ApiConstant.CAMPAIGN_SWITCH_ENDS);
				campaignSwitch.setDrawType(campaignSwitchIn.getDrawType());
				campaignSwitchList.add(campaignSwitch);
			}
		}
		return campaignSwitchList;
	}
	
	private CampaignActionSendPrivt initCampaignActionSendPrivt(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionSendPrivt campaignActionSendPrivt = new CampaignActionSendPrivt();
		CampaignActionSendPrivtIn campaignActionSendPrivtIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionSendPrivtIn.class);
		campaignActionSendPrivt.setName(campaignActionSendPrivtIn.getName());
		campaignActionSendPrivt.setItemId(campaignNodeChainIn.getItemId());
		campaignActionSendPrivt.setCampaignHeadId(campaignHeadId);
		campaignActionSendPrivt.setPrvtId(campaignActionSendPrivtIn.getPrvtId());
		campaignActionSendPrivt.setPrvtName(campaignActionSendPrivtIn.getPrvtName());
		campaignActionSendPrivt.setPrvtGroupName(campaignActionSendPrivtIn.getPrvtGroupName());
		campaignActionSendPrivt.setTextInfo(campaignActionSendPrivtIn.getTextInfo());
		return campaignActionSendPrivt;
	}
	
	private CampaignActionSendH5 initCampaignActionSendH5(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionSendH5 campaignActionSendH5 = new CampaignActionSendH5();
		CampaignActionSendH5In campaignActionSendH5In = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionSendH5In.class);
		campaignActionSendH5.setName(campaignActionSendH5In.getName());
		campaignActionSendH5.setItemId(campaignNodeChainIn.getItemId());
		campaignActionSendH5.setCampaignHeadId(campaignHeadId);
		campaignActionSendH5.setWechatH5Id(campaignActionSendH5In.getWechatH5Id());
		campaignActionSendH5.setWechatH5Name(campaignActionSendH5In.getWechatH5Name());
		campaignActionSendH5.setPubId(campaignActionSendH5In.getPubId());
		campaignActionSendH5.setPubName(campaignActionSendH5In.getPubName());
		campaignActionSendH5.setPrvtId(campaignActionSendH5In.getPrvtId());
		campaignActionSendH5.setPrvtName(campaignActionSendH5In.getPrvtName());
		campaignActionSendH5.setPrvtGroupName(campaignActionSendH5In.getPrvtGroupName());
		return campaignActionSendH5;
	}
	
	private CampaignActionSendPub initCampaignActionSendPub(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionSendPub campaignActionSendPub = new CampaignActionSendPub();
		CampaignActionSendPubIn campaignActionSendPubIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionSendPubIn.class);
		campaignActionSendPub.setName(campaignActionSendPubIn.getName());
		campaignActionSendPub.setItemId(campaignNodeChainIn.getItemId());
		campaignActionSendPub.setCampaignHeadId(campaignHeadId);
		campaignActionSendPub.setWechatH5Id(campaignActionSendPubIn.getWechatH5Id());
		campaignActionSendPub.setWechatH5Name(campaignActionSendPubIn.getWechatH5Name());
		campaignActionSendPub.setPubId(campaignActionSendPubIn.getPubId());
		campaignActionSendPub.setPubName(campaignActionSendPubIn.getPubName());
		return campaignActionSendPub;
	}
	
	private CampaignActionSetTag initCampaignActionSetTag(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionSetTag campaignActionSetTag = new CampaignActionSetTag();
		CampaignActionSetTagIn campaignActionSetTagIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionSetTagIn.class);
		campaignActionSetTag.setName(campaignActionSetTagIn.getName());
		campaignActionSetTag.setItemId(campaignNodeChainIn.getItemId());
		campaignActionSetTag.setCampaignHeadId(campaignHeadId);
//		campaignActionSetTag.setTagIds(campaignActionSetTagIn.getTagIds());//TO DO:关联关系写入到custom_tag_map表
//		campaignActionSetTag.setTagNames(campaignActionSetTagIn.getTagNames());
		return campaignActionSetTag;
	}
	
	private CampaignActionSaveAudience initCampaignActionSaveAudience(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionSaveAudience campaignActionSaveAudience = new CampaignActionSaveAudience();
		CampaignActionSaveAudienceIn campaignActionSaveAudienceIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionSaveAudienceIn.class);
		campaignActionSaveAudience.setName(campaignActionSaveAudienceIn.getName());
		campaignActionSaveAudience.setItemId(campaignNodeChainIn.getItemId());
		campaignActionSaveAudience.setCampaignHeadId(campaignHeadId);
		campaignActionSaveAudience.setAudienceId(campaignActionSaveAudienceIn.getAudienceId());
		campaignActionSaveAudience.setAudienceName(campaignActionSaveAudienceIn.getAudienceName());
		return campaignActionSaveAudience;
	}
	
	private CampaignActionWait initCampaignActionWait(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignActionWait campaignActionWait = new CampaignActionWait();
		CampaignActionWaitIn campaignActionWaitIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignActionWaitIn.class);
		campaignActionWait.setName(campaignActionWaitIn.getName());
		campaignActionWait.setItemId(campaignNodeChainIn.getItemId());
		campaignActionWait.setCampaignHeadId(campaignHeadId);
		campaignActionWait.setType(campaignActionWaitIn.getType());
		campaignActionWait.setRelativeValue(campaignActionWaitIn.getRelativeValue());
		campaignActionWait.setRelativeType(campaignActionWaitIn.getRelativeType());
		campaignActionWait.setSpecificTime(campaignActionWait.getSpecificTime());
		return campaignActionWait;
	}
	
	private CampaignDecisionTag initCampaignDecisionTag(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionTag campaignDecisionTag = new CampaignDecisionTag();
		CampaignDecisionTagIn campaignDecisionTagIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionTagIn.class);
		campaignDecisionTag.setName(campaignDecisionTagIn.getName());
		campaignDecisionTag.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionTag.setCampaignHeadId(campaignHeadId);
//		campaignDecisionTag.setTagIds(campaignDecisionTagIn.getTagIds());//TO DO:关联关系写入到campaign_decision_tag_map表
		return campaignDecisionTag;
	}
	
	private CampaignDecisionPrvtFriends initCampaignDecisionPrvtFriends(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionPrvtFriends campaignDecisionPrvtFriends = new CampaignDecisionPrvtFriends();
		CampaignDecisionPrvtFriendsIn campaignDecisionPrvtFriendsIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionPrvtFriendsIn.class);
		campaignDecisionPrvtFriends.setName(campaignDecisionPrvtFriendsIn.getName());
		campaignDecisionPrvtFriends.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionPrvtFriends.setCampaignHeadId(campaignHeadId);
		campaignDecisionPrvtFriends.setPrvtId(campaignDecisionPrvtFriendsIn.getPrvtId());
		campaignDecisionPrvtFriends.setPrvtName(campaignDecisionPrvtFriendsIn.getPrvtName());
		campaignDecisionPrvtFriends.setGroupName(campaignDecisionPrvtFriendsIn.getGroupName());
		campaignDecisionPrvtFriends.setRefreshInterval(campaignDecisionPrvtFriendsIn.getRefreshInterval());
		campaignDecisionPrvtFriends.setRefreshIntervalType(campaignDecisionPrvtFriendsIn.getRefreshIntervalType());
		return campaignDecisionPrvtFriends;
	}
	
	private CampaignDecisionPubFans initCampaignDecisionPubFans(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionPubFans campaignDecisionPubFans = new CampaignDecisionPubFans();
		CampaignDecisionPubFansIn campaignDecisionPubFansIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionPubFansIn.class);
		campaignDecisionPubFans.setName(campaignDecisionPubFansIn.getName());
		campaignDecisionPubFans.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionPubFans.setCampaignHeadId(campaignHeadId);
		campaignDecisionPubFans.setPubId(campaignDecisionPubFansIn.getPubId());
		campaignDecisionPubFans.setPubName(campaignDecisionPubFansIn.getPubName());
		campaignDecisionPubFans.setSubscribeTime(campaignDecisionPubFansIn.getSubscribeTime());
		campaignDecisionPubFans.setRefreshInterval(campaignDecisionPubFansIn.getRefreshInterval());
		campaignDecisionPubFans.setRefreshIntervalType(campaignDecisionPubFansIn.getRefreshIntervalType());
		return campaignDecisionPubFans;
	}
	
	private CampaignDecisionWechatForward initCampaignDecisionWechatForward(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionWechatForward campaignDecisionWechatForward = new CampaignDecisionWechatForward();
		CampaignDecisionWechatForwardIn campaignDecisionWechatForwardIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionWechatForwardIn.class);
		campaignDecisionWechatForward.setName(campaignDecisionWechatForwardIn.getName());
		campaignDecisionWechatForward.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionWechatForward.setCampaignHeadId(campaignHeadId);
		campaignDecisionWechatForward.setPubId(campaignDecisionWechatForwardIn.getPubId());
		campaignDecisionWechatForward.setPubName(campaignDecisionWechatForwardIn.getPubName());
		campaignDecisionWechatForward.setRefreshInterval(campaignDecisionWechatForwardIn.getRefreshInterval());
		campaignDecisionWechatForward.setRefreshIntervalType(campaignDecisionWechatForwardIn.getRefreshIntervalType());
		campaignDecisionWechatForward.setWechatH5Id(campaignDecisionWechatForwardIn.getWechatH5Id());
		campaignDecisionWechatForward.setWechatH5Name(campaignDecisionWechatForwardIn.getWechatH5Name());
		campaignDecisionWechatForward.setForwardTimes(campaignDecisionWechatForwardIn.getForwardTimes());
		return campaignDecisionWechatForward;
	}
	
	private CampaignDecisionWechatRead initCampaignDecisionWechatRead(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionWechatRead campaignDecisionWechatRead = new  CampaignDecisionWechatRead();
		CampaignDecisionWechatReadIn campaignDecisionWechatReadIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionWechatReadIn.class);
		campaignDecisionWechatRead.setName(campaignDecisionWechatReadIn.getName());
		campaignDecisionWechatRead.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionWechatRead.setCampaignHeadId(campaignHeadId);
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
	
	private CampaignDecisionWechatSent initCampaignDecisionWechatSent(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionWechatSent campaignDecisionWechatSent = new  CampaignDecisionWechatSent();
		CampaignDecisionWechatSentIn campaignDecisionWechatSentIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionWechatSentIn.class);
		campaignDecisionWechatSent.setName(campaignDecisionWechatSentIn.getName());
		campaignDecisionWechatSent.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionWechatSent.setCampaignHeadId(campaignHeadId);
		campaignDecisionWechatSent.setPubId(campaignDecisionWechatSentIn.getPubId());
		campaignDecisionWechatSent.setPubName(campaignDecisionWechatSentIn.getPubName());
		campaignDecisionWechatSent.setRefreshInterval(campaignDecisionWechatSentIn.getRefreshInterval());
		campaignDecisionWechatSent.setRefreshIntervalType(campaignDecisionWechatSentIn.getRefreshIntervalType());
		campaignDecisionWechatSent.setWechatH5Id(campaignDecisionWechatSentIn.getWechatH5Id());
		campaignDecisionWechatSent.setWechatH5Name(campaignDecisionWechatSent.getWechatH5Name());
		return campaignDecisionWechatSent;
	}
	
	private CampaignDecisionPropCompare initCampaignDecisionPropCompare(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignDecisionPropCompare campaignDecisionPropCompare = new  CampaignDecisionPropCompare();
		CampaignDecisionPropCompareIn campaignDecisionPropCompareIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignDecisionPropCompareIn.class);
		campaignDecisionPropCompare.setName(campaignDecisionPropCompareIn.getName());
		campaignDecisionPropCompare.setItemId(campaignNodeChainIn.getItemId());
		campaignDecisionPropCompare.setCampaignHeadId(campaignHeadId);
		campaignDecisionPropCompare.setPropType(campaignDecisionPropCompareIn.getPropType());
		campaignDecisionPropCompare.setRule(campaignDecisionPropCompareIn.getRule());
		return campaignDecisionPropCompare;
	}
	
	private CampaignBody initCampaignBody(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignBody campaignBody = new CampaignBody();
		campaignBody.setHeadId(campaignHeadId);
		campaignBody.setItemType(campaignNodeChainIn.getItemType());
		campaignBody.setNodeType(campaignNodeChainIn.getNodeType());
		campaignBody.setItemId(campaignNodeChainIn.getItemId());
		campaignBody.setPosX(campaignNodeChainIn.getPosX());
		campaignBody.setPosY(campaignNodeChainIn.getPosY());
		campaignBody.setPosZ(campaignNodeChainIn.getPosZ());
		return campaignBody;
	}
	
	private CampaignTriggerTimer initCampaignTriggerTimer(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignTriggerTimer campaignTriggerTimer = new CampaignTriggerTimer();
		CampaignTriggerTimerIn campaignTriggerTimerIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignTriggerTimerIn.class);
		campaignTriggerTimer.setCampaignHeadId(campaignHeadId);
		campaignTriggerTimer.setItemId(campaignNodeChainIn.getItemId());
		campaignTriggerTimer.setName(campaignTriggerTimerIn.getName());
		campaignTriggerTimer.setStartTime(DateUtil.getDateFromString(campaignTriggerTimerIn.getStartTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
		campaignTriggerTimer.setEndTime(DateUtil.getDateFromString(campaignTriggerTimerIn.getEndTime(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
		return campaignTriggerTimer;
	}
	
	private CampaignAudienceTarget initCampaignAudienceTarget(CampaignNodeChainIn campaignNodeChainIn,int campaignHeadId) {
		CampaignAudienceTarget campaignAudienceTarget = new CampaignAudienceTarget();
		CampaignAudienceTargetIn campaignAudienceTargetIn = jacksonObjectMapper.convertValue(campaignNodeChainIn.getInfo(), CampaignAudienceTargetIn.class);
		campaignAudienceTarget.setName(campaignAudienceTargetIn.getName());
		campaignAudienceTarget.setCampaignHeadId(campaignHeadId);
		campaignAudienceTarget.setItemId(campaignNodeChainIn.getItemId());
		campaignAudienceTarget.setSegmentationId(campaignAudienceTargetIn.getSegmentationId());
		campaignAudienceTarget.setSegmentationName(campaignAudienceTargetIn.getSegmentationName());
		campaignAudienceTarget.setAllowedNew(campaignAudienceTargetIn.getAllowedNew());
		campaignAudienceTarget.setRefreshInterval(campaignAudienceTargetIn.getRefreshInterval());
		campaignAudienceTarget.setRefreshIntervalType(campaignAudienceTargetIn.getRefreshIntervalType());
		return campaignAudienceTarget;
	}
}
