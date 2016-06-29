package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.service.TagDownloadCustomAudienceService;
import cn.rongcapital.mkt.vo.BaseOutput;

public class TagDownloadCustomAudienceServiceImpl implements TagDownloadCustomAudienceService {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public BaseOutput downloadCustomAudience(Integer tagId) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("tagId", tagId);
        List<DataParty> dataList = dataPartyDao.selectCustomAudiencesByTagId(paramMap);

        return null;
    }

}
