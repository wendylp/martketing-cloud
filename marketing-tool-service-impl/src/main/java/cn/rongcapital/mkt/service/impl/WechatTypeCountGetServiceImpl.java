package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.WechatTypeCountGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-5-30.
 */
@Service
public class WechatTypeCountGetServiceImpl implements WechatTypeCountGetService{

    @Override
    public Object getWechatTypeCount() {
        Map<String,Object> typeCounts = new HashMap<String,Object>();
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
        return Response.ok().entity(baseOutput).build();
    }
}
