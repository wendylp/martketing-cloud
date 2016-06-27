package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WechatRegisterDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.vo.in.H5MktPubListResponse;
import cn.rongcapital.mkt.job.vo.in.H5Pub;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-17.
 */
@Service
public class GetH5PubListServiceImpl implements TaskService {

    //Todo:同步大连那边获取服务号，订阅号接口。
    //Todo:1.调用Http请求获取接口返回值  (checked)
    //Todo:2.对接口返回值进行JSON解析，保存到entity类中 (checked)


    @Autowired
    private TenementDao tenementDao;

    @Autowired
    private WechatRegisterDao wechatRegisterDao;

    @Override
    public void task(Integer taskId) {
        Map<String,String> h5ParamMap = tenementDao.selectPid();
        h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_PUB_LIST_API);
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
        if(httpResponse != null){
            try {
                JSONObject obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_pub_list_response");
                H5MktPubListResponse h5MktPubListResponse = JSON.parseObject(obj.toString(),H5MktPubListResponse.class);
                //Todo:3.判断pub_id是否已经注册，如果没有注册则属于非法数据，反之属于合法数据，则更新register表中的数据
                checkPublistStatus(h5MktPubListResponse);
                updatePublistInfo(h5MktPubListResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkPublistStatus(H5MktPubListResponse h5MktPubListResponse) {
        ArrayList<H5Pub> dirtyH5Pubs = new ArrayList<H5Pub>();
        for(int index = 0 ; index < h5MktPubListResponse.getPubs().getPub().size() ; index++ ){
            H5Pub h5Pub = h5MktPubListResponse.getPubs().getPub().get(index);
            Integer status = wechatRegisterDao.selectStatus(h5Pub.getPubId());
            if(status == null || status == 1){
                dirtyH5Pubs.add(h5Pub);
            }
        }
        h5MktPubListResponse.getPubs().getPub().removeAll(dirtyH5Pubs);
    }

    private void updatePublistInfo(H5MktPubListResponse h5MktPubListResponse) {
        List<Map<String,Object>> paramList = new ArrayList<Map<String,Object>>();
        for(H5Pub h5Pub : h5MktPubListResponse.getPubs().getPub()){
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("wx_acct",h5Pub.getPubId());
            paramMap.put("name",h5Pub.getPubName());
            paramMap.put("header_image",h5Pub.getHeadUrl());
            if(h5Pub.getPubType() < 2 ){
                paramMap.put("type",2);
            }else{
                paramMap.put("type",0);
            }
            wechatRegisterDao.updatePubInfo(paramMap);
        }
    }
}
