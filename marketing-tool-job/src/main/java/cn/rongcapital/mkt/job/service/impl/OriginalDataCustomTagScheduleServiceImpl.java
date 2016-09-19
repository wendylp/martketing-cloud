package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.OriginalDataCustomerTagsDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.OriginalDataCustomerTags;
import cn.rongcapital.mkt.service.OriginalDataCustomTagScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bianyulong on 16/6/24.
 */
@Service
public class OriginalDataCustomTagScheduleServiceImpl implements OriginalDataCustomTagScheduleService, TaskService {

    @Autowired
    private OriginalDataCustomerTagsDao originalDataCustomerTagsDao;

    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {
        OriginalDataCustomerTags paramOriginalDataCustomTags = new OriginalDataCustomerTags();
        paramOriginalDataCustomTags.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
        int totalCount = originalDataCustomerTagsDao.selectListCount(paramOriginalDataCustomTags);
        int totalPages = (totalCount + BATCH_NUM - 1) / BATCH_NUM;
        paramOriginalDataCustomTags.setPageSize(BATCH_NUM);
        for (int i = 0; i < totalPages; i++) {
            paramOriginalDataCustomTags.setStartIndex(Integer.valueOf(i * BATCH_NUM));
            List<OriginalDataCustomerTags> originalDataCustomerTags =
                    originalDataCustomerTagsDao.selectList(paramOriginalDataCustomTags);
            if (originalDataCustomerTags.isEmpty()) {
                continue;
            }
            handleOriginalDataCustomTags(originalDataCustomerTags);
        }
    }

    // 处理OriginalDataArchPoint的数据
    private void handleOriginalDataCustomTags(List<OriginalDataCustomerTags> tmpOriginalDataCustomTags) {
        if (tmpOriginalDataCustomTags.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataCustomTags.size();

        List<DataCustomerTags> dataCustomerTags = new ArrayList<>(batchCount);
        // 将OriginalDataCustomTags的数据同步到DataCustomTags
        for (int i = 0; i < batchCount; i++) {
            DataCustomerTags paramDataCustomTags = new DataCustomerTags();
            OriginalDataCustomerTags tmpOriginalDataCustomerTag = tmpOriginalDataCustomTags.get(i);
            BeanUtils.copyProperties(tmpOriginalDataCustomerTag, paramDataCustomTags);

            // 因为在一个事务里 , 直接修改OriginalDataCustomTags的状态
            tmpOriginalDataCustomerTag.setStatus(StatusEnum.PROCESSED.getStatusCode().byteValue());
            originalDataCustomerTagsDao.updateById(tmpOriginalDataCustomerTag);
            dataCustomerTags.add(paramDataCustomTags);
        }

        Map<String, List<DataCustomerTags>> paramMap = new HashMap<>();
        paramMap.put("list", dataCustomerTags);

        dataCustomerTagsDao.cleanAndUpdateByOriginal(paramMap);
    }

    @Override
    public void task(Integer taskId) {
        cleanData();
    }
}
