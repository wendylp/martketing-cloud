/*************************************************
 * @功能简述: 获取系统标签内容列表
 * @see MktApi
 * @author: zhangwei
 * @version: 1.0 @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.PagingUtil;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.service.TagSystemListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TagSystemListGetServiceImpl implements TagSystemListGetService {

    @Autowired
    TaggroupDao taggroupDao;

    @Override
    public BaseOutput getTagcount(String method, String userToken, String tagGroupName, Integer index, Integer size) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);

        PagingUtil.fixPagingParam(index, size);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tagGroupName", tagGroupName);
        paramMap.put("index", index);
        paramMap.put("size", size);
        List<String> tagNames = taggroupDao.selectSubNodesByGroupName(paramMap);

        baseOutput.getData().addAll(tagNames);
        if (!CollectionUtils.isEmpty(tagNames)) {
            baseOutput.setTotal(tagNames.size());
        }

        return baseOutput;
    }

}
