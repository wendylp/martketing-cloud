package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
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
import cn.rongcapital.mkt.service.TaggroupSystemListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TaggroupSystemListGetServiceImpl implements TaggroupSystemListGetService {

    @Autowired
    TaggroupDao taggroupDao;

    @Override
    public BaseOutput getTagGroupByParentGroupId(String method, String userToken, Integer tagGroupId, Integer index,
                    Integer size) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        Taggroup paramTaggroup = new Taggroup();
        paramTaggroup.setParentGroupId(Long.valueOf(tagGroupId));
        paramTaggroup.setStartIndex(index);
        paramTaggroup.setPageSize(size);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Taggroup> groupList = taggroupDao.selectList(paramTaggroup);
        if (CollectionUtils.isNotEmpty(groupList)) {
            baseOutput.setTotal(groupList.size());
            for (Taggroup tagGroup : groupList) {
                Taggroup paramNodeTaggroup = new Taggroup();
                paramNodeTaggroup.setParentGroupId(Long.valueOf(tagGroup.getId()));
                int count = taggroupDao.selectListCount(paramNodeTaggroup);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tag_group_id", tagGroup.getId());
                map.put("tag_group_name", tagGroup.getName());
                map.put("tag_group_creat_time", sdf.format(tagGroup.getCreateTime()));
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
