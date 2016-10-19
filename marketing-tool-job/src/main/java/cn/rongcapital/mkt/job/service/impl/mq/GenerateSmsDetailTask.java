package cn.rongcapital.mkt.job.service.impl.mq;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.SmsDetailSendStateEnum;
import cn.rongcapital.mkt.common.enums.SmsTargetAudienceTypeEnum;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.*;
import cn.rongcapital.mkt.po.mongodb.Segment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by byf on 10/18/16.
 */
@Service
public class generateSmsDetailTask implements TaskService {

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

    private final String SEGMENTATION_HEAD_ID = "segmentation_head_id";

    @Override
    public void task(Integer taskId) {

    }

    @Override
    public void task(String taskHeadIdStr) {
        Long taskHeadId = Long.valueOf(taskHeadIdStr);

        //1根据headId选出模板内容
        SmsTaskHead paramSmsTaskHead = new SmsTaskHead();
        paramSmsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSmsTaskHead.setId(taskHeadId);
        List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectList(paramSmsTaskHead);
        if(CollectionUtils.isEmpty(smsTaskHeads)) return;
        SmsTaskHead targetHead = smsTaskHeads.get(0);
        String taskTemplateContent = targetHead.getSmsTaskTemplateContent();

        //2根据headId依次选出受众人群
        Set<String> targetDistinctReceiveMobiles = new HashSet<>();
        SmsTaskBody paramSmsTaskBody = new SmsTaskBody();
        paramSmsTaskBody.setSmsTaskHeadId(taskHeadId);
        paramSmsTaskBody.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsTaskBody> smsTaskBodies = smsTaskBodyDao.selectList(paramSmsTaskBody);
        if(CollectionUtils.isEmpty(smsTaskBodies)) return;
        for(SmsTaskBody smsTaskBody : smsTaskBodies){
            List<String> receiveMobileList = queryReceiveMobileListByTargetAudienceIdAndType(smsTaskBody);
            if(CollectionUtils.isEmpty(receiveMobileList)) continue;
            targetDistinctReceiveMobiles.addAll(receiveMobileList);
        }

        //3将受众人群+模板内容+受众类型存入Task_Detail表中
        for(String distinctReceiveMobile : targetDistinctReceiveMobiles){
            SmsTaskDetail smsTaskDetail = new SmsTaskDetail();
            smsTaskDetail.setReceiveMobile(distinctReceiveMobile);
            smsTaskDetail.setSendMessage(targetHead.getSmsTaskTemplateContent());
            smsTaskDetail.setSendTime(new Date(System.currentTimeMillis()));
            smsTaskDetail.setSmsTaskHeadId(taskHeadId);
            smsTaskDetailDao.insert(smsTaskDetail);

            SmsTaskDetailState smsTaskDetailState = new SmsTaskDetailState();
            smsTaskDetailState.setSmsTaskDetailId(smsTaskDetail.getId());
            smsTaskDetailState.setSmsTaskSendStatus(SmsDetailSendStateEnum.SMS_DETAIL_WAITING.getStateCode());
            smsTaskDetailStateDao.insert(smsTaskDetailState);
        }

        //Todo:4检测TaskHead的发送状态
        
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
        List<cn.rongcapital.mkt.po.mongodb.Segment> segmentList = mongoTemplate.find(query,cn.rongcapital.mkt.po.mongodb.Segment.class);

        //2构造出dataParty的IdList的集合
        if(CollectionUtils.isEmpty(segmentList)) return null;
        List<Long> dataPartyIdList = new ArrayList<>();
        for(Segment segment : segmentList){
            if(segment.getDataId() != null){
                dataPartyIdList.add(Long.valueOf(segment.getDataId()));
            }
        }

        //3将选出来的这些数据做缓存存到cache表中,一开始先一条一条插入,后续优化成使用batchInsert进行插入
        if(CollectionUtils.isEmpty(dataPartyIdList)) return null;
        cacheDataPartyIdInSmsAudienceCache(taskHeadId, targetId, dataPartyIdList);

        //4根据dataPartyIdList选出相应的不同的mobile(去重),然后做为返回值返回
        List<String> distinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList);
        if(CollectionUtils.isEmpty(distinctMobileList)) return null;
        return distinctMobileList;
    }

    private List<String> queryAndCacheAudienceDetailByAudienceListId(Long taskHeadId,Long targetId) {
        //1将data_party的Id从audienceListPartyMap表中取出来
        AudienceListPartyMap paramAudienceListPartyMap = new AudienceListPartyMap();
        paramAudienceListPartyMap.setAudienceListId(targetId.intValue());
        paramAudienceListPartyMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
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
        List<String> distinctMobileList = dataPartyDao.selectDistinctMobileListByIdList(dataPartyIdList);
        if(CollectionUtils.isEmpty(distinctMobileList)) return null;
        return distinctMobileList;
    }

    private void cacheDataPartyIdInSmsAudienceCache(Long taskHeadId, Long targetId, List<Long> dataPartyIdList) {
        for(Long dataPartyId : dataPartyIdList){
            SmsTaskTargetAudienceCache smsTaskTargetAudienceCache = new SmsTaskTargetAudienceCache();
            smsTaskTargetAudienceCache.setTaskHeadId(taskHeadId);
            smsTaskTargetAudienceCache.setTargetId(targetId);
            smsTaskTargetAudienceCache.setDataPartyId(dataPartyId);
            smsTaskTargetAudienceCache.setTargetType(SmsTargetAudienceTypeEnum.SMS_TARGET_AUDIENCE.getTypeCode());
            smsTaskTargetAudienceCacheDao.insert(smsTaskTargetAudienceCache);
        }
    }
}
