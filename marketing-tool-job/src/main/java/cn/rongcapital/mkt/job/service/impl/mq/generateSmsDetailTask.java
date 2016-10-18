package cn.rongcapital.mkt.job.service.impl.mq;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.SmsTargetAudienceTypeEnum;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.po.SmsTaskHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byf on 10/18/16.
 */
@Service
public class generateSmsDetailTask implements TaskService {

    @Autowired
    private SmsTaskDetailDao smsTaskDetailDao;

    @Autowired
    private SmsTaskHeadDao smsTaskHeadDao;

    @Autowired
    private SmsTaskBodyDao smsTaskBodyDao;

    @Override
    public void task(Integer taskId) {

    }

    @Override
    public void task(String taskHeadIdStr) {
        Long taskHeadId = Long.valueOf(taskHeadIdStr);

        //Todo:1根据headId选出模板内容
        SmsTaskHead paramSmsTaskHead = new SmsTaskHead();
        paramSmsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSmsTaskHead.setId(taskHeadId);
        List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectList(paramSmsTaskHead);
        if(CollectionUtils.isEmpty(smsTaskHeads)) return;
        SmsTaskHead targetHead = smsTaskHeads.get(0);
        String taskTemplateContent = targetHead.getSmsTaskTemplateContent();

        //Todo:2根据headId依次选出受众人群
        List<String> targetDistinctReceiveMobiles = new ArrayList<>();
        SmsTaskBody paramSmsTaskBody = new SmsTaskBody();
        paramSmsTaskBody.setSmsTaskHeadId(taskHeadId);
        paramSmsTaskBody.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsTaskBody> smsTaskBodies = smsTaskBodyDao.selectList(paramSmsTaskBody);
        if(CollectionUtils.isEmpty(smsTaskBodies)) return;
        for(SmsTaskBody smsTaskBody : smsTaskBodies){
            List<String> receiveMobileList = queryReceiveMobileListByTargetAudienceIdAndType(smsTaskBody);
            targetDistinctReceiveMobiles.addAll(receiveMobileList);
        }

        //Todo:3将受众人群+模板内容+受众类型存入Task_Detail表中
    }

    private List<String> queryReceiveMobileListByTargetAudienceIdAndType(SmsTaskBody smsTaskBody) {
        if(SmsTargetAudienceTypeEnum.SMS_TARGET_SEGMENTATION.getTypeCode() == smsTaskBody.getTargetType()){
            return queryAudienceMobileBySegmentationId(smsTaskBody.getTargetId());
        }else if(SmsTargetAudienceTypeEnum.SMS_TARGET_AUDIENCE.getTypeCode() == smsTaskBody.getTargetType()){
            return queryAudienceMobileByAudienceListId(smsTaskBody.getTargetId());
        }
        return null;
    }

    private List<String> queryAudienceMobileBySegmentationId(Long targetId) {
        return null;
    }

    private List<String> queryAudienceMobileByAudienceListId(Long targetId) {
        return null;
    }
}
