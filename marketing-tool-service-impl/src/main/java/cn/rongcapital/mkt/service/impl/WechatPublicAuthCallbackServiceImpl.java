package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.po.WechatRegister;
import cn.rongcapital.mkt.service.WechatPublicAuthCallbackService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatPublicAuthCallbackIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-17.
 */
@Service
public class WechatPublicAuthCallbackServiceImpl implements WechatPublicAuthCallbackService {

    private static final Integer UNKNOWN_WECHAT_ASSET_TYPE = -1;

    @Autowired
    private WechatRegisterDao wechatRegisterDao;

    //如果表里已经存在weixinId则

    @Override
    public BaseOutput authWechatPublicCallback(WechatPublicAuthCallbackIn wechatPublicAuthCallbackIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        WechatRegister wechatRegister = new WechatRegister();
        wechatRegister.setWxAcct(wechatPublicAuthCallbackIn.getWeixinId());
        wechatRegister.setName(wechatPublicAuthCallbackIn.getName());
        if("success".equals(wechatPublicAuthCallbackIn.getStatus())){
            wechatRegister.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            wechatRegister.setType(UNKNOWN_WECHAT_ASSET_TYPE);
            wechatRegisterDao.insert(wechatRegister);
            baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
            baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        }
        return baseOutput;
    }
}
