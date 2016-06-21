package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.vo.in.H5MktPubFansListResponse;
import cn.rongcapital.mkt.job.vo.in.H5Personal;
import cn.rongcapital.mkt.job.vo.in.H5PersonalContactlistResponse;
import cn.rongcapital.mkt.job.vo.in.PersonalContact;
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
 * Created by Yunfeng on 2016-6-20.
 */
@Service
public class GetPersonContactsList implements TaskService {

    @Autowired
    private TenementDao tenementDao;
    @Autowired
    private WechatMemberDao wechatMemberDao;

    @Override
    public void task(Integer taskId) {
        Map<String,String> h5ParamMap = tenementDao.selectPid();
        h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_PERSONAL_CONTACTLIST);
        h5ParamMap.put("uuid","wZ_5PrO_fw==");
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
        if(httpResponse != null){
            JSONObject obj = null;
            try {
                obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_personal_contactlist_response");
                if(obj != null){
                    H5PersonalContactlistResponse h5PersonalContactlistResponse = JSON.parseObject(obj.toString(),H5PersonalContactlistResponse.class);
                    batchInsertContacts(h5PersonalContactlistResponse);
                    System.out.print(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void batchInsertContacts(H5PersonalContactlistResponse h5PersonalContactlistResponse) {
        List<Map<String,Object>> paramContacts = new ArrayList<Map<String,Object>>();
        for(PersonalContact personalContact : h5PersonalContactlistResponse.getContacts().getContact()){
            Map<String,Object> paramContact = new HashMap<String,Object>();
            paramContact.put("wx_code", personalContact.getUcode());
            paramContact.put("nickname", personalContact.getNickname());
            paramContact.put("sex", personalContact.getSex());
            paramContact.put("signature", personalContact.getSignature());
            paramContact.put("province", personalContact.getProvince());
            paramContact.put("city", personalContact.getCity());

        }
    }
}
