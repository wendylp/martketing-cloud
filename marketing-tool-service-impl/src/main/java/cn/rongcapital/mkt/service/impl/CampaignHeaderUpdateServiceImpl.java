package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.job.service.impl.mq.BaseMQService;
import cn.rongcapital.mkt.po.CampaignAudienceTarget;
import cn.rongcapital.mkt.po.SegmentationHead;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.UserSessionUtil;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.service.CampaignHeaderUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CampaignHeadUpdateIn;

@Service
public class CampaignHeaderUpdateServiceImpl implements CampaignHeaderUpdateService {

    @Autowired
    private CampaignAudienceTargetDao campaignAudienceTargetDao;

    @Autowired
    private SegmentationHeadDao segmentationHeadDao;

	@Autowired
	private CampaignHeadDao campaignHeadDao;
	@Autowired
	private TaskScheduleDao taskScheduleDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput campaignHeaderUpdate(CampaignHeadUpdateIn body, SecurityContext securityContext) {
        Integer campaignHeadId = body.getCampaignId();
        Byte publishStatus = body.getPublishStatus();

        CampaignHead existsCampaignHead = queryCampaignHead(campaignHeadId);
		BaseOutput ur = validate(existsCampaignHead, publishStatus);
		if(null != ur) {
			return ur;
		}

        Byte triggerType = body.getTriggerType();
        modifyReferData(existsCampaignHead, publishStatus, triggerType);

        if (triggerType != null && triggerType.byteValue() == ApiConstant.CAMPAIGN_ITEM_TRIGGER_MANUAL &&
                    ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH == publishStatus.byteValue()) {
            publishStatus = ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS;
        }
		CampaignHead t = new CampaignHead();
        t.setId(campaignHeadId);
    	t.setName(body.getCampaignName());
    	t.setPublishStatus(publishStatus);
    	campaignHeadDao.updateById(t);

    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("oper", UserSessionUtil.getUserNameByUserToken());//TODO:获取当前用户名
    	map.put("updatetime", DateUtil.getStringFromDate(new Date(), ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
    	map.put("id", t.getId());
    	ur = new BaseOutput(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
    	ur.getData().add(map);
    	ur.setTotal(ur.getData().size());
    	return ur;
	}

    @Override
    public void decreaseSegmentReferCampaignCount(Integer campaignHeadId) {
        changeSegmentReferCampaignCount(campaignHeadId, Integer.valueOf(-1));
    }

	private void modifyReferData(CampaignHead existsCampaignHead, byte publishStatus, Byte triggerType) {
        Integer campaignHeadId = existsCampaignHead.getId();
        // update segment refer count
        if (ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH == publishStatus) {
            changeSegmentReferCampaignCount(campaignHeadId, Integer.valueOf(1));

        } else if ((ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH == publishStatus &&
                ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH == existsCampaignHead.getPublishStatus()) ||
                ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH == publishStatus) {
            changeSegmentReferCampaignCount(campaignHeadId, Integer.valueOf(-1));
        }

        // update schedule
        if (triggerType != null && triggerType.byteValue() == ApiConstant.CAMPAIGN_ITEM_TRIGGER_MANUAL) {
            if (ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH == publishStatus) {
                taskScheduleDao.activateTaskByCampaignHeadId(campaignHeadId);
            } else if (ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH == publishStatus ||
                               ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH == publishStatus) {
                taskScheduleDao.deActivateTaskByCampaignHeadId(campaignHeadId);
            }
        } else {
            TaskSchedule taskScheduleT = new TaskSchedule();
            taskScheduleT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            taskScheduleT.setCampaignHeadId(campaignHeadId);
            taskScheduleT.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_TRUGGER_TIME);
            List<TaskSchedule> taskScheduleList = taskScheduleDao.selectList(taskScheduleT);
            if(CollectionUtils.isNotEmpty(taskScheduleList)){
                for(TaskSchedule taskSchedule : taskScheduleList) {
                    if(publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH){
                        taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_VALID);
                    } else if(publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH ||
                                      publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH) {
                        taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_INVALID);
                    } else {
                        continue;
                    }
                    taskScheduleDao.updateById(taskSchedule);
                }
            }
        }

	}

