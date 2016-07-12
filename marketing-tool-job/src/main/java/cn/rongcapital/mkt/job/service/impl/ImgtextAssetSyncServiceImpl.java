package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.H5ImgtextType;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.vo.in.H5TuwenSyncResponse;
import cn.rongcapital.mkt.job.vo.in.MaterialContent;
import cn.rongcapital.mkt.job.vo.in.WTuwen;
import cn.rongcapital.mkt.po.ImgTextAsset;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
        h5ParamMap.put("sync_mp","true");
        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5ParamMap);
        if(httpResponse != null){
            try {
                JSONObject obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_wtuwen_sync_response");
                if(obj != null){
                    h5TuwenSyncResponse = JSON.parseObject(obj.toString(),H5TuwenSyncResponse.class);
                    if(h5TuwenSyncResponse != null){
                        Integer totalNumber = h5TuwenSyncResponse.getTotal();
                        if(totalNumber != null){
                            syncImgtextByPageNum(h5ParamMap, totalNumber);
                        }
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
                        batchSyncImgtext(h5TuwenSyncResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void batchSyncImgtext(H5TuwenSyncResponse h5TuwenSyncResponse) {
        List<ImgTextAsset> imgTextAssetInsertList = new ArrayList<ImgTextAsset>();
        List<ImgTextAsset> imgTextAssetUpdateList = new ArrayList<ImgTextAsset>();
        for(WTuwen wtuwen : h5TuwenSyncResponse.getWtuwens().getWtuwen()){
            if(wtuwen.getPubId() == null || wtuwen.getMaterialId() == null || !checkChannelType(wtuwen.getChannelType()) || wtuwen.getMaterialContents() == null ||CollectionUtils.isEmpty(wtuwen.getMaterialContents().getMaterialContent())) continue;
            MaterialContent materialContent = wtuwen.getMaterialContents().getMaterialContent().get(0);
            ImgTextAsset imgTextAsset = new ImgTextAsset();
            if(isAlreadySync(wtuwen.getMaterialId())){
                //已经导入的图文进行图文更新，通过Id
                constructLegalImagetextAsset(wtuwen, materialContent, imgTextAsset);
                Integer updateId = imgTextAssetDao.selectImgtextIdByMaterialId(Integer.toString(wtuwen.getMaterialId()));
                imgTextAsset.setId(updateId);
                imgTextAssetUpdateList.add(imgTextAsset);
            }else{
                //没有导入的图文进行图文插入，通过materialId做唯一标识
                constructLegalImagetextAsset(wtuwen, materialContent, imgTextAsset);
                imgTextAssetInsertList.add(imgTextAsset);
            }
        }

        if(!CollectionUtils.isEmpty(imgTextAssetInsertList)){
            for(ImgTextAsset imgTextAsset : imgTextAssetInsertList){
                imgTextAssetDao.insert(imgTextAsset);
            }
        }

        if(!CollectionUtils.isEmpty(imgTextAssetUpdateList)){
            for(ImgTextAsset imgTextAsset : imgTextAssetUpdateList){
                imgTextAssetDao.updateById(imgTextAsset);
            }
        }
    }

    private void constructLegalImagetextAsset(WTuwen wtuwen, MaterialContent materialContent, ImgTextAsset imgTextAsset) {
        imgTextAsset.setWechatStatus(wtuwen.getStatus().byteValue());
        imgTextAsset.setName(materialContent.getTitle());
        imgTextAsset.setImgfileUrl(materialContent.getScreenshotUrl());
        imgTextAsset.setPubId(wtuwen.getPubId());
        imgTextAsset.setPubName(wtuwen.getPubName());
        imgTextAsset.setMaterialId(Integer.toString(wtuwen.getMaterialId()));
        imgTextAsset.setCreateTime(DateUtil.getDateFromString(wtuwen.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
        imgTextAsset.setMobilePreviewUrl(materialContent.getMobileUrl());
        imgTextAsset.setShowCoverPic(materialContent.getShowCoverPic().byteValue());
        imgTextAsset.setPcPreviewUrl(materialContent.getPcUrl());
        imgTextAsset.setThumbReady(materialContent.getThumbReady().byteValue());
        if(wtuwen.getChannelType() != null){
            switch (wtuwen.getChannelType()){
                case 1:
                    imgTextAsset.setType(wtuwen.getChannelType().byteValue());
                    imgTextAsset.setOwnerName(wtuwen.getPubName());
                    break;
                case 2:
                    imgTextAsset.setType(H5ImgtextType.YI_QI_XIU.getType().byteValue());
                    imgTextAsset.setOwnerName(H5ImgtextType.YI_QI_XIU.getOwnerName());
                    break;
                case 3:
                    imgTextAsset.setType(H5ImgtextType.TU_ZHAN.getType().byteValue());
                    imgTextAsset.setOwnerName(H5ImgtextType.TU_ZHAN.getOwnerName());
                    break;
                case 4:
                    imgTextAsset.setType(H5ImgtextType.MAKA.getType().byteValue());
                    imgTextAsset.setOwnerName(H5ImgtextType.MAKA.getOwnerName());
                    break;
            }
        }
    }

    private boolean checkChannelType(Integer channelType) {
        if(channelType != null && channelType >= 1 && channelType <= 4) return true;
        return false;
    }

    private boolean isAlreadySync(Integer materialId) {
        Integer id = imgTextAssetDao.selectImgtextIdByMaterialId(Integer.toString(materialId));
        return id != null;
    }

}
