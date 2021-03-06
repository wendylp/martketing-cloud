package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.UserSessionUtil;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.po.CampaignAudienceTarget;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.service.CampaignDetailService;
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
	private CampaignBodyDao campaignBodyDao;
	@Autowired
	private TaskScheduleDao taskScheduleDao;
	@Autowired
	private CampaignDetailService campaignDetailService; // 活动统计

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(CampaignHeaderUpdateServiceImpl.class);


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

        //modify by xiexiaoliang at v1.8 删掉已发布的状态，启动后获得状态变为 "活动中"  ----start
//        if (triggerType != null && triggerType.byteValue() == ApiConstant.CAMPAIGN_ITEM_TRIGGER_MANUAL &&
//                    ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH == publishStatus.byteValue()) {
        if (ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH == publishStatus.byteValue()){
        //modify by xiexiaoliang at v1.8 删掉已发布的状态，启动后获得状态变为 "活动中"  ----end
            publishStatus = ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS;
        }

		CampaignHead t = new CampaignHead();
        t.setId(campaignHeadId);
    	t.setName(body.getCampaignName());
    	t.setPublishStatus(publishStatus);
		if (ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS == publishStatus.byteValue()) {
			t.setStartTime(new Date()); // @since 1.9 记录活动启动时间
		} else if (ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH == publishStatus.byteValue()) {
			t.setEndTime(new Date()); // @since 1.9 记录活动手动停止时间
		}
    	campaignHeadDao.updateById(t);

		if (ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS == publishStatus.byteValue()) {
			this.campaignDetailService.saveCampaignDetail(campaignHeadId); // @since 1.9 记录活动统计数据
		} else if (ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH == publishStatus.byteValue()) {
			this.campaignDetailService.updateCampaignDetailMemberTotal(campaignHeadId); // @since 1.9 记录活动统计数据
		}

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
        logger.info("CampaignHead Info: 开始修改相关数据");
        Integer campaignHeadId = existsCampaignHead.getId();
        Byte oldPublishStatus = existsCampaignHead.getPublishStatus();

        //业务逻辑描述：如果是手动启动的，设置当前状态为已发布，
        if (triggerType != null
                && (triggerType.byteValue() == ApiConstant.CAMPAIGN_ITEM_TRIGGER_MANUAL || triggerType.byteValue() == ApiConstant.CAMPAIGN_ITEM_EVENT_MANUAL)) {
            if (ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH == publishStatus) {
                logger.info("CampaignHeader Info: 进入第一重处理逻辑" );
                changeSegmentReferCampaignCount(campaignHeadId, Integer.valueOf(1));
                taskScheduleDao.activateTaskByCampaignHeadId(campaignHeadId);
            } else if (ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH == publishStatus ||
                               ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH == publishStatus) {
                logger.info("CampaignHeader Info: 进入第二重处理逻辑" );
                if (oldPublishStatus != null &&
                            oldPublishStatus != ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH) {
                    changeSegmentReferCampaignCount(campaignHeadId, Integer.valueOf(-1));
                }
                logger.info("CampaignHeader Info: 进入第三重处理逻辑" );
                //taskScheduleDao.deActivateTaskByCampaignHeadId(campaignHeadId);
            }
        } else {

            logger.info("CampaignHeader Info:进入活动预约的处理逻辑");
            if (ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH == publishStatus) {
                changeSegmentReferCampaignCount(campaignHeadId, Integer.valueOf(1));
            }

            TaskSchedule taskScheduleT = new TaskSchedule();
            taskScheduleT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            taskScheduleT.setCampaignHeadId(campaignHeadId);
            taskScheduleT.setServiceName(ApiConstant.TASK_NAME_CAMPAIGN_TRUGGER_TIME);
            List<TaskSchedule> taskScheduleList = taskScheduleDao.selectList(taskScheduleT);
            logger.info("CampaignHeader Info:选出的需要更新的任务总数" + taskScheduleList.size() );
            if(CollectionUtils.isNotEmpty(taskScheduleList)){
                for(TaskSchedule taskSchedule : taskScheduleList) {
                    if(publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH){
                        logger.info("CampaignHeader Info: 设置task_status为0");
                        taskSchedule.setTaskStatus(ApiConstant.TASK_STATUS_VALID);
                        taskSchedule.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
                    } else if(publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH ||
                                      publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH) {
                        taskSchedule.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
                        taskSchedule.setStatus(ApiConstant.TASK_STATUS_INVALID);
                    } else {
                        continue;
                    }
                    logger.info("CampaignHeader Info:当前任务的taskStatus: " + taskSchedule.getTaskStatus());
                    taskScheduleDao.updateById(taskSchedule);
                    logger.info("CampaignHeader Info:执行跟新任务后的taskStatus: " );
                }
            }
        }

	}

    /**
     * 修改细分被活动引用了多少次
     * @param campaignHeadId
     * @param referCampaignCount
     */
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
                if (referCampaignCount.intValue() < 0) {
                    Integer existsReferCount = segmentationHead.getReferCampaignCount();
                    if (existsReferCount.intValue() < 0) {
                        continue;
                    }
                }
                segmentationHeadDao.incrementReferCampaignCount(segmentationHead.getId(), referCampaignCount);
            }
        }
    }

    /**
     * 如果查找到数据进行状态机制的校验
     * 1、如果要调整的状态为未发布，当前状态不是未发布、已发布，则不能修改
     * 2、如果要调整的状态为已发布，当前状态不是未发布，则不能修改
     * 3、当前要调整的状态为已结束，当前状态不是活动中，则不能调整
     * @param existsCampaignHead
     * @param publishStatus
     * @return
     */
	private BaseOutput validate(CampaignHead existsCampaignHead, byte publishStatus) {
        BaseOutput ur = null;
        if(existsCampaignHead != null) {
             Byte existsPublishStatus = existsCampaignHead.getPublishStatus();
             if (publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH &&
                         (existsPublishStatus != ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH &&
                                  existsPublishStatus != ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH)) {
                 logger.info("CampaingnHeaderUpdate Info:第一次验证拦住了数据");
                 return getResponse(existsPublishStatus);
             } else if (publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH &&
                    existsPublishStatus != ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH) {
                 logger.info("CampaignHeaderUpdate Info:第二次验证拦住了数据");
                 return getResponse(existsPublishStatus);
             } else if (publishStatus == ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH &&
                    existsPublishStatus != ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS) {
                 logger.info("CampaignHeaderUpdate Info:第三次验证拦住了数据");
                 return getResponse(existsPublishStatus);
             }

		 } else {
			ur = new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
								ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(),
								ApiConstant.INT_ZERO,null);
		 }
        logger.info("CampaignHeaderUpdate Info:数据通过了验证");
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
