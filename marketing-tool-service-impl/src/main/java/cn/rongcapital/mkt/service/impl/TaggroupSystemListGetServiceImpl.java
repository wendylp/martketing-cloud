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
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.service.TaggroupSystemListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TaggroupSystemListGetServiceImpl implements TaggroupSystemListGetService {

    @Autowired
    TaggroupDao taggroupDao;

    @Autowired
    TagDao tagDao;

    @Override
    public BaseOutput getTagGroupByParentGroupId(String method, String userToken, Integer tagGroupId, Integer index,
                    Integer size) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        Taggroup taggroup = new Taggroup();
        taggroup.setParentGroupId(Long.valueOf(tagGroupId));
        taggroup.setStartIndex(index);
        taggroup.setPageSize(size);
        List<Taggroup> groupList = taggroupDao.selectList(taggroup);
        if (CollectionUtils.isNotEmpty(groupList)) {
            baseOutput.setTotal(groupList.size());
            for (Taggroup group : groupList) {
                int count = tagDao.selectListCountByGroupId(String.valueOf(group.getId()));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tag_group_id", group.getId());
                map.put("tag_group_name", group.getName());
                map.put("tag_group_creat_time", group.getCreateTime());
                map.put("tag_count", count);
                baseOutput.getData().add(map);
            }
        }

        baseOutput.setColNames(generateColumnNameList());

        return baseOutput;
    }

    private List<Object> generateColumnNameList() {
        List<Object> columnList = new ArrayList<>();
        Map<String, Object> groupNameColumnMap = new HashMap<>();
        Map<String, Object> groupCreateTimeColumnMap = new HashMap<>();
        Map<String, Object> countColumnMap = new HashMap<>();
        groupNameColumnMap.put("col_code", "tag_group_name");
        groupNameColumnMap.put("col_name", "标签名称");
        groupCreateTimeColumnMap.put("col_code", "tag_group_creat_time");
        groupCreateTimeColumnMap.put("col_name", "添加时间");
        countColumnMap.put("col_code", "tag_count");
        countColumnMap.put("col_name", "覆盖人群");
        columnList.add(groupNameColumnMap);
        columnList.add(groupCreateTimeColumnMap);
        columnList.add(countColumnMap);

        return columnList;
    }

}
