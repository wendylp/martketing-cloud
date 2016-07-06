package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.dao.WechatGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.vo.in.H5MktPubFansListResponse;
import cn.rongcapital.mkt.job.vo.in.H5PubFan;
import cn.rongcapital.mkt.job.vo.in.UserGroup;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by Yunfeng on 2016-6-17.
 */
@Service
public class GetPubFansListServiceImpl implements TaskService {

    //同步粉丝列表
    //Todo:1.调用Http请求获取接口返回值 (checked)
    //Todo:2.对接口返回值进行JSON解析，保存到entity类中(checked)
    //Todo:3.将获得的数据首先同步到粉丝表，其次推算出group表，最后再将group表同步到
    @Autowired
    private TenementDao tenementDao;
    @Autowired
    private WechatMemberDao wechatMemeberDao;
    @Autowired
    private WechatGroupDao wechatGroupDao;

    @Override
    public void task(Integer taskId) {
        if(true) return;
        Map<String,String> h5ParamMap = new HashMap<String,String>();
        h5ParamMap.put("pid",tenementDao.selectPid().get("pid"));
        h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_PUB_FANSLIST_API);
        h5ParamMap.put("page_size", ApiConstant.FANS_LIST_SYNC_SIZE + "");
        h5ParamMap.put("page_num",1 + "");
        h5ParamMap.put("start_time",DateUtil.getStringFromDate(new Date(System.currentTimeMillis()-24*3600*1000),"yyyy-MM-dd HH:mm:ss"));  //Todo:这个增量时间怎么算
        h5ParamMap.put("end_time", DateUtil.getStringFromDate(new Date(System.currentTimeMillis()),"yyyy-MM-dd HH:mm:ss"));   //Todo:这个增量怎么算
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
        if(httpResponse != null){
            JSONObject obj = null;
            try {
                String entityString = EntityUtils.toString(httpResponse.getEntity());
                if(entityString == null || "".equals(entityString)) return;
                obj = JSON.parseObject(entityString).getJSONObject("hfive_mkt_pub_fanslist_response");
                if(obj != null){
                    H5MktPubFansListResponse h5MktPubFansListResponse = JSON.parseObject(obj.toString(),H5MktPubFansListResponse.class);
                    long total = h5MktPubFansListResponse.getTotal();
                    for(int pageNumber =(int)(total/ApiConstant.FANS_LIST_SYNC_SIZE + 1); pageNumber >= 1; pageNumber--){
                        if (syncFansListByPageNum(h5ParamMap, entityString, pageNumber)) continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean syncFansListByPageNum(Map<String, String> h5ParamMap, String entityString, int pageNumber) throws IOException {
        HttpResponse httpResponse;
        JSONObject obj;
        H5MktPubFansListResponse h5MktPubFansListResponse;
        h5ParamMap.put("page_num",pageNumber + "");
        httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
        if(httpResponse != null){
            JSONObject insertObj = null;
            String insertString = EntityUtils.toString(httpResponse.getEntity());
            if(entityString == null || "".equals(entityString)) return true;
            obj = JSON.parseObject(insertString).getJSONObject("hfive_mkt_pub_fanslist_response");
            if(obj != null){
                h5MktPubFansListResponse = JSON.parseObject(obj.toString(), H5MktPubFansListResponse.class);
                if (h5MktPubFansListResponse != null && h5MktPubFansListResponse.getFans() != null && h5MktPubFansListResponse.getFans().getFan() != null && h5MktPubFansListResponse.getFans().getFan().size() > 0) {
                    fansBatchInsert(h5MktPubFansListResponse);
                }
            }
        }
        return false;
    }

    private void fansBatchInsert(H5MktPubFansListResponse h5MktPubFansListResponse) {
        List<Map<String,Object>> fansList = new ArrayList<Map<String,Object>>();
        for(H5PubFan h5PubFan : h5MktPubFansListResponse.getFans().getFan()){
            for(UserGroup userGroup : h5PubFan.getUserGroups().getUserGroup()){
                if(!isFansAlreadyImported(h5PubFan.getPubId(),h5PubFan.getOpenId())) continue;
                Map<String,Object> paramGroup = new HashMap<String,Object>();
                paramGroup.put("wx_acct",h5PubFan.getPubId());
                paramGroup.put("group_name",userGroup.getUserGroup());
                Integer groupId = wechatGroupDao.selectGroupId(paramGroup);
                if(groupId == null) {
                    wechatGroupDao.insertWechatGroup(paramGroup);
                    groupId = wechatGroupDao.selectGroupId(paramGroup);
                }
                Map<String,Object> paramFan = new HashMap<String,Object>();
                paramFan.put("wx_group_id",groupId);
                paramFan.put("wx_code",h5PubFan.getOpenId());
                if(h5PubFan.getName() != null && h5PubFan.getName().length() > 0){
                    paramFan.put("wx_name",h5PubFan.getName().replaceAll("[^\\u0000-\\uFFFF]", ""));
                }else{
                    paramFan.put("wx_name",null);
                }
                if(h5PubFan.getNickName() != null && h5PubFan.getNickName().length() > 0){
                    paramFan.put("nickname",h5PubFan.getNickName().replaceAll("[^\\u0000-\\uFFFF]", ""));
                }else{
                    paramFan.put("nickname",null);
                }
                paramFan.put("sex",h5PubFan.getSex());
                paramFan.put("country",h5PubFan.getCountry());
                paramFan.put("province",h5PubFan.getProvince());
                paramFan.put("city",h5PubFan.getCity());
                paramFan.put("county",h5PubFan.getCounty());
                paramFan.put("birthday",h5PubFan.getBirthday());
                paramFan.put("subscribe_yn",h5PubFan.getSubscribeYn());
                paramFan.put("subscribe_time",h5PubFan.getSubscribeTime());
                paramFan.put("active_time",h5PubFan.getActiveTime());
                paramFan.put("activity_48h_yn",h5PubFan.getActive48hYn());
                paramFan.put("head_image_url",h5PubFan.getHeadImageUrl());
                paramFan.put("remark",h5PubFan.getRemark());
                paramFan.put("pub_id",h5PubFan.getPubId());

                fansList.add(paramFan);
            }
        }

        if(fansList != null && fansList.size() > 0){
            wechatMemeberDao.batchInsertFans(fansList);
        }
    }

    private boolean isFansAlreadyImported(String pubId, String openId) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("pub_id",pubId);
        paramMap.put("wx_code",openId);
        Long id = wechatMemeberDao.selectIdByPubIdAndOpenId(paramMap);
        return id == null;
    }
}
