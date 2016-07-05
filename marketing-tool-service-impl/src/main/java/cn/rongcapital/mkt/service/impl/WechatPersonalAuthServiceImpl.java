package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.job.service.base.TaskManager;
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
    private TaskManager taskManager;
    @Autowired
    private WechatMemberDao wechatMemberDao;
    @Autowired
    private WechatGroupDao wechatGroupDao;
    @Autowired
    private WechatAssetDao wechatAssetDao;
    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;

    @Override
    public BaseOutput authPersonWechat(WechatPersonalAuthIn wechatPersonalAuthIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        Map<String,Object> paramMap = new HashMap<String,Object>();
        String uuid = wechatPersonalAuthIn.getUuid();
        if(uuid != null){
            paramMap.put("uuid",wechatPersonalAuthIn.getUuid());
            String uin = wechatPersonalAuthIn.getUin();
            if(uin != null){
                paramMap.put("uin",uin);
                wechatPersonalUuidDao.insertUuidAndUin(paramMap);
                taskManager.manualInitTask(1098,null);
                baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
                baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            }
        }
        return baseOutput;
    }
}
