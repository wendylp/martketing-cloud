package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WechatPersonalUuidDao;
import cn.rongcapital.mkt.job.service.impl.GetPersonContactsList;
import cn.rongcapital.mkt.job.vo.in.H5PersonalContactlistResponse;
import cn.rongcapital.mkt.service.WechatPersonalAuthService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatPersonalAuthIn;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-22.
 */
@Service
public class WechatPersonalAuthServiceImpl implements WechatPersonalAuthService {

    @Autowired
    private TenementDao tenementDao;
    @Autowired
    private WechatPersonalUuidDao wechatPersonalUuidDao;
    @Autowired
    private GetPersonContactsList getPersonContactsList;

    @Override
    public BaseOutput authPersonWechat(WechatPersonalAuthIn wechatPersonalAuthIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        Map<String,Object> paramMap = new HashMap<String,Object>();
        String uuid = wechatPersonalAuthIn.getUuid();
        if(uuid != null){
            paramMap.put("uuid",wechatPersonalAuthIn.getUuid());
            String uin = getString(uuid);
            if(uin != null){
                paramMap.put("uin",uin);
                wechatPersonalUuidDao.insertUuidAndUin(paramMap);
                baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
                baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            }
        }
        return baseOutput;
    }

    private String getString(String uuid) {
        Map<String,String> h5ParamMap = tenementDao.selectPid();
        h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_PERSONAL_CONTACTLIST);
        h5ParamMap.put("uuid",uuid);
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
        if(httpResponse != null) {
            JSONObject obj = null;
            try {
                obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_personal_contactlist_response");
                if (obj != null) {
                    H5PersonalContactlistResponse h5PersonalContactlistResponse = JSON.parseObject(obj.toString(), H5PersonalContactlistResponse.class);
                    return h5PersonalContactlistResponse.getUin();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
