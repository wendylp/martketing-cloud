package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsMaterialVariableTypeEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsMaterialMaterielMapDao;
import cn.rongcapital.mkt.dao.SmsMaterialVariableMapDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.po.SmsMaterialMaterielMap;
import cn.rongcapital.mkt.po.SmsMaterialVariableMap;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsMaterialGetService;
import cn.rongcapital.mkt.service.SmsMaterialService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SmsMaterialMaterielOut;
import cn.rongcapital.mkt.vo.out.SmsMaterialOut;
import cn.rongcapital.mkt.vo.out.SmsMaterialVariableOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by byf on 11/9/16.
 */
@Service
public class SmsMaterialGetServiceImpl implements SmsMaterialGetService{

    @Autowired
    private SmsMaterialDao smsMaterialDao;

    @Autowired
    private SmsTempletDao smsTempletDao;

    @Autowired
    private SmsMaterialMaterielMapDao smsMaterialMaterielMapDao;

    @Autowired
    private SmsMaterialVariableMapDao smsMaterialVariableMapDao;

    @Autowired
    private SmsMaterialService smsMaterialService;

    @Override
    public BaseOutput getSmsMaterialById(Long id) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        SmsMaterial paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setId(id.intValue());
        paramSmsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsMaterial> smsMaterialList = smsMaterialDao.selectList(paramSmsMaterial);

        if(CollectionUtils.isEmpty(smsMaterialList)){
            baseOutput.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
            baseOutput.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
            return baseOutput;
        }

        SmsMaterial rs = smsMaterialList.get(0);

        SmsMaterialMaterielMap paramSmsMaterialMaterielMap = new SmsMaterialMaterielMap();
        paramSmsMaterialMaterielMap.setSmsMaterialId(id);
        paramSmsMaterialMaterielMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsMaterialMaterielMap> smsMaterialMaterielMapList = smsMaterialMaterielMapDao.selectList(paramSmsMaterialMaterielMap);

        SmsMaterialVariableMap paramSmsMaterialVariableMap = new SmsMaterialVariableMap();
        paramSmsMaterialVariableMap.setSmsMaterialId(id);
        paramSmsMaterialVariableMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsMaterialVariableMap> smsMaterialVariableMapList = smsMaterialVariableMapDao.selectList(paramSmsMaterialVariableMap);

        SmsMaterialOut smsMaterialOut = getSmsMaterialOut(rs, null, smsMaterialMaterielMapList, smsMaterialVariableMapList);

        baseOutput.getData().add(smsMaterialOut);
        baseOutput.setTotal(baseOutput.getData().size());
        return baseOutput;
    }

    @Override
    public BaseOutput getSmsMaterialListByKeyword(String searchWord, Integer channelType, Integer smsType, Integer index, Integer size) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        SmsMaterial paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setName(searchWord);
        paramSmsMaterial.setChannelType(channelType == null? null : channelType.byteValue());
        paramSmsMaterial.setSmsType(smsType == null? null : smsType.byteValue());
        paramSmsMaterial.setStartIndex((index - 1) * size);
        paramSmsMaterial.setPageSize(size);
        List<SmsMaterial> smsMaterialList = smsMaterialDao.selectListByKeyword(paramSmsMaterial);
        int totalCount = smsMaterialDao.selectListByKeywordCount(paramSmsMaterial);

        if(CollectionUtils.isEmpty(smsMaterialList)) return baseOutput;

        for(SmsMaterial smsMaterial : smsMaterialList){
            SmsTemplet paramSmsTemplet = new SmsTemplet();
            paramSmsTemplet.setId(smsMaterial.getSmsTempletId());
            paramSmsTemplet.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            List<SmsTemplet> smsTempletList = smsTempletDao.selectList(paramSmsTemplet);
            String templateName = CollectionUtils.isEmpty(smsTempletList)?"":smsTempletList.get(0).getName();
            SmsMaterialOut smsMaterialOut = getSmsMaterialOut(smsMaterial,templateName, null, null);
            baseOutput.getData().add(smsMaterialOut);
        }

        baseOutput.setTotal(baseOutput.getData().size());
        baseOutput.setTotalCount(totalCount);
        return baseOutput;
    }

    @Override
    public BaseOutput getSmsMaterialCount() {
        SmsMaterial paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        return null;
    }

    private SmsMaterialOut getSmsMaterialOut(SmsMaterial rs, String smsTemplateName, List<SmsMaterialMaterielMap> smsMaterialMaterielMapList, List<SmsMaterialVariableMap> smsMaterialVariableMapList) {
        SmsMaterialOut smsMaterialOut = new SmsMaterialOut();
        smsMaterialOut.setId(rs.getId() == null?null:Long.valueOf(rs.getId()));
        smsMaterialOut.setMaterialId(rs.getCode());
        smsMaterialOut.setMaterialName(rs.getName());
        smsMaterialOut.setSmsType(rs.getSmsType()==null?null:Integer.valueOf(rs.getSmsType()));
        smsMaterialOut.setSmsSignId(rs.getSmsSignId());
        smsMaterialOut.setSmsSignContent(rs.getSmsSignName());
        smsMaterialOut.setSmsTemplateId(rs.getSmsTempletId());
        smsMaterialOut.setSmsTemplateContent(rs.getSmsTempletContent());
        smsMaterialOut.setSmsTemplateName(smsTemplateName);
        smsMaterialOut.setCreateTime(DateUtil.getStringFromDate(rs.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
        smsMaterialOut.setEditStatus(smsMaterialService.smsMaterialValidate(rs.getId())?0:1);
        smsMaterialOut.setDeleteStatus(smsMaterialOut.getEditStatus());
        if(!CollectionUtils.isEmpty(smsMaterialMaterielMapList)){
            List<SmsMaterialMaterielOut> smsMaterialMaterielOutList = new LinkedList<>();
            for(SmsMaterialMaterielMap smsMaterialMaterielMap : smsMaterialMaterielMapList){
                SmsMaterialMaterielOut smsMaterialMaterielOut = new SmsMaterialMaterielOut();
                smsMaterialMaterielOut.setMaterielId(smsMaterialMaterielMap.getId().intValue());
                smsMaterialMaterielOut.setMaterielType(smsMaterialMaterielMap.getSmsMaterielType());
                smsMaterialMaterielOutList.add(smsMaterialMaterielOut);
            }
            smsMaterialOut.setSmsMaterialMaterielOutList(smsMaterialMaterielOutList);
        }
        if(!CollectionUtils.isEmpty(smsMaterialVariableMapList)){
            List<SmsMaterialVariableOut> smsMaterialVariableOutList = new LinkedList<>();
            for(SmsMaterialVariableMap smsMaterialVariableMap : smsMaterialVariableMapList){
                SmsMaterialVariableOut smsMaterialVariableOut = new SmsMaterialVariableOut();
                smsMaterialVariableOut.setVariableName(smsMaterialVariableMap.getSmsVariableName());
                smsMaterialVariableOut.setVariableType(SmsMaterialVariableTypeEnum.getTypeValueByTypeCode(smsMaterialVariableMap.getSmsVariableType()));
                smsMaterialVariableOut.setVariableValue(smsMaterialVariableMap.getSmsVariableValue());
                smsMaterialVariableOutList.add(smsMaterialVariableOut);
            }
            smsMaterialOut.setSmsMaterialVariableOutList(smsMaterialVariableOutList);
        }
        return smsMaterialOut;
    }
}
