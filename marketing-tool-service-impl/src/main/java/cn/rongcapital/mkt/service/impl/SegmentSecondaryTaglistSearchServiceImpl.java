package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.SegmentSecondaryTaglistSearchService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagListSecondarySearchIn;
import org.springframework.stereotype.Service;

/**
 * Created by byf on 11/9/16.
 */

@Service
public class SegmentSecondaryTaglistSearchServiceImpl implements SegmentSecondaryTaglistSearchService {
    @Override
    public BaseOutput searchSegmentSecondaryTaglist(TagListSecondarySearchIn tagListSecondarySearchIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        return baseOutput;
    }
}
