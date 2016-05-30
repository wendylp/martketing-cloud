package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.ImgtextHostService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ImgtextHostIn;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by Yunfeng on 2016-5-30.
 */
@Service
public class ImgtextHostServiceImpl implements ImgtextHostService {
    @Override
    public Object hostImgtextAsset(ImgtextHostIn imgtextHostIn, SecurityContext securityContext) {
        BaseOutput ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
        return Response.ok().entity(ur).build();
    }
}
