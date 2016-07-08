package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.vo.in.H5TuwenSyncResponse;
import cn.rongcapital.mkt.job.vo.in.WTuwen;
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
 * Created by Yunfeng on 2016-7-8.
 */
@Service
public class ImgtextAssetSyncServiceImpl implements TaskService{

    @Autowired
    ImgTextAssetDao imgTextAssetDao;
    @Autowired
    TenementDao tenementDao;

    @Override
    public void task(Integer taskId) {
        Map<String,String> h5ParamMap = new HashMap<String,String>();
        H5TuwenSyncResponse h5TuwenSyncResponse = null;
        Map<String,String> pidMap = tenementDao.selectPid();
        h5ParamMap.put(ApiConstant.DL_API_PARAM_METHOD,ApiConstant.DL_WUWEN_SYNC);
        h5ParamMap.put("pid",pidMap.get("pid"));
        h5ParamMap.put("page_size",ApiConstant.IMGTEXT_SYNC_SIZE + "");
        h5ParamMap.put("page_num","1");
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
        if(httpResponse != null){
            try {
                JSONObject obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_wtuwen_sync_response");
                if(obj != null){
                    h5TuwenSyncResponse = JSON.parseObject(obj.toString(),H5TuwenSyncResponse.class);
                    if(h5TuwenSyncResponse != null){
                        Integer totalNumber = h5TuwenSyncResponse.getTotal();
                        syncImgtextByPageNum(h5ParamMap, totalNumber);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void syncImgtextByPageNum(Map<String, String> h5ParamMap, Integer totalNumber) {
        HttpResponse httpResponse;
        JSONObject obj;
        H5TuwenSyncResponse h5TuwenSyncResponse;
        for(int pageNumber = totalNumber/ApiConstant.IMGTEXT_SYNC_SIZE +1; pageNumber > 0; pageNumber --){
            h5ParamMap.put("page_num",pageNumber + "");
            httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
            if(httpResponse != null){
                try {
                    obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_wtuwen_sync_response");
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


    private void batchInsertImgtext(H5TuwenSyncResponse h5TuwenSyncResponse) {
        List<Map<String,Object>> paramTuwenList = new ArrayList<Map<String,Object>>();
        for(WTuwen wtuwen : h5TuwenSyncResponse.getWtuwens().getWtuwen()){
            if(wtuwen.getPcUrl() == null || isAlreadySync(wtuwen.getPcUrl())) continue;
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("name",wtuwen.getTitle());
            paramMap.put("pc_preview_url",wtuwen.getPcUrl());
            paramMap.put("mobile_preview_url",wtuwen.getMobileUrl());
            paramMap.put("imgfile_url",wtuwen.getScreenshotUrl());
            paramMap.put("material_id",wtuwen.getMaterial_id());
            if(wtuwen.getChannelType() != null){
                switch (wtuwen.getChannelType()){
                    case 1:
                        paramMap.put("type", 0);
                        paramMap.put("owner_name",wtuwen.getPubName());
                        break;
                    case 2:
                        paramMap.put("type",1);
                        paramMap.put("owner_name","易企秀");
                        break;
                    case 3:
                        paramMap.put("type",2);
                        paramMap.put("owner_name","兔展");
                        break;
                    case 4:
                        paramMap.put("type",3);
                        paramMap.put("owner_name","MAMA");
                        break;
                }
            }else{
                continue;
            }

            paramTuwenList.add(paramMap);
        }
        if(paramTuwenList != null && paramTuwenList.size() > 0){
            imgTextAssetDao.batchInsertTuwen(paramTuwenList);
        }
    }

    private boolean isAlreadySync(String pcUrl) {
        Integer id = imgTextAssetDao.selectImgtextIdByPcPreviewUrl(pcUrl);
        return id != null;
    }

}
