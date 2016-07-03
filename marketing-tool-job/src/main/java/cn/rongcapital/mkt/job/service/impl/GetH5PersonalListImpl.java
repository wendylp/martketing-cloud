package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.vo.in.H5MktPersonalListResponse;
import cn.rongcapital.mkt.job.vo.in.H5Personal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-17.
 */
@Service
public class GetH5PersonalListImpl implements TaskService {

    //Todo:同步大连那边个人号列表接口。
    //Todo:1.调用Http请求获取接口返回值  (checked)
    //Todo:2.对接口返回值进行JSON解析，保存到entity类中 (checked)

    @Autowired
    private TenementDao tenementDao;
    @Autowired
    private WechatRegisterDao wechatRegisterDao;


    @Transactional
    @Override
    public void task(Integer taskId) {
        Map<String,String> h5ParamMap = tenementDao.selectPid();
        h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_PERSONAL_LIST);
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
        if(httpResponse != null){
            try {
                JSONObject obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_personal_list_response");
                if(obj != null){
                    H5MktPersonalListResponse h5MktPersonalListResponse = JSON.parseObject(obj.toString(),H5MktPersonalListResponse.class);
                    insertPersonalInfo(h5MktPersonalListResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void insertPersonalInfo(H5MktPersonalListResponse h5MktPersonalListResponse) {
        List<Map<String,Object>> paramPersonals = new ArrayList<Map<String,Object>>();
        if(h5MktPersonalListResponse.getPersonals().getPersonal() != null && h5MktPersonalListResponse.getPersonals().getPersonal().size() > 0){
            for(H5Personal h5Personal :h5MktPersonalListResponse.getPersonals().getPersonal()){
                if(personAlreadySaved(h5Personal)) continue;
                Map<String,Object> paramPersonal = new HashMap<String,Object>();
                paramPersonal.put("wx_acct",h5Personal.getUin());
                paramPersonal.put("nickname",h5Personal.getNickname());
                paramPersonal.put("type",1);
                paramPersonal.put("header_image",h5Personal.getHeadImage());
                paramPersonal.put("sex",h5Personal.getSex());
                paramPersonal.put("province",h5Personal.getProvince());
                paramPersonal.put("city",h5Personal.getCity());
                paramPersonal.put("signature",h5Personal.getSignature());
                paramPersonals.add(paramPersonal);
            }
            if(paramPersonals != null && paramPersonals.size() > 0){
                wechatRegisterDao.batchInsertPersonList(paramPersonals);
            }
        }
    }

    private boolean personAlreadySaved(H5Personal h5Personal) {
        boolean flag = false;
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("wx_acct",h5Personal.getUin());
        Long id = wechatRegisterDao.selectPersonalId(paramMap);
        if(id != null && id > 0){
            flag = true;
        }
        return flag;
    }
}
