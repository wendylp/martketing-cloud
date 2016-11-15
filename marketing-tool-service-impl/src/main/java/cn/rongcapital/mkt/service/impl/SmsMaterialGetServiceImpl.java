package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsMaterialGetService;
import cn.rongcapital.mkt.service.SmsMaterialService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SmsMaterialOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    private SmsMaterialService smsMaterialService;

    @Override
    public BaseOutput getSmsMaterialById(Long id) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        SmsMaterial paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setId(id.intValue());
        List<SmsMaterial> smsMaterialList = smsMaterialDao.selectList(paramSmsMaterial);

        if(CollectionUtils.isEmpty(smsMaterialList)){
            baseOutput.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
            baseOutput.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
            return baseOutput;
        }

        SmsMaterial rs = smsMaterialList.get(0);
        SmsMaterialOut smsMaterialOut = getSmsMaterialOut(rs, null);

        baseOutput.getData().add(smsMaterialOut);
        baseOutput.setTotal(baseOutput.getData().size());
        return baseOutput;
    }

    private SmsMaterialOut getSmsMaterialOut(SmsMaterial rs, String smsTemplateName) {
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
        return smsMaterialOut;
    }

    @Override
    public BaseOutput getSmsMaterialListByKeyword(String searchWord, Integer index, Integer size) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        SmsMaterial paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setName(searchWord);
        paramSmsMaterial.setStartIndex((index - 1) * size);
        paramSmsMaterial.setPageSize(size);
        List<SmsMaterial> smsMaterialList = smsMaterialDao.selectListByKeyword(paramSmsMaterial);

        if(CollectionUtils.isEmpty(smsMaterialList)) return baseOutput;

        for(SmsMaterial smsMaterial : smsMaterialList){
            SmsTemplet paramSmsTemplet = new SmsTemplet();
            paramSmsTemplet.setId(smsMaterial.getSmsTempletId());
            paramSmsTemplet.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            List<SmsTemplet> smsTempletList = smsTempletDao.selectList(paramSmsTemplet);
            if(CollectionUtils.isEmpty(smsMaterialList)) continue;
            String templateName = smsTempletList.get(0).getName();
            SmsMaterialOut smsMaterialOut = getSmsMaterialOut(smsMaterial,templateName);
            baseOutput.getData().add(smsMaterialOut);
        }

        baseOutput.setTotal(baseOutput.getData().size());
        return baseOutput;
    }
}
