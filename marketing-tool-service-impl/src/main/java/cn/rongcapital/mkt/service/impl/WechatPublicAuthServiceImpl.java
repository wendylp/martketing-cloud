package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.service.WechatPublicAuthService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-16.
 */
@Service
public class WechatPublicAuthServiceImpl implements WechatPublicAuthService{

    @Autowired
    private TenementDao tenementDao;
    @Override
    public BaseOutput authWechatPublicAccount() {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        Map<String,Object> h5ParamRequestMap = tenementDao.selectPidAndShortname();
        HttpResponse response = HttpUtils.requestH5Interface(h5ParamRequestMap);
        if(response != null){
            try {
                String result = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
