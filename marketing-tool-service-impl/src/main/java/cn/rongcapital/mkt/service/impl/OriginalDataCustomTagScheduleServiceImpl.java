package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.OriginalDataCustomerTagsDao;
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
public class OriginalDataCustomTagScheduleServiceImpl implements OriginalDataCustomTagScheduleService{
    /**
     * 每次批量处理的数据数
     */
    public static final int BATCH_NUM = 1000;

    @Autowired
    private OriginalDataCustomerTagsDao originalDataCustomerTagsDao;

    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {

        // 1. 取出需要处理的数据
        OriginalDataCustomerTags paramOriginalDataCustomTags = new OriginalDataCustomerTags();
        paramOriginalDataCustomTags.setStatus(false);
        // 查询没有被处理过的数据 (未删除的)
        List<OriginalDataCustomerTags> originalDataCustomerTags =
                originalDataCustomerTagsDao.selectList(paramOriginalDataCustomTags);

        if (originalDataCustomerTags.isEmpty()) {
            // 根本没有要处理的数据
            return;
        }

        // 2. 分多次处理所有的数据,每次处理BATCH_NUM条数据
        // 一共有多少条要处理的数据
        int totalCount = originalDataCustomerTags.size();

        // 需要多少次循环去处理,相当于一个房间住M个人,N个人需要多少房间的问题.不解释
        int loopCount = (totalCount + BATCH_NUM - 1) / BATCH_NUM;

        for (int i = 0; i < loopCount; i++) {
            // 每次循环中的临时数据表
            List<OriginalDataCustomerTags> tmpOriginalDataCustomTags = new ArrayList<>(BATCH_NUM);
            if (i == loopCount - 1) {
                tmpOriginalDataCustomTags =
                        originalDataCustomerTags.subList(i * BATCH_NUM, originalDataCustomerTags.size());
            } else {
                tmpOriginalDataCustomTags = originalDataCustomerTags.subList(i * BATCH_NUM, (i + 1) * BATCH_NUM - 1);
            }

            handleOriginalDataArchPoint(tmpOriginalDataCustomTags);

        }
    }

    // 处理OriginalDataArchPoint的数据
    private void handleOriginalDataArchPoint(List<OriginalDataCustomerTags> tmpOriginalDataCustomTags) {
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
            tmpOriginalDataCustomerTag.setStatus(Boolean.TRUE);
            originalDataCustomerTagsDao.updateById(tmpOriginalDataCustomerTag);
            dataCustomerTags.add(paramDataCustomTags);
        }

        Map<String, List<DataCustomerTags>> paramMap = new HashMap<>();
        paramMap.put("list", dataCustomerTags);

        dataCustomerTagsDao.cleanAndUpdateByOriginal(paramMap);
    }
}
