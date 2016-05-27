package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.po.ImgTextAsset;
import cn.rongcapital.mkt.service.GetImgTextAssetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-5-27.
 */
@Service
public class GetImgTextAssetServiceImpl implements GetImgTextAssetService {

    @Autowired
    ImgTextAssetDao imgTextAssetDao;

    @Override
    public Object getImgTextAssetService(ImgAsset imgAsset) {

        Map<String, Object> paramMap = getParamMap(imgAsset);
        List<Map<String, Object>> imgTextAssets = getImgTextAssets(imgAsset, paramMap);
        BaseOutput baseOutput = constructBaseOutput(imgTextAssets);
        return Response.ok().entity(baseOutput).build();
    }

    private Map<String, Object> getParamMap(ImgAsset imgAsset) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("owner_name",imgAsset.getOwnerName());
        paramMap.put("type",imgAsset.getAssetType());
        paramMap.put("index",(imgAsset.getIndex()-1)*imgAsset.getSize());
        paramMap.put("size",imgAsset.getSize());
        return paramMap;
    }

    private List<Map<String, Object>> getImgTextAssets(ImgAsset imgAsset, Map<String, Object> paramMap) {
        List<Map<String,Object>> imgTextAssets = null;
        if(imgAsset.getAssetType() == 2){
            if(imgAsset.getOwnerName() == null){
                imgTextAssets = imgTextAssetDao.selectListByMap(paramMap);
            }else{
                imgTextAssets = imgTextAssetDao.selectListByName(paramMap);
            }
        }else{
            if(imgAsset.getOwnerName() == null){
                imgTextAssets = imgTextAssetDao.selectListByType(paramMap);
            }else{
                imgTextAssets = imgTextAssetDao.selectListByNameAndType(paramMap);
            }
        }
        return imgTextAssets;
    }


    private BaseOutput constructBaseOutput(List<Map<String,Object>> imgTextAssets) {
        BaseOutput baseOutput = new BaseOutput();
        baseOutput.setCode(0);
        baseOutput.setMsg("success");
        baseOutput.setTotal(imgTextAssets.size());
        for(Map<String,Object> map: imgTextAssets){
            map.put("imgtext_id",map.remove("id"));
            map.put("imgtext_name",map.remove("name"));
            baseOutput.getData().add(map);
        }
        return baseOutput;
    }
}
