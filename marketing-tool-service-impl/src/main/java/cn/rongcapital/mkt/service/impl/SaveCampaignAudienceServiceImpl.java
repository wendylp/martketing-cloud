package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.service.SaveCampaignAudienceService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.Audience;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.*;

/**
 * Created by Xu kun on 2016-6-1.
 */
@Service
public class SaveCampaignAudienceServiceImpl implements SaveCampaignAudienceService{

    @Autowired
    private AudienceListDao audienceListDao;
    

    @Override    
    public Object saveCampaignAudience(Audience audience, SecurityContext securityContext) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);

        //1.现根据传进来的name判断这个人群名称是否已经存在，存在返回，不存在继续下一步
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("audience_name",audience.getAudience_name());
        Long id = audienceListDao.selectIdByAudienceName(paramMap);
        if(id != null){
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            baseOutput.setMsg("人群名称已经重复");
            return Response.ok().entity(baseOutput).build();
        }

        //2.保存人群名称到audience_list表中        
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("audience_name",audience.getAudience_name());        
        dataMap.put("create_time",new Date(System.currentTimeMillis()));
        
        audienceListDao.insertAudience(dataMap);
        

        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(0);
        return Response.ok().entity(baseOutput).build();
    }
}
