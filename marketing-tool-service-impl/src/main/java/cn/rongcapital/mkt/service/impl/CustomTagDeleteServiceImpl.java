package cn.rongcapital.mkt.service.impl;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.service.CustomTagDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CustomTagDeleteServiceImpl implements CustomTagDeleteService {

    @Autowired
    private CustomTagDao customTagDao;

    @Override
    public Object deleteCustomTag(String method, String userToken, Integer tag_id) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        CustomTag customTag = new CustomTag();
        customTag.setId(new Long(tag_id.longValue()));
        customTag.setStatus(new Integer(1).byteValue());
        customTag.setUpdateTime(new Date());
        customTagDao.updateById(customTag);

        result.setTotal(result.getData().size());

        return Response.ok().entity(result).build();
    }

}
