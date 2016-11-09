package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.service.SmsMaterialGetService;
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
        SmsMaterialOut smsMaterialOut = new SmsMaterialOut();
        smsMaterialOut.setId(id);
        smsMaterialOut.setMaterialId(rs.getCode());
        smsMaterialOut.setMaterialName(rs.getName());
        smsMaterialOut.setSmsType(rs.getSmsType()==null?null:Integer.valueOf(rs.getSmsType()));
        smsMaterialOut.setSmsSignId(rs.getSmsSignId());
        smsMaterialOut.setSmsSignContent(rs.getSmsSignName());
        smsMaterialOut.setSmsTemplateId(rs.getSmsTempletId());
        smsMaterialOut.setSmsTemplateContent(rs.getSmsTempletContent());

        baseOutput.getData().add(smsMaterialOut);
        baseOutput.setTotal(baseOutput.getData().size());
        return baseOutput;
    }
}
