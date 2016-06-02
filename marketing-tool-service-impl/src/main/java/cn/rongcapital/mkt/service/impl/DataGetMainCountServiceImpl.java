package cn.rongcapital.mkt.service.impl;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.service.DataGetMainCountService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataGetMainCountServiceImpl implements DataGetMainCountService {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public Object getMainCount(String method, String userToken, String ver) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        Map<String, Object> resultMap = dataPartyDao.selectMainCount();
        result.getData().add(resultMap);
        result.setTotal(result.getData().size());

        return Response.ok().entity(result).build();
    }

}
