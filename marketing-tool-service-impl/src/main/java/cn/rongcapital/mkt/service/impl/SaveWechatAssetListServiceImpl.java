package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.service.SaveWechatAssetListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.SaveWechatAssetListIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-1.
 */
@Service
public class SaveWechatAssetListServiceImpl implements SaveWechatAssetListService{

    @Autowired
    private AudienceListDao audienceListDao;

    @Override
    public Object saveWechatAssetList(SaveWechatAssetListIn saveWechatAssetListIn, SecurityContext securityContext) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);

        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("audience_name",saveWechatAssetListIn.getPeopleGroupName());
        paramMap.put("create_time",new Date(System.currentTimeMillis()));
        int effectRow = audienceListDao.insertWechatGroups(paramMap);

        if(effectRow > 0){
            baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
            baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            baseOutput.setTotal(effectRow);
        }

        return Response.ok().entity(baseOutput).build();
    }
}
