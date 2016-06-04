package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.service.WechatAssetListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-1.
 */
@Service
public class WechatAssetListGetServiceImpl implements WechatAssetListGetService{

    @Autowired
    private WechatAssetDao wechatAssetDao;
    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;

    @Override
    public Object getWechatAssetList(Integer assetId) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        if(assetId == null){
            baseOutput.setMsg("请求参数传递错误");
            return Response.ok().entity(baseOutput).build();
        }

        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("asset_id",assetId);

        Map<String,Object> assetDetaiMap = wechatAssetDao.selectWechatAssetDetai(paramMap);
        if(assetDetaiMap != null){
            setNicknameFlag(assetDetaiMap);
            assetDetaiMap.put("consignation_time", assetDetaiMap.remove("consignation_time").toString().substring(0,19));
            String groupIds = (String) assetDetaiMap.get("group_ids");
            String[] ids = groupIds.split(",");
            ArrayList<Map<String,Object>> groups = new ArrayList<Map<String,Object>>();
            for(String id : ids){
                Map<String,Object> group = wechatAssetGroupDao.selectGroupById(id);
                group.put("group_name",group.remove("name"));
                group.put("member_count",group.remove("members"));
                groups.add(group);
            }
            assetDetaiMap.put("group_data",groups);
            assetDetaiMap.remove("group_ids");
        }
        baseOutput.getData().add(assetDetaiMap);
        return Response.ok().entity(baseOutput).build();
    }

    private void setNicknameFlag(Map<String, Object> assetDetaiMap) {
        if(assetDetaiMap.remove("nickname") != null){
            assetDetaiMap.put("nickname_flag",true);
        }else{
            assetDetaiMap.put("nickname_flag",false);
        }
    }

}