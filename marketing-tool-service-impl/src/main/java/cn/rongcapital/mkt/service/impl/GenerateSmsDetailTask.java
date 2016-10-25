package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.SmsDetailSendStateEnum;
import cn.rongcapital.mkt.common.enums.SmsTargetAudienceTypeEnum;
import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.*;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.service.MQTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by byf on 10/18/16.
 */
@Service
public class GenerateSmsDetailTask implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SmsTaskDetailDao smsTaskDetailDao;

    @Autowired
    private SmsTaskDetailStateDao smsTaskDetailStateDao;

    @Autowired
    private SmsTaskHeadDao smsTaskHeadDao;

    @Autowired
    private SmsTaskBodyDao smsTaskBodyDao;

    @Autowired
    private SmsTaskTargetAudienceCacheDao smsTaskTargetAudienceCacheDao;

    @Autowired
    private AudienceListDao audienceListDao;

    @Autowired
    private AudienceListPartyMapDao audienceListPartyMapDao;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MQTopicService mqTopicService;

    private final String SEGMENTATION_HEAD_ID = "segmentation_head_id";
    private final int PAGE_SIZE = 10000;

    @Override
    public void task(Integer taskId) {

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void task(String taskHeadIdStr) {
        logger.info("taskHeadId :" + taskHeadIdStr);
        Long taskHeadId = Long.valueOf(taskHeadIdStr);

        //1根据headId选出模板内容
        SmsTaskHead paramSmsTaskHead = new SmsTaskHead();
        paramSmsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSmsTaskHead.setId(taskHeadId);
        List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectList(paramSmsTaskHead);
        if(CollectionUtils.isEmpty(smsTaskHeads)) return;

        SmsTaskHead targetHead = smsTaskHeads.get(0);

        //2根据headId依次选出受众人群
        Set<String> targetDistinctReceiveMobiles = new HashSet<>();
        SmsTaskBody paramSmsTaskBody = new SmsTaskBody();
        paramSmsTaskBody.setSmsTaskHeadId(taskHeadId);
        paramSmsTaskBody.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsTaskBody> smsTaskBodies = smsTaskBodyDao.selectList(paramSmsTaskBody);
        if(CollectionUtils.isEmpty(smsTaskBodies)) return;
        for(SmsTaskBody smsTaskBody : smsTaskBodies){
            List<String> receiveMobileList = queryReceiveMobileListByTargetAudienceIdAndType(smsTaskBody);
            logger.info("sms list distinct mobile size: " + receiveMobileList.size());
            if(CollectionUtils.isEmpty(receiveMobileList)) continue;
            targetDistinctReceiveMobiles.addAll(receiveMobileList);
        }

        logger.info("sms task audience size:" + targetDistinctReceiveMobiles.size());
        //3将受众人群+模板内容+受众类型存入Task_Detail表中
        insertDataToSmsDetailAndDetailState(taskHeadId, targetHead, targetDistinctReceiveMobiles);

        //4更新SmsTaskHead表的人群相关的字段
        targetHead.setAudienceGenerateStatus(ApiConstant.INT_ZERO);
        targetHead.setTotalCoverNum(targetDistinctReceiveMobiles.size());
        targetHead.setWaitingNum(targetDistinctReceiveMobiles.size());
        smsTaskHeadDao.updateById(targetHead);
        logger.info("sms task head update info");

        smsTaskHeads = smsTaskHeadDao.selectList(paramSmsTaskHead);
        if(CollectionUtils.isEmpty(smsTaskHeads)) return;
        SmsTaskHead currentTaskHead = smsTaskHeads.get(0);
        if(currentTaskHead.getSmsTaskStatus() == SmsTaskStatusEnum.TASK_EXECUTING.getStatusCode()){
            //Todo:4检测TaskHead的发送状态
            mqTopicService.sendSmsByTaskId(taskHeadIdStr);
        }
        
    }

    private void insertDataToSmsDetailAndDetailState(Long taskHeadId, SmsTaskHead targetHead, Set<String> targetDistinctReceiveMobiles) {
        List<SmsTaskDetail> smsTaskDetailList = new LinkedList<>();
        List<SmsTaskDetailState> smsTaskDetailStateList = new LinkedList<>();
        for(String distinctReceiveMobile : targetDistinctReceiveMobiles){
            SmsTaskDetail smsTaskDetail = new SmsTaskDetail();
            smsTaskDetail.setReceiveMobile(distinctReceiveMobile);
            smsTaskDetail.setSendMessage(targetHead.getSmsTaskTemplateContent());
            smsTaskDetail.setSendTime(new Date(System.currentTimeMillis()));
            smsTaskDetail.setSmsTaskHeadId(taskHeadId);
            smsTaskDetailList.add(smsTaskDetail);

            SmsTaskDetailState smsTaskDetailState = new SmsTaskDetailState();
            smsTaskDetailState.setSmsTaskDetailId(smsTaskDetail.getId());
            smsTaskDetailState.setSmsTaskSendStatus(SmsDetailSendStateEnum.SMS_DETAIL_WAITING.getStateCode());
            smsTaskDetailStateList.add(smsTaskDetailState);
        }

        int totalNum = (smsTaskDetailList.size() + PAGE_SIZE) / PAGE_SIZE;
        for(int index = 0; index < totalNum; index++){
            if(index == totalNum-1){
                logger.info("insert index : " + index);
                smsTaskDetailDao.batchInsert(smsTaskDetailList.subList(index*PAGE_SIZE,smsTaskDetailList.size()));
                smsTaskDetailStateDao.batchInsert(smsTaskDetailStateList.subList(index*PAGE_SIZE,smsTaskDetailStateList.size()));
            }else{
                logger.info("insert index : " + index);
                smsTaskDetailDao.batchInsert(smsTaskDetailList.subList(index*PAGE_SIZE,(index + 1)*PAGE_SIZE-1));
                smsTaskDetailStateDao.batchInsert(smsTaskDetailStateList.subList(index*PAGE_SIZE,(index + 1)*PAGE_SIZE-1));
            }
        }
    }

    //Todo:可以进行分页处理来优化
    private List<String> queryReceiveMobileListByTargetAudienceIdAndType(SmsTaskBody smsTaskBody) {
        if(SmsTargetAudienceTypeEnum.SMS_TARGET_SEGMENTATION.getTypeCode() == smsTaskBody.getTargetType()){
            return queryAndCacheAudienceDetailBySegmentationId(smsTaskBody.getSmsTaskHeadId(),smsTaskBody.getTargetId());
        }else if(SmsTargetAudienceTypeEnum.SMS_TARGET_AUDIENCE.getTypeCode() == smsTaskBody.getTargetType()){
            return queryAndCacheAudienceDetailByAudienceListId(smsTaskBody.getSmsTaskHeadId(),smsTaskBody.getTargetId());
        }
        return null;
    }

    private List<String> queryAndCacheAudienceDetailBySegmentationId(Long taskHeadId,Long targetId) {
        //1根据segmentation的Id在Mongo中选出相应得细分,然后取出dataPartyId
        Query query = new Query(Criteria.where(SEGMENTATION_HEAD_ID).is(targetId));
        List<Segment> segmentList = mongoTemplate.find(query,Segment.class);
        logger.info("segment list size : " +segmentList.size());
        //2构造出dataParty的IdList的集合
        if(CollectionUtils.isEmpty(segmentList)) return null;
        List<Long> dataPartyIdList = new ArrayList<>();
        for(Segment segment : segmentList){
            if(segment.getDataId() != null){
                dataPartyIdList.add(Long.valueOf(segment.getDataId()));
            }
        }
        logger.info("dataParty id list size : " + dataPartyIdList.size());

        //3将选出来的这些数据做缓存存到cache表中,一开始先一条一条插入,后续优化成使用batchInsert进行插入
        if(CollectionUtils.isEmpty(dataPartyIdList)) return null;
        cacheDataPartyIdInSmsAudienceCache(taskHeadId, targetId, dataPartyIdList);

        //4根据dataPartyIdList选出相应的不同的mobile(去重),然后做为返回值返回
        Set<String> distinctMobileSet = new HashSet<>();
        List<String> subDistinctMobileList = null;
        int totalPage = (dataPartyIdList.size() + PAGE_SIZE)/PAGE_SIZE;
        for(int index = 0; index < totalPage; index++){
            if(index == totalPage - 1){
                subDistinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,dataPartyIdList.size() -1));
                logger.info("index : " + index);
            }else {
                subDistinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,(index+1) * PAGE_SIZE -1));
                logger.info("index : " + index);
            }
            distinctMobileSet.addAll(subDistinctMobileList);
        }
        logger.info("already search distinct mobile list " + subDistinctMobileList.size());
        List<String> distinctMobileList = new LinkedList<>();
        distinctMobileList.addAll(distinctMobileSet);
        if(CollectionUtils.isEmpty(distinctMobileList)) return null;
        return distinctMobileList;
    }

    private List<String> queryAndCacheAudienceDetailByAudienceListId(Long taskHeadId,Long targetId) {
        //1将data_party的Id从audienceListPartyMap表中取出来
        AudienceListPartyMap paramAudienceListPartyMap = new AudienceListPartyMap();
        paramAudienceListPartyMap.setAudienceListId(targetId.intValue());
        paramAudienceListPartyMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramAudienceListPartyMap.setPageSize(Integer.MAX_VALUE);
        List<AudienceListPartyMap> audienceListPartyMapList = audienceListPartyMapDao.selectList(paramAudienceListPartyMap);
        if(CollectionUtils.isEmpty(audienceListPartyMapList)) return null;

        //2构造出DataPartIdList得合集
        List<Long> dataPartyIdList = new ArrayList<>();
        for(AudienceListPartyMap audienceListPartyMap : audienceListPartyMapList){
            if(audienceListPartyMap.getPartyId() != null){
                dataPartyIdList.add(Long.valueOf(audienceListPartyMap.getPartyId()));
            }
        }
        if(CollectionUtils.isEmpty(dataPartyIdList)) return null;

        //3将选出来的这些数据做缓存存到cache表中,一开始先一条一条插入,后续优化成使用batchInsert进行插入
        cacheDataPartyIdInSmsAudienceCache(taskHeadId, targetId, dataPartyIdList);

        //4根据dataPartyIdList选出相应的不同的mobile(去重),然后做为返回值返回
        Set<String> distinctMobileSet = new HashSet<>();
        List<String> subDistinctMobileList = null;
        int totalPage = (dataPartyIdList.size() + PAGE_SIZE)/PAGE_SIZE;
        for(int index = 0; index < totalPage; index++){
            if(index == totalPage - 1){
                subDistinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,dataPartyIdList.size() -1));
                logger.info("index : " + index);
            }else {
                subDistinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList.subList(index*PAGE_SIZE,(index+1) * PAGE_SIZE -1));
                logger.info("index : " + index);
            }
            distinctMobileSet.addAll(subDistinctMobileList);
        }
        logger.info("already search distinct mobile list " + subDistinctMobileList.size());
        List<String> distinctMobileList = new LinkedList<>();
        distinctMobileList.addAll(distinctMobileSet);
        if(CollectionUtils.isEmpty(distinctMobileList)) return null;
        return distinctMobileList;
    }

    private void cacheDataPartyIdInSmsAudienceCache(Long taskHeadId, Long targetId, List<Long> dataPartyIdList) {
        logger.info("begin to cache target audience");
        List<SmsTaskTargetAudienceCache> smsTaskTargetAudienceCacheList = new LinkedList<>();
        for(Long dataPartyId : dataPartyIdList){
            SmsTaskTargetAudienceCache smsTaskTargetAudienceCache = new SmsTaskTargetAudienceCache();
            smsTaskTargetAudienceCache.setTaskHeadId(taskHeadId);
            smsTaskTargetAudienceCache.setTargetId(targetId);
            smsTaskTargetAudienceCache.setDataPartyId(dataPartyId);
            smsTaskTargetAudienceCache.setTargetType(SmsTargetAudienceTypeEnum.SMS_TARGET_AUDIENCE.getTypeCode());
            smsTaskTargetAudienceCacheList.add(smsTaskTargetAudienceCache);
        }

        int totalNum = (smsTaskTargetAudienceCacheList.size() + PAGE_SIZE) / PAGE_SIZE;
        for(int index = 0; index < totalNum; index++){
            if(index == totalNum-1){
                logger.info("insert index : " + index);
                smsTaskTargetAudienceCacheDao.batchInsert(smsTaskTargetAudienceCacheList.subList(index*PAGE_SIZE,smsTaskTargetAudienceCacheList.size()));
            }else{
                logger.info("insert index : " + index);
                smsTaskTargetAudienceCacheDao.batchInsert(smsTaskTargetAudienceCacheList.subList(index*PAGE_SIZE,(index + 1)*PAGE_SIZE-1));
            }
        }
    }
}
