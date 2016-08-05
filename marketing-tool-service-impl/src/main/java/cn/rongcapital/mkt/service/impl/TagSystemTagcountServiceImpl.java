package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import cn.rongcapital.mkt.service.TagSystemTagcountService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TagSystemTagcountServiceImpl implements TagSystemTagcountService {

    @Autowired
    private TaggroupDao taggroupDao;

    @Override
    public BaseOutput getTagcount(String method, String userToken) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Taggroup paramTaggroup = new Taggroup(0, 1);
        paramTaggroup.setOrderField("end_time");
        paramTaggroup.setOrderFieldType("DESC");
        taggroupDao.selectList(paramTaggroup);
        int tagCount = taggroupDao.selectSystemTagCount();
        List<Taggroup> taggroupResult = taggroupDao.selectList(paramTaggroup);
        Date resultDate = new Date();
        if (!CollectionUtils.isEmpty(taggroupResult)) {
            Date updateTime = taggroupResult.get(0).getUpdateTime();
            if (updateTime != null) {
                resultDate = updateTime;
            }
        }

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ONE, null);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tag_count", tagCount);
        map.put("sync_time", format.format(resultDate));
        result.getData().add(map);
        return result;
    }

}
