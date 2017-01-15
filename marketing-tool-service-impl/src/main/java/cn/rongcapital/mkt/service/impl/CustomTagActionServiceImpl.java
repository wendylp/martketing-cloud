package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagDao;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.service.CustomTagActionService;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by byf on 1/15/17.
 */
@Service
public class CustomTagActionServiceImpl implements CustomTagActionService{

    @Autowired
    private MongoCustomTagDao mongoCustomTagDao;

    @Override
    public BaseOutput insertCustomTag() {
        CustomTag customTag = new CustomTag();
        customTag.setCustomTagId("XXXXXX");
        customTag.setCustomTagName("测试标签");
        mongoCustomTagDao.insertCustomTag(customTag);
        return new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
    }
}