    private void changeSegmentReferCampaignCount(Integer campaignHeadId, Integer referCampaignCount) {
        CampaignAudienceTarget campaignAudienceTarget = new CampaignAudienceTarget();
        campaignAudienceTarget.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        campaignAudienceTarget.setCampaignHeadId(campaignHeadId);
        List<CampaignAudienceTarget> campaignAudienceTargetList =
                campaignAudienceTargetDao.selectList(campaignAudienceTarget);
        if(CollectionUtils.isEmpty(campaignAudienceTargetList)) {
            return;
        }
        for(CampaignAudienceTarget tempCampaignAudienceTarget : campaignAudienceTargetList) {
            SegmentationHead sh = new SegmentationHead();
            sh.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            sh.setId(tempCampaignAudienceTarget.getSegmentationId());
            List<SegmentationHead> segmentationHeadList = segmentationHeadDao.selectList(sh);
            if (CollectionUtils.isEmpty(segmentationHeadList)) {
                continue;
            }
            for(SegmentationHead segmentationHead : segmentationHeadList) {
                segmentationHeadDao.incrementReferCampaignCount(segmentationHead.getId(), referCampaignCount);
            }
        }
    }

	private BaseOutput validate(CampaignHead existsCampaignHead, byte publishStatus) {
        BaseOutput ur = null;
        if(existsCampaignHead != null) {
             Byte existsPublishStatus = existsCampaignHead.getPublishStatus();
             if (publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH &&
                         (existsPublishStatus != ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH &&
                                  existsPublishStatus != ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH)) {
                 return getResponse(existsPublishStatus);
             } else if (publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH &&
                    existsPublishStatus != ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH) {
                 return getResponse(existsPublishStatus);
             } else if (publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH &&
                    existsPublishStatus != ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS) {
                 return getResponse(existsPublishStatus);
             }

		 } else {
			ur = new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
								ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(),
								ApiConstant.INT_ZERO,null);
		 }
		 return ur;
	 }

    private CampaignHead queryCampaignHead(Integer campaignId) {
        CampaignHead t = new CampaignHead();
        t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        t.setId(campaignId);
        List<CampaignHead> campaignHeadList = campaignHeadDao.selectList(t);
        if (CollectionUtils.isNotEmpty(campaignHeadList)) {
            return campaignHeadList.get(0);
        }
        return null;
    }

    private BaseOutput getResponse(Byte existsPublishStatus) {
        BaseOutput baseOutput = null;
        if(existsPublishStatus.byteValue() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS) {
            baseOutput = new BaseOutput(ApiErrorCode.BIZ_ERROR_CANPAIGN_IN_PROGRESS.getCode(),
                                       ApiErrorCode.BIZ_ERROR_CANPAIGN_IN_PROGRESS.getMsg(),
                                       ApiConstant.INT_ZERO,null);
        } else if (existsPublishStatus.byteValue() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH) {
            baseOutput = new BaseOutput(ApiErrorCode.BIZ_ERROR_CANPAIGN_FINISH.getCode(),
                                       ApiErrorCode.BIZ_ERROR_CANPAIGN_FINISH.getMsg(),
                                       ApiConstant.INT_ZERO,null);
        } else if (existsPublishStatus.byteValue() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH) {
            baseOutput = new BaseOutput(ApiErrorCode.BIZ_ERROR_CANPAIGN_NOT_PUBLISH.getCode(),
                                               ApiErrorCode.BIZ_ERROR_CANPAIGN_NOT_PUBLISH.getMsg(),
                                               ApiConstant.INT_ZERO,null);
        } else if (existsPublishStatus.byteValue() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH) {
            baseOutput = new BaseOutput(ApiErrorCode.BIZ_ERROR_CANPAIGN_PUBLISHED.getCode(),
                                               ApiErrorCode.BIZ_ERROR_CANPAIGN_PUBLISHED.getMsg(),
                                               ApiConstant.INT_ZERO,null);
        }
        return baseOutput;
    }
}
