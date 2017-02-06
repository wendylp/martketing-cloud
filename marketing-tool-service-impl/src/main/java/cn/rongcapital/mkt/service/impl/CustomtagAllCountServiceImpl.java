package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.service.CustomtagAllCountService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CustomtagAllCountServiceImpl implements CustomtagAllCountService {

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    /**
     * 功能描述：自定义标签个数
     * 
     * 接口：mkt.customtag.all.count
     * 
     * @return
     */
    @Override
    public BaseOutput customtagAllCount() {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ONE, null);

        long count = mongoCustomTagDao.countAll();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", count);

        result.getData().add(map);

        return result;
    }

}
