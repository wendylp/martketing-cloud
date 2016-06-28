package cn.rongcapital.mkt.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.service.CustomTagGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CustomTagGetServiceImpl implements CustomTagGetService {

    @Autowired
    private CustomTagDao customTagDao;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public BaseOutput getCustomTagList(String method, String userToken, Integer index, Integer size) {

        CustomTag customTag = new CustomTag(index, size);
        List<CustomTag> customTagList = customTagDao.selectList(customTag);
        int totalCount = customTagDao.selectListCount(null);
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);

        if (customTagList != null && !customTagList.isEmpty()) {
            for (CustomTag tag : customTagList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tag_id", tag.getId());
                map.put("tag_name", tag.getName());
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                map.put("create_time", format.format(tag.getCreateTime()));
                map.put("cover_audience_count", tag.getCoverAudienceCount());
                result.getData().add(map);
            }
        }

        Map<String, Object> columnNameA = new HashMap<>();
        Map<String, Object> columnNameB = new HashMap<>();
        Map<String, Object> columnNameC = new HashMap<>();
        columnNameA.put("col_id", 1);
        columnNameB.put("col_id", 2);
        columnNameC.put("col_id", 3);

        columnNameA.put("col_code", "tag_name");
        columnNameB.put("col_code", "create_time");
        columnNameC.put("col_code", "cover_audience_count");

        columnNameA.put("col_name", "标签名称");
        columnNameB.put("col_name", "添加时间");
        columnNameC.put("col_name", "覆盖人群");

        List colNameList = new ArrayList();
        colNameList.add(columnNameA);
        colNameList.add(columnNameB);
        colNameList.add(columnNameC);

        result.setTotal(result.getData().size());
        result.setTotalCount(totalCount);
        result.setColNames(colNameList);
        return result;
    }
}
