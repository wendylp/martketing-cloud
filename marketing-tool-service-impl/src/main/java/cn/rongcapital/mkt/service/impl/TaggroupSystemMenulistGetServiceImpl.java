/*************************************************
 * @功能简述: 获取系统标签组列表
 * @see MktApi
 * @author: nianjun
 * @version: 1.0 @date：2016-06-24
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.service.TaggroupSystemMenulistGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TaggroupSystemMenulistGetServiceImpl implements TaggroupSystemMenulistGetService {

    private static final long TOP_LEVEL = -1L;

    @Autowired
    TaggroupDao TaggroupDao;

    @Override
    public BaseOutput getTaggroupSystemMenulist(String method, String userToken, Integer index, Integer size) {

        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);

        Taggroup paramTaggroup = new Taggroup(0, 0);
        paramTaggroup.setParentGroupId(TOP_LEVEL);
        paramTaggroup.setStatus((byte)1); // 如果状态是0则不显示，是1则显示
        List<Taggroup> topTaggroups = TaggroupDao.selectList(paramTaggroup);
        List<Map<String, Object>> resultList = new ArrayList<>();

        // 获取一级节点的子节点
        if (CollectionUtils.isEmpty(topTaggroups)) {
            return baseOutput;
        } else {
            for (int i = 0; i < topTaggroups.size(); i++) {
                Taggroup topTaggroup = topTaggroups.get(i);
                Map<String, Object> selectMap = new HashMap<>();
                selectMap.put("select_name", topTaggroup.getName());
                selectMap.put("id", topTaggroup.getId());
                resultList.add(selectMap);
            }
        }

        baseOutput.getData().addAll(resultList);
        baseOutput.setTotalCount(resultList.size());

        return baseOutput;
    }

}