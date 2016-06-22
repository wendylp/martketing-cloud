package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.job.vo.in.H5MktPubListResponse;
import cn.rongcapital.mkt.service.ImgtextAssetSyncService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.H5TuwenSyncResponse;
import cn.rongcapital.mkt.vo.in.ImgtextAssetSyncIn;
import cn.rongcapital.mkt.vo.in.WTuwen;
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
 * Created by Yunfeng on 2016-6-21.
 */
@Service
public class ImgtextAssetSyncServiceImpl implements ImgtextAssetSyncService{

    @Autowired
    ImgTextAssetDao imgTextAssetDao;
    @Autowired
    TenementDao tenementDao;

    @Override
    public BaseOutput syncImgtextAsset(ImgtextAssetSyncIn imgtextAssetSyncIn) {
        Map<String,String> h5ParamMap = new HashMap<String,String>();
        H5TuwenSyncResponse h5TuwenSyncResponse = null;
        Map<String,String> pidMap = tenementDao.selectPid();
        h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_WUWEN_SYNC);
        h5ParamMap.put("pid",pidMap.get("pid"));
        h5ParamMap.put("page_size","100");
        h5ParamMap.put("page_number","1");
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
        if(httpResponse != null){
            try {
                JSONObject obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_wtuwen_sync_response");
                if(obj != null){
                    h5TuwenSyncResponse = JSON.parseObject(obj.toString(),H5TuwenSyncResponse.class);
                    batchInsertImgtext(h5TuwenSyncResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(h5TuwenSyncResponse != null){
            //Todo:2.根据total和pagesize进行循环请求并且同步到数据库里
            for(int pageNumber = 2; pageNumber <= h5TuwenSyncResponse.getTotal()/100 + 1; pageNumber ++){
                h5ParamMap.put("page_number",pageNumber + "");
                httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
                if(httpResponse != null){
                    try {
                        JSONObject obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_wtuwen_sync_response");
                        if(obj != null){
                            h5TuwenSyncResponse = JSON.parseObject(obj.toString(),H5TuwenSyncResponse.class);
                            batchInsertImgtext(h5TuwenSyncResponse);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private void batchInsertImgtext(H5TuwenSyncResponse h5TuwenSyncResponse) {
        List<Map<String,Object>> paramTuwenList = new ArrayList<Map<String,Object>>();
        for(WTuwen wtuwen : h5TuwenSyncResponse.getWtuwens().getWtuwen()){
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("name",wtuwen.getTitle());
            paramMap.put("pc_preview_url",wtuwen.getPcUrl());
            paramMap.put("mobile_preview_url",wtuwen.getMobileUrl());
            paramMap.put("imgfile_url",wtuwen.getScreenshotUrl());
            if(wtuwen.getChannelType() != null){
                switch (wtuwen.getChannelType()){
                    case 1:
                        paramMap.put("type", 0);
                        paramMap.put("owner_name",wtuwen.getPubName());
                        break;
                    case 2:
                        paramMap.put("type",1);
                        paramMap.put("owner_name","易企秀图文");
                        break;
                    case 3:
                        paramMap.put("type",2);
                        paramMap.put("owner_name","兔展图文");
                        break;
                    case 4:
                        paramMap.put("type",3);
                        paramMap.put("owner_name","MAMA图文");
                        break;
                }
            }

            paramTuwenList.add(paramMap);
        }
        imgTextAssetDao.batchInsertTuwen(paramTuwenList);
    }
}
