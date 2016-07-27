package cn.rongcapital.mkt.service.impl;

import java.util.*;

import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.*;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.vo.out.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.UserSessionUtil;
import cn.rongcapital.mkt.service.CampaignHeaderGetService;

@Service
public class CampaignHeaderGetServiceImpl implements CampaignHeaderGetService {
	
	@Autowired
	CampaignHeadDao campaignHeadDao;

    @Autowired
    private CampaignTriggerTimerDao campaignTriggerTimerDao;

    @Autowired
    private CampaignAudienceTargetDao campaignAudienceTargetDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CampaignActionSendH5Dao campaignActionSendH5Dao;

    @Autowired
    private CampaignActionSendPrivtDao campaignActionSendPrivtDao;

    @Autowired
    private CampaignActionSendPubDao campaignActionSendPubDao;

    @Autowired
    private WechatAssetDao wechatAssetDao;

    @Override
	public CampaignHeaderGetOut campaignHeaderGet(String userToken, String ver, int campaignHeadId) {
		CampaignHead t = new CampaignHead();
		t.setId(campaignHeadId);
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		List<CampaignHead> list = campaignHeadDao.selectList(t);
		
		CampaignHeaderGetOut out = new CampaignHeaderGetOut(ApiConstant.INT_ZERO,
															ApiErrorCode.SUCCESS.getMsg(),
															ApiConstant.INT_ZERO);
		
		if(CollectionUtils.isNotEmpty(list)){
			CampaignHead obj = list.get(0);
			CampaignHeaderGetDataOut chgto = new CampaignHeaderGetDataOut();
			chgto.setCampaignName(obj.getName());
			chgto.setPublishStatus(obj.getPublishStatus());
			chgto.setId(obj.getId());
			chgto.setOper(UserSessionUtil.getUserNameByUserToken());
			chgto.setUpdatetime(DateUtil.getStringFromDate(obj.getUpdateTime(), 
								ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
			out.getDataCutom().add(chgto);
		}
		out.setTotal(out.getDataCutom().size());
		return out;
	}

	@Override
	public CampaignProfileOut campaignProfileList(String userToken, String ver, Integer campaignHeadId) {
        CampaignProfileOut campaignProfileOut = new CampaignProfileOut(ApiConstant.INT_ZERO,
                ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);
        queryAndSetTime(campaignHeadId, campaignProfileOut);
        queryAndSetAudience(campaignHeadId, campaignProfileOut);
        queryAndSetContent(campaignHeadId, campaignProfileOut);
        //queryTAndSetouchPopulation(wechatAccountSet, campaignProfileOut);
		return campaignProfileOut;
	}

    private void queryAndSetTime(Integer campaignHeadId, CampaignProfileOut out) {
        CampaignTriggerTimer triggerTimer = new CampaignTriggerTimer();
        triggerTimer.setCampaignHeadId(campaignHeadId);
        triggerTimer.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<CampaignTriggerTimer> campaignTriggerTimerList = campaignTriggerTimerDao.selectList(triggerTimer);
        if (!CollectionUtils.isEmpty(campaignTriggerTimerList)) {
            CampaignTriggerTimer campaignTriggerTimer = campaignTriggerTimerList.get(0);
            out.setStartTime(DateUtil.getStringFromDate(campaignTriggerTimer.getStartTime(),
                    ApiConstant.DATE_FORMAT_yyyy_MM_dd));
            out.setEndTime(DateUtil.getStringFromDate(campaignTriggerTimer.getEndTime(),
                    ApiConstant.DATE_FORMAT_yyyy_MM_dd));
        }
    }

    private void queryAndSetAudience(Integer campaignHeadId, CampaignProfileOut out) {
        List<CampaignAudienceTargetProfileOut> audienceTargetOutList = new ArrayList<>();
        CampaignAudienceTarget audienceTarget = new CampaignAudienceTarget();
        audienceTarget.setCampaignHeadId(campaignHeadId);
        audienceTarget.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        audienceTarget.setStartIndex(null);
        List<CampaignAudienceTarget> audienceTargetList = campaignAudienceTargetDao.selectList(audienceTarget);
        if (!CollectionUtils.isEmpty(audienceTargetList)) {
            Map<String, Long> populationCountMap = new HashMap<>();
            for (CampaignAudienceTarget tempCampaignAudienceTarget : audienceTargetList) {
                String audienceName = tempCampaignAudienceTarget.getName();
                Integer segmentHeadId = tempCampaignAudienceTarget.getSegmentationId();
                Long populationCount = populationCountMap.get(segmentHeadId.toString());
                if (populationCount == null) {
                    populationCount = Long.valueOf(mongoTemplate.count(
                            new Query(Criteria.where("segmentationHeadId").is(segmentHeadId)), Segment.class));
                    populationCountMap.put(segmentHeadId.toString(), populationCount);
                }

                CampaignAudienceTargetProfileOut audienceTargetProfileOut = new CampaignAudienceTargetProfileOut();
                audienceTargetProfileOut.setAudienceName(audienceName);
                audienceTargetProfileOut.setPopulationCount(populationCount);
                audienceTargetOutList.add(audienceTargetProfileOut);
            }
        }

        out.setAudienceTargetList(audienceTargetOutList);
    }

    private void queryAndSetContent(Integer campaignHeadId, CampaignProfileOut out) {
//        Set<String> wechatAccounts = new HashSet<>();
        List<CampaignContentOut> contentList = new ArrayList<>();
        CampaignActionSendPub campaignActionSendPub = new CampaignActionSendPub();
        campaignActionSendPub.setCampaignHeadId(campaignHeadId);
        campaignActionSendPub.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<CampaignActionSendPub> campaignActionSendPubList =
                campaignActionSendPubDao.selectList(campaignActionSendPub);
        if (!CollectionUtils.isEmpty(campaignActionSendPubList)) {
            for (CampaignActionSendPub sendPub : campaignActionSendPubList) {
                CampaignContentOut contentOut = new CampaignContentOut();
                contentOut.setContentType(ApiConstant.CAMPAIGN_CONTENT_WECHAT);
                contentOut.setContentName(sendPub.getName());
                contentList.add(contentOut);
//                wechatAccounts.add(sendPub.getPubId());
            }
        }

        CampaignActionSendH5 campaignActionSendH5 = new CampaignActionSendH5();
        campaignActionSendH5.setCampaignHeadId(campaignHeadId);
        campaignActionSendH5.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<CampaignActionSendH5> campaignActionSendH5List =
                campaignActionSendH5Dao.selectList(campaignActionSendH5);
        if (!CollectionUtils.isEmpty(campaignActionSendH5List)) {
            for (CampaignActionSendH5 sendH5 : campaignActionSendH5List) {
                CampaignContentOut contentOut = new CampaignContentOut();
                contentOut.setContentType(ApiConstant.CAMPAIGN_CONTENT_H5);
                contentOut.setContentName(sendH5.getName());
                contentList.add(contentOut);
//                wechatAccounts.add(sendH5.getPubId());
            }
        }

        CampaignActionSendPrivt campaignActionSendPrivt = new CampaignActionSendPrivt();
        campaignActionSendPrivt.setCampaignHeadId(campaignHeadId);
        campaignActionSendPrivt.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<CampaignActionSendPrivt> campaignActionSendPrivtList =
                campaignActionSendPrivtDao.selectList(campaignActionSendPrivt);
        if (!CollectionUtils.isEmpty(campaignActionSendPrivtList)) {
            for (CampaignActionSendPrivt sendPrivt : campaignActionSendPrivtList) {
                CampaignContentOut contentOut = new CampaignContentOut();
                contentOut.setContentType(ApiConstant.CAMPAIGN_CONTENT_PRIVATE_TEXT);
                contentOut.setContentName(sendPrivt.getName());
                contentList.add(contentOut);
            }
        }
        out.setContentList(contentList);
//        return wechatAccounts;
    }
/**
    private void queryTAndSetouchPopulation(Set<String> wechatAccountSet, CampaignProfileOut out) {
        if (wechatAccountSet.size() < 1) {
            return;
        }
        List<WechatAsset> wechatAssetList = wechatAssetDao.selectByWechatAccounts(wechatAccountSet);
        if (!CollectionUtils.isEmpty(wechatAssetList)) {
            List<CampaignTouchPopulationOut> touchPopulationList = new ArrayList<>();
            for (WechatAsset wechatAsset : wechatAssetList) {
                Integer assetType = wechatAsset.getAssetType();
                Integer totalCount = wechatAsset.getTotalCount();
                String assetName = wechatAsset.getAssetName();
                String assetFullName;
                if (ApiConstant.WECHAT_ASSET_SERVER_NUMBER == assetType) {
                    assetFullName = ApiConstant.WECHAT_TYPE_PUB + assetName;
                } else if (ApiConstant.WECHAT_ASSET_SUBSCRIPTION_NUMBER == assetType) {
                    assetFullName = ApiConstant.WECHAT_TYPE_SUB + assetName;
                } else if (ApiConstant.WECHAT_ASSET_PERSONAL_NUMBER == assetType) {
                    assetFullName = ApiConstant.WECHAT_TYPE_PRI + assetName;
                } else {
                    assetFullName = "[未知]" + assetName;
                }

                CampaignTouchPopulationOut touchPopulationOut = new CampaignTouchPopulationOut();
                touchPopulationOut.setPopulationName(assetFullName);
                touchPopulationOut.setPopulationCount(totalCount);
                touchPopulationList.add(touchPopulationOut);
            }
            out.setTouchPopulationList(touchPopulationList);
        }
    }

 **/

}
