/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年6月15日 
 * @date(最后修改日期)：2017年6月15日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.service.impl;

import static cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum.FIXED;
import static cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum.VARIABLE;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.bbx.service.BbxCouponCodeAddService;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.SmsDetailSendStateEnum;
import cn.rongcapital.mkt.common.enums.SmsMaterialVariableEnum;
import cn.rongcapital.mkt.common.enums.SmsTargetAudienceTypeEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsMaterialMaterielMapDao;
import cn.rongcapital.mkt.dao.SmsMaterialVariableMapDao;
import cn.rongcapital.mkt.dao.SmsSignatureDao;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailStateDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.dao.SmsTaskTargetAudienceCacheDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.factory.CalcSmsTargetAudienceStrateyFactory;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.CouponCodeListService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeStatusUpdateService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponEditDetailService;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCodeStatusUpdateVO;
import cn.rongcapital.mkt.material.coupon.vo.out.CouPonEditInfoOut;
import cn.rongcapital.mkt.po.CampaignBody;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.po.SmsMaterialMaterielMap;
import cn.rongcapital.mkt.po.SmsMaterialVariableMap;
import cn.rongcapital.mkt.po.SmsSignature;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.po.SmsTaskDetail;
import cn.rongcapital.mkt.po.SmsTaskDetailState;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SendSmsPrepare;
import cn.rongcapital.mkt.service.SmsSyncCouponService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class SendSmsPrepareServiceImpl implements SendSmsPrepare{
    
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
    
    @Autowired
    private SmsSignatureDao smsSignatureDao;

    @Autowired
    private SmsMaterialDao smsMaterialDao;

    @Autowired
    private CampaignBodyDao campaignBodyDao;

    @Autowired
    private SmsMaterialMaterielMapDao smsMaterialMaterielMapDao;

    @Autowired
    private SmsMaterialVariableMapDao smsMaterialVariableMapDao;

    @Autowired
    private MaterialCouponEditDetailService materialCouponEditDetailService;

    @Autowired
    private CouponCodeListService couponCodeListService;
    
    @Autowired
    private MaterialCouponCodeStatusUpdateService materialCouponCodeStatusUpdateService;

    @Autowired
    private CalcSmsTargetAudienceStrateyFactory calcSmsTargetAudienceStrateyFactory;

    @Autowired
    private SegmentCalcSmsTargetAudienceStrategy segmentCalcSmsTargetAudienceStrategy;

    @Autowired
    private AudienceCalcSmsTargetAudienceStrategy audienceCalcSmsTargetAudienceStrategy;

    @Autowired
    private CampaignCalcSmsTargetAudienceStrategy campaignCalcSmsTargetAudienceStrategy;
    
    @Autowired
    private MaterialCouponDao materialCouponDao;
    @Autowired
    private BbxCouponCodeAddService couponCodeAddService;
    @Autowired
    private SmsSyncCouponService smsSyncCouponService;

    private Map<Integer,AbstractCalcSmsTargetAudienceStrategy> strategyMap = new HashMap<>();
    private final int PAGE_SIZE = 10000;
    private static final String FRONT_SMS_VARIABLE_MASK="{";
    private static final String BACK_SMS_VARIABLE_MASK="}";
    public static final Integer POOL_INDEX = 2;
    private void insertDataToSmsDetailAndDetailState(Long taskHeadId, SmsTaskHead targetHead, Set<String> targetDistinctReceiveMobiles) {
        List<SmsTaskDetail> smsTaskDetailList = new LinkedList<>();
        List<SmsTaskDetailState> smsTaskDetailStateList = new LinkedList<>();
        if(targetHead!=null){
            long signatureId = targetHead.getSmsTaskSignatureId();
            SmsSignature smsSignature = new SmsSignature();
            smsSignature.setId(signatureId);
            List<SmsSignature> smsSignatures =smsSignatureDao.selectList(smsSignature);
            if(!CollectionUtils.isEmpty(smsSignatures)){
                smsSignature = smsSignatures.get(0);
            }

            //Todo:添加方法,在这里根据短信类型生成不同类型的短信.
            generateSmsTaskDetailAccordSmsType(taskHeadId, targetHead, targetDistinctReceiveMobiles, smsTaskDetailList, smsSignature);
        }

        if(CollectionUtils.isEmpty(smsTaskDetailList)) return;
        int totalNum = (smsTaskDetailList.size() + PAGE_SIZE) / PAGE_SIZE;
        for(int index = 0; index < totalNum; index++){
            if(index == totalNum-1){
                logger.info("insert SmsTaskDetail index : " + index);
                smsTaskDetailDao.batchInsert(smsTaskDetailList.subList(index*PAGE_SIZE,smsTaskDetailList.size()));
            }else{
                logger.info("insert SmsTaskDetail index : " + index);
                smsTaskDetailDao.batchInsert(smsTaskDetailList.subList(index*PAGE_SIZE,(index + 1)*PAGE_SIZE));
            }
        }

        for(SmsTaskDetail taskDetail : smsTaskDetailList){
            SmsTaskDetailState smsTaskDetailState = new SmsTaskDetailState();
            smsTaskDetailState.setSmsTaskDetailId(taskDetail.getId());
            smsTaskDetailState.setSmsTaskSendStatus(SmsDetailSendStateEnum.SMS_DETAIL_WAITING.getStateCode());
            smsTaskDetailStateList.add(smsTaskDetailState);
        }

        for(int index = 0; index < totalNum; index++){
            if(index == totalNum-1){
                logger.info("insert SmsTaskDetailState index : " + index);
                smsTaskDetailStateDao.batchInsert(smsTaskDetailStateList.subList(index*PAGE_SIZE,smsTaskDetailStateList.size()));
            }else{
                logger.info("insert SmsTaskDetailState index : " + index);
                smsTaskDetailStateDao.batchInsert(smsTaskDetailStateList.subList(index*PAGE_SIZE,(index + 1)*PAGE_SIZE));
            }
        }
    }

    //Todo:如果是固定短信,按照现有的生成方法保持不变;如果是变量短信按照规则一步一步生成.
    private void generateSmsTaskDetailAccordSmsType(Long taskHeadId, SmsTaskHead targetHead, Set<String> targetDistinctReceiveMobiles, List<SmsTaskDetail> smsTaskDetailList, SmsSignature smsSignature) {
        String targetMaterialContentCopy = targetHead.getSmsTaskMaterialContent();   //备份出原始的带有变量的内容
        SmsMaterial paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSmsMaterial.setId(targetHead.getSmsTaskMaterialId().intValue());
        List<SmsMaterial> targetSmsMaterialList = smsMaterialDao.selectList(paramSmsMaterial);
        if(CollectionUtils.isEmpty(targetSmsMaterialList)) return;
        Integer smsType = targetSmsMaterialList.get(0).getSmsType().intValue(); // 短信类型：0:固定短信,1:变量短信

        if(smsType.equals(FIXED.getStatusCode())){
            for(String distinctReceiveMobile : targetDistinctReceiveMobiles){
                SmsTaskDetail smsTaskDetail = new SmsTaskDetail();
                smsTaskDetail.setReceiveMobile(distinctReceiveMobile);
                if(smsSignature!=null&& StringUtils.isNotEmpty(smsSignature.getSmsSignatureName())){
                    String sendMessage = smsSignature.getSmsSignatureName()+targetHead.getSmsTaskMaterialContent();
                    smsTaskDetail.setSendMessage(sendMessage);
                }
                smsTaskDetail.setSendTime(new Date(System.currentTimeMillis()));
                smsTaskDetail.setSmsTaskHeadId(taskHeadId);
                smsTaskDetailList.add(smsTaskDetail);
            }
        }else if(smsType.equals(VARIABLE.getStatusCode())){
            //Todo:整体上要完成一个变量短信的变量替换过程,那么整体上要完成获取变量目标,准备好源短信,根据短信映射规则获取建立起源到目标的Map,完成替换.
            //Todo:1查出对应的优惠券信息
            SmsMaterialMaterielMap paramSmsMaterialMaterielMap = new SmsMaterialMaterielMap();
            paramSmsMaterialMaterielMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            paramSmsMaterialMaterielMap.setSmsMaterialId(targetHead.getSmsTaskMaterialId());
            List<SmsMaterialMaterielMap> smsMaterialMaterielMapList = smsMaterialMaterielMapDao.selectList(paramSmsMaterialMaterielMap);

            //Todo:2.1查出这个素材对应的具体的优惠券信息
            MaterialCoupon mcp = materialCouponDao.selectOneCoupon(smsMaterialMaterielMapList.get(0).getSmsMaterielId());
            CouPonEditInfoOut couPonEditInfoOut = new CouPonEditInfoOut();
            BeanUtils.copyProperties(mcp, couPonEditInfoOut);
            couPonEditInfoOut.setAmount(mcp.getAmount()==null?0:mcp.getAmount().doubleValue());
            couPonEditInfoOut.setStartTime(mcp.getStartTime() == null ? 0 : mcp.getStartTime().getTime());
            couPonEditInfoOut.setEndTime(mcp.getEndTime() == null ? 0 : mcp.getEndTime().getTime());

            //Todo:2.2创建一个方法传入变量名称和优惠券信息,返回该变量对应的值,需要增加物料类型在传入参数中,针对优惠码等特殊变量要传入额外字段,这块可以做成策略模式，方便日后做扩展
            //Todo:2查出对应的变量信息
            SmsMaterialVariableMap paramSmsMaterialVariableMap = new SmsMaterialVariableMap();
            paramSmsMaterialVariableMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            paramSmsMaterialVariableMap.setSmsMaterialId(targetHead.getSmsTaskMaterialId());
            List<SmsMaterialVariableMap> smsMaterialVariableMapList = smsMaterialVariableMapDao.selectList(paramSmsMaterialVariableMap);

            //Todo:5优惠码必定比手机号要多,將手机号拆成1W一页的然后逐页生成
            List<String> targetDistinctReceiveMobileList = new ArrayList<>(targetDistinctReceiveMobiles);
            int totalPage = (targetDistinctReceiveMobileList.size() + PAGE_SIZE) / PAGE_SIZE;
            for(int index = 0; index < totalPage; index++){
                //Todo:1选出每页所需要的优惠码
                BaseOutput couponCodeList = couponCodeListService.couponCodeList(smsMaterialMaterielMapList.get(0).getSmsMaterielId(),index*PAGE_SIZE,PAGE_SIZE);
                List<MaterialCouponCodeStatusUpdateVO> materialCouponCodeStatusUpdateVOes = materialCouponCodeStatusUpdateService.getReleasedMaterialCouponCodeStatusUpdateVOes(couponCodeList.getData());
                materialCouponCodeStatusUpdateService.updateMaterialCouponCodeStatus(materialCouponCodeStatusUpdateVOes);
                
                //Todo:2拆分出每页的手机号
                List<String> subTargetDistinctReceiveMobileList = null;
                if(index == totalPage-1){
                    subTargetDistinctReceiveMobileList = targetDistinctReceiveMobileList.subList(index*PAGE_SIZE,targetDistinctReceiveMobileList.size());
                }else{
                    subTargetDistinctReceiveMobileList = targetDistinctReceiveMobileList.subList(index*PAGE_SIZE,(index+1)*PAGE_SIZE);
                }

                //Todo:3进行短信内容的生成
                int couponCodeIndex = 0;
                for(String distinctReceiveMobile : subTargetDistinctReceiveMobileList){
                    MaterialCouponCode materialCouponCode = (MaterialCouponCode) couponCodeList.getData().get(couponCodeIndex);
                    couponCodeIndex++;
                    SmsTaskDetail smsTaskDetail = new SmsTaskDetail();
                    smsTaskDetail.setReceiveMobile(distinctReceiveMobile);
                    if(smsSignature != null && StringUtils.isNotEmpty(smsSignature.getSmsSignatureName())){
                        //Todo:3进行一次初始替换,替换变量值,然后用一个Map保存起来
                        Map<String,String> variableToValueMap = new HashMap<>();
                        for(SmsMaterialVariableMap smsMaterialVariableMap : smsMaterialVariableMapList){
                            if(SmsMaterialVariableEnum.VARIABLE_COUPON_COUPON_CODE.getVariableName().equals(smsMaterialVariableMap.getSmsVariableValue())){
                                setVariableValueInMaterialVariable(variableToValueMap,smsMaterialVariableMap,couPonEditInfoOut,materialCouponCode.getCode());
                                continue;
                            }
                            setVariableValueInMaterialVariable(variableToValueMap,smsMaterialVariableMap,couPonEditInfoOut,null);
                        }

                        for(String key : variableToValueMap.keySet()){
                            targetHead.setSmsTaskMaterialContent(targetHead.getSmsTaskMaterialContent().replace(key,variableToValueMap.get(key)));
                        }

                        String sendMessage = smsSignature.getSmsSignatureName()+targetHead.getSmsTaskMaterialContent();
                        targetHead.setSmsTaskMaterialContent(targetMaterialContentCopy);
                        smsTaskDetail.setSendMessage(sendMessage);
                    }
                    smsTaskDetail.setMaterielCouponCodeId(materialCouponCode.getId());
                    smsTaskDetail.setMaterielCouponCodeCode(materialCouponCode.getCode());
                    smsTaskDetail.setSendTime(new Date(System.currentTimeMillis()));
                    smsTaskDetail.setSmsTaskHeadId(taskHeadId);
                    smsTaskDetail.setSendStatus(ApiConstant.SMS_TASK_PROCESS_STATUS_WRITING);
                    smsTaskDetailList.add(smsTaskDetail);
                }
            }


        }
    }

    private void setVariableValueInMaterialVariable(Map<String, String> variableToValueMap, SmsMaterialVariableMap smsMaterialVariableMap, CouPonEditInfoOut couPonEditInfoOut, String specialTypeValue) {
        SmsMaterialVariableEnum smsMaterialVariableEnum = SmsMaterialVariableEnum.getSmsMaterialVariableEnum(smsMaterialVariableMap.getSmsVariableType(),smsMaterialVariableMap.getSmsVariableValue());
        switch (smsMaterialVariableEnum){
            case VARIABLE_COUPON_EFFECTIVE_TIME:
                String startTime = DateUtil.getStringFromDate(new Date(couPonEditInfoOut.getStartTime()),"MM月dd日-");
                String endTime = DateUtil.getStringFromDate(new Date(couPonEditInfoOut.getEndTime()),"MM月dd日");
                variableToValueMap.put(deractorSmsVariableName(smsMaterialVariableMap.getSmsVariableName()),startTime + endTime);
                break;
            case VARIABLE_COUPON_AMOUNT:
                variableToValueMap.put(deractorSmsVariableName(smsMaterialVariableMap.getSmsVariableName()),couPonEditInfoOut.getAmount() + "");
                break;
            case VARIABLE_COUPON_CHANNEL_CODE:
                variableToValueMap.put(deractorSmsVariableName(smsMaterialVariableMap.getSmsVariableName()),couPonEditInfoOut.getChannelCode());
                break;
            case VARIABLE_COUPON_COUPON_CODE:
                variableToValueMap.put(deractorSmsVariableName(smsMaterialVariableMap.getSmsVariableName()),specialTypeValue);
                break;
        }
    }

    private String deractorSmsVariableName(String smsVariableName) {
        return FRONT_SMS_VARIABLE_MASK + smsVariableName + BACK_SMS_VARIABLE_MASK;
    }

    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.service.SendSmsPrepare#getSmSTaskHead(java.lang.String)
     */
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public SmsTaskHead getSmSTaskHead(String taskHeadIdStr) {
        
        strategyMap.put(SmsTargetAudienceTypeEnum.SMS_TARGET_SEGMENTATION.getTypeCode(),segmentCalcSmsTargetAudienceStrategy);
        strategyMap.put(SmsTargetAudienceTypeEnum.SMS_TARGET_AUDIENCE.getTypeCode(), audienceCalcSmsTargetAudienceStrategy);
        strategyMap.put(SmsTargetAudienceTypeEnum.SMS_TARGET_CAMPAIGN.getTypeCode(), campaignCalcSmsTargetAudienceStrategy);
        
        logger.info("taskHeadId :" + taskHeadIdStr);
        Long taskHeadId = Long.valueOf(taskHeadIdStr);

        //1根据headId选出模板内容
        SmsTaskHead paramSmsTaskHead = new SmsTaskHead();
        paramSmsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSmsTaskHead.setId(taskHeadId);
        List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectList(paramSmsTaskHead);
        if (CollectionUtils.isEmpty(smsTaskHeads)) {
            logger.info("sms head is null, head id is {}", taskHeadId);
            return null;
        }

        SmsTaskHead targetHead = smsTaskHeads.get(0);

        //2根据headId依次选出受众人群
        Set<String> targetDistinctReceiveMobiles = new HashSet<>();
        SmsTaskBody paramSmsTaskBody = new SmsTaskBody();
        paramSmsTaskBody.setSmsTaskHeadId(taskHeadId);
        paramSmsTaskBody.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        // paramSmsTaskBody.setSendStatus(ApiConstant.SMS_TASK_PROCESS_STATUS_WRITING);
        List<SmsTaskBody> smsTaskBodies = smsTaskBodyDao.selectList(paramSmsTaskBody);
        if (CollectionUtils.isEmpty(smsTaskBodies)) {
            logger.info("sms body is null, head id is {}", taskHeadId);
            return null;
        }
        logger.info("sms body size {}", smsTaskBodies.size());
        for(SmsTaskBody smsTaskBody : smsTaskBodies){
            AbstractCalcSmsTargetAudienceStrategy strategy = strategyMap.get(smsTaskBody.getTargetType());
            List<String> receiveMobileList = strategy.queryReceiveMobileList(smsTaskBody.getSmsTaskHeadId(), smsTaskBody.getTargetId());
            if(CollectionUtils.isEmpty(receiveMobileList)) continue;
            logger.info("sms list distinct mobile size: " + receiveMobileList.size());
            targetDistinctReceiveMobiles.addAll(receiveMobileList);
            // smsTaskBody.setSendStatus(ApiConstant.SMS_TASK_PROCESS_STATUS_DONE);
            // smsTaskBodyDao.updateById(smsTaskBody);
        }

        logger.info("sms task audience size:" + targetDistinctReceiveMobiles.size());
        //3将受众人群+模板内容+受众类型存入Task_Detail表中
        if(targetDistinctReceiveMobiles.contains(null)){
            targetDistinctReceiveMobiles.remove(null);
        }
        if(targetDistinctReceiveMobiles.contains("")){
            targetDistinctReceiveMobiles.remove("");
        }
        //3.5如果targetDistinctReceiveMobiles的容量为空,则不执行下面得短信生成语句
        if(CollectionUtils.isEmpty(targetDistinctReceiveMobiles))
        {
            return null;
        }
        else
        {
        insertDataToSmsDetailAndDetailState(taskHeadId, targetHead, targetDistinctReceiveMobiles);
        }
        //4更新SmsTaskHead表的人群相关的字段
        targetHead.setAudienceGenerateStatus(ApiConstant.INT_ZERO);
        CampaignBody campaignBody = new CampaignBody();
        Byte nodeType = 0; // 第一个节点
        Byte itemType = 3; // 触发类型的活动
        campaignBody.setHeadId(targetHead.getCampaignHeadId());
        campaignBody.setNodeType(nodeType);
        campaignBody.setItemType(itemType);
        List<CampaignBody> campaignBodyList = this.campaignBodyDao.selectList(campaignBody);
        boolean isEventTask = campaignBodyList != null && !campaignBodyList.isEmpty();
        if (isEventTask) { // 事件类型的活动
            targetHead.setTotalCoverNum(targetHead.getTotalCoverNum() + targetDistinctReceiveMobiles.size());
            targetHead.setWaitingNum(targetHead.getWaitingNum() + targetDistinctReceiveMobiles.size());
            logger.info("事件类型活动，有 {} 个手机号。", targetDistinctReceiveMobiles.size());
        } else {
            targetHead.setTotalCoverNum(targetDistinctReceiveMobiles.size());
            targetHead.setWaitingNum(targetDistinctReceiveMobiles.size());
            logger.info("非事件类型活动，有 {} 个手机号。", targetDistinctReceiveMobiles.size());
        }

        smsTaskHeadDao.updateById(targetHead);
        logger.info("sms task head update info");

        smsTaskHeads = smsTaskHeadDao.selectList(paramSmsTaskHead);
        if(CollectionUtils.isEmpty(smsTaskHeads))
            {
            return null;
            }
        SmsTaskHead currentTaskHead = smsTaskHeads.get(0);
        return currentTaskHead;
    }



}
