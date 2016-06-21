package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
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

    @Autowired
    private WechatRegisterDao wechatRegisterDao;

    @Override
    public BaseOutput authWechatPublicCallback(WechatPublicAuthCallbackIn wechatPublicAuthCallbackIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        Map<String,Object> paramMap =new HashMap<String,Object>();
        paramMap.put("wx_acct",wechatPublicAuthCallbackIn.getWeixinId());
        paramMap.put("name",wechatPublicAuthCallbackIn.getName());
        if("success".equals(wechatPublicAuthCallbackIn.getStatus())){
            paramMap.put("status",0);
            wechatRegisterDao.insertAuthPublic(paramMap);
            baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
            baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        }
        return baseOutput;
    }
}
