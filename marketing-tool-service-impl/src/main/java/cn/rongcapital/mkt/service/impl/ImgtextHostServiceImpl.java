package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.HttpUtils;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.dao.TenementDao;
import cn.rongcapital.mkt.po.ImgTextAsset;
import cn.rongcapital.mkt.service.ImgtextHostService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgtextHostIn;
import cn.rongcapital.mkt.vo.in.H5MktWtuwenConvertResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-5-30.
 */
@Service
public class ImgtextHostServiceImpl implements ImgtextHostService {

    @Autowired
    private TenementDao tenementDao;
    @Autowired
    private ImgTextAssetDao imgTextAssetDao;

    @Override
    public Object hostImgtextAsset(ImgtextHostIn imgtextHostIn, SecurityContext securityContext) {
        BaseOutput ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        Map<String, String> h5RequestParamMap = tenementDao.selectPid();
        h5RequestParamMap.put("request_url", imgtextHostIn.getAssetUrl());

        HttpResponse httpResponse = HttpUtils.requestH5Interface(h5RequestParamMap);
        if(httpResponse != null){
            try {
                JSONObject obj = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity())).getJSONObject("hfive_mkt_wtuwen_convert_response");
                if(obj != null){
                    H5MktWtuwenConvertResponse h5MktWtuwenConvertResponse = JSON.parseObject(obj.toString(),  H5MktWtuwenConvertResponse.class);
                    ImgTextAsset imgTextAsset = new ImgTextAsset();
                    imgTextAsset.setName(h5MktWtuwenConvertResponse.getTitle());
                    imgTextAsset.setPcPreviewUrl(h5MktWtuwenConvertResponse.getPcUrl());
                    imgTextAsset.setMobilePreviewUrl(h5MktWtuwenConvertResponse.getMobileUrl());
                    imgTextAsset.setImgfileUrl(h5MktWtuwenConvertResponse.getScreenshopUrl());
                    imgTextAsset.setCreateTime(new Date(h5MktWtuwenConvertResponse.getCreateTime()));
                    switch (h5MktWtuwenConvertResponse.getChannelType()){
                        case 1:
                            imgTextAsset.setType((byte) 1);
                            imgTextAsset.setOwnerName("易企秀图文");
                            break;
                        case 2:
                            imgTextAsset.setType((byte) 2);
                            imgTextAsset.setOwnerName("兔展图文");
                            break;
                        case 3:
                            imgTextAsset.setType((byte) 3);
                            imgTextAsset.setOwnerName("MAMA图文");
                            break;
                    }
                    imgTextAssetDao.insert(imgTextAsset);
                    ur.setCode(ApiErrorCode.SUCCESS.getCode());
                    ur.setMsg(ApiErrorCode.SUCCESS.getMsg());
                }else{
                    ur.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
                    ur.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Response.ok().entity(ur).build();
    }
}
