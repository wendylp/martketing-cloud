package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.*;
import cn.rongcapital.mkt.service.SmsMaterialService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsMaterialComponentIn;
import cn.rongcapital.mkt.vo.in.SmsMaterialVariableIn;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialDeleteIn;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialIn;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class SmsMaterialServiceImpl implements SmsMaterialService {

    private final String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    private final Integer MODIFY_TYPE_INSERT = 0;
    private final Integer MODIFY_TYPE_UPDATE = 1;

    @Autowired
    private SmsMaterialDao smsMaterialDao;

    @Autowired
    private SmsTaskHeadDao smsTaskHeadDao;

    @Autowired
    private SmsTempletDao smsTempletDao;

    @Autowired
    private SmsMaterialComponentMapDao smsMaterialComponentMapDao;

    @Autowired
    private SmsMaterialVariableMapDao smsMaterialVariableMapDao;

    @Override
    public BaseOutput insertOrUpdateSmsMaterial(@NotEmpty SmsMaterialIn smsMaterialIn) {
        BaseOutput output = getNewSuccessBaseOutput();
        SmsMaterial smsMaterial = getSmsMaterial(smsMaterialIn);
        if (smsMaterial.getId() != null && smsMaterial.getId() != 0) {
            if (smsMaterialValidate(smsMaterial.getId())) {
                smsMaterialDao.updateById(smsMaterial);
                modifySmsMaterialComponentAndVariable(smsMaterialIn, MODIFY_TYPE_UPDATE);
            } else {
                output.setCode(ApiErrorCode.SMS_ERROR_MATERIAL_CAN_NOT_UPDATE.getCode());
                output.setMsg(ApiErrorCode.SMS_ERROR_MATERIAL_CAN_NOT_UPDATE.getMsg());
            }
        } else {
            smsMaterialDao.insert(smsMaterial);
            smsMaterialIn.setId(smsMaterial.getId());
            modifySmsMaterialComponentAndVariable(smsMaterialIn, MODIFY_TYPE_INSERT);
        }
        output.getData().add(smsMaterial);
        output.setDate(DateUtil.getStringFromDate(new Date(), FORMAT_STRING));
        return output;
    }

    private void modifySmsMaterialComponentAndVariable(@NotEmpty SmsMaterialIn smsMaterialIn, @NotNull Integer modifyType) {
        //0如果是修改,则删除以前的物料和变量数据
        if (modifyType.equals(MODIFY_TYPE_UPDATE)) {
            SmsMaterialComponentMap paramSmsMaterialComponentMap = new SmsMaterialComponentMap();
            paramSmsMaterialComponentMap.setSmsMaterialId(Long.valueOf(smsMaterialIn.getId()));
            paramSmsMaterialComponentMap.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
            smsMaterialComponentMapDao.updateById(paramSmsMaterialComponentMap);

            SmsMaterialVariableMap paramSmsMaterialVariableMap = new SmsMaterialVariableMap();
            paramSmsMaterialVariableMap.setSmsMaterialId(Long.valueOf(smsMaterialIn.getId()));
            paramSmsMaterialVariableMap.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
            smsMaterialVariableMapDao.updateById(paramSmsMaterialVariableMap);
        }
        //1修改SmsMaterialComponent表
        List<SmsMaterialComponentIn> smsMaterialComponentInList = smsMaterialIn.getSmsMaterialComponentInList();
        if (!org.springframework.util.CollectionUtils.isEmpty(smsMaterialComponentInList)) {
            for (SmsMaterialComponentIn smsMaterialComponentIn : smsMaterialComponentInList) {
                SmsMaterialComponentMap insertSmsMaterialComponentMap = new SmsMaterialComponentMap();
                insertSmsMaterialComponentMap.setSmsMaterialId(Long.valueOf(smsMaterialIn.getId()));
                insertSmsMaterialComponentMap.setSmsComponentId(smsMaterialComponentIn.getComponentId());
                insertSmsMaterialComponentMap.setSmsComponentType(smsMaterialComponentIn.getComponentType());
                insertSmsMaterialComponentMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
                smsMaterialComponentMapDao.insert(insertSmsMaterialComponentMap);
            }
        }

        //2修改SmsMaterialVariable表
        List<SmsMaterialVariableIn> smsMaterialVariableInList = smsMaterialIn.getSmsMaterialVariableInList();
        if (!org.springframework.util.CollectionUtils.isEmpty(smsMaterialVariableInList)) {
            for (SmsMaterialVariableIn smsMaterialVariableIn : smsMaterialVariableInList) {
                SmsMaterialVariableMap insertSmsMaterialVariableMap = new SmsMaterialVariableMap();
                insertSmsMaterialVariableMap.setSmsMaterialId(Long.valueOf(smsMaterialIn.getId()));
                insertSmsMaterialVariableMap.setSmsVariableName(smsMaterialVariableIn.getVariableName());
                insertSmsMaterialVariableMap.setSmsVariableValue(smsMaterialVariableIn.getVariableValue());
                insertSmsMaterialVariableMap.setSmsVariableType(smsMaterialVariableIn.getVariableType());
                smsMaterialVariableMapDao.insert(insertSmsMaterialVariableMap);
            }
        }
    }

    @Override
    public BaseOutput deleteSmsMaterial(@NotEmpty SmsMaterialDeleteIn smsMaterialDeleteIn) {
        BaseOutput output = getNewSuccessBaseOutput();
        if (smsMaterialDeleteIn.getId() != null) {
            if (smsMaterialValidate(smsMaterialDeleteIn.getId())) {
                SmsMaterial smsMaterial = new SmsMaterial();
                smsMaterial.setId(smsMaterialDeleteIn.getId());
                smsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
                smsMaterialDao.updateById(smsMaterial);

                //1.干掉物料表
                SmsMaterialComponentMap paramSmsMaterialComponentMap = new SmsMaterialComponentMap();
                paramSmsMaterialComponentMap.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
                paramSmsMaterialComponentMap.setSmsMaterialId(Long.valueOf(smsMaterialDeleteIn.getId()));
                smsMaterialComponentMapDao.deleteBySmsMaterialId(paramSmsMaterialComponentMap);
                //2.干掉变量表
                SmsMaterialVariableMap paramSmsMaterialVariableMap = new SmsMaterialVariableMap();
                paramSmsMaterialVariableMap.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
                paramSmsMaterialVariableMap.setSmsMaterialId(Long.valueOf(smsMaterialDeleteIn.getId()));
                smsMaterialVariableMapDao.deleteBySmsMaterialId(paramSmsMaterialVariableMap);
            } else {
                output.setCode(ApiErrorCode.SMS_ERROR_MATERIAL_CAN_NOT_DELETE.getCode());
                output.setMsg(ApiErrorCode.SMS_ERROR_MATERIAL_CAN_NOT_DELETE.getMsg());
            }
        } else {
            output.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
            output.setMsg(ApiErrorCode.PARAMETER_ERROR.getMsg());
        }
        return output;
    }

    public boolean smsMaterialValidate(Integer id) {
        SmsTaskHead paramSmsTaskHead = new SmsTaskHead();
        paramSmsTaskHead.setSmsTaskMaterialId(Long.valueOf(id));
        paramSmsTaskHead.setSmsTaskStatus(SmsTaskStatusEnum.TASK_FINISH.getStatusCode());
        paramSmsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectListByMaterial(paramSmsTaskHead);
        return CollectionUtils.isNotEmpty(smsTaskHeads);
    }

    /**
     * 根据输入得到素材对象
     *
     * @param smsMaterialIn 素材类型
     * @return SmsMaterial
     */
    private SmsMaterial getSmsMaterial(@NotEmpty SmsMaterialIn smsMaterialIn) {
        SmsMaterial smsMaterial = new SmsMaterial();
        if (smsMaterial.getId() == null) {
            smsMaterial.setCreator(getValidateString(smsMaterialIn.getCreator()));
            smsMaterial.setCreateTime(new Date());
            smsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        }
        smsMaterial.setId(getValidateInteger(smsMaterialIn.getId()));
        smsMaterial.setUpdateUser(getValidateString(smsMaterialIn.getUpdateUser()));
        smsMaterial.setSmsSignId(getValidateInteger(smsMaterialIn.getSmsSignId()));
        smsMaterial.setName(getValidateString(smsMaterialIn.getName()));
        smsMaterial.setSmsTempletContent(getValidateString(smsMaterialIn.getSmsTempletContent()));
        smsMaterial.setSmsSignName(getValidateString(smsMaterialIn.getSmsSignName()));
        smsMaterial.setSmsType(getValidateInteger(smsMaterialIn.getSmsType()).byteValue());
        smsMaterial.setSmsTempletId(getValidateInteger(smsMaterialIn.getSmsTempletId()));
        if (smsMaterialIn.getSmsTempletId() != null && smsMaterialIn.getSmsTempletId() != 0) {
            SmsTemplet paramSmsTemplet = new SmsTemplet();
            paramSmsTemplet.setId(smsMaterialIn.getSmsTempletId());
            paramSmsTemplet.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            List<SmsTemplet> smsTemplets = smsTempletDao.selectList(paramSmsTemplet);
            if (CollectionUtils.isNotEmpty(smsTemplets)) {
                smsMaterial.setChannelType(smsTemplets.get(0).getChannelType());
            }
        }
        return smsMaterial;
    }

    private String getValidateString(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return value;
    }

    private Integer getValidateInteger(Integer value) {
        if (value == null) {
            return null;
        }
        return value;
    }

    private BaseOutput getNewSuccessBaseOutput() {
        return new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
    }

}
