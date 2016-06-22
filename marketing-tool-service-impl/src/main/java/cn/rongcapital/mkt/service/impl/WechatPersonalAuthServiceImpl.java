package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatPersonalUuidDao;
import cn.rongcapital.mkt.service.WechatPersonalAuthService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatPersonalAuthIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-22.
 */
@Service
public class WechatPersonalAuthServiceImpl implements WechatPersonalAuthService {

    @Autowired
    private WechatPersonalUuidDao wechatPersonalUuidDao;

    @Override
    public BaseOutput authPersonWechat(WechatPersonalAuthIn wechatPersonalAuthIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        Map<String,Object> paramMap = new HashMap<String,Object>();
        String uuid = wechatPersonalAuthIn.getUuid();
        if(uuid != null){
            paramMap.put("uuid",wechatPersonalAuthIn.getUuid());
            wechatPersonalUuidDao.insertUuid(paramMap);
            baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
            baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        }
        return baseOutput;
    }
}
